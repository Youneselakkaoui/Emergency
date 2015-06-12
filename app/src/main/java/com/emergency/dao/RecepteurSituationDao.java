package com.emergency.dao;

import java.util.List;

import com.emergency.entity.RecepteursSituation;

/**
 * Created by YOUNES on 27/05/2015.
 */
public interface RecepteurSituationDao {


	int insert (RecepteursSituation recepteursSituation);

	int update (RecepteursSituation recepteursSituation);

	int delete (RecepteursSituation recepteursSituation);

	RecepteursSituation select (int id);

	List<RecepteursSituation> findBySituationId(long id);

	void deleteByIdSituation (long idSituation);

	List<RecepteursSituation> findAll ();


}
