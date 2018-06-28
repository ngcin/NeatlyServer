package com.geeyao.common.repository;

import com.geeyao.common.bean.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, String> {

    public Room findByRoomNum(long roomNum);

}
