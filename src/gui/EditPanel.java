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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Dave
 */
public class EditPanel {

    public JPanel edit() {
        editPanel = new JPanel();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        GridBagConstraints gc = new GridBagConstraints();

        ShowTableBean stb = new ShowTableBean();
        stb.connect();
        ArrayList tabs = stb.getTableNames();
        JComboBox tabList = new JComboBox(tabs.toArray());
        stb.close();
        if(tabList.getSelectedItem() != null) {
            selected = tabList.getSelectedItem().toString();
        } else {
            selected = "";
        }

        gc.gridx = 0;
        gc.gridy = 0;
        tabList.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
        tabList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tableListActionPerformed(evt);
            }
        });
        panel.add(tabList, gc);

        gc.gridx = 1;
        JButton edit = new JButton("Vybrat tabulku");
        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editAction();
            }
        });

        panel.add(edit, gc);

        gc.gridx = 2;

        JButton delete = new JButton("Smazat položku");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deleteAction();
            }
        });
        JPanel button = new JPanel();
        button.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));
        button.add(delete);

        panel.add(button);

        editPanel.setLayout(new GridBagLayout());
        gc.weightx = 1.0;
        gc.weighty = 0.0;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.WEST;
        editPanel.add(panel, gc);

        table = new JPanel();
        JPanel tablePanel = new JPanel();
        tablePanel.add(table);
        gc.gridy = 1;
        gc.weighty = 1.0;
        //gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.NORTHWEST;
        editPanel.add(tablePanel, gc);

        return editPanel;
    }

    private void tableListActionPerformed(ActionEvent evt){
        JComboBox cb = (JComboBox)evt.getSource();
        Object newItem = cb.getSelectedItem();
        selected = newItem.toString();
    }

    private void editAction() {
        table.removeAll();
        ShowTableBean stb = new ShowTableBean();
        stb.connect();
        ArrayList<String> attributies = stb.getAttributies(selected, "Field");
        DBTableModel dbmodel = new DBTableModel(attributies, stb.getEntities(selected, attributies));
        stb.close();
        tab = new JTable(dbmodel.data,dbmodel.columnNames);
        JScrollPane scrollPane = new JScrollPane(tab);
        tab.setFillsViewportHeight(true);
        scrollPane.setViewportView(tab);
        Dimension d = tab.getPreferredSize();
        if(d.getHeight() > 310) {
            d.setSize(d.getWidth(), 310);
        }
        tab.setPreferredScrollableViewportSize(d);
        tab.setAutoCreateRowSorter(true);
        tab.getModel().addTableModelListener(new TableModelListener(){
            public void tableChanged(TableModelEvent e){
                tabChanged(e);
            }
        });
        table.add(scrollPane);
        table.updateUI();

    }

    private void deleteAction() {
        try{
            int from = tab.getSelectedRow();
            if(from == -1)
                message("Vyberte nějakou položku!!");
            else {
                int to = tab.getSelectedRowCount() + from;
                InsertBean ib = new InsertBean();
                ib.addInsertEventListener(new InsertEventListener() {
                    public void insertError(InsertEvent evt, String msg) {
                        message(msg);
                    }
                });
                ib.connect();
                for(int i = from; i < to; i++) {
                    ArrayList<String> values = new ArrayList<String>();
                    ArrayList<String> colNames = new ArrayList<String>();
                    for(int j = 0; j < tab.getColumnCount(); j++) {
                        colNames.add(tab.getColumnName(j));
                        try {
                            values.add(tab.getValueAt(i, j).toString());
                        } catch (Exception e) {
                            values.add("");
                        }
                    }
                    ib.delete(selected, values, colNames);
                }
                editAction();
                ib.close();
            }
        } catch (Exception e) {
            message("Vyberte nějakou tabulku!!");
        }
    }

    private void tabChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        ArrayList<String> values = new ArrayList<String>();
        ArrayList<String> colNames = new ArrayList<String>();
        String value = model.getValueAt(row, column).toString();
        for(int i=0; i < model.getColumnCount(); i++) {
            if(i != column) {
                try {
                    values.add(model.getValueAt(row, i).toString());
                } catch (Exception ex) {
                    values.add("");
                }
                colNames.add(model.getColumnName(i));
            }
        }
        InsertBean ib = new InsertBean();
        ib.addInsertEventListener(new InsertEventListener() {
            public void insertError(InsertEvent evt, String msg) {
                message(msg);
            }
        });
        ib.connect();
        ib.update(selected, value, columnName, values, colNames);
        ib.close();
        
    }

    private void message(String msg) {
        JOptionPane.showMessageDialog(table, msg, "Info", 1);
    }

    JPanel table;
    JTable tab;
    String selected;
    static JPanel editPanel;

}
