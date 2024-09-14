
package Clases;

import static Clases.ControladorProveedor.cn;
import connections.ListasTablas;
import connections.iList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class ControladorTipoPrecio {
    public ControladorTipoPrecio(){}
    
  public static void agregarTipoPrecio(TipoPrecio tipoPrecio) throws Exception{
      
        try {
            cn.Conectar();
            iList p = new iList(new ListasTablas("IdTipoPrecio",tipoPrecio.idTipoPrecio));
            p.add(new ListasTablas("Nombre", tipoPrecio.nombre));
            p.add(new ListasTablas("Utilidad", tipoPrecio.utilidad));
            

            cn.AgregarRegistro("TipoPrecio", p, false);

        } catch (Exception e) {
            throw new ErrorTienda("Class ControladorTipoPrecio/Agregar", e.getMessage());
        }
  } 
      public static void eliminarTipoPrecio(TipoPrecio tipoPrecio) throws ErrorTienda {
        try {
            cn.Conectar();
            iList p = new iList(new ListasTablas("IdTipoPrecio", tipoPrecio.idTipoPrecio));

            cn.Eliminar("TipoPrecio", p);

        } catch (Exception e) {
            throw new ErrorTienda("Class ControladorTipoPrecio/Eliminar", e.getMessage());
        }

    }
      public static void Modificar(TipoPrecio tipoPrecio) throws ErrorTienda {

        try {
            cn.Conectar();
            iList a = new iList(new ListasTablas("IdTipoPrecio", tipoPrecio.idTipoPrecio));
            iList p = new iList(new ListasTablas("Nombre", tipoPrecio.nombre));
            p.add(new ListasTablas("Utilidad", tipoPrecio.utilidad));
            cn.ModificarRegistro("TipoPrecio", p, a);
        } catch (Exception e) {
            throw new ErrorTienda("Class ControladorTipoPrecio/Modificar", e.getMessage());
        }
    }
  
      public static ArrayList<TipoPrecio> Buscar(String buscar) throws ErrorTienda {

        if (buscar.isEmpty()) {
            //SI ESTA VACIO LLENAR TODO
            System.out.println("buscar = " + buscar);
            String[] cm = new String[]{"IdTipoPrecio", "Nombre", "Utilidad"};
            ArrayList<Object> listaTipoPrecio = new ArrayList();
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarTodos("TipoPrecio", cm);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    listaTipoPrecio.add(rs.getString("IdTipoPrecio"));
                    listaTipoPrecio.add(rs.getString("Nombre"));
                    listaTipoPrecio.add(rs.getString("Utilidad"));
                    
                }
                cn.Desconectar();
            } catch (Exception e) {
                throw new ErrorTienda("Class ControladorTipoPrecio/BuscarIF", e.getMessage());
            }
            ArrayList<TipoPrecio> listaTipoPrecios = (ArrayList) listaTipoPrecio;
            return listaTipoPrecios;

        } else {
            ArrayList<Object> listaTipoPrecio = new ArrayList();
            System.out.println("buscar = " + buscar);
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarRegistroLikeTP("TipoPrecio", "Nombre", buscar);
                System.out.println("ps " + ps.toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    listaTipoPrecio.add(rs.getString("IdTipoPrecio"));
                    listaTipoPrecio.add(rs.getString("Nombre"));
                    listaTipoPrecio.add(rs.getString("Utilidad"));
                    
                }
                cn.Desconectar();
            } catch (Exception e) {
                throw new ErrorTienda("Class ControladorTipoPrecio/BuscarELSE ", e.getMessage());
            }
            ArrayList<TipoPrecio> TipoPrecios = (ArrayList) listaTipoPrecio;
            return TipoPrecios;
        }

    }
          public static TipoPrecio ObtenerTipoPrecio(int id) throws ErrorTienda {
        TipoPrecio TipoPrecio = new TipoPrecio();
        String[] bs = new String[]{"IdTipoPrecio", "Nombre", "Utilidad"};
        try {
            cn.Conectar();
            iList p = new iList(new ListasTablas("IdTipoPrecio", id));
            ResultSet rs = cn.BuscarRegistro("TipoPrecio", bs, p).executeQuery();
            while (rs.next()){
                rs.first();
                TipoPrecio.idTipoPrecio = Integer.parseInt(rs.getString(1));
                TipoPrecio.nombre = (rs.getString(2));
                TipoPrecio.utilidad= Double.parseDouble(rs.getString(3));
                
            
            }


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex.getMessage() + " mensaje: " + ex.getLocalizedMessage());
        }
        return TipoPrecio;
    }
          
    public Integer ObtenerIdTipoPrecio() throws ErrorTienda {

        int Id = 0;
        ResultSet rs;
        PreparedStatement ps;
        try {
            cn.Conectar();
            ps = cn.BuscarIdMax("IdTipoPrecio", "tipoprecio");
            rs = ps.executeQuery();
            while (rs.next()) {
                Id = rs.getInt(1);
            }
        } catch (Exception e) {
            throw new ErrorTienda("Error al obtener el IdTipoPrecio", e.getMessage());
        }
        return Id;
    }
}
