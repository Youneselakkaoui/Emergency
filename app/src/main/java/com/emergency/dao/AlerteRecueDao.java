package com.emergency.dao;

import com.emergency.entity.AlerteRecue;

import java.util.List;

/**
 * Created by elmehdiharabida on 31/05/15.
 */
public interface AlerteRecueDao {
	long insert (AlerteRecue alerte);

	long update (AlerteRecue alerte);

	long delete (AlerteRecue alerte);

	AlerteRecue select (int id);

	List<AlerteRecue> selectAll ();

	AlerteRecue findById (long id);

	void markAlerteLue (AlerteRecue alerteRecue);

	long nbrAlertesNonLues ();


	long insertOrUpdate (AlerteRecue alerte);
}
