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
public interface InsertEventListener extends EventListener{
    public void insertError(InsertEvent e, String msg);

}
