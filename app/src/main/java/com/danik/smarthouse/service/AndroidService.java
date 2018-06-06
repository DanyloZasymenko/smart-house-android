package com.danik.smarthouse.service;

import com.danik.smarthouse.model.AlertButtons;
import com.danik.smarthouse.model.Device;
import com.danik.smarthouse.model.Temperature;

public interface AndroidService {

    Device changeActive(Long deviceId, Boolean active);

    Temperature getTemperature();

    AlertButtons checkAlert();

    AlertButtons alert(Boolean fire, Boolean police);
}
