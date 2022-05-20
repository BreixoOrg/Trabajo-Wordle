/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daw1.breixo.GestorFicheros;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        cargarPalabrasFileToSet();
    }
    
    

    @Override
    public String cargarPalabraAleatoria() {
        
        String palabra = "";

        java.util.Random genNum = new java.util.Random();

        Iterator it = setPalabras.iterator();

        int numPalabraSeleccionada = genNum.nextInt(setPalabras.size());
        int contador = 0;

        while (contador <= numPalabraSeleccionada) {
            palabra = (String) it.next();
            contador++;
        }

        return palabra;

    }

    @Override
    public boolean guardarPalabra(String palabra) {
        
        try(Connection conn = DriverManager.getConnection(URL);
            PreparedStatement statement = conn.prepareStatement("INSERT INTO palabras (palabra,lang) VALUES(?,?)")){
                
                statement.setString(1,palabra.toUpperCase().trim());
                statement.setString(2,idioma);
                int insertadas = statement.executeUpdate();
                setPalabras.add(palabra);
                return insertadas > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(GestorFicherosBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminarPalabra(String palabra) {
        try(Connection conn = DriverManager.getConnection(URL);
            PreparedStatement statement = conn.prepareStatement("DELETE FROM palabras WHERE palabra=?")){
                
                statement.setString(1,palabra.toUpperCase().trim());
                int borradas = statement.executeUpdate();
                setPalabras.remove(palabra);
                return borradas > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(GestorFicherosBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public int comprobarCaracter(int posicion, String palabraProg, String palabraUser) {
        
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
            Logger.getLogger(GestorFicherosBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
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
