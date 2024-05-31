package org.example.model.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;

public class Sneaker implements Serializable {
    private Long id;
    private double preuActual;
    private double talla;
    private String dataSortida;
    private String nomSneaker;
    private String modelSneaker;
    private double preuSortida;
    private String numProducte;
    private boolean dispo;

    private Collection<Tenda> market;


    public Sneaker(){
        this.market = new TreeSet<>();

    }
    public Sneaker (double preuActual, double talla, String dataSortida, String nomSneaker, String modelSneaker, double preuSortida, String numProducte,boolean dispo, Collection<Tenda> tenda) {
        this.preuActual = preuActual;
        this.talla = talla;
        this.dataSortida = dataSortida;
        this.nomSneaker = nomSneaker;
        this.modelSneaker = modelSneaker;
        this.preuSortida = preuSortida;
        this.numProducte = numProducte;
        this.dispo = dispo;
        this.market = tenda;
    }


    public Collection<Sneaker.Tenda> getMarket() {
        return this.market;
    }

    void setMarket(Collection<Tenda> market) {
        this.market = market;
    }


    public String getNomSneaker() {
        return nomSneaker;
    }
    public void setNomSneaker(String nomSneaker) {
        this.nomSneaker = nomSneaker;
    }
    public String getModelSneaker() {
        return modelSneaker;
    }
    public void setModelSneaker(String modelSneaker) {
        this.modelSneaker = modelSneaker;
    }
    public double getPreuActual() {
        return preuActual;
    }
    public void setPreuActual(double preuActual) {
        this.preuActual = preuActual;
    }
    public double getTalla() {
        return talla;
    }
    public void setTalla(double talla) {
        this.talla = talla;
    }
    public String getDataSortida() {
        return dataSortida;
    }
    public void setDataSortida(String dataSortida) {
        this.dataSortida = dataSortida;
    }
    public double getPreuSortida() {
        return preuSortida;
    }
    public void setPreuSortida(double preuSortida) {
        this.preuSortida = preuSortida;
    }
    public String getNumProducte() {
        return numProducte;
    }
    public void setNumProducte(String numProducte) {
        this.numProducte = numProducte;
    }
    public boolean isDispo() {
        return dispo;
    }
    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }







    public static class Tenda implements Serializable, Comparable<Tenda>{

        @Override
        public int compareTo(Tenda o) {
            return this.Location.compareTo(o.getLocation());
        }

        public static enum Location {
            SPAIN("Barcelona"),
            FRANCE("Paris"),
            UK("Londres");
            private String nom;

            private Location(String nom) {
                this.nom = nom;
            }
            public String getNom() {
                return nom;
            }
            @Override
            public String toString() {
                return this.name() + " - " + nom;
            }
        }
        private Tenda.Location Location;
        private String nomTenda;

        public Tenda(Tenda.Location location, String nomTenda, TreeSet<Tenda> tendas) {
            this.Location = location;
            this.nomTenda = nomTenda;
        }
        public Tenda.Location getLocation() {
            return Location;
        }
        public void setLocation(Tenda.Location location) {
            this.Location = location;
        }
        public String getNomTenda() {
            return nomTenda;
        }
        public void setNomTenda(String nomTenda) {
            this.nomTenda = nomTenda;
        }
    }


}
