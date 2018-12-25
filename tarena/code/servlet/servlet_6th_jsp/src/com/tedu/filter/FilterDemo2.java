package com.tedu.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FilterDemo2 implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("FilterDemo2.doFilter()");
        
        // 1. ��ȡ����
        request.setCharacterEncoding("utf-8");  // ���ý���ʱʹ�õı���
        String strCode2 = request.getParameter("code2");
        if (!strCode2.equals("���������")) {
            // ��ת������ҳ��
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        // ���������У��������ʺ������Դ
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
