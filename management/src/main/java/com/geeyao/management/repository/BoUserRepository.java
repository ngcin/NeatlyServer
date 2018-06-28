package com.geeyao.management.repository;

import com.geeyao.management.bean.BoUser;
import org.springframework.data.repository.CrudRepository;

public interface BoUserRepository extends CrudRepository<BoUser, String> {
    BoUser findByCsid(String csid);

    BoUser findByUserNameAndPassword(String userName, String password);
}
