package edison;

import gui.MainFrame;

/**
 *
 * @author Dave
 */
public class Main {

    public static void main(String[] args) {
        new beans.Connect().initialize();
         new MainFrame().setVisible(true);
    }

}
