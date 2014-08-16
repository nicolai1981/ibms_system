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
import com.ito.ibms.view.component.ButtonListener;

public class EPBTypeNew extends BasePanel implements ButtonListener {
    private static final long serialVersionUID = -1565165257499022922L;
    private static final int SAVE_BUTTON = 1;

    private BaseEdit mFieldName = null;
    private BaseComboBox mFieldRequired = null;

    public EPBTypeNew() {
        super();

        addComponent(new BaseLabel("CRIAR TIPO DE CURSO", 1130, BaseLabel.TYPE.TITLE));
        addLine();
        addLine();

        addLine();
        addComponent(new BaseLabel("Nome", 500, BaseLabel.TYPE.EDIT));
        addComponent(new BaseLabel("Pré-requisito", 500, BaseLabel.TYPE.EDIT));

        addLine();
        mFieldName = new BaseEdit(null, 500, BaseEdit.TYPE.EDIT, "Nome");
        mFieldName.mCheckType = BaseEdit.CHECK_FILLED;
        addComponent(mFieldName);

        List<CourseType> list = DAOCourseType.getCourseList(true);
        CourseType none = new CourseType();
        none.mName = "-";
        list.add(0, none);
        mFieldRequired = new BaseComboBox(list.toArray(), 500, BaseComboBox.TYPE.EDIT, "Pré-requisito", 0, null);
        addComponent(mFieldRequired);

        addButton(new BaseButton("Salvar", "button_save", SAVE_BUTTON, BaseButton.POS_TYPE.LEFT, this));
        mountPanel();
    }

    @Override
    public void onClick(int tag) {
        if (tag == SAVE_BUTTON) {
            String msg = checkData();
            if (msg == null) {
                setEnabledFields(false);
                FrameHome.getInstance().setInfo("Criando tipo de curso", true);

                CourseType item = new CourseType();
                item.mName = mFieldName.getText();
                item.mStart = Calendar.getInstance().getTime();
                if (mFieldRequired.getSelectedIndex() > 0) {
                    item.mRequired = (CourseType)mFieldRequired.getSelectedItem();
                }

                if (DAOCourseType.insert(item) == null) {
                    JOptionPane.showMessageDialog(this, "Não foi possível criar o Tipo de Encontro.\nPor favor reinicie a aplicação.", "Falha na criação", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Tipo de Encontro criado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    List<CourseType> list = DAOCourseType.getCourseList(true);
                    CourseType none = new CourseType();
                    none.mName = "-";
                    list.add(0, none);
                    mFieldRequired.updateList(list.toArray());
                }

                FrameHome.getInstance().setInfo("", false);
                setEnabledFields(true);
            } else {
                JOptionPane.showMessageDialog(this, msg, "Falha na criação", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected String checkData() {
        String msg = super.checkData();

        if (msg == null) {
            String name = mFieldName.getText();
            if (DAOCourseType.findByName(name) != null) {
                msg = "Já existe um tipo de curso com o nome \"" + name + "\".";
            }
        }

        return msg;
    }
}
