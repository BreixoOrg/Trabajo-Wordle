/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daw1.breixo.GestorFicheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Breixo
 */


public class GestorFicherosTXT implements IGestorDatos {

    private java.util.Set<String> setPalabras = new java.util.HashSet<>();

    private static File FICHERO;

    public GestorFicherosTXT(File fichero) {
        FICHERO = fichero;
        cargarPalabrasFileToSet();
    }
    
    

    @Override
    public String cargarPalabraAleatoria() {

        if (!FICHERO.exists()) {
            crearFichero();
        }

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
    public boolean guardarPalabra(String palabra) throws IOException {

        palabra = palabra.toLowerCase().trim();

        if (checkPalabra(palabra)) {

            if (!FICHERO.exists()) {
                crearFichero();
            }

            try (Writer wr = new BufferedWriter(new FileWriter(FICHERO,true))) {
                wr.write(palabra + "\n");
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean eliminarPalabra(String palabra) {

        palabra = palabra.toLowerCase().trim();

        if (checkPalabra(palabra)) {

            if (!FICHERO.exists()) {
                crearFichero();
            }

            if (setPalabras.contains(palabra)) {

                if (setPalabras.remove(palabra)) {
                    if(cargarPalabrasSetToFile()){
                        return true;
                    }
                }

            }
        }

        return false;

    }

    @Override
    public int comprobarCaracter(int posicion, String palabraProg, String palabraUser) {
        
        palabraProg = palabraProg.toLowerCase().trim();
        palabraUser = palabraUser.toLowerCase().trim();
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
        palabra = palabra.toLowerCase().trim();
        return setPalabras.contains(palabra);
    }

    private boolean cargarPalabrasFileToSet() {

        try (BufferedReader br = new BufferedReader(new FileReader(FICHERO))) {
            String linea = br.readLine();
            while (linea != null) {
                setPalabras.add(linea);
                linea = br.readLine();
            }
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return false;
    }

    private void crearFichero() {

        FICHERO.getParentFile().mkdirs();
        try {
            FICHERO.createNewFile();
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }

    private boolean checkPalabra(String palabra) {

        palabra = palabra.toLowerCase().trim();
        if (palabra.matches("[^a-z]{5}")) {
            return false;
        }
        return true;

    }
    
    
    private boolean cargarPalabrasSetToFile() {

        if (!FICHERO.exists()) {
            crearFichero();
        }

        try (Writer wr = new BufferedWriter(new FileWriter(FICHERO))) {
            wr.write("");
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        try (Writer wr = new BufferedWriter(new FileWriter(FICHERO, true))) {

            StringBuilder sb = new StringBuilder();

            Iterator it = setPalabras.iterator();

            while (it.hasNext()) {

                String aux = (String) it.next();

                sb.append(aux).append("\n");
            }

            wr.write(sb.toString());
            
            return true;

        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return false;

    }

}
