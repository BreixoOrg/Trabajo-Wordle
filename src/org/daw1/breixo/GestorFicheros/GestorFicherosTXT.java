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
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Breixo
 */


public class GestorFicherosTXT implements IGestorDatos {

    java.util.Set<String> setPalabras = new java.util.HashSet<>();

    File FICHERO = new File(Path.of(".") + File.separator + "data" + File.separator + "palabras.txt");

    @Override
    public String cargarPalabraAleatoria() {

        if (!FICHERO.exists()) {
            crearFichero();
        }

        cargarPalabrasEnSet();

        String palabra = "";

        java.util.Random genNum = new java.util.Random();

        Iterator it = setPalabras.iterator();

        int numPalabraSeleccionada = genNum.nextInt(setPalabras.size());
        int contador = 0;

        while (contador <= numPalabraSeleccionada) {
            palabra = (String) it.next();
        }

        return palabra;
    }

    @Override
    public boolean guardarPalabra(String palabra) {

        palabra = palabra.toLowerCase().trim();

        if (checkPalabra(palabra)) {

            if (!FICHERO.exists()) {
                crearFichero();
            }

            try (Writer wr = new BufferedWriter(new FileWriter(FICHERO))) {
                wr.write(palabra);
                return true;
            } catch (IOException ex) {
                Logger.getLogger(GestorFicherosTXT.class.getName()).log(Level.SEVERE, null, ex);
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

            cargarPalabrasEnSet();

            if (setPalabras.contains(palabra)) {

                if (setPalabras.remove(palabra)) {
                    return true;
                }

            }
        }

        return false;

    }

    @Override
    public int comprobarCaracter(int posicion, String palabraProg, String palabraUser) {

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

    private boolean cargarPalabrasEnSet() {

        try (BufferedReader br = new BufferedReader(new FileReader(FICHERO))) {
            String linea = br.readLine();
            while (linea != null) {
                setPalabras.add(linea);
                linea = br.readLine();
            }
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestorFicherosTXT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestorFicherosTXT.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void crearFichero() {

        FICHERO.getParentFile().mkdirs();
        try {
            FICHERO.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(GestorFicherosTXT.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean checkPalabra(String palabra) {

        if (palabra.matches("[^a-z]{5}")) {
            return false;
        }
        return true;

    }

}
