package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static models.EmployeesDao.address_user;
import static models.EmployeesDao.email_user;
import static models.EmployeesDao.full_name_user;
import static models.EmployeesDao.id_user;
import views.SystemView;

public class SettingsController implements MouseListener {

    private final SystemView views;

    public SettingsController(SystemView views) {
        this.views = views;
        this.views.JLabelProducts.addMouseListener(this);
        this.views.JLabelPurchases.addMouseListener(this);
        this.views.JLabelSales.addMouseListener(this);
        this.views.JLabelCustomers.addMouseListener(this);
        this.views.JLabelEmployees.addMouseListener(this);
        this.views.JLabelSuppliers.addMouseListener(this);
        this.views.JLabelCategories.addMouseListener(this);
        this.views.JLabelReports.addMouseListener(this);
        this.views.JLabelSettings.addMouseListener(this);
        Profile();
    }

    //Asignar el perfil del usuario
    public void Profile() {
        this.views.txt_id_profile.setText("" + id_user);
        this.views.txt_name_profile.setText(full_name_user);
        this.views.txt_address_profile.setText(address_user);
        this.views.txt_phone_profile.setText(full_name_user);
        this.views.txt_email_profile.setText(email_user);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
           if(e.getSource() == views.JLabelSettings) {
               views.jTabbedPane10.setSelectedIndex(8);
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

        if (e.getSource() == views.JLabelProducts) {
            views.JPanelProducts.setBackground(new Color(252, 202, 63));
        } else if (e.getSource() == views.JLabelPurchases) {
            views.JPanelPurchases.setBackground(new Color(252, 202, 63));
        } else if (e.getSource() == views.JLabelSales) {
            views.JPanelSales.setBackground(new Color(252, 202, 63));
        } else if (e.getSource() == views.JLabelCustomers) {
            views.JPanelCustomers.setBackground(new Color(252, 202, 63));
        } else if (e.getSource() == views.JLabelEmployees) {
            views.JPanelEmployees.setBackground(new Color(252, 202, 63));
        } else if (e.getSource() == views.JLabelSuppliers) {
            views.JPanelSuppliers.setBackground(new Color(252, 202, 63));
        } else if (e.getSource() == views.JLabelCategories) {
            views.JPanelCategories.setBackground(new Color(252, 202, 63));
        } else if (e.getSource() == views.JLabelReports) {
            views.JPanelReports.setBackground(new Color(252, 202, 63));
        } else if (e.getSource() == views.JLabelSettings) {
            views.JPanelSettings.setBackground(new Color(252, 202, 63));
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (e.getSource() == views.JLabelProducts) {
            views.JPanelProducts.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.JLabelPurchases) {
            views.JPanelPurchases.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.JLabelSales) {
            views.JPanelSales.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.JLabelCustomers) {
            views.JPanelCustomers.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.JLabelEmployees) {
            views.JPanelEmployees.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.JLabelSuppliers) {
            views.JPanelSuppliers.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.JLabelCategories) {
            views.JPanelCategories.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.JLabelReports) {
            views.JPanelReports.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.JLabelSettings) {
            views.JPanelSettings.setBackground(new Color(18, 45, 61));
        }

    }

}
