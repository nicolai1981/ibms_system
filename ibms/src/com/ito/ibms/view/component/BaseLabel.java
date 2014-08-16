package com.ito.ibms.view.component;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class BaseLabel extends JLabel {
    private static final long serialVersionUID = -1743947058190119013L;
    protected static final SimpleDateFormat sDateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public static enum TYPE {
        TITLE,
        EDIT,
        VIEW,
    }

    private final TYPE mType;
    public final int mWidth;
    public final int mHeight;

    public BaseLabel(String text, int width, TYPE type) {
        super(text);
        mWidth = width;
        mType = type;
        switch (mType) {
        case TITLE:
            setFont(BaseConstant.FONT_TITLE);
            setHorizontalAlignment(SwingConstants.CENTER);
            mHeight = 25;
            break;
        case EDIT:
            setFont(BaseConstant.FONT_BOLD_12);
            mHeight = 20;
            break;
        case VIEW:
            setFont(BaseConstant.FONT_BOLD_14);
            mHeight = 20;
            break;
        default:
            setFont(BaseConstant.FONT_12);
            mHeight = 0;
            break;
        }
    }

    public void setDate(Date date) {
        setText(sDateFormatter.format(date));
    }
}
