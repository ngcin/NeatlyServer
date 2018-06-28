package com.geeyao.common.repository;

import com.geeyao.common.bean.Room;
import com.geeyao.common.bean.UserRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRoomRepository extends CrudRepository<UserRoom, String> {

    public List<UserRoom> findByUserIdAndRoomId(String userId, String roomId);

    List<UserRoom> findByUserId(String userId);
}
