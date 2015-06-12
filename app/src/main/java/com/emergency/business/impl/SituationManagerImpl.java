package com.emergency.business.impl;

import android.content.Context;

import com.emergency.business.SituationManager;
import com.emergency.dao.RecepteurSituationDao;
import com.emergency.dao.SituationDao;
import com.emergency.dao.impl.RecepteurSituationDaoImpl;
import com.emergency.dao.impl.SituationDaoImpl;
import com.emergency.entity.RecepteursSituation;
import com.emergency.entity.Situation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noureddine on 20/04/2015.
 */
public class SituationManagerImpl implements SituationManager {
	private static SituationDao situationDao = new SituationDaoImpl();
	private static RecepteurSituationDao recepteurSituationDao = new RecepteurSituationDaoImpl();

	@Override
	public boolean add (Situation s) {
		return situationDao.insert(s) > 0;
	}

	@Override
	public Situation getById (int id) {
		return situationDao.select(id);
	}

	@Override
	public List<Situation> getAll () {
		List<Situation> situations = situationDao.findAll();

		for (Situation s : situations) {
			s.setRecepteursSituations(recepteurSituationDao.findBySituationId(s.getId()));

		}


		return situations;
	}

	@Override
	public boolean edit (Situation s) {
		return situationDao.update(s) > 0;
	}

	@Override
	public void remove (Situation s) {
		situationDao.delete(s);
	}
}
