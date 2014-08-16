package com.ito.ibms.view.component;

import javax.swing.JTextField;

public class BaseEdit extends JTextField {
    private static final long serialVersionUID = -1743947058190119013L;

    public static enum TYPE {
        EDIT,
        VIEW,
    }

    private final TYPE mType;
    public final int mWidth;
    public final int mHeight;
    public final String mFieldName;
    public long mCheckType = 0;

    public BaseEdit(String text, int width, TYPE type, String fieldName) {
        super(text);
        mWidth = width;
        mType = type;
        mFieldName = fieldName;

        switch (mType) {
        case EDIT:
            setFont(BaseConstant.FONT_14);
            mHeight = 20;
            break;
        case VIEW:
            setFont(BaseConstant.FONT_BOLD_14);
            mHeight = 20;
            setEnabled(false);
            break;
        default:
            setFont(BaseConstant.FONT_14);
            mHeight = 0;
            break;
        }
    }

    @Override
    public String getText() {
        String text = super.getText();
        if (text != null) {
            text = text.trim().toUpperCase();
            if (text.length() == 0) {
                text = null;
            }
        }
        return text;
    }

    public static final int CHECK_FILLED = 0x00000001;

    public String check() {
        if (mCheckType == 0) {
            return null;
        }

        String text = getText();

        if ((mCheckType & CHECK_FILLED) > 0) {
            if (text == null) {
                return "O campo " + mFieldName + " não pode estar vazio.";
            }
        }
        
        return null;
    }
}
