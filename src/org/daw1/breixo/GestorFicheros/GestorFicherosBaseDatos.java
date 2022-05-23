/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daw1.breixo.GestorFicheros;

import java.sql.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Breixo
 */
public class GestorFicherosBaseDatos implements IGestorDatos{
    
    private static String idioma;
    
    private java.util.Set<String> setPalabras = new java.util.HashSet<>();
    
    private static final String URL = "jdbc:sqlite:data/dbwordle.db";

    public GestorFicherosBaseDatos(String idioma) {
        this.idioma = idioma;
    }
    
    

    @Override
    public String cargarPalabraAleatoria() throws SQLException {

        String palabraAleatoria = "xxxxx";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement statement = conn.prepareStatement("SELECT palabra FROM palabras WHERE lang= ? ORDER BY RANDOM() LIMIT 1")) {

            statement.setString(1, idioma);

            try (ResultSet rs = statement.executeQuery()) {
                palabraAleatoria = rs.getString("palabra");
                return palabraAleatoria;
            }
        }

    }

    @Override
    public boolean guardarPalabra(String palabra) throws  SQLException{
        
        try(Connection conn = DriverManager.getConnection(URL);
            PreparedStatement statement = conn.prepareStatement("INSERT INTO palabras (palabra,lang) VALUES(?,?)")){
                
                statement.setString(1,palabra.toUpperCase().trim());
                statement.setString(2,idioma);
                int insertadas = statement.executeUpdate();
                return insertadas > 0;
        }
        
    }

    @Override
    public boolean eliminarPalabra(String palabra) throws  SQLException {
        try(Connection conn = DriverManager.getConnection(URL);
            PreparedStatement statement = conn.prepareStatement("DELETE FROM palabras WHERE palabra=?")){
                
                statement.setString(1,palabra.toUpperCase().trim());
                int borradas = statement.executeUpdate();
                return borradas > 0;
            
        }
    }

    @Override
    public int comprobarCaracter(int posicion, String palabraProg, String palabraUser) {
        
        palabraProg = palabraProg.toUpperCase().trim();
        palabraUser = palabraUser.toUpperCase().trim();
        char letra = palabraUser.charAt(posicion);

        if (palabraProg.contains(letra + "")) {

            if (palabraProg.charAt(posicion) == letra) {
                return 1;
            }

            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public boolean existePalabra(String palabra) {
        cargarPalabrasFileToSet();
        palabra = palabra.toUpperCase().trim();
        return setPalabras.contains(palabra);
    }
    
    private void cargarPalabrasFileToSet() {

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement consulta = conn.prepareStatement("SELECT palabra FROM palabras WHERE lang = ?")) {

            consulta.setString(1, idioma);
            try (ResultSet rs = consulta.executeQuery()) {

                while (rs.next()) {
                    String word = rs.getString("palabra");
                    setPalabras.add(word);
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    
    private boolean checkPalabra(String palabra) {

        palabra = palabra.toUpperCase().trim();
        if (palabra.matches("[^A-Z]{5}")) {
            return false;
        }
        return true;

    }
    
}
