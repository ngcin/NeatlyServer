package com.geeyao.common.repository;

import com.geeyao.common.bean.Replay;
import com.geeyao.common.bean.Room;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReplayRepository extends CrudRepository<Replay, String> {

    public List<Replay> findByRoomId(String roomId);

    public List<Replay> findByRoomIdAndRound(String roomId, int round);
}
