package com.app.MessageScheduler.authenticator;

import com.app.MessageScheduler.entity.Client;
import com.app.MessageScheduler.entity.Response;
import com.app.MessageScheduler.util.AuthorizationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class ClientAuth implements HandlerInterceptor {

    @Autowired
    AuthorizationUtil authorizationUtil;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        String token= request.getHeader("clientToken");
        Client client= authorizationUtil.validateToken(token);

        if(client==null){
            response.setContentType("application/json");
            response.setStatus(400);

            PrintWriter out= response.getWriter();
            Response resp= new Response(1001, "Authentication failed");
            String responseString= new ObjectMapper().writeValueAsString(resp);
            out.print(responseString);

            return false;
        }

        request.setAttribute("client",client);

        return true;
    }

}
