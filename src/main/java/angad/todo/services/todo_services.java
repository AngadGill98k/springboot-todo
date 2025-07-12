package angad.todo.services;


import angad.todo.repository.userrepo;
import org.springframework.stereotype.Service;
import angad.todo.dto.todo_details;
@Service
public class todo_services {

    private final userrepo repo;
    todo_services (userrepo repo){
        this.repo=repo;
    }


}
