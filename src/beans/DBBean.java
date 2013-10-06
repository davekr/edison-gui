package beans;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Dave
 */
public class DBBean implements Serializable{

        List<DBEventListener> listeners = new ArrayList<DBEventListener>();
        String log;
        static com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds;

        public void initializeDS(String name, String server,
                String user, String password){
            
            //DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/myDB");
	    //Connection con = ds.getConnection();

            log = "cn=kru228-" + server + "-" + user + ",ou=ut1045,o=jat";

            try {
                 ds = lookup();
            } catch (NamingException e) {
                 ds = new MysqlDataSource();
                 ds.setPort(3306);
                 ds.setDatabaseName(name);
                 ds.setServerName(server);
                 ds.setUser(user);
                 ds.setPassword(password);
                 ds.setCharacterEncoding("UTF-8");
                 bind(ds);
            }
            
        }

	public Connection getConnection() {
             try {
                return ds.getConnection();
             } catch (SQLException e) {
                 fireNoConnection(e);
                 return null;
             }
	}

        public MysqlDataSource lookup() throws NamingException{
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
		    "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL,"ldap://pca1035a.vsb.cz:10389/");
                env.put(Context.SECURITY_CREDENTIALS, "david");
                env.put(Context.SECURITY_PRINCIPAL,"uid=admin, ou=system");
                env.put(Context.SECURITY_AUTHENTICATION, "simple");
		Context ctx = new InitialContext(env);
                return (MysqlDataSource) ctx.lookup(log);
	}

        public void bind(MysqlDataSource ds) {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL,"ldap://pca1035a.vsb.cz:10389/");
            env.put(Context.SECURITY_CREDENTIALS, "david");
            env.put(Context.SECURITY_PRINCIPAL,"uid=admin, ou=system");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            try {
                Context ctx = new InitialContext(env);
                ctx.bind(log, ds);
            } catch (NamingException ex) {
                //Logger.getLogger(DBBean.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
        }

	public void setAutoCommitOff(Connection con) {
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			fireNoConnection(e);
		}
	}

	public void commint(Connection con) {
		try {
			con.commit();
		} catch (SQLException e) {
			fireNoConnection(e);
		}
	}

	public void closeConnection(Connection con) {
            try {
		con.close();
            } catch(Exception e) {
                fireNoConnection(e);
            }
	}

        public void addDBEventListener(DBEventListener l) {
            listeners.add(l);
        }

        public void removeDBEventListener(DBEventListener l) {
            listeners.remove(l);
        }

        private void fireNoConnection(Exception exc) {
            DBEvent e = new DBEvent(this);
            for (DBEventListener l : listeners) {
                l.noConnection(e, exc.getMessage());
            }
        }

}
