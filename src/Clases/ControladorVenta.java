/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import static Clases.ControladorProveedor.cn;
import com.mysql.jdbc.exceptions.DeadlockTimeoutRollbackMarker;
import connections.ListasTablas;
import connections.conection;
import connections.iList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HAZAEL
 */
public class ControladorVenta {

    static conection cn = new conection();

    public void Agregar(Venta venta) throws ErrorTienda, SQLException, Exception {
        try {
            cn.Conectar();
            iList p = new iList(new ListasTablas("IdVenta", venta.idVenta));
            p.add(new ListasTablas("IdSucursal", venta.idSucursal));
            p.add(new ListasTablas("TipoVenta", venta.tipoVenta));
            p.add(new ListasTablas("IdTipoPrecio", venta.idTipoPrecio));
            p.add(new ListasTablas("Cliente", venta.cliente));
            p.add(new ListasTablas("Fecha", venta.fecha));
            p.add(new ListasTablas("IVA", venta.IVA));
            p.add(new ListasTablas("TotalGravado", venta.totalGrabado));
            p.add(new ListasTablas("Total", venta.total));
            p.add(new ListasTablas("Direccion", venta.direccion));
            p.add(new ListasTablas("Giro", venta.giro));
            p.add(new ListasTablas("NIT", venta.NIT));
            p.add(new ListasTablas("NRC", venta.NRC));
            p.add(new ListasTablas("NDocumento", venta.numDocumento));
            cn.AgregarRegistro("Venta", p, false);

        } catch (SQLException e) {
            throw new ErrorTienda("Class ControladorVenta/Agregar", e.getMessage());
        }
    }

    public static ArrayList<Venta> Obtener() throws ErrorTienda {
        String[] cm = new String[]{"IdVenta", "IdSucursal", "TipoVenta", "IdTipoPrecio", "Cliente", "Fecha", "IVA", "TotalGravado", "Total", "Direccion", "Giro", "NIT", "NRC", "NDocumento"};

        ArrayList<Object> listaVentas = new ArrayList();
        try {
            cn.Conectar();
            PreparedStatement ps = cn.BuscarTodos("Venta", cm);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                listaVentas.add(rs.getString("IdVenta"));
                listaVentas.add(rs.getString("IdSucursal"));
                listaVentas.add(rs.getString("TipoVenta"));
                listaVentas.add(rs.getString("TipoPrecio"));
                listaVentas.add(rs.getString("Cliente"));
                listaVentas.add(rs.getString("Fecha"));
                listaVentas.add(rs.getString("IVA"));
                listaVentas.add(rs.getString("TotalGravado"));
                listaVentas.add(rs.getString("Total"));
                listaVentas.add(rs.getString("Direccion"));
                listaVentas.add(rs.getString("Giro"));
                listaVentas.add(rs.getString("NIT"));
                listaVentas.add(rs.getString("NRC"));
                listaVentas.add(rs.getString("NDocumento"));

            }
            cn.Desconectar();
        } catch (Exception e) {
            throw new ErrorTienda("Class ControladorVenta/Obtener", e.getMessage());
        }
        ArrayList<Venta> listaVenta = (ArrayList) listaVentas;
        return listaVenta;
    }

    public static int ObtenerIdVenta() throws ErrorTienda {

        int Id = 0;
        ResultSet rs;
        PreparedStatement ps;
        try {

            cn.Conectar();
            ps = cn.BuscarIdMax("IdVenta", "Venta");
            rs = ps.executeQuery();
            while (rs.next()) {
                Id = rs.getInt(1);
            }

        } catch (Exception e) {
            throw new ErrorTienda("Error al obtener el IdVenta", e.getMessage());
        }
        return Id;
    }

    public void ActualizarInventario(ArrayList<DetalleVenta> actualizarInventario, int idSucursal) throws ErrorTienda {

        ControladorProducto cp = new ControladorProducto();

        for (int i = 0; i < actualizarInventario.size(); i++) {
            try {
                cn.Conectar();
                System.out.println("UNIT " + actualizarInventario.get(i).PrecioUnitario);
                System.out.println("CANTIDAD " + actualizarInventario.get(i).cantidad);
                System.out.println("PRODUCTO " + actualizarInventario.get(i).producto.CodBarra);

                Double inventarioActual = 0.0;
                inventarioActual = cn.inventarioEnSucursalbyProducto(actualizarInventario.get(i).producto.CodBarra, idSucursal);

                iList a = new iList(new ListasTablas("CodBarra", actualizarInventario.get(i).producto.CodBarra));
                a.add(new ListasTablas("IdSucursal", idSucursal));
                iList p = new iList(new ListasTablas("Cantidad", inventarioActual - actualizarInventario.get(i).cantidad));

                cn.ModificarRegistro("inventario", p, a);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error en ActualizarInventario " + ex.getMessage());
            }
        }
    }
}
