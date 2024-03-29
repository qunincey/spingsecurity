package com.qunincey.security.core.social.qq.api;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @program: spingsecurity
 * @description:
 * @author: qiuxu
 * @create: 2019-06-25 15:35
 **/

public class QQImpl extends AbstractOAuth2ApiBinding  implements QQ {

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private ObjectMapper objectMapper = new ObjectMapper();

    private String appId;
    private String openId;

    public QQImpl(String accessToken,String appId){
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        String url = String.format(URL_GET_OPENID,accessToken);
        String result = getRestTemplate().getForObject(url,String.class);

        this.openId = StringUtils.substringBetween(result,"\"openid\":\"","\"}");


    }



    @Override
    public QQUserInfo getUserInfo() throws IOException  {

        String url = String.format(URL_GET_USERINFO,appId,openId);
        String result = getRestTemplate().getForObject(url,String.class);
        QQUserInfo userInfo=objectMapper.readValue(result,QQUserInfo.class);
        userInfo.setOpenId(openId);

        return userInfo;
    }
}
