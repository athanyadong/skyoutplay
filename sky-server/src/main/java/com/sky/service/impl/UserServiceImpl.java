package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

        public static  final String WX_LOGIN= "https://api.weixin.qq.com/sns/jscode2session";

        @Autowired
       private WeChatProperties weChatProperties;

        @Autowired
        private UserMapper userMapper;
        /**
     * 微信登录
     * @param dto
     * @return
     */
    public User wxLogin(UserLoginDTO dto) {

        String openid = getOpenid(dto.getCode());

        //openid为空，登陆失败，跑出异常
        if (openid==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        //当前用户是否为新用户
        User user = userMapper.getByOpenId(openid);


        //新用户完成注册
        if (user == null){
             user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now()).build();
            userMapper.insert(user);
        }

        return user;
    }

    /**
     * 调用微信接口服务，获取openid
     * @param code
     * @return
     */
    private String getOpenid(String code){
        //调用微信接口，获得微信的openid
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid",weChatProperties.getAppid() );
        paramMap.put("secret",weChatProperties.getSecret() );
        paramMap.put("js_code",code );
        paramMap.put("grant_type","authorization_code");
        String wxLogin = HttpClientUtil.doGet(WX_LOGIN, paramMap);

        JSONObject jsonObject = JSON.parseObject(wxLogin);

        String openid = jsonObject.getString("openid");
        return openid;
    }







}
