package ir.Epy.MyStock.DAOs;

import ir.Epy.MyStock.DBConnection;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hsqldb.Tokens.WHERE;

/**
 * Created by py4_ on 5/19/16.
 */


public class DAO {

    public String TABLE_NAME;
    public ArrayList<String> db_fields = new ArrayList<String>();
    public ArrayList<String> db_pks = new ArrayList<String>(); //primary keys

    private ArrayList<String> to_string(ArrayList<Object> args) {
        ArrayList<String> result = new ArrayList<>();
        for(Object e : args)
            result.add(e.toString());
        return result;
    }

    private String stringify_fields(Boolean sq, ArrayList<String> arr) {
        String result = "(";
        for(int i = 0; i < arr.size(); i++)
            result += ((sq ? "'" : "\"")+arr.get(i)+(sq ? "'" : "\"") + (i < arr.size() - 1 ? ", " : ""));
        return result + ")";
    }

    private String get_field_values(Object obj, ArrayList<String> fields)  {
        String result = "";
        try {
            for (int i = 0; i < fields.size(); i++) {
                Field f = null;
                f = obj.getClass().getDeclaredField(fields.get(i).toLowerCase());
                f.setAccessible(true);
                result += ("\"" + fields.get(i) + "\"" + "=" + "'" + f.get(obj).toString() + "'") + ((i < (fields.size() - 1)) ? "," : "");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteAll() throws SQLException {
        Statement st = DBConnection.createStatement();
        st.executeQuery("DELETE FROM "+TABLE_NAME);
    }

    public ResultSet find(Object... args) throws SQLException {
        ResultSet result = null;
        String query = "SELECT * FROM "+TABLE_NAME+" s WHERE ";
        for(int i = 0; i < db_pks.size(); i++)
            query += "s."+db_pks.get(i) + "="+args[i] + (i < db_pks.size() - 1 ? " AND " : "");
        System.out.println("[DEBUG] query: "+query);
        return DBConnection.createStatement().executeQuery(query);
    }

    public void create(Object... args) throws SQLException {
        ResultSet result = null;
        String query = "INSERT INTO "+TABLE_NAME+" "+stringify_fields(false, db_fields) + " VALUES " + stringify_fields(true, to_string(new ArrayList<Object>(Arrays.asList(args))));
        System.out.println("[DEBUG] query: "+query);
        DBConnection.createStatement().executeQuery(query);
    }

    public void update(Object obj) throws SQLException {
        String query = "UPDATE "+TABLE_NAME+" SET ";
        query += get_field_values(obj, db_fields);
        query += " WHERE ";
        query += get_field_values(obj, db_pks);
        System.out.println("[DEBUG] query:  "+query);
        DBConnection.createStatement().executeQuery(query);
    }

}
