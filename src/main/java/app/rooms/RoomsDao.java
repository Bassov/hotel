package app.rooms;

import app.db.AbstractDao;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Function;

public class RoomsDao extends AbstractDao<Room>{
    @Override
    protected Function<ResultSet, List<Room>> mapToObject() {
        return null;
    }
}
