package com.ito.ibms.view.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class BasePanel extends JPanel {
    private static final long serialVersionUID = 4749133958618215608L;

    private static final int LINE_Y_MARGIN = 5;
    private static final int LINE_Y_START = 10;
    private static final int LINE_X_START = 10;
    private static final int COMPONENT_MARGIN = 5;
    private static final int BUTTON_MARGIN = 10;

    private final List<List<Object>> mComponentList;
    private final List<BaseButton> mButtonList;
    private List<Object> mCurrentLine;

    public BasePanel() {
        super();
        setBounds(0, 21, 1150, 600);
        setLayout(null);

        mComponentList = new ArrayList<List<Object>>();
        mButtonList = new ArrayList<BaseButton>();
        addLine();
    }

    protected void addLine() {
        mCurrentLine = new ArrayList<Object>();
        mComponentList.add(mCurrentLine);
    }

    protected void addComponent(Object item) {
        mCurrentLine.add(item);
    }

    protected void addButton(BaseButton item) {
        mButtonList.add(item);
    }

    protected void mountPanel() {
        int posX = LINE_X_START;
        int posY = LINE_Y_START;

        for (List<Object> line : mComponentList) {
            int lineHeight = 0;
            posX = LINE_X_START;

            if (line.size() == 0) {
                posY += LINE_Y_MARGIN;
            } else {
                for (Object component : line) {
                    if (component instanceof BaseLabel) {
                        BaseLabel label = (BaseLabel) component;
                        if (lineHeight < label.mHeight) {
                            lineHeight = label.mHeight;
                        }
                        label.setBounds(posX, posY, label.mWidth, label.mHeight);
                        add(label);
                        posX += label.mWidth;
                    } else if (component instanceof BaseEdit) {
                        BaseEdit edit = (BaseEdit) component;
                        if (lineHeight < edit.mHeight) {
                            lineHeight = edit.mHeight;
                        }
                        edit.setBounds(posX, posY, edit.mWidth, edit.mHeight);
                        add(edit);
                        posX += edit.mWidth;
                    } else if (component instanceof BaseComboBox) {
                        BaseComboBox combobox = (BaseComboBox) component;
                        if (lineHeight < combobox.mHeight) {
                            lineHeight = combobox.mHeight;
                        }
                        combobox.setBounds(posX, posY, combobox.mWidth, combobox.mHeight);
                        add(combobox);
                        posX += combobox.mWidth;
                    } else if (component instanceof BaseSeparator) {
                        BaseSeparator separator = (BaseSeparator) component;
                        if (lineHeight < separator.mHeight) {
                            lineHeight = separator.mHeight;
                        }
                        separator.setBounds(posX, posY, separator.mWidth, separator.mHeight);
                        add(separator);
                        posX += separator.mWidth;
                    } else if (component instanceof BaseButton) {
                        BaseButton button = (BaseButton) component;
                        if (lineHeight < button.mHeight) {
                            lineHeight = button.mHeight;
                        }
                        button.setBounds(posX, posY, button.mWidth, button.mHeight);
                        add(button);
                        posX += button.mWidth;
                    } else if (component instanceof BaseCheckBox) {
                        BaseCheckBox checkBox = (BaseCheckBox) component;
                        if (lineHeight < checkBox.mHeight) {
                            lineHeight = checkBox.mHeight;
                        }
                        checkBox.setBounds(posX, posY, checkBox.mWidth, checkBox.mHeight);
                        add(checkBox);
                        posX += checkBox.mWidth;
                    }
                    posX += COMPONENT_MARGIN;
                }
                posY += lineHeight;
            }
        }

        int posXLeft = LINE_X_START;
        int posXRight = 1150 - BaseButton.WIDTH - LINE_X_START;
        posY = 600 - BaseButton.HEIGHT - LINE_Y_START;
        for (BaseButton button : mButtonList) {
            if (button.mPos == BaseButton.POS_TYPE.LEFT) {
                posX = posXLeft;
                posXLeft += BaseButton.WIDTH + BUTTON_MARGIN;
            } else {
                posX = posXRight;
                posXRight -= (BaseButton.WIDTH + BUTTON_MARGIN);
            }
            button.setBounds(posX, posY, BaseButton.WIDTH, BaseButton.HEIGHT);
            add(button);
        }
    }

    protected String checkData() {
        String msg = null;
        for (List<Object> line : mComponentList) {
            for (Object component : line) {
                if (component instanceof BaseEdit) {
                    BaseEdit edit = (BaseEdit) component;
                    msg = edit.check();

                } else if (component instanceof BaseComboBox) {
                    BaseComboBox combo = (BaseComboBox) component;
                    msg = combo.check();
                }

                if (msg != null) {
                    return msg;
                }
            }
        }
        return null;
    }

    protected void clearFields() {
        for (List<Object> line : mComponentList) {
            for (Object component : line) {
                if (component instanceof BaseEdit) {
                    BaseEdit edit = (BaseEdit) component;
                    edit.setText(null);

                } else if (component instanceof BaseComboBox) {
                    BaseComboBox combo = (BaseComboBox) component;
                    combo.setSelectedIndex(0);

                } else if (component instanceof BaseCheckBox) {
                    BaseCheckBox checkBox = (BaseCheckBox) component;
                    checkBox.setSelected(false);
                }
            }
        }
    }

    protected void setEnabledFields(boolean enable) {
        for (List<Object> line : mComponentList) {
            for (Object component : line) {
                if (component instanceof BaseEdit) {
                    BaseEdit edit = (BaseEdit) component;
                    edit.setEnabled(enable);

                } else if (component instanceof BaseComboBox) {
                    BaseComboBox combo = (BaseComboBox) component;
                    combo.setEnabled(enable);
                }
            }
        }
        for (BaseButton button : mButtonList) {
            button.setEnabled(enable);
        }
    }
}
