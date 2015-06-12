package com.emergency.dao.impl;

import com.emergency.dao.AlerteDao;
import com.emergency.entity.Alerte;

import java.util.List;

/**
 * Created by elmehdiharabida on 24/05/2015.
 */
public class AlerteDaoImpl implements AlerteDao {
	@Override
	public int insert (Alerte alerte) {
		long id = 0L;
		if (alerte != null) {
			alerte.save();
			id = alerte.getId();
		}
		return (int) id;
	}

	@Override
	public int update (Alerte alerte) {
		long id = 0L;
		if (alerte != null) {
			alerte.save();
			id = alerte.getId();
		}
		return (int) id;
	}

	@Override
	public int delete (Alerte alerte) {
		alerte.delete();
		return 0;
	}

	@Override
	public Alerte select (long id) {
		return Alerte.findById(Alerte.class, id);
	}

	@Override
	public Alerte selectByIdAlerte (int id) {
		List<Alerte> alertes = Alerte.find(Alerte.class, "id_alerte = ?", String.valueOf(id));

		return (alertes != null && alertes.size() > 0) ? alertes.get(0) : null;

	}

	@Override
	public List<Alerte> selectAll () {
		return Alerte.listAll(Alerte.class);
	}
}
