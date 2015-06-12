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
		if (alerte != null && alerte.getSituation() != null && alerte.getSituation().getUser() != null) {
			alerte.setLue(false);
			alerte.getSituation().getUser().save();
			alerte.getSituation().save();
			alerte.save();
		}
		return alerte.getId();
	}

	@Override
	public long update (AlerteRecue alerte) {
		if (alerte != null && alerte.getSituation() != null && alerte.getSituation().getUser() != null) {
			alerte.getSituation().getUser().save();
			alerte.getSituation().save();
			alerte.save();
		}
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
		List<AlerteRecue> alertesRecues = AlerteRecue.find(AlerteRecue.class, "id_alerte = ?", String.valueOf(id));
		if (alertesRecues != null && alertesRecues.size() > 0) {
			return alertesRecues.get(0);
		}
		return null;
	}

	@Override
	public List<AlerteRecue> selectAll () {
		return AlerteRecue.listAll(AlerteRecue.class);
	}

	@Override
	public AlerteRecue findById (long id) {
		return AlerteRecue.findById(AlerteRecue.class, id);
	}

	@Override
	public void markAlerteLue (AlerteRecue alerteRecue) {
		alerteRecue.setLue(true);
		alerteRecue.save();
	}

	@Override
	public long nbrAlertesNonLues () {
		return AlerteRecue.count(AlerteRecue.class, "lue = ?", new String[]{"0"});
	}

	@Override
	public long insertOrUpdate (AlerteRecue alerte) {
		long id = 0L;
		List<AlerteRecue> alertes = AlerteRecue.find(AlerteRecue.class, "id_alerte = ?", String.valueOf(alerte.getIdAlerte()));
		if (alertes != null && alertes.size() > 0) {
			AlerteRecue alerteRemote = alertes.get(0);
			alerte.setId(alerteRemote.getId());
			alerte.getSituation().setId(alerteRemote.getSituation().getId());
			alerte.getSituation().getUser().setId(alerteRemote.getSituation().getUser().getId());

			alerte.getSituation().getUser().save();
			alerte.getSituation().save();
			alerte.save();

			id = alerte.getId();
		}
		return id;
	}
}
