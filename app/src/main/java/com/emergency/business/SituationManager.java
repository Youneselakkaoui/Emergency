package com.emergency.business;

import com.emergency.entity.Situation;

import java.util.List;

/**
 * Created by Noureddine on 18/04/2015.
 */
public interface SituationManager {
    public boolean add(Situation s);

    public Situation getById(int id);

    public List<Situation> getAll();

    public boolean edit(Situation s);

    public void remove(int id);

}
