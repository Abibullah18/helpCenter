package com.Help.Center.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.Help.Center.Filter.CustomAuthenticationFilter;
import com.Help.Center.Filter.CustomAuthorizationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.lang.model.element.AnnotationMirror;

@Configuration
@EnableWebSecurity 
@RequiredArgsConstructor	 
@Slf4j
public class SecurityConfig  extends  WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		log.info("Running Authentication ManagerBuilder:{}",userDetailsService.toString());

		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		

	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("running HttpSecurity");

		// TODO Auto-generated method stub
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/login/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/users/**","/api/company/**","/api/article/**","/role/**","/scope/**","/api/tags/**").hasAuthority("Read");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/users","/api/company","/scopes","/role","/api/tags","/article").hasAuthority("Write");
		http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/users/**","/api/UpadateCompany/**","/api/UpdateArticle").hasAuthority("Modify");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/users/**","api/deleteCompany/**","/api/article").hasAuthority("Delete");
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(new CustomAuthenticationFilter(authenticationManager()));
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/signup/**","/api/article/**","/api/addRoleToUsers/**","/addScopeToRole/**","/api/AddTagsToArticles/**," +
				"/api/AddUserToArticle/**","/api/AddCompanyToArticle/**","/api/addCompanyToTags/**","/api/addUserToTags/**");
		
	}


	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	

}
//"/api/users/**"
//,"/role/**","/scope/**",