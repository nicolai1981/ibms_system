package com.ito.ibms.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JSeparator;

public class temp extends JPanel {
    private JTextField textField;
    public temp() {
        setLayout(null);
        
        JButton btnNewButton = new JButton("New button");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnNewButton.setBounds(52, 51, 89, 23);
        add(btnNewButton);
        
        textField = new JTextField();
        textField.setBounds(52, 136, 86, 20);
        add(textField);
        textField.setColumns(10);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(46, 107, 215, 2);
        add(separator);
    }
}
