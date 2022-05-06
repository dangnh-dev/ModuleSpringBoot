package com.example.demo.security;

import com.example.demo.entity.Provider;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.IUserRepository;
import com.example.demo.security.oauth.CustomOAuth2User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Oauth2SuccessLoginHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserService userService;
    @Autowired
    IUserRepository iUserRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String clientName = customOAuth2User.getClientName();
        UserEntity userEntity = iUserRepository.findByUsername(customOAuth2User.getEmail());
        Provider provider = Provider.LOCAL;

        if (clientName.equals("Facebook")){
            provider = Provider.FACEBOOK;
        } else if (clientName.equals("Google")){
            provider = Provider.GOOGLE;
        }
        userService.checkUserExistOauth2(customOAuth2User.getEmail(), provider);
        request.getSession().setAttribute("user", userEntity);
        response.sendRedirect("/home");
    }
}
