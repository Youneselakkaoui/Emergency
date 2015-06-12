package com.emergency.dao;

import com.emergency.entity.Situation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by YOUNES on 27/05/2015.
 */
public interface SituationDao {


	int insert (Situation situation);

	int update (Situation situation);

	int delete (Situation situation);

	Situation select (int id);

	List<Situation> findAll ();


}