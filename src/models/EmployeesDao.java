
package models;


import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class EmployeesDao {
    
    //Instanciar la conneccion
    ConnectionMySql cn = new ConnectionMySql();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    //Variables para envia datos entre interfaces
    public static int id_user = 0;
    public static String full_name_user = "";
    public static String username_user = "";
    public static String address_user = "";
    public static String rol_user = "";
    public static String email_user = "";
    public static String telephone_user = "";
    
    
    //Methodo de login
    public Employees loginQuery(String user, String password) {
        String query = "SELECT * FROM employees WHERE username = ? AND password = ?";
        Employees employee = new Employees();
        
        try {
            
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            //Enviar parametros
            pst.setString(1, user);
            pst.setString(2, password);
            rs = pst.executeQuery();
            
            if(rs.next()) {
                
                employee.setId(rs.getInt("id"));
                id_user = employee.getId();
                employee.setFull_name(rs.getString("full_name"));
                full_name_user = employee.getFull_name();
                employee.setUsername(rs.getString("username"));
                username_user = employee.getUsername();
                employee.setAddress(rs.getString("address"));
                address_user = employee.getAddress();
                employee.setTelephone(rs.getString("telephone"));
                telephone_user = employee.getTelephone();
                employee.setEmail(rs.getString("email"));
                email_user = employee.getEmail();
                employee.setRol(rs.getString("rol"));
                rol_user = employee.getRol();
                
            }
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null ,"Error al obtener empleado " + e);
        }
        
        return employee;
    }
    
    //Registrar empleados
    public boolean registerEmployeeQuery(Employees employee) {
        String query = "INSERT INTO employees(id, full_name, username, address, telephone, email, password, rol, created, updated) "
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Timestamp dateTime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, employee.getId());
            pst.setString(2, employee.getFull_name());
            pst.setString(3, employee.getUsername());
            pst.setString(4, employee.getAddress());
            pst.setString(5, employee.getTelephone());
            pst.setString(6, employee.getEmail());
            pst.setString(7, employee.getPassword());
            pst.setString(8, employee.getRol());
            pst.setTimestamp(9, dateTime);
            pst.setTimestamp(10, dateTime);
            
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el epmleado" + e);
            return false;
        }
        
        
    }
    
    //Listar empleados
    public List listEmployeesQuery(String value) {
        
        List<Employees> list_emloyees = new ArrayList<>();
        String query = "SELECT * FROM employees ORDER by rol ASC";
        String query_search_employee = "SELECT * FROM employees WHERE id LIKE '%" + value + "%'";
        
        try {
            
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_employee);
                rs = pst.executeQuery();
            }
            
            while(rs.next()) {
                Employees employee = new Employees();
                employee.setId(rs.getInt("id"));
                employee.setFull_name(rs.getString("full_name"));
                employee.setUsername(rs.getString("username"));
                employee.setAddress(rs.getString("address"));
                employee.setTelephone(rs.getString("telephone"));
                employee.setEmail(rs.getString("email"));
                employee.setRol(rs.getString("rol"));
                
                list_emloyees.add(employee);
            }
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return list_emloyees;
    }
    
    //Modificar empleado
    public boolean updaterEmployeeQuery(Employees employee) {
        String query = "UPDATE employees SET full_name = ?, username = ?, address = ?, telephone = ?, email = ?, rol = ?, updated = ? "
                + "WHERE id = ?";
        
        Timestamp dateTime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getFull_name());
            pst.setString(2, employee.getUsername());
            pst.setString(3, employee.getAddress());
            pst.setString(4, employee.getTelephone());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getRol());
            pst.setTimestamp(7, dateTime);
            pst.setInt(8, employee.getId());
            
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modoficar los datos del epmleado" + e);
            return false;
        }
        
        
    }
    
    //Eliminar empleado
    public boolean deleteEmployeeQuery(int id) {
        String query = "DELETE FROM employees WHERE id =" + id;
        
        try{
            
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            
            return true;
            
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "No puedes eliminar empleado que tengo relcaion con otra tabla" + e);
            return false;
        }
    }
    
    //Cambia contraseña
    public boolean updateEmployeePassword(Employees employee) {
        String query = "UPDATE employees SET password = ? WHERE username = '" + username_user + "'";
        
        try {
            
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getPassword());
            
            pst.executeUpdate();
            return true;
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar la contraseña" + e);
            return false;
        }
        
    }
    
    
}
