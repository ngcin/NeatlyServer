package com.geeyao.neatly;


public class MsgDef {
    public final static short LOAD_CONFIG = 200;//
    public final static short VERSION = 201;
    public final static short LOAD_SERVER = 300;//获取服务器列表

    public final static short LOAD_LOGIN = 101;
    public final static short GET_USER_INFO = 20002;
    public final static short CREATE_USER_INFO = 20003;

    public final static short CREATE_HOUSE = 21001;
    public final static short ADD_TO_HOUSE = 21002;
    public final static short Del_HOUSE = 21003;//解散房间..
    public final static short Prepare_HOUSE = 21004;//准备..
    // 出牌
    public final static short DESIGN_POKER = 21005;
    public final static short GET_RESULT = 21006;	//查看战绩..
    public final static short DOUBLE = 21007;	//闲家加倍..
    public final static short CHAT_TEXT = 21008;	//聊天..

    public final static short LEVEL_HOUSE = 21009;//满局后退出房间，返回大厅..

    public final static short CONTINUE = 21010;//续局..

    public final static short ASK = 21011;//提交反馈..
    public final static short ASK_INFO = 21012;//获取反馈结果..

    public final static short UPDATE_USERNAME = 21013;//更新玩家名字..

    public final static short SET_AGENT = 21014;//设置代理..
    public final static short AGENT = 21015;//代理..
    public final static short FORCE = 21018;//强制比牌..

    public final static short BACKTO_MAIN = 21019;//返回大厅..
    public final static short LAST_RESULT = 21020;//上一局战绩..
    public final static short GET_SHARE_REWARD = 21021;//分享微信成功..

    public final static short VIP_LOTTYERY = 21022;	//抽奖..
    public final static short VIP_RANK = 21023;	//排行榜..

    public final static short REMOVE_USER = 21024;	//T人..


    public final static short ADD_HOUSE = 22001;
    public final static short UPDATE_HOUSE = 22002;

    // 发牌
    public final static short DEAL_POKER = 22003;
    // 比牌
    public final static short PK_POKER = 22004;

    public final static short SERVER_Del_HOUSE = 22005;

    public final static short CONTINUE_SUCCESS = 22006;//续局成功..

    public final static short BE_REMOVE_USER = 22007;//被T成功..

    public final static short CHANGE_BAG = 23001;

    public final static short PAY_SUCCESS = 23002; //充值到账..

    public final static short CHAT_SUCCESS = 24001; //收到聊天..

    public final static short NOTICE_SUCCESS = 24002; //公告..

    public final static short HEART = 20001; //心跳..
}
