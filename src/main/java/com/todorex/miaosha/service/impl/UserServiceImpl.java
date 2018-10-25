package com.todorex.miaosha.service.impl;

import com.todorex.miaosha.dao.UserDao;
import com.todorex.miaosha.domain.User;
import com.todorex.miaosha.service.UserService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author rex
 * 2018/10/25
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }
}
