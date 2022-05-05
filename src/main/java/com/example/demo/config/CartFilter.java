package com.example.demo.config;

import com.example.demo.dto.CartProduct;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter
public class CartFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Map<Long, CartProduct> cart = (Map<Long, CartProduct>) request.getSession().getAttribute("cart");
        if (cart == null)
            cart = new HashMap<Long, CartProduct>();

        request.getSession().setAttribute("cart", cart);
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
