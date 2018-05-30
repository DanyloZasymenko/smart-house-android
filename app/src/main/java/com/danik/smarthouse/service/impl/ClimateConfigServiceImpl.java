package com.danik.smarthouse.service.impl;

import com.danik.smarthouse.model.ClimateConfig;
import com.danik.smarthouse.service.ClimateConfigService;

import java.util.List;

public class ClimateConfigServiceImpl implements ClimateConfigService {

    private static final String SERVER_URL = "http://192.168.1.232:9090/climate-config";

    @Override
    public ClimateConfig save(ClimateConfig climateConfig) {
        return null;
    }

    @Override
    public ClimateConfig update(ClimateConfig climateConfig) {
        return null;
    }

    @Override
    public ClimateConfig findOne(Long id) {
        return null;
    }

    @Override
    public List<ClimateConfig> findAll() {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
}
