/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author HAZAEL
 */

public class DetalleVenta {
    
  public Producto producto;
  public double cantidad;
  public double PrecioUnitario;

  DecimalFormat decimal = new DecimalFormat("0.00");
  public DetalleVenta(){
        
    }

  public DetalleVenta(Producto producto, double cantidad, double PrecioUnitario){
      this.PrecioUnitario = PrecioUnitario;
      this.cantidad = cantidad;
      this.producto = producto;
  }
 
 
}
