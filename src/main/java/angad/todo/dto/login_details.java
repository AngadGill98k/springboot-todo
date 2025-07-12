package angad.todo.dto;

import angad.todo.models.todos;

import java.util.List;

public class login_details {
    private String name;
    private String mail;
    private String pass;
    private List<todos> todos;

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

    public void setPass(String paas) {
        this.pass = paas;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTodos(List<todos> todos) {
        this.todos = todos;
    }

    public List<todos> getTodos() {
        return todos;
    }
}
