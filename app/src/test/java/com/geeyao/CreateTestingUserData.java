package com.geeyao;

import com.geeyao.common.bean.WxUserInfo;
import com.geeyao.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CreateTestingUserData {
    @Autowired
    private UserService userService;


    public static void main(String[] args) {
        SpringApplication.run(CreateTestingUserData.class, args);
    }

    @EventListener
    public void addingTestData(ContextRefreshedEvent event) throws InterruptedException {
        List<String> imgList = new ArrayList<>();
        imgList.add("http://i1.wp.com/inews.gtimg.com/newsapp_match/0/1181577115/0");
        imgList.add("http://i1.wp.com/inews.gtimg.com/newsapp_match/0/1181577135/0");
        imgList.add("http://i1.wp.com/inews.gtimg.com/newsapp_match/0/1181576910/0");
        imgList.add("http://i1.wp.com/inews.gtimg.com/newsapp_match/0/1181576991/0");
        imgList.add("http://i1.wp.com/inews.gtimg.com/newsapp_match/0/1181576905/0");
        imgList.add("http://i1.wp.com/inews.gtimg.com/newsapp_match/0/1181576908/0");
        imgList.add("http://i1.wp.com/inews.gtimg.com/newsapp_match/0/1181576900/0");
        imgList.add("http://i1.wp.com/inews.gtimg.com/newsapp_match/0/1181576903/0");
        imgList.add("http://i1.wp.com/inews.gtimg.com/newsapp_match/0/1181577131/0");
        imgList.add("http://i1.wp.com/inews.gtimg.com/newsapp_match/0/1181576985/0");
        for (int i = 0; i < imgList.size(); i++) {
            String nickName = i + "号美女";
            String unionId = "m" + i;
            if (userService.getByUnionId(unionId) == null) {
                userService.saveUser(new WxUserInfo(unionId, nickName, imgList.get(i)));
                System.out.println("增加了：" + nickName);
            } else {
                System.out.println("已经存在：" + nickName);
            }
        }
        System.exit(1);
    }
}
