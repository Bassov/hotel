package app.util;

import app.employees.Employee;
import app.employees.EmployeeDao;
import spark.Request;

public class RequestUtil {

    public static boolean removeSessionAttrLoggedOut(Request request) {
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        return loggedOut != null;
    }

    public static String removeSessionAttrLoginRedirect(Request request) {
        String loginRedirect = request.session().attribute("loginRedirect");
        request.session().removeAttribute("loginRedirect");
        return loginRedirect;
    }

    public static Employee getSessionCurrentUser(Request request) {
        return EmployeeDao.findByLogin(request.session().attribute("currentUser"));
    }

    public static String getAlert(Request request) {
        return request.session().attribute("alert");
    }

    public static String queryValue(Request request, String atr) {
        return request.queryMap(atr).value();
    }

}
