package models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CustomersDao {

    //Instanciar la conneccion
    ConnectionMySql cn = new ConnectionMySql();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Registrar cliente
    public boolean registerCustomerQeury(Customers customer) {
        String query = "INSERT INTO customers (id, full_name, address, telephone, email, created, updated)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, customer.getId());
            pst.setString(2, customer.getFull_name());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getTelephone());
            pst.setString(5, customer.getEmail());
            pst.setTimestamp(6, dateTime);
            pst.setTimestamp(7, dateTime);

            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error ala registrar el cliente" + e);
            return false;
        }

    }

    //Listar clientes
    public List listCustomerQuery(String value) {

        List<Customers> list_customers = new ArrayList<>();

        String query = "SELECT * FROM customers";
        String query_search_customer = "SELECT * FROM customers WHERE id LIKE '%" + value + "%'";

        try {

            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_customer);
                rs = pst.executeQuery();
            }

            while (rs.next()) {
                Customers customer = new Customers();
                customer.setId(rs.getInt("id"));
                customer.setFull_name(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));

                list_customers.add(customer);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        return list_customers;
    }

    //Buscar clientes
    public Customers searchCustomer(int customer_id) {

        Customers customer = new Customers();

        String query_search_customer = "SELECT cu.full_name FROM customers cu WHERE id LIKE '%" + customer_id + "%'";

        try {

            conn = cn.getConnection();

                pst = conn.prepareStatement(query_search_customer);
                rs = pst.executeQuery();

                if (rs.next()) {
                    customer.setFull_name(rs.getString("full_name"));
                }

            }catch(SQLException e ) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

            return customer;
        }
        //Modificar cliente
    public boolean updateCustomerQeury(Customers customer) {
        String query = "UPDATE customers SET full_name = ?, address = ?, telephone = ?, email = ?, updated = ? WHERE id = ?";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            pst.setString(1, customer.getFull_name());
            pst.setString(2, customer.getAddress());
            pst.setString(3, customer.getTelephone());
            pst.setString(4, customer.getEmail());
            pst.setTimestamp(5, dateTime);
            pst.setInt(6, customer.getId());

            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error ala modificar los datos del cliente" + e);
            return false;
        }

    }

    //Eliminar cliente
    public boolean deleteCustomerQuery(int id) {
        String query = "DELETE FROM customers WHERE id =" + id;

        try {

            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();

            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No puedes eliminar un cliente que tengo relcaion con otra tabla" + e);
            return false;
        }
    }

}
