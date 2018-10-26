package com.todorex.miaosha.vo;

import com.todorex.miaosha.validator.IsMobile;
import lombok.Data;

import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

/**
 * @Author rex
 * 2018/10/25
 */
@Data
public class LoginVo {
    @NonNull
    @IsMobile
    private String mobile;
    @NonNull
    @Length(min=32)
    private String password;
}
