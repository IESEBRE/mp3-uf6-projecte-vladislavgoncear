package org.example.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VistaPestanyes extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel Stock;
    private JTable table1;
    private JButton INSERIRButton;
    private JTextField preuActual;
    private JTextField talla;
    private JTextField dataSortida;
    private JTextField nomSneaker;
    private JTextField modelSneaker;
    private JTextField preuSortida;
    private JTextField numProducte;
    private JCheckBox dispo;
    private JButton MODIFICARButton;
    private JButton BORRARButton;
    private JPanel Tenda;
    private JTable table2;
    private JComboBox comboBox1;
    private JButton inserirButton2;
    private JTextField tendanom;
    private JButton modificarButton2;
    private JButton borrarButton2;
    private JButton trobar;
    private JButton filtreButton;

    public JButton getInserirButton2() {
        return inserirButton2;
    }

    public JTextField getTendanom() {
        return tendanom;
    }

    public JButton getModificarButton2() {
        return modificarButton2;
    }

    public JButton getBorrarButton2() {
        return borrarButton2;
    }
//Getters dels objectes de la vista

    public JTable getTable1() {
        return table1;
    }

    public JButton getBORRARButton() {
        return BORRARButton;
    }

    public JButton getMODIFICARButton() {
        return MODIFICARButton;
    }

    public JButton getINSERIRButton() {
        return INSERIRButton;
    }

    public JTextField getPreuActual() {
        return preuActual;
    }

    public JTextField getTalla() {
        return talla;
    }

    public JTextField getDataSortida() {
        return dataSortida;
    }

    public JTextField getNomSneaker() {
        return nomSneaker;
    }

    public JTextField getModelSneaker() {
        return modelSneaker;
    }

    public JTextField getPreuSortida() {
        return preuSortida;
    }

    public JTextField getNumProducte() {
        return numProducte;
    }

    public JCheckBox getDispo() {
        return dispo;
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    public JTable getTable2() {
        return table2;
    }

    public JButton getInserirButton() {
        return inserirButton2;
    }

    public JTextField getTextField1() {
        return tendanom;
    }

    public JButton getTrobar() {
        return trobar;
    }

    public void setTrobar(JButton trobar) {
        this.trobar = trobar;
    }

    public JButton getFiltreButton() {
        return filtreButton;
    }

    //fem que es pugui veure la finestra
    public VistaPestanyes() {

        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(false);

        //Diseny de la finestra

        this.setMinimumSize(new Dimension(400, 400));
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel1.setBackground(Color.LIGHT_GRAY);

    }


}
