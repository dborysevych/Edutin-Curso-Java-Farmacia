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
import models.Categories;
import models.CategoriesDao;
import models.DynamicComboBox;
import static models.EmployeesDao.rol_user;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import views.SystemView;

public class CategoriesController implements ActionListener, MouseListener, KeyListener {

    private Categories category;
    private CategoriesDao categoryDao;
    private SystemView views;
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public CategoriesController(Categories category, CategoriesDao categoryDao, SystemView views) {
        this.category = category;
        this.categoryDao = categoryDao;
        this.views = views;

        //Boton de registrar
        this.views.btn_register_category.addActionListener(this);

        //Boton de modificar categorias
        views.btn_modify_category.addActionListener(this);

        //Boton de eliminar categorias
        this.views.btn_delete_category.addActionListener(this);
        
        //Boton de cancelar
        this.views.btn_cancel_category.addActionListener(this);

        this.views.categories_table.addMouseListener(this);
        this.views.txt_search_category.addKeyListener(this);
        this.views.JLabelCategories.addMouseListener(this);
        
        getCategoryName();
        AutoCompleteDecorator.decorate(views.cmb_product_category);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_category) {
            if (views.txt_category_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                category.setName(views.txt_category_name.getText().trim());

                if (categoryDao.registerCategoryQuery(category)) {
                    cleanTable();
                    cleanFields();
                    listAllCategories();
                    JOptionPane.showMessageDialog(null, "Categoria registrada con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurido un error al registrar la categoria");
                }
            }
        } else if (e.getSource() == views.btn_modify_category) {
            if (views.txt_category_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para confirmar");
            } else {
                if (views.txt_category_id.getText().equals("")
                        || views.txt_category_name.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    category.setId(Integer.parseInt(views.txt_category_id.getText()));
                    category.setName(views.txt_category_name.getText());

                    if (categoryDao.updateCategoryQuery(category)) {
                        cleanTable();
                        cleanFields();
                        views.btn_register_category.setEnabled(true);
                        listAllCategories();
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_category) {
            int row = views.categories_table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una categoria para eliminar");
            } else {
                int id = Integer.parseInt(views.categories_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "En realidad quieres eliminar esta categoria?");

                if (question == 0 && categoryDao.deleteCategoryQuery(id) != false) {
                    cleanFields();
                    cleanTable();
                    views.btn_register_category.setEnabled(true);
                    listAllCategories();
                    JOptionPane.showMessageDialog(null, "Categoria eliminado con exito");
                }
            }
        } else if(e.getSource() == views.btn_cancel_category) {
            cleanFields();
            views.btn_register_category.setEnabled(true);
        }
    }

    //Listar categorias
    public void listAllCategories() {
        if (rol.equals("Administrador")) {

            List<Categories> list = categoryDao.listCategoryQuery(views.txt_search_category.getText());
            model = (DefaultTableModel) views.categories_table.getModel();
            Object[] row = new Object[2];

            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                model.addRow(row);
            }

            views.categories_table.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.categories_table) {
            int row = views.categories_table.rowAtPoint(e.getPoint());
            views.txt_category_id.setText(views.categories_table.getValueAt(row, 0).toString());
            views.txt_category_name.setText(views.categories_table.getValueAt(row, 1).toString());
            views.btn_register_category.setEnabled(false);
        } else if(e.getSource() == views.JLabelCategories) {
            if(rol.equals("Administrador")) {
                views.jTabbedPane10.setSelectedIndex(6);
                cleanTable();
                cleanFields();
                listAllCategories();
            } else {
                views.jTabbedPane10.setEnabledAt(6, false);
                views.JLabelCategories.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes privilejios de administrador para acceder a esta vista");
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
        if (e.getSource() == views.txt_search_category) {
            cleanTable();
            listAllCategories();
        }
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i -= 1;
        }
    }

    public void cleanFields() {
        views.txt_category_id.setText("");
        views.txt_category_name.setText("");
    }
    
    //Metodo para mostrar el nombre de las categorias
    public void getCategoryName() {
        List<Categories> list = categoryDao.listCategoryQuery(views.txt_search_category.getText());
        for(int i = 0; i < list.size();i++) {
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            
            views.cmb_product_category.addItem(new DynamicComboBox(id, name));
        }
    }

}
