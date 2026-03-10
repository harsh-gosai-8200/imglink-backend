package com.springboot.imglinkbackend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Configuration
public class RateLimitFilter implements Filter {

    private static final long TIME_WINDOW_MS = TimeUnit.MINUTES.toMillis(1);
    private static final int MAX_REQUESTS = 10;

    private Map<String, RequestInfo> ipRequestMap = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ip = httpRequest.getRemoteAddr();

        RequestInfo info = ipRequestMap.getOrDefault(ip, new RequestInfo(0, System.currentTimeMillis()));

        long now = System.currentTimeMillis();

        if (now - info.startTime > TIME_WINDOW_MS) {
            info.count = 1;
            info.startTime = now;
        } else {
            info.count++;
        }

        ipRequestMap.put(ip, info);

        if (info.count > MAX_REQUESTS) {
            ((HttpServletResponse) response).setStatus(429);
            response.getWriter().write("Too many requests. Try again later.");
            return;
        }

        chain.doFilter(request, response);
    }

    private static class RequestInfo {
        int count;
        long startTime;

        RequestInfo(int count, long startTime) {
            this.count = count;
            this.startTime = startTime;
        }
    }
}
