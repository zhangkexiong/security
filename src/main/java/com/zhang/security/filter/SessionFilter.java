package com.zhang.security.filter;

import com.zhang.security.auth.MyAuthentication;
import com.zhang.security.bean.User;
import com.zhang.security.utils.Constants;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * filter:表示对所有请求的预处理
 * 一般可以在这里做缓存
 */
public class SessionFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        try {
            final HttpServletRequest req = (HttpServletRequest) servletRequest;
            final MyAuthentication myAuthentication = new MyAuthentication();
            HttpSession httpSession = req.getSession();
            User user = (User) httpSession.getAttribute(Constants.USER);
            //将session中的认证信息注入到Authentication中去
            System.out.println(httpSession.getId());
            myAuthentication.inject(user);
            httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext()
            );
        }finally {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
