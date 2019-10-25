//package com.linker.tower.config;
//
//import com.linker.tower.security.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * @author huang.ziqing
// * @date 2019/10/24
// */
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    //@Qualifier("UserDetailsService")
//    private UserDetailsService userDetailsService;
//
//
//    //@Autowired
//    //private TokenProvider tokenProvider;
//
//
//    @Bean("userAuthenticationManager")
//    public AuthenticationManager developerAuthenticationManager() throws Exception {
//        return authenticationManager(userDetailsService);
//    }
//
//    @Bean
//    public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
//        return new AjaxAuthenticationSuccessHandler();
//    }
//
//    @Bean
//    public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
//        return new AjaxAuthenticationFailureHandler();
//    }
//
//    @Bean
//    public AjaxAccessDeniedHandler ajaxAccessDeniedHandler() {
//        return new AjaxAccessDeniedHandler();
//    }
//
//    @Bean
//    public AjaxAuthenticationEntryPoint ajaxAuthenticationEntryPoint() {
//        return new AjaxAuthenticationEntryPoint();
//    }
//
//    @Bean
//    public AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler() {
//        return new AjaxLogoutSuccessHandler();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user").password("123456").roles();
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers(HttpMethod.OPTIONS, "/**")
//                .antMatchers("/app/**/*.{js,html}")
//                .antMatchers("/content/**")
//                .antMatchers("/swagger-ui/index.html");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement().maximumSessions(1).expiredSessionStrategy(new ResponseBodySessionInformationExpiredStrategy());
//        http.authorizeRequests().and().cors().and()
//                .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(ajaxAuthenticationEntryPoint())
//                .accessDeniedHandler(ajaxAccessDeniedHandler())
//                .and()
//                .authorizeRequests()
//                //.antMatchers("/api/public/**").permitAll()
//                .antMatchers("/api/**").authenticated()
//                .antMatchers("/v2/api-docs/**").permitAll()
//                .antMatchers("/swagger-resources/configuration/ui").permitAll()
//                .antMatchers("/swagger-ui.html").permitAll()
//                //.and().apply(securityConfigurerAdapter())
//
//        ;
//    }
//
//}
