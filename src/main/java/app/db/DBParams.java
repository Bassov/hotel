package app.db;

import java.util.ArrayList;
import java.util.Collections;

public class DBParams {

    private final ArrayList<String> container = new ArrayList<>();

    public DBParams(String... args) {
        Collections.addAll(container, args);
    }

    public int size() {
        return container.size();
    }

    public String get(int index) {
        return container.get(index);
    }

}
