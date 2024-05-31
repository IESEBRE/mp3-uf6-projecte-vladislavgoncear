package org.example.model;

import org.example.model.daos.DAO;
import org.example.model.entities.Sneaker;
import org.example.model.exceptions.DAOException;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class SneakerDAOJDBCOracleImpl implements DAO<Sneaker> {
    //Cridem al metode per crear la taula si no existeix
    public SneakerDAOJDBCOracleImpl() throws DAOException {
       createTables();
    }

    /**
     * Obtiene una sneaker de la base de datos a partir de su número de producto.
     *
     * @param numProducte Número de producto de la sneaker a obtener.
     * @return La sneaker con el número de producto especificado o null si no se encuentra.
     * @throws DAOException Si se produce un error al interactuar con la base de datos.
     */
    @Override
    public Sneaker get(String numProducte) throws DAOException {
        Sneaker sneaker = null;

        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/connexio.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new DAOException(19);
        }

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement st = con.prepareStatement("SELECT * FROM SNEAKERS WHERE prod_id = ?")
        ) {
            st.setString(1, numProducte);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    sneaker = new Sneaker();
                    sneaker.setNumProducte(rs.getString("prod_id"));
                    sneaker.setNomSneaker(rs.getString("marca"));
                    sneaker.setModelSneaker(rs.getString("model"));
                    sneaker.setTalla(rs.getDouble("talla"));
                    sneaker.setPreuActual(rs.getDouble("preu_actual"));
                    sneaker.setPreuSortida(rs.getDouble("preu_sortida"));
                    sneaker.setDataSortida(rs.getString("data_sortida"));
                    sneaker.setDispo(rs.getInt("disponible") == 1);
                }
            }
        } catch (SQLException throwables) {
            int tipoError = throwables.getErrorCode();
            switch(throwables.getErrorCode()){
                case 17002: //l'he obtingut posant un sout en el throwables.getErrorCode()
                    tipoError = 0;
                    break;
                default:
                    tipoError = 1;  //error desconegut
            }
            throw new DAOException(tipoError);
        }

        return sneaker;
    }

    /**
     * Obtiene todas las sneakers de la base de datos.
     *
     * @return Una lista con todas las sneakers de la base de datos.
     * @throws DAOException Si se produce un error al interactuar con la base de datos.
     */
    @Override
    public List<Sneaker> getAll() throws DAOException {
        List<Sneaker> sneakers = new ArrayList<>();

        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/connexio.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new DAOException(19);
        }

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement st = con.prepareStatement("SELECT * FROM SNEAKERS");
             ResultSet rs = st.executeQuery();
        ) {
            while (rs.next()) {
                Sneaker sneaker = new Sneaker();
                sneaker.setNumProducte(rs.getString("prod_id"));
                sneaker.setNomSneaker(rs.getString("marca"));
                sneaker.setModelSneaker(rs.getString("model"));
                sneaker.setTalla(rs.getDouble("talla"));
                sneaker.setPreuActual(rs.getDouble("preu_actual"));
                sneaker.setPreuSortida(rs.getDouble("preu_sortida"));
                sneaker.setDataSortida(rs.getString("data_sortida"));
                sneaker.setDispo(rs.getInt("disponible") == 1);
                sneakers.add(sneaker);
            }
        } catch (SQLException throwables) {
            int tipoError = throwables.getErrorCode();
            switch(throwables.getErrorCode()){
                case 17002: //l'he obtingut posant un sout en el throwables.getErrorCode()
                    tipoError = 0;
                    break;
                default:
                    tipoError = 1;  //error desconegut
            }
            throw new DAOException(tipoError);
        }
        return sneakers;
    }

    /**
     * Guarda una sneaker en la base de datos.
     *
     * @param obj La sneaker a guardar.
     * @throws DAOException Si se produce un error al interactuar con la base de datos.
     */
    @Override
    public void save(Sneaker obj) throws DAOException {
        //Cridem al metode per crear la taula si no existeix
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/connexio.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new DAOException(19);
        }

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement st = con.prepareStatement("INSERT INTO SNEAKERS (prod_id, marca, model, talla, preu_actual, preu_sortida, data_sortida, disponible) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
        ) {
            st.setString(1, obj.getNumProducte());
            st.setString(2, obj.getNomSneaker());
            st.setString(3, obj.getModelSneaker());
            st.setDouble(4, obj.getTalla());
            st.setDouble(5, obj.getPreuActual());
            st.setDouble(6, obj.getPreuSortida());
            st.setString(7, obj.getDataSortida());
            st.setInt(8, obj.isDispo() ? 1 : 0);

            int rowsAffected = st.executeUpdate(); // Este método devuelve el número de filas afectadas por la consulta SQL
            if (rowsAffected == 0) {
                // Si no se afectaron filas, significa que la inserción no tuvo éxito
                throw new DAOException(1); // Puedes cambiar el código de error según tus necesidades
            }
        } catch (SQLException throwables) {
            int tipoError = throwables.getErrorCode();
            switch(throwables.getErrorCode()){
                case 17002: //l'he obtingut posant un sout en el throwables.getErrorCode()
                    tipoError = 0;
                    break;
                default:
                    tipoError = 1;  //error desconegut
            }
            throw new DAOException(tipoError);
        }
    }

    /**
     * Actualiza una sneaker en la base de datos.
     *
     * @param obj La sneaker a actualizar.
     * @throws DAOException Si se produce un error al interactuar con la base de datos.
     */
    @Override
    public void update(Sneaker obj) throws DAOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/connexio.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new DAOException(19);
        }

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement st = con.prepareStatement("UPDATE SNEAKERS SET marca = ?, model = ?, talla = ?, preu_actual = ?, preu_sortida = ?, data_sortida = ?, disponible = ? WHERE prod_id = ?")
        ) {
            st.setString(1, obj.getNomSneaker());
            st.setString(2, obj.getModelSneaker());
            st.setDouble(3, obj.getTalla());
            st.setDouble(4, obj.getPreuActual());
            st.setDouble(5, obj.getPreuSortida());
            st.setString(6, obj.getDataSortida());
            st.setInt(7, obj.isDispo() ? 1 : 0);
            st.setString(8, obj.getNumProducte());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) {
                // Si no se afectaron filas, significa que la actualización no tuvo éxito
                throw new DAOException(1); // Puedes cambiar el código de error según tus necesidades
            }
        } catch (SQLException throwables) {
            int tipoError = throwables.getErrorCode();
            switch(throwables.getErrorCode()){
                case 17002: //l'he obtingut posant un sout en el throwables.getErrorCode()
                    tipoError = 0;
                    break;
                default:
                    tipoError = 1;  //error desconegut
            }
            throw new DAOException(tipoError);
        }
    }

    /**
     * Elimina una sneaker de la base de datos.
     *
     * @param obj La sneaker a eliminar.
     * @throws DAOException Si se produce un error al interactuar con la base de datos.
     */
    @Override
    public void delete(Sneaker obj) throws DAOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/connexio.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new DAOException(19);
        }

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement st = con.prepareStatement("DELETE FROM SNEAKERS WHERE prod_id = ?")
        ) {
            st.setString(1, obj.getNumProducte());

            int rowsAffected = st.executeUpdate(); // Este método devuelve el número de filas afectadas por la consulta SQL
            if (rowsAffected == 0) {
                // Si no se afectaron filas, significa que la eliminación no tuvo éxito
                throw new DAOException(1); // Puedes cambiar el código de error según tus necesidades
            }
        } catch (SQLException throwables) {
            int tipoError = throwables.getErrorCode();
            switch(throwables.getErrorCode()){
                case 17002: //l'he obtingut posant un sout en el throwables.getErrorCode()
                    tipoError = 0;
                    break;
                default:
                    tipoError = 1;  //error desconegut
            }
            throw new DAOException(tipoError);
        }
    }

    //Metode per crear les taules i el trigger si no ho tenim a la base de dades
    public static void createTables() throws DAOException {
        ResourceBundle rb = ResourceBundle.getBundle("connexio");

        String url = rb.getString("jdbc.url");
        String user = rb.getString("jdbc.username");
        String password = rb.getString("jdbc.password");
        String driver = rb.getString("jdbc.driver");

        //Declaració de variables del mètode
        Connection con = null;
        Statement st = null;

        //Accés a la BD usant l'API JDBC
        try {
            Class.forName(driver);

            con = DriverManager.getConnection(
                    url,
                    user,
                    password
            );
            st = con.createStatement();

            // Llegeix el contingut del fitxer pl.sql des del directori de recursos
            String script = readScript("/pl.sql");

            // Divideix el script en sentències individuals si és necessari
            String[] sqlStatements = script.split("/");

            // Executa cada sentència SQL
            for (String sql : sqlStatements) {
                if (!sql.trim().isEmpty()) {
                    st.execute(sql);
                }
            }

            // Llama al procedimiento almacenado
            try (CallableStatement cst = con.prepareCall("{call crear_taula_sneakers}")) {
                cst.execute();
            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new DAOException(1);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new DAOException(2295);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new DAOException(1);
        } finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw new DAOException(1);
            }
        }
    }

    /**
     * Llegeix el contingut d'un fitxer i el retorna com una cadena
     * @param resourcePath la ruta del fitxer
     * @return el contingut del fitxer com una cadena
     * @throws IOException si es produeix un error en la lectura del fitxer
     * */
// Mètode per llegir el contingut d'un fitxer i retornar-lo com una cadena
    private static String readScript(String resourcePath) throws IOException {
        StringBuilder script = new StringBuilder();
        // Llegeix el fitxer línia per línia
        try (InputStream inputStream = SneakerDAOJDBCOracleImpl.class.getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            // Llegeix cada línia del fitxer i l'afegeix a la cadena de text
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }
        }
        return script.toString();
    }

}
