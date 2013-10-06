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
public class InsertEvent extends EventObject{
    public InsertEvent(InsertBean source) {
        super(source);
    }
}
