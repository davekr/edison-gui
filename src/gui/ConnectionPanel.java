package gui;

import beans.ShowTableBean;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Dave
 */
public class ConnectionPanel {

    String warning = "\n\nObnovení připojení a všech komponent GUI, které ho používají " +
                "je velmi náročná operace, proto se doporučuje důkladně se ujistit " +
                "o výše zmíněném. Také se nedoporučuje používat pokud je spojení už " +
                "funkční.";

    public JPanel info(){
        info = new JPanel();
        info.setLayout(new GridBagLayout());
        info.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GridBagConstraints gc = new GridBagConstraints();

        String text;

        ShowTableBean stb = new ShowTableBean();
        stb.connect();
        if(stb.getMessage().equals("OK")) {
            text = "Spojení k databázi je funkční. \n" +
                    "Lze používat všechny možnosti administračního rozhraní aplikace.";
        } else {
            text = "Momentálně neexistuje žádné spojení k databázi. \n" +
                "Zkontrolujte přihlašovací ůdaje v souboru DBLoginBean " +
                "a spusťte databázi. Potom stikněte obnovit." +
                warning;
        }
        stb.close();

        area = new JTextArea(text);
        area.setEditable(false);
        //area.setEnabled(false);
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setOpaque(false);
        area.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        JButton refresh = new JButton("Obnovit");
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                refreshButtonActionPerformed();
            }
        });

        gc.weightx = 1.0;
        gc.weighty = 0.0;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;

        info.add(area, gc);

        gc.gridy = 1;
        gc.weighty = 1.0;
        gc.fill = 0;
        gc.anchor = GridBagConstraints.NORTHWEST;
        info.add(refresh, gc);
        
        return info;
    }

    public void refreshButtonActionPerformed(){
        String text;

        ShowTableBean stb = new ShowTableBean();
        stb.connect();
        if(stb.getMessage().equals("OK")) {
            text = "Spojení k databázi je funkční. \n" +
                    "Lze používat všechny možnosti administračního rozhraní aplikace.";
            JOptionPane.showMessageDialog(info, "Connection ok", "Info", 1);
            refresh();
        } else {
            text = "Momentálně neexistuje žádné spojení k databázi. \n" +
                "Zkontrolujte přihlašovací ůdaje v souboru DBLoginBean " +
                "a spusťte databázi. Potom stikněte obnovit." +
                warning;
            JOptionPane.showMessageDialog(info, "No connection", "Info", 1);
        }
        stb.close();

        area.setText(text);
        info.updateUI();
    }

    JPanel info;
    JTextArea area;

    static void refresh(){
        GridBagConstraints gc = new GridBagConstraints();
        gc.weighty = 1;
        gc.weightx = 1;
        gc.anchor = GridBagConstraints.NORTHWEST;
        InsertPanel.insertPanel.removeAll();
        InsertPanel.insertPanel.add(new InsertPanel().insert(), gc);
        InsertPanel.insertPanel.updateUI();
        EditPanel.editPanel.removeAll();
        EditPanel.editPanel.add(new EditPanel().edit(), gc);
        EditPanel.editPanel.updateUI();
    }

}
