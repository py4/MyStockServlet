package ir.Epy.MyStock.DAOs;

import ir.Epy.MyStock.Constants;
import ir.Epy.MyStock.DBConnection;
import ir.Epy.MyStock.Mappers.GTCRequestMapper;
import ir.Epy.MyStock.models.GTCRequest;
import ir.Epy.MyStock.models.StockRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class GTCDAO extends DAO {

    private static GTCDAO ourInstance = new GTCDAO();
    private int maxId = 0;

    private GTCDAO() {
        TABLE_NAME = "stock_requests";
        //db_fields = new ArrayList<String> (Arrays.asList("ID", "CUSTOMER_ID", "STOCK_SYMBOL", "BASE_PRICE", "QUANTITY", "IS_BUY"));
        db_fields = new ArrayList<String> (Arrays.asList("ID", "CUSTOMER_ID", "STOCK_SYMBOL", "BASE_PRICE", "QUANTITY", "TYPE", "IS_BUY"));
        db_pks = new ArrayList<String>(Arrays.asList("ID"));

        ResultSet rs = null;
        try {
            rs = DBConnection.createStatement().executeQuery("select max(ID) as max_id from " + TABLE_NAME);
            if (rs.next()) {
                maxId = rs.getInt("max_id");
                System.out.println("new max id = " + rs.getInt("max_id"));
            }
            else System.out.println("couldnt get max id ");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(Constants.DB_EXIT_CODE);
        }
    }


    public static GTCDAO I() {
        return ourInstance;
    }

    public GTCRequest find(int id) throws SQLException {

        ResultSet rs = super.find(id);
        if (rs.next())
            return GTCRequestMapper.mapRow(rs);
        else return null;
    }

    public GTCRequest create(String customer_id, String stock_symbol, Integer base_price, Integer quantity, String type, Boolean is_buy) throws SQLException {
        System.out.println("new req id " + (maxId+1));
        super.create(maxId+1, customer_id, stock_symbol, base_price, quantity, type, is_buy);
        return new GTCRequest(maxId++, customer_id, stock_symbol, base_price, quantity, type, is_buy);
    }
//    public void delete(int id) throws SQLException {
//        String query = "DELETE FROM " + TABLE_NAME + " r WHERE r.ID=" + id;
//        DBConnection.createStatement().execute(query);
//    }

    public ArrayList<StockRequest> getRequests(String stock_symbol, boolean is_buy) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " r WHERE r.IS_BUY="+(is_buy?"TRUE":"FALSE")+" AND r.STOCK_SYMBOL='"+stock_symbol+"' ORDER BY r.BASE_PRICE "+(is_buy?"ASC":"DESC");
        System.out.println("[DEBUG] query: "+query);
        ResultSet rs = DBConnection.createStatement().executeQuery(query);
        ArrayList<StockRequest> reqs = new ArrayList<>();
        while(rs.next())
            reqs.add(GTCRequestMapper.mapRow(rs));
        return reqs;
    }

    public ArrayList<StockRequest> getSellRequests(String stock_symbol) throws SQLException {
        return getRequests(stock_symbol, false);
    }

    public ArrayList<StockRequest> getBuyRequests(String stock_symbol) throws SQLException {
        return getRequests(stock_symbol, true);
    }
    public ArrayList<StockRequest> getAll() throws SQLException {
        ResultSet rs = super.all();
        ArrayList<StockRequest> cs = new ArrayList<>();
        while(rs.next())
            cs.add(GTCRequestMapper.mapRow(rs));
        return cs;
    }

}