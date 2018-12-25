package com.tedu.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * ȫվ���봦�������
 * 
 * @author suyh
 *
 */
public class EncodingFilter implements Filter {

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // ������������
        // >> �����������(POST)
        request.setCharacterEncoding("UTF-8");

        // >> ��Ӧ��������
        response.setContentType("text/html;charset=UTF-8");

        HttpServletRequest myRequest = new MyHttpServletRequest((HttpServletRequest)request);

        // ���й�����
        chain.doFilter(myRequest, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}

/**
 * װ����ģʽ
 * 1. дһ��װ���࣬Ҫ��ͱ�װ������������ʵ��ͬһ���ӿڻ����Ǽ̳���ͬһ�� ���ࣻ
 *  ** дһ���࣬�̳�һ��װ����(�Ա�װ�ζ����˰�װ)
 * 2. �ṩ ���캯�������ձ�װ���߲�����������ڲ�
 * 3. ������Ҫ����ķ���ֱ�ӽ��и��죬���ڲ������ķ�����ֱ�ӵ��ã�ԭ�ж����ϵķ�����
 */

/**
 * װ����: MyHttpServletRequest
 * ��װ����: request ����
 * @author suyh
 *
 */
class MyHttpServletRequest extends HttpServletRequestWrapper
{
    HttpServletRequest request;
    
    public MyHttpServletRequest(HttpServletRequest request) {
        /*
         * ���ø���Ĺ��췽������request ���󴫸�����
         * �ø�����а�װ��Ȼ����������ֱ�� �̳и����װ��ķ�������
         */
        super(request);
        this.request = request;
    }
    
    /**
     * ����getParameter ���������������������(GET)
     */
    @Override
    public String getParameter(String name) {
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            return request.getParameter(name);
        }
        
//        String strValue = request.getParameter(name);
//        try {
//            strValue = new String(strValue.getBytes("ISO8859-1"), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return null;
//        }
        
        // ������û�����룬������ͷ���������UTF-8 
        return request.getParameter(name);
    }
}





















