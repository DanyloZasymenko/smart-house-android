package com.danik.smarthouse.service;

import com.danik.smarthouse.model.User;

import java.util.List;

public interface UserService {

    User getUser();

    User save(String name,
              String middleName,
              String lastName,
              String email,
              String password);

    User update(Long id,
                String name,
                String middleName,
                String lastName,
                String email,
                Float temperature);

    User findOne(Long id);

    List<User> findAll();

    Boolean delete(Long id);

    User findByEmail(String email);

    List<User> findAllByHouseId(Long houseId);
}
