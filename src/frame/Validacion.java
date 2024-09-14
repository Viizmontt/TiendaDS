/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import java.awt.event.KeyEvent;

/**
 *
 * @author Roger Alvarez
 */
public class Validacion {

    Character s;

    public void SoloLetras(KeyEvent evt) {
        s = evt.getKeyChar();
        if (!Character.isLetter(s) && s != KeyEvent.VK_SPACE) {
            evt.consume();
        }
    }

    public void Numeros(KeyEvent evt) {
        s = evt.getKeyChar();
        if (!Character.isDigit(s)) {
            evt.consume();
        }

    }

    public void Nit(KeyEvent evt, int longitud) {
        s = evt.getKeyChar();
        if (!Character.isDigit(s) || longitud > 13) {
            evt.consume();
        }

    }

    public void NRC(KeyEvent evt, int longitud) {
        s = evt.getKeyChar();
        if (!Character.isDigit(s) || longitud > 6) {
            evt.consume();
        }

    }

    public void Tel(KeyEvent evt, int longitud) {
        s = evt.getKeyChar();
        if (!Character.isDigit(s) || longitud > 7) {
            evt.consume();
        }

    }

    public void Nombre(KeyEvent evt, int longitud) {
        s = evt.getKeyChar();
        if (!Character.isLetter(s) && s != KeyEvent.VK_SPACE || longitud > 30) {
            evt.consume();
        }
    }

    public void Direccion(KeyEvent evt, int longitud) {
        if (longitud > 99) {
            evt.consume();
        }
    }

    public void CodigoBarra(KeyEvent evt, int longitud) {
        if (longitud > 12) {
            evt.consume();
        }
    }
    
    public void Email(KeyEvent evt, int longitud) {
        if (longitud > 99) {
            evt.consume();
        }
    }

    public void NombreProducto(KeyEvent evt, int longitud) {
        if (longitud > 29) {
            evt.consume();
        }
    }

    public void ProductoInventario(KeyEvent evt, int longitud) {
        s = evt.getKeyChar();
        if (!Character.isDigit(s) || longitud > 10) {
            evt.consume();
        }
    }

    public void CostoProducto(KeyEvent evt,String x) {
        char caracter = evt.getKeyChar();
                if(((caracter < '0') || (caracter > '9')) && (caracter != KeyEvent.VK_BACK_SPACE)
                                && (caracter !='.')){
                
                evt.consume();
                
                }
                if (caracter == '.' && x.contains(".")) {
                        evt.consume();
                    }
                
    }

    public void ClienteVenta(KeyEvent evt, int longitud) {
        if (longitud > 99) {
            evt.consume();
        }
    }
    
    public void LongitudLetras(KeyEvent evt, int longitud, int longitudDeseada) {
        s = evt.getKeyChar();
        if (!Character.isLetter(s) || longitud > longitudDeseada) {
            evt.consume();
        }
    }
}
