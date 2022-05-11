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
public abstract interface IGestorFicheros {
    
    public String cargarPalabraAleatoria();
    
    public boolean guardarPalabra(String palabra);
    
    public boolean eliminarPalabra(String palabra);
    
    /**
     * 
     * @param posicion Posición de la letra. Empieza en 1
     * @param palabraProg Palabra del programa
     * @param palabraUser Palabra del usuario
     * @return Devuelve un int. 1 si está bien, 0 si se encuentra en la palabra pero mal posicionada y -1 si no existe la letra
     */
    
    public int comprobarCaracter(int  posicion, String palabraProg, String palabraUser);
    
}
