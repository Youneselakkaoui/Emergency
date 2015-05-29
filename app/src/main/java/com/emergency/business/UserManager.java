package com.emergency.business;

import com.emergency.entity.User;

/**
 * Created by elmehdiharabida on 10/05/2015.
 */
public interface UserManager {
    public boolean create(User user);

    public User getUser();

    public boolean edit(User s);

    public void remove(User user);
}
