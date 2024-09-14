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
public class Proveedor {
    public int idProveedor;
    public String nombre;
    public String telefono;
    public String direccion;
    public String nit;
    public String email;
    public String NRC;
    
    public Proveedor(){
    
    }

    public Proveedor(int idProveedor, String nombre, String telefono, String direccion, String nit,  String NRC, String email) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.nit = nit;
        this.email = email;
        this.NRC = NRC;
    }

    
    
    
}