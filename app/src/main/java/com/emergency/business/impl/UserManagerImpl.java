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

    public UserManagerImpl(Context c) {
        this.userDao = new UserDaoImpl(c);
    }

    @Override
    public boolean create(User user) {
        return userDao.insert(user) > 0 ;
    }

    @Override
    public User getUser() {
        return userDao.selectUser();
    }

    @Override
    public boolean edit(User s) {
        return false;
    }

    @Override
    public void remove(int id) {

    }
}
