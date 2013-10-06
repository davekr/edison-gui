/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.sql.Connection;

/**
 *
 * @author Dave
 */
public class ConnectionObject {

    private String msg;
    private Connection con;

    public String getMessage(){
        return this.msg;
    }

    public Connection getConnection() {
        return this.con;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public void setConnection(Connection con) {
        this.con = con;
    }

}
