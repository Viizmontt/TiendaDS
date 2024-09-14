/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import static Clases.ControladorProveedor.cn;
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
public class ControladorProducto {

    static conection cn = new conection();

    public static void Agregar(Producto producto) throws Exception {
        Producto productoObtenido = new Producto();
        productoObtenido = Obtener(producto.CodBarra);
        Inventario inventarioObtenido = new Inventario();
        inventarioObtenido = ObtenerInventario(producto.CodBarra, producto.idSucursal);
        if (productoObtenido.nombre != null) {
            if (inventarioObtenido.codBarra == null) {
                try {
                    cn.Conectar();
                    iList d = new iList(new ListasTablas("IdSucursal", producto.idSucursal));
                    d.add(new ListasTablas("CodBarra", productoObtenido.CodBarra));
                    d.add(new ListasTablas("Cantidad", producto.inventario));
                    cn.AgregarRegistro("Inventario", d, false);
                    JOptionPane.showMessageDialog(null, "Este producto ya existe en el catalogo. Se agrego al inventario de la sucursal la cantidad especificada");

                } catch (Exception e) {
                    throw new ErrorTienda("Class ControladorProducto/AgregarYaexiste", e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Este producto ya existe en esta sucursal");
            }

        } else {
            try {
                cn.Conectar();
                iList p = new iList(new ListasTablas("CodBarra", producto.CodBarra));
                p.add(new ListasTablas("Costo", producto.costo));
                p.add(new ListasTablas("Nombre", producto.nombre));
                cn.AgregarRegistro("Producto", p, false);
                iList d = new iList(new ListasTablas("IdSucursal", producto.idSucursal));
                d.add(new ListasTablas("CodBarra", producto.CodBarra));
                d.add(new ListasTablas("Cantidad", producto.inventario));
                cn.AgregarRegistro("Inventario", d, false);

            } catch (Exception e) {
                throw new ErrorTienda("Class ControladorProducto/Agregar", e.getMessage());
            }
        }

    }

    public static void Modificar(Producto P) throws ErrorTienda {

        try {
            cn.Conectar();
            iList a = new iList(new ListasTablas("CodBarra", P.CodBarra));
            iList p = new iList(new ListasTablas("Costo", P.costo));
            p.add(new ListasTablas("Nombre", P.nombre));
            cn.ModificarRegistro("Producto", p, a);
            iList b = new iList(new ListasTablas("CodBarra", P.CodBarra));
            b.add(new ListasTablas("IdSucursal", P.idSucursal));
            iList c = new iList(new ListasTablas("Cantidad", P.inventario));
            if (cn.revisarInventario(P.CodBarra, P.idSucursal) == true){
                cn.ModificarRegistro("Inventario", c, b);
            } else {
                cn.insertarInventario(P.CodBarra, P.idSucursal, P.inventario);
            }
            
        } catch (Exception e) {
            throw new ErrorTienda("Class ControladorProducto/Modificar", e.getMessage());
        }
    }

    public static void Eliminar(Producto producto) throws ErrorTienda {
        try {
            cn.Conectar();
            iList p = new iList(new ListasTablas("CodBarra", producto.CodBarra));
            p.add(new ListasTablas("IdSucursal", producto.idSucursal));

            cn.Eliminar("Inventario", p);

        } catch (Exception e) {
            throw new ErrorTienda("Class ControladorProducto/Eliminar", e.getMessage());
        }

    }

    public ArrayList<Producto> Buscar(String buscar, int idSucursal) throws ErrorTienda {

        if (buscar.isEmpty()) {
            //SI ESTA VACIO LLENAR TODO
            System.out.println("buscar = " + buscar);
            String[] cm = new String[]{"CodBarra", "Nombre", "Costo"};
            ArrayList<Object> listaProductos = new ArrayList();
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarTodosEnSucursal(idSucursal);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    listaProductos.add(rs.getString("CodBarra"));
                    listaProductos.add(rs.getString("Nombre"));
                    listaProductos.add(rs.getString("Cantidad"));
                    listaProductos.add(rs.getString("Costo"));
                    listaProductos.add(cn.nombreSucursal(rs.getString("IdSucursal")));
                    listaProductos.add(rs.getString("IdSucursal"));
                }

                cn.Desconectar();
            } catch (Exception e) {
                throw new ErrorTienda("Class ControladorProductos/BuscarIF", e.getMessage());
            }
            ArrayList<Producto> listaProducto = (ArrayList) listaProductos;
            return listaProducto;

        } else {
            ArrayList<Object> listaProductos = new ArrayList();
            System.out.println("buscar = " + buscar);
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarTodosEnSucursal(idSucursal, buscar);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    listaProductos.add(rs.getString("CodBarra"));
                    listaProductos.add(rs.getString("Nombre"));
                    listaProductos.add(rs.getString("Cantidad"));
                    listaProductos.add(rs.getString("Costo"));
                    listaProductos.add(cn.nombreSucursal(rs.getString("IdSucursal")));
                    listaProductos.add(rs.getString("IdSucursal"));
                }

                cn.Desconectar();
            } catch (Exception e) {
                throw new ErrorTienda("Class ControladorProducto/BuscarELSE ", e.getMessage());
            }
            ArrayList<Producto> productos = (ArrayList) listaProductos;
            return productos;
        }

    }

    public static Producto Obtener(String CodigoBarra, int idSucursal) throws ErrorTienda {
        Producto producto = new Producto();
        String[] bs = new String[]{"CodBarra", "Costo", "Nombre"};
        String[] ba = new String[]{"Cantidad"};
        try {
            cn.Conectar();
            iList p = new iList(new ListasTablas("CodBarra", CodigoBarra));
            ResultSet rs = cn.BuscarRegistro("Producto", bs, p).executeQuery();
            iList a = new iList(new ListasTablas("CodBarra", CodigoBarra));
            a.add(new ListasTablas("IdSucursal", idSucursal));
            ResultSet rt = cn.BuscarRegistro("Inventario", ba, a).executeQuery();
            while (rs.next()) {
                rs.first();
                producto.CodBarra = rs.getString(1);
                producto.inventario = Double.parseDouble(rt.getString(3));
                producto.costo = Double.parseDouble(rs.getString(3));
                producto.nombre = rs.getString(2);
                producto.idSucursal = Integer.parseInt(rt.getString(1));

            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex.getMessage() + " mensaje: " + ex.getLocalizedMessage());
        }
        return producto;
    }

    public static Producto Obtener(String CodigoBarra) throws ErrorTienda {
        Producto producto = new Producto();
        String[] bs = new String[]{"CodBarra", "Costo", "Nombre"};
        try {
            cn.Conectar();
            iList p = new iList(new ListasTablas("CodBarra", CodigoBarra));
            ResultSet rs = cn.BuscarRegistro("Producto", bs, p).executeQuery();
            while (rs.next()) {
                rs.first();
                producto.CodBarra = rs.getString(1);
                producto.costo = Double.parseDouble(rs.getString(2));
                producto.nombre = rs.getString(3);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex.getMessage() + " mensaje: " + ex.getLocalizedMessage());
        }
        return producto;
    }

    public static Inventario ObtenerInventario(String CodigoBarra, int idSucursal) throws ErrorTienda {
        Inventario inventario = new Inventario();
        String[] bs = new String[]{"CodBarra", "idSucursal", "Cantidad"};
        try {
            cn.Conectar();
            iList p = new iList(new ListasTablas("CodBarra", CodigoBarra));
            p.add(new ListasTablas("IdSucursal", idSucursal));
            ResultSet rs = cn.BuscarRegistro("Inventario", bs, p).executeQuery();
            while (rs.next()) {
                rs.first();
                inventario.codBarra = rs.getString(1);
                inventario.idSucursal = rs.getInt(2);
                inventario.cantidad = rs.getInt(3);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex.getMessage() + " mensaje de INV: " + ex.getLocalizedMessage());
        }
        return inventario;
    }

    public ControladorProducto() {

    }
}
