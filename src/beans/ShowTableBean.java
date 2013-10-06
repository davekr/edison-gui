/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author Dave
 */
public class ShowTableBean implements Serializable{

    private Connect ct;
    private Connection con;
    private String msg = "";

    public void connect(){
        ct = new Connect();
        ConnectionObject co = ct.getConnection();
        con = co.getConnection();    
        msg = co.getMessage();
    }
    
    public void close() {
        ct.closeConnection(con);
    }

    public ArrayList<String> getTableNames() {
        ArrayList<String> tables = new ArrayList<String>();
        if(msg.equals("OK")) {
            try {
                PreparedStatement ps = con.prepareStatement("SHOW TABLES");
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    tables.add(rs.getString("Tables_in_edison"));
                }
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return tables;
    }

    public ArrayList<String> getAttributies(String tableName, String parameter) {
        ArrayList<String> attributies = new ArrayList<String>();
        if(msg.equals("OK")) {
            try {
                PreparedStatement ps = con.prepareStatement("SHOW FIELDS FROM " + tableName);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    attributies.add(rs.getString(parameter));
                }
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return attributies;
    }

    public ArrayList<ArrayList<String>> getEntities(String tableName, ArrayList<String> attributies) {
        ArrayList<ArrayList<String>> entities = new ArrayList<ArrayList<String>>();
        if(msg.equals("OK")) {
            try {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM " + tableName);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    Iterator it = attributies.iterator();
                    ArrayList<String> row = new ArrayList<String>();
                    while(it.hasNext()) {
                        row.add(rs.getString(it.next().toString()));
                    }
                    entities.add(row);
                }
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return entities;
    }

    public String getMessage(){
        return msg;
    }

}
