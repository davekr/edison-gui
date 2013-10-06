/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;


import beans.InitBean;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 *
 * @author Dave
 */
public class InitPanel {

    public JPanel init(){
        init = new JPanel();

        init.setLayout(new GridBagLayout());
        init.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GridBagConstraints gc = new GridBagConstraints();

        String text  = "Zahájením školního roku se vytvoří všechny potřebné tabulky automaticky. " +
                "\nPro úplné zahajení je třeba ovšem ještě vytvořit předměty a jejich garanty. " +
                "To lze provést v nabídce 'Vložit záznamy do tabulky'.";

        JTextArea area = new JTextArea(text) ;
        area.setEditable(false);
        //area.setEnabled(false);
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setOpaque(false);
        area.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        JButton start = new JButton("Zahájit školní rok");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        gc.weightx = 1.0;
        gc.weighty = 0.0;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;

        init.add(area, gc);

        gc.gridy = 1;
        gc.weighty = 1.0;
        gc.fill = 0;
        gc.anchor = GridBagConstraints.NORTHWEST;

        init.add(start, gc);

        return init;
    }

    private void startButtonActionPerformed(ActionEvent evt) {
        String result = new InitBean().doInit();
        String title = result;
        int type = 1;
        if(result.equals("OK")) {
            result = "Školní rok byl zahájen";
            ConnectionPanel.refresh();
        } else if(result.equals("INFO")) {
            result = "Školní rok byl již zahájen, nelze provést znovu.";
        } else {
            title = "No connection";
            type = 0;
        }

        JOptionPane.showMessageDialog(init, result, title, type);
    }

    JPanel init;

}
