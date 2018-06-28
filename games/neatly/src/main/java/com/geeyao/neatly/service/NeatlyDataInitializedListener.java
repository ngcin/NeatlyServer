package com.geeyao.neatly.service;

import com.geeyao.common.log.Log;
import com.geeyao.neatly.compare.CompareCtrl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NeatlyDataInitializedListener {
    @Log
    private Logger log;
    @Autowired
    private CompareCtrl compareCtrl;

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        log.info("十三水逻辑数据初始化中...");
        compareCtrl.init();
    }
}
