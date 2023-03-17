package shop.mtcoding.springetc5.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.springetc5.config.filter.MyBlackListFilter;

@Configuration
public class FilterRegisterConfig {

    @Bean // component 스캔이 될때 Bean을 띄워줌 ※ 내가 만든 클래스가 아닌 것들은 Bean으로 등록하는게 좋음
    public FilterRegistrationBean<?> blackListFilter(){
        FilterRegistrationBean<MyBlackListFilter> registration = new FilterRegistrationBean();
        registration.setFilter(new MyBlackListFilter());
        registration.addUrlPatterns("/filter");
        registration.setOrder(1); // 여러개의 필터 중 순서 정하기
        return registration;
    }
}
