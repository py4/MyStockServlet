package ir.Epy.MyStock.tests;

import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.exceptions.CustomerAlreadyExistsException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.models.Customer;

import java.sql.SQLException;

public class CustomerDAOTest {
    public static void main(String[] args) {

        try {
            CustomerDAO.deleteCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Customer customer = null;
        try {
            customer = CustomerDAO.findCustomer("2");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CustomerNotFoundException e) {
            System.out.println("CustomerNotFoundException is working!");
        }

        try {
            CustomerDAO.addCustomer("2","Ehsan", "HajYasini", 100);
            System.out.println("Customer added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CustomerAlreadyExistsException e) {
            e.printStackTrace();
        }

        try {
            CustomerDAO.addCustomer("2", "Pooya", "Moradi", 200);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CustomerAlreadyExistsException e) {
            System.out.println("CustomerAlreadyExistsException is working!");
        }

        try {
            customer = CustomerDAO.findCustomer("2");
            System.out.println("Customer was found successfully!");
            System.out.println(customer.name + " " + customer.family + " " + customer.getDeposit());
        } catch (CustomerNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}