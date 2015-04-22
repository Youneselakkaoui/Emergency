package com.emergency.business;

import android.content.Context;

import com.emergency.dao.SituationDao;
import com.emergency.dao.SituationSQLiteDao;
import com.emergency.entity.Situation;

import java.util.List;

/**
 * Created by Noureddine on 20/04/2015.
 */
public class DefaultSituationManager implements SituationManager{
    private SituationDao situationDao;

    public DefaultSituationManager(Context c) {
        this.situationDao = new SituationSQLiteDao(c);
    }

    @Override
    public boolean add(Situation s) {
        return situationDao.insert(s) > 0 ;
    }

    @Override
    public Situation getById(int id) {
        return situationDao.select(id);
    }

    @Override
    public List<Situation> getAll() {
        return situationDao.selectAll();
    }

    @Override
    public boolean edit(Situation s) {
       return true;
    }

    @Override
    public void remove(int id) {
    //    situationDao.delete(id);
    }
}
