package com.emergency.dao.impl;

import com.emergency.dao.AlerteRecueDao;
import com.emergency.entity.Alerte;
import com.emergency.entity.AlerteRecue;

import java.util.List;

/**
 * Created by elmehdiharabida on 31/05/15.
 */
public class AlerteRecueDaoImpl implements AlerteRecueDao {
	@Override
	public long insert (AlerteRecue alerte) {
		alerte.getSituation().getUser().save();
		alerte.getSituation().save();
		alerte.save();
		return alerte.getId();
	}

	@Override
	public long update (AlerteRecue alerte) {
		alerte.getSituation().getUser().save();
		alerte.getSituation().save();
		alerte.save();
		return alerte.getId();
	}

	@Override
	public long delete (AlerteRecue alerte) {
		long id = alerte.getId();
		alerte.getSituation().getUser().delete();
		alerte.getSituation().delete();
		alerte.delete();
		return id;
	}

	@Override
	public AlerteRecue select (int id) {
		List<AlerteRecue> alertesRecues = AlerteRecue.find(AlerteRecue.class,"idAlerte = ?",String.valueOf(id));
		if(alertesRecues != null && alertesRecues.size()>0)
			return alertesRecues.get(0);
		return null;
	}

	@Override
	public List<AlerteRecue> selectAll () {
		return AlerteRecue.listAll(AlerteRecue.class);
	}

	@Override
	public AlerteRecue findById (long id) {
		return AlerteRecue.findById(AlerteRecue.class,id);
	}
}
