package com.emergency.dao;

import com.emergency.entity.User;

import java.util.List;

/**
 * Created by elmehdiharabida on 25/04/2015.
 */
public interface UserDao {

    public int insert(User situation);

    public int update(User situation);

    public int delete(User situation);

    public User select(String telephone);

    public User selectUser();
}
