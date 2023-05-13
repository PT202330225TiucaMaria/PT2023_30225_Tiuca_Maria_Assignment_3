package Dao;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Connection.ConnectionFactory;
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    private String createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    private String createDeleteQuery(HashMap<String,Object> fields){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        for(String field:fields.keySet()){
            sb.append(field+" = ? AND ");
        }
        sb.delete(sb.length()-5,sb.length());
        return sb.toString();
    }
    private void setDeleteStatementParameters(PreparedStatement statement, HashMap<String,Object> conditions) {
        int parameterIndex = 1;
        for (String fieldName : conditions.keySet()) {
            try {
                Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = convertObject(conditions.get(fieldName));
                statement.setObject(parameterIndex++, value);
            } catch (NoSuchFieldException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private String createInsertQuery(T t) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");
        Field[] fields=type.getDeclaredFields();
        for(Field field:fields){
            sb.append(field.getName());
            sb.append(", ");
        }
        sb.delete(sb.length()-2,sb.length()); //si dupa ultimul camp pune ", " - stergem
        sb.append(") VALUE (");
        for(int i=0;i<fields.length;i++){
            sb.append("?, ");
        }
        sb.delete(sb.length()-2,sb.length()); //si dupa ultimul camp pune ", " - stergem
        sb.append(");");
        return sb.toString();
    }
    private void setInsertStatementParameters(PreparedStatement statement, T t) {
        Field[] fields=type.getDeclaredFields();
        for(int i=0;i<fields.length;i++){
            fields[i].setAccessible(true);
            Object value;
            try{
                value=fields[i].get(t);
                statement.setObject(i+1,value);
            }catch(IllegalAccessException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private String createUpdatetQuery(T t, HashMap<String, Object> fieldsUpdatedValues, HashMap<String, Object> conditionFieldsValues) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `");
        sb.append(type.getSimpleName());
        sb.append("` SET ");
        for (String field : fieldsUpdatedValues.keySet()) {
            sb.append("`");
            sb.append(field);
            sb.append("` = ?, ");
        }
        sb.delete(sb.length() - 2, sb.length()); // Remove the last comma and space
        sb.append(" WHERE ");
        for (String condition : conditionFieldsValues.keySet()) {
            sb.append("`");
            sb.append(condition);
            sb.append("` = ? AND ");
        }
        sb.delete(sb.length() - 5, sb.length()); // Remove the last "AND" and space
        return sb.toString();
    }
    private void setUpdateStatementParameters(PreparedStatement statement, T t, HashMap<String, Object> fieldsUpdated, HashMap<String, Object> conditionField) {
        int parameterIndex = 1;
        // Set the updated field values
        for (String fieldName : fieldsUpdated.keySet()) {
            try {
                Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = convertObject(fieldsUpdated.get(fieldName));
                statement.setObject(parameterIndex++, value);
            } catch (NoSuchFieldException | SQLException e) {
                e.printStackTrace();
            }
        }
        // Set the condition field values
        for (String fieldName : conditionField.keySet()) {
            try {
                Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = convertObject(conditionField.get(fieldName));
                statement.setObject(parameterIndex++, value);
            } catch (NoSuchFieldException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private Object convertObject(Object value) {
        if (value instanceof Integer) {
            // Conversion for Integer fields
            return ((Integer) value).intValue(); // Convert to int
        } else if (value instanceof Double) {
            // Conversion for Double fields
            return ((Double) value).doubleValue(); // Convert to double
        } else if (value instanceof String) {
            // Conversion for String fields
            return value.toString(); // Convert to String
        } else {
            // Handle other field types as needed
            return value;
        }
    }
    public List<T> findAll() {
        // TODO:
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            String query = createSelectQuery();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }
    public T insert(T t) {
        // TODO:
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            String insert=createInsertQuery(t);
            statement = connection.prepareStatement(insert);
            setInsertStatementParameters(statement,t);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }
    public T update(T t,HashMap<String, Object> fieldsUpdatedValues, HashMap<String, Object> conditionFieldsValues) {
        // TODO:
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            String update=createUpdatetQuery(t,fieldsUpdatedValues,conditionFieldsValues);
            statement = connection.prepareStatement(update);
            setUpdateStatementParameters(statement,t,fieldsUpdatedValues,conditionFieldsValues);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }
    public void delete( HashMap<String,Object> conditions){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            String delete=createDeleteQuery(conditions);
            statement = connection.prepareStatement(delete);
            setDeleteStatementParameters(statement,conditions);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}