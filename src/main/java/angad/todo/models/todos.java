package angad.todo.models;

import org.springframework.data.annotation.Id;
import java.util.UUID;
public class todos {
    @Id
    private String  id = UUID.randomUUID().toString();
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
