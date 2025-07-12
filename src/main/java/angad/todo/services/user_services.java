package angad.todo.services;



import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import angad.todo.repository.userrepo;
import angad.todo.models.User;
import angad.todo.dto.login_details;

@Service
public class user_services {
    private final userrepo repo;
    private final BCryptPasswordEncoder hash;
    public user_services(userrepo repo,BCryptPasswordEncoder hash){
        this.repo=repo;
        this.hash=hash;
    }
    public User signup(login_details login){
        User user=new User();
        user.setMail(login.getMail());
        user.setName(login.getName());
        String hashed=hash.encode(login.getPass());
        user.setPaas(hashed);
        repo.save(user);
        return user;
    }
    public User signin(login_details login) {
        User user = repo.findByMail(login.getMail());
        if (user != null) {
            if (user.getName().equals(login.getName())) {
                if (hash.matches(login.getPass(), user.getPass())) {
                    return user;
                }
            }
        }
        return null;
    }
    public User finduser(login_details user) {
        User existingUser = repo.findByMail(user.getMail());
        if (existingUser == null) {
            return null;
        }
        if (
                existingUser.getPass().equals(user.getPass()) &&
                        existingUser.getName().equals(user.getName())
        ) {
            return existingUser;
        }
        return null;
    }

}

