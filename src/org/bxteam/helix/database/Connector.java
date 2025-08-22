package org.bxteam.helix.database;

import java.sql.SQLException;

public interface Connector {
    void open() throws SQLException;

    void close();

    boolean available();
}
