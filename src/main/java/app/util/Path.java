package app.util;


import lombok.Getter;

public class Path {

    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
        @Getter public static final String HOTELS_INDEX = "/hotels";
        @Getter public static final String HOTELS_NEW = "/hotels/new";
        @Getter public static final String HOTELS_CREATE = "/hotels/new";
    }

    public static class Template {
        public static final String HOTEL_NEW = "templates/hotels/new.vm";
        public static final String HOTEL_INDEX = "templates/hotels/index.vm";
        public static final String NOT_FOUND = "templates/notFound.vm";
    }

}
