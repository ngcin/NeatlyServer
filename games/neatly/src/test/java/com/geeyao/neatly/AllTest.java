package com.geeyao.neatly;

import com.geeyao.neatly.compare.simple.*;
import com.geeyao.neatly.compare.special.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        //simple
        WuLongTest.class,
        DuiZiTest.class,
        LiangDuiTest.class,
        SanTiaoTest.class,
        ShunZiTest.class,
        TongHuaTest.class,
        HuLuTest.class,
        TieZhiTest.class,
        TongHuaShunTest.class,
        WuTongTest.class,
        //special
        SanTongHuaTest.class,
        SanShunZiTest.class,
        LiuDuiBanTest.class,
        WuDuiChongSanTest.class,
        SiTaoSanTiaoTest.class,
        CouYiSeTest.class,
        QuanXiaoTest.class,
        QuanDaTest.class,
        SanZhaDanTest.class,
        SanTongHuaShunTest.class,
        ShiErHuangZuTest.class,
        YiTiaoLongTest.class,
        ZhiZhunQingLongTest.class


})

public class AllTest {
}
