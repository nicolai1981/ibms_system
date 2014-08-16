package com.ito.ibms.view.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BaseButton extends JButton {
    private static final long serialVersionUID = -1743947058190119013L;

    public enum POS_TYPE {
        LEFT,
        RIGHT
    }
    public static final int WIDTH = 120;
    public static final int HEIGHT = 35;
    public final int mTag;
    public final POS_TYPE mPos;
    private final ButtonListener mlistener;

    public BaseButton(String text, String icon, int tag, POS_TYPE pos, ButtonListener listener) {
        super(text);
        mTag = tag;
        mPos = pos;
        mlistener = listener;
        setFont(BaseConstant.FONT_BOLD_12);
        setIcon(new ImageIcon(BaseButton.class.getResource("/com/ito/ibms/resources/" + icon + ".png")));
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mlistener.onClick(mTag);
            }
        });
    }

    public void changeIcon(String text, String icon) {
        setText(text);
        setIcon(new ImageIcon(BaseButton.class.getResource("/com/ito/ibms/resources/" + icon + ".png")));
    }
}
