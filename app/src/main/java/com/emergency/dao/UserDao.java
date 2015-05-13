package com.emergency.dao;

import com.emergency.entity.User;

import java.util.List;

/**
 * Created by elmehdiharabida on 25/04/2015.
 */
public interface UserDao {

    public int insert(User user);

    public int update();

    public int delete();

    public User selectUser();
}
