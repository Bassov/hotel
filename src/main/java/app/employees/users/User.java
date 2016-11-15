package app.employees.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class User {

    int emp_id;
    String login;
    String password;
    
}
