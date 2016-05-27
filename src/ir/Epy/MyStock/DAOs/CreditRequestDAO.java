package ir.Epy.MyStock.DAOs;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DBConnection;
import ir.Epy.MyStock.Mappers.CreditRequestMapper;
import ir.Epy.MyStock.Mappers.CustomerMapper;
import ir.Epy.MyStock.Mappers.GTCRequestMapper;
import ir.Epy.MyStock.exceptions.CreditRequestNotFoundException;
import ir.Epy.MyStock.exceptions.CustomerAlreadyExistsException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.exceptions.InvalidCreditValueRequest;
import ir.Epy.MyStock.models.CreditRequest;
import ir.Epy.MyStock.models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;

public class CreditRequestDAO extends DAO {

    private static CreditRequestDAO ourInstance = new CreditRequestDAO();
    private int maxId = 0;

    private CreditRequestDAO() {
        TABLE_NAME = "credit_requests";
        db_fields = new ArrayList<String> (Arrays.asList("ID", "STATUS", "CUSTOMER_ID", "AMOUNT", "IS_DEPOSIT"));
        db_pks = new ArrayList<String>(Arrays.asList("ID"));

        ResultSet rs = null;
        try {
            rs = DBConnection.createStatement().executeQuery("select max(ID) as max_id from " + TABLE_NAME);
            if (rs.next())
                maxId = rs.getInt("max_id");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(Constants.DB_EXIT_CODE);
        }
    }


    public static CreditRequestDAO I() {
        return ourInstance;
    }

    public CreditRequest find(String id) throws CreditRequestNotFoundException, SQLException {

        ResultSet rs = super.find(id);
        if (rs.next())
            return CreditRequestMapper.mapRow(rs);
        else
            throw new CreditRequestNotFoundException();
    }

    public ArrayList<CreditRequest> getAll() throws SQLException {
        ArrayList<CreditRequest> result = new ArrayList<>();
        ResultSet rs = super.all();
        while(rs.next())
            result.add(CreditRequestMapper.mapRow(rs));
        return result;
    }

    public ArrayList<CreditRequest> getPendingRequests() throws SQLException {
        ArrayList<CreditRequest> result = new ArrayList<>();
        ResultSet rs = super.all("WHERE STATUS='"+Constants.PendingStatus+"'");
        while(rs.next()) {
            result.add(CreditRequestMapper.mapRow(rs));
        }

        return result;
    }

    public void create(String customer_id, int amount, int status, boolean is_deposit) throws SQLException, InvalidCreditValueRequest {
        if(amount < 0)
            throw new InvalidCreditValueRequest();
        super.create(maxId+1, status, customer_id, amount, is_deposit);
        maxId++;
    }

}