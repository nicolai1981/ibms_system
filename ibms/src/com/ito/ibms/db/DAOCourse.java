package com.ito.ibms.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.ito.ibms.db.model.Course;
import com.ito.ibms.db.model.CourseType;

public class DAOCourse {
    protected static final SimpleDateFormat sDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public static Course insert(Course item) {
        Course result = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = DBConector.getConnection();
            statement = connection.createStatement();

            String cmd = "INSERT INTO CURSO ("
                  + "DATA_INICIAL, "
                  + "DATA_FINAL, "
                  + "DATA_DESATIVAR, "
                  + "TOTAL_AULAS, "
                  + "VERSAO, "
                  + "FK_TIPO_CURSO, "
                  + "VALOR_INSCRICAO, "
                  + "VALOR_MATERIAL, "
                  + "DIA_SEMANA, "
                  + "DIA_AULA"
                  + ") VALUES ("
                  + "'" + sDateFormatter.format(item.mStart) + "', "
                  + "'" + sDateFormatter.format(item.mEnd) + "', "
                  + "'0001-01-01', "
                  + item.mTotalClasses + ", "
                  + item.mVersion + ", "
                  + item.mType.mId + ", "
                  + item.mSubscribeValue + ", "
                  + item.mBookValue + ", "
                  + item.getWeekDays() + ", "
                  + "'" + item.getClassDays() + "'"
                  + ");";
            statement.execute(cmd);

            cmd = "SELECT TIPO_CURSO.*, CURSO.* FROM TIPO_CURSO, CURSO "
                + "WHERE "
                + "CURSO.FK_TIPO_CURSO=" + item.mType.mId + " "
                + "AND CURSO.VERSAO=" + item.mVersion + " "
                + "AND TIPO_CURSO._ID=CURSO.FK_TIPO_CURSO";
            statement.execute(cmd);
            resultSet = statement.getResultSet();
            if ((resultSet != null) && resultSet.next()) {
                result = DAOCourse.fromCursor(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public static Course update(Course item) {
        Course result = null;
        Statement statement = null;

        try {
            Connection connection = DBConector.getConnection();
            statement = connection.createStatement();

            String cmd = "UPDATE CURSO "
                  + "SET "
                  + "FK_TIPO_CURSO=" + item.mType.mId + ", "
                  + "VERSAO=" + item.mVersion + ", "
                  + "VALOR_INSCRICAO=" + item.mSubscribeValue + ", "
                  + "VALOR_MATERIAL=" + item.mBookValue + ", "
                  + "DATA_INICIAL='" + sDateFormatter.format(item.mStart) + "', "
                  + "DATA_FINAL='" + sDateFormatter.format(item.mEnd) + "', "
                  + "DATA_DESATIVAR='" + sDateFormatter.format(item.mDeactivate) + "', "
                  + "TOTAL_AULAS=" + item.mTotalClasses + ", "
                  + "DIA_SEMANA=" + item.getWeekDays() + ", "
                  + "DIA_AULA='" + item.getClassDays() + "' "
                  + "WHERE _ID=" + item.mId;
            statement.execute(cmd);

            result = item;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public static int getNextVersion(int id) {
        Statement statement = null;
        ResultSet resultSet = null;
        int result = -1;

        try {
            Connection connection = DBConector.getConnection();
            statement = connection.createStatement();
            String cmd = "SELECT MAX(VERSAO) AS NEXT_VERSION FROM CURSO WHERE FK_TIPO_CURSO=" + id;
            statement.execute(cmd);
            resultSet = statement.getResultSet();
            if ((resultSet != null) && resultSet.next()) {
                result = resultSet.getInt("NEXT_VERSION") + 1;
            } else {
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static Course findById(int id) {
        Course result = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = DBConector.getConnection();
            statement = connection.createStatement();
            String cmd = "SELECT TIPO_CURSO.*, CURSO.* FROM TIPO_CURSO, CURSO "
                       + "WHERE "
                       + "CURSO._ID=" + id + " "
                       + "AND CURSO.FK_TIPO_CURSO=CURSO._ID";
            statement.execute(cmd);
            resultSet = statement.getResultSet();
            if ((resultSet != null) && resultSet.next()) {
                result = DAOCourse.fromCursor(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static List<Course> getCourseList(boolean activated) {
        List<Course> result = new ArrayList<Course>();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = DBConector.getConnection();
            statement = connection.createStatement();
            String cmd = "SELECT TIPO_CURSO.*, CURSO.* FROM TIPO_CURSO, CURSO "
                       + "WHERE "
                       + (activated ? "CURSO.DATA_FINAL >= '" + sDateFormatter.format(Calendar.getInstance().getTime()) + "' "
                                    + "AND DATA_DESATIVAR='0001-01-01' " : " ")
                       + "AND CURSO.FK_TIPO_CURSO=TIPO_CURSO._ID "
                       + "ORDER BY CURSO.DATA_INICIAL DESC, NOME ASC";
            statement.execute(cmd);
            resultSet = statement.getResultSet();
            while ((resultSet != null) && resultSet.next()) {
                Course item = DAOCourse.fromCursor(resultSet);
                if (item != null) {
                    result.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }


    public static Course fromCursor(ResultSet resultSet) {
        Course result = null;
        Statement statement = null;

        try {
            result = new Course();
            result.mId = resultSet.getInt("_ID");
            result.mStart = resultSet.getDate("CURSO.DATA_INICIAL");
            result.mEnd = resultSet.getDate("CURSO.DATA_FINAL");
            result.mDeactivate = resultSet.getDate("DATA_DESATIVAR");
            result.mTotalClasses = resultSet.getInt("TOTAL_AULAS");
            result.mVersion = resultSet.getInt("VERSAO");
            result.mBookValue = resultSet.getDouble("VALOR_MATERIAL");
            result.mSubscribeValue = resultSet.getDouble("VALOR_INSCRICAO");
            result.setWeekDays(resultSet.getInt("DIA_SEMANA"));
            result.setClassDays(resultSet.getString("DIA_AULA"));
            result.mType = new CourseType();
            result.mType.mId = resultSet.getInt("FK_TIPO_CURSO");
            result.mType.mName = resultSet.getString("NOME");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
