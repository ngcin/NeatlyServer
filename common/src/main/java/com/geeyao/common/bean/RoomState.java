package com.geeyao.common.bean;

public enum RoomState {
    Ready,//等待玩家加入或准备
    InGame,//牌局开始
    Compareing,//正在比牌
    Over,//所有牌局比完，结束
}
