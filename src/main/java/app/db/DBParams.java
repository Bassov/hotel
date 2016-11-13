package app.db;

import java.util.ArrayList;

public class DBParams {

    private ArrayList<String> container = new ArrayList<>();

    public DBParams(String... args) {
        for (String s : args) {
            container.add(s);
        }
    }

    public DBParams() {
    }

    public void add(String parameter) {
        container.add(parameter);
    }

    public int size() {
        return container.size();
    }

    public String get(int index) {
        return container.get(index);
    }

}
