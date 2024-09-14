
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

/**
 *
 * @author Roger
 */
public class Parametro {

    public int idParametro;
    public String nombre;
    public String valor;

    public Parametro() {
        //this.idParametro = id;
        //this.nombre = nombreParametro;
        //this.valor = valorParametro;
    }

    public static ArrayList<Parametro> Obtener() throws ErrorTienda {
        String[] cm = new String[]{"IdParametro", "Nombre", "Valor"};
        conection cn = new conection();
        ArrayList<Object> listaParametro = new ArrayList();
        try {
            cn.Conectar();
            PreparedStatement ps = cn.BuscarTodos("Parametro", cm);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                listaParametro.add(rs.getString("IdParametro"));
                listaParametro.add(rs.getString("Nombre"));
                listaParametro.add(rs.getString("Valor"));

            }
            cn.Desconectar();
        } catch (Exception e) {
            throw new ErrorTienda("Class Parametro/Obtener", e.getMessage());
        }
        ArrayList<Parametro> listaParametros = (ArrayList) listaParametro;
        return listaParametros;
    }

    public Parametro ObtenerUtilidad() {
        Parametro parametro = new Parametro();
        try {
            String nombreParametro = "Utilidad";
            iList p = new iList(new ListasTablas("Nombre", nombreParametro));
            conection cn = new conection();
            cn.Conectar();
            String cm[] = new String[]{"Valor"};
            PreparedStatement ps = cn.BuscarRegistro("parametros", cm, p);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("La utilidad es: " + rs.getString("Valor"));
                parametro.idParametro = rs.getInt("idParametro");
                parametro.nombre = rs.getString("Nombre");
                parametro.valor = rs.getString("Valor");
            }
        } catch (Exception ex) {
        }

        return parametro;
    }

    public static ArrayList<Parametro> Buscar(String buscar) throws ErrorTienda {
        conection cn = new conection();
        if (buscar.isEmpty()) {
            //SI ESTA VACIO LLENAR TODO
            System.out.println("buscar = " + buscar);
            String[] cm = new String[]{"IdParametro", "Nombre", "Valor"};
            ArrayList<Object> parametro = new ArrayList();
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarTodos("Parametro", cm);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    parametro.add(rs.getString("IdParametro"));
                    parametro.add(rs.getString("Nombre"));
                    parametro.add(rs.getString("Valor"));

                }
                cn.Desconectar();
            } catch (Exception e) {
                throw new ErrorTienda("Class Parametro/BuscarIF", e.getMessage());
            }
            ArrayList<Parametro> Parametros = (ArrayList) parametro;
            return Parametros;

        } else {
            ArrayList<Object> parametro = new ArrayList();
            System.out.println("buscar = " + buscar);
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarRegistroLikePar("Parametro", "Nombre", buscar);
                System.out.println("ps " + ps.toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    parametro.add(rs.getString("IdParametro"));
                    parametro.add(rs.getString("Nombre"));
                    parametro.add(rs.getString("valor"));
                }
                cn.Desconectar();
            } catch (Exception e) {
                throw new ErrorTienda("Class Parametro/BuscarELSE ", e.getMessage());
            }
            ArrayList<Parametro> Parametros = (ArrayList) parametro;
            return Parametros;
        }

    }

    public static void Modificar(Parametro par) throws ErrorTienda {
        conection cn = new conection();
        try {
            cn.Conectar();
            iList a = new iList(new ListasTablas("IdParametro", par.idParametro));
            iList p = new iList(new ListasTablas("Valor", par.valor));
            cn.ModificarRegistro("Parametro", p, a);
        } catch (Exception e) {
            throw new ErrorTienda("Class Parametro/Modificar", e.getMessage());
        }
    }
}
