package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Customers;
import models.CustomersDao;
import views.SystemView;

public class CustomersController implements ActionListener, MouseListener, KeyListener {

    private Customers customer;
    private CustomersDao customerDao;
    private SystemView views;
    DefaultTableModel model = new DefaultTableModel();

    public CustomersController(Customers customer, CustomersDao customerDao, SystemView views) {
        this.customer = customer;
        this.customerDao = customerDao;
        this.views = views;

        //Boton de registrar clienet
        this.views.btn_register_customer.addActionListener(this);

        //Boton de modificar clientes
        this.views.btn_update_customer.addActionListener(this);

        //Boton de eliminar cliente
        this.views.btn_delete_customer.addActionListener(this);

        //Boton de cancelar
        this.views.btn_cancel_customer.addActionListener(this);

        //Bsucador
        this.views.txt_search_customer.addKeyListener(this);

        this.views.JLabelCustomers.addMouseListener(this);

        this.views.customers_table.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_customer) {
            //Verificar si los campos estan vacios
            if (views.txt_customer_id.getText().equals("")
                    || views.txt_customer_full_name.getText().equals("")
                    || views.txt_customer_adress.getText().equals("")
                    || views.txt_customer_telephone.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                customer.setFull_name(views.txt_customer_full_name.getText().trim());
                customer.setAddress(views.txt_customer_adress.getText().trim());
                customer.setTelephone(views.txt_customer_telephone.getText().trim());
                customer.setEmail(views.txt_customer_email.getText().trim());

                if (customerDao.registerCustomerQeury(customer)) {
                    cleanTable();
                    listAllCustomers();
                    JOptionPane.showMessageDialog(views, "Clienet registrado con exito");
                } else {
                    JOptionPane.showMessageDialog(views, "Ha ocurrido un error al registrar el cliente");
                }
            }
        } else if (e.getSource() == views.btn_update_customer) {
            if (views.txt_customer_id.getText().equals("")) {
                JOptionPane.showMessageDialog(views, "Selecciona una fila para continuar");
            } else {
                if (views.txt_customer_id.getText().equals("")
                        || views.txt_customer_full_name.getText().equals("")
                        || views.txt_customer_adress.getText().equals("")
                        || views.txt_customer_telephone.getText().equals("")
                        || views.txt_customer_email.getText().equals("")) {
                    JOptionPane.showMessageDialog(views, "Todos los campos son obligatorios");
                } else {
                    customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                    customer.setFull_name(views.txt_customer_full_name.getText().trim());
                    customer.setAddress(views.txt_customer_adress.getText().trim());
                    customer.setTelephone(views.txt_customer_telephone.getText().trim());
                    customer.setEmail(views.txt_customer_email.getText().trim());

                    if (customerDao.updateCustomerQeury(customer)) {
                        cleanTable();
                        cleanFields();
                        listAllCustomers();
                        views.btn_register_customer.setEnabled(true);
                        JOptionPane.showMessageDialog(views, "Datos del cliente modificados con exito");
                    } else {
                        JOptionPane.showMessageDialog(views, "Ha occurido un error al modificar los datos del cliente");
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_customer) {
            int row = views.customers_table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(views, "Debes seleccionar un cliente para eliminar");
            } else {
                int id = Integer.parseInt(views.customers_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminae este cliente?");

                if (question == 0 && customerDao.deleteCustomerQuery(id) != false) {
                    cleanFields();
                    cleanTable();
                    views.btn_register_customer.setEnabled(true);
                    listAllCustomers();
                    JOptionPane.showMessageDialog(null, "Cliente eliminado con exito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_customer) {
            views.btn_register_customer.setEnabled(true);
            cleanFields();
        }
    }

    //Listar clientes
    public void listAllCustomers() {
        List<Customers> list = customerDao.listCustomerQuery(views.txt_search_customer.getText());
        model = (DefaultTableModel) views.customers_table.getModel();

        Object[] row = new Object[5];

        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getFull_name();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getTelephone();
            row[4] = list.get(i).getEmail();
            model.addRow(row);
        }

        views.customers_table.setModel(model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.customers_table) {
            int row = views.customers_table.rowAtPoint(e.getPoint());

            views.txt_customer_id.setText(views.customers_table.getValueAt(row, 0).toString());
            views.txt_customer_full_name.setText(views.customers_table.getValueAt(row, 1).toString());
            views.txt_customer_adress.setText(views.customers_table.getValueAt(row, 2).toString());
            views.txt_customer_telephone.setText(views.customers_table.getValueAt(row, 3).toString());
            views.txt_customer_email.setText(views.customers_table.getValueAt(row, 4).toString());

            //Desabilitar botones
            views.btn_register_customer.setEnabled(false);
            views.txt_customer_id.setEditable(false);
        } else if (e.getSource() == views.JLabelCustomers) {
            views.jTabbedPane10.setSelectedIndex(3);
            cleanTable();
            cleanFields();
            listAllCustomers();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_customer) {
            cleanTable();
            listAllCustomers();
        }
    }

    public void cleanFields() {
        views.txt_customer_id.setText("");
        views.txt_customer_id.setEditable(true);
        views.txt_customer_full_name.setText("");
        views.txt_customer_adress.setText("");
        views.txt_customer_telephone.setText("");
        views.txt_customer_email.setText("");
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i -= 1;
        }
    }

}
