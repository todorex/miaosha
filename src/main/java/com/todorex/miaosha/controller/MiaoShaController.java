package com.todorex.miaosha.controller;

import com.todorex.miaosha.access.AccessLimit;
import com.todorex.miaosha.domain.MiaoShaOrder;
import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.domain.OrderInfo;
import com.todorex.miaosha.rabbitmq.MQSender;
import com.todorex.miaosha.rabbitmq.MiaoShaMessage;
import com.todorex.miaosha.redis.MiaoShaKey;
import com.todorex.miaosha.redis.ProductKey;
import com.todorex.miaosha.redis.RedisService;
import com.todorex.miaosha.result.CodeMsg;
import com.todorex.miaosha.result.Result;
import com.todorex.miaosha.service.MiaoShaService;
import com.todorex.miaosha.service.OrderService;
import com.todorex.miaosha.service.ProductService;
import com.todorex.miaosha.util.MD5Util;
import com.todorex.miaosha.util.UUIDUtil;
import com.todorex.miaosha.vo.ProductVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @Author rex
 * 2018/10/26
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoShaController implements InitializingBean{

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoShaService miaoShaService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender sender;

    /**
     * Redis标记
     * 标记商品已经卖完
     * 接下来就不用再去访问redis了
     * 优化点
     */
    private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();

    /**
     * 系统初始化做的事
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<ProductVo> productList = productService.listProductVo();
        if (productList == null) {
            return;
        }
        // 1. 系统初始化，把商品库存数量加载到Redis
        for (ProductVo product : productList) {
            redisService.set(ProductKey.getMiaoShaProductStock, "" + product.getId(), product.getStockCount());
            localOverMap.put(product.getId(), false);
        }

    }


    @PostMapping("/{path}")
    public String miaosha(Model model, MiaoShaUser user, @RequestParam("productId") long productId, @PathVariable("path") String path) {
        model.addAttribute("user", user);

        //验证path
        boolean check = miaoShaService.checkPath(user, productId, path);
        if(!check){
            model.addAttribute("errMsg", CodeMsg.REQUEST_ILLEGAL.getMsg());
            return "miaosha_failure";
        }

        // 内存标记，用来减少redis访问
        boolean over = localOverMap.get(productId);
        if (over) {
            model.addAttribute("errMsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_failure";
        }


        // 2. 收到请求，Redis预减库存，库存不足，直接返回
        long stock = redisService.decr(ProductKey.getMiaoShaProductStock, "" + productId);
        if (stock < 0) {
            model.addAttribute("errMsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_failure";

        }
        // 判断是否已经秒杀到了
        MiaoShaOrder order = orderService.getMiaoShaOrderByUserIdAndProductId(user.getId(), productId);
        if(order != null) {
            model.addAttribute("errMsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_failure";
        }
        // 3. 请求入队，立即返回排队中
        MiaoShaMessage mm = new MiaoShaMessage();
        mm.setUser(user);
        mm.setProductId(productId);
        sender.sendMiaoshaMessage(mm);
        model.addAttribute("productId",productId);
        return "miaosha_queue";


    }

    @AccessLimit(seconds = 2, maxCount = 5, needLogin = true)
    @GetMapping("/path")
    @ResponseBody
    public Result<String> miaoShaPath(Model model, MiaoShaUser user,
                                      @RequestParam("productId") long productId,
                                      @RequestParam(value="verifyCode", defaultValue="0")int verifyCode) {
        boolean check = miaoShaService.checkVerifyCode(user, productId, verifyCode);
        if(!check) {
            return Result.failure(CodeMsg.REQUEST_ILLEGAL);
        }
        String path  =miaoShaService.createMiaoshaPath(user, productId);
        return Result.success(path);
    }


    @RequestMapping(value="/result", method= RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoShaResult(Model model, MiaoShaUser user,
                                      @RequestParam("productId")long productId) {
        // 5. 客户端轮询，是否秒杀成功
        model.addAttribute("user", user);
        if(user == null) {
            return Result.failure(CodeMsg.SESSION_ERROR);
        }
        long result  = miaoShaService.getMiaoShaResult(user.getId(), productId);
        return Result.success(result);
    }


    @RequestMapping(value="/verifyCode", method=RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoShaVerifyCod(HttpServletResponse response, MiaoShaUser user,
                                              @RequestParam("productId")long productId) {
        if(user == null) {
            return Result.failure(CodeMsg.SESSION_ERROR);
        }
        try {
            BufferedImage image  = miaoShaService.createVerifyCode(user, productId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        }catch(Exception e) {
            e.printStackTrace();
            return Result.failure(CodeMsg.MIAOSHA_FAIL);
        }
    }

}
