package com.emergency.dao;

import com.emergency.entity.Alerte;

import java.util.List;

/**
 * Created by elmehdiharabida on 24/05/2015.
 */
public interface AlerteDao {

    public int insert(Alerte alerte);

    public int update(Alerte alerte);

    public int delete(Alerte alerte);

    public Alerte select(long id);

    public Alerte selectByIdAlerte(int id);

    public List<Alerte> selectAll();
}
