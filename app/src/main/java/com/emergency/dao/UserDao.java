package com.emergency.dao;

import com.emergency.entity.User;

import java.util.List;

/**
 * Created by elmehdiharabida on 25/04/2015.
 * Utilisé pour propriétaire uniquement
 */

public interface UserDao {

    public long insert(User user);

    public long update(User user);

    public long delete(User user);

    public User selectUser();
}
