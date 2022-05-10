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
    
}
