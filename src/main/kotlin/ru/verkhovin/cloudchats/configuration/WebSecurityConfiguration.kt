package ru.verkhovin.cloudchats.configuration

import org.mindrot.jbcrypt.BCrypt
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.verkhovin.cloudchats.security.JWTAuthenticationFilter
import ru.verkhovin.cloudchats.service.AccountSecurityService

@Configuration
class WebSecurityConfiguration(private val jwtAuthenticationFilter: JWTAuthenticationFilter): WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    //todo 401 when no token provided
    http.csrf().disable()
//        .httpBasic().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST,"/account/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
  }
}
