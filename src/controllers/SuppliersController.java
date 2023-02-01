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
import models.DynamicComboBox;
import static models.EmployeesDao.rol_user;
import models.Suppliers;
import models.SuppliersDao;
import views.SystemView;

public class SuppliersController implements ActionListener, MouseListener, KeyListener {

    private Suppliers supplier;
    private SuppliersDao supplierDao;
    private SystemView views;
    String rol = rol_user;

    DefaultTableModel model = new DefaultTableModel();

    public SuppliersController(Suppliers suppliers, SuppliersDao supplierDao, SystemView views) {
        this.supplier = suppliers;
        this.supplierDao = supplierDao;
        this.views = views;

        //Boton de registrar proveedor
        this.views.btn_register_supplier.addActionListener(this);

        //Boton de modificar proveedor
        this.views.btn_update_supplier.addActionListener(this);

        //Boton de eleiminar proveedor
        this.views.btn_delete_supplier.addActionListener(this);

        //Boton de cancelar
        this.views.btn_cancel_supplier.addActionListener(this);

        this.views.suppliers_table.addMouseListener(this);

        this.views.txt_search_supplier.addKeyListener(this);

        this.views.JLabelSuppliers.addMouseListener(this);
        
        getSupplierName();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_supplier) {
            if (views.txt_supplier_name.getText().equals("")
                    || views.txt_supplier_description.getText().equals("")
                    || views.txt_supplier_addres.getText().equals("")
                    || views.txt_supplier_telephone.getText().equals("")
                    || views.txt_supplier_email.getText().equals("")
                    || views.cmb_supplier_city.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                //Realizar insercion
                supplier.setName(views.txt_supplier_name.getText().trim());
                supplier.setDescription(views.txt_supplier_description.getText().trim());
                supplier.setAddress(views.txt_supplier_addres.getText().trim());
                supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
                supplier.setEmail(views.txt_supplier_email.getText().trim());
                supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString());

                if (supplierDao.registerSupploerQuery(supplier)) {
                    cleanTable();
                    cleanFields();
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Provedor registrado con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha occurido un error al registrar al rpoveedor");
                }
            }
        } else if (e.getSource() == views.btn_update_supplier) {
            if (views.txt_supplier_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {

                if (views.txt_supplier_name.getText().equals("")
                        || views.txt_supplier_addres.getText().equals("")
                        || views.txt_supplier_telephone.getText().equals("")
                        || views.txt_supplier_email.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    supplier.setName(views.txt_supplier_name.getText().trim());
                    supplier.setDescription(views.txt_supplier_description.getText().trim());
                    supplier.setAddress(views.txt_supplier_addres.getText().trim());
                    supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
                    supplier.setEmail(views.txt_supplier_email.getText().trim());
                    supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString());
                    supplier.setId(Integer.parseInt(views.txt_supplier_id.getText()));

                    if (supplierDao.updateSupploerQuery(supplier)) {
                        cleanTable();
                        cleanFields();
                        listAllSuppliers();
                        views.btn_register_supplier.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Datos del provedor modificados con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha occurido un error al modificar los datos del proveedor");
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_supplier) {
            int row = views.suppliers_table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un proveedor para eliminar");
            } else {
                int id = Integer.parseInt(views.suppliers_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminar este proveedor?");

                if (question == 0 && supplierDao.deleteSupplierQuery(id) != false) {
                    cleanTable();
                    cleanFields();
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Proveedor eliminado con exito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_supplier) {
            cleanFields();
            views.btn_register_supplier.setEnabled(true);
        }
    }

    //Listar proveedores
    public void listAllSuppliers() {
        if (rol.equals("Administrador")) {

            List<Suppliers> list = supplierDao.listSuppliersQuery(views.txt_search_supplier.getText());
            model = (DefaultTableModel) views.suppliers_table.getModel();
            Object[] row = new Object[7];

            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getDescription();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getCity();
                model.addRow(row);
            }

            views.suppliers_table.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.suppliers_table) {
            int row = views.suppliers_table.rowAtPoint(e.getPoint());
            views.txt_supplier_id.setText(views.suppliers_table.getValueAt(row, 0).toString());
            views.txt_supplier_name.setText(views.suppliers_table.getValueAt(row, 1).toString());
            views.txt_supplier_description.setText(views.suppliers_table.getValueAt(row, 2).toString());
            views.txt_supplier_addres.setText(views.suppliers_table.getValueAt(row, 3).toString());
            views.txt_supplier_telephone.setText(views.suppliers_table.getValueAt(row, 4).toString());
            views.txt_supplier_email.setText(views.suppliers_table.getValueAt(row, 5).toString());
            views.cmb_supplier_city.setSelectedItem(views.suppliers_table.getValueAt(row, 6).toString());

            //Desabilitar botones
            views.btn_register_supplier.setEnabled(false);
            views.txt_supplier_id.setEditable(false);
        } else if (e.getSource() == views.JLabelSuppliers) {
            if (rol.equals("Administrador")) {
                views.jTabbedPane10.setSelectedIndex(5);
                cleanTable();
                cleanFields();
                listAllSuppliers();
            } else {
                views.jTabbedPane10.setEnabledAt(4, false);
                views.JLabelSuppliers.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes privilegios de administrador para acceder a esta vista");
            }
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
        if (e.getSource() == views.txt_search_supplier) {
            cleanTable();
            listAllSuppliers();
        }
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i -= 1;
        }
    }

    public void cleanFields() {
        views.txt_supplier_id.setText("");
        views.txt_supplier_id.setEditable(true);
        views.txt_supplier_name.setText("");
        views.txt_supplier_description.setText("");
        views.txt_supplier_addres.setText("");
        views.txt_supplier_telephone.setText("");
        views.txt_supplier_email.setText("");
        views.cmb_supplier_city.setSelectedIndex(0);
    }

    //Metodo para mostrar el nombre del proveedor
    public void getSupplierName() {
        List<Suppliers> list = supplierDao.listSuppliersQuery(views.txt_search_supplier.getText());
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            String name = list.get(i).getName();

            views.cmb_purchase_supplier.addItem(new DynamicComboBox(id, name));
        }
    }

}
