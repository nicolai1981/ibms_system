package com.ito.ibms.view.component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTextField;

public class BaseEdit extends JTextField {
    private static final long serialVersionUID = -1743947058190119013L;
    protected static final SimpleDateFormat sDateFormatter = new SimpleDateFormat("dd/MM/yyyy");

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

    public Integer getInt() {
        String text = super.getText();
        if (text == null) {
            return null;
        }

        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            return null;
        }
    }

    public Double getDouble() {
        String text = super.getText();
        if (text == null) {
            return null;
        }

        try {
            String temp = text.replace(',', '.');
            return Double.parseDouble(temp);
        } catch (Exception e) {
            return null;
        }
    }

    public Date getDate() {
        String text = super.getText();
        if (text == null) {
            return null;
        }

        try {
            return sDateFormatter.parse(text);
        } catch (Exception e) {
            return null;
        }
    }

    public String getDateText() {
        Date date = getDate();
        if (date == null) {
            return null;
        }

        try {
            return sDateFormatter.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static final int CHECK_FILLED   = 0x00000001;

    public static final int CHECK_NUMBER   = 0x00000002;
    public static final int CHECK_NOT_ZERO = 0x00000004;
    public static final int CHECK_POSITIVE = 0x00000008;

    public static final int CHECK_DATE     = 0x00000010;
    public static final int CHECK_NOT_PAST = 0x00000020;

    public static final int CHECK_MONEY    = 0x00000040;

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

        if ((mCheckType & CHECK_NUMBER) > 0) {
            if (text != null) {
                try {
                    int number = Integer.parseInt(text);
                    if ((mCheckType & CHECK_NOT_ZERO) > 0) {
                        if (number == 0) {
                            return "O campo " + mFieldName + " não pode ser 0.";
                        }
                    }

                    if ((mCheckType & CHECK_POSITIVE) > 0) {
                        if (number < 0) {
                            return "O campo " + mFieldName + " deve ser maior que 0.";
                        }
                    }
                } catch (Exception e) {
                    return "O campo " + mFieldName + " deve ser um número.";
                }
            }
        }

        if ((mCheckType & CHECK_DATE) > 0) {
            if (text != null) {
                try {
                    Date date = sDateFormatter.parse(text);
                    if ((mCheckType & CHECK_NOT_PAST) > 0) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 1);
                        if (calendar.getTimeInMillis() > date.getTime()) {
                            return "O campo " + mFieldName + " deve ser maior que a data atual.";
                        }
                    }
                    setText(sDateFormatter.format(date));
                } catch (Exception e) {
                    return "O campo " + mFieldName + " deve seguir o formato DD/MM/AAAA.";
                }
            }
        }

        if ((mCheckType & CHECK_MONEY) > 0) {
            if (text != null) {
                try {
                    String temp = text.replace(',', '.');
                    double money = Double.parseDouble(temp);

                    if ((mCheckType & CHECK_NOT_ZERO) > 0) {
                        if (money == 0) {
                            return "O campo " + mFieldName + " não pode ser 0.";
                        }
                    }
                    setText(String.format("%.2f", money).replace('.', ','));
                } catch (Exception e) {
                    return "O campo " + mFieldName + " deve ser um número.";
                }
            }
        }

        return null;
    }
}
