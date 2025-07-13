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

    @DeleteMapping ("/delete/{id}")
    public Map<String,Object> delete ( @PathVariable String id,@CookieValue(value = "token", required = false) String token){
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
        boolean removed = user.getTodos().removeIf(todo -> todo.getId().equals(id));

        if (removed) {
            repo.save(user);
            res.put("msg", "Todo deleted");
        } else {
            res.put("msg", "Todo not found");
        }

        return res;
    }
    @PutMapping("/toggle/{id}")
    public Map<String, Object> toggleDone(@PathVariable String id,
                                          @CookieValue(value = "token", required = false) String token) {
        Map<String, Object> res = new HashMap<>();

        // 1. Check token validity
        if (token == null || !jwtutil.validateToken(token)) {
            res.put("msg", "Unauthorized: token missing or invalid");
            return res;
        }

        // 2. Get user by email from token
        String email = jwtutil.extractmail(token);
        User user = repo.findByMail(email);

        if (user == null) {
            res.put("msg", "User not found");
            return res;
        }

        // 3. Find todo and toggle status
        List<todos> todosList = user.getTodos();
        boolean updated = false;

        for (todos todo : todosList) {
            if (todo.getId().equals(id)) {
                todo.setStatus(!todo.isStatus()); // ✅ toggle
                updated = true;
                break;
            }
        }

        // 4. Save if toggled
        if (updated) {
            repo.save(user);
            res.put("msg", "Todo status toggled");
        } else {
            res.put("msg", "Todo not found");
        }

        return res;
    }
}
