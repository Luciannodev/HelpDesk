package br.com.zezinho.helpdesk.config;

import br.com.zezinho.helpdesk.security.JWTAuthenticationFilter;
import br.com.zezinho.helpdesk.security.JWTAuthorizationFilter;
import br.com.zezinho.helpdesk.security.JWTUtil;
import br.com.zezinho.helpdesk.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    private final AuthenticationProvider authenticationProvider;

    private final JWTAuthenticationFilter jwtAuthenticationFilter;




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .regexMatchers("/")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, OncePerRequestFilter.class);
        return http.build();

    }
}


////    @Bean
////    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
////        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
////            http.headers().frameOptions().disable();
////        }
////
////        http.cors().and().csrf().disable();
////        http.addFilter(new JWTAuthenticationFilter(AuthenticationManager(), jwtUtil));
////        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
////        http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();
////
////        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////        return http.build();
////    }
////
//
//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        final List<GlobalAuthenticationConfigurerAdapter> configurers = new ArrayList<>();
//        configurers.add(new GlobalAuthenticationConfigurerAdapter() {
//                            @Override
//                            public void configure(AuthenticationManagerBuilder auth) throws Exception {
//                                auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//                            }
//                        }
//        );
//        return authConfig.getAuthenticationManager();
//    }

//
////    @Bean
////    CorsConfigurationSource corsConfigurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
////        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
////        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
