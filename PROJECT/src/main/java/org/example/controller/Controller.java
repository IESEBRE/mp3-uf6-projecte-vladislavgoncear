package org.example.controller;

import org.example.model.SneakerDAOJDBCOracleImpl;
import org.example.model.daos.DAO;
import org.example.model.entities.Sneaker;
import org.example.model.exceptions.DAOException;
import org.example.view.ModelComponentsVisuals;
import org.example.view.VistaPestanyes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class Controller implements PropertyChangeListener {

    private ModelComponentsVisuals modelComponentsVisuals = new ModelComponentsVisuals();
    private SneakerDAOJDBCOracleImpl dadesSneaker;
    private VistaPestanyes vista;

    private SneakerDAOJDBCOracleImpl model;

    public Controller(SneakerDAOJDBCOracleImpl dadesSneaker, VistaPestanyes vista) {
        this.dadesSneaker = dadesSneaker;
        this.vista = vista;

        //Metoge per lligar la vista i el model
        lligarVistaModel();
        //Assigno el codi dels listeners
        assignarCodiListeners();

        //Necessari per a que COntroller reaccione davant de canvis a les propietats lligades
        change.addPropertyChangeListener(this);

        //Si no hem tingut cap problema amb la BD, mostren la finestra
        vista.setVisible(true);

    }

    private void lligarVistaModel() {
        //Carreguem la taula amb les dades de la BD
        try{
            setModelTaulaStock(modelComponentsVisuals.getModelTaulaStock(), dadesSneaker.getAll());
        } catch (DAOException e){
            this.setExcepcio(e);
        }


        //Fixem el model de la taula Sneakers
        JTable taula = vista.getTable1();
        taula.setModel(this.modelComponentsVisuals.getModelTaulaStock());

        //Amago la columna que conte l'objecte sneaker
        taula.getColumnModel().getColumn(8).setMinWidth(0);
        taula.getColumnModel().getColumn(8).setMaxWidth(0);
        taula.getColumnModel().getColumn(8).setPreferredWidth(0);

        //Fixem el model de la taula Tenda
        JTable taulaTenda = vista.getTable2();
        taulaTenda.setModel(this.modelComponentsVisuals.getModelTaulaLocation());

        //Posem el valor al comboBox
        vista.getComboBox1().setModel(modelComponentsVisuals.getComboBoxModel());

        //Desactivem pestanyes del panel
        vista.getTabbedPane1().setEnabledAt(1, false);
        vista.getTabbedPane1().setTitleAt(1, "Tenda");

        //Forcem a que nomes es pugue seleccionar una fila de la taula
        taula.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taulaTenda.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    private void setModelTaulaStock(DefaultTableModel modelTaulaStock, List<org.example.model.entities.Sneaker> all) {
        //Omplim el model de la taula amb les dades de la coleccio
        for (org.example.model.entities.Sneaker kick : all){
            modelTaulaStock.addRow(new Object []{kick.getNomSneaker(),kick.getModelSneaker(),kick.getTalla(),kick.getPreuSortida(),kick.getPreuActual(),kick.getDataSortida(),kick.isDispo(),kick.getNumProducte(),kick});
        }
    }

    private void assignarCodiListeners() {

        ModelComponentsVisuals modelo = this.modelComponentsVisuals;

        DefaultTableModel model = modelo.getModelTaulaStock();
        DefaultTableModel modelTenda = modelo.getModelTaulaLocation();
        JTable taula = vista.getTable1();
        JTable taulaTenda = vista.getTable2();
        JButton INSERIRButton = vista.getINSERIRButton();
        JButton BORRARButton = vista.getBORRARButton();
        JButton MODIFICARButton = vista.getMODIFICARButton();
        JTextField preuActual = vista.getPreuActual();
        JTextField talla = vista.getTalla();
        JTextField nomSneaker = vista.getNomSneaker();
        JTextField modelSneaker = vista.getModelSneaker();
        JTextField dataSortida = vista.getDataSortida();
        JTextField preuSortida = vista.getPreuSortida();
        JTextField numProducte = vista.getNumProducte();
        JCheckBox dispo = vista.getDispo();
        JTabbedPane pestanyes = vista.getTabbedPane1();
        JComboBox comboBox1 = vista.getComboBox1();
        JTextField tendanom = vista.getTendanom();
        JButton modificar2 = vista.getModificarButton2();
        JButton borrar2 = vista.getBorrarButton2();
        JButton inserir2 = vista.getInserirButton2();
        JButton generar = vista.getTrobar();





        //BOTO INSERIR --------------------------
        INSERIRButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (pestanyes.getSelectedIndex() == 0) {
                            //Comprovem que totes les caselles continguen informacio
                            if (nomSneaker.getText().isEmpty() || modelSneaker.getText().isEmpty() || preuSortida.getText().isEmpty() || preuActual.getText().isEmpty() || talla.getText().isEmpty() || dataSortida.getText().isEmpty() || numProducte.getText().isEmpty()) {
                                setExcepcio(new DAOException(7));
                            } else {
                                    org.example.model.entities.Sneaker n = new org.example.model.entities.Sneaker(Double.parseDouble(preuActual.getText()), Double.parseDouble(talla.getText()), dataSortida.getText(), nomSneaker.getText(), modelSneaker.getText(), Double.parseDouble(preuSortida.getText()), numProducte.getText(), dispo.isSelected(), new ArrayList<org.example.model.entities.Sneaker.Tenda>());
                                    model.addRow(new Object[]{nomSneaker.getText(), modelSneaker.getText(), talla.getText(), preuSortida.getText(), preuActual.getText(), dataSortida.getText(), dispo.isSelected(), numProducte.getText(), n});

                               //Apliquem el metode save per guardar les dades a la BD
                                try {
                                    dadesSneaker.save(n);
                                } catch (DAOException ex) {
                                    setExcepcio(new DAOException(15));
                                }

                                //Despres de afegir posem els camps de text en blanc
                                nomSneaker.setText("");
                                modelSneaker.setText("");
                                preuSortida.setText("");
                                preuActual.setText("");
                                talla.setText("");
                                dataSortida.setText("");
                                numProducte.setText("");
                                dispo.setSelected(false);
                                nomSneaker.requestFocus(); //Inentem que el foto vaigue al camp del nom


                            }


                            //Comprovem si hi ha algun producte duplicat entre el model de producte i el numero de producte
                            for (int i = 0; i < model.getRowCount(); i++) {
                                for (int j = i + 1; j < model.getRowCount(); j++) {
                                    if (model.getValueAt(i, 1).equals(model.getValueAt(j, 1)) || (model.getValueAt(i, 7).equals(model.getValueAt(j, 7)))){
                                        setExcepcio(new DAOException(6));
                                        model.removeRow(j);

                                    }
                                }

                            }


                        } else { //Si estem a la pestanya de la tenda
                            //Obtinc el sneaker de la columna que estiga seleccionada
                            org.example.model.entities.Sneaker sn = (org.example.model.entities.Sneaker) model.getValueAt(taula.getSelectedRow(), 8);
                            org.example.model.entities.Sneaker.Tenda tenda = new org.example.model.entities.Sneaker.Tenda((org.example.model.entities.Sneaker.Tenda.Location) vista.getComboBox1().getSelectedItem(), vista.getTextField1().getText(), new TreeSet<org.example.model.entities.Sneaker.Tenda>());
                            sn.getMarket().add(tenda);
                            omplirTenda(sn, modelTenda);
                        }
//

                    }


                });

        //fem que el boto filtreButton en busque quin es el preuVenta mes baix i que te digue el model, si hi han dos preus iguals que agafe tots dos models en forma de streams
        vista.getFiltreButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Si no hi ha cap fila mostrem un missatge d'error
                if (model.getRowCount() == 0) {
                    setExcepcio(new DAOException(12));
                }else {

                    //creem un stream amb els preusVenta
                    ArrayList<Double> preus = new ArrayList<>();
                    for (int i = 0; i < model.getRowCount(); i++) {
                        preus.add(Double.parseDouble(model.getValueAt(i, 4).toString()));
                    }
                    //Busquem el preu mes baix
                    double preuMesBaix = preus.stream().min(Double::compareTo).get();
                    //Busquem els models amb el preu mes baix
                    String models = model.getDataVector().stream().filter(x -> Double.parseDouble(x.get(4).toString()) == preuMesBaix).map(x -> x.get(1).toString()).reduce("", (x, y) -> x + " " + y);
                    JOptionPane.showMessageDialog(null, "El preu mes baix es de: " + preuMesBaix + " i el model es: " + models);
                }

            }
        });






        //REGEX ------------------------------
        numProducte.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                //fem que el numero de producte sigui de 8 caracters, tots en majuscula i que acabe per un numero
                String numProducteRegex = "^[A-Z]{7}[0-9]{1}$";
                if (!numProducte.getText().matches(numProducteRegex) ||(numProducte.getText().isEmpty())) {
                    setExcepcio(new DAOException(2));
                }
            }
        });
        //Fem la talla Regex que sigue de tipus numeric i que nomes tinguue un decimal
        talla.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                String tallaRegex = "^[0-9]{1,2}(\\.[0-9]{1})?$";
                if (!talla.getText().matches(tallaRegex)|| (talla.getText().isEmpty() )) {
                    setExcepcio(new DAOException(3));
                }
            }
        });

        //Fem que el camp data siga de tipus data
        dataSortida.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                String dataRegex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d\\d)$";
                if (!dataSortida.getText().matches(dataRegex) || (dataSortida.getText().isEmpty())) {
                    setExcepcio(new DAOException(11));
                }
            }
        });




        //-----------------------------------


        //BOTO BORRAR -------------------
        BORRARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //Comprovem que hi hagi una fila seleccionada
                if (taula.getSelectedRow() != -1) {
                    //Demanem confirmacio per a borrar la fila
                    int confirmacio = JOptionPane.showConfirmDialog(null, "Estas segur de borrar la fila?","Borrar Fila",JOptionPane.YES_NO_OPTION);
                    if (confirmacio == JOptionPane.YES_OPTION) {

                        Sneaker seleccionat = (Sneaker) model.getValueAt(taula.getSelectedRow(), 8);
                        try {
                            dadesSneaker.delete(seleccionat);
                            model.removeRow(taula.getSelectedRow());
                        } catch (DAOException ex) {
                            setExcepcio(new DAOException(16));
                        }


                        //Despres de borrar posem els camps de text en blanc
                        nomSneaker.setText("");
                        modelSneaker.setText("");
                        preuSortida.setText("");
                        preuActual.setText("");
                        talla.setText("");
                        dataSortida.setText("");
                        numProducte.setText("");
                        dispo.setSelected(false);

                        //Desactivem pestanyes
                        vista.getTabbedPane1().setEnabledAt(1, false);
                        vista.getTabbedPane1().setTitleAt(1, "Tenda");
                    }
                }//Si no s'ha seleccionat cap fila mostrem un missatge d'error
                else {
                    setExcepcio(new DAOException(4));
                }
            }
        });
        //----------------------------


        //BOTO MODIFICAR -------------------
        MODIFICARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Comprovem que hi hagi una fila seleccionada
                int filasel = taula.getSelectedRow();

                if (filasel != -1) {

                    //Comprovem que totes les caselles continguen informacio
                    if (nomSneaker.getText().isEmpty() || modelSneaker.getText().isEmpty() || preuSortida.getText().isEmpty() || preuActual.getText().isEmpty() || talla.getText().isEmpty() || dataSortida.getText().isEmpty() || numProducte.getText().isEmpty()) {
                        setExcepcio(new DAOException(7));
                    } else {
                        boolean esDuplicat = false;
                        for (int i = 0; i < model.getRowCount(); i++) {
                            //Comprovem si el producte ja existeix
                            if (nomSneaker.getText().equals(model.getValueAt(i, 0)) &&
                                    modelSneaker.getText().equals(model.getValueAt(i, 1)) &&
                                    talla.getText().equals(model.getValueAt(i, 2)) &&
                                    preuSortida.getText().equals(model.getValueAt(i, 3)) &&
                                    preuActual.getText().equals(model.getValueAt(i, 4)) &&
                                    dataSortida.getText().equals(model.getValueAt(i, 5)) &&
                                    numProducte.getText().equals(model.getValueAt(i, 7))) {
                                esDuplicat = true;
                                setExcepcio(new DAOException(10));
                                break;
                            }
                        }
                        if (!esDuplicat) {


                           //Obte el objecte sneaker de la fila seleccionada
                            Sneaker seleccionat = (Sneaker) model.getValueAt(filasel, 8);

                            //Actualitza los valors de l'objecte sneaker
                            seleccionat.setNomSneaker(nomSneaker.getText());
                            seleccionat.setModelSneaker(modelSneaker.getText());
                            seleccionat.setTalla(Double.parseDouble(talla.getText()));
                            seleccionat.setPreuSortida(Double.parseDouble(preuSortida.getText()));
                            seleccionat.setPreuActual(Double.parseDouble(preuActual.getText()));
                            seleccionat.setDataSortida(dataSortida.getText());
                            seleccionat.setNumProducte(numProducte.getText());
                            seleccionat.setDispo(dispo.isSelected());

                            try{
                                //Crida al metode update
                                dadesSneaker.update(seleccionat);

                                //Si el metode update no genera cap excepcio, actualitza la fila de la taula
                                model.setValueAt(seleccionat.getNomSneaker(), filasel, 0);
                                model.setValueAt(seleccionat.getModelSneaker(), filasel, 1);
                                model.setValueAt(seleccionat.getTalla(), filasel, 2);
                                model.setValueAt(seleccionat.getPreuSortida(), filasel, 3);
                                model.setValueAt(seleccionat.getPreuActual(), filasel, 4);
                                model.setValueAt(seleccionat.getDataSortida(), filasel, 5);
                                model.setValueAt(seleccionat.isDispo(), filasel, 6);
                                model.setValueAt(seleccionat.getNumProducte(), filasel, 7);

                            } catch (DAOException ex){
                                setExcepcio(new DAOException(17));
                            }

                            //modifiquem les dades de la fila seleccionada
                            model.setValueAt(nomSneaker.getText(), filasel, 0);
                            model.setValueAt(modelSneaker.getText(), filasel, 1);
                            model.setValueAt(talla.getText(), filasel, 2);
                            model.setValueAt(preuSortida.getText(), filasel, 3);
                            model.setValueAt(preuActual.getText(), filasel, 4);
                            model.setValueAt(dataSortida.getText(), filasel, 5);
                            model.setValueAt(dispo.isSelected(), filasel, 6);
                            model.setValueAt(numProducte.getText(), filasel, 7);

                            //Actualitzem el nom de la pestanya
                            vista.getTabbedPane1().setTitleAt(1, "Disponibilitat de " + modelSneaker.getText());



                            //Neteja els camps de text
                            nomSneaker.setText("");
                            modelSneaker.setText("");
                            preuSortida.setText("");
                            preuActual.setText("");
                            talla.setText("");
                            dataSortida.setText("");
                            numProducte.setText("");
                            dispo.setSelected(false);
                            nomSneaker.requestFocus();

                        }
                    }
                } else {
                    //Si no s'ha seleccionat cap fila mostrem un missatge d'error
                    setExcepcio(new DAOException(5));
                }
            }
        });
        //-----------------------------------




        //Fem que els textfield numerics nomes accepten numeros i dos decimals
        preuActual.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE && c != KeyEvent.VK_PERIOD) {
                    e.consume();
                }
            }
        });

        talla.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE && c != KeyEvent.VK_PERIOD) {
                    e.consume();
                }
            }
        });

        preuSortida.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE && c != KeyEvent.VK_PERIOD) {
                    e.consume();
                }
            }
        });




        //fem que el camp data siga de tipus data
        dataSortida.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE && c != KeyEvent.VK_SLASH) {
                    e.consume();
                }
            }
        });



        //-----------------------------------



        taula.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                super.mouseClicked(e);

                //Al seleccionar la taula omplim els camps de text amb els valors de la fila seleccionada
                int filaSel = taula.getSelectedRow();
                if (filaSel != -1) { //Tenim una fila seleccionadaç
                    //Deshabilitem el camp de text de numProducte
                    numProducte.setEnabled(false);

                    nomSneaker.setText(model.getValueAt(filaSel, 0).toString());
                    modelSneaker.setText(model.getValueAt(filaSel, 1).toString());
                    talla.setText(model.getValueAt(filaSel, 2).toString());
                    preuSortida.setText(model.getValueAt(filaSel, 3).toString());
                    preuActual.setText(model.getValueAt(filaSel, 4).toString());
                    dataSortida.setText(model.getValueAt(filaSel, 5).toString());
                    if (model.getValueAt(filaSel, 6).toString().equals("true")) {//Agafem el valor de la casella i l'intentem convertir de forma que la checkbox agafe el valor correcte i el pugue mostrar
                        dispo.setSelected(true);
                    } else {
                        dispo.setSelected(false);
                    }
                    numProducte.setText(model.getValueAt(filaSel, 7).toString());


                    //Activem la pestanya de tenda del model de sneaker seleccionat
                    vista.getTabbedPane1().setEnabledAt(1, true);
                    vista.getTabbedPane1().setTitleAt(1, "Disponibilitat de " + modelSneaker.getText());

                    //Posem valor a el comboBox

                    vista.getComboBox1().setModel(modelo.getComboBoxModel());
                    omplirTenda((org.example.model.entities.Sneaker) model.getValueAt(filaSel, 8), modelTenda);

                } else { //Hem deseleccionat una fila
                    //Habilitem el camp de text de numProducte
                    vista.getNumProducte().setEnabled(true);


                    nomSneaker.setText("");
                    modelSneaker.setText("");
                    preuSortida.setText("");
                    preuActual.setText("");
                    talla.setText("");
                    dataSortida.setText("");
                    numProducte.setText("");
                    dispo.setSelected(false);

                    //Desactivem pestanyes
                    vista.getTabbedPane1().setEnabledAt(1, false);
                    vista.getTabbedPane1().setTitleAt(1, "Tenda");
                }

            }
        });

        //Codi de la segona pestanya
        inserir2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Comprovem que totes les caselles continguen informacio
                if (tendanom.getText().isEmpty()){
                    setExcepcio(new DAOException(7));
                }else {
                    //Afegim les dades a la taula
                    org.example.model.entities.Sneaker sn=(org.example.model.entities.Sneaker) model.getValueAt(taula.getSelectedRow(),8);
                    org.example.model.entities.Sneaker.Tenda t = new org.example.model.entities.Sneaker.Tenda((org.example.model.entities.Sneaker.Tenda.Location) vista.getComboBox1().getSelectedItem(), String.valueOf(vista.getTextField1().getText()),new TreeSet<Sneaker.Tenda>());
                    sn.getMarket().add(t);
                    omplirTenda(sn,modelTenda);

                    //Despres de afegir les dades actualitzem el objecte sneaker
                    model.setValueAt(sn,taula.getSelectedRow(),8);


                    //Despres posem els camps en blanc
                    tendanom.setText("");
                    tendanom.requestFocus(); //donem el focus al camp de text

                }

            }


        });



        taulaTenda.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                //Al seleccionar una fila de la taula de tenda posem el nom de la tenda al textfield
                int filaSel = taulaTenda.getSelectedRow();
                if (filaSel != -1) {
                    vista.getTextField1().setText(modelTenda.getValueAt(filaSel, 1).toString());
                    vista.getComboBox1().setSelectedItem(modelTenda.getValueAt(filaSel, 0));
                } else {
                    vista.getTextField1().setText("");
                }
            }
        });

        //fem que el boto borrar pregunte si volem borrar la fila cuan esta seleccionada, en cas contrari ens mostrara un missatge dient que s'ha de seleccionar una fila
        borrar2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Comprovem que hi hagi una fila seleccionada
                if (taulaTenda.getSelectedRow() != -1) {
                    //Demanem confirmacio per a borrar la fila
                    int confirmacio = JOptionPane.showConfirmDialog(null, "Estas segur de borrar la fila?","Borrar Fila",JOptionPane.YES_NO_OPTION);
                    if (confirmacio == JOptionPane.YES_OPTION) {

                        //Borrem la fila seleccionada
                        modelTenda.removeRow(taulaTenda.getSelectedRow());

                        //Actualitzem el objecte sneaker
                        org.example.model.entities.Sneaker sn = (org.example.model.entities.Sneaker) model.getValueAt(taula.getSelectedRow(),8);
                        sn.getMarket().clear();
                        for (int i = 0; i < modelTenda.getRowCount(); i++){
                            org.example.model.entities.Sneaker.Tenda t = new org.example.model.entities.Sneaker.Tenda((org.example.model.entities.Sneaker.Tenda.Location) modelTenda.getValueAt(i,0),modelTenda.getValueAt(i,1).toString(), new TreeSet<Sneaker.Tenda>());
                            sn.getMarket().add(t);
                        }
                        //Actualitzem la taula de tenda
                        omplirTenda(sn,modelTenda);


                        //Despres de borrar posem els camps de text en blanc
                        tendanom.setText("");

                    }
                }
                //Si no s'ha seleccionat cap fila mostrem un missatge d'error
                else {
                    setExcepcio(new DAOException(4));
                }
            }
        });

        //Fem que el boto trobar obrigue una nova finestra en un textield i un boto el cual compara el numero de producte del textfield
        // amb els de la base de dades i ens mostra el model de la sneaker. Tot aixo fent un del metode get de la classe SneakerDAOJDBCOracleImpl
        generar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Creem una nova finestra
                JFrame frame = new JFrame("Trobar Model");
                frame.setSize(300, 200);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                //Creem un nou textfield
                JTextField text = new JTextField();
                frame.add(text);
                text.setSize(100, 50);
                //fem el textfield visible
                text.setVisible(true);
                //fem que el textfield siga de 8 caracters, tots en majuscula i que acabe per un numero
                text.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        super.focusLost(e);
                        String numProducteRegex = "^[A-Z]{7}[0-9]{1}$";
                        if (!text.getText().matches(numProducteRegex) || (text.getText().isEmpty())) {
                            setExcepcio(new DAOException(2));
                        }
                    }
                });


                //Creem un nou boto
                JButton boto = new JButton("Trobar");
                frame.add(boto);
                //fem el boto petit
                boto.setSize(50, 50);
                //fem el boto visible
                boto.setVisible(true);

                //Fem que el boto trobar ens mostre el model de la sneaker
                boto.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Sneaker sneaker = dadesSneaker.get(text.getText());
                            if (sneaker != null) {
                                JOptionPane.showMessageDialog(null, "El model de la sneaker es: " + sneaker.getModelSneaker());
                            } else {
                                setExcepcio(new DAOException(18));
                            }
                        } catch (DAOException ex) {
                            setExcepcio(new DAOException(18));
                        }
                        text.setText("");
                    }
                });
            }
        });








        //fem que el boto modificar modifique la fila seleccionada
        modificar2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Comprovem que hi hagi una fila seleccionada
                int filasel = taulaTenda.getSelectedRow();

                if (filasel != -1) {
                    //Comprovem que totes les caselles continguen informacio
                    if (tendanom.getText().isEmpty()){
                        setExcepcio(new DAOException(7));
                    } else {
                        //Eliminem la fila seleccionada
                        modelTenda.removeRow(filasel);
                        //Inserim una nova fila amb les dades actualitzades
                        modelTenda.insertRow(filasel, new Object[]{vista.getComboBox1().getSelectedItem(),tendanom.getText()});

                        //Actualitzem el objecte sneaker
                        org.example.model.entities.Sneaker sn = (org.example.model.entities.Sneaker) model.getValueAt(taula.getSelectedRow(),8);
                        sn.getMarket().clear();
                        for (int i = 0; i < modelTenda.getRowCount(); i++){
                            org.example.model.entities.Sneaker.Tenda t = new org.example.model.entities.Sneaker.Tenda((org.example.model.entities.Sneaker.Tenda.Location) modelTenda.getValueAt(i,0),modelTenda.getValueAt(i,1).toString(), new TreeSet<Sneaker.Tenda>());
                            sn.getMarket().add(t);
                        }
                        //Actualitzem la taula de tenda
                        omplirTenda(sn,modelTenda);


                        //Neteja els camps de text
                        tendanom.setText("");
                        }
                    }
                else {
                    //Si no s'ha seleccionat cap fila mostrem un missatge d'error
                    setExcepcio(new DAOException(5));
                }
            }

        });
    }




    private static void omplirTenda(org.example.model.entities.Sneaker sn, DefaultTableModel modelTenda){
        //Omplim el model de la taula de tenda
        modelTenda.setRowCount(0);

        //Plenem la taula amb les dades de la coleccion
        for (org.example.model.entities.Sneaker.Tenda tenda : sn.getMarket()){
            modelTenda.addRow(new Object[]{tenda.getLocation(),tenda.getNomTenda()});
        }
    }


    //TRACTAMENT D'EXCEPCIONS


    //Propietat lligada per controlar quan genero una excepcio
    public static final String PROP_EXCEPCIO = "excepcio";
    private DAOException excepcio;

    public DAOException getExcepcio() {
        return excepcio;
    }

    public void setExcepcio(DAOException excepcio){
        DAOException oldExcepcio = this.excepcio;
        this.excepcio = excepcio;
        change.firePropertyChange(PROP_EXCEPCIO, oldExcepcio, excepcio);

    }

    //Propietat propertyChangesupport necessaria per poder controlar les propietats lligades
    PropertyChangeSupport change = new PropertyChangeSupport(this);

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        DAOException rebuda = (DAOException) evt.getNewValue();

        try{
            throw rebuda;
        }catch (DAOException e){
            //Aqui farem el tractament de les excepcions de l'aplicacio
            switch (evt.getPropertyName()){
                case PROP_EXCEPCIO:

                    switch (rebuda.getTipo()){
                        case 1:
                            JOptionPane.showMessageDialog(null,rebuda.getMessage());
                            //En cas de que salte la excepcio, borrem el text del camp de text
                            vista.getNumProducte().setText("");
                        case 2:
                            JOptionPane.showMessageDialog(null,rebuda.getMessage());
                            //En cas de que salte la excepcio, borrem el text del camp de text
                            vista.getTalla().setText("");
                            break;
                        case 3, 4, 5, 6, 7, 8, 9, 17, 16, 15, 14, 13, 12, 10, 18, 20:
                            JOptionPane.showMessageDialog(null,rebuda.getMessage());
                            break;
                            case 11:
                            JOptionPane.showMessageDialog(null,rebuda.getMessage());
                            vista.getDataSortida().setText("");
                        case 19:
                            JOptionPane.showMessageDialog(null,rebuda.getMessage());
                            vista.getNumProducte().setText("");
                            break;


                    }
            }
        }
    }

}
