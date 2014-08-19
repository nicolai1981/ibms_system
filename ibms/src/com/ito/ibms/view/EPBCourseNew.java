package com.ito.ibms.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.ito.ibms.db.DAOCourse;
import com.ito.ibms.db.DAOCourseType;
import com.ito.ibms.db.model.Course;
import com.ito.ibms.db.model.CourseType;
import com.ito.ibms.view.component.BaseButton;
import com.ito.ibms.view.component.BaseCheckBox;
import com.ito.ibms.view.component.BaseComboBox;
import com.ito.ibms.view.component.BaseEdit;
import com.ito.ibms.view.component.BaseLabel;
import com.ito.ibms.view.component.BasePanel;
import com.ito.ibms.view.component.BaseSeparator;
import com.ito.ibms.view.component.ButtonListener;
import com.ito.ibms.view.component.ComboListener;

public class EPBCourseNew extends BasePanel implements ButtonListener, ComboListener {
    private static final long serialVersionUID = -1565165257499022922L;
    private static final SimpleDateFormat sDateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    private static final int COURSE_COMBO = 1;
    private static final int SAVE_BUTTON = 2;
    private static final int CALENDAR_BUTTON = 3;

    private static final int DATE_SIZE = 80;

    private BaseComboBox mFieldTypeList = null;
    private BaseEdit mFieldBookValue = null;
    private BaseEdit mFieldSubscribeValue = null;
    private BaseEdit mFieldStart = null;
    private BaseLabel mFieldEnd = null;
    private BaseEdit mFieldClasses = null;
    private BaseLabel mFieldVersion = null;
    private BaseLabel mFieldRequired = null;

    private BaseCheckBox[] mFieldWeek = new BaseCheckBox[7];
    private BaseLabel[] mLabelDates = new BaseLabel[DATE_SIZE];
    private BaseEdit[] mFieldDates = new BaseEdit[DATE_SIZE];

    private BaseButton mButtonCalendar = null;
    private BaseButton mButtonSave = null;

    public EPBCourseNew() {
        super();

        addComponent(new BaseLabel("CRIAR DE CURSO", 1130, BaseLabel.TYPE.TITLE));
        addLine();
        addLine();

        addLine();
        addComponent(new BaseLabel("Selecione o tipo do curso", 500, BaseLabel.TYPE.EDIT));
        addComponent(new BaseLabel("Edição", 50, BaseLabel.TYPE.EDIT));
        addComponent(new BaseLabel("Pré-requisito", 500, BaseLabel.TYPE.EDIT));

        addLine();
        List<CourseType> list = DAOCourseType.getCourseList(true);
        CourseType none = new CourseType();
        none.mName = "-";
        list.add(0, none);
        mFieldTypeList = new BaseComboBox(list.toArray(), 500, BaseComboBox.TYPE.EDIT, "Tipo de Curso", COURSE_COMBO, this);

        addComponent(mFieldTypeList);
        mFieldVersion = new BaseLabel("-", 50, BaseLabel.TYPE.VIEW);
        addComponent(mFieldVersion);

        mFieldRequired = new BaseLabel("-", 500, BaseLabel.TYPE.VIEW);
        addComponent(mFieldRequired);

        addLine();
        addLine();
        addLine();
        addComponent(new BaseSeparator());

        addLine();
        addLine();
        addComponent(new BaseLabel("Valor Inscrição", 100, BaseLabel.TYPE.EDIT));
        addComponent(new BaseLabel("Valor Material", 100, BaseLabel.TYPE.EDIT));
        addComponent(new BaseLabel("Total de aulas", 90, BaseLabel.TYPE.EDIT));
        addComponent(new BaseLabel("Data de início", 90, BaseLabel.TYPE.EDIT));
        addComponent(new BaseLabel("Data de fim", 90, BaseLabel.TYPE.EDIT));
        addComponent(new BaseLabel("Dia das aulas", 700, BaseLabel.TYPE.EDIT));

        addLine();
        mFieldSubscribeValue = new BaseEdit(null, 100, BaseEdit.TYPE.EDIT, "Valor Inscrição");
        mFieldSubscribeValue.mCheckType = BaseEdit.CHECK_FILLED | BaseEdit.CHECK_MONEY;
        addComponent(mFieldSubscribeValue);

        mFieldBookValue = new BaseEdit(null, 100, BaseEdit.TYPE.EDIT, "Valor Material");
        mFieldBookValue.mCheckType = BaseEdit.CHECK_FILLED | BaseEdit.CHECK_MONEY;
        addComponent(mFieldBookValue);

        mFieldClasses = new BaseEdit(null, 90, BaseEdit.TYPE.EDIT, "Total de aulas");
        mFieldClasses.mCheckType = BaseEdit.CHECK_FILLED | BaseEdit.CHECK_NUMBER
                                 | BaseEdit.CHECK_NOT_ZERO | BaseEdit.CHECK_POSITIVE;
        addComponent(mFieldClasses);

        mFieldStart = new BaseEdit(null, 90, BaseEdit.TYPE.EDIT, "Data de início");
        mFieldStart.mCheckType = BaseEdit.CHECK_FILLED | BaseEdit.CHECK_DATE;
        addComponent(mFieldStart);

        mFieldEnd = new BaseLabel("-", 90, BaseLabel.TYPE.VIEW);
        addComponent(mFieldEnd);

        mFieldWeek[0] = new BaseCheckBox("Dom", 60);
        addComponent(mFieldWeek[0]);
        mFieldWeek[1] = new BaseCheckBox("Seg", 60);
        addComponent(mFieldWeek[1]);
        mFieldWeek[2] = new BaseCheckBox("Ter", 60);
        addComponent(mFieldWeek[2]);
        mFieldWeek[3] = new BaseCheckBox("Qua", 60);
        addComponent(mFieldWeek[3]);
        mFieldWeek[4] = new BaseCheckBox("Qui", 60);
        addComponent(mFieldWeek[4]);
        mFieldWeek[5] = new BaseCheckBox("Sex", 60);
        addComponent(mFieldWeek[5]);
        mFieldWeek[6] = new BaseCheckBox("Sáb", 60);
        addComponent(mFieldWeek[6]);

        addLine();
        addLine();
        addLine();
        addComponent(new BaseSeparator());

        addLine();
        addLine();
        mButtonCalendar = new BaseButton("Criar Aulas", "button_calendar", CALENDAR_BUTTON, 30, 130, this);
        mButtonCalendar.setVisible(false);
        addComponent(mButtonCalendar);

        for (int i=0; i < DATE_SIZE/10; i++) {
            addLine();
            addLine();
            for (int j=0; j < 10; j++) {
                BaseLabel label =  new BaseLabel("Aula " + (i*10 + j + 1), 100, BaseLabel.TYPE.EDIT);
                label.setVisible(false);
                mLabelDates[i*10 + j] = label;
                addComponent(label);
            }
            addLine();
            for (int j=0; j < 10; j++) {
                BaseEdit edit =  new BaseEdit(null, 100, BaseEdit.TYPE.EDIT, "Aula " + (i*10 + j + 1));;
                edit.setVisible(false);
                mFieldDates[i*10 + j] = edit;
                addComponent(edit);
            }
        }

        // Buttons
        mButtonSave = new BaseButton("Salvar", "button_save", SAVE_BUTTON, BaseButton.POS_TYPE.LEFT, this);
        mButtonSave.setVisible(false);
        addButton(mButtonSave);

        mountPanel();
    }

    @Override
    public void onClick(int tag) {
        if (tag == SAVE_BUTTON) {
            String msg = checkData();
            if (msg == null) {
                setEnabledFields(false);
                FrameHome.getInstance().setInfo("Criando curso", true);

                // Get course information
                Course item = new Course();
                item.mType = (CourseType)mFieldTypeList.getSelectedItem();
                item.mVersion = Integer.valueOf(mFieldVersion.getText());
                item.mSubscribeValue = mFieldSubscribeValue.getDouble();
                item.mBookValue = mFieldBookValue.getDouble();
                item.mTotalClasses = mFieldClasses.getInt();
                item.mStart = mFieldStart.getDate();
                item.mEnd = mFieldDates[item.mTotalClasses-1].getDate();
                for (int i=0; i < 7; i++) {
                    item.mWeekDays[i] = mFieldWeek[i].isSelected();
                }
                item.mClassList.clear();
                for (int i=0; i<item.mTotalClasses; i++) {
                    item.mClassList.add(mFieldDates[i].getDate());
                }

                if (DAOCourse.insert(item) == null) {
                    JOptionPane.showMessageDialog(this, "Não foi possível criar o curso.\nPor favor reinicie a aplicação.", "Falha na criação", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Curso criado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();

                    mFieldEnd.setText("-");
                    mButtonCalendar.setVisible(false);
                    mButtonSave.setVisible(false);
                    for (BaseEdit field : mFieldDates) {
                        field.setVisible(false);
                    }
                    for (BaseLabel label : mLabelDates) {
                        label.setVisible(false);
                    }
                }

                FrameHome.getInstance().setInfo("", false);
                setEnabledFields(true);
            } else {
                JOptionPane.showMessageDialog(this, msg, "Falha na criação", JOptionPane.ERROR_MESSAGE);
            }

            FrameHome.getInstance().setInfo("", false);
            setEnabledFields(true);
        } else if (tag == CALENDAR_BUTTON) {
            String msg = checkCalendar();

            if (msg == null) {
                int nClasses = mFieldClasses.getInt();
                for (int i=0; i < DATE_SIZE; i++) {
                    if (i < nClasses) {
                        mLabelDates[i].setVisible(true);
                        mFieldDates[i].setVisible(true);
                        mFieldDates[i].mCheckType = BaseEdit.CHECK_FILLED | BaseEdit.CHECK_DATE | BaseEdit.CHECK_NOT_PAST;
                    } else {
                        mLabelDates[i].setVisible(false);
                        mFieldDates[i].setVisible(false);
                        mFieldDates[i].mCheckType = 0;
                    }
                    mFieldDates[i].setText("");
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mFieldStart.getDate());

                int index = getWeekIndex(calendar.getTime());
                mFieldDates[0].setText(sDateFormatter.format(calendar.getTime()));
                for (int i=1; i < nClasses; i++) {
                    int delta = 0;
                    for (int j=0; j < 7; j++) {
                        delta++;
                        index++;
                        if (index >= 7) {
                            index = 0;
                        }
                        if (mFieldWeek[index].isSelected()) {
                            break;
                        }
                    }
                    calendar.add(Calendar.DAY_OF_MONTH, delta);
                    mFieldDates[i].setText(sDateFormatter.format(calendar.getTime()));
                }
                mFieldEnd.setText(sDateFormatter.format(calendar.getTime()));

                mButtonSave.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, msg, "Falha na criação das aulas", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String checkCalendar() {
        String msg = null;

        msg = mFieldClasses.check();
        if (msg != null) {
            return msg;
        }

        msg = mFieldStart.check();
        if (msg != null) {
            return msg;
        }

        int nSelected = 0;
        int nClasses = mFieldClasses.getInt();
        for (int i=0; i < 7; i++) {
            if (mFieldWeek[i].isSelected()) {
                nSelected++;
            }
        }
        if (nSelected == 0) {
            return "Selecione pelo menos um dia da semana.";
        }
        if (nClasses < nSelected) {
            return "O número de aulas é menor que a quantidade de dias da semana selecionados.";
        }

        if (!mFieldWeek[getWeekIndex(mFieldStart.getDate())].isSelected()) {
            return "A data inicial não é nenhum dia da semana selecionado.";
        }

        return msg;
    }

    private int getWeekIndex(Date date) {
        int index = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
        case Calendar.SUNDAY:
            index = 0;
            break;
        case Calendar.MONDAY:
            index = 1;
            break;
        case Calendar.TUESDAY:
            index = 2;
            break;
        case Calendar.WEDNESDAY:
            index = 3;
            break;
        case Calendar.THURSDAY:
            index = 4;
            break;
        case Calendar.FRIDAY:
            index = 5;
            break;
        case Calendar.SATURDAY:
            index = 6;
            break;
        }
        return index;
    }

    @Override
    protected String checkData() {
        String msg = super.checkData();

        if (msg == null) {
        }

        return msg;
    }

    @Override
    public void onItemSelected(int tag) {
        if (tag == COURSE_COMBO) {
            if (mFieldTypeList.getSelectedIndex() == 0) {
                clearFields();
                mFieldVersion.setText("-");
                mFieldRequired.setText("-");
                mButtonSave.setVisible(false);
                mButtonCalendar.setVisible(false);
            } else {
                CourseType item = (CourseType) mFieldTypeList.getSelectedItem();
                if (item == null) {
                    return;
                }
                int version = DAOCourse.getNextVersion(item.mId);
                mFieldVersion.setText(String.valueOf(version));
                if (item.mRequired == null) {
                    mFieldRequired.setText("NENHUM");
                } else {
                    mFieldRequired.setText(item.mRequired.mName);
                }
                mButtonCalendar.setVisible(true);
            }
        }
    }
}
