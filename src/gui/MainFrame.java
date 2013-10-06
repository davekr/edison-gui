package gui;

import beans.ShowTableBean;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Dave
 */
public class MainFrame extends JFrame{
    public MainFrame()
    {
        this.setTitle("Projekt kru228");
        this.setSize(900,500);

        Dimension dimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        this.setLocation((int)dimension.getWidth()/7,(int)dimension.getHeight()/7);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
        gc.weightx = 1.0;
        //gc.weighty = 0.0;
        //gc.anchor = GridBagConstraints.NORTH;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        this.add(getPanelNadpis(),gc);

        gc.gridy = 1;
        gc.weighty = 1.0;
        gc.fill = GridBagConstraints.BOTH;
        this.add(getPanelMenu(),gc);
        
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(1);
            }
        });

    }

    public JPanel getPanelNadpis()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(20,20));
        panel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));

        JLabel labelNadpis  = new JLabel("Můj zelený systém - Projekt JAT");
        Font fontNadpis = new Font("SansSerif", Font.BOLD, 24);
        labelNadpis.setFont(fontNadpis);
        labelNadpis.setForeground(Color.white);

        ImageIcon icon = new ImageIcon("images/edison-logo.gif");

        JLabel powered  = new JLabel("Powered by");
        powered.setForeground(Color.white);
        JLabel labelObrazek = new JLabel(icon);

        JPanel left = new JPanel();
        left.add(powered, BorderLayout.WEST);
        left.add(labelObrazek, BorderLayout.EAST);
        left.setBackground(new Color(46, 98, 40));
        
        panel.add(labelNadpis, BorderLayout.WEST);
        panel.setBackground(new Color(46, 98, 40));
        panel.add(left,BorderLayout.EAST);

        return panel;

    }

    public JTabbedPane getPanelMenu() {
        JTabbedPane tabbedPane = new JTabbedPane();
        JComponent panel1 = new InitPanel().init();
        JComponent panel2 = new CreatePanel().create();
        JComponent panel3 = new JPanel();
        JComponent panel4 = new InsertPanel().insert();
        JComponent panel5 = new EditPanel().edit();
        JComponent panel6 = new ConnectionPanel().info();
        
        tabbedPane.addTab("<html><body><table width='150'>Zahájit školní rok</table></body></html>", panel1);
        tabbedPane.addTab("<html><body><table width='150'>Vytvořit vlastní tabulky</table></body></html>", panel2);
        tabbedPane.addTab("<html><body><table width='150'>Editovat tabulky</table></body></html>", panel3);
        tabbedPane.addTab("<html><body><table width='150'>Vložit záznamy do tabulky</table></body></html>", panel4);
        tabbedPane.addTab("<html><body><table width='150'>Editovat záznamy</table></body></html>", panel5);
        tabbedPane.addTab("<html><body><table width='150'>Spojení k databázi</table></body></html>", panel6);
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);

        return tabbedPane;
    }

}
