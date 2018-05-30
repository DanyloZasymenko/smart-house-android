package com.danik.smarthouse.service;

import com.danik.smarthouse.model.ClimateConfig;

import java.sql.Time;
import java.util.List;

public interface ClimateConfigService {

    ClimateConfig save(Double temperature, Time startTime, Time endTime, Boolean active);

    ClimateConfig update(Long id, Double temperature, Time startTime, Time endTime, Boolean active);

    ClimateConfig findOne(Long id);

    List<ClimateConfig> findAll();

    Boolean delete(Long id);
}
