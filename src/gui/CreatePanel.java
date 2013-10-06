/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import javax.swing.*;

/**
 *
 * @author Dave
 */
public class CreatePanel {

    public JPanel create(){
        createPanel = new JPanel();
        JPanel create = new JPanel();
        create.add(new JLabel("Název"));
        create.add(new JTextField(TEXT_FIELD_LENGTH));
        create.add(new JLabel("Typ"));
        create.add(type);
        create.add(new JLabel("Délka"));
        create.add(new JTextField(TEXT_FIELD_LENGTH));
        create.add(new JLabel("Primární klíč"));
        JRadioButton y = new JRadioButton();
        JRadioButton n = new JRadioButton();
        y.setActionCommand("Yes");
        n.setActionCommand("No");
        ButtonGroup group = new ButtonGroup();
        group.add(y);
        group.add(n);
        create.add(y);
        create.add(n);

        createPanel.add(create);
        return createPanel;
    }
    JPanel createPanel;

    private final static int TEXT_FIELD_LENGTH = 15;
    String[] types = {"Integer", "Varchar", "Date", "Time"};
    private JComboBox type = new JComboBox(types);
}
