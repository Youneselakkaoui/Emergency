package com.emergency.dao.impl;


import com.emergency.dao.UserDao;
import com.emergency.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by elmehdiharabida on 25/04/2015.
 */
public class UserDaoImpl implements UserDao {


	@Override
	public long insert (User user) {
		user.setId(null);
		user.setLastChanged(new Date());
		user.setSyncTime(new Date(user.getLastChanged().getTime() - 1000L));
		user.save();
		return user.getId();
	}

	@Override
	public long update (User user) {
		user.setId(1L);
		user.setLastChanged(new Date());
		if (user.getSyncTime() == null) {
			user.setSyncTime(selectUser().getSyncTime());
		}
		user.save();
		return user.getId();
	}

	@Override
	public long delete (User user) {
		long id = user.getId();
		user.delete();
		return id;
	}


	@Override
	public User selectUser () {
		return User.findById(User.class, 1L);

	}

	private List<User> selectAll () {
		List<User> users = new ArrayList<User>();
		while (User.findAll(User.class).hasNext())
			users.add(User.findAll(User.class).next());
		return users;
	}
}
