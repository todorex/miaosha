package com.todorex.miaosha.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author rex
 * 2018/10/25
 */
@Data
public class MiaoShaUser {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
