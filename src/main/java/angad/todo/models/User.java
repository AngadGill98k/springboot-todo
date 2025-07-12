package angad.todo.models;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import angad.todo.models.todos;
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String mail;
    private String pass;
    private List<todos> todos= new ArrayList<>();

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPaas(String paas) {
        this.pass = paas;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    public List<todos> getTodos() {
        return todos;
    }
}
