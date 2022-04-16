package com.janero.movies.config;

import static java.lang.String.format;
import javax.servlet.http.HttpServletResponse;
import com.janero.movies.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(UserRepository userRepository, JwtTokenFilter jwtTokenFilter) {
        super();
        this.userRepository = userRepository;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(format("User: %s, not found", username))));
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }).and();

        // Set permissions on endpoints
        http.authorizeRequests()
                // Public endpoints
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                .antMatchers(HttpMethod.GET, "/").permitAll().antMatchers(HttpMethod.GET, "/v3/**")
                .permitAll()
                // Private endpoints
                .anyRequest().authenticated();

        // Add JWT token filter
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
