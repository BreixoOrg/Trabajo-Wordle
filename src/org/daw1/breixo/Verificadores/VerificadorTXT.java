/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daw1.breixo.Verificadores;

/**
 *
 * @author Breixo
 */
public class VerificadorTXT implements IVerificador{

    
    
    @Override
    public int comprobarCaracter(int posicion, String palabraProg, String palabraUser) {
        
        char letra = palabraUser.charAt(posicion - 1);
        
        if(palabraProg.contains(letra + "")){
        
            if(palabraProg.charAt(posicion- 1) == letra){
                return 1;
            }
            
            return 0;
        }
        else{
            return -1;
        }
        
    }
    
    
}
