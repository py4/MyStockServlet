package ir.Epy.MyStock.Mappers;

import ir.Epy.MyStock.models.Role;
import ir.Epy.MyStock.models.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper {
    public static Role mapRow(ResultSet rs) {
        try {
            return new Role(rs.getString("USERNAME"), rs.getString("ROLE_NAME"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}