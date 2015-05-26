package com.emergency.dao;

import com.emergency.entity.User;

import java.util.List;

/**
 * Created by elmehdiharabida on 25/04/2015.
 * Utilisé pour propriétaire uniquement
 */

public interface UserDao {

    public int insert(User user);

    public int update(User user);

    public int delete();

    public User selectUser();
}
