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
import Model.Bill;
import Model.Order;
/**
 * An abstract DAO (Data Access Object) class providing generic database operations.
 *
 * @param <T> The type of the DAO object.
 */
public class AbstractDAO<T> {
    /**
     * The logger for logging messages related to the DAO.
     */
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    /**
     * The logger for logging messages related to the DAO.
     */
    private final Class<T> type;
    /**
     * Constructs a new AbstractDAO instance.
     * It retrieves the class type of the entity based on the generic superclass.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    /**
     * Creates a SELECT query to retrieve all records of the entity.
     *
     * @return The SELECT query string.
     */
    private String createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        if(type== Order.class){
            sb.append("`").append(type.getSimpleName()).append("`");
        }
        else
            sb.append(type.getSimpleName());
        return sb.toString();
    }
    /**
     * Creates a SELECT query to retrieve records of the entity based on a specific field.
     *
     * @param field The field name to filter the records.
     * @return The SELECT query string.
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        if(type== Order.class){
            sb.append("`").append(type.getSimpleName()).append("`");
        }
        else
            sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    /**
     * Creates a DELETE query to delete records of the entity based on given field-value conditions.
     *
     * @param fields The field-value conditions for deletion.
     * @return The DELETE query string.
     */
    private String createDeleteQuery(HashMap<String,Object> fields){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        if(type== Order.class){
            sb.append("`").append(type.getSimpleName()).append("`");
        }
        else
            sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        for(String field:fields.keySet()){
            sb.append(field+" = ? AND ");
        }
        sb.delete(sb.length()-5,sb.length());
        return sb.toString();
    }
    /**
     * Sets the parameter values for the DELETE statement based on the field-value conditions.
     *
     * @param statement   The PreparedStatement object for the DELETE query.
     * @param conditions  The field-value conditions for deletion.
     */
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
    /**
     * Creates the INSERT query for inserting a record into the database.
     *
     * @param t The entity object to be inserted.
     * @return The INSERT query string.
     */
    private String createInsertQuery(T t) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        if(type== Order.class){
            sb.append("`").append(type.getSimpleName()).append("`");
        }
        else
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
    /**
     * Sets the parameters of the INSERT statement based on the entity object.
     *
     * @param statement The PreparedStatement object to set the parameters for.
     * @param t         The entity object containing the values.
     */
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
    /**
     * Creates the UPDATE query for updating records in the database.
     *
     * @param t                   The entity object to be updated.
     * @param fieldsUpdatedValues The field-value pairs of the fields to be updated.
     * @param conditionFieldsValues The field-value pairs of the condition fields.
     * @return The UPDATE query string.
     */
    private String createUpdatetQuery(T t, HashMap<String, Object> fieldsUpdatedValues, HashMap<String, Object> conditionFieldsValues) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `");
        sb.append(type.getSimpleName());
        sb.append("` SET ");
        for (String field : fieldsUpdatedValues.keySet()) {
            sb.append(field);
            sb.append(" = ?, ");
        }
        sb.delete(sb.length() - 2, sb.length()); // Remove the last comma and space
        sb.append(" WHERE ");
        for (String condition : conditionFieldsValues.keySet()) {
            sb.append(condition);
            sb.append(" = ? AND ");
        }
        sb.delete(sb.length() - 5, sb.length()); // Remove the last "AND" and space
        return sb.toString();
    }
    /**
     * Sets the parameters of the UPDATE statement based on the entity object, fields to be updated,
     * and condition fields.
     *
     * @param statement           The PreparedStatement object to set the parameters for.
     * @param t                   The entity object containing the values.
     * @param fieldsUpdated       The field-value pairs of the fields to be updated.
     * @param conditionField      The field-value pairs of the condition fields.
     */
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
    /**
     * Converts the given object value to the appropriate type.
     * This is used for setting parameter values in prepared statements.
     *
     * @param value The object value to convert.
     * @return The converted value.
     */
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
    /**
     * Retrieves all records of the entity from the database.
     *
     * @return A list of all records.
     * @throws SQLException If a database access error occurs.
     */
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
    /**
     * Finds an entity object by its ID.
     *
     * @param id The ID of the entity object to find.
     * @return The found entity object, or null if not found.
     */
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
    /**
     * Creates a list of entity objects based on the ResultSet.
     *
     * @param resultSet The ResultSet containing the data.
     * @return The list of created entity objects.
     */
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
    /**
     * Inserts an entity object into the database.
     *
     * @param t The entity object to be inserted.
     * @return The inserted entity object.
     */
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
    /**
     * Updates an entity object in the database.
     *
     * @param t                     The entity object to be updated.
     * @param fieldsUpdatedValues   The field-value pairs of the fields to be updated.
     * @param conditionFieldsValues The field-value pairs of the condition fields.
     * @return The updated entity object.
     */
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
    /**
     * Deletes records from the database based on the specified conditions.
     *
     * @param conditions The field-value pairs representing the conditions for deletion.
     */
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
    /**
     * Retrieves all records from the database.
     *
     * @return A list of Bill objects representing the retrieved records.
     */
    public List<Bill> findAllRecordsObjects() {
        // TODO:
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            String query = createSelectQuery();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createRecordObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAllRecordsObjects " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    /**
     * Creates a list of Bill objects based on the ResultSet.
     *
     * @param resultSet The ResultSet containing the data.
     * @return The list of created Bill objects.
     * @throws SQLException If an SQL exception occurs.
     */
    private List<Bill> createRecordObjects(ResultSet resultSet) throws SQLException {
        List<Bill> list = new ArrayList<Bill>();
            while (resultSet.next()) {
                int idOrder = resultSet.getInt("idOrder");
                String clientName = resultSet.getString("clientName");
                String clientAddress = resultSet.getString("clientAddress");
                String productName = resultSet.getString("productName");
                int productId = resultSet.getInt("productId");
                int productQuantity = resultSet.getInt("productQuantity");
                double price = resultSet.getDouble("price");
                // Create a new Bill object using the retrieved values
                Bill bill = new Bill(idOrder, clientName, clientAddress, productName, productId, productQuantity, price);
                list.add(bill);
            }
        return list;
    }
    /**
     * Finds Bill objects by their ID.
     *
     * @param id The ID of the Bill objects to find.
     * @return A list of Bill objects matching the specified ID.
     */
    public List<Bill> findByIdBill(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("idOrder");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createRecordObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByIdBill " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}