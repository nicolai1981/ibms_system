package com.ito.ibms.view.component;

import javax.swing.JCheckBox;

public class BaseCheckBox extends JCheckBox {
    private static final long serialVersionUID = -1743947058190119013L;

    public final int mWidth;
    public final int mHeight;

    public BaseCheckBox(String text, int width) {
        super(text);
        mWidth = width;
        mHeight = 20;
        setFont(BaseConstant.FONT_BOLD_12);
    }
}
