package angad.todo.repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import angad.todo.models.User;

public interface userrepo extends MongoRepository<User, String> {

    User findByMail(String mail);
    User findByPass(String name);
    User findByName(String pass);
}


