package com.interceptor;

import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by wjh on 2016/3/29.
 */
//注册拦截器
public class RegisterInterceptor implements HandlerInterceptor {
    @Autowired
    private User user;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        System.out.println("-------在action之前执行,如果返回true,则继续向后执行--------");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
//        System.out.println(request.getParameter("name"));
        String email2=user.getUser_email();
        String email3 = (String) request.getParameter("email");
        // 此处实现登陆的拦截判断
        if (email == null&&email2==null&&email3==null) {
            response.sendRedirect(request.getContextPath() + "/register/toregister");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("----在Action 方法执行完毕之后,执行(没有抛异常的话)----------");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("----在Action 方法执行完毕之后,无论是否抛出异常,通常用来进行异常处理----------");
    }
}
