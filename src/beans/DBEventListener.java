/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.util.EventListener;

/**
 *
 * @author Dave
 */
public interface DBEventListener extends EventListener{
    public void noConnection(DBEvent e, String msg);
}
