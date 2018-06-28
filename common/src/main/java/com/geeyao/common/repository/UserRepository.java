package com.geeyao.common.repository;

import com.geeyao.common.bean.User;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

public interface UserRepository extends CrudRepository<User, BigInteger> {
    public User findByUnionId(String unionId);

    public List<User> findByCurrentRoomId(String unionId);
}
