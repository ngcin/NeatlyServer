package com.geeyao.common.service;

import com.geeyao.common.bean.Replay;
import com.geeyao.common.repository.ReplayRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReplayService {
    @Autowired
    private ReplayRepository replayRepository;

    public void saveReplay(Replay replay){
        replayRepository.save(replay);
    }

    public List<Replay> findByRoomId(String roomId){
        return replayRepository.findByRoomId(roomId);
    }

    public Replay findByRoomIdAndRound(String roomId, int round){
        List<Replay> replays = replayRepository.findByRoomIdAndRound(roomId, round);
        if(replays.size() > 0){
            return replays.get(0);
        }
        return null;
    }
}
