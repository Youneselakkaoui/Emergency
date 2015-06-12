package com.emergency.business;

import com.emergency.entity.AlerteRecue;
import com.emergency.entity.Situation;

import java.util.List;

/**
 * Created by elmehdiharabida on 31/05/15.
 */
public interface AlerteRecueManager {

	public boolean add(AlerteRecue s);

	public AlerteRecue getById(long id);

	public List<AlerteRecue> getAll();

	public boolean edit(AlerteRecue s);

	public void remove(long id);

	int sync (AlerteRecue syncAlerteOut);

	long nbrNonLues();

	void majLue(long id);
}
