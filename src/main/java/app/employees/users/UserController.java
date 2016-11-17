package app.employees.users;

public class UserController {

    public static boolean authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        User user = UserDao.findByLogin(username);
        return user != null && password.equals(user.getPassword());
    }

}
