package com.danik.smarthouse.service;

import com.danik.smarthouse.model.LightConfig;

import java.sql.Time;
import java.util.List;

public interface LightConfigService {

    LightConfig save(Time startTime, Time endTime, Boolean active);

    LightConfig update(Long id, Time startTime, Time endTime, Boolean active);

    LightConfig findOne(Long id);

    List<LightConfig> findAll();

    Boolean delete(Long id);
}
