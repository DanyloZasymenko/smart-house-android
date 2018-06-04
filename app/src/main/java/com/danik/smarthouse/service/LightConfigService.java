package com.danik.smarthouse.service;

import com.danik.smarthouse.model.LightConfig;

import java.util.List;

public interface LightConfigService {

    LightConfig save(String startTime, String endTime, Boolean active);

    LightConfig update(Long id, String startTime, String endTime, Boolean active);

    LightConfig findOne(Long id);

    List<LightConfig> findAll();

    Boolean delete(Long id);
}
