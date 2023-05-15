package com.iiitb.spe.JwtUtil;

import com.iiitb.spe.AuthenticationService;
import com.iiitb.spe.models.User_Login;
import com.iiitb.spe.repositories.UserLoginRepository;
import com.iiitb.spe.service.MovieDetailsService;
import com.iiitb.spe.JwtUtil.models.JwtResponseModel;
import com.iiitb.spe.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@Configuration
@ComponentScan("com.iiitb.spe")

public class JwtController {

    @Autowired
    private  final MovieDetailsService movieDetailsService;

    @Autowired
    private final UserLoginService userLoginService;
    @Autowired
    private final AuthenticationService authenticationService;
    public JwtController(AuthenticationService authenticationService, MovieDetailsService movieDetailsService, UserLoginService userLoginService)
    {
        this.authenticationService = authenticationService;
        this.movieDetailsService=movieDetailsService;
        this.userLoginService = userLoginService;
    }
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenManager tokenManager;


    @CrossOrigin
    @GetMapping("user/verifyOTP")
    public ResponseEntity createToken(@RequestParam String phone_number,@RequestParam String otp) {

        String auth = this.authenticationService.verify_otp(otp, phone_number);
        System.out.println("reached: "+ auth);

        if (Objects.equals(auth, "approved")) {

            System.out.println("entered");
            final UserDetails userDetails = userDetailsService.loadUserByUsername(phone_number);
            final String jwtToken = tokenManager.generateJwtToken(userDetails);
        System.out.println("hellso");
        //System.out.println(jwtToken);
        //System.out.println());
        return ResponseEntity.ok(new JwtResponseModel(jwtToken));

        }
        else
        {
            return ResponseEntity.ok(new JwtResponseModel("not"));
        }
        //return auth;
    }

    @CrossOrigin
    @GetMapping("user/getID")
    @Transactional
    public int get_userID(@RequestParam String phone_number) {

            User_Login user = userLoginService.getIDbyPhone(phone_number);
            if (user != null)
            {
                return user.getId();
            }
            else
            {
                userLoginService.addUser(phone_number);
                return userLoginService.getIDbyPhone(phone_number).getId();
            }
    }
}
