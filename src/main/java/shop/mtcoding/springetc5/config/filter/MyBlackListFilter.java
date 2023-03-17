package shop.mtcoding.springetc5.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyBlackListFilter implements Filter{// javaX의 servlet 필터를 써야 함

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // ※ 주의 : 버퍼를 비우면, 컨트롤러에서 버퍼를 읽지 못함
        
        // x-www-form-urlencoded
        String value = request.getParameter("value");

        if (value == null) {
            response.setContentType("text/plain; charset=utf-8");
            response.getWriter().println("value 파라메터를 전송해주세요");
            return;
        }

        if (value.equals("babo")) {
            response.setContentType("text/plain; charset=utf-8");
            response.getWriter().println("당신은 블랙리스트에 등록 되었습니다.");
            return;
        }
        chain.doFilter(request, response); // 정상적이면 체인을 다시 타면 됨
    } 
    
}
