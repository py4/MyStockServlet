package ir.Epy.MyStock.DAOs;

import ir.Epy.MyStock.DBConnection;
import ir.Epy.MyStock.Mappers.StockMapper;
import ir.Epy.MyStock.exceptions.StockAlreadyExistsException;
import ir.Epy.MyStock.exceptions.StockNotFoundException;
import ir.Epy.MyStock.models.Stock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;

public class ConfigDAO extends DAO {

    private static ConfigDAO ourInstance = new ConfigDAO();
    private int default_status = 0;//pending

    private ConfigDAO() {
        TABLE_NAME = "config";
        db_fields = new ArrayList<String> (Arrays.asList("ID","LIMIT"));
        db_pks = new ArrayList<String>(Arrays.asList("ID"));
    }


    public static ConfigDAO I() {
        return ourInstance;
    }

    public int get_limit() throws SQLException {
        ResultSet rs = super.all();
        if(rs.next())
            return rs.getInt("limit");
        else return Integer.MAX_VALUE;
    }

    public void set_limit(int new_limit) throws SQLException {
        PreparedStatement ps = DBConnection.prepareStatement("UPDATE "+TABLE_NAME+ " SET LIMIT=? WHERE ID='1'");
        ps.setInt(1, new_limit);
        System.out.println("[DEBUG] query: "+ps.toString());
        ps.executeUpdate();
        /*(ResultSet rs = super.find();
        if(rs.next())
            return rs.getInt("limit");
        else return Integer.MAX_VALUE;*/
    }

}