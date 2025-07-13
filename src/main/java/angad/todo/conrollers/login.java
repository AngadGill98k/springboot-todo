package angad.todo.conrollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import angad.todo.dto.login_details;
import angad.todo.jwt.jwtutil;
import angad.todo.models.User;
import angad.todo.services.user_services;
import jakarta.servlet.http.HttpServletResponse;
@RestController
@RequestMapping("/")
public class login{
    private final user_services u_service;
    private final jwtutil jwtutil;
    login(user_services s,jwtutil jwt){
        this.jwtutil=jwt;
        this.u_service=s;
    }


    @PostMapping("/signup")
    public Map<String,String> signup(@RequestBody login_details login){
        Map<String,String>res=new HashMap<>();
        User user=u_service.signup(login);
        if(user !=null){
            res.put("msg","user saved");
            return res;
        }
        res.put("msg","user not saved");
        return res;
    }
    @PostMapping("/signin")
    public Map<String,String> signin(@RequestBody login_details user, HttpServletResponse response ){
        Map<String, String> response2 = new HashMap<>();
        User status=u_service.signin(user);
        if (status != null) {
            String jwttoken= jwtutil.generateToken((status.getMail()));
            ResponseCookie cookie = ResponseCookie.from("token", jwttoken)
                    .httpOnly(true)
                    .secure(false) // only works on HTTPS; use false for localhost testing
                    .path("/")
                    .sameSite("Strict")
                    .maxAge(60 * 60)
                    .build();
            response.addHeader("Set-Cookie", cookie.toString());
            response2.put("msg", "Logged in successfully");

        } else {
            response2.put("msg", "Signin failed");
        }

        return response2;
    }
}
