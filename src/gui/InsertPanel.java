/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import beans.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Dave
 */
public class InsertPanel {

     public JPanel insert() {
        insertPanel = new JPanel();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        GridBagConstraints gc = new GridBagConstraints();

        ShowTableBean stb = new ShowTableBean();
        stb.connect();
        ArrayList tabs = stb.getTableNames();
        JComboBox tabList = new JComboBox(tabs.toArray());
        if(tabList.getSelectedItem() != null) {
            selected = tabList.getSelectedItem().toString();
        } else {
            selected = "";
        }
        String tableName = "";
        if(!tabs.isEmpty()) {
            tableName = tabs.get(0).toString();
        }
        ArrayList attr = stb.getAttributies(tableName, "Field");
        stb.close();

        gc.weightx = 1.0;
        gc.weighty = 0.0;

        gc.gridx = 0;
        gc.gridy = 0;
        tabList.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        panel.add(tabList, gc);
        tabList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tableListActionPerformed(evt);
            }
        });

        JButton insert = new JButton("Vložit záznam");
        insert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                insertAction();
            }
        });

        gc.gridy = 1;
        insertForm = new JPanel();
        getInsertForm(attr);
        insertForm.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(insertForm, gc);

        gc.gridy = 2;
        panel.add(insert, gc);

        insertPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;
        gbc.weighty = 1;
        insertPanel.add(panel, gbc);

        return insertPanel;
    }

     public void getInsertForm(ArrayList table) {
        GridBagConstraints gc = new GridBagConstraints();
        insertForm.removeAll();
        insertForm.setLayout(new GridBagLayout());

        gc.gridx = 0;
        gc.gridy = 0;

        Iterator it = table.iterator();
        while(it.hasNext()) {
            gc.anchor = GridBagConstraints.WEST;
            insertForm.add(new JLabel(it.next().toString()), gc);
            gc.gridx++;
            gc.anchor = GridBagConstraints.EAST;
            insertForm.add(new JTextField(TEXT_FIELD_LENGTH), gc);
            gc.gridy++;
            gc.gridx--;
        }
        insertForm.updateUI();
    }

     private void tableListActionPerformed(ActionEvent evt){
        JComboBox cb = (JComboBox)evt.getSource();
        Object newItem = cb.getSelectedItem();
        selected = newItem.toString();

        ShowTableBean stb = new ShowTableBean();
        stb.connect();
        ArrayList attr = stb.getAttributies(selected, "Field");
        stb.close();
        getInsertForm(attr);
    }

    private void insertAction() {
        ArrayList<String> arguments = new ArrayList<String>();
        for(int i = 0; i < insertForm.getComponentCount(); i++) {
            if(i % 2 != 0) {
                JTextField text = (JTextField) insertForm.getComponent(i);
                arguments.add(text.getText());
            }
        }
        InsertBean ib = new InsertBean();
        ib.addInsertEventListener(new InsertEventListener() {
            public void insertError(InsertEvent evt, String msg) {
                message(msg);
            }
        });
        ib.insert(selected, arguments);
    }

    private void message(String msg) {
        JOptionPane.showMessageDialog(insertForm, msg, "Info", 1);
    }

    JPanel insertForm;
    String selected;
    static JPanel insertPanel;
    private final static int TEXT_FIELD_LENGTH = 15;

}
