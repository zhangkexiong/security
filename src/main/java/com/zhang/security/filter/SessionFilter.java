package com.zhang.security.filter;

import com.zhang.security.bean.User;
import com.zhang.security.service.UserService;
import com.zhang.security.utils.Constants;
import com.zhang.security.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * filter:表示对所有请求的预处理
 * 一般可以在这里做缓存
 */
public class SessionFilter implements Filter{

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @Autowired
    private UserService userService;

    private String []excludedUris;
    /**
     * 此方法中可以配置取消的拦截资源
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedUris = filterConfig.getInitParameter("excludeUri").split(",");
    }

    /**
     * 1.判断用户是否登录
     * 2.获取用户的信息
     * 在每次访问资源之前都根据传过来的token查询用户信息
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);
        String userInfo = getUserInfo(request);
        /**
         * 判断用户是否登录和userInfo值之间的关系
         */
        //如果是不需要过滤的资源,直接放行
        if (isExcludedUri(request.getRequestURI())){
            filterChain.doFilter(request,response);
        }
        else {
        if (user == null && userInfo != null){
            //执行本地的登录操作,将用户的信息保存到redis中
            response.sendRedirect("/login");
            return;
            //filterChain.doFilter(servletRequest,servletResponse);
        }
        else if (user != null && userInfo != null){
            //直接跳过
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else if (user != null && userInfo == null){
            //跳转到登出页面,执行退出登录(说明redis中的数据已经删除)
            response.sendRedirect("/logout");
            return;
            //filterChain.doFilter(servletRequest,servletResponse);
        }
        else {
            //跳转到登录页面,执行登录操作,同时进行登录成功的跳转页面
            response.sendRedirect(request.getContextPath()+ "/login");
            //filterChain.doFilter(servletRequest,servletResponse);
        }
        //filterChain.doFilter(servletRequest,servletResponse);
    } }

    /*@Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        try {
            final HttpServletRequest req = (HttpServletRequest) servletRequest;
            //final MyAuthentication myAuthentication = new MyAuthentication();
            final MyAuthentication userAuth=new MyAuthentication();
            SecurityContextHolder.getContext().setAuthentication(userAuth);
            HttpSession httpSession = req.getSession();
            User user = (User) httpSession.getAttribute(Constants.USER);
            //将session中的认证信息注入到Authentication中去
            System.out.println(httpSession.getId());
            userAuth.inject(user);
            httpSession.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
            System.out.println(httpSession.getId());
            System.out.println(httpSession.toString());
        }finally {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }*/


    @Override
    public void destroy() {

    }

    private String getUserInfo(HttpServletRequest request){
        String token = CookieUtils.getCookieValue(request,TOKEN_KEY);
        if (token!=null && !"null".equals(token) && !StringUtils.isEmpty(token)){
            return userService.selectUserByToken(token).getData();
        }
        return null;
    }

    /**
     * 判断是否是不用过滤掉的资源
     * @param uri
     * @return
     */
    private boolean isExcludedUri(String uri) {
        if (excludedUris == null || excludedUris.length <= 0) {
            return false;
        }
        for (String ex : excludedUris) {
            uri = uri.trim();
            ex = ex.trim();
            if (uri.toLowerCase().matches(ex.toLowerCase().replace("*",".*")))
                return true;
        }
        return false;
    }

}
