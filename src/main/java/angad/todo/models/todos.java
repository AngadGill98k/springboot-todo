package angad.todo.models;

import org.springframework.data.annotation.Id;

public class todos {
    @Id
    private String id;
    private String name;
    private boolean status;

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }
}
