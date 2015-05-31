package com.emergency.dao;

import com.emergency.entity.AlerteRecue;

import java.util.List;

/**
 * Created by elmehdiharabida on 31/05/15.
 */
public interface AlerteRecueDao {
	public long insert(AlerteRecue alerte);

	public long update(AlerteRecue alerte);

	public long delete(AlerteRecue alerte);

	public AlerteRecue select(int id);

	public List<AlerteRecue> selectAll();

	public AlerteRecue findById(long id);
}
