package com.emergency.business.impl;

import android.content.Context;

import com.emergency.business.UserManager;
import com.emergency.dao.UserDao;
import com.emergency.dao.impl.UserDaoImpl;
import com.emergency.entity.User;

/**
 * Created by elmehdiharabida on 10/05/2015.
 */
public class UserManagerImpl implements UserManager {

    private UserDao userDao;

    public UserManagerImpl() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public boolean create(User user) {
        return userDao.insert(user) > 0;
    }

    @Override
    public User getUser() {
        return userDao.selectUser();
    }

    @Override
    public boolean edit(User s) {
        return userDao.update(s) > 0;
    }

    @Override
    public void remove(User user) {
        userDao.delete(user);

    }
}
