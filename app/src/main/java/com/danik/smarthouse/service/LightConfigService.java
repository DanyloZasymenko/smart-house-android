package com.danik.smarthouse.service;

import com.danik.smarthouse.model.LightConfig;

import java.util.List;

public interface LightConfigService {

    LightConfig save(LightConfig lightConfig);

    LightConfig update(LightConfig lightConfig);

    LightConfig findOne(Long id);

    List<LightConfig> findAll();

    Boolean delete(Long id);
}
