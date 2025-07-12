package angad.todo.conrollers;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import angad.todo.repository.userrepo;
import angad.todo.dto.todo_details;
import angad.todo.services.todo_services;
import angad.todo.models.User;
import angad.todo.models.todos;
import angad.todo.jwt.jwtutil;
import angad.todo.services.user_services;
@RestController
@RequestMapping("/main")
public class todo{
    private final userrepo repo;
    private final todo_services todo;
    private final user_services u_servicesl;
    private final jwtutil jwtutil;
    todo(userrepo repo,jwtutil jwtutil,todo_services todo,user_services s){
        this.repo=repo;
        this.todo=todo;
        this.u_servicesl=s;
        this.jwtutil=jwtutil;

    }


    @PostMapping("/add")
    public Map<String,String> add(@RequestBody todo_details todo , @CookieValue(value = "token", required = false) String token) {
        Map<String,String>res=new HashMap<>();
        if (token == null || !jwtutil.validateToken(token)) {
            res.put("msg", "Unauthorized: token missing or invalid");
            return res;
        }
        String email = jwtutil.extractmail(token);
        User user = repo.findByMail(email);
        if (user == null) {
            res.put("msg", "User not found");
            return res;
        }
        todos newtodo= new todos();
        newtodo.setName(todo.getName());
        newtodo.setStatus(todo.isStatus());
        user.getTodos().add(newtodo);
        repo.save(user);

        res.put("msg", "Todo added for user: ");
        return res;


    }
    @GetMapping("/gettodos")
    public Map<String,Object> gettodos ( @CookieValue(value = "token", required = false) String token){
        Map<String,Object> res=new HashMap<>();;
        if (token == null || !jwtutil.validateToken(token)) {
            res.put("msg", "Unauthorized: token missing or invalid");
            return res;
        }
        String email = jwtutil.extractmail(token);
        User user = repo.findByMail(email); // you'll need userrepo injected
        if (user == null) {
            res.put("msg", "User not found");
            return res;
        }
        List<todos>todo=user.getTodos();
        res.put("todos",todo);
        return res;
    }

}
