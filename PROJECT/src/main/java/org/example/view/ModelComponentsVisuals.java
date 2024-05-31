package org.example.view;

import org.example.model.entities.Sneaker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ModelComponentsVisuals {

    private DefaultTableModel modelTaulaStock;
    private DefaultTableModel modelTaulaLocation;
    private ComboBoxModel<Sneaker.Tenda.Location> comboBoxModel;

    //Getters

    public DefaultTableModel getModelTaulaStock() {
        return modelTaulaStock;
    }

    public DefaultTableModel getModelTaulaLocation() {
        return modelTaulaLocation;
    }

    public ComboBoxModel<Sneaker.Tenda.Location> getComboBoxModel() {
        return comboBoxModel;
    }

    public ModelComponentsVisuals(){

        //Anem a definir l'estructura de la taula Stock
        modelTaulaStock = new DefaultTableModel(new Object[]{"Marca", "Model", "Talla", "PreuSortida", "PreuVenta", "DataSortida", "En Stock", "Numero Producte","Object"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //Fem que TOTES les cel·les no siguen editables
                return false;
            }
            //Bloquejem la columna del num produte per a que no es pugui modificar un cop introduit
        };

        modelTaulaLocation = new DefaultTableModel(new Object[]{"Localitzacio","Tenda"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //Fem que TOTES les cel·les no siguen editables
                return false;
            }
            //Permet definir el tipo de cada columna
            @Override
            public Class getColumnClass(int column){
                return switch (column) {
                    case 0 -> Sneaker.Tenda.Location.class;
                    case 1 -> String.class;
                    default -> Object.class;
                };
            }
        };

        //Estructura del combobox
        comboBoxModel = new DefaultComboBoxModel<>(Sneaker.Tenda.Location.values());
    }
}
