package angad.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class hash{
@Bean
public BCryptPasswordEncoder hashing(){
        return new BCryptPasswordEncoder();
    }
}