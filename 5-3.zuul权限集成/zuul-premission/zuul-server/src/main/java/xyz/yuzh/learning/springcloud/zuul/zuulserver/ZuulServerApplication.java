package xyz.yuzh.learning.springcloud.zuul.zuulserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ZuulServerApplication extends WebSecurityConfigurerAdapter  {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 不需要授权即可访问的接口
                .authorizeRequests()
                .antMatchers("/login", "/client/**")
                .permitAll()
                // 其他接口需要授权
                .anyRequest()
                .authenticated()
                // 关闭 csrf
                .and()
                .csrf()
                .disable();
    }
}
