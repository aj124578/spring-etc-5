package shop.mtcoding.springetc5.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;


public class LoginInterceptor implements HandlerInterceptor {

    @Override // override를 preHandle 하나만 했음
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
                System.out.println("로그인 인터셉터 발동함");
        return true;
    }
    
}
