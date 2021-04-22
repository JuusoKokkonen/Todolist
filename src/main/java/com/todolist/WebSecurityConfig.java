package com.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.todolist.domain.UserDetailServiceImpl;



@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .authorizeRequests()
        	.antMatchers("/css/**", "/login", "/h2-console/**", "/register", "/registersave").permitAll()
        	.and()
        	.authorizeRequests().antMatchers("/api/**").hasRole("ADMIN")
        	.anyRequest().authenticated()
        	.and()
        	.csrf().ignoringAntMatchers("/h2-console/**")
        	.and()
        	.headers().frameOptions().sameOrigin()
        	.and()
      .formLogin()
          .loginPage("/login")
          .defaultSuccessUrl("/tasklist", true)
          .permitAll()
          .and()
      .logout()
          .permitAll();
    }

	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}