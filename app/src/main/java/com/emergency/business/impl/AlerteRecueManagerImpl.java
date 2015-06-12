package com.emergency.business.impl;

import com.emergency.business.AlerteRecueManager;
import com.emergency.dao.AlerteRecueDao;
import com.emergency.dao.impl.AlerteRecueDaoImpl;
import com.emergency.entity.Alerte;
import com.emergency.entity.AlerteRecue;

import java.util.List;

/**
 * Created by elmehdiharabida on 31/05/15.
 */
public class AlerteRecueManagerImpl implements AlerteRecueManager {

	AlerteRecueDao alerteRecueDao = new AlerteRecueDaoImpl();

	@Override
	public boolean add (AlerteRecue s) {
		return alerteRecueDao.insert(s) > 0;
	}

	@Override
	public AlerteRecue getById (long id) {
		return alerteRecueDao.findById(id);
	}

	@Override
	public List<AlerteRecue> getAll () {
		return alerteRecueDao.selectAll();
	}

	@Override
	public boolean edit (AlerteRecue s) {
		return alerteRecueDao.update(s) > 0;
	}

	@Override
	public void remove (long id) {
		alerteRecueDao.delete(alerteRecueDao.findById(id));

	}

	@Override
	public int sync (AlerteRecue syncAlerteOut) {
		if (syncAlerteOut == null) {
			return -1;
		}
		if (findByIdAlerte(syncAlerteOut.getIdAlerte()))
		//TODO update

		{
			return 0;
		}
		add(syncAlerteOut);
		return 1;
	}

	@Override
	public long nbrNonLues () {
		return alerteRecueDao.nbrAlertesNonLues();
	}

	@Override
	public void majLue (long id) {
		alerteRecueDao.markAlerteLue(alerteRecueDao.findById(id));
	}

	private boolean findByIdAlerte (int idAlerte) {
		List<AlerteRecue> alertes = AlerteRecue.find(AlerteRecue.class, "id_Alerte = ?", String.valueOf(idAlerte));

		return !((alertes == null) || (0 == alertes.size()));
	}
}
