package com.themeeplers.statistics.config.cors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kanakisn on 12/02/2017.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {
    
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req instanceof HttpServletRequest) {
            String url = ((HttpServletRequest) req).getRequestURL().toString();
            if (url.contains("/v1") || url.contains("/v2")|| url.contains("/api")) {
                HttpServletResponse response = (HttpServletResponse) res;
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, " +
                        "Accept, Authorization");
                response.setHeader("Access-Control-Expose-Headers", "Location");
                if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    return;
                }
            }
        }
        chain.doFilter(req, res);
    }
    
    public void init(FilterConfig filterConfig) {}
    
    public void destroy() {}
    
}
