/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.util.EventObject;

/**
 *
 * @author Dave
 */
public class DBEvent extends EventObject{
    public DBEvent(DBBean source) {
        super(source);
    }

}
