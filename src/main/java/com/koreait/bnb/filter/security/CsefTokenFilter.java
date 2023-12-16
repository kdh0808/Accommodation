package com.koreait.bnb.filter.security;

import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.*;
import java.io.IOException;

public class CsefTokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CsrfToken token = (CsrfToken) servletRequest.getAttribute("_csrf");
        System.out.println("생성된 CSRF Token:" + token.getToken());
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
