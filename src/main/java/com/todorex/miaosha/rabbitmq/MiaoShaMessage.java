package com.todorex.miaosha.rabbitmq;

import com.todorex.miaosha.domain.MiaoShaUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author rex
 * 2018/10/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiaoShaMessage {
    private MiaoShaUser user;
    private long productId;
}
