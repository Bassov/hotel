//package app.guests;
//
//import app.util.Path;
//import app.util.ViewUtil;
//import spark.Request;
//import spark.Response;
//import spark.Route;
//
//import java.util.HashMap;
//import java.util.List;
//
//public class GuestController {
//
//    public static Route indexGuests = (Request request, Response response) -> {
//        List<Guest> guests = GuestsDao.selectAll();
//        HashMap<String,Object> model = new HashMap<>();
//        model.put("guests", guests);
//        return ViewUtil.render(request, model, Path.Template.GUESTS_INDEX);
//    };
//
//    public static Route newGuest = (Request request, Response response) -> {
//        return ViewUtil.render(request, new HashMap<>(), Path.Template.GUESTS_NEW);
//    };
//
//    public static Route createGuest = (Request request, Response response) -> {
//        String mail = request.queryMap("mail").value();
//        String name = request.queryMap("name").value();
//        String lastName = request.queryMap("lastName").value();
//        String phone = request.queryMap("phone").value();
//        GuestsDao.insert(mail, name, lastName, phone);
//        return ViewUtil.render(request, new HashMap<>(), Path.Template.GUESTS_NEW);
//    };
//
//}
