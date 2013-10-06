/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Dave
 */
public class InsertBean implements Serializable{

    private Connect ct;
    private Connection con;
    private String msg;
    private List<InsertEventListener> listeners = new ArrayList<InsertEventListener>();

    public void connect(){
        ct = new Connect();
        ConnectionObject co = ct.getConnection();
        con = co.getConnection();
        msg = co.getMessage();
    }

    public void close() {
        ct.closeConnection(con);
    }

    public void insert(String tabName, ArrayList arguments) {
        connect();
        if(msg.equals("OK")) {
            ShowTableBean stb = new ShowTableBean();
            int size = arguments.size() * 2;
            stb.connect();
            ArrayList fields = stb.getAttributies(tabName, "Field");
            //ArrayList types = stb.getAttributies(tabName, "Type");
            stb.close();

            String statement = "INSERT INTO " + tabName + "(";
            for(int i = 0; i < size; i++) {
                if(i < size/2-1) {
                    statement = statement.concat(fields.get(i) + ",");
                } else if((i + 1)*2 == size) {
                    statement = statement.concat(fields.get(i) + ") VALUES(");
                } else if(i + 1 == size) {
                    statement = statement.concat("?);");
                } else {
                    statement = statement.concat("?,");
                }
            }
            try {
                PreparedStatement ps = con.prepareStatement(statement);
                int position = 0;

                Iterator itr = arguments.iterator();
                while(itr.hasNext())
                {
                    position++;
                    String argument = itr.next().toString();
                    try {
                         ps.setInt(position, Integer.parseInt(argument));
                    } catch(Exception e) {
                         ps.setString(position, argument);
                    }
                }
                ps.executeUpdate();
                fireResult("Záznam byl vložen");
            } catch (SQLException e) {
                fireResult(e.getMessage());
            }
        } else {
            fireResult("No connection");
        }
        close();
    }

    public void update(String tabName, String value, String column, ArrayList<String> values,
            ArrayList<String> names){
        String statement = "UPDATE " + tabName + " SET " + column + "=? WHERE ";
        connect();
        if(msg.equals("OK")) {
            for(int i = 0; i < names.size(); i++) {
                if(i + 1 == values.size()) {
                    statement = statement.concat(names.get(i)+"=?");
                } else {
                    statement = statement.concat(names.get(i)+"=? && ");
                }
            }
            try{
                PreparedStatement ps = con.prepareCall(statement);
                try {
                        ps.setInt(1, Integer.parseInt(value));
                    } catch (Exception e) {
                        ps.setString(1, value);
                    }
                for(int i = 0; i < values.size(); i++) {
                    try {
                        ps.setInt(i + 2, Integer.parseInt(values.get(i)));
                    } catch (Exception e) {
                        ps.setString(i + 2, values.get(i));
                    }
                }
                ps.executeUpdate();
                fireResult("Záznam byl změněn");
            } catch (SQLException e) {
                fireResult(e.getMessage());
            }
        } else {
            fireResult("No connection");
        }
        close();
    }

    public void delete(String tabName, ArrayList<String> values, ArrayList<String> names) {
        String statement = "DELETE FROM " + tabName + " WHERE ";
        connect();
        if(msg.equals("OK")) {
            for(int i = 0; i < names.size(); i++) {
                if(i + 1 == values.size()) {
                    statement = statement.concat(names.get(i)+"=?");
                } else {
                    statement = statement.concat(names.get(i)+"=? && ");
                }
            }
            System.out.println(statement);
            try{
                PreparedStatement ps = con.prepareCall(statement);
                for(int i = 0; i < values.size(); i++) {
                    try {
                        ps.setInt(i + 1, Integer.parseInt(values.get(i)));
                    } catch (Exception e) {
                        ps.setString(i + 1, values.get(i));
                    }
                }
                ps.executeUpdate();
                //fireResult("Smazáno");
            } catch (SQLException e) {
                fireResult(e.getMessage());
            }
        } else {
            fireResult("No connection");
        }
        close();
    }

    public void addInsertEventListener(InsertEventListener l) {
            listeners.add(l);
        }

        public void removeInsertEventListener(InsertEventListener l) {
            listeners.remove(l);
        }

        private void fireResult(String result) {
            InsertEvent e = new InsertEvent(this);
            for (InsertEventListener l : listeners) {
                l.insertError(e, result);
            }
        }

}
