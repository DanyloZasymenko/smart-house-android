package com.danik.smarthouse.service;

import com.danik.smarthouse.model.ClimateConfig;

import java.util.List;

public interface ClimateConfigService {

    ClimateConfig save(ClimateConfig climateConfig);

    ClimateConfig update(ClimateConfig climateConfig);

    ClimateConfig findOne(Long id);

    List<ClimateConfig> findAll();

    Boolean delete(Long id);
}
