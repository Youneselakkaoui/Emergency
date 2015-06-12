package com.emergency.business;

import com.emergency.entity.Alerte;

import java.util.List;

/**
 * Created by elmehdiharabida on 06/06/15.
 */
public interface AlerteManager {
	boolean add (Alerte s);

	Alerte getById (long id);

	Alerte getByIdAlerte (int id);

	List<Alerte> getAll ();

	boolean edit (Alerte s);

	void remove (Alerte s);


}
