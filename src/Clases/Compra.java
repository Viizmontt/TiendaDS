/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import connections.conection;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author HAZAEL
 */
public class Compra {

    public int idCompra;
    public Date fecha;
    public Proveedor proveedor;
    public int idSucursal;
    public String tipoCompra;
    public String numDocumento;
    public double subTotal;
    public double IVA;
    public double percepcion;
    public double total;
    public ArrayList<DetalleCompra> articulo;

    public Compra() {

    }

    public Compra(int idCompra, Date fecha, Proveedor proveedor, double total) {
        this.idCompra = idCompra;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.total = total;
    }

    

    public double CalcularSubTotal() throws ErrorTienda {
        double total1 = 0;
        try {
            for (DetalleCompra dc : this.articulo) {
                total1 = total1 + (dc.cantidad * dc.costoUnitario);
            }
            this.subTotal = Math.round(total1 * 100.0) / 100.0;
        } catch (ArithmeticException ex) {
            throw new ErrorTienda("Class Compra/CalcularTotal", ex.getMessage());
        }
        return this.subTotal;
    }    
    public double CalcularIVA() throws ErrorTienda {
        CalcularSubTotal();
        double iva = 0;
        iva=subTotal*0.13;
        this.IVA = Math.round(iva * 100.0) / 100.0;
        return this.IVA;
    }
      public double CalcularTotal() throws ErrorTienda {
        CalcularSubTotal();
        CalcularIVA();
        double total = 0;
        total=subTotal+IVA;
        this.total = Math.round(total * 100.0) / 100.0;
        return this.total;
    }
    
    

    public ArrayList<DetalleCompra> AgregarItem(DetalleCompra detalleCompra) throws ErrorTienda {

        try {
            
            this.articulo.add(detalleCompra);
            this.CalcularTotal();

        } catch (Exception ex) {
            throw new ErrorTienda("Class Compra/AgregarItem", ex.getMessage());
        }
        return this.articulo;
    }

    public Compra(int idCompra, Date fecha, Proveedor proveedor, int idSucursal, String tipoCompra, String numDocumento, double subTotal, double IVA, double percepcion, double total) {
        this.idCompra = idCompra;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.idSucursal = idSucursal;
        this.tipoCompra = tipoCompra;
        this.numDocumento = numDocumento;
        this.subTotal = subTotal;
        this.IVA = IVA;
        this.percepcion = percepcion;
        this.total = total;
    }
    
    
}
