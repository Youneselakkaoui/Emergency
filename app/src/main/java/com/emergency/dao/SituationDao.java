package com.emergency.dao;

import com.emergency.entity.Situation;

import java.util.List;

/**
 * Created by Noureddine on 19/04/2015.
 */
public interface SituationDao {
    public int insert(Situation situation);

    public int update(Situation situation);

    public int delete(Situation situation);

    public Situation select(int id);

    public List<Situation> selectAll();
}
