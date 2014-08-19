package com.ito.ibms.view;

import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import com.ito.ibms.db.DAOCourseType;
import com.ito.ibms.db.model.CourseType;
import com.ito.ibms.view.component.BaseButton;
import com.ito.ibms.view.component.BaseComboBox;
import com.ito.ibms.view.component.BaseEdit;
import com.ito.ibms.view.component.BaseLabel;
import com.ito.ibms.view.component.BasePanel;
import com.ito.ibms.view.component.BaseSeparator;
import com.ito.ibms.view.component.ButtonListener;
import com.ito.ibms.view.component.ComboListener;

public class EPBTypeEdit extends BasePanel implements ButtonListener, ComboListener {
    private static final long serialVersionUID = -1565165257499022922L;
    private static final int COURSE_COMBO = 1;
    private static final int SAVE_BUTTON = 2;
    private static final int STATUS_BUTTON = 3;

    private BaseEdit mFieldName = null;
    private BaseComboBox mFieldRequired = null;
    private BaseComboBox mFieldTypeList = null;
    private BaseLabel mFieldStart = null;
    private BaseLabel mFieldEnd = null;
    private BaseButton mButtonSave = null;
    private BaseButton mButtonStatus = null;

    public EPBTypeEdit() {
        super();

        addComponent(new BaseLabel("EDITAR TIPO DE CURSO", 1130, BaseLabel.TYPE.TITLE));
        addLine();
        addLine();

        addLine();
        addComponent(new BaseLabel("Selecione o tipo do curso", 500, BaseLabel.TYPE.EDIT));

        addLine();
        List<CourseType> list = DAOCourseType.getCourseList(false);
        CourseType none = new CourseType();
        none.mName = "-";
        list.add(0, none);
        mFieldTypeList = new BaseComboBox(list.toArray(), 500, BaseComboBox.TYPE.EDIT, "Pré-requisito", COURSE_COMBO, this);
        addComponent(mFieldTypeList);

        addLine();
        addLine();
        addLine();
        addComponent(new BaseSeparator());

        addLine();
        addLine();
        addComponent(new BaseLabel("Nome", 500, BaseLabel.TYPE.EDIT));
        addComponent(new BaseLabel("Pré-requisito", 500, BaseLabel.TYPE.EDIT));

        addLine();
        mFieldName = new BaseEdit(null, 500, BaseEdit.TYPE.EDIT, "Nome");
        mFieldName.mCheckType = BaseEdit.CHECK_FILLED;
        addComponent(mFieldName);

        list = DAOCourseType.getCourseList(true);
        list.add(0, none);
        mFieldRequired = new BaseComboBox(list.toArray(), 500, BaseComboBox.TYPE.EDIT, "Pré-requisito", 0, null);
        addComponent(mFieldRequired);

        addLine();
        addLine();
        addComponent(new BaseLabel("Data de Criação", 100, BaseLabel.TYPE.EDIT));
        addComponent(new BaseLabel("Data de Desativação", 150, BaseLabel.TYPE.EDIT));

        addLine();
        mFieldStart = new BaseLabel("-", 100, BaseLabel.TYPE.VIEW);
        addComponent(mFieldStart);
        mFieldEnd = new BaseLabel("-", 100, BaseLabel.TYPE.VIEW);
        addComponent(mFieldEnd);

        mButtonSave = new BaseButton("Salvar", "button_save", SAVE_BUTTON, BaseButton.POS_TYPE.LEFT, this);
        mButtonSave.setVisible(false);
        addButton(mButtonSave);

        mButtonStatus = new BaseButton("Desabilitar", "button_deactivate", STATUS_BUTTON, BaseButton.POS_TYPE.RIGHT, this);
        mButtonStatus.setVisible(false);
        addButton(mButtonStatus);
        mountPanel();
    }

    @Override
    public void onClick(int tag) {
        if (tag == SAVE_BUTTON) {
            String msg = checkData();
            if (msg == null) {
                setEnabledFields(false);
                FrameHome.getInstance().setInfo("Atualizando tipo de curso", true);
                CourseType selected = (CourseType)mFieldTypeList.getSelectedItem();
                CourseType item = new CourseType();

                item.mId = selected.mId;
                item.mName = mFieldName.getText();
                if (mFieldRequired.getSelectedIndex() > 0) {
                    item.mRequired = (CourseType)mFieldRequired.getSelectedItem();
                } else {
                    item.mRequired = null;
                }
                item.mStart = selected.mStart;
                item.mEnd = selected.mEnd;

                if (DAOCourseType.update(item) == null) {
                    JOptionPane.showMessageDialog(this, "Não foi possível alterar o Tipo de Curso.\nPor favor reinicie a aplicação.", "Falha na criação", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Tipo de Curso alterado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();

                    List<CourseType> list = DAOCourseType.getCourseList(false);
                    CourseType none = new CourseType();
                    none.mName = "-";
                    list.add(0, none);
                    mFieldTypeList.updateList(list.toArray());

                    list = DAOCourseType.getCourseList(true);
                    none = new CourseType();
                    none.mName = "-";
                    list.add(0, none);
                    mFieldRequired.updateList(list.toArray());
                }

                FrameHome.getInstance().setInfo("", false);
                setEnabledFields(true);
            } else {
                JOptionPane.showMessageDialog(this, msg, "Falha na alteração", JOptionPane.ERROR_MESSAGE);
            }
        } else if (tag == STATUS_BUTTON) {
            setEnabledFields(false);
            CourseType selected = (CourseType)mFieldTypeList.getSelectedItem();
            CourseType item = new CourseType();

            item.mId = selected.mId;
            item.mName = selected.mName;
            item.mRequired = selected.mRequired;
            item.mStart = selected.mStart;
            item.mEnd = selected.mEnd;
            if (item.mEnd.getTime() == -62135758800000L) {
                FrameHome.getInstance().setInfo("Desativando tipo de curso", true);
                item.mEnd = Calendar.getInstance().getTime();
            } else {
                item.mEnd.setTime(-62135758800000L);
                FrameHome.getInstance().setInfo("Ativando tipo de curso", true);
            }

            if (DAOCourseType.update(item) == null) {
                JOptionPane.showMessageDialog(this, "Não foi possível alterar o Tipo de Curso.\nPor favor reinicie a aplicação.", "Falha na criação", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Tipo de Curso alterado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                clearFields();

                List<CourseType> list = DAOCourseType.getCourseList(false);
                CourseType none = new CourseType();
                none.mName = "-";
                list.add(0, none);
                mFieldTypeList.updateList(list.toArray());

                list = DAOCourseType.getCourseList(true);
                none = new CourseType();
                none.mName = "-";
                list.add(0, none);
                mFieldRequired.updateList(list.toArray());
            }

            FrameHome.getInstance().setInfo("", false);
            setEnabledFields(true);
        }
    }

    @Override
    protected String checkData() {
        String msg = super.checkData();

        if (msg == null) {
            CourseType item = (CourseType)mFieldTypeList.getSelectedItem();
            String name = mFieldName.getText();
            if (!item.mName.equals(name) && (DAOCourseType.findByName(name) != null)) {
                msg = "Já existe um tipo de curso com o nome \"" + name + "\".";
            }
        }

        return msg;
    }

    @Override
    public void onItemSelected(int tag) {
        if (tag == COURSE_COMBO) {
            if (mFieldTypeList.getSelectedIndex() == 0) {
                clearFields();
                mFieldStart.setText("-");
                mFieldEnd.setText("-");
                mButtonStatus.setVisible(false);
                mButtonSave.setVisible(false);
            } else {
                CourseType item = (CourseType) mFieldTypeList.getSelectedItem();
                if (item == null) {
                    return;
                }
                mFieldName.setText(item.mName);
                mFieldStart.setDate(item.mStart);
                if (item.mEnd.getTime() == -62135758800000L) {
                    mFieldEnd.setText("-");
                    mButtonStatus.changeIcon("Desativar","button_deactivate");
                } else {
                    mFieldEnd.setDate(item.mEnd);
                    mButtonStatus.changeIcon("Ativar","button_activate");
                }
                if (item.mRequired == null) {
                    mFieldRequired.setSelectedIndex(0);
                } else {
                    int reqIndex = getRequiredIndex(item.mRequired.mId);
                    if (reqIndex == -1) {
                        mFieldRequired.setSelectedIndex(0);
                    } else {
                        mFieldRequired.setSelectedIndex(reqIndex);
                    }
                }
                mButtonStatus.setVisible(true);
                mButtonSave.setVisible(true);
            }
        }
    }

    public int getRequiredIndex(int id) {
        for (int i=0; i < mFieldRequired.mList.length; i++) {
            CourseType item = (CourseType) mFieldRequired.mList[i];
            if (item.mId == id) {
                return i;
            }
        }
        return -1;
    }
}
