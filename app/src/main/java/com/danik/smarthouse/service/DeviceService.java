package com.danik.smarthouse.service;

import com.danik.smarthouse.model.Device;
import com.danik.smarthouse.model.enums.DeviceType;

import java.util.List;

public interface DeviceService {

    Device save(String name, Integer pin, String deviceType, Long houseId);

    Device update(Long id, String name, Integer pin, String deviceType, Long houseId);

    Device findOne(Long id);

    List<Device> findAll();

    Boolean delete(Long id);

    Device findByNameAndHouseId(String name, Long houseId);

    List<Device> findAllByHouseId(Long houseId);

    List<Device> findAllByActiveAndHouseId(Boolean active, Long houseId);

    List<Device> findAllByDeviceTypeAndHouseId(DeviceType deviceType, Long houseId);
}
