package ir.Epy.MyStock.DAOs;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DBConnection;
import ir.Epy.MyStock.Mappers.CustomerMapper;
import ir.Epy.MyStock.Mappers.RoleMapper;
import ir.Epy.MyStock.Mappers.TransactionLogMapper;
import ir.Epy.MyStock.exceptions.StockAlreadyExistsException;
import ir.Epy.MyStock.models.Customer;
import ir.Epy.MyStock.models.Role;
import ir.Epy.MyStock.models.StockTransactionLog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;

public class TransactionLogDAO extends DAO {

    private static TransactionLogDAO ourInstance = new TransactionLogDAO();
    private int maxId = 0;

    private TransactionLogDAO() {
        TABLE_NAME = "transaction_logs";
        db_fields = new ArrayList<String> (Arrays.asList("ID", "BUYER", "SELLER", "SYMBOL", "TRADE_TYPE", "QUANTITY", "BUYER_REMAINED_MONEY", "SELLER_CURR_MONEY"));
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

    public static TransactionLogDAO I() {
        return ourInstance;
    }


    public void create(String buyer, String seller, String symbol, String trade_type, int quantity, int buyer_remained_money, int seller_curr_money) throws SQLException {
        try {
            maxId++;
            super.create(maxId, buyer, seller, symbol, trade_type, quantity, buyer_remained_money, seller_curr_money);
        } catch (SQLIntegrityConstraintViolationException e) {
        }
    }

    public ArrayList<StockTransactionLog> getAll() throws SQLException {
        ResultSet rs = super.all();
        ArrayList<StockTransactionLog> cs = new ArrayList<>();
        while(rs.next())
            cs.add(TransactionLogMapper.mapRow(rs));
        return cs;
    }



}