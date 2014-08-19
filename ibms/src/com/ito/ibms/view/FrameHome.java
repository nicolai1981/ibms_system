package com.ito.ibms.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;

import com.ito.ibms.view.component.BaseConstant;
import com.ito.ibms.view.component.BasePanel;

public class FrameHome extends JFrame {
    private static final long serialVersionUID = 3184410437389210954L;

    private static FrameHome sInstance = null;

    private JLabel mInfo = null;
    private BasePanel mCurrentPanel = null;
    private JProgressBar mProgress = null;
    private JMenuBar mMenuBar = null;

    private FrameHome() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBounds(0, 0, 1166, 681);
        getContentPane().setLayout(null);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        bottomPanel.setBounds(0, 621, 1150, 24);
        getContentPane().add(bottomPanel);
        bottomPanel.setLayout(null);

        mInfo = new JLabel();
        mInfo.setFont(BaseConstant.FONT_10);
        mInfo.setBounds(5, 0, 940, 22);
        bottomPanel.add(mInfo);

        mProgress = new JProgressBar();
        mProgress.setBounds(950, 2, 200, 20);
        bottomPanel.add(mProgress);

        mountMenu();

        mCurrentPanel = new BasePanel();
        mCurrentPanel.setBackground(Color.BLACK);
        getContentPane().add(mCurrentPanel);
    }

    public static FrameHome getInstance() {
        if (sInstance == null) {
            sInstance = new FrameHome();
        }

        return sInstance;
    }

    public void setTitle(String title) {
        setTitle(title);
    }

    public void setInfo(String info, boolean enableProgress) {
        mInfo.setText(info);
        mProgress.setIndeterminate(enableProgress);
    }

    public void mountMenu() {
        JMenu menu = null;
        JMenu subMenu = null;

        mMenuBar = new JMenuBar();
        mMenuBar.setBounds(0, 0, 1150, 21);
        getContentPane().add(mMenuBar);

        // Menu principal
        menu = new JMenu("Principal");
        menu.setFont(BaseConstant.FONT_BOLD_12);
        mMenuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("Dados pessoais");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        menu.add(menuItem);

        menuItem = new JMenuItem("Sair");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        menu.add(menuItem);

        // Membro
        menu = new JMenu("Membro");
        menu.setFont(BaseConstant.FONT_BOLD_12);
        mMenuBar.add(menu);

        menuItem = new JMenuItem("Cadastrar");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        menu.add(menuItem);

        // Celula
        menu = new JMenu("Célula");
        menu.setFont(BaseConstant.FONT_BOLD_12);
        mMenuBar.add(menu);

        menuItem = new JMenuItem("Cadastrar");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        menu.add(menuItem);

        // Geracao
        menu = new JMenu("Geração");
        menu.setFont(BaseConstant.FONT_BOLD_12);
        mMenuBar.add(menu);

        menuItem = new JMenuItem("Cadastrar");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        menu.add(menuItem);

        // Encontro
        menu = new JMenu("Encontro");
        menu.setFont(BaseConstant.FONT_BOLD_12);
        mMenuBar.add(menu);

        menuItem = new JMenuItem("Cadastrar");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        menu.add(menuItem);

        // EPB
        menu = new JMenu("EPB");
        menu.setFont(BaseConstant.FONT_BOLD_12);
        mMenuBar.add(menu);

        menuItem = new JMenuItem("Inscrição");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        menu.add(menuItem);

        // Curso
        subMenu = new JMenu("Curso");
        subMenu.setFont(BaseConstant.FONT_BOLD_12);
        menu.add(subMenu);

        menuItem = new JMenuItem("Novo");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setPanel(new EPBCourseNew());
            }
        });
        subMenu.add(menuItem);

        menuItem = new JMenuItem("Editar");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setPanel(new EPBCourseEdit());
            }
        });
        subMenu.add(menuItem);

        menuItem = new JMenuItem("Gerenciar");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        subMenu.add(menuItem);

        menuItem = new JMenuItem("Consultar");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        subMenu.add(menuItem);

        menuItem = new JMenuItem("Relatório");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        subMenu.add(menuItem);

        // Tipo de curso
        subMenu = new JMenu("Tipo de Curso");
        subMenu.setFont(BaseConstant.FONT_BOLD_12);
        menu.add(subMenu);

        menuItem = new JMenuItem("Novo");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setPanel(new EPBTypeNew());
            }
        });
        subMenu.add(menuItem);

        menuItem = new JMenuItem("Editar");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setPanel(new EPBTypeEdit());
            }
        });
        subMenu.add(menuItem);

        menuItem = new JMenuItem("Consultar");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        subMenu.add(menuItem);

        menuItem = new JMenuItem("Relatório");
        menuItem.setFont(BaseConstant.FONT_BOLD_12);
        subMenu.add(menuItem);

    }

    private void setPanel(BasePanel panel) {
        FrameHome.this.getContentPane().remove(mCurrentPanel);
        mCurrentPanel = panel;
        FrameHome.this.getContentPane().add(mCurrentPanel);
        FrameHome.this.repaint();
    }
}
