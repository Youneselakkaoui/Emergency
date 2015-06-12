package com.emergency.business.impl;

import com.emergency.business.AlerteManager;
import com.emergency.dao.AlerteDao;
import com.emergency.dao.impl.AlerteDaoImpl;
import com.emergency.entity.Alerte;

import java.util.List;

/**
 * Created by elmehdiharabida on 06/06/15.
 */
public class AlerteManagerImpl implements AlerteManager {

	private static AlerteDao alerteDao = new AlerteDaoImpl();

	@Override
	public boolean add (Alerte s) {
		return alerteDao.insert(s) > 0;
	}

	@Override
	public Alerte getById (long id) {
		return alerteDao.select(id);
	}

	@Override
	public Alerte getByIdAlerte (int id) {
		return alerteDao.selectByIdAlerte(id);
	}


	@Override
	public List<Alerte> getAll () {
		return alerteDao.selectAll();
	}

	@Override
	public boolean edit (Alerte s) {
		return alerteDao.update(s) > 0;
	}

	@Override
	public void remove (Alerte s) {
		alerteDao.delete(s);

	}
}
