package ir.Epy.MyStock.tests;

import ir.Epy.MyStock.DAOs.CustomerDAO;
import ir.Epy.MyStock.exceptions.CustomerAlreadyExistsException;
import ir.Epy.MyStock.exceptions.CustomerNotFoundException;
import ir.Epy.MyStock.models.Customer;

import java.sql.SQLException;

public class CustomerDAOTest {
    public static void main(String[] args) {

        try {
            CustomerDAO.I().deleteAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Customer customer = null;
        try {
            customer = CustomerDAO.I().find("2");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CustomerNotFoundException e) {
            System.out.println("CustomerNotFoundException is working!");
        }

        try {
            CustomerDAO.I().create("2","Ehsan", "HajYasini", 100);
            System.out.println("Customer added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CustomerAlreadyExistsException e) {
            e.printStackTrace();
        }

        try {
            CustomerDAO.I().create("2", "Pooya", "Moradi", 200);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CustomerAlreadyExistsException e) {
            System.out.println("CustomerAlreadyExistsException is working!");
        }

        try {
            customer = CustomerDAO.I().find("2");
            System.out.println("Customer was found successfully!");
            System.out.println(customer.name + " " + customer.family + " " + customer.getDeposit());
        } catch (CustomerNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            customer.name = "ALISH";
            CustomerDAO.I().update(customer);
            Customer c = CustomerDAO.I().find("2");
            if(c.name.equals("ALISH"))
                System.out.println("Update query is working!");
            else
                System.out.println("Update did not work :|");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CustomerNotFoundException e) {
            e.printStackTrace();
        }

    }
}