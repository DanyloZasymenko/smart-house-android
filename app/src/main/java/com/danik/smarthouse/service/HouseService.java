package com.danik.smarthouse.service;

import com.danik.smarthouse.model.House;

import java.util.List;

public interface HouseService {

    House save(String name, String serial);

    House update(Long id, String name, String serial);

    Boolean getStatus();

    House findOne(Long id);

    List<House> findAll();

    Boolean delete(Long id);

    House findBySerial(String serial);

    House createOrFindBySerial(String serial);
}
