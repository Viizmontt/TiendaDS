
package Clases;

import static Clases.ControladorProveedor.cn;
import connections.ListasTablas;
import connections.iList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ControladorSucursal {
  public static void agregarSucursal(Sucursal s) throws Exception{
      
        try {
            cn.Conectar();
            iList p = new iList(new ListasTablas("IdSucursal",s.idSucursal));
            p.add(new ListasTablas("Nombre", s.nombre));
            p.add(new ListasTablas("Direccion", s.direccion));
            p.add(new ListasTablas("Telefono", s.telefono));

            cn.AgregarRegistro("Sucursal", p, false);

        } catch (Exception e) {
            throw new ErrorTienda("Class ControladorSucursal/Agregar", e.getMessage());
        }
  } 
      public static void eliminarSucursal(Sucursal s) throws ErrorTienda {
        try {
            cn.Conectar();
            iList p = new iList(new ListasTablas("IdSucursal", s.idSucursal));

            cn.Eliminar("Sucursal", p);

        } catch (Exception e) {
            throw new ErrorTienda("Class ControladorSucursal/Eliminar", e.getMessage());
        }

    }
      public static void Modificar(Sucursal s) throws ErrorTienda {

        try {
            cn.Conectar();
            iList a = new iList(new ListasTablas("IdSucursal", s.idSucursal));
            iList p = new iList(new ListasTablas("Nombre", s.nombre));
            p.add(new ListasTablas("Direccion", s.direccion));
            p.add(new ListasTablas("Telefono", s.telefono));
            cn.ModificarRegistro("Sucursal", p, a);
        } catch (Exception e) {
            throw new ErrorTienda("Class ControladorSucursal/Modificar", e.getMessage());
        }
    }
      public static ArrayList<Sucursal> Obtener() throws ErrorTienda {
        String[] cm = new String[]{"IdSucursal", "Nombre", "Direccion", "Telefono", };

        ArrayList<Object> listaSucursal = new ArrayList();
        try {
            cn.Conectar();
            PreparedStatement ps = cn.BuscarTodos("Sucursal", cm);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                listaSucursal.add(rs.getString("IdSucursal"));
                listaSucursal.add(rs.getString("Nombre"));
                
                listaSucursal.add(rs.getString("Direccion"));
                listaSucursal.add(rs.getString("Telefono"));

            }
            cn.Desconectar();
        } catch (Exception e) {
            throw new ErrorTienda("Class ControladorSucursal/Obtener", e.getMessage());
        }
        ArrayList<Sucursal> listaSucursales = (ArrayList) listaSucursal;
        return listaSucursales;
    }
  
      public static ArrayList<Sucursal> Buscar(String buscar) throws ErrorTienda {

        if (buscar.isEmpty()) {
            //SI ESTA VACIO LLENAR TODO
            System.out.println("buscar = " + buscar);
            String[] cm = new String[]{"IdSucursal", "Nombre", "Direccion","Telefono"};
            ArrayList<Object> sucursal = new ArrayList();
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarTodos("Sucursal", cm);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    sucursal.add(rs.getString("IdSucursal"));
                    sucursal.add(rs.getString("Nombre"));
                    sucursal.add(rs.getString("Direccion"));
                    sucursal.add(rs.getString("Telefono"));
                    
                }
                cn.Desconectar();
            } catch (Exception e) {
                throw new ErrorTienda("Class ControladorSucursal/BuscarIF", e.getMessage());
            }
            ArrayList<Sucursal> Sucursales= (ArrayList) sucursal;
            return Sucursales;

        } else {
            ArrayList<Object> sucursal = new ArrayList();
            System.out.println("buscar = " + buscar);
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarRegistroLike("Sucursal", "Nombre", buscar);
                System.out.println("ps " + ps.toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    sucursal.add(rs.getString("IdSucursal"));
                    sucursal.add(rs.getString("Nombre"));
                    sucursal.add(rs.getString("Direccion"));
                    sucursal.add(rs.getString("Telefono"));
                }
                cn.Desconectar();
            } catch (Exception e) {
                throw new ErrorTienda("Class ControladorSucursal/BuscarELSE ", e.getMessage());
            }
            ArrayList<Sucursal> Sucursales = (ArrayList) sucursal;
            return Sucursales;
        }

    }
          public static Sucursal ObtenerSucursal(int id) throws ErrorTienda {
        Sucursal s = new Sucursal();
        String[] bs = new String[]{"IdSucursal", "Nombre", "Direccion","Telefono"};
        try {
            cn.Conectar();
            iList p = new iList(new ListasTablas("IdSucursal", id));
            ResultSet rs = cn.BuscarRegistro("Sucursal", bs, p).executeQuery();
            while (rs.next()){
                rs.first();
                s.idSucursal = Integer.parseInt(rs.getString(1));
                s.nombre = (rs.getString(2));
                s.direccion= (rs.getString(3));
                s.telefono= rs.getString(4);
            
            }


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex.getMessage() + " mensaje: " + ex.getLocalizedMessage());
        }
        return s;
    }
           public Integer ObtenerIdSucursal() throws ErrorTienda {

        int Id = 0;
        ResultSet rs;
        PreparedStatement ps;
        try {
            cn.Conectar();
            ps = cn.BuscarIdMax("IdSucursal", "Sucursal");
            rs = ps.executeQuery();
            while (rs.next()) {
                Id = rs.getInt(1);
            }
        } catch (Exception e) {
            throw new ErrorTienda("Error al obtener el IdSucursal", e.getMessage());
        }
        return Id;
    }
}
