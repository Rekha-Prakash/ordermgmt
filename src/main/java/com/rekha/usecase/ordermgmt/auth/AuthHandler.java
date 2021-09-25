package com.rekha.usecase.ordermgmt.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rekha.usecase.ordermgmt.model.ErrorModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthHandler implements Filter {

    @Value("${app.auth.header}")
    private String authHeader;

    @Value("${app.auth.token}")
    private String authToken;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String message = null;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if (httpRequest.getServletPath().equals("/health") || authToken.equals(httpRequest.getHeader(authHeader))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            message = "Direct access to this resource is forbidden";
            servletResponse
                    .getWriter()
                    .write(new ObjectMapper().writeValueAsString(new ErrorModel(message, "403", null)));
            servletResponse.setContentType(MediaType.APPLICATION_JSON.toString());
            httpResponse.setStatus(403);
        }
    }
}
