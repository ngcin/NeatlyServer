package com.geeyao.common.service;

import com.geeyao.common.bean.AccessToken;
import com.geeyao.common.bean.WxUserInfo;
import com.geeyao.common.log.Log;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Component
public class WeChatService {
    RestTemplate restTemplate = new RestTemplate();
    @Log
    private Logger log;
    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.appSecret}")
    private String appSecret;
    @Value("${wx.jsdomain}")
    private String wxDomain;

    public WeChatService() {
        for (HttpMessageConverter<?> httpMessageConverter : restTemplate.getMessageConverters()) {
            if (httpMessageConverter instanceof AbstractJackson2HttpMessageConverter) {
                List<MediaType> supportedMediaTypes = httpMessageConverter.getSupportedMediaTypes();
                ArrayList<MediaType> mediaTypes = new ArrayList<>(supportedMediaTypes);
                mediaTypes.add(MediaType.TEXT_PLAIN);
                ((AbstractJackson2HttpMessageConverter) httpMessageConverter).setSupportedMediaTypes(mediaTypes);
            }
        }
    }

    public String getWxUrlForCode(String origin) {
        String redirectUrl = String.format("https://%s/?origin=%s#/wx/authback", wxDomain, origin);
        String url;
        try {
            url = String.format("https://open.weixin.qq.com/connect/oauth2/authorize" +
                            "?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=I#wechat_redirect",
                    appId, URLEncoder.encode(redirectUrl, "UTF-8"));
            log.info("微信网页登录url: " + url);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("转码失败，请检查微信绑定域名的配置", e);
        }
        return url;
    }

    public WxUserInfo getWxUserInfo(String code) {
        AccessToken accessToken = restTemplate.getForObject("https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + appId + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code", AccessToken.class);
        log.info("获得access code: " + accessToken.getAccessToken());
        WxUserInfo wxUserInfo = restTemplate.getForObject("https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + accessToken.getAccessToken() + "&openid=" + accessToken.getOpenId() + "&lang=zh_CN", WxUserInfo.class);
        if (StringUtils.isEmpty(wxUserInfo.getUnionId())) {
            wxUserInfo.setUnionId(wxUserInfo.getOpenId());//如果因为公众号没有绑定开发平台，就不会有union id，那就用open id来替代。
            log.info("获取不到union id，将union id设为open id");
        }
        return wxUserInfo;
    }
}
