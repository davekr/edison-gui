package beans;

import java.io.Serializable;
/**
 *
 * @author Dave
 */
public class DBLoginBean implements Serializable{

    String dbName = "edison";
    String dbServer = "localhost";
    String user = "root";
    String password = "root";

    public String getDbName() {
        return this.dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbServer() {
        return this.dbServer;
    }

    public void setDbServer(String dbServer) {
        this.dbServer = dbServer;
    }
    
    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
