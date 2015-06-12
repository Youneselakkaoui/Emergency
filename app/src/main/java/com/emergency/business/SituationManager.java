package com.emergency.business;

import com.emergency.entity.Situation;

import java.util.List;

/**
 * Created by Noureddine on 18/04/2015.
 */
public interface SituationManager {
	boolean add (Situation s);

	Situation getById (int id);

	List<Situation> getAll ();

	boolean edit (Situation s);

	void remove (Situation s);

}
