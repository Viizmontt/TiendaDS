/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import connections.ListasTablas;
import connections.conection;
import connections.iList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HAZAEL
 */
public class ControladorCompra {

    static conection cn = new conection();

    public static void Agregar(Compra compra) throws Exception {
        
        try {

            cn.Conectar();
            iList p = new iList(new ListasTablas("IdCompra", compra.idCompra));
            p.add(new ListasTablas("Fecha", compra.fecha));
//            p.add(new ListasTablas("Cantidad", compra.));
            p.add(new ListasTablas("IdProveedor", compra.proveedor.idProveedor));
            p.add(new ListasTablas("IdSucursal", compra.idSucursal));
            p.add(new ListasTablas("TipoCompra", compra.tipoCompra));
            p.add(new ListasTablas("NumDocumento", compra.numDocumento));
            p.add(new ListasTablas("SubTotal", compra.subTotal));
            p.add(new ListasTablas("IVA", compra.IVA));
            p.add(new ListasTablas("Percepcion", compra.percepcion));
            p.add(new ListasTablas("Total", compra.total));
            

            cn.AgregarRegistro("Compra", p, false);

        } catch (Exception ex) {
            cn.Desconectar();
            JOptionPane.showMessageDialog(null, ex.getMessage() + " mensaje: " + ex.getLocalizedMessage());

        }
    }

    public static void ActualizarInventario(Compra compra) throws ErrorTienda {
        try {
            cn.Conectar();
            iList a = new iList(new ListasTablas("IdCompra", compra.idCompra));
            iList p = new iList(new ListasTablas("Fecha", compra.fecha));
            p.add(new ListasTablas("Cantidad", compra.articulo.get(1).cantidad));
            p.add(new ListasTablas("IdProveedor", compra.proveedor.idProveedor));
            p.add(new ListasTablas("Total", compra.total));
            cn.ModificarRegistro("Compra", p, a);
        } catch (Exception e) {
            throw new ErrorTienda("Error al actualizar ", e.getMessage());
        }

    }

    public static void ActualizarPrecioPromedioProducto(ArrayList<DetalleCompra> detalleCompra) {

    }
     public static ArrayList<Compra> Obtener() throws ErrorTienda {
        String[] cm = new String[]{"IdCompra", "Fecha", "IdProveedor", "IdSucursal", "TipoCompra","NumDocumento","SubTotal","IVA","Percepcion","Total"};

        ArrayList<Object> listaCompra= new ArrayList();
        try {
            cn.Conectar();
            PreparedStatement ps = cn.BuscarTodos("Venta", cm);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                listaCompra.add(rs.getString("IdCompra"));
                listaCompra.add(rs.getString("Fecha"));
                listaCompra.add(rs.getString("IdProveedor"));
                listaCompra.add(rs.getString("IdSucursal"));
                listaCompra.add(rs.getString("TipoCompra"));
                listaCompra.add(rs.getString("NumDocumento"));
                listaCompra.add(rs.getString("SubTotal"));
                listaCompra.add(rs.getString("IVA"));
                listaCompra.add(rs.getString("Percepcion"));
                listaCompra.add(rs.getString("Total"));
                

            }
            cn.Desconectar();
        } catch (Exception e) {
            throw new ErrorTienda("Class ControladorCompra/Obtener", e.getMessage());
        }
        ArrayList<Compra> listaCompras = (ArrayList) listaCompra;
        return listaCompras;
    }

    public static int ObtenerIdCompra() throws ErrorTienda {

        int Id = 0;
        ResultSet rs;
        PreparedStatement ps;
        try {

            cn.Conectar();
            ps = cn.BuscarIdMax("IdCompra", "Compra");
            rs = ps.executeQuery();
            while (rs.next()) {
                Id = rs.getInt(1);
            }
//            Id = Id + 1;
        } catch (Exception e) {
            throw new ErrorTienda("Error al obtener el IdCompra", e.getMessage());
        }
        return Id;
    }

    public ControladorCompra() {

    }
}
