package org.example.model.daos;

import org.example.model.exceptions.DAOException;



import java.util.List;

public interface DAO <T>{
    T get(String numProducte) throws DAOException;
    List<T> getAll() throws DAOException;
    void save(T obj) throws DAOException;
    void update(T obj) throws DAOException;
    void delete(T obj) throws DAOException;


    //Tots els mètodes necessaris per interactuar en la BD


}

