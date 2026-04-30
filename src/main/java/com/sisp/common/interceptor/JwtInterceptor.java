package com.sisp.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sisp.beans.HttpResponseEntity;
import com.sisp.common.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (JwtUtil.validateToken(token) != null) {
                return true;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        HttpResponseEntity entity = new HttpResponseEntity();
        entity.setCode("401");
        entity.setMessage("未授权，请重新登录");
        new ObjectMapper().writeValue(response.getWriter(), entity);
        return false;
    }
}
