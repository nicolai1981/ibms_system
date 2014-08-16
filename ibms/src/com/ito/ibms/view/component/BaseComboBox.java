package com.ito.ibms.view.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class BaseComboBox extends JComboBox {
    private static final long serialVersionUID = -1743947058190119013L;

    public static enum TYPE {
        EDIT,
        VIEW,
    }

    private final TYPE mType;
    public final int mWidth;
    public final int mHeight;
    public final String mFieldName;
    public final int mTag;
    public final ComboListener mListener;
    public long mCheckType = 0;
    public Object[] mList;

    public BaseComboBox(Object[] itemList, int width, TYPE type, String fieldName, int tag, ComboListener listener) {
        super(itemList);
        mList = itemList;
        mWidth = width;
        mType = type;
        mFieldName = fieldName;
        mTag = tag;
        mListener = listener;
        if (mListener != null) {
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    mListener.onItemSelected(mTag);
                }
            });
        }

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

    public static final int CHECK_FILLED = 0x00000001;

    public String check() {
        if (mCheckType == 0) {
            return null;
        }

        Object item = this.getSelectedItem();

        if ((mCheckType & CHECK_FILLED) > 0) {
            if (item == null) {
                return "Nenhum item do campo " + mFieldName + " foi selecionado.";
            }
        }

        return null;
    }

    public void updateList(Object[] list) {
        removeAllItems();
        for (Object item : list) {
            addItem(item);
        }
    }
}
