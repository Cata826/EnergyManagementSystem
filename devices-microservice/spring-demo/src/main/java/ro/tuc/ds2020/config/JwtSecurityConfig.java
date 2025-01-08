package ro.tuc.ds2020.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ro.tuc.ds2020.jwt.*;
@Configuration
@EnableWebSecurity
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtRequestFilter jwtRequestFilter;

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtSecurityConfig(JwtRequestFilter jwtRequestFilter, JwtUtil jwtUtil) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/auth/login").permitAll() // Allow login endpoint
//                .antMatchers(HttpMethod.POST, "/person").permitAll()
//                .antMatchers("/person/**").hasAuthority("ADMIN") // Require ADMIN authority for all /person endpoints
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/person-reference/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/device/person/**").permitAll()
                .antMatchers("/device/**/mhec").permitAll()
                .antMatchers("/device/**/personId").permitAll()
                .antMatchers("/device/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/auth/login").permitAll() // Allow login endpoint
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // Explicitly allow OPTIONS requests
//                .antMatchers(HttpMethod.OPTIONS, "/person-reference/**").permitAll()
//                .antMatchers("/device/**").hasRole("ADMIN")
//                .antMatchers("/person-reference/**").hasRole("ADMIN") // Only ADMIN has access to /device/**
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:3000"); // Originea frontend-ului
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
