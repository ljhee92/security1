package com.cos.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    // google 로부터 받은 userRequest 데이터에 대한 후처리를 진행하는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
        System.out.println("userRequest accessToken : " + userRequest.getAccessToken().getTokenValue());
        System.out.println("userRequest attributes : " + super.loadUser(userRequest).getAttributes());
        return super.loadUser(userRequest);
    }
}