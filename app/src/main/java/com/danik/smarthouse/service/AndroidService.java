package com.danik.smarthouse.service;

import com.danik.smarthouse.model.Device;
import com.danik.smarthouse.model.Temperature;

public interface AndroidService {

    Device changeActive(Long deviceId, Boolean active);

    Temperature getTemperature();
}
