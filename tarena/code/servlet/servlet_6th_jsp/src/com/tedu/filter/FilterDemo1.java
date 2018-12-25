package com.tedu.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FilterDemo1 implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

    /**
     * �����ص�������д���ĺ��ķ���
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("FilterDemo1.doFilter()");
        request.setCharacterEncoding("utf-8");
        String strCode = request.getParameter("code");
        if (!strCode.equals("�����ǵػ�")) {
            // ��ת��error.jsp ҳ����ʾ�û����Ŵ���
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } else {
            // ���й��������ſ���ִ�к������Դ
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
