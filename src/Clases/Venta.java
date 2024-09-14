/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author HAZAEL
 */
public class Venta {
    
    public int idVenta;
    public int idSucursal;
    public String tipoVenta;
    public int idTipoPrecio;
    public double IVA;
    public double totalGrabado;
    public String direccion;
    public String giro;
    public String NIT;
    public String NRC;
    public String numDocumento;
    public Date fecha;
    public String cliente;
    public double total;
    public ArrayList<DetalleVenta> articulo;
    DecimalFormat decimal = new DecimalFormat("0.00");
    public Venta(){
        
    }
    
    public Venta(int idVenta, Date fecha, String cliente, double total) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.cliente = cliente;
        this.total = total;
    }
    
    public void AgregarItem( DetalleVenta detalleVenta) throws ErrorTienda{
        articulo = new ArrayList<DetalleVenta>();
        articulo.add(detalleVenta);
        CalcularTotal();
    }
    
    public double CalcularTotalGrabado() throws ErrorTienda{
        double total=0;
        try{
            for(DetalleVenta v: this.articulo){
            total=total+((v.PrecioUnitario)*(v.cantidad));
        }
        this.totalGrabado=total;
        decimal.setRoundingMode(RoundingMode.CEILING);
        return Double.parseDouble(decimal.format(total));
        }catch(ArithmeticException ex){
            throw new ErrorTienda("Error CalcularTotalGrabado", ex.getMessage());
}        
    }
        public double CalcularIVA() throws ErrorTienda{
        CalcularTotalGrabado();
        double iva;
        iva=totalGrabado*0.13;
        this.IVA=iva;
        decimal.setRoundingMode(RoundingMode.CEILING);
        return Double.parseDouble(decimal.format(iva));
        }
        
        public double CalcularTotal() throws ErrorTienda{
        CalcularTotalGrabado(); 
        CalcularIVA();
        double total;
        total=totalGrabado+IVA;
        this.total=total;
        decimal.setRoundingMode(RoundingMode.CEILING);
        return Double.parseDouble(decimal.format(total));
    }

    public Venta(int idVenta, int idSucursal, String tipoVenta, int idTipoPrecio, double IVA, double totalGrabado, String direccion, String giro, String NIT, String NRC, String numDocumento, Date fecha, String cliente, double total, ArrayList<DetalleVenta> articulo) {
        this.idVenta = idVenta;
        this.idSucursal = idSucursal;
        this.tipoVenta = tipoVenta;
        this.idTipoPrecio = idTipoPrecio;
        this.IVA = IVA;
        this.totalGrabado = totalGrabado;
        this.direccion = direccion;
        this.giro = giro;
        this.NIT = NIT;
        this.NRC = NRC;
        this.numDocumento = numDocumento;
        this.fecha = fecha;
        this.cliente = cliente;
        this.total = total;
        this.articulo = articulo;
    }
        
        
}
