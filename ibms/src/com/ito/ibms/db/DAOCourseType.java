package com.ito.ibms.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ito.ibms.db.model.CourseType;

public class DAOCourseType {
    protected static final SimpleDateFormat sDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public static CourseType insert(CourseType item) {
        CourseType result = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = DBConector.getConnection();
            statement = connection.createStatement();
            String cmd = "SELECT * FROM TIPO_CURSO WHERE NOME='" + item.mName + "'";
            statement.execute(cmd);
            resultSet = statement.getResultSet();
            if ((resultSet != null) && resultSet.next()) {
                return null;
            }
            resultSet.close();

            cmd = "INSERT INTO TIPO_CURSO "
                  + "(NOME, DATA_INICIAL, DATA_FINAL, REQUISITO) "
                  + "VALUES ("
                  + "'" + item.mName + "', "
                  + "'" + sDateFormatter.format(item.mStart) + "', "
                  + "'0001-01-01', "
                  + (item.mRequired == null ? "0" : item.mRequired.mId)
                  + ");";
            statement.execute(cmd);

            cmd = "SELECT * FROM TIPO_CURSO WHERE NOME='" + item.mName + "'";
            statement.execute(cmd);
            resultSet = statement.getResultSet();
            if ((resultSet != null) && resultSet.next()) {
                result = DAOCourseType.fromCursor(resultSet);
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

    public static CourseType update(CourseType item) {
        CourseType result = null;
        Statement statement = null;

        try {
            Connection connection = DBConector.getConnection();
            statement = connection.createStatement();

            String cmd = "UPDATE TIPO_CURSO "
                  + "SET "
                  + "NOME='" + item.mName + "', "
                  + "DATA_INICIAL='" + sDateFormatter.format(item.mStart) + "', "
                  + "DATA_FINAL='" + sDateFormatter.format(item.mEnd) + "', "
                  + "REQUISITO=" + (item.mRequired == null ? "0" : item.mRequired.mId) + " "
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

    public static CourseType findById(int id) {
        CourseType result = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = DBConector.getConnection();
            statement = connection.createStatement();
            String cmd = "SELECT * FROM TIPO_CURSO WHERE _ID=" + id;
            statement.execute(cmd);
            resultSet = statement.getResultSet();
            if ((resultSet != null) && resultSet.next()) {
                result = DAOCourseType.fromCursor(resultSet);
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

    public static CourseType findByName(String name) {
        CourseType result = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = DBConector.getConnection();
            statement = connection.createStatement();
            String cmd = "SELECT * FROM TIPO_CURSO WHERE NOME='" + name + "'";
            statement.execute(cmd);
            resultSet = statement.getResultSet();
            if ((resultSet != null) && resultSet.next()) {
                result = DAOCourseType.fromCursor(resultSet);
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

    public static List<CourseType> getCourseList(boolean activated) {
        List<CourseType> result = new ArrayList<CourseType>();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = DBConector.getConnection();
            statement = connection.createStatement();
            String cmd = "SELECT * FROM TIPO_CURSO "
                       + (activated ? "WHERE DATA_FINAL='0001-01-01' " : " ")
                       + "ORDER BY NOME";
            statement.execute(cmd);
            resultSet = statement.getResultSet();
            while ((resultSet != null) && resultSet.next()) {
                CourseType item = DAOCourseType.fromCursor(resultSet);
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


    public static CourseType fromCursor(ResultSet resultSet) {
        CourseType result = null;
        Statement statement = null;
        ResultSet reqResult = null;

        try {
            result = new CourseType();
            result.mId = resultSet.getInt("_ID");
            result.mName = resultSet.getString("NOME");
            result.mStart = resultSet.getDate("DATA_INICIAL");
            result.mEnd = resultSet.getDate("DATA_FINAL");

            int reqId = resultSet.getInt("REQUISITO");
            if (reqId != 0) {
                Connection connection = DBConector.getConnection();
                statement = connection.createStatement();
                statement.execute("SELECT * FROM TIPO_CURSO WHERE _ID=" + reqId + " AND DATA_FINAL='0001-01-01'");
                reqResult = statement.getResultSet();
                if ((reqResult != null) && reqResult.next()) {
                    result.mRequired = new CourseType();
                    result.mRequired.mId = reqId;
                    result.mRequired.mName = reqResult.getString("NOME");
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
}
