package beans;

import java.sql.*;

/**
 *
 * @author Dave
 */
public class Connect {

    private ConnectionObject co;
    private DBBean db;

    public void initialize(){
        db = new DBBean();
        DBLoginBean log = new DBLoginBean();
        db.initializeDS(log.getDbName(), log.getDbServer(),
                    log.getUser(), log.getPassword());
    }

    public ConnectionObject getConnection() {
        db = new DBBean();
        co = new ConnectionObject();
        db.addDBEventListener(new DBEventListener() {
            public void noConnection(DBEvent evt, String msg) {
                returnMessage(msg);
            }
        });
        co.setMessage("OK");
        co.setConnection(db.getConnection());
        return co;
    }

    public void closeConnection(Connection con) {
            db.closeConnection(con);
    }

    public void returnMessage(String msg) {
         co.setMessage(msg);
    }

}
