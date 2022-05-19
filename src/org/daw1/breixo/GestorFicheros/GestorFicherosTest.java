/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daw1.breixo.GestorFicheros;

/**
 *
 * @author Breixo
 */
public class GestorFicherosTest implements IGestorDatos{

    @Override
    public String cargarPalabraAleatoria() {
        return "ciclo";
    }

    @Override
    public boolean guardarPalabra(String palabra) {
        return true;
    }

    @Override
    public boolean eliminarPalabra(String palabra) {
        return true;
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

    @Override
    public boolean existePalabra(String palabra) {
        return true;
    }
    
}
