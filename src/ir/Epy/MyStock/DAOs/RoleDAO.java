package ir.Epy.MyStock.DAOs;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DBConnection;
import ir.Epy.MyStock.Mappers.GTCRequestMapper;
import ir.Epy.MyStock.Mappers.RoleMapper;
import ir.Epy.MyStock.models.GTCRequest;
import ir.Epy.MyStock.models.Role;
import ir.Epy.MyStock.models.StockRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class RoleDAO extends DAO {

    private static RoleDAO ourInstance = new RoleDAO();
    private int maxId = 0;

    private RoleDAO() {
        TABLE_NAME = "roles";
        db_fields = new ArrayList<String> (Arrays.asList("USERNAME", "ROLE_NAME"));
        db_pks = new ArrayList<String>(Arrays.asList("USERNAME"));
    }

    public static RoleDAO I() {
        return ourInstance;
    }

    /*public Role find(String username, String role_name) throws SQLException {
        ResultSet rs = super.find(username, role_name);
        if (rs.next())
            return RoleMapper.mapRow(rs);
        else return null;
    } */

    public Role find(String username) throws SQLException {
        PreparedStatement ps = DBConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " s WHERE USERNAME=?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return RoleMapper.mapRow(rs);
        return null;
    }

}