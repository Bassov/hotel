package app.employees.users;

public class UserController {

    public static boolean authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        User user = UserDao.findByLogin(username);
        if (user == null) {
            return false;
        }
        return password.equals(user.getPassword());
    }

}
