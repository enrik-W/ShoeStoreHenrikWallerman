package Database;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {
    private Properties p = new Properties();

    public Repository() {
        try {
            p.load(new FileInputStream("src/Database/Settings.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String addToCart(int customerId, Integer orderId, int shoeId) {
        ResultSet rs = null;
        String query = "CALL AddToCart(?, ?, ?)";
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {

            stmt.setInt(1, customerId);
            stmt.setObject(2, orderId);
            stmt.setInt(3, shoeId);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occurred, could not add product to cart.";
        }
        return "Product was added successfully";
    }

    public Customer findIdOfCustomer(String firstName, String lastName, String password) {
        String query = "SELECT customerId FROM customer WHERE surname = ? and lastname = ? and userPassword = ?";
        ResultSet rs;
        Customer customer = null;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, password);

            rs = stmt.executeQuery();
            while (rs.next()) {
                customer = new Customer(rs.getInt("customerId"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return customer;
    }

    public Shoe findIdOfShoe(String colour, int shoeSize, int prize, String brand) {
        String query = "SELECT shoeId FROM shoe WHERE colour = ? and shoeSize = ? and prize = ? and brand = ?";
        ResultSet rs;
        Shoe shoe = null;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, colour);
            stmt.setInt(2, shoeSize);
            stmt.setInt(3, prize);
            stmt.setString(4, brand);

            rs = stmt.executeQuery();
            while (rs.next()) {
                shoe = new Shoe(rs.getInt("shoeId"), colour, shoeSize, prize, brand);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return shoe;
    }

    public List<Shoe> getAllShoesInStock() {
        String query = "SELECT * FROM shoe " +
                "INNER JOIN stock " +
                "ON stock.shoeId = shoe.shoeId " +
                "WHERE stock.stock > 0";
        ResultSet rs;
        List<Shoe> shoesInStock = new ArrayList();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {
            rs = stmt.executeQuery();
            while (rs.next()) {
                shoesInStock.add(new Shoe(rs.getInt("shoeId"), rs.getString("colour"),
                        rs.getInt("shoeSize"), rs.getInt("prize"),
                        rs.getString("brand")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoesInStock;
    }

    //NOT DONE!!!!!!!!
    public List<Order> getAllShoesInCustomersOrder(int customerId) {
        String query = "SELECT * FROM shoesinorder " +
                "INNER JOIN orders " +
                "ON orders.orderId = shoesinorder.orderId " +
                "INNER JOIN shoe " +
                "ON shoe.shoeId = shoesinorder.shoeId " +
                "WHERE orders.customerId = ?";
        ResultSet rs;
        List<Order> allShoesInCustomersOrder = new ArrayList();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                allShoesInCustomersOrder.add(new Order(rs.getInt("shoe.shoeId"), rs.getString("shoe.colour"),
                        rs.getInt("shoe.shoeSize"), rs.getInt("shoe.prize"),
                        rs.getString("shoe.brand"), rs.getString("orders.dateoforder")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allShoesInCustomersOrder;
    }
}
