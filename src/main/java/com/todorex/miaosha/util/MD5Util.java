package com.todorex.miaosha.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author rex
 * 2018/10/25
 */
public class MD5Util {
    private static final String SALT = "1a2b3c4d";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * 输入密码 -> 表单传输密码
     * 浏览器传输加密
     * @param inputPassword
     * @return
     */
    public static String inputPasswordToFormPassword(String inputPassword)  {
        String str = "" + SALT.charAt(4) + SALT.charAt(3) + inputPassword + SALT.charAt(2) + SALT.charAt(1);
        return md5(str);
    }

    /**
     * 表单传输密码 -> 数据库密码
     * @param formPassword
     * @param salt
     * @return
     */
    public static String formPasswordToDBPassword(String formPassword, String salt) {
        String str = "" + salt.charAt(2) + salt.charAt(3) + formPassword + salt.charAt(4) + salt.charAt(5);
        return md5(str);
    }


    /**
     * 输入密码 -> 数据库密码
     * @param inputPassword
     * @param DBSalt
     * @return
     */
    public static String inputPasswordToDBPassword(String inputPassword, String DBSalt) {
        String formPassword = inputPasswordToFormPassword(inputPassword);
        String dbPassword = formPasswordToDBPassword(formPassword, DBSalt);
        return dbPassword;
    }
    public static void main(String[] args) {
        System.out.println(MD5Util.inputPasswordToFormPassword("123456"));
        System.out.println(MD5Util.inputPasswordToDBPassword("123456", "1a2b3c4d"));
    }
}
