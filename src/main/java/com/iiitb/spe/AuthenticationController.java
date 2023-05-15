package com.iiitb.spe;

import com.iiitb.spe.JwtUtil.JwtAuthenticationEntryPoint;
import com.iiitb.spe.JwtUtil.JwtFilter;
import com.iiitb.spe.JwtUtil.JwtUserDetailsService;
import com.iiitb.spe.JwtUtil.TokenManager;
import com.iiitb.spe.service.MovieDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
@CrossOrigin(origins = "*")
@RestController


public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    private final MovieDetailsService movieDetailsService;
    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }
    public AuthenticationController(AuthenticationService authenticationService,MovieDetailsService movieDetailsService)
    {
        this.authenticationService = authenticationService;
        tokenManager = new TokenManager();
        this.movieDetailsService=movieDetailsService;
    }

    @CrossOrigin
    @GetMapping("user/sendOTP")
    public int send_otp(@RequestParam("phone_number") String mobile_number)
    {
        System.out.println("Hello?");
        String result=this.authenticationService.send_otp(mobile_number);
        if (result.equals("pending"))
            return 1;
        return 0;
    }

    /*record verify_otp_body(String mobile_number,String otp){}
    @CrossOrigin
    @PostMapping("/verify_otp")
    public String verify_otp(@RequestBody verify_otp_body verify_otp_rec)
    {
        return authenticationService.verify_otp(verify_otp_rec.otp,verify_otp_rec.mobile_number);
    }*/
    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private final TokenManager tokenManager;



    @Bean
    public JwtFilter authenticationJwtTokenFilter() {
        return new JwtFilter(userDetailsService, tokenManager);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/user/verifyOTP").permitAll()
                .antMatchers("/user/sendOTP").permitAll().and()
                .authorizeRequests().antMatchers("/addnewrelease").permitAll().and()
                .authorizeRequests().antMatchers("/changemoviedetails").permitAll().and()
                .authorizeRequests().anyRequest().authenticated();

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}