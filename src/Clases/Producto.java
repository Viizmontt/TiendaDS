/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author HAZAEL
 */
public class Producto {

    public String CodBarra;
    public String nombre;
    public double inventario;
    public double costo;
    public int idSucursal;
    
    public Producto(String CodBarra, String nombre, int inventario, double costo ){
        this.CodBarra = CodBarra;
        this.nombre = nombre;
        this.inventario = inventario;
        this.costo = costo;
    }

    public Producto(String CodBarra, String nombre, double inventario, double costo, int idSucursal) {
        this.CodBarra = CodBarra;
        this.nombre = nombre;
        this.inventario = inventario;
        this.costo = costo;
        this.idSucursal = idSucursal;
    }
    
    
    public Producto(){}
    
}
