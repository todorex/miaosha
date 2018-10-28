package com.todorex.miaosha.service.impl;

import com.todorex.miaosha.dao.MiaoShaUserDao;
import com.todorex.miaosha.domain.MiaoShaUser;
import com.todorex.miaosha.exception.GlobalException;
import com.todorex.miaosha.redis.MiaoshaUserKey;
import com.todorex.miaosha.redis.RedisService;
import com.todorex.miaosha.result.CodeMsg;
import com.todorex.miaosha.service.MiaoShaUserService;
import com.todorex.miaosha.util.MD5Util;
import com.todorex.miaosha.util.UUIDUtil;
import com.todorex.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author rex
 * 2018/10/25
 */
@Service
public class MiaoShaUserServiceImpl implements MiaoShaUserService {



    @Autowired
    private MiaoShaUserDao miaoShaUserDao;

    @Autowired
    private RedisService redisService;

    @Override
    public MiaoShaUser getById(long id) {
        // 取缓存
        MiaoShaUser user = redisService.get(MiaoshaUserKey.getById, ""+id, MiaoShaUser.class);
        if(user != null) {
            return user;
        }
        // 取数据库
        user = miaoShaUserDao.getById(id);
        if(user != null) {
            redisService.set(MiaoshaUserKey.getById, ""+id, user);
        }
        return user;
    }

    /**
     * 一定要先修改数据库，然后修改缓存
     * @param token
     * @param id
     * @param formPassword
     * @return
     */
    @Override
    public boolean updatePassword(String token, long id, String formPassword) {
        // 取user
        MiaoShaUser user = getById(id);
        if(user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        // 更新数据库
        MiaoShaUser toBeUpdate = new MiaoShaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPasswordToDBPassword(formPassword, user.getSalt()));
        miaoShaUserDao.update(toBeUpdate);
        // 处理缓存（要修改所有与用户相关的缓存）
        redisService.delete(MiaoshaUserKey.getById, ""+id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token, token, user);
        return true;
    }

    @Override
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
           throw  new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String inputPassword = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        // 手机号是否存在
        MiaoShaUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw  new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        // 验证密码
        String dbPassword = user.getPassword();
        String salt = user.getSalt();
        String calcPassword = MD5Util.formPasswordToDBPassword(inputPassword, salt);
        if (!calcPassword.equals(dbPassword)) {
            throw  new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        // 生成token
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }

    @Override
    public MiaoShaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return  null;
        } else {

            MiaoShaUser user =  redisService.get(MiaoshaUserKey.token, token, MiaoShaUser.class);
            if (user == null) {
                return null;
            } else {
                // 延长有效期
                addCookie(response, token, user);
            }
            return user;

        }

    }


    private void addCookie(HttpServletResponse response, String token, MiaoShaUser user) {
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
