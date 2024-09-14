package frame;

import Clases.Compra;
import Clases.ControladorCompra;
import Clases.ControladorProducto;
import Clases.ControladorProveedor;
import Clases.ControladorSucursal;
import Clases.ControladorTipoPrecio;
import Clases.ControladorVenta;
import Clases.DetalleCompra;
import Clases.DetalleVenta;
import Clases.ErrorTienda;
import Clases.Parametro;
import Clases.Producto;
import Clases.Proveedor;
import Clases.Sucursal;
import Clases.TipoPrecio;
import Clases.Venta;
import connections.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.table.JTableHeader;
import connections.conection;
import connections.iList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Roger Alvarez
 */
public final class JFRPrincipal extends javax.swing.JFrame {

    boolean ventas, compras, productos, proveedores, sucursales, precios, configuracion;
    boolean apagado, principal;
    int x, y;
    int filas = 0;
    double totalC = 0;
    JTableHeader tHeadVentas, tHeadListaVentas, tHeadCompras, tHeadProductos, tHeadCompra, tHeadProveedores, tHeadDetalleCompra, tHeadCompraDet, tHeadDetalleCompra1, tHeadSucursales, tHeadPrecios, tHeadParametros, tHeadListaVentasMes2, tHeadListaComprasMes;
    Validacion validacion = new Validacion();
    DefaultTableModel model0;
    private TableRowSorter trsFiltro;

    public JFRPrincipal() {
        initComponents();
        conection cn = new conection();

        try {
            cn.Conectar();
        } catch (Exception ex) {
//            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Try again...");
        }
        tHeadVentas = tblProductosVender.getTableHeader();
        tHeadListaVentas = tblListaVentas.getTableHeader();
        tHeadCompras = tblCompras.getTableHeader();
        tHeadProductos = jtblProductos.getTableHeader();
        tHeadCompra = tblCompras.getTableHeader();
        tHeadProveedores = tblProveedores.getTableHeader();
        tHeadDetalleCompra = tblDetalleCompra.getTableHeader();
        tHeadDetalleCompra1 = tblDetalleVenta.getTableHeader();
        tHeadSucursales = tblSucursal.getTableHeader();
        tHeadPrecios = tblTP.getTableHeader();
        tHeadParametros = tblParametro.getTableHeader();
        tHeadCompraDet = tblCompra.getTableHeader();
        tHeadListaVentasMes2 = tblListaVentasMes2.getTableHeader();
        tHeadListaComprasMes = tblListaComprasMes.getTableHeader();

        cabezera();
        ventas = compras = productos = proveedores = sucursales = precios = configuracion = apagado = false;
        btnVentas.setBorder(null);
        btnCompras.setBorder(null);
        btnProductos.setBorder(null);
        btnProveedores.setBorder(null);
        apagado2();
        Principal(true);
        Compras(false);
        Ventas(false);
        Productos(false);
        Proveedores(false);
        LlenarProveedor();
        LlenarProducto("");
        LlenarTipoPrecio();
        LlenarSucursal();
        LlenarCompra();
        LlenarVenta();
        LlenarParametros();
    }

    public void LlenarProveedor() {
        System.out.println("LLENAR PRO");
        DefaultTableModel modelo = new DefaultTableModel();
        ArrayList<Proveedor> proveedor = new ArrayList();
        Object[] fila = new Object[7];
        try {
            proveedor = ControladorProveedor.Obtener();
            String[] proveedores = new String[]{"IdProveedor", "Nombre", "Telefono", "Direccion", "NIT", "NRC", "Email"};
            modelo.setColumnIdentifiers(proveedores);
            Iterator<Proveedor> prov = proveedor.iterator();
            
            if (prov.hasNext()){
                while (prov.hasNext()) {
                    fila[0] = prov.next();
                    fila[1] = prov.next();
                    fila[2] = prov.next();
                    fila[3] = prov.next();
                    fila[4] = prov.next();
                    fila[5] =  prov.next();
                    fila[6] = prov.next();
                    modelo.addRow(fila);
                    tblProveedores.setModel(modelo);
                }
            } else {
                fila[0] = "-";
                fila[1] = "-";
                fila[2] = "-";
                fila[3] = "-";
                fila[4] = "-";
                fila[5] = "-";
                fila[6] = "-";
                modelo.addRow(fila);
                tblProveedores.setModel(modelo);
            }

        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblProveedores.getColumnModel().getColumn(5).setMinWidth(0);
        tblProveedores.getColumnModel().getColumn(5).setMaxWidth(0);
        tblProveedores.getColumnModel().getColumn(5).setWidth(0);

        tblProveedores.getColumnModel().getColumn(6).setMinWidth(0);
        tblProveedores.getColumnModel().getColumn(6).setMaxWidth(0);
        tblProveedores.getColumnModel().getColumn(6).setWidth(0);

    }

    public void LlenarTipoPrecio() {
        System.out.println("LLENAR TipoPrecio" + "");
        DefaultTableModel modelo = new DefaultTableModel();
        ArrayList<TipoPrecio> tp = new ArrayList();
        Object[] fila = new Object[3];
        try {
            tp = ControladorTipoPrecio.Buscar("");
            String[] tipo = new String[]{"IdTipoPrecio", "Nombre", "Utilidad"};
            modelo.setColumnIdentifiers(tipo);
            Iterator<TipoPrecio> prov = tp.iterator();
            while (prov.hasNext()) {
                fila[0] = prov.next();
                fila[1] = prov.next();
                fila[2] = prov.next();

                modelo.addRow(fila);
                tblTP.setModel(modelo);
            }
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void llena_comboMes(String año) {
        System.out.println("LLENAR COMBO MES");
        cmbMes.removeAllItems();
        conection cn = new conection();
        try {
            try {
                cn.Conectar();

                PreparedStatement ps = cn.BuscarMes(año);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String mes = rs.getString(1);
                    System.out.println(mes);
                    if (mes.equalsIgnoreCase("1")) {
                        cmbMes.addItem("Enero");
                    } else if (mes.equalsIgnoreCase("2")) {
                        cmbMes.addItem("Febrero");
                    } else if (mes.equalsIgnoreCase("3")) {
                        cmbMes.addItem("Marzo");
                    } else if (mes.equalsIgnoreCase("4")) {
                        cmbMes.addItem("Abril");
                    } else if (mes.equalsIgnoreCase("5")) {
                        cmbMes.addItem("Mayo");
                    } else if (mes.equalsIgnoreCase("6")) {
                        cmbMes.addItem("Junio");
                    } else if (mes.equalsIgnoreCase("7")) {
                        cmbMes.addItem("Julio");
                    } else if (mes.equalsIgnoreCase("8")) {
                        cmbMes.addItem("Agosto");
                    } else if (mes.equalsIgnoreCase("9")) {
                        cmbMes.addItem("Septiembre");
                    } else if (mes.equalsIgnoreCase("10")) {
                        cmbMes.addItem("Octubre");
                    } else if (mes.equalsIgnoreCase("11")) {
                        cmbMes.addItem("Noviembre");
                    } else if (mes.equalsIgnoreCase("12")) {
                        cmbMes.addItem("Diciembre");
                    }
                }
                cn.Desconectar();
            } catch (Exception ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void llena_comboAño() {
        System.out.println("LLENAR COMBO Año");
        cmbAño.removeAllItems();
        conection cn = new conection();
        try {
            try {
                cn.Conectar();

                PreparedStatement ps = cn.BuscarAño();
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cmbAño.addItem(rs.getString(1));
                }
            } catch (Exception ex) {

            }
        } catch (Exception ex) {

        }
    }

    public void LlenarTablaMes(String mes, String año) {
        String Mes = "";
        if (mes.equalsIgnoreCase("Enero")) {
            Mes = "1";
        } else if (mes.equalsIgnoreCase("Febrero")) {
            Mes = "2";
        } else if (mes.equalsIgnoreCase("Marzo")) {
            Mes = "3";
        } else if (mes.equalsIgnoreCase("Abril")) {
            Mes = "4";
        } else if (mes.equalsIgnoreCase("Mayo")) {
            Mes = "5";
        } else if (mes.equalsIgnoreCase("Junio")) {
            Mes = "6";
        } else if (mes.equalsIgnoreCase("Julio")) {
            Mes = "7";
        } else if (mes.equalsIgnoreCase("Agosto")) {
            Mes = "8";
        } else if (mes.equalsIgnoreCase("Septiembre")) {
            Mes = "9";
        } else if (mes.equalsIgnoreCase("Octubre")) {
            Mes = "10";
        } else if (mes.equalsIgnoreCase("Noviembre")) {
            Mes = "11";
        } else if (mes.equalsIgnoreCase("Diciembre")) {
            Mes = "12";
        }
        String[] cm = new String[]{"No", "Fecha", "No Documento", "NRC", "Sucursal", "Iva", "Venta Gravada", "Total"};
        ArrayList<Object> listaVentas = new ArrayList();
        DefaultTableModel modelo = new DefaultTableModel();
        conection cn = new conection();
        try {
            cn.Conectar();
            PreparedStatement ps = cn.BuscarRegistroVentas(Mes, año);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                listaVentas.add(rs.getString("IdVenta"));
                listaVentas.add(rs.getString("Fecha"));
                listaVentas.add(rs.getString("NDocumento"));
                listaVentas.add(rs.getString("NRC"));
                listaVentas.add(cn.nombreSucursal(rs.getString("IdSucursal")));

                listaVentas.add(rs.getString("IVA"));
                listaVentas.add(rs.getString("TotalGravado"));
                listaVentas.add(rs.getString("Total"));

            }
            cn.Desconectar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        Object[] fila = new Object[9];

        modelo.setColumnIdentifiers(cm);
        Iterator prod = listaVentas.iterator();
        while (prod.hasNext()) {
            fila[0] = prod.next();
            fila[1] = prod.next();
            fila[2] = prod.next();
            fila[3] = prod.next();
            fila[4] = prod.next();
            fila[5] = prod.next();
            fila[6] = prod.next();
            fila[7] = prod.next();

            modelo.addRow(fila);

        }
        tblListaVentasMes2.setModel(modelo);
    }

    void llena_comboMesCompra(String año) {
        System.out.println("LLENAR COMBO MES COMPRA");
        cmbMes1.removeAllItems();
        conection cn = new conection();
        try {
            try {
                cn.Conectar();

                PreparedStatement ps = cn.BuscarMesCompra(año);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String mes = rs.getString(1);
                    System.out.println(mes);
                    if (mes.equalsIgnoreCase("1")) {
                        cmbMes1.addItem("Enero");
                    } else if (mes.equalsIgnoreCase("2")) {
                        cmbMes1.addItem("Febrero");
                    } else if (mes.equalsIgnoreCase("3")) {
                        cmbMes1.addItem("Marzo");
                    } else if (mes.equalsIgnoreCase("4")) {
                        cmbMes1.addItem("Abril");
                    } else if (mes.equalsIgnoreCase("5")) {
                        cmbMes1.addItem("Mayo");
                    } else if (mes.equalsIgnoreCase("6")) {
                        cmbMes1.addItem("Junio");
                    } else if (mes.equalsIgnoreCase("7")) {
                        cmbMes1.addItem("Julio");
                    } else if (mes.equalsIgnoreCase("8")) {
                        cmbMes1.addItem("Agosto");
                    } else if (mes.equalsIgnoreCase("9")) {
                        cmbMes1.addItem("Septiembre");
                    } else if (mes.equalsIgnoreCase("10")) {
                        cmbMes1.addItem("Octubre");
                    } else if (mes.equalsIgnoreCase("11")) {
                        cmbMes1.addItem("Noviembre");
                    } else if (mes.equalsIgnoreCase("12")) {
                        cmbMes1.addItem("Diciembre");
                    }
                }
                cn.Desconectar();
            } catch (Exception ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void llena_comboAñoCompra() {
        System.out.println("LLENAR COMBO Año");
        cmbAño1.removeAllItems();
        conection cn = new conection();
        try {
            try {
                cn.Conectar();

                PreparedStatement ps = cn.BuscarAñoCompra();
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cmbAño1.addItem(rs.getString(1));
                }
            } catch (Exception ex) {

            }
        } catch (Exception ex) {

        }
    }

    public void LlenarTablaMesCompra(String mes, String año) {
        String Mes = "";
        if (mes.equalsIgnoreCase("Enero")) {
            Mes = "1";
        } else if (mes.equalsIgnoreCase("Febrero")) {
            Mes = "2";
        } else if (mes.equalsIgnoreCase("Marzo")) {
            Mes = "3";
        } else if (mes.equalsIgnoreCase("Abril")) {
            Mes = "4";
        } else if (mes.equalsIgnoreCase("Mayo")) {
            Mes = "5";
        } else if (mes.equalsIgnoreCase("Junio")) {
            Mes = "6";
        } else if (mes.equalsIgnoreCase("Julio")) {
            Mes = "7";
        } else if (mes.equalsIgnoreCase("Agosto")) {
            Mes = "8";
        } else if (mes.equalsIgnoreCase("Septiembre")) {
            Mes = "9";
        } else if (mes.equalsIgnoreCase("Octubre")) {
            Mes = "10";
        } else if (mes.equalsIgnoreCase("Noviembre")) {
            Mes = "11";
        } else if (mes.equalsIgnoreCase("Diciembre")) {
            Mes = "12";
        }
        String[] cm = new String[]{"No", "Fecha", "No Documento", "SubTotal", "IVA", "Percepcion", "Total"};
        ArrayList<Object> listaCompras = new ArrayList();
        DefaultTableModel modelo = new DefaultTableModel();
        conection cn = new conection();
        try {
            cn.Conectar();
            PreparedStatement ps = cn.BuscarRegistroCompras(Mes, año);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                listaCompras.add(rs.getString("IdCompra"));
                listaCompras.add(rs.getString("Fecha"));
                listaCompras.add(rs.getString("NumDocumento"));
                listaCompras.add(rs.getString("Subtotal"));
                listaCompras.add(rs.getString("IVA"));

                listaCompras.add(rs.getString("Percepcion"));
                listaCompras.add(rs.getString("Total"));

            }
            cn.Desconectar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        Object[] fila = new Object[7];

        modelo.setColumnIdentifiers(cm);
        Iterator prod = listaCompras.iterator();
        while (prod.hasNext()) {
            fila[0] = prod.next();
            fila[1] = prod.next();
            fila[2] = prod.next();
            fila[3] = prod.next();
            fila[4] = prod.next();
            fila[5] = prod.next();
            fila[6] = prod.next();

            modelo.addRow(fila);

        }
        tblListaComprasMes.setModel(modelo);
    }

    public void LlenarProducto(String criterio) {
        /* System.out.println("Criterio = " + criterio);
        DefaultTableModel modelo = new DefaultTableModel();
        ArrayList<Producto> producto = new ArrayList();
        Object[] fila = new Object[4];
        try {
            producto = ControladorProducto.Buscar(criterio);
            String[] productos = new String[]{"CodBarra", "Nombre", "Inventario", "Costo"};
            modelo.setColumnIdentifiers(productos);
            Iterator<Producto> prod = producto.iterator();
            while (prod.hasNext()) {
                fila[0] = prod.next();
                fila[2] = prod.next();
                fila[3] = prod.next();
                fila[1] = prod.next();
                modelo.addRow(fila);
                jtblProductos.setModel(modelo);
            }
            System.out.println("Lleno producto!");
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
    }

    void llena_combo() {
        /* System.out.println("LLENAR COMBO");

        ArrayList<Proveedor> proveedor;
        Object[] fila = new Object[5];
        try {
            proveedor = ControladorProveedor.Obtener();

            Iterator<Proveedor> prov = proveedor.iterator();
            while (prov.hasNext()) {
                fila[0] = prov.next();
                fila[1] = prov.next();
                fila[2] = prov.next();
                fila[3] = prov.next();
                fila[4] = prov.next();
                cmbProveedor.addItem(fila[0]);

            }
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    public void LlenarSucursal() {
        System.out.println("LLENAR Sucursal" + "");
        DefaultTableModel modelo = new DefaultTableModel();
        ArrayList<Sucursal> sucursal = new ArrayList();
        Object[] fila = new Object[7];
        try {
            sucursal = ControladorSucursal.Obtener();
            String[] sucursales = new String[]{"IdSucursal", "Nombre", "Direccion", "Telefono"};
            modelo.setColumnIdentifiers(sucursales);
            Iterator<Sucursal> prov = sucursal.iterator();
            
            if (prov.hasNext()){
                while (prov.hasNext()) {
                    fila[0] = prov.next();
                    fila[1] = prov.next();
                    fila[2] = prov.next();
                    fila[3] = prov.next();

                    modelo.addRow(fila);
                    tblSucursal.setModel(modelo);
                }
            } else {
                fila[0] = "-";
                fila[1] = "-";
                fila[2] = "-";
                fila[3] = "-";

                modelo.addRow(fila);
                tblSucursal.setModel(modelo);
            }

        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void LlenarParametros() {
        System.out.println("LLENAR Parametros" + "");
        DefaultTableModel modelo = new DefaultTableModel();
        ArrayList<Parametro> parametro = new ArrayList();
        Object[] fila = new Object[7];
        try {
            parametro = Parametro.Obtener();
            String[] parametros = new String[]{"IdParametro", "Nombre", "Valor"};
            modelo.setColumnIdentifiers(parametros);
            Iterator<Parametro> para = parametro.iterator();
            while (para.hasNext()) {
                fila[0] = para.next();
                fila[1] = para.next();
                fila[2] = para.next();

                modelo.addRow(fila);
                tblParametro.setModel(modelo);
            }
        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void LlenarCompra() {
        String[] cm = new String[]{"IdCompra", "TipoCompra", "IdSucursal", "Fecha", "IdProveedor", "Total", "NumDocumento", "Subtotal", "IVA", "Percepcion"};
        ArrayList<Object> listaCompras = new ArrayList();
        DefaultTableModel modelo = new DefaultTableModel();
        conection cn = new conection();
        try {
            cn.Conectar();
            PreparedStatement ps = cn.BuscarTodosCV("compra", cm);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                listaCompras.add(rs.getString("IdCompra"));
                switch (rs.getString("TipoCompra")) {
                    case "L":
                        listaCompras.add("Libre");
                        break;
                    case "F":
                        listaCompras.add("Factura");
                        break;
                    case "C":
                        listaCompras.add("Credito Fiscal");
                        break;
                }

                listaCompras.add(cn.nombreSucursal(rs.getString("IdSucursal")));
//                listaCompras.add(rs.getString("IdSucursal"));
                listaCompras.add(rs.getString("Fecha"));
//                listaCompras.add(rs.getString("IdProveedor"));
                listaCompras.add(cn.nombreProveedor(rs.getString("IdProveedor")));
                listaCompras.add(rs.getString("Total"));
                listaCompras.add(rs.getString("NumDocumento"));
                listaCompras.add(rs.getString("Subtotal"));
                listaCompras.add(rs.getString("IVA"));
                listaCompras.add(rs.getString("Percepcion"));
            }
            cn.Desconectar();
        } catch (Exception e) {
            //ERROR!!!
        }

        Object[] fila = new Object[10];

        String[] compras = new String[]{"IdCompra", "Fecha", "TipoCompra", "Sucursal", "Proveedor", "Total", "NumDocumento", "Subtotal", "IVA", "Percepcion"};
        modelo.setColumnIdentifiers(compras);
        Iterator prod = listaCompras.iterator();
        while (prod.hasNext()) {
            fila[0] = prod.next();
            fila[2] = prod.next();
            fila[3] = prod.next();
            fila[1] = prod.next();
            fila[4] = prod.next();
            fila[5] = prod.next();
            fila[6] = prod.next();
            fila[7] = prod.next();
            fila[8] = prod.next();
            fila[9] = prod.next();
            modelo.addRow(fila);

        }
        tblCompras.setModel(modelo);
        tblCompras.getColumnModel().getColumn(6).setMinWidth(0);
        tblCompras.getColumnModel().getColumn(6).setMaxWidth(0);
        tblCompras.getColumnModel().getColumn(6).setWidth(0);

        tblCompras.getColumnModel().getColumn(7).setMinWidth(0);
        tblCompras.getColumnModel().getColumn(7).setMaxWidth(0);
        tblCompras.getColumnModel().getColumn(7).setWidth(0);

        tblCompras.getColumnModel().getColumn(8).setMinWidth(0);
        tblCompras.getColumnModel().getColumn(8).setMaxWidth(0);
        tblCompras.getColumnModel().getColumn(8).setWidth(0);

        tblCompras.getColumnModel().getColumn(9).setMinWidth(0);
        tblCompras.getColumnModel().getColumn(9).setMaxWidth(0);
        tblCompras.getColumnModel().getColumn(9).setWidth(0);
        System.out.println("Lleno Compra!...creo");
    }

    public void LlenarVenta() {
        String[] cm = new String[]{"IdVenta", "IdSucursal", "TipoVenta", "IdTipoPrecio", "Cliente", "Fecha", "IVA", "TotalGravado", "Total", "Direccion",
            "Giro", "NIT", "NRC", "NDocumento"};
        ArrayList<Object> listaVentas = new ArrayList();
        DefaultTableModel modelo = new DefaultTableModel();
        conection cn = new conection();
        try {
            cn.Conectar();
            PreparedStatement ps = cn.BuscarTodosCV("venta", cm);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                listaVentas.add(rs.getString("IdVenta"));
                listaVentas.add(cn.nombreSucursal(rs.getString("IdSucursal")));
                switch (rs.getString("TipoVenta")) {
                    case "L":
                        listaVentas.add("Libre");
                        break;
                    case "F":
                        listaVentas.add("Factura");
                        break;
                    case "C":
                        listaVentas.add("Credito Fiscal");
                        break;
                }
                listaVentas.add(cn.nombrePrecio(rs.getString("IdTipoPrecio")));
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
            //ERROR!!!
        }
        ArrayList<Venta> listaVenta = (ArrayList) listaVentas;
        Object[] fila = new Object[14];

        String[] ventas = new String[]{"IdVenta", "Fecha", "Sucursal", "Tipo Venta", "Tipo Precio", "Cliente", "IVA", "TotalGravado", "Total", "Direccion",
            "Giro", "NIT", "NRC", "NDocumento"};
        modelo.setColumnIdentifiers(ventas);
        Iterator<Venta> prod = listaVenta.iterator();
        while (prod.hasNext()) {
            fila[0] = prod.next();
            fila[2] = prod.next();
            fila[3] = prod.next();
            fila[4] = prod.next();
            fila[5] = prod.next();
            fila[1] = prod.next();
            fila[6] = prod.next();
            fila[7] = prod.next();
            fila[8] = prod.next();
            fila[9] = prod.next();
            fila[10] = prod.next();
            fila[11] = prod.next();
            fila[12] = prod.next();
            fila[13] = prod.next();
            modelo.addRow(fila);

        }
        tblListaVentas.setModel(modelo);

        tblListaVentas.getColumnModel().getColumn(3).setMinWidth(0);
        tblListaVentas.getColumnModel().getColumn(3).setMaxWidth(0);
        tblListaVentas.getColumnModel().getColumn(3).setWidth(0);

        tblListaVentas.getColumnModel().getColumn(6).setMinWidth(0);
        tblListaVentas.getColumnModel().getColumn(6).setMaxWidth(0);
        tblListaVentas.getColumnModel().getColumn(6).setWidth(0);

        tblListaVentas.getColumnModel().getColumn(7).setMinWidth(0);
        tblListaVentas.getColumnModel().getColumn(7).setMaxWidth(0);
        tblListaVentas.getColumnModel().getColumn(7).setWidth(0);

        tblListaVentas.getColumnModel().getColumn(9).setMinWidth(0);
        tblListaVentas.getColumnModel().getColumn(9).setMaxWidth(0);
        tblListaVentas.getColumnModel().getColumn(9).setWidth(0);

        tblListaVentas.getColumnModel().getColumn(10).setMinWidth(0);
        tblListaVentas.getColumnModel().getColumn(10).setMaxWidth(0);
        tblListaVentas.getColumnModel().getColumn(10).setWidth(0);

        tblListaVentas.getColumnModel().getColumn(11).setMinWidth(0);
        tblListaVentas.getColumnModel().getColumn(11).setMaxWidth(0);
        tblListaVentas.getColumnModel().getColumn(11).setWidth(0);

        tblListaVentas.getColumnModel().getColumn(12).setMinWidth(0);
        tblListaVentas.getColumnModel().getColumn(12).setMaxWidth(0);
        tblListaVentas.getColumnModel().getColumn(12).setWidth(0);

        tblListaVentas.getColumnModel().getColumn(13).setMinWidth(0);
        tblListaVentas.getColumnModel().getColumn(13).setMaxWidth(0);
        tblListaVentas.getColumnModel().getColumn(13).setWidth(0);
        System.out.println("Lleno Venta!...creo");
    }

    /*  ---- Color a las cabeceras de las tablas ----  */
    public void cabezera() {
        Font fuente = new Font("Tahoma", Font.BOLD, 12);
        tHeadVentas.setBackground(jpnBarraMenu.getBackground());
        tHeadVentas.setForeground(Color.WHITE);
        tHeadVentas.setFont(fuente);

        tHeadListaVentas.setBackground(jpnBarraMenu.getBackground());
        tHeadListaVentas.setForeground(Color.WHITE);
        tHeadListaVentas.setFont(fuente);

        tHeadCompras.setBackground(jpnBarraMenu.getBackground());
        tHeadCompras.setForeground(Color.WHITE);
        tHeadCompras.setFont(fuente);

        tHeadProductos.setBackground(jpnBarraMenu.getBackground());
        tHeadProductos.setForeground(Color.WHITE);
        tHeadProductos.setFont(fuente);

        tHeadCompra.setBackground(jpnBarraMenu.getBackground());
        tHeadCompra.setForeground(Color.WHITE);
        tHeadCompra.setFont(fuente);

        tHeadProveedores.setBackground(jpnBarraMenu.getBackground());
        tHeadProveedores.setForeground(Color.WHITE);
        tHeadProveedores.setFont(fuente);

        tHeadDetalleCompra.setBackground(jpnBarraMenu.getBackground());
        tHeadDetalleCompra.setForeground(Color.WHITE);
        tHeadDetalleCompra.setFont(fuente);

        tHeadDetalleCompra1.setBackground(jpnBarraMenu.getBackground());
        tHeadDetalleCompra1.setForeground(Color.WHITE);
        tHeadDetalleCompra1.setFont(fuente);

        tHeadCompraDet.setBackground(jpnBarraMenu.getBackground());
        tHeadCompraDet.setForeground(Color.WHITE);
        tHeadCompraDet.setFont(fuente);

        tHeadSucursales.setBackground(jpnBarraMenu.getBackground());
        tHeadSucursales.setForeground(Color.WHITE);
        tHeadSucursales.setFont(fuente);

        tHeadPrecios.setBackground(jpnBarraMenu.getBackground());
        tHeadPrecios.setForeground(Color.WHITE);
        tHeadPrecios.setFont(fuente);

        tHeadParametros.setBackground(jpnBarraMenu.getBackground());
        tHeadParametros.setForeground(Color.WHITE);
        tHeadParametros.setFont(fuente);

        tHeadListaVentasMes2.setBackground(jpnBarraMenu.getBackground());
        tHeadListaVentasMes2.setForeground(Color.WHITE);
        tHeadListaVentasMes2.setFont(fuente);

        tHeadListaComprasMes.setBackground(jpnBarraMenu.getBackground());
        tHeadListaComprasMes.setForeground(Color.WHITE);
        tHeadListaComprasMes.setFont(fuente);

    }

    /*  ---- Visualización de imágenes en Menú ----  */
    public void Principal(boolean estado) {
        if (!jpnProveedores.isVisible()) {
            jpnPrimero.setVisible(estado);

        } else {
        }
    }

    public void Compras(boolean estado) {
        if (!jpnProveedores.isVisible()) {
            jpnSegundo.setVisible(estado);
        } else {
        }
    }

    public void Ventas(boolean estado) {
        if (!jpnProveedores.isVisible()) {
            jpnTercero.setVisible(estado);
        } else {
        }
    }

    public void Productos(boolean estado) {
        if (!jpnProveedores.isVisible()) {
            jpnCuarto.setVisible(estado);
        } else {
        }
    }

    public void Proveedores(boolean estado) {
        if (!jpnProveedores.isVisible()) {
            jpnQuinto.setVisible(estado);
        } else {
        }
    }

    public void apagado() {
        apagado = true;
        jpnPrincipal.setVisible(false);
    }

    public void apagado2() {
        jpnProveedores.setVisible(false);
        jpnAgregarProv.setVisible(false);
        jpnModificarProveedor.setVisible(false);
        jpnModificarProducto.setVisible(false);
        jpnListaVentas.setVisible(false);
        jpnAgregarVenta.setVisible(false);
        jpnProductos.setVisible(false);
        jpnNuevoProducto.setVisible(false);
        jpnRegistroCompra.setVisible(false);
        jpnCompras.setVisible(false);
        jpnDetalleCompra.setVisible(false);
        jpnDetalleVenta.setVisible(false);
        jpnModificarSucursal.setVisible(false);
        jpnNuevaSucursal.setVisible(false);
        jpnSucursal.setVisible(false);
        jpnNuevaSucursal.setVisible(false);
        jpnModificarSucursal.setVisible(false);
        jpnTipoPrecio.setVisible(false);
        jpnModificarPrecio.setVisible(false);
        jpnNuevoPrecio.setVisible(false);
        jpnConfiguracion.setVisible(false);
        jpnModificarParametro.setVisible(false);
        jpnReporteMesVentas2.setVisible(false);
        jpnReporteMesCompras.setVisible(false);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngFiltroProductos = new javax.swing.ButtonGroup();
        jpnBarraSuperior = new javax.swing.JPanel();
        lblBotonCerrar = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        panelCurves1 = new org.edisoncor.gui.panel.PanelCurves();
        jpnBarraMenu = new javax.swing.JPanel();
        lblMenu = new javax.swing.JLabel();
        jpnSubMenu = new javax.swing.JPanel();
        btnCompras = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnProveedores = new javax.swing.JButton();
        btnAbrirPara = new javax.swing.JButton();
        btnAbrirSuc = new javax.swing.JButton();
        btnAbrirTP = new javax.swing.JButton();
        btnHome = new javax.swing.JLabel();
        jpnPrincipal = new javax.swing.JPanel();
        jpnPrimero = new javax.swing.JPanel();
        lbl3 = new javax.swing.JLabel();
        lbl4 = new javax.swing.JLabel();
        lbl7 = new javax.swing.JLabel();
        lbl5 = new javax.swing.JLabel();
        lbl6 = new javax.swing.JLabel();
        lblMitad = new javax.swing.JLabel();
        lbl8 = new javax.swing.JLabel();
        lbl9 = new javax.swing.JLabel();
        lbl10 = new javax.swing.JLabel();
        lbl16 = new javax.swing.JLabel();
        lbl17 = new javax.swing.JLabel();
        jpnSegundo = new javax.swing.JPanel();
        lbl11 = new javax.swing.JLabel();
        lbl12 = new javax.swing.JLabel();
        lbl13 = new javax.swing.JLabel();
        lbl14 = new javax.swing.JLabel();
        lbl15 = new javax.swing.JLabel();
        lblMitad2 = new javax.swing.JLabel();
        jpnTercero = new javax.swing.JPanel();
        lbl21 = new javax.swing.JLabel();
        lbl22 = new javax.swing.JLabel();
        lbl23 = new javax.swing.JLabel();
        lbl24 = new javax.swing.JLabel();
        lbl25 = new javax.swing.JLabel();
        lblMitad3 = new javax.swing.JLabel();
        jpnCuarto = new javax.swing.JPanel();
        lbl31 = new javax.swing.JLabel();
        lbl32 = new javax.swing.JLabel();
        lbl33 = new javax.swing.JLabel();
        lbl34 = new javax.swing.JLabel();
        lbl35 = new javax.swing.JLabel();
        lblMitad4 = new javax.swing.JLabel();
        jpnQuinto = new javax.swing.JPanel();
        lbl41 = new javax.swing.JLabel();
        lbl42 = new javax.swing.JLabel();
        lbl43 = new javax.swing.JLabel();
        lbl44 = new javax.swing.JLabel();
        lbl45 = new javax.swing.JLabel();
        lblMitad5 = new javax.swing.JLabel();
        jpnProveedores = new javax.swing.JPanel();
        btnEliminarProveedor = new javax.swing.JButton();
        btnAgregarProveedor = new javax.swing.JButton();
        btnModificarProveedor = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProveedores = new javax.swing.JTable();
        jPanel42 = new javax.swing.JPanel();
        jSeparator20 = new javax.swing.JSeparator();
        lblProveedores6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator21 = new javax.swing.JSeparator();
        txtProductosBuscar1 = new javax.swing.JTextField();
        jSeparator38 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jpnAgregarProv = new javax.swing.JPanel();
        btnGuardarProveedor = new javax.swing.JButton();
        btnAtrasProveedores = new javax.swing.JButton();
        txtDireccionProveedor = new javax.swing.JTextField();
        txtNIT = new javax.swing.JTextField();
        txtNombreProveedor = new javax.swing.JTextField();
        txtTelefonoProveedor = new javax.swing.JTextField();
        jPanel45 = new javax.swing.JPanel();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtIDProveedor = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        jSeparator18 = new javax.swing.JSeparator();
        jSeparator19 = new javax.swing.JSeparator();
        txtNRCProveedor = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jSeparator22 = new javax.swing.JSeparator();
        txtEmailProveedor = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jSeparator23 = new javax.swing.JSeparator();
        jpnModificarProveedor = new javax.swing.JPanel();
        btnGuardarModificarProveedor = new javax.swing.JButton();
        btnAtrasModificarProveedor = new javax.swing.JButton();
        txtNuevoDireccionProveedor = new javax.swing.JTextField();
        txtNuevoNIT = new javax.swing.JTextField();
        txtNuevoNombreProveedor = new javax.swing.JTextField();
        txtNuevoTelefonoProveedor = new javax.swing.JTextField();
        jPanel48 = new javax.swing.JPanel();
        jSeparator40 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtIDProveedor1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator41 = new javax.swing.JSeparator();
        jSeparator42 = new javax.swing.JSeparator();
        jSeparator43 = new javax.swing.JSeparator();
        txtEmail = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jSeparator82 = new javax.swing.JSeparator();
        txtNuevoNRC = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jSeparator24 = new javax.swing.JSeparator();
        jpnCompras = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblCompras = new javax.swing.JTable();
        btnAgregarCompra = new javax.swing.JButton();
        btnVerDetalle = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        jSeparator27 = new javax.swing.JSeparator();
        lblProveedores3 = new javax.swing.JLabel();
        lblListadoCompras = new javax.swing.JLabel();
        jSeparator35 = new javax.swing.JSeparator();
        reporteComprabtn = new javax.swing.JButton();
        jpnProductos = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblProductos = new javax.swing.JTable();
        btnNuevoProducto = new javax.swing.JButton();
        btnBuscarProducto = new javax.swing.JButton();
        btnModificarProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        jPanel43 = new javax.swing.JPanel();
        jSeparator14 = new javax.swing.JSeparator();
        lblProveedores4 = new javax.swing.JLabel();
        jSeparator25 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        txtProductosBuscar = new javax.swing.JTextField();
        jSeparator37 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        cmbSucursales1 = new javax.swing.JComboBox();
        lblSucursalProd = new javax.swing.JLabel();
        jSeparator53 = new javax.swing.JSeparator();
        jpnNuevoProducto = new javax.swing.JPanel();
        btnAgregarNuevoProducto = new javax.swing.JButton();
        btnSalirProductos = new javax.swing.JButton();
        txtCodBarraProductos = new javax.swing.JTextField();
        txtNombreProductos = new javax.swing.JTextField();
        txtPrecioProductos = new javax.swing.JTextField();
        jPanel46 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator26 = new javax.swing.JSeparator();
        jSeparator34 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        txtProductoInventario = new javax.swing.JTextField();
        jSeparator39 = new javax.swing.JSeparator();
        jLabel38 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        cmbSucursal2 = new javax.swing.JComboBox();
        jpnDetalleCompra = new javax.swing.JPanel();
        txtIdDetalleCompra = new javax.swing.JTextField();
        txtProveedorDetalleCompra = new javax.swing.JTextField();
        jPanel47 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jSeparator28 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblDetalleCompra = new javax.swing.JTable();
        jSeparator32 = new javax.swing.JSeparator();
        jLabel37 = new javax.swing.JLabel();
        txtFechaDetalleCompra = new javax.swing.JTextField();
        txtTotalDetalleCompra = new javax.swing.JTextField();
        btnAtrasDetalleCompra = new javax.swing.JButton();
        jLabel67 = new javax.swing.JLabel();
        txtSucursalDetalleCompra = new javax.swing.JTextField();
        txtNumDocumentoDetalleCompra = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        txtSubTotalDetalleCompra = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        txtIVADetalleCompra = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        txtPercepcionDetalleCompra = new javax.swing.JTextField();
        txtTipoCompraDetalleCompra = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jpnDetalleVenta = new javax.swing.JPanel();
        txtClienteDetalleVenta = new javax.swing.JTextField();
        jPanel52 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jSeparator52 = new javax.swing.JSeparator();
        txtIdDetalleVenta = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        txtNoDocDetalleVenta = new javax.swing.JTextField();
        txtFechaDetalleVenta = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblDetalleVenta = new javax.swing.JTable();
        jSeparator73 = new javax.swing.JSeparator();
        jLabel60 = new javax.swing.JLabel();
        txtTotalGravadoDetalleVenta = new javax.swing.JTextField();
        btnAtrasDetalleCompra1 = new javax.swing.JButton();
        txtNITDetalleVenta = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        txtNRCDetalleVenta = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        txtDireccionDetalleVenta = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        txtGiroDetalleVenta = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        txtTipoVentaDetalleVenta = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        txtSucursalDetalleVenta = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        txtTipoPrecioDetalleVenta = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        jSeparator102 = new javax.swing.JSeparator();
        txtIVADetalleVenta = new javax.swing.JTextField();
        jLabel93 = new javax.swing.JLabel();
        txtTotalDetalleVenta = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        jpnListaVentas = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblListaVentas = new javax.swing.JTable();
        btnAgregarVenta = new javax.swing.JButton();
        btnVerDetalleVenta = new javax.swing.JButton();
        jPanel38 = new javax.swing.JPanel();
        jSeparator44 = new javax.swing.JSeparator();
        lblVentas = new javax.swing.JLabel();
        lblListadoVentas = new javax.swing.JLabel();
        jSeparator45 = new javax.swing.JSeparator();
        btnReport = new javax.swing.JButton();
        jpnAgregarVenta = new javax.swing.JPanel();
        jSeparator62 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblProductosVender = new javax.swing.JTable();
        btnVender = new javax.swing.JButton();
        btnEliminarProductoVenta = new javax.swing.JButton();
        txtNombreProductoVender = new javax.swing.JTextField();
        txtCantidadVender = new javax.swing.JTextField();
        btnAgregarProductoVenta = new javax.swing.JButton();
        jPanel50 = new javax.swing.JPanel();
        jSeparator63 = new javax.swing.JSeparator();
        lblProveedores7 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txtNoDocVenta = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtIdVenta = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        txtCodigoBarraVender = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        lblNITVenta = new javax.swing.JLabel();
        txtNITVenta = new javax.swing.JTextField();
        lblnrc = new javax.swing.JLabel();
        lblgiro = new javax.swing.JLabel();
        txtNRCVenta = new javax.swing.JTextField();
        txtDireccionVenta = new javax.swing.JTextField();
        lblCodBarraProd18 = new javax.swing.JLabel();
        lblCodBarraProd21 = new javax.swing.JLabel();
        txtClienteVenta = new javax.swing.JTextField();
        txtGiroVenta = new javax.swing.JTextField();
        cmbTipoVenta = new javax.swing.JComboBox();
        lblCodBarraProd22 = new javax.swing.JLabel();
        cmbSucursalVenta = new javax.swing.JComboBox();
        lblCodBarraProd23 = new javax.swing.JLabel();
        txtSumaVenta = new javax.swing.JTextField();
        lblSumaVenta = new javax.swing.JLabel();
        txtIvaVenta = new javax.swing.JTextField();
        lblIvaVenta = new javax.swing.JLabel();
        txtTotalVenta = new javax.swing.JTextField();
        lblTotalVenta = new javax.swing.JLabel();
        jSeparator68 = new javax.swing.JSeparator();
        jSeparator72 = new javax.swing.JSeparator();
        lblCodBarraProd24 = new javax.swing.JLabel();
        cmbTipoPrecioVenta = new javax.swing.JComboBox();
        lblFecha3 = new javax.swing.JLabel();
        txt_fecha_venta = new com.toedter.calendar.JDateChooser();
        jpnModificarProducto = new javax.swing.JPanel();
        btnGuardarModificarProducto = new javax.swing.JButton();
        btnAtrasModificarProducto = new javax.swing.JButton();
        txtNuevoInventarioProducto = new javax.swing.JTextField();
        txtNuevoCostoProducto = new javax.swing.JTextField();
        txtNuevoCodigoBarra = new javax.swing.JTextField();
        txtNuevoNombreProducto = new javax.swing.JTextField();
        jPanel49 = new javax.swing.JPanel();
        jSeparator46 = new javax.swing.JSeparator();
        jLabel32 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jSeparator47 = new javax.swing.JSeparator();
        jSeparator48 = new javax.swing.JSeparator();
        jSeparator49 = new javax.swing.JSeparator();
        jSeparator50 = new javax.swing.JSeparator();
        jLabel66 = new javax.swing.JLabel();
        jSeparator83 = new javax.swing.JSeparator();
        txtNuevaSucursalProd = new javax.swing.JTextField();
        jpnRegistroCompra = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtIdCompra = new javax.swing.JTextField();
        jPanel41 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jSeparator56 = new javax.swing.JSeparator();
        panelCurves2 = new org.edisoncor.gui.panel.PanelCurves();
        lblFecha2 = new javax.swing.JLabel();
        lblIdCompra2 = new javax.swing.JLabel();
        lblProveedor2 = new javax.swing.JLabel();
        lblNomProd2 = new javax.swing.JLabel();
        txtNomProd = new javax.swing.JTextField();
        lblCantidad2 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jSeparator60 = new javax.swing.JSeparator();
        lblCostoProd2 = new javax.swing.JLabel();
        txtCostoProd = new javax.swing.JTextField();
        btnAgregarProd1 = new javax.swing.JButton();
        btnEliminarprod = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblCompra = new javax.swing.JTable();
        lblCodBarraProd3 = new javax.swing.JLabel();
        txtCodBarraProd = new javax.swing.JTextField();
        lblCodBarraProd6 = new javax.swing.JLabel();
        lblCodBarraProd9 = new javax.swing.JLabel();
        txtNumDocCompra = new javax.swing.JTextField();
        cmbSucursalCompra = new javax.swing.JComboBox();
        cmbProveedor = new javax.swing.JComboBox();
        txtSuma = new javax.swing.JTextField();
        lblSuma = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        lblIva = new javax.swing.JLabel();
        lblPercepcion = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        lblTotal7 = new javax.swing.JLabel();
        jSeparator71 = new javax.swing.JSeparator();
        lblCodBarraProd10 = new javax.swing.JLabel();
        cmbTipoCompra = new javax.swing.JComboBox();
        btnBuscarProdCompra = new javax.swing.JButton();
        txtPercepcion = new javax.swing.JTextField();
        txt_fecha_compra = new com.toedter.calendar.JDateChooser();
        jpnTipoPrecio = new javax.swing.JPanel();
        jPanel55 = new javax.swing.JPanel();
        jSeparator88 = new javax.swing.JSeparator();
        jLabel65 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblTP = new javax.swing.JTable();
        btnModificarTP = new javax.swing.JButton();
        btnBuscarTP = new javax.swing.JButton();
        txtBuscaTipoPrecio = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator51 = new javax.swing.JSeparator();
        btnTPNuevo = new javax.swing.JButton();
        btnTPEliminar = new javax.swing.JButton();
        jpnModificarSucursal = new javax.swing.JPanel();
        btnGuardarSuc = new javax.swing.JButton();
        btnAtrasModificarProducto1 = new javax.swing.JButton();
        jPanel54 = new javax.swing.JPanel();
        jSeparator79 = new javax.swing.JSeparator();
        jLabel56 = new javax.swing.JLabel();
        txtModIdSuc = new javax.swing.JTextField();
        txtModNombreSuc = new javax.swing.JTextField();
        txtTelSuc = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jSeparator84 = new javax.swing.JSeparator();
        jSeparator85 = new javax.swing.JSeparator();
        jSeparator86 = new javax.swing.JSeparator();
        jLabel64 = new javax.swing.JLabel();
        txtModDirSuc = new javax.swing.JTextField();
        jSeparator87 = new javax.swing.JSeparator();
        jpnNuevaSucursal = new javax.swing.JPanel();
        btnAgregarSuc = new javax.swing.JButton();
        btnAtrasSuc = new javax.swing.JButton();
        txtIdSuc = new javax.swing.JTextField();
        txtNombreSuc = new javax.swing.JTextField();
        jPanel53 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jSeparator74 = new javax.swing.JSeparator();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jSeparator75 = new javax.swing.JSeparator();
        jSeparator76 = new javax.swing.JSeparator();
        jSeparator77 = new javax.swing.JSeparator();
        jLabel55 = new javax.swing.JLabel();
        txtDireccionSuc = new javax.swing.JTextField();
        jSeparator78 = new javax.swing.JSeparator();
        txtTelefonoSuc = new javax.swing.JTextField();
        jpnSucursal = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblSucursal = new javax.swing.JTable();
        btnSucursalNueva = new javax.swing.JButton();
        btnSucursalBuscar = new javax.swing.JButton();
        btnModSucursal = new javax.swing.JButton();
        btnSucursalEliminar = new javax.swing.JButton();
        jPanel51 = new javax.swing.JPanel();
        jSeparator66 = new javax.swing.JSeparator();
        lblProveedores8 = new javax.swing.JLabel();
        jSeparator69 = new javax.swing.JSeparator();
        jLabel48 = new javax.swing.JLabel();
        txtSucursalBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSeparator70 = new javax.swing.JSeparator();
        jpnModificarPrecio = new javax.swing.JPanel();
        btnGuardarPar = new javax.swing.JButton();
        btnAtrasModPar = new javax.swing.JButton();
        jPanel56 = new javax.swing.JPanel();
        jSeparator89 = new javax.swing.JSeparator();
        jLabel73 = new javax.swing.JLabel();
        txtIdPar = new javax.swing.JTextField();
        txtNomPar = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jSeparator90 = new javax.swing.JSeparator();
        jSeparator91 = new javax.swing.JSeparator();
        jLabel76 = new javax.swing.JLabel();
        txtUtPar = new javax.swing.JTextField();
        jSeparator93 = new javax.swing.JSeparator();
        jpnNuevoPrecio = new javax.swing.JPanel();
        btnAgregarSuc1 = new javax.swing.JButton();
        btnAtrasSuc1 = new javax.swing.JButton();
        txtIdTP = new javax.swing.JTextField();
        txtNombrePrecio = new javax.swing.JTextField();
        jPanel57 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jSeparator92 = new javax.swing.JSeparator();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jSeparator94 = new javax.swing.JSeparator();
        jSeparator95 = new javax.swing.JSeparator();
        jLabel81 = new javax.swing.JLabel();
        txtUtilidadPrecio = new javax.swing.JTextField();
        jSeparator97 = new javax.swing.JSeparator();
        jpnConfiguracion = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        jSeparator96 = new javax.swing.JSeparator();
        jLabel80 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblParametro = new javax.swing.JTable();
        btnModificarParametro = new javax.swing.JButton();
        btnBuscarParametro = new javax.swing.JButton();
        txtBuscarParametro = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jSeparator57 = new javax.swing.JSeparator();
        jpnModificarParametro = new javax.swing.JPanel();
        btnGuardarPar1 = new javax.swing.JButton();
        btnAtrasModPar1 = new javax.swing.JButton();
        jPanel59 = new javax.swing.JPanel();
        jSeparator98 = new javax.swing.JSeparator();
        jLabel82 = new javax.swing.JLabel();
        txtIdPar1 = new javax.swing.JTextField();
        txtNomPar1 = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jSeparator99 = new javax.swing.JSeparator();
        jSeparator100 = new javax.swing.JSeparator();
        jLabel85 = new javax.swing.JLabel();
        txtValorPar = new javax.swing.JTextField();
        jSeparator101 = new javax.swing.JSeparator();
        jpnReporteMesVentas2 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tblListaVentasMes2 = new javax.swing.JTable();
        jPanel44 = new javax.swing.JPanel();
        jSeparator61 = new javax.swing.JSeparator();
        lblVentas3 = new javax.swing.JLabel();
        lblListadoVentas3 = new javax.swing.JLabel();
        jSeparator64 = new javax.swing.JSeparator();
        btnAtrasReporteVenta2 = new javax.swing.JButton();
        cmbMes = new javax.swing.JComboBox<>();
        jLabel96 = new javax.swing.JLabel();
        jSeparator103 = new javax.swing.JSeparator();
        jLabel100 = new javax.swing.JLabel();
        jSeparator107 = new javax.swing.JSeparator();
        cmbAño = new javax.swing.JComboBox<>();
        btnGenerar = new javax.swing.JButton();
        jpnReporteMesCompras = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tblListaComprasMes = new javax.swing.JTable();
        jPanel60 = new javax.swing.JPanel();
        jSeparator65 = new javax.swing.JSeparator();
        lblVentas4 = new javax.swing.JLabel();
        lblListadoVentas4 = new javax.swing.JLabel();
        jSeparator67 = new javax.swing.JSeparator();
        btnAtrasReporteCompra = new javax.swing.JButton();
        cmbMes1 = new javax.swing.JComboBox<>();
        jLabel97 = new javax.swing.JLabel();
        jSeparator104 = new javax.swing.JSeparator();
        jLabel101 = new javax.swing.JLabel();
        jSeparator108 = new javax.swing.JSeparator();
        cmbAño1 = new javax.swing.JComboBox<>();
        btnGenerar1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/iconos/lanzador.png")).getImage());
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpnBarraSuperior.setBackground(new java.awt.Color(0, 0, 0));
        jpnBarraSuperior.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpnBarraSuperior.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jpnBarraSuperiorMouseDragged(evt);
            }
        });
        jpnBarraSuperior.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpnBarraSuperiorMousePressed(evt);
            }
        });
        jpnBarraSuperior.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBotonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/exit32.png"))); // NOI18N
        lblBotonCerrar.setToolTipText("Salir");
        lblBotonCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblBotonCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBotonCerrarMouseClicked(evt);
            }
        });
        jpnBarraSuperior.add(lblBotonCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 0, 30, 50));

        lblLogo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(255, 255, 255));
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/lanzador.png"))); // NOI18N
        lblLogo.setText("iShop");
        lblLogo.setToolTipText("");
        jpnBarraSuperior.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 110, 50));
        jpnBarraSuperior.add(panelCurves1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, -1, -1));

        getContentPane().add(jpnBarraSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 55));

        jpnBarraMenu.setBackground(new java.awt.Color(102, 0, 0));
        jpnBarraMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMenu.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        lblMenu.setForeground(new java.awt.Color(255, 255, 255));
        lblMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/mas32.png"))); // NOI18N
        lblMenu.setText("Menu");
        jpnBarraMenu.add(lblMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 15, 90, 50));

        jpnSubMenu.setBackground(new java.awt.Color(102, 0, 0));
        jpnSubMenu.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpnSubMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/compras.png"))); // NOI18N
        btnCompras.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComprasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnComprasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnComprasMouseExited(evt);
            }
        });
        btnCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprasActionPerformed(evt);
            }
        });
        jpnSubMenu.add(btnCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(-126, 20, 180, 40));

        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/productos.png"))); // NOI18N
        btnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnProductosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnProductosMouseExited(evt);
            }
        });
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        jpnSubMenu.add(btnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(-126, 120, 180, 40));

        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ventas.png"))); // NOI18N
        btnVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVentasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVentasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVentasMouseExited(evt);
            }
        });
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });
        jpnSubMenu.add(btnVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(-126, 70, 180, 40));

        btnProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/proveedores.png"))); // NOI18N
        btnProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProveedoresMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnProveedoresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnProveedoresMouseExited(evt);
            }
        });
        btnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresActionPerformed(evt);
            }
        });
        jpnSubMenu.add(btnProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(-130, 170, 180, 40));

        btnAbrirPara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/configuracion.png"))); // NOI18N
        btnAbrirPara.setToolTipText("");
        btnAbrirPara.setBorder(null);
        btnAbrirPara.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAbrirParaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAbrirParaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAbrirParaMouseExited(evt);
            }
        });
        btnAbrirPara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirParaActionPerformed(evt);
            }
        });
        jpnSubMenu.add(btnAbrirPara, new org.netbeans.lib.awtextra.AbsoluteConstraints(-130, 310, 180, 40));

        btnAbrirSuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sucursales.png"))); // NOI18N
        btnAbrirSuc.setBorder(null);
        btnAbrirSuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAbrirSucMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAbrirSucMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAbrirSucMouseExited(evt);
            }
        });
        jpnSubMenu.add(btnAbrirSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(-130, 220, 180, 40));

        btnAbrirTP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/precios.png"))); // NOI18N
        btnAbrirTP.setBorder(null);
        btnAbrirTP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAbrirTPMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAbrirTPMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAbrirTPMouseExited(evt);
            }
        });
        btnAbrirTP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirTPActionPerformed(evt);
            }
        });
        jpnSubMenu.add(btnAbrirTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(-130, 270, 180, 40));

        jpnBarraMenu.add(jpnSubMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 77, 190, 400));

        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Home48.png"))); // NOI18N
        btnHome.setToolTipText("Inicio");
        btnHome.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
        });
        jpnBarraMenu.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 540, -1, -1));

        getContentPane().add(jpnBarraMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 190, 600));

        jpnPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        jpnPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpnPrimero.setBackground(new java.awt.Color(0, 0, 0));
        jpnPrimero.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Entypo_e776(0)_64.png"))); // NOI18N
        jpnPrimero.add(lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 70, 60));

        lbl4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl4.setForeground(new java.awt.Color(255, 255, 255));
        lbl4.setText("iShop");
        jpnPrimero.add(lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 150, 50, -1));

        lbl7.setBackground(new java.awt.Color(153, 153, 153));
        lbl7.setForeground(new java.awt.Color(102, 102, 102));
        lbl7.setText("Versión 1.0");
        jpnPrimero.add(lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 570, 70, -1));

        lbl5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl5.setForeground(new java.awt.Color(102, 102, 102));
        lbl5.setText("Te damos la bienvenida a tu");
        jpnPrimero.add(lbl5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, -1, -1));

        lbl6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl6.setForeground(new java.awt.Color(102, 102, 102));
        lbl6.setText("Wilfredo Enrique Garcia");
        jpnPrimero.add(lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 410, -1, 20));

        lblMitad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mitad.jpg"))); // NOI18N
        jpnPrimero.add(lblMitad, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 600));

        lbl8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl8.setForeground(new java.awt.Color(102, 102, 102));
        lbl8.setText("nuevo sistema de Tienda.");
        jpnPrimero.add(lbl8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, -1, 20));

        lbl9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl9.setForeground(new java.awt.Color(102, 102, 102));
        lbl9.setText("Integrantes:");
        jpnPrimero.add(lbl9, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 290, -1, 20));

        lbl10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl10.setForeground(new java.awt.Color(102, 102, 102));
        lbl10.setText("Rogelio Antonio Alvarez Aragon");
        jpnPrimero.add(lbl10, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 320, -1, 20));

        lbl16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl16.setForeground(new java.awt.Color(102, 102, 102));
        lbl16.setText("Francisco Nahum Castro Solis");
        jpnPrimero.add(lbl16, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 350, -1, 20));

        lbl17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl17.setForeground(new java.awt.Color(102, 102, 102));
        lbl17.setText("Walter Hazael Lopez Diaz");
        jpnPrimero.add(lbl17, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 380, -1, 20));

        jpnPrincipal.add(jpnPrimero, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 730, 600));

        jpnSegundo.setBackground(new java.awt.Color(0, 0, 0));
        jpnSegundo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl11.setForeground(new java.awt.Color(255, 255, 255));
        lbl11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Entypo_e73d(0)_32.png"))); // NOI18N
        lbl11.setText("Compras");
        jpnSegundo.add(lbl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 120, -1));

        lbl12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl12.setForeground(new java.awt.Color(102, 102, 102));
        lbl12.setText("Podrás realizar compras y ");
        jpnSegundo.add(lbl12, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, -1, -1));

        lbl13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl13.setForeground(new java.awt.Color(102, 102, 102));
        lbl13.setText("abastecer tu Tienda.");
        jpnSegundo.add(lbl13, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, -1, 30));

        lbl14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl14.setForeground(new java.awt.Color(102, 102, 102));
        lbl14.setText("Usa esta opción para manejar");
        jpnSegundo.add(lbl14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, 190, -1));

        lbl15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl15.setForeground(new java.awt.Color(102, 102, 102));
        lbl15.setText("el sistema de Compras.");
        jpnSegundo.add(lbl15, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, 30));

        lblMitad2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mitad2.jpg"))); // NOI18N
        jpnSegundo.add(lblMitad2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 370, 600));

        jpnPrincipal.add(jpnSegundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 730, 600));

        jpnTercero.setBackground(new java.awt.Color(0, 0, 0));
        jpnTercero.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl21.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl21.setForeground(new java.awt.Color(255, 255, 255));
        lbl21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Entypo_e789(2)_32.png"))); // NOI18N
        lbl21.setText("Ventas");
        jpnTercero.add(lbl21, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 120, -1));

        lbl22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl22.setForeground(new java.awt.Color(102, 102, 102));
        lbl22.setText("Podrás manejar los ingresos");
        jpnTercero.add(lbl22, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, -1, -1));

        lbl23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl23.setForeground(new java.awt.Color(102, 102, 102));
        lbl23.setText("que obtiene tu tienda.");
        jpnTercero.add(lbl23, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 260, -1, 30));

        lbl24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl24.setForeground(new java.awt.Color(102, 102, 102));
        lbl24.setText("Usa esta opción y maneja");
        jpnTercero.add(lbl24, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, 160, -1));

        lbl25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl25.setForeground(new java.awt.Color(102, 102, 102));
        lbl25.setText("el sistema de Ventas.");
        jpnTercero.add(lbl25, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 310, -1, 30));

        lblMitad3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mitad3.jpg"))); // NOI18N
        lblMitad3.setText("jLabel2");
        jpnTercero.add(lblMitad3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 600));

        jpnPrincipal.add(jpnTercero, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 730, 600));

        jpnCuarto.setBackground(new java.awt.Color(0, 0, 0));
        jpnCuarto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl31.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl31.setForeground(new java.awt.Color(255, 255, 255));
        lbl31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Entypo_e738(1)_32.png"))); // NOI18N
        lbl31.setText("Productos");
        jpnCuarto.add(lbl31, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 130, -1));

        lbl32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl32.setForeground(new java.awt.Color(102, 102, 102));
        lbl32.setText("Podrás manejar los productos");
        jpnCuarto.add(lbl32, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 190, -1));

        lbl33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl33.setForeground(new java.awt.Color(102, 102, 102));
        lbl33.setText("de tu sistema de Tienda.");
        jpnCuarto.add(lbl33, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, -1, 30));

        lbl34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl34.setForeground(new java.awt.Color(102, 102, 102));
        lbl34.setText("Usa esta opción para modificar,");
        jpnCuarto.add(lbl34, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, 200, -1));

        lbl35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl35.setForeground(new java.awt.Color(102, 102, 102));
        lbl35.setText("agregar o eliminar Productos.");
        jpnCuarto.add(lbl35, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, 190, 30));

        lblMitad4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mitad4.jpg"))); // NOI18N
        jpnCuarto.add(lblMitad4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 360, 600));

        jpnPrincipal.add(jpnCuarto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 730, 600));

        jpnQuinto.setBackground(new java.awt.Color(0, 0, 0));
        jpnQuinto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl41.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl41.setForeground(new java.awt.Color(255, 255, 255));
        lbl41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Entypo_e78e(0)_32.png"))); // NOI18N
        lbl41.setText("Proveedores");
        jpnQuinto.add(lbl41, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 160, -1));

        lbl42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl42.setForeground(new java.awt.Color(102, 102, 102));
        lbl42.setText("¿Deseas nuevas alianzas?");
        jpnQuinto.add(lbl42, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, -1, -1));

        lbl43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl43.setForeground(new java.awt.Color(102, 102, 102));
        lbl43.setText("¡Estamos para eso!");
        jpnQuinto.add(lbl43, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 260, -1, 30));

        lbl44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl44.setForeground(new java.awt.Color(102, 102, 102));
        lbl44.setText("Usa esta opción y agrega");
        jpnQuinto.add(lbl44, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, 160, -1));

        lbl45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl45.setForeground(new java.awt.Color(102, 102, 102));
        lbl45.setText("a tus nuevos proveedores.");
        jpnQuinto.add(lbl45, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 310, -1, 30));

        lblMitad5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mitad5.jpg"))); // NOI18N
        jpnQuinto.add(lblMitad5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 600));

        jpnPrincipal.add(jpnQuinto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 730, 600));

        getContentPane().add(jpnPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 55, 750, 595));

        jpnProveedores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEliminarProveedor.setBackground(new java.awt.Color(0, 0, 0));
        btnEliminarProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEliminarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btnEliminarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarProveedorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarProveedorMouseExited(evt);
            }
        });
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });
        jpnProveedores.add(btnEliminarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 520, 110, 30));

        btnAgregarProveedor.setBackground(new java.awt.Color(0, 0, 0));
        btnAgregarProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAgregarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregarprov.png"))); // NOI18N
        btnAgregarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregarProveedor.setFocusCycleRoot(true);
        btnAgregarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarProveedorMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarProveedorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarProveedorMouseExited(evt);
            }
        });
        btnAgregarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProveedorActionPerformed(evt);
            }
        });
        jpnProveedores.add(btnAgregarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, 110, 30));

        btnModificarProveedor.setBackground(new java.awt.Color(0, 0, 0));
        btnModificarProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnModificarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModificarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarProveedorMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarProveedorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarProveedorMouseExited(evt);
            }
        });
        btnModificarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarProveedorActionPerformed(evt);
            }
        });
        jpnProveedores.add(btnModificarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 520, 110, 30));

        tblProveedores =new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idProveedor", "Nombre", "Teléfono", "Dirección", "NIT", "e-mail"
            }
        ));
        tblProveedores.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblProveedores);

        jpnProveedores.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 670, 260));

        jPanel42.setBackground(new java.awt.Color(0, 0, 0));
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator20.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel42.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, -1, 50));

        lblProveedores6.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores6.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores6.setText("Proveedores");
        jPanel42.add(lblProveedores6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 120, 30));

        jpnProveedores.add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Listado de los Proveedores actuales:");
        jpnProveedores.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 175, -1, -1));
        jpnProveedores.add(jSeparator21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 230, -1));

        txtProductosBuscar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductosBuscar1KeyTyped(evt);
            }
        });
        jpnProveedores.add(txtProductosBuscar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 670, 30));
        jpnProveedores.add(jSeparator38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 120, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Proveedor a buscar:");
        jpnProveedores.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 85, -1, -1));

        getContentPane().add(jpnProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnAgregarProv.setPreferredSize(new java.awt.Dimension(95, 95));
        jpnAgregarProv.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarprov.png"))); // NOI18N
        btnGuardarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarProveedorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarProveedorMouseExited(evt);
            }
        });
        btnGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedorActionPerformed(evt);
            }
        });
        jpnAgregarProv.add(btnGuardarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 550, 110, 30));

        btnAtrasProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAtrasProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasProveedoresMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasProveedoresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasProveedoresMouseExited(evt);
            }
        });
        btnAtrasProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasProveedoresActionPerformed(evt);
            }
        });
        jpnAgregarProv.add(btnAtrasProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 550, 110, 30));

        txtDireccionProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionProveedorKeyTyped(evt);
            }
        });
        jpnAgregarProv.add(txtDireccionProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, 410, 30));

        txtNIT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNITKeyTyped(evt);
            }
        });
        jpnAgregarProv.add(txtNIT, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 290, 230, 30));

        txtNombreProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProveedorKeyTyped(evt);
            }
        });
        jpnAgregarProv.add(txtNombreProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 170, 410, 30));

        txtTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedorKeyTyped(evt);
            }
        });
        jpnAgregarProv.add(txtTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 230, 30));

        jPanel45.setBackground(new java.awt.Color(0, 0, 0));
        jPanel45.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator16.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel45.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Agrega un nuevo proveedor:");
        jPanel45.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 10, 290, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("ID:");
        jPanel45.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, -1, 30));

        txtIDProveedor.setEditable(false);
        jPanel45.add(txtIDProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 90, 30));

        jpnAgregarProv.add(jPanel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Nombre:");
        jpnAgregarProv.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, -1, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Teléfono:");
        jpnAgregarProv.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, -1, 20));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Dirección:");
        jpnAgregarProv.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, -1, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("NIT:");
        jpnAgregarProv.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, -1, 20));
        jpnAgregarProv.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 30, 10));
        jpnAgregarProv.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 50, 10));
        jpnAgregarProv.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, 60, 10));
        jpnAgregarProv.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 60, 10));

        txtNRCProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNRCProveedorKeyTyped(evt);
            }
        });
        jpnAgregarProv.add(txtNRCProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 400, 230, 30));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("NRC:");
        jpnAgregarProv.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 400, -1, 20));
        jpnAgregarProv.add(jSeparator22, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 420, 60, 10));

        txtEmailProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmailProveedorKeyTyped(evt);
            }
        });
        jpnAgregarProv.add(txtEmailProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 450, 230, 30));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Email:");
        jpnAgregarProv.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 450, -1, 20));
        jpnAgregarProv.add(jSeparator23, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 470, 60, 10));

        getContentPane().add(jpnAgregarProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnModificarProveedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardarModificarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarprov.png"))); // NOI18N
        btnGuardarModificarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardarModificarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarModificarProveedorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarModificarProveedorMouseExited(evt);
            }
        });
        btnGuardarModificarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarModificarProveedorActionPerformed(evt);
            }
        });
        jpnModificarProveedor.add(btnGuardarModificarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 550, 110, 30));

        btnAtrasModificarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasModificarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAtrasModificarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasModificarProveedorMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasModificarProveedorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasModificarProveedorMouseExited(evt);
            }
        });
        jpnModificarProveedor.add(btnAtrasModificarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 550, 110, 30));

        txtNuevoDireccionProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoDireccionProveedorKeyTyped(evt);
            }
        });
        jpnModificarProveedor.add(txtNuevoDireccionProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 410, 30));

        txtNuevoNIT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoNITKeyTyped(evt);
            }
        });
        jpnModificarProveedor.add(txtNuevoNIT, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, 230, 30));

        txtNuevoNombreProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtNuevoNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoNombreProveedorKeyTyped(evt);
            }
        });
        jpnModificarProveedor.add(txtNuevoNombreProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 410, 30));

        txtNuevoTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoTelefonoProveedorKeyTyped(evt);
            }
        });
        jpnModificarProveedor.add(txtNuevoTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 230, 30));

        jPanel48.setBackground(new java.awt.Color(0, 0, 0));
        jPanel48.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator40.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel48.add(jSeparator40, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Modifica un proveedor:");
        jPanel48.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 230, 30));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("ID:");
        jPanel48.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, -1, 30));

        txtIDProveedor1.setEditable(false);
        jPanel48.add(txtIDProveedor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 90, 30));

        jpnModificarProveedor.add(jPanel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Nombre:");
        jpnModificarProveedor.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, -1, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Teléfono:");
        jpnModificarProveedor.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, -1, 20));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Dirección:");
        jpnModificarProveedor.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, -1, 20));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("NIT:");
        jpnModificarProveedor.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, -1, 20));
        jpnModificarProveedor.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 30, 10));
        jpnModificarProveedor.add(jSeparator41, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 50, 10));
        jpnModificarProveedor.add(jSeparator42, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, 60, 10));
        jpnModificarProveedor.add(jSeparator43, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 60, 10));

        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmailKeyTyped(evt);
            }
        });
        jpnModificarProveedor.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 420, 230, 30));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("Email:");
        jpnModificarProveedor.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 420, -1, 20));
        jpnModificarProveedor.add(jSeparator82, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 440, 30, 10));

        txtNuevoNRC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoNRCKeyTyped(evt);
            }
        });
        jpnModificarProveedor.add(txtNuevoNRC, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 230, 30));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("NRC:");
        jpnModificarProveedor.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, -1, 20));
        jpnModificarProveedor.add(jSeparator24, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, 30, 10));

        getContentPane().add(jpnModificarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnCompras.setName("jpnCompras"); // NOI18N
        jpnCompras.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblCompras =new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblCompras.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdCompra", "Tipo Compra", "IdSucursal", "Fecha", "IdProveedor", "Total"
            }
        ));
        tblCompras.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(tblCompras);

        jpnCompras.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 650, 230));

        btnAgregarCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar.png"))); // NOI18N
        btnAgregarCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregarCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarCompraMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarCompraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarCompraMouseExited(evt);
            }
        });
        btnAgregarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCompraActionPerformed(evt);
            }
        });
        jpnCompras.add(btnAgregarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 380, 110, 30));

        btnVerDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/detalles2.png"))); // NOI18N
        btnVerDetalle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVerDetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVerDetalleMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVerDetalleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVerDetalleMouseExited(evt);
            }
        });
        btnVerDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerDetalleActionPerformed(evt);
            }
        });
        jpnCompras.add(btnVerDetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 380, 110, 30));

        jPanel37.setBackground(new java.awt.Color(0, 0, 0));
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator27.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel37.add(jSeparator27, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, -1, 50));

        lblProveedores3.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores3.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores3.setText("Compras");
        jPanel37.add(lblProveedores3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, 30));

        jpnCompras.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        lblListadoCompras.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblListadoCompras.setText("Listado de Compras Realizadas:");
        jpnCompras.add(lblListadoCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 200, -1));
        jpnCompras.add(jSeparator35, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 117, 200, 10));

        reporteComprabtn.setText("Reporte");
        reporteComprabtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reporteComprabtnActionPerformed(evt);
            }
        });
        jpnCompras.add(reporteComprabtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 380, 100, 30));

        getContentPane().add(jpnCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnProductos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtblProductos =new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        jtblProductos.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jtblProductos);

        jpnProductos.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 690, 220));

        btnNuevoProducto.setBackground(new java.awt.Color(0, 0, 0));
        btnNuevoProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo3.png"))); // NOI18N
        btnNuevoProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevoProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNuevoProductoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNuevoProductoMouseExited(evt);
            }
        });
        btnNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProductoActionPerformed(evt);
            }
        });
        jpnProductos.add(btnNuevoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 510, 110, 30));

        btnBuscarProducto.setBackground(new java.awt.Color(0, 0, 0));
        btnBuscarProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscar.png"))); // NOI18N
        btnBuscarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBuscarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscarProductoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBuscarProductoMouseExited(evt);
            }
        });
        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoActionPerformed(evt);
            }
        });
        jpnProductos.add(btnBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 180, 110, 30));

        btnModificarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModificarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarProductoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarProductoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarProductoMouseExited(evt);
            }
        });
        btnModificarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarProductoActionPerformed(evt);
            }
        });
        jpnProductos.add(btnModificarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 510, 110, 30));

        btnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btnEliminarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarProductoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarProductoMouseExited(evt);
            }
        });
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });
        jpnProductos.add(btnEliminarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 510, 110, 30));

        jPanel43.setBackground(new java.awt.Color(0, 0, 0));
        jPanel43.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator14.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel43.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        lblProveedores4.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores4.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores4.setText("Productos");
        jPanel43.add(lblProveedores4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 110, 30));

        jpnProductos.add(jPanel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 50));
        jpnProductos.add(jSeparator25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 160, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Listado de los Productos:");
        jpnProductos.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 225, -1, 30));

        txtProductosBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProductosBuscarKeyPressed(evt);
            }
        });
        jpnProductos.add(txtProductosBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 530, 30));
        jpnProductos.add(jSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 120, 20));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Producto a buscar:");
        jpnProductos.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, 40));

        cmbSucursales1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSucursales1ItemStateChanged(evt);
            }
        });
        jpnProductos.add(cmbSucursales1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 330, 30));

        lblSucursalProd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSucursalProd.setText("Sucursal:");
        jpnProductos.add(lblSucursalProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 60, 40));

        jSeparator53.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator53.setForeground(new java.awt.Color(0, 0, 0));
        jpnProductos.add(jSeparator53, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 730, 10));

        getContentPane().add(jpnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnNuevoProducto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAgregarNuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar2.png"))); // NOI18N
        btnAgregarNuevoProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregarNuevoProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarNuevoProductoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarNuevoProductoMouseExited(evt);
            }
        });
        btnAgregarNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarNuevoProductoActionPerformed(evt);
            }
        });
        jpnNuevoProducto.add(btnAgregarNuevoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 380, 110, 30));

        btnSalirProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnSalirProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSalirProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalirProductosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalirProductosMouseExited(evt);
            }
        });
        btnSalirProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirProductosActionPerformed(evt);
            }
        });
        jpnNuevoProducto.add(btnSalirProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 380, 110, 30));

        txtCodBarraProductos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBarraProductosFocusLost(evt);
            }
        });
        txtCodBarraProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodBarraProductosKeyTyped(evt);
            }
        });
        jpnNuevoProducto.add(txtCodBarraProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 230, 30));

        txtNombreProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProductosKeyTyped(evt);
            }
        });
        jpnNuevoProducto.add(txtNombreProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, 230, 30));

        txtPrecioProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProductosKeyTyped(evt);
            }
        });
        jpnNuevoProducto.add(txtPrecioProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 320, 90, 30));

        jPanel46.setBackground(new java.awt.Color(0, 0, 0));
        jPanel46.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(240, 240, 240));
        jLabel34.setText("Agregar un nuevo producto:");
        jPanel46.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 10, 290, 30));

        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel46.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jpnNuevoProducto.add(jPanel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 50));

        jLabel27.setBackground(new java.awt.Color(0, 0, 0));
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Código de barra:");
        jpnNuevoProducto.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, -1, 20));

        jLabel29.setBackground(new java.awt.Color(0, 0, 0));
        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Nombre:");
        jpnNuevoProducto.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, -1, 20));

        jLabel25.setBackground(new java.awt.Color(0, 0, 0));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Costo:");
        jpnNuevoProducto.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 60, 40));
        jpnNuevoProducto.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 100, 10));
        jpnNuevoProducto.add(jSeparator26, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, 70, 10));
        jpnNuevoProducto.add(jSeparator34, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 340, 40, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Inventario:");
        jpnNuevoProducto.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, -1, 20));

        txtProductoInventario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductoInventarioKeyTyped(evt);
            }
        });
        jpnNuevoProducto.add(txtProductoInventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 260, 90, 30));
        jpnNuevoProducto.add(jSeparator39, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 50, 20));

        jLabel38.setBackground(new java.awt.Color(0, 0, 0));
        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setText("Sucursal:");
        jpnNuevoProducto.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, -1, 20));
        jpnNuevoProducto.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 60, 10));

        cmbSucursal2.setEditable(true);
        cmbSucursal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSucursal2ActionPerformed(evt);
            }
        });
        jpnNuevoProducto.add(cmbSucursal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, 190, 30));

        getContentPane().add(jpnNuevoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnDetalleCompra.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtIdDetalleCompra.setEditable(false);
        jpnDetalleCompra.add(txtIdDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 80, 30));

        txtProveedorDetalleCompra.setEditable(false);
        txtProveedorDetalleCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProveedorDetalleCompraActionPerformed(evt);
            }
        });
        jpnDetalleCompra.add(txtProveedorDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 140, 30));

        jPanel47.setBackground(new java.awt.Color(0, 0, 0));
        jPanel47.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(240, 240, 240));
        jLabel36.setText("Detalle de la Compra:");
        jPanel47.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, 30));

        jSeparator28.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel47.add(jSeparator28, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jpnDetalleCompra.add(jPanel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        jLabel28.setBackground(new java.awt.Color(0, 0, 0));
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("ID Compra:");
        jpnDetalleCompra.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, 20));

        jLabel30.setBackground(new java.awt.Color(0, 0, 0));
        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("Proveedor:");
        jpnDetalleCompra.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, 30));

        jLabel26.setBackground(new java.awt.Color(0, 0, 0));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Fecha:");
        jpnDetalleCompra.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 60, 20));

        tblDetalleCompra =new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblDetalleCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Producto", "Producto", "Cantidad", "Costo", "Sub Total"
            }
        ));
        tblDetalleCompra.setEnabled(false);
        tblDetalleCompra.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tblDetalleCompra);

        jpnDetalleCompra.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 670, 230));

        jSeparator32.setForeground(new java.awt.Color(0, 0, 0));
        jpnDetalleCompra.add(jSeparator32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 750, 10));

        jLabel37.setBackground(new java.awt.Color(0, 0, 0));
        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setText("Total:   $");
        jpnDetalleCompra.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 560, 60, 20));

        txtFechaDetalleCompra.setEditable(false);
        jpnDetalleCompra.add(txtFechaDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 160, 30));

        txtTotalDetalleCompra.setEditable(false);
        jpnDetalleCompra.add(txtTotalDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 560, 100, 30));

        btnAtrasDetalleCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasDetalleCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAtrasDetalleCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasDetalleCompraMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasDetalleCompraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasDetalleCompraMouseExited(evt);
            }
        });
        jpnDetalleCompra.add(btnAtrasDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 110, 30));

        jLabel67.setBackground(new java.awt.Color(0, 0, 0));
        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel67.setText("Sucursal:");
        jpnDetalleCompra.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, 60, 20));

        txtSucursalDetalleCompra.setEditable(false);
        txtSucursalDetalleCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSucursalDetalleCompraActionPerformed(evt);
            }
        });
        jpnDetalleCompra.add(txtSucursalDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 70, 160, 30));

        txtNumDocumentoDetalleCompra.setEditable(false);
        txtNumDocumentoDetalleCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocumentoDetalleCompraActionPerformed(evt);
            }
        });
        jpnDetalleCompra.add(txtNumDocumentoDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 100, 30));

        jLabel68.setBackground(new java.awt.Color(0, 0, 0));
        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel68.setText("Num Documento:");
        jpnDetalleCompra.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, -1, 30));

        jLabel69.setBackground(new java.awt.Color(0, 0, 0));
        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel69.setText("SubTotal:   $");
        jpnDetalleCompra.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 440, 90, 30));

        txtSubTotalDetalleCompra.setEditable(false);
        jpnDetalleCompra.add(txtSubTotalDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 440, 100, 30));

        jLabel70.setBackground(new java.awt.Color(0, 0, 0));
        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel70.setText("IVA:   $");
        jpnDetalleCompra.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 480, 60, 20));

        txtIVADetalleCompra.setEditable(false);
        jpnDetalleCompra.add(txtIVADetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 480, 100, 30));

        jLabel71.setBackground(new java.awt.Color(0, 0, 0));
        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel71.setText("Percepcion:   $");
        jpnDetalleCompra.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 520, 100, 30));

        txtPercepcionDetalleCompra.setEditable(false);
        jpnDetalleCompra.add(txtPercepcionDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 520, 100, 30));

        txtTipoCompraDetalleCompra.setEditable(false);
        txtTipoCompraDetalleCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoCompraDetalleCompraActionPerformed(evt);
            }
        });
        jpnDetalleCompra.add(txtTipoCompraDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, 110, 30));

        jLabel72.setBackground(new java.awt.Color(0, 0, 0));
        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel72.setText("Tipo Compra:");
        jpnDetalleCompra.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, -1, 30));

        getContentPane().add(jpnDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnDetalleVenta.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtClienteDetalleVenta.setEditable(false);
        jpnDetalleVenta.add(txtClienteDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 210, 30));

        jPanel52.setBackground(new java.awt.Color(0, 0, 0));
        jPanel52.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(240, 240, 240));
        jLabel50.setText("Detalle de la Venta:");
        jPanel52.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, 30));

        jSeparator52.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel52.add(jSeparator52, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 20, 70));

        txtIdDetalleVenta.setEditable(false);
        jPanel52.add(txtIdDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 120, 30));

        jLabel57.setBackground(new java.awt.Color(0, 0, 0));
        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("ID Venta:");
        jPanel52.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, 20));

        jLabel86.setBackground(new java.awt.Color(0, 0, 0));
        jLabel86.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(255, 255, 255));
        jLabel86.setText("Num Documento:");
        jPanel52.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, 20));

        txtNoDocDetalleVenta.setEditable(false);
        jPanel52.add(txtNoDocDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 150, 30));

        txtFechaDetalleVenta.setEditable(false);
        jPanel52.add(txtFechaDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 160, 30));

        jLabel95.setBackground(new java.awt.Color(0, 0, 0));
        jLabel95.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(255, 255, 255));
        jLabel95.setText("Fecha:");
        jPanel52.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 120, 20));

        jpnDetalleVenta.add(jPanel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 70));

        jLabel58.setBackground(new java.awt.Color(0, 0, 0));
        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel58.setText("Cliente:");
        jpnDetalleVenta.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 30));

        tblDetalleVenta =new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo Barra", "Producto", "Cantidad", "Precio Unitario", "SubTotal"
            }
        ));
        tblDetalleVenta.setEnabled(false);
        tblDetalleVenta.getTableHeader().setReorderingAllowed(false);
        jScrollPane9.setViewportView(tblDetalleVenta);

        jpnDetalleVenta.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 670, 170));

        jSeparator73.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator73.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jpnDetalleVenta.add(jSeparator73, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 750, 10));

        jLabel60.setBackground(new java.awt.Color(0, 0, 0));
        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel60.setText("Total Gravado: $");
        jpnDetalleVenta.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 430, 110, 40));

        txtTotalGravadoDetalleVenta.setEditable(false);
        jpnDetalleVenta.add(txtTotalGravadoDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 430, 100, 40));

        btnAtrasDetalleCompra1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasDetalleCompra1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAtrasDetalleCompra1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasDetalleCompra1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasDetalleCompra1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasDetalleCompra1MouseExited(evt);
            }
        });
        jpnDetalleVenta.add(btnAtrasDetalleCompra1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 110, 30));

        txtNITDetalleVenta.setEditable(false);
        jpnDetalleVenta.add(txtNITDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 170, 30));

        jLabel59.setBackground(new java.awt.Color(0, 0, 0));
        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel59.setText("NIT:");
        jpnDetalleVenta.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, -1, 30));

        txtNRCDetalleVenta.setEditable(false);
        jpnDetalleVenta.add(txtNRCDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 100, 30));

        jLabel87.setBackground(new java.awt.Color(0, 0, 0));
        jLabel87.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel87.setText("NRC:");
        jpnDetalleVenta.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, -1, 30));

        txtDireccionDetalleVenta.setEditable(false);
        jpnDetalleVenta.add(txtDireccionDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 270, 30));

        jLabel88.setBackground(new java.awt.Color(0, 0, 0));
        jLabel88.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel88.setText("Direccion:");
        jpnDetalleVenta.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, 30));

        txtGiroDetalleVenta.setEditable(false);
        jpnDetalleVenta.add(txtGiroDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 200, 30));

        jLabel89.setBackground(new java.awt.Color(0, 0, 0));
        jLabel89.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel89.setText("Giro:");
        jpnDetalleVenta.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, -1, 30));

        txtTipoVentaDetalleVenta.setEditable(false);
        jpnDetalleVenta.add(txtTipoVentaDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 130, 30));

        jLabel90.setBackground(new java.awt.Color(0, 0, 0));
        jLabel90.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel90.setText("Tipo de Venta:");
        jpnDetalleVenta.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 30));

        txtSucursalDetalleVenta.setEditable(false);
        jpnDetalleVenta.add(txtSucursalDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 130, 30));

        jLabel91.setBackground(new java.awt.Color(0, 0, 0));
        jLabel91.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel91.setText("Sucursal:");
        jpnDetalleVenta.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, -1, 30));

        txtTipoPrecioDetalleVenta.setEditable(false);
        jpnDetalleVenta.add(txtTipoPrecioDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 170, 170, 30));

        jLabel92.setBackground(new java.awt.Color(0, 0, 0));
        jLabel92.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel92.setText("Tipo Precio:");
        jpnDetalleVenta.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, -1, 30));

        jSeparator102.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator102.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jpnDetalleVenta.add(jSeparator102, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 750, 10));

        txtIVADetalleVenta.setEditable(false);
        jpnDetalleVenta.add(txtIVADetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 470, 100, 40));

        jLabel93.setBackground(new java.awt.Color(0, 0, 0));
        jLabel93.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel93.setText("IVA: $");
        jpnDetalleVenta.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 470, 50, 40));

        txtTotalDetalleVenta.setEditable(false);
        jpnDetalleVenta.add(txtTotalDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 510, 100, 40));

        jLabel94.setBackground(new java.awt.Color(0, 0, 0));
        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel94.setText("Total: $");
        jpnDetalleVenta.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 510, -1, 40));

        getContentPane().add(jpnDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnListaVentas.setName("jpnListaVentas"); // NOI18N
        jpnListaVentas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblListaVentas =new javax.swing.JTable(){ public boolean isCellEditable(int rowIndex, int colIndex){     return false; } };
        tblListaVentas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblListaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdVenta", "Fecha", "Cliente", "Iva", "Total"
            }
        ));
        tblListaVentas.setToolTipText("");
        tblListaVentas.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tblListaVentas);

        jpnListaVentas.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 660, 190));

        btnAgregarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar.png"))); // NOI18N
        btnAgregarVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregarVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarVentaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarVentaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarVentaMouseExited(evt);
            }
        });
        btnAgregarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarVentaActionPerformed(evt);
            }
        });
        jpnListaVentas.add(btnAgregarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 350, 110, 30));

        btnVerDetalleVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/detalles2.png"))); // NOI18N
        btnVerDetalleVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVerDetalleVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVerDetalleVentaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVerDetalleVentaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVerDetalleVentaMouseExited(evt);
            }
        });
        btnVerDetalleVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerDetalleVentaActionPerformed(evt);
            }
        });
        jpnListaVentas.add(btnVerDetalleVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 350, 110, 30));

        jPanel38.setBackground(new java.awt.Color(0, 0, 0));
        jPanel38.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator44.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel38.add(jSeparator44, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, -1, 50));

        lblVentas.setBackground(new java.awt.Color(255, 255, 255));
        lblVentas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblVentas.setForeground(new java.awt.Color(255, 255, 255));
        lblVentas.setText("Ventas");
        jPanel38.add(lblVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, 30));

        jpnListaVentas.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 50));

        lblListadoVentas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblListadoVentas.setText("Listado de Ventas Realizadas:");
        jpnListaVentas.add(lblListadoVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 200, 30));
        jpnListaVentas.add(jSeparator45, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 127, 180, 10));

        btnReport.setText("Reporte");
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });
        jpnListaVentas.add(btnReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, 90, 30));

        getContentPane().add(jpnListaVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnAgregarVenta.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator62.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator62.setForeground(new java.awt.Color(0, 0, 0));
        jpnAgregarVenta.add(jSeparator62, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 760, 10));

        tblProductosVender.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cod Barra", "Producto", "Cantidad", "Precio Unitario", "Iva", "Sub Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProductosVender.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tblProductosVender);

        jpnAgregarVenta.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 630, 190));

        btnVender.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnVender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/vender.png"))); // NOI18N
        btnVender.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVenderMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVenderMouseExited(evt);
            }
        });
        btnVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVenderActionPerformed(evt);
            }
        });
        jpnAgregarVenta.add(btnVender, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 500, 110, 30));

        btnEliminarProductoVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btnEliminarProductoVenta.setToolTipText("Eliminar Productos Seleccionados");
        btnEliminarProductoVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminarProductoVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarProductoVentaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarProductoVentaMouseExited(evt);
            }
        });
        btnEliminarProductoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoVentaActionPerformed(evt);
            }
        });
        jpnAgregarVenta.add(btnEliminarProductoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, 110, 30));

        txtNombreProductoVender.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNombreProductoVender.setEnabled(false);
        txtNombreProductoVender.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreProductoVenderKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProductoVenderKeyTyped(evt);
            }
        });
        jpnAgregarVenta.add(txtNombreProductoVender, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 330, 30));

        txtCantidadVender.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCantidadVender.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVenderKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVenderKeyTyped(evt);
            }
        });
        jpnAgregarVenta.add(txtCantidadVender, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 250, 70, 30));

        btnAgregarProductoVenta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAgregarProductoVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar2.png"))); // NOI18N
        btnAgregarProductoVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregarProductoVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarProductoVentaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarProductoVentaMouseExited(evt);
            }
        });
        btnAgregarProductoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoVentaActionPerformed(evt);
            }
        });
        jpnAgregarVenta.add(btnAgregarProductoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 250, 110, 30));

        jPanel50.setBackground(new java.awt.Color(0, 0, 0));
        jPanel50.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator63.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel50.add(jSeparator63, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 20, 70));

        lblProveedores7.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores7.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores7.setText("Ventas");
        jPanel50.add(lblProveedores7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 110, 50));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("ID Venta");
        jPanel50.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 70, -1));

        txtNoDocVenta.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtNoDocVenta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        txtNoDocVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoDocVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoDocVentaKeyTyped(evt);
            }
        });
        jPanel50.add(txtNoDocVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 210, 30));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("N° de Documento");
        jPanel50.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 120, -1));

        txtIdVenta.setEditable(false);
        txtIdVenta.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtIdVenta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jPanel50.add(txtIdVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 140, 30));

        jpnAgregarVenta.add(jPanel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 70));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel46.setText("Producto");
        jpnAgregarVenta.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, -1, -1));

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setText("Cantidad");
        jpnAgregarVenta.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 230, -1, -1));

        txtCodigoBarraVender.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoBarraVenderKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoBarraVenderKeyTyped(evt);
            }
        });
        jpnAgregarVenta.add(txtCodigoBarraVender, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 140, 30));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setText("Código de Barra");
        jpnAgregarVenta.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

        lblNITVenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNITVenta.setText("NIT:");
        jpnAgregarVenta.add(lblNITVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 30, 30));

        txtNITVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNITVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNITVentaKeyTyped(evt);
            }
        });
        jpnAgregarVenta.add(txtNITVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 140, 30));

        lblnrc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblnrc.setText("NRC:");
        jpnAgregarVenta.add(lblnrc, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, 30, 30));

        lblgiro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblgiro.setText("Giro:");
        jpnAgregarVenta.add(lblgiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 40, 30));

        txtNRCVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNRCVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNRCVentaKeyTyped(evt);
            }
        });
        jpnAgregarVenta.add(txtNRCVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 140, 30));

        txtDireccionVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDireccionVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionVentaKeyTyped(evt);
            }
        });
        jpnAgregarVenta.add(txtDireccionVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 210, 30));

        lblCodBarraProd18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCodBarraProd18.setText("Cliente:");
        jpnAgregarVenta.add(lblCodBarraProd18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 50, 30));

        lblCodBarraProd21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCodBarraProd21.setText("Tipo de Venta:");
        jpnAgregarVenta.add(lblCodBarraProd21, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 90, 30));

        txtClienteVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClienteVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtClienteVentaKeyTyped(evt);
            }
        });
        jpnAgregarVenta.add(txtClienteVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 160, 30));

        txtGiroVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGiroVentaKeyTyped(evt);
            }
        });
        jpnAgregarVenta.add(txtGiroVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, 140, 30));

        cmbTipoVenta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Libre", "Credito Fiscal", "Factura" }));
        cmbTipoVenta.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTipoVentaItemStateChanged(evt);
            }
        });
        jpnAgregarVenta.add(cmbTipoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, 110, 30));

        lblCodBarraProd22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCodBarraProd22.setText("Direccion:");
        jpnAgregarVenta.add(lblCodBarraProd22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 70, 30));
        jpnAgregarVenta.add(cmbSucursalVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 140, 30));

        lblCodBarraProd23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCodBarraProd23.setText("Sucursal:");
        jpnAgregarVenta.add(lblCodBarraProd23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 60, 30));
        jpnAgregarVenta.add(txtSumaVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 500, 100, 30));

        lblSumaVenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSumaVenta.setText("Suma :  $");
        jpnAgregarVenta.add(lblSumaVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 510, 70, 10));
        jpnAgregarVenta.add(txtIvaVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 530, 100, 30));

        lblIvaVenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIvaVenta.setText("Iva :   $");
        jpnAgregarVenta.add(lblIvaVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 530, 50, 30));
        jpnAgregarVenta.add(txtTotalVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 560, 100, 30));

        lblTotalVenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotalVenta.setText("TOTAL:   $");
        jpnAgregarVenta.add(lblTotalVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 570, 70, 10));

        jSeparator68.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator68.setForeground(new java.awt.Color(0, 0, 0));
        jpnAgregarVenta.add(jSeparator68, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 760, 10));

        jSeparator72.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator72.setForeground(new java.awt.Color(0, 0, 0));
        jpnAgregarVenta.add(jSeparator72, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 760, 10));

        lblCodBarraProd24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCodBarraProd24.setText("Tipo Precio:");
        jpnAgregarVenta.add(lblCodBarraProd24, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 80, 30));

        cmbTipoPrecioVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoPrecioVentaActionPerformed(evt);
            }
        });
        jpnAgregarVenta.add(cmbTipoPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 170, 140, 30));

        lblFecha3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFecha3.setText("Fecha:");
        jpnAgregarVenta.add(lblFecha3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, 50, 30));

        txt_fecha_venta.setDateFormatString("MM/dd/yyyy HH:mm:ss");
        jpnAgregarVenta.add(txt_fecha_venta, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 160, 30));

        getContentPane().add(jpnAgregarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnModificarProducto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardarModificarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarprov.png"))); // NOI18N
        btnGuardarModificarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardarModificarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarModificarProductoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarModificarProductoMouseExited(evt);
            }
        });
        btnGuardarModificarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarModificarProductoActionPerformed(evt);
            }
        });
        jpnModificarProducto.add(btnGuardarModificarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 450, 110, 30));

        btnAtrasModificarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasModificarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAtrasModificarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasModificarProductoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasModificarProductoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasModificarProductoMouseExited(evt);
            }
        });
        jpnModificarProducto.add(btnAtrasModificarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 450, 110, 30));

        txtNuevoInventarioProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoInventarioProductoKeyTyped(evt);
            }
        });
        jpnModificarProducto.add(txtNuevoInventarioProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 230, 30));

        txtNuevoCostoProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoCostoProductoKeyTyped(evt);
            }
        });
        jpnModificarProducto.add(txtNuevoCostoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 230, 30));

        txtNuevoCodigoBarra.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtNuevoCodigoBarra.setEnabled(false);
        txtNuevoCodigoBarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoCodigoBarraKeyTyped(evt);
            }
        });
        jpnModificarProducto.add(txtNuevoCodigoBarra, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 230, 30));

        txtNuevoNombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevoNombreProductoActionPerformed(evt);
            }
        });
        txtNuevoNombreProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoNombreProductoKeyTyped(evt);
            }
        });
        jpnModificarProducto.add(txtNuevoNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 230, 30));

        jPanel49.setBackground(new java.awt.Color(0, 0, 0));
        jPanel49.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator46.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel49.add(jSeparator46, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Modificar un producto:");
        jPanel49.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 320, 30));

        jpnModificarProducto.add(jPanel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 50));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setText("Codigo de barra:");
        jpnModificarProducto.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, -1, 20));

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel40.setText("Nombre:");
        jpnModificarProducto.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, -1, 20));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel41.setText("Inventario:");
        jpnModificarProducto.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, -1, 20));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setText("Costo:");
        jpnModificarProducto.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 350, -1, 20));
        jpnModificarProducto.add(jSeparator47, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 60, 10));
        jpnModificarProducto.add(jSeparator48, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 110, 10));
        jpnModificarProducto.add(jSeparator49, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, 60, 10));
        jpnModificarProducto.add(jSeparator50, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 70, 10));

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel66.setText("Sucursal:");
        jpnModificarProducto.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, 20));
        jpnModificarProducto.add(jSeparator83, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 60, 10));

        txtNuevaSucursalProd.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtNuevaSucursalProd.setEnabled(false);
        txtNuevaSucursalProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevaSucursalProdKeyTyped(evt);
            }
        });
        jpnModificarProducto.add(txtNuevaSucursalProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 230, 30));

        getContentPane().add(jpnModificarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnRegistroCompra.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarprov.png"))); // NOI18N
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                btnGuardarMouseDragged(evt);
            }
        });
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jpnRegistroCompra.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 480, 100, 30));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });
        jpnRegistroCompra.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 480, 110, 30));

        txtIdCompra.setEnabled(false);
        txtIdCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdCompraActionPerformed(evt);
            }
        });
        jpnRegistroCompra.add(txtIdCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 70, 30));

        jPanel41.setBackground(new java.awt.Color(0, 0, 0));
        jPanel41.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(240, 240, 240));
        jLabel43.setText("Agregar una Compra:");
        jPanel41.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 430, 30));

        jSeparator56.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel41.add(jSeparator56, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));
        jPanel41.add(panelCurves2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -90, 730, 160));

        jpnRegistroCompra.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 50));

        lblFecha2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFecha2.setText("Fecha:");
        jpnRegistroCompra.add(lblFecha2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 50, 30));

        lblIdCompra2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIdCompra2.setText("Id Compra:");
        jpnRegistroCompra.add(lblIdCompra2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 90, 30));

        lblProveedor2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblProveedor2.setText("Proveedor:");
        jpnRegistroCompra.add(lblProveedor2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 90, 30));

        lblNomProd2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNomProd2.setText("Producto:");
        jpnRegistroCompra.add(lblNomProd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 170, 70, 30));

        txtNomProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomProdKeyTyped(evt);
            }
        });
        jpnRegistroCompra.add(txtNomProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 160, 30));

        lblCantidad2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCantidad2.setText("Cantidad:");
        jpnRegistroCompra.add(lblCantidad2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 60, 30));

        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        jpnRegistroCompra.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 170, 50, 30));

        jSeparator60.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator60.setForeground(new java.awt.Color(0, 0, 0));
        jpnRegistroCompra.add(jSeparator60, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 720, 10));

        lblCostoProd2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCostoProd2.setText("Costo:");
        jpnRegistroCompra.add(lblCostoProd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 170, 60, 30));

        txtCostoProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCostoProdKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoProdKeyTyped(evt);
            }
        });
        jpnRegistroCompra.add(txtCostoProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 170, 60, 30));

        btnAgregarProd1.setBackground(new java.awt.Color(0, 0, 0));
        btnAgregarProd1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAgregarProd1.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarProd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregarprov.png"))); // NOI18N
        btnAgregarProd1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregarProd1.setFocusCycleRoot(true);
        btnAgregarProd1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarProd1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarProd1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarProd1MouseExited(evt);
            }
        });
        btnAgregarProd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProd1ActionPerformed(evt);
            }
        });
        jpnRegistroCompra.add(btnAgregarProd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 230, 100, 30));

        btnEliminarprod.setBackground(new java.awt.Color(0, 0, 0));
        btnEliminarprod.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEliminarprod.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarprod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btnEliminarprod.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminarprod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarprodMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarprodMouseExited(evt);
            }
        });
        btnEliminarprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarprodActionPerformed(evt);
            }
        });
        jpnRegistroCompra.add(btnEliminarprod, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 480, 100, 30));

        tblCompra =new javax.swing.JTable(){ public boolean isCellEditable(int rowIndex, int colIndex){     return false; } };
        tblCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id producto", "Producto", "Cantidad", "Costo", "Iva", "Sub Total"
            }
        ));
        tblCompra.getTableHeader().setReorderingAllowed(false);
        jScrollPane10.setViewportView(tblCompra);

        jpnRegistroCompra.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 600, 190));

        lblCodBarraProd3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCodBarraProd3.setText("Cod Barra:");
        jpnRegistroCompra.add(lblCodBarraProd3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 80, 30));

        txtCodBarraProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodBarraProdKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodBarraProdKeyTyped(evt);
            }
        });
        jpnRegistroCompra.add(txtCodBarraProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 120, 30));

        lblCodBarraProd6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCodBarraProd6.setText("N° de Documento:");
        jpnRegistroCompra.add(lblCodBarraProd6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 130, 30));

        lblCodBarraProd9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCodBarraProd9.setText("Sucursal:");
        jpnRegistroCompra.add(lblCodBarraProd9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, 80, 30));

        txtNumDocCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumDocCompraKeyTyped(evt);
            }
        });
        jpnRegistroCompra.add(txtNumDocCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 120, 30));
        jpnRegistroCompra.add(cmbSucursalCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, 140, 30));

        cmbProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProveedorActionPerformed(evt);
            }
        });
        jpnRegistroCompra.add(cmbProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 180, 30));

        txtSuma.setFocusable(false);
        jpnRegistroCompra.add(txtSuma, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 470, 100, 30));

        lblSuma.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSuma.setText("Suma :  $");
        jpnRegistroCompra.add(lblSuma, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 480, 60, 10));

        txtIva.setFocusable(false);
        jpnRegistroCompra.add(txtIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 500, 100, 30));

        lblIva.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIva.setText("Iva :   $");
        jpnRegistroCompra.add(lblIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 500, 50, 30));

        lblPercepcion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPercepcion.setText("Percepcion: $");
        jpnRegistroCompra.add(lblPercepcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 530, 90, 30));

        txtTotal.setFocusable(false);
        jpnRegistroCompra.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 560, 100, 30));

        lblTotal7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotal7.setText("Total:   $");
        jpnRegistroCompra.add(lblTotal7, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 560, -1, 30));

        jSeparator71.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator71.setForeground(new java.awt.Color(0, 0, 0));
        jpnRegistroCompra.add(jSeparator71, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 720, 10));

        lblCodBarraProd10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCodBarraProd10.setText("Tipo Compra:");
        jpnRegistroCompra.add(lblCodBarraProd10, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 110, 90, 30));

        cmbTipoCompra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Credito Fiscal", "Libre", "Factura" }));
        cmbTipoCompra.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTipoCompraItemStateChanged(evt);
            }
        });
        jpnRegistroCompra.add(cmbTipoCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, 110, 30));

        btnBuscarProdCompra.setBackground(new java.awt.Color(0, 0, 0));
        btnBuscarProdCompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBuscarProdCompra.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarProdCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscar.png"))); // NOI18N
        btnBuscarProdCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBuscarProdCompra.setFocusCycleRoot(true);
        btnBuscarProdCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarProdCompraMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscarProdCompraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBuscarProdCompraMouseExited(evt);
            }
        });
        btnBuscarProdCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProdCompraActionPerformed(evt);
            }
        });
        jpnRegistroCompra.add(btnBuscarProdCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 100, 30));

        txtPercepcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPercepcionActionPerformed(evt);
            }
        });
        txtPercepcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPercepcionKeyTyped(evt);
            }
        });
        jpnRegistroCompra.add(txtPercepcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 530, 100, 30));

        txt_fecha_compra.setDateFormatString("MM/dd/yyyy HH:mm:ss");
        jpnRegistroCompra.add(txt_fecha_compra, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 230, 30));

        getContentPane().add(jpnRegistroCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnTipoPrecio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel55.setBackground(new java.awt.Color(0, 0, 0));
        jPanel55.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator88.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel55.add(jSeparator88, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("Tipos de precio");
        jPanel55.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 250, 30));

        jpnTipoPrecio.add(jPanel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        tblTP =new javax.swing.JTable(){ public boolean isCellEditable(int rowIndex, int colIndex){     return false; } };
        tblTP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre Precio", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTP.getTableHeader().setReorderingAllowed(false);
        jScrollPane12.setViewportView(tblTP);

        jpnTipoPrecio.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 550, 120));

        btnModificarTP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificarTP.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModificarTP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarTPMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarTPMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarTPMouseExited(evt);
            }
        });
        btnModificarTP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarTPActionPerformed(evt);
            }
        });
        jpnTipoPrecio.add(btnModificarTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 110, 30));

        btnBuscarTP.setBackground(new java.awt.Color(0, 0, 0));
        btnBuscarTP.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarTP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscar.png"))); // NOI18N
        btnBuscarTP.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBuscarTP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscarTPMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBuscarTPMouseExited(evt);
            }
        });
        btnBuscarTP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarTPActionPerformed(evt);
            }
        });
        jpnTipoPrecio.add(btnBuscarTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 110, 30));

        txtBuscaTipoPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscaTipoPrecioKeyPressed(evt);
            }
        });
        jpnTipoPrecio.add(txtBuscaTipoPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 430, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Buscar tipo de precio:");
        jpnTipoPrecio.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));
        jpnTipoPrecio.add(jSeparator51, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 96, 120, 20));

        btnTPNuevo.setBackground(new java.awt.Color(0, 0, 0));
        btnTPNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnTPNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo3.png"))); // NOI18N
        btnTPNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnTPNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTPNuevoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTPNuevoMouseExited(evt);
            }
        });
        btnTPNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTPNuevoActionPerformed(evt);
            }
        });
        jpnTipoPrecio.add(btnTPNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 320, 110, 30));

        btnTPEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btnTPEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnTPEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTPEliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTPEliminarMouseExited(evt);
            }
        });
        btnTPEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTPEliminarActionPerformed(evt);
            }
        });
        jpnTipoPrecio.add(btnTPEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 110, 30));

        getContentPane().add(jpnTipoPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnModificarSucursal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardarSuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarprov.png"))); // NOI18N
        btnGuardarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardarSuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarSucMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarSucMouseExited(evt);
            }
        });
        btnGuardarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarSucActionPerformed(evt);
            }
        });
        jpnModificarSucursal.add(btnGuardarSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 350, 110, 30));

        btnAtrasModificarProducto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasModificarProducto1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAtrasModificarProducto1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasModificarProducto1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasModificarProducto1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasModificarProducto1MouseExited(evt);
            }
        });
        btnAtrasModificarProducto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasModificarProducto1ActionPerformed(evt);
            }
        });
        jpnModificarSucursal.add(btnAtrasModificarProducto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, 110, 30));

        jPanel54.setBackground(new java.awt.Color(0, 0, 0));
        jPanel54.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator79.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel54.add(jSeparator79, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Modifica una sucursal:");
        jPanel54.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 270, 30));

        jpnModificarSucursal.add(jPanel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 50));

        txtModIdSuc.setEnabled(false);
        jpnModificarSucursal.add(txtModIdSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 290, 30));

        txtModNombreSuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtModNombreSucKeyTyped(evt);
            }
        });
        jpnModificarSucursal.add(txtModNombreSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 290, 30));

        txtTelSuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelSucKeyTyped(evt);
            }
        });
        jpnModificarSucursal.add(txtTelSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, 160, 30));

        jLabel61.setBackground(new java.awt.Color(0, 0, 0));
        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel61.setText("ID Sucursal");
        jpnModificarSucursal.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, -1, 20));

        jLabel62.setBackground(new java.awt.Color(0, 0, 0));
        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel62.setText("Nombre:");
        jpnModificarSucursal.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, 20));

        jLabel63.setBackground(new java.awt.Color(0, 0, 0));
        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel63.setText("Telefono:");
        jpnModificarSucursal.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 60, 40));
        jpnModificarSucursal.add(jSeparator84, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 70, 10));
        jpnModificarSucursal.add(jSeparator85, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 70, 20));
        jpnModificarSucursal.add(jSeparator86, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 60, 30));

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel64.setText("Direccion:");
        jpnModificarSucursal.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, 20));

        txtModDirSuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtModDirSucKeyTyped(evt);
            }
        });
        jpnModificarSucursal.add(txtModDirSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 290, 30));
        jpnModificarSucursal.add(jSeparator87, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 50, 20));

        getContentPane().add(jpnModificarSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnNuevaSucursal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAgregarSuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar2.png"))); // NOI18N
        btnAgregarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregarSuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarSucMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarSucMouseExited(evt);
            }
        });
        btnAgregarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarSucActionPerformed(evt);
            }
        });
        jpnNuevaSucursal.add(btnAgregarSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 380, 110, 30));

        btnAtrasSuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAtrasSuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasSucMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasSucMouseExited(evt);
            }
        });
        btnAtrasSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasSucActionPerformed(evt);
            }
        });
        jpnNuevaSucursal.add(btnAtrasSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 380, 110, 30));

        txtIdSuc.setEditable(false);
        jpnNuevaSucursal.add(txtIdSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 110, 230, 30));

        txtNombreSuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreSucKeyTyped(evt);
            }
        });
        jpnNuevaSucursal.add(txtNombreSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, 230, 30));

        jPanel53.setBackground(new java.awt.Color(0, 0, 0));
        jPanel53.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(240, 240, 240));
        jLabel51.setText("Agregar una nueva sucursal:");
        jPanel53.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 300, 30));

        jSeparator74.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel53.add(jSeparator74, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jpnNuevaSucursal.add(jPanel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        jLabel52.setBackground(new java.awt.Color(0, 0, 0));
        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel52.setText("ID Sucursal");
        jpnNuevaSucursal.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, -1, 20));

        jLabel53.setBackground(new java.awt.Color(0, 0, 0));
        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel53.setText("Nombre:");
        jpnNuevaSucursal.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, -1, 20));

        jLabel54.setBackground(new java.awt.Color(0, 0, 0));
        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel54.setText("Telefono:");
        jpnNuevaSucursal.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 60, 40));
        jpnNuevaSucursal.add(jSeparator75, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 70, 10));
        jpnNuevaSucursal.add(jSeparator76, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 70, 20));
        jpnNuevaSucursal.add(jSeparator77, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 60, 30));

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel55.setText("Direccion:");
        jpnNuevaSucursal.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, -1, 20));

        txtDireccionSuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionSucKeyTyped(evt);
            }
        });
        jpnNuevaSucursal.add(txtDireccionSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, 230, 30));
        jpnNuevaSucursal.add(jSeparator78, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 50, 20));

        txtTelefonoSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoSucActionPerformed(evt);
            }
        });
        txtTelefonoSuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoSucKeyTyped(evt);
            }
        });
        jpnNuevaSucursal.add(txtTelefonoSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, 230, 30));

        getContentPane().add(jpnNuevaSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnSucursal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblSucursal =new javax.swing.JTable(){ public boolean isCellEditable(int rowIndex, int colIndex){     return false; } };
        tblSucursal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nombre", "Direccion", "telefono"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSucursal.getTableHeader().setReorderingAllowed(false);
        jScrollPane11.setViewportView(tblSucursal);

        jpnSucursal.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 650, 180));

        btnSucursalNueva.setBackground(new java.awt.Color(0, 0, 0));
        btnSucursalNueva.setForeground(new java.awt.Color(255, 255, 255));
        btnSucursalNueva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo3.png"))); // NOI18N
        btnSucursalNueva.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSucursalNueva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSucursalNuevaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSucursalNuevaMouseExited(evt);
            }
        });
        btnSucursalNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSucursalNuevaActionPerformed(evt);
            }
        });
        jpnSucursal.add(btnSucursalNueva, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 430, 110, 30));

        btnSucursalBuscar.setBackground(new java.awt.Color(0, 0, 0));
        btnSucursalBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnSucursalBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscar.png"))); // NOI18N
        btnSucursalBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSucursalBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSucursalBuscarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSucursalBuscarMouseExited(evt);
            }
        });
        btnSucursalBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSucursalBuscarActionPerformed(evt);
            }
        });
        jpnSucursal.add(btnSucursalBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, 110, 30));

        btnModSucursal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModSucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModSucursalMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModSucursalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModSucursalMouseExited(evt);
            }
        });
        btnModSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModSucursalActionPerformed(evt);
            }
        });
        jpnSucursal.add(btnModSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 430, 110, 30));

        btnSucursalEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btnSucursalEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSucursalEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSucursalEliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSucursalEliminarMouseExited(evt);
            }
        });
        btnSucursalEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSucursalEliminarActionPerformed(evt);
            }
        });
        jpnSucursal.add(btnSucursalEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 110, 30));

        jPanel51.setBackground(new java.awt.Color(0, 0, 0));
        jPanel51.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator66.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel51.add(jSeparator66, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        lblProveedores8.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores8.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores8.setText("Sucursales");
        jPanel51.add(lblProveedores8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 110, 30));

        jpnSucursal.add(jPanel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 50));
        jpnSucursal.add(jSeparator69, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 130, 10));

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setText("Listado de sucursales:");
        jpnSucursal.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        txtSucursalBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSucursalBuscarActionPerformed(evt);
            }
        });
        txtSucursalBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSucursalBuscarKeyPressed(evt);
            }
        });
        jpnSucursal.add(txtSucursalBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 430, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Buscar sucursal");
        jpnSucursal.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));
        jpnSucursal.add(jSeparator70, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 96, 100, 20));

        getContentPane().add(jpnSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnModificarPrecio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardarPar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarprov.png"))); // NOI18N
        btnGuardarPar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardarPar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarParMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarParMouseExited(evt);
            }
        });
        btnGuardarPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarParActionPerformed(evt);
            }
        });
        jpnModificarPrecio.add(btnGuardarPar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 350, 110, 30));

        btnAtrasModPar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasModPar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAtrasModPar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasModParMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasModParMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasModParMouseExited(evt);
            }
        });
        btnAtrasModPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasModParActionPerformed(evt);
            }
        });
        jpnModificarPrecio.add(btnAtrasModPar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, 110, 30));

        jPanel56.setBackground(new java.awt.Color(0, 0, 0));
        jPanel56.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator89.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel56.add(jSeparator89, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("Modifica un tipo de precio:");
        jPanel56.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 270, 30));

        jpnModificarPrecio.add(jPanel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 50));

        txtIdPar.setEnabled(false);
        jpnModificarPrecio.add(txtIdPar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 290, 30));

        txtNomPar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomParKeyTyped(evt);
            }
        });
        jpnModificarPrecio.add(txtNomPar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 290, 30));

        jLabel74.setBackground(new java.awt.Color(0, 0, 0));
        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel74.setText("ID Tipo de precio:");
        jpnModificarPrecio.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, -1, 20));

        jLabel75.setBackground(new java.awt.Color(0, 0, 0));
        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel75.setText("Nombre:");
        jpnModificarPrecio.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, 20));
        jpnModificarPrecio.add(jSeparator90, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 70, 10));
        jpnModificarPrecio.add(jSeparator91, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 50, 20));

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel76.setText("Utilidad:");
        jpnModificarPrecio.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, 20));

        txtUtPar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUtParKeyTyped(evt);
            }
        });
        jpnModificarPrecio.add(txtUtPar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 120, 30));
        jpnModificarPrecio.add(jSeparator93, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 50, 20));

        getContentPane().add(jpnModificarPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnNuevoPrecio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAgregarSuc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar2.png"))); // NOI18N
        btnAgregarSuc1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregarSuc1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarSuc1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarSuc1MouseExited(evt);
            }
        });
        btnAgregarSuc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarSuc1ActionPerformed(evt);
            }
        });
        jpnNuevoPrecio.add(btnAgregarSuc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 380, 110, 30));

        btnAtrasSuc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasSuc1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAtrasSuc1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasSuc1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasSuc1MouseExited(evt);
            }
        });
        btnAtrasSuc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasSuc1ActionPerformed(evt);
            }
        });
        jpnNuevoPrecio.add(btnAtrasSuc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 380, 110, 30));

        txtIdTP.setEditable(false);
        jpnNuevoPrecio.add(txtIdTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 110, 230, 30));

        txtNombrePrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombrePrecioKeyTyped(evt);
            }
        });
        jpnNuevoPrecio.add(txtNombrePrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, 230, 30));

        jPanel57.setBackground(new java.awt.Color(0, 0, 0));
        jPanel57.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(240, 240, 240));
        jLabel77.setText("Agregar un nuevo tipo de precio:");
        jPanel57.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 300, 30));

        jSeparator92.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel57.add(jSeparator92, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jpnNuevoPrecio.add(jPanel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        jLabel78.setBackground(new java.awt.Color(0, 0, 0));
        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel78.setText("ID Tipo Precio:");
        jpnNuevoPrecio.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, -1, 20));

        jLabel79.setBackground(new java.awt.Color(0, 0, 0));
        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel79.setText("Nombre:");
        jpnNuevoPrecio.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, -1, 20));
        jpnNuevoPrecio.add(jSeparator94, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 70, 10));
        jpnNuevoPrecio.add(jSeparator95, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 70, 20));

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel81.setText("Utilidad:");
        jpnNuevoPrecio.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, -1, 20));

        txtUtilidadPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUtilidadPrecioKeyTyped(evt);
            }
        });
        jpnNuevoPrecio.add(txtUtilidadPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, 230, 30));
        jpnNuevoPrecio.add(jSeparator97, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 50, 20));

        getContentPane().add(jpnNuevoPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnConfiguracion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel58.setBackground(new java.awt.Color(0, 0, 0));
        jPanel58.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator96.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel58.add(jSeparator96, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setText("Configuracion");
        jPanel58.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 250, 30));

        jpnConfiguracion.add(jPanel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        tblParametro =new javax.swing.JTable(){ public boolean isCellEditable(int rowIndex, int colIndex){     return false; } };
        tblParametro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Parametro", "Nombre", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblParametro.getTableHeader().setReorderingAllowed(false);
        jScrollPane13.setViewportView(tblParametro);

        jpnConfiguracion.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 550, 120));

        btnModificarParametro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificarParametro.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModificarParametro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarParametroMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarParametroMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarParametroMouseExited(evt);
            }
        });
        btnModificarParametro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarParametroActionPerformed(evt);
            }
        });
        jpnConfiguracion.add(btnModificarParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 110, 30));

        btnBuscarParametro.setBackground(new java.awt.Color(0, 0, 0));
        btnBuscarParametro.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarParametro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscar.png"))); // NOI18N
        btnBuscarParametro.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBuscarParametro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscarParametroMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBuscarParametroMouseExited(evt);
            }
        });
        btnBuscarParametro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarParametroActionPerformed(evt);
            }
        });
        jpnConfiguracion.add(btnBuscarParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 110, 30));

        txtBuscarParametro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarParametroKeyPressed(evt);
            }
        });
        jpnConfiguracion.add(txtBuscarParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 430, 30));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Buscar parametro:");
        jpnConfiguracion.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));
        jpnConfiguracion.add(jSeparator57, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 96, 120, 20));

        getContentPane().add(jpnConfiguracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnModificarParametro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardarPar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarprov.png"))); // NOI18N
        btnGuardarPar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardarPar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarPar1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarPar1MouseExited(evt);
            }
        });
        btnGuardarPar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPar1ActionPerformed(evt);
            }
        });
        jpnModificarParametro.add(btnGuardarPar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 350, 110, 30));

        btnAtrasModPar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasModPar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAtrasModPar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasModPar1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasModPar1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasModPar1MouseExited(evt);
            }
        });
        btnAtrasModPar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasModPar1ActionPerformed(evt);
            }
        });
        jpnModificarParametro.add(btnAtrasModPar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, 110, 30));

        jPanel59.setBackground(new java.awt.Color(0, 0, 0));
        jPanel59.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator98.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel59.add(jSeparator98, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText("Modifica un parametro:");
        jPanel59.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 270, 30));

        jpnModificarParametro.add(jPanel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 50));

        txtIdPar1.setEnabled(false);
        jpnModificarParametro.add(txtIdPar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 290, 30));

        txtNomPar1.setEnabled(false);
        jpnModificarParametro.add(txtNomPar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 290, 30));

        jLabel83.setBackground(new java.awt.Color(0, 0, 0));
        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel83.setText("IDParametro:");
        jpnModificarParametro.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, -1, 20));

        jLabel84.setBackground(new java.awt.Color(0, 0, 0));
        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel84.setText("Nombre:");
        jpnModificarParametro.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, 20));
        jpnModificarParametro.add(jSeparator99, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 70, 10));
        jpnModificarParametro.add(jSeparator100, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 50, 20));

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel85.setText("Valor");
        jpnModificarParametro.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, 20));

        txtValorPar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValorParKeyTyped(evt);
            }
        });
        jpnModificarParametro.add(txtValorPar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 290, 30));
        jpnModificarParametro.add(jSeparator101, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 50, 20));

        getContentPane().add(jpnModificarParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnReporteMesVentas2.setName("jpnListaVentas"); // NOI18N
        jpnReporteMesVentas2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblListaVentasMes2 =new javax.swing.JTable(){ public boolean isCellEditable(int rowIndex, int colIndex){     return false; } };
        tblListaVentasMes2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblListaVentasMes2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Fecha De emision", "No Documento", "NRC", "Proveedor", "Venta Gravada", "Iva", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListaVentasMes2.setToolTipText("");
        tblListaVentasMes2.getTableHeader().setReorderingAllowed(false);
        jScrollPane16.setViewportView(tblListaVentasMes2);

        jpnReporteMesVentas2.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 700, 190));

        jPanel44.setBackground(new java.awt.Color(0, 0, 0));
        jPanel44.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator61.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel44.add(jSeparator61, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, -1, 50));

        lblVentas3.setBackground(new java.awt.Color(255, 255, 255));
        lblVentas3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblVentas3.setForeground(new java.awt.Color(255, 255, 255));
        lblVentas3.setText("Reporte Ventas");
        jPanel44.add(lblVentas3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, 30));

        jpnReporteMesVentas2.add(jPanel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 50));

        lblListadoVentas3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblListadoVentas3.setText("Listado de Ventas Realizadas:");
        jpnReporteMesVentas2.add(lblListadoVentas3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 200, 30));
        jpnReporteMesVentas2.add(jSeparator64, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 180, 20));

        btnAtrasReporteVenta2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasReporteVenta2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAtrasReporteVenta2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasReporteVenta2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasReporteVenta2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasReporteVenta2MouseExited(evt);
            }
        });
        btnAtrasReporteVenta2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasReporteVenta2ActionPerformed(evt);
            }
        });
        jpnReporteMesVentas2.add(btnAtrasReporteVenta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 110, 30));

        cmbMes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMesItemStateChanged(evt);
            }
        });
        jpnReporteMesVentas2.add(cmbMes, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 80, 30));

        jLabel96.setBackground(new java.awt.Color(0, 0, 0));
        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel96.setText("Mes:");
        jpnReporteMesVentas2.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, 20));
        jpnReporteMesVentas2.add(jSeparator103, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 30, 20));

        jLabel100.setBackground(new java.awt.Color(0, 0, 0));
        jLabel100.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel100.setText("Año:");
        jpnReporteMesVentas2.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, 20));
        jpnReporteMesVentas2.add(jSeparator107, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 30, 20));

        cmbAño.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbAñoItemStateChanged(evt);
            }
        });
        jpnReporteMesVentas2.add(cmbAño, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 70, 30));

        btnGenerar.setText("Generar Reporte");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });
        jpnReporteMesVentas2.add(btnGenerar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 420, 130, 40));

        getContentPane().add(jpnReporteMesVentas2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnReporteMesCompras.setName("jpnListaVentas"); // NOI18N
        jpnReporteMesCompras.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblListaComprasMes =new javax.swing.JTable(){ public boolean isCellEditable(int rowIndex, int colIndex){     return false; } };
        tblListaComprasMes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblListaComprasMes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Fecha De emision", "No Documento", "NRC", "Proveedor", "Venta Gravada", "Iva", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListaComprasMes.setToolTipText("");
        tblListaComprasMes.getTableHeader().setReorderingAllowed(false);
        jScrollPane17.setViewportView(tblListaComprasMes);

        jpnReporteMesCompras.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 700, 190));

        jPanel60.setBackground(new java.awt.Color(0, 0, 0));
        jPanel60.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator65.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel60.add(jSeparator65, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, -1, 50));

        lblVentas4.setBackground(new java.awt.Color(255, 255, 255));
        lblVentas4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblVentas4.setForeground(new java.awt.Color(255, 255, 255));
        lblVentas4.setText("Reporte Compras");
        jPanel60.add(lblVentas4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, 30));

        jpnReporteMesCompras.add(jPanel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 50));

        lblListadoVentas4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblListadoVentas4.setText("Listado de Ventas Realizadas:");
        jpnReporteMesCompras.add(lblListadoVentas4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 200, 30));
        jpnReporteMesCompras.add(jSeparator67, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 180, 20));

        btnAtrasReporteCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasReporteCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAtrasReporteCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasReporteCompraMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasReporteCompraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasReporteCompraMouseExited(evt);
            }
        });
        btnAtrasReporteCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasReporteCompraActionPerformed(evt);
            }
        });
        jpnReporteMesCompras.add(btnAtrasReporteCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 110, 30));

        cmbMes1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMes1ItemStateChanged(evt);
            }
        });
        jpnReporteMesCompras.add(cmbMes1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 80, 30));

        jLabel97.setBackground(new java.awt.Color(0, 0, 0));
        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel97.setText("Mes:");
        jpnReporteMesCompras.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, 20));
        jpnReporteMesCompras.add(jSeparator104, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 30, 20));

        jLabel101.setBackground(new java.awt.Color(0, 0, 0));
        jLabel101.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel101.setText("Año:");
        jpnReporteMesCompras.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, 20));
        jpnReporteMesCompras.add(jSeparator108, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 30, 20));

        cmbAño1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbAño1ItemStateChanged(evt);
            }
        });
        jpnReporteMesCompras.add(cmbAño1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 70, 30));

        btnGenerar1.setText("Generar Reporte");
        btnGenerar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerar1ActionPerformed(evt);
            }
        });
        jpnReporteMesCompras.add(btnGenerar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 400, 130, 40));

        getContentPane().add(jpnReporteMesCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblBotonCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBotonCerrarMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblBotonCerrarMouseClicked

    /*  ---- Animaciones de los botones del menú ----  */
    private void btnComprasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComprasMouseEntered
        /*  ---- Animación compras, mover ----  */
        if (!compras) {
            Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnCompras);
        }
        Principal(false);
        Compras(true);
    }//GEN-LAST:event_btnComprasMouseEntered

    private void btnComprasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComprasMouseExited
        /*  ---- Animación compras, volver posición anterior ----  */
        if (!compras) {
            Animacion.Animacion.mover_izquierda(0, -126, 1, 2, btnCompras);
        }
        Principal(true);
        Compras(false);
    }//GEN-LAST:event_btnComprasMouseExited

    private void btnVentasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVentasMouseEntered
        if (!ventas) {
            Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnVentas);
        }
        Principal(false);
        Ventas(true);
    }//GEN-LAST:event_btnVentasMouseEntered

    private void btnVentasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVentasMouseExited
        if (!ventas) {
            Animacion.Animacion.mover_izquierda(0, -126, 1, 2, btnVentas);
        }
        Principal(true);
        Ventas(false);
    }//GEN-LAST:event_btnVentasMouseExited

    private void btnProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductosMouseEntered
        if (!productos) {
            Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnProductos);
        }
        Principal(false);
        Productos(true);
    }//GEN-LAST:event_btnProductosMouseEntered

    private void btnProductosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductosMouseExited
        if (!productos) {
            Animacion.Animacion.mover_izquierda(0, -126, 1, 2, btnProductos);
        }
        Principal(true);
        Productos(false);
    }//GEN-LAST:event_btnProductosMouseExited

    private void btnProveedoresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProveedoresMouseEntered
        if (!proveedores) {
            Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnProveedores);
        }
        Principal(false);
        Proveedores(true);
    }//GEN-LAST:event_btnProveedoresMouseEntered

    private void btnProveedoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProveedoresMouseExited
        if (!proveedores) {
            Animacion.Animacion.mover_izquierda(0, -126, 1, 2, btnProveedores);
        }
        Principal(true);
        Proveedores(false);
    }//GEN-LAST:event_btnProveedoresMouseExited

    private void btnProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProveedoresMouseClicked
        apagado();
        Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnProveedores);
        apagado2();
        jpnProveedores.setVisible(true);
    }//GEN-LAST:event_btnProveedoresMouseClicked

    /*  ---- Acción de botones, cambiar de pantallas (Paneles) ----  */
    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        apagado2();
        apagado = false;
        jpnPrincipal.setVisible(true);
        Principal(true);
        Compras(false);
        Ventas(false);
        Productos(false);
        Proveedores(false);
    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVentasMouseClicked
        apagado();
        apagado2();
        jpnListaVentas.setVisible(true);
    }//GEN-LAST:event_btnVentasMouseClicked

    private void btnProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductosMouseClicked
        apagado();
        Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnProductos);
        apagado2();
        txtProductosBuscar.setText("");
        jtblProductos.removeAll();
        jpnProductos.setVisible(true);
        lblSucursalProd.setVisible(false);
        cmbSucursales1.setVisible(false);
        ControladorProducto CP = new ControladorProducto();

        //LISTADO DE SUCURSALES EN COMBOBOX
        try {
            String[] cm = new String[]{"Nombre"};
            ArrayList<Object> listaSucursales = new ArrayList();
            

            // ComboBoxModel modelo;
            //cmbSucursales1.removeAllItems();

            conection cn = new conection();
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarTodos("Sucursal", cm);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cmbSucursales1.addItem(rs.getString("Nombre"));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "111111111 " + e.getMessage());
            }

            //ArrayList<Producto> listaProducto = CP.Buscar("", Integer.parseInt(cn.BuscarId("Sucursal", "IdSucursal", "Nombre", cmbSucursales1.getItemAt(0).toString())));
            //DefaultTableModel model = new DefaultTableModel();
            //System.out.println("Lista de productos que coinciden: " + listaProducto.toString());

            //Object[] fila = new Object[5];

            //String[] productos = new String[]{"CodBarra", "Nombre", "Sucursal", "Cantidad", "Costo"};
            //model.setColumnIdentifiers(productos);
            //Iterator<Producto> prod = listaProducto.iterator();
            
            //if (prod.hasNext()){
                //while (prod.hasNext()) {
                    //fila[0] = prod.next();
                    //fila[1] = prod.next();
                    //fila[3] = prod.next();
                    //fila[4] = prod.next();
                    //fila[2] = prod.next();
                    //model.addRow(fila);

                //}
            //} else {
                //fila[0] = "-";
                //fila[1] = "-";
                //fila[3] = "-";
                //fila[4] = "-";
                //fila[2] = "-";
                //model.addRow(fila);
            //}

            //jtblProductos.setModel(model);
//            jtblProductos.getColumnModel().getColumn(5).setMinWidth(0);
//            jtblProductos.getColumnModel().getColumn(5).setMaxWidth(0);
//            jtblProductos.getColumnModel().getColumn(5).setWidth(0);

            cn.Desconectar();
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "2222222222" + e.getMessage());
            JOptionPane.showMessageDialog(null, "2222222222lm " + e.getLocalizedMessage());
            System.out.println("22222222st" + Arrays.toString(e.getStackTrace()));
        }

    }//GEN-LAST:event_btnProductosMouseClicked

    private void btnComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComprasMouseClicked
        apagado();
        Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnCompras);
        apagado2();
        jpnCompras.setVisible(true);
    }//GEN-LAST:event_btnComprasMouseClicked

    /*  ---- Mover barra ----  */
    private void jpnBarraSuperiorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnBarraSuperiorMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jpnBarraSuperiorMousePressed

    private void jpnBarraSuperiorMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnBarraSuperiorMouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jpnBarraSuperiorMouseDragged


    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProveedoresActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnComprasActionPerformed

    private void btnEliminarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProveedorMouseEntered
        btnEliminarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminarB.png")));
    }//GEN-LAST:event_btnEliminarProveedorMouseEntered

    private void btnEliminarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProveedorMouseExited
        btnEliminarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminar.png")));
    }//GEN-LAST:event_btnEliminarProveedorMouseExited

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        // Eliminar un proveedor:
        try {
            Proveedor p = new Proveedor();
            p.idProveedor = Integer.parseInt(tblProveedores.getValueAt(tblProveedores.getSelectedRow(), 0).toString().trim());
            ControladorProveedor.Eliminar(p);

            tblProveedores.removeAll();
            LlenarProveedor();

            JOptionPane.showMessageDialog(null, "Proveedor Eliminado");

        } catch (ErrorTienda e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar proveedor");
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnAgregarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProveedorMouseClicked
        jpnProveedores.setVisible(false);
        jpnAgregarProv.setVisible(true);
    }//GEN-LAST:event_btnAgregarProveedorMouseClicked

    private void btnAgregarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProveedorMouseEntered
        // Cambio del botón Agregar Proveedor a negro:
        btnAgregarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/agregarprovB.png")));
    }//GEN-LAST:event_btnAgregarProveedorMouseEntered

    private void btnAgregarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProveedorMouseExited
        // Cambio del botón Agregar Proveedor a blanco:
        btnAgregarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/agregarprov.png")));
    }//GEN-LAST:event_btnAgregarProveedorMouseExited

    private void btnAgregarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProveedorActionPerformed
        ControladorProveedor CProveedor = new ControladorProveedor();
        int idProveedor = 0;
        try {
            idProveedor = CProveedor.ObtenerIdProveedor();
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtIDProveedor.setText(Integer.toString(idProveedor));
    }//GEN-LAST:event_btnAgregarProveedorActionPerformed

    private void btnModificarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProveedorMouseClicked
        jpnProveedores.setVisible(false);
        jpnModificarProveedor.setVisible(true);
    }//GEN-LAST:event_btnModificarProveedorMouseClicked

    private void btnModificarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProveedorMouseEntered
        // Cambio del botón Modificar Proveedor a negro:
        btnModificarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/modificarB.png")));
    }//GEN-LAST:event_btnModificarProveedorMouseEntered

    private void btnModificarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProveedorMouseExited
        // Cambio del botón Modificar Proveedor a blanco:
        btnModificarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/modificar.png")));
    }//GEN-LAST:event_btnModificarProveedorMouseExited

    private void btnModificarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProveedorActionPerformed
        try {
            txtNuevoNombreProveedor.setText(tblProveedores.getValueAt(tblProveedores.getSelectedRow(), 1).toString());
            txtNuevoDireccionProveedor.setText(tblProveedores.getValueAt(tblProveedores.getSelectedRow(), 3).toString());
            txtNuevoTelefonoProveedor.setText(tblProveedores.getValueAt(tblProveedores.getSelectedRow(), 2).toString());
            txtNuevoNIT.setText(tblProveedores.getValueAt(tblProveedores.getSelectedRow(), 4).toString());
            txtNuevoNRC.setText(tblProveedores.getValueAt(tblProveedores.getSelectedRow(), 5).toString());
            txtEmail.setText(tblProveedores.getValueAt(tblProveedores.getSelectedRow(), 6).toString());

            txtIDProveedor1.setText(tblProveedores.getValueAt(tblProveedores.getSelectedRow(), 0).toString());
            //            txtNombreActualProveedor1.setText(tblProveedores.getValueAt(tblProveedores.getSelectedRow(), 1).toString());
            //            txtDireccionActualProveedor.setText(tblProveedores.getValueAt(tblProveedores.getSelectedRow(), 3).toString());
            //            txtTelefonoActualProveedor.setText(tblProveedores.getValueAt(tblProveedores.getSelectedRow(), 2).toString());
            //            txtNitActualProveedor.setText(tblProveedores.getValueAt(tblProveedores.getSelectedRow(), 4).toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleccione un proveedor");
        }
    }//GEN-LAST:event_btnModificarProveedorActionPerformed

    private void txtProductosBuscar1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductosBuscar1KeyTyped

        txtProductosBuscar1.addKeyListener(new KeyAdapter() {
            //@Override
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtProductosBuscar1.getText());
                txtProductosBuscar1.setText(cadena);
                repaint();
                trsFiltro.setRowFilter(RowFilter.regexFilter(txtProductosBuscar1.getText(), 1));

            }
        });
        trsFiltro = new TableRowSorter(tblProveedores.getModel());
        tblProveedores.setRowSorter(trsFiltro);
    }//GEN-LAST:event_txtProductosBuscar1KeyTyped

    private void btnGuardarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarProveedorMouseEntered
        btnGuardarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/guardarprovB.png")));
    }//GEN-LAST:event_btnGuardarProveedorMouseEntered

    private void btnGuardarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarProveedorMouseExited
        btnGuardarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/guardarprov.png")));
    }//GEN-LAST:event_btnGuardarProveedorMouseExited

    private void btnGuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedorActionPerformed

        Proveedor proveedor = new Proveedor(Integer.parseInt(txtIDProveedor.getText()), txtNombreProveedor.getText(), txtTelefonoProveedor.getText(), txtDireccionProveedor.getText(),
                txtNIT.getText(), txtNRCProveedor.getText(), txtEmailProveedor.getText());
        try {
            ControladorProveedor.Agregar(proveedor);
            JOptionPane.showMessageDialog(rootPane, "Agregado");
            this.txtNombreProveedor.setText("");
            this.txtTelefonoProveedor.setText("");
            this.txtDireccionProveedor.setText("");
            this.txtNIT.setText("");
            txtNRCProveedor.setText("");
            txtEmailProveedor.setText("");
            tblProveedores.removeAll();
            LlenarProveedor();
            apagado2();
            jpnAgregarProv.setVisible(false);
            jpnProveedores.setVisible(true);
        } catch (ErrorTienda ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnGuardarProveedorActionPerformed

    private void btnAtrasProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasProveedoresMouseClicked
        jpnAgregarProv.setVisible(false);
        jpnProveedores.setVisible(true);
        this.txtNombreProveedor.setText("");
        this.txtTelefonoProveedor.setText("");
        this.txtDireccionProveedor.setText("");
        this.txtNIT.setText("");
    }//GEN-LAST:event_btnAtrasProveedoresMouseClicked

    private void btnAtrasProveedoresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasProveedoresMouseEntered
        btnAtrasProveedores.setIcon(new ImageIcon(getClass().getResource("/iconos/atrasB.png")));
    }//GEN-LAST:event_btnAtrasProveedoresMouseEntered

    private void btnAtrasProveedoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasProveedoresMouseExited
        btnAtrasProveedores.setIcon(new ImageIcon(getClass().getResource("/iconos/atras.png")));
    }//GEN-LAST:event_btnAtrasProveedoresMouseExited

    private void btnAtrasProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasProveedoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasProveedoresActionPerformed

    private void txtNITKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNITKeyTyped
        int longitud = txtNIT.getText().length();
        validacion.Nit(evt, longitud);
    }//GEN-LAST:event_txtNITKeyTyped

    private void txtNombreProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProveedorKeyTyped
        int longitud = txtNombreProveedor.getText().length();
        validacion.Nombre(evt, longitud);
    }//GEN-LAST:event_txtNombreProveedorKeyTyped

    private void txtTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorKeyTyped
        int longitud = txtTelefonoProveedor.getText().length();
        validacion.Tel(evt, longitud);
    }//GEN-LAST:event_txtTelefonoProveedorKeyTyped

    private void btnGuardarModificarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarModificarProveedorMouseEntered
        btnGuardarModificarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/guardarprovB.png")));
    }//GEN-LAST:event_btnGuardarModificarProveedorMouseEntered

    private void btnGuardarModificarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarModificarProveedorMouseExited
        btnGuardarModificarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/guardarprov.png")));
    }//GEN-LAST:event_btnGuardarModificarProveedorMouseExited

    private void btnGuardarModificarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificarProveedorActionPerformed

        Proveedor proveedor = new Proveedor(Integer.parseInt(txtIDProveedor1.getText()), txtNuevoNombreProveedor.getText(), txtNuevoTelefonoProveedor.getText(),
                txtNuevoDireccionProveedor.getText(), txtNuevoNIT.getText(), txtNuevoNRC.getText(), txtEmail.getText());
        try {
            ControladorProveedor.Modificar(proveedor);
            JOptionPane.showMessageDialog(rootPane, "Modificado");

            tblProveedores.removeAll();
            LlenarProveedor();

            jpnModificarProveedor.setVisible(false);
            jpnProveedores.setVisible(true);
        } catch (ErrorTienda ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        LlenarProveedor();
    }//GEN-LAST:event_btnGuardarModificarProveedorActionPerformed

    private void btnAtrasModificarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModificarProveedorMouseClicked
        jpnModificarProveedor.setVisible(false);
        jpnProveedores.setVisible(true);
    }//GEN-LAST:event_btnAtrasModificarProveedorMouseClicked

    private void btnAtrasModificarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModificarProveedorMouseEntered
        btnAtrasModificarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/atrasB.png")));
    }//GEN-LAST:event_btnAtrasModificarProveedorMouseEntered

    private void btnAtrasModificarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModificarProveedorMouseExited
        btnAtrasModificarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/atras.png")));
    }//GEN-LAST:event_btnAtrasModificarProveedorMouseExited

    private void txtNuevoNITKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoNITKeyTyped
        int longitud = txtNuevoNIT.getText().length();
        validacion.Nit(evt, longitud);
    }//GEN-LAST:event_txtNuevoNITKeyTyped

    private void txtNuevoNombreProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoNombreProveedorKeyTyped
        int longitud = txtNuevoNombreProveedor.getText().length();
        validacion.Nombre(evt, longitud);
    }//GEN-LAST:event_txtNuevoNombreProveedorKeyTyped

    private void txtNuevoTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoTelefonoProveedorKeyTyped
        int longitud = txtNuevoTelefonoProveedor.getText().length();
        validacion.Tel(evt, longitud);
    }//GEN-LAST:event_txtNuevoTelefonoProveedorKeyTyped

    private void btnAgregarCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarCompraMouseClicked
        // TODO add your handling code here:
        jpnRegistroCompra.setVisible(true);
        jpnCompras.setVisible(false);
    }//GEN-LAST:event_btnAgregarCompraMouseClicked

    private void btnAgregarCompraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarCompraMouseEntered
        btnAgregarCompra.setIcon(new ImageIcon(getClass().getResource("/iconos/agregar2B.png")));
    }//GEN-LAST:event_btnAgregarCompraMouseEntered

    private void btnAgregarCompraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarCompraMouseExited
        btnAgregarCompra.setIcon(new ImageIcon(getClass().getResource("/iconos/agregar2.png")));
    }//GEN-LAST:event_btnAgregarCompraMouseExited

    private void btnAgregarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCompraActionPerformed
        model0 = (DefaultTableModel) this.tblCompra.getModel();
        model0.setRowCount(0);
        filas = 0;
        txtNumDocCompra.setText("");
        txtCodBarraProd.setText("");
        txtNomProd.setText("");
        txtCantidad.setText("");
        txtCostoProd.setText("");
        txtTotal.setText("");
        txtSuma.setText("");
        txtIva.setText("");
        txtPercepcion.setText("");
        txtNumDocCompra.requestFocus();
        cmbTipoCompra.setEnabled(true);
        txtPercepcion.setText("");

        //NUEVA COMPRA - VARIOS DETALLES
        //NUEVO ID
        ControladorCompra COmpra = new ControladorCompra();
        int idCompra = 0;
        try {
            idCompra = COmpra.ObtenerIdCompra();
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (idCompra == 0) {
            txtIdCompra.setText("1");
        } else {
            txtIdCompra.setText(Integer.toString(idCompra));
        }
        

        //FECHA DEL MOMENTO
        java.util.Date date = new Date();
        Object param = new java.sql.Timestamp(date.getTime());
        //txtFecha.setText(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(param));
        txt_fecha_compra.setDate(date);                                                       //.setDate(date);

        //LISTADO DE PROVEEDORES EN COMBOBOX
        try {
            String[] cm = new String[]{"Nombre"};
            ArrayList<Object> listaProveedores = new ArrayList();
            ComboBoxModel modelo;
            cmbProveedor.removeAllItems();

            conection cn = new conection();
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarTodos("proveedor", cm);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cmbProveedor.addItem(rs.getString("Nombre"));
                }
                cn.Desconectar();
            } catch (Exception e) {
                //ERROR!!!
            }
        } catch (Exception e) {
        }
        //LISTADO DE SUCURSALES EN COMBOBOX
        try {
            String[] cm = new String[]{"Nombre"};
            ArrayList<Object> listaSucursales = new ArrayList();
            ComboBoxModel modelo;
            cmbSucursalCompra.removeAllItems();

            conection cn = new conection();
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarTodos("sucursal", cm);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cmbSucursalCompra.addItem(rs.getString("Nombre"));
                }
                cn.Desconectar();
            } catch (Exception e) {
                //ERROR!!!
            }
        } catch (Exception e) {
        }

        cmbTipoCompra.setSelectedIndex(1);
        cmbTipoCompra.setSelectedIndex(0);

        /* jpnRegistroCompra.setVisible(true);
        jpnCompras.setVisible(false);
        // Will 08:20 a.m 08-05-2017
        this.txtFecha.setText(fechaActual());

        int idCompra = 0;
        try {
            idCompra = ControladorCompra.ObtenerIdCompra();
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtIDProveedor.setText(Integer.toString(idCompra));
        llena_combo();
        }*/
    }//GEN-LAST:event_btnAgregarCompraActionPerformed

    private void btnVerDetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerDetalleMouseClicked
        jpnDetalleCompra.setVisible(true);
        jpnCompras.setVisible(false);
    }//GEN-LAST:event_btnVerDetalleMouseClicked

    private void btnVerDetalleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerDetalleMouseEntered
        btnVerDetalle.setIcon(new ImageIcon(getClass().getResource("/iconos/detalles2B.png")));
    }//GEN-LAST:event_btnVerDetalleMouseEntered

    private void btnVerDetalleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerDetalleMouseExited
        btnVerDetalle.setIcon(new ImageIcon(getClass().getResource("/iconos/detalles2.png")));
    }//GEN-LAST:event_btnVerDetalleMouseExited

    private void btnVerDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetalleActionPerformed
        // Ver un detalle de Compra....
        try {
            //LLEVAR LA INFO AL PANEL DE DETALLE VENTA
            txtIdDetalleCompra.setText(tblCompras.getValueAt(tblCompras.getSelectedRow(), 0).toString());
            txtTipoCompraDetalleCompra.setText(tblCompras.getValueAt(tblCompras.getSelectedRow(), 2).toString());
            txtSucursalDetalleCompra.setText(tblCompras.getValueAt(tblCompras.getSelectedRow(), 3).toString());
            txtProveedorDetalleCompra.setText(tblCompras.getValueAt(tblCompras.getSelectedRow(), 4).toString());
            txtFechaDetalleCompra.setText(tblCompras.getValueAt(tblCompras.getSelectedRow(), 1).toString());
            txtTotalDetalleCompra.setText("$ " + tblCompras.getValueAt(tblCompras.getSelectedRow(), 5).toString());
            txtNumDocumentoDetalleCompra.setText(tblCompras.getValueAt(tblCompras.getSelectedRow(), 6).toString());
            txtSubTotalDetalleCompra.setText(tblCompras.getValueAt(tblCompras.getSelectedRow(), 7).toString());
            txtIVADetalleCompra.setText(tblCompras.getValueAt(tblCompras.getSelectedRow(), 8).toString());
            txtPercepcionDetalleCompra.setText(tblCompras.getValueAt(tblCompras.getSelectedRow(), 9).toString());

            //LLENAR TABlA DE DETALLE VENTA
            String[] cm = new String[]{"CodBarra", "IdCompra", "Cantidad", "CostoUnitario"};
            iList p = new iList(new ListasTablas("IdCompra", tblCompras.getValueAt(tblCompras.getSelectedRow(), 0)));
            ArrayList<Object> listaDetalleCompras = new ArrayList();
            DefaultTableModel modelo = new DefaultTableModel();
            conection cn = new conection();
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarRegistro("detallecompra", cm, p);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    listaDetalleCompras.add(rs.getString("CodBarra"));
                    listaDetalleCompras.add(rs.getString("IdCompra"));
                    listaDetalleCompras.add(rs.getString("Cantidad"));
                    listaDetalleCompras.add(rs.getString("CostoUnitario"));
                }
                cn.Desconectar();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Algo paso llenando la tabla de detalle compra " + e.getMessage());
            }
            ArrayList<DetalleCompra> listaDetalleCompra = (ArrayList) listaDetalleCompras;
            Object[] fila = new Object[4];

            String[] detallecompras = new String[]{"CodBarra", "IdCompra", "Cantidad", "CostoUnitario"};
            modelo.setColumnIdentifiers(detallecompras);
            Iterator<DetalleCompra> prod = listaDetalleCompra.iterator();
            while (prod.hasNext()) {
                fila[0] = prod.next();
                fila[1] = prod.next();
                fila[2] = prod.next();
                fila[3] = prod.next();
                modelo.addRow(fila);

            }
            tblDetalleCompra.setModel(modelo);
            System.out.println("Lleno DetalleCompra!...creo");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleccione una compra");
        }
    }//GEN-LAST:event_btnVerDetalleActionPerformed

    private void btnNuevoProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoProductoMouseEntered
        btnNuevoProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/nuevo3B.png")));
    }//GEN-LAST:event_btnNuevoProductoMouseEntered

    private void btnNuevoProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoProductoMouseExited
        btnNuevoProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/nuevo3.png")));
    }//GEN-LAST:event_btnNuevoProductoMouseExited

    private void btnNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProductoActionPerformed
        jpnProductos.setVisible(false);
        jpnNuevoProducto.setVisible(true);
        cmbSucursal2.setModel(cmbSucursales1.getModel());
        cmbSucursal2.setSelectedIndex(cmbSucursales1.getSelectedIndex());
        txtCodBarraProductos.setText("");
        txtNombreProductos.setText("");
        txtProductoInventario.setText("");
        txtPrecioProductos.setText("");
    }//GEN-LAST:event_btnNuevoProductoActionPerformed

    private void btnBuscarProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarProductoMouseEntered
        btnBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/buscarB.png")));
    }//GEN-LAST:event_btnBuscarProductoMouseEntered

    private void btnBuscarProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarProductoMouseExited
        btnBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/buscar.png")));
    }//GEN-LAST:event_btnBuscarProductoMouseExited

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed
        System.out.println("txtProductosBuscar EN ACCION DE BOTON = " + txtProductosBuscar.getText());
        ControladorProducto CP = new ControladorProducto();
        LlenarProducto(txtProductosBuscar.getText());
        //ID SUCURSAL SELECCIONADA
        int idSucursalSeleccionada = 0;
        String suc = cmbSucursales1.getSelectedItem().toString();
        conection cn = new conection();
        try {
            String[] cm = new String[]{"IdSucursal", "Nombre", "Direccion", "Telefono"};
            iList p = new iList(new ListasTablas("Nombre", suc));

            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarRegistro("Sucursal", cm, p);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    idSucursalSeleccionada = Integer.parseInt(rs.getString("IdSucursal"));

                }
                cn.Desconectar();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error interno buscando la sucursal " + e.getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error externo buscando la sucursal " + e.getMessage());
        }

        try {
            cn.Conectar();
            ArrayList<Producto> listaProducto = CP.Buscar(txtProductosBuscar.getText(), idSucursalSeleccionada);
            DefaultTableModel modelo = new DefaultTableModel();
            System.out.println("Lista de productos que coinciden: " + listaProducto.toString());

            Object[] fila = new Object[6];

            String[] productos = new String[]{"CodBarra", "Nombre", "Sucursal", "Cantidad", "Costo", "IdSucursal"};
            modelo.setColumnIdentifiers(productos);
            Iterator<Producto> prod = listaProducto.iterator();
            
            if(prod.hasNext()) {
                while (prod.hasNext()) {
                    fila[0] = prod.next();
                    fila[1] = prod.next();
                    fila[3] = prod.next();
                    fila[4] = prod.next();
                    fila[2] = prod.next();
                    fila[5] = prod.next();
                    modelo.addRow(fila);

                }
            } else {
                fila[0] = "-";
                fila[1] = "-";
                fila[3] = "-";
                fila[4] = "-";
                fila[2] = "-";
                fila[5] = "-";
                modelo.addRow(fila);
            }
            

            jtblProductos.setModel(modelo);
            jtblProductos.getColumnModel().getColumn(5).setMinWidth(0);
            jtblProductos.getColumnModel().getColumn(5).setMaxWidth(0);
            jtblProductos.getColumnModel().getColumn(5).setWidth(0);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error buscando producto segun sucursal " + e.getMessage());

        }


    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void btnModificarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProductoMouseClicked
        jpnProductos.setVisible(false);
        jpnModificarProducto.setVisible(true);
    }//GEN-LAST:event_btnModificarProductoMouseClicked

    private void btnModificarProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProductoMouseEntered
        btnModificarProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/modificarB.png")));
    }//GEN-LAST:event_btnModificarProductoMouseEntered

    private void btnModificarProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProductoMouseExited
        btnModificarProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/modificar.png")));
    }//GEN-LAST:event_btnModificarProductoMouseExited

    private void btnModificarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProductoActionPerformed
//        cmbSucursalMod.setModel(cmbSucursales1.getModel());
//        cmbSucursalMod.setSelectedIndex(cmbSucursales1.getSelectedIndex());
        try {
            txtNuevaSucursalProd.setText(jtblProductos.getValueAt(jtblProductos.getSelectedRow(), 2).toString());
            txtNuevoCodigoBarra.setText(jtblProductos.getValueAt(jtblProductos.getSelectedRow(), 0).toString());
            txtNuevoInventarioProducto.setText(jtblProductos.getValueAt(jtblProductos.getSelectedRow(), 3).toString());
            txtNuevoCostoProducto.setText(jtblProductos.getValueAt(jtblProductos.getSelectedRow(), 4).toString());
            txtNuevoNombreProducto.setText(jtblProductos.getValueAt(jtblProductos.getSelectedRow(), 1).toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");
        }
    }//GEN-LAST:event_btnModificarProductoActionPerformed

    private void btnEliminarProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProductoMouseEntered
        btnEliminarProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminarB.png")));
    }//GEN-LAST:event_btnEliminarProductoMouseEntered

    private void btnEliminarProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProductoMouseExited
        btnEliminarProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminar.png")));
    }//GEN-LAST:event_btnEliminarProductoMouseExited

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        // Eliminar un producto
        try {
            Producto p = new Producto();
            p.CodBarra = jtblProductos.getValueAt(jtblProductos.getSelectedRow(), 0).toString();
            p.idSucursal = Integer.parseInt(jtblProductos.getValueAt(jtblProductos.getSelectedRow(), 5).toString());
            ControladorProducto.Eliminar(p);

            jtblProductos.removeAll();
            btnBuscarProducto.doClick();

            JOptionPane.showMessageDialog(null, "Producto Eliminado");

        } catch (ErrorTienda e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar Producto");
        }
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnAgregarNuevoProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarNuevoProductoMouseEntered
        btnAgregarNuevoProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/agregarB.png")));
    }//GEN-LAST:event_btnAgregarNuevoProductoMouseEntered

    private void btnAgregarNuevoProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarNuevoProductoMouseExited
        btnAgregarNuevoProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/agregar.png")));
    }//GEN-LAST:event_btnAgregarNuevoProductoMouseExited

    private void btnAgregarNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarNuevoProductoActionPerformed
        DecimalFormat cuatrodigitos = new DecimalFormat("0.0000");
        // Agregar nuevo producto:
        conection cn = new conection();
        String id = "";

        try {
            cn.Conectar();
            id = cn.BuscarId("Sucursal", "IdSucursal", "Nombre", cmbSucursal2.getSelectedItem().toString());
            Producto producto = new Producto(txtCodBarraProductos.getText(), txtNombreProductos.getText(), Integer.parseInt(txtProductoInventario.getText()),
                    Double.parseDouble(cuatrodigitos.format(Double.parseDouble(txtPrecioProductos.getText()))), Integer.parseInt(id));
            try {
                ControladorProducto.Agregar(producto);
                JOptionPane.showMessageDialog(null, "Agregado");

                jtblProductos.removeAll();
                btnBuscarProducto.doClick();

                jpnNuevoProducto.setVisible(false);
                jpnProductos.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            cn.Desconectar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }//GEN-LAST:event_btnAgregarNuevoProductoActionPerformed

    private void btnSalirProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirProductosMouseEntered
        btnSalirProductos.setIcon(new ImageIcon(getClass().getResource("/iconos/atrasB.png")));
    }//GEN-LAST:event_btnSalirProductosMouseEntered

    private void btnSalirProductosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirProductosMouseExited
        btnSalirProductos.setIcon(new ImageIcon(getClass().getResource("/iconos/atras.png")));
    }//GEN-LAST:event_btnSalirProductosMouseExited

    private void btnSalirProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirProductosActionPerformed
        jpnNuevoProducto.setVisible(false);
        jpnProductos.setVisible(true);
        txtCodBarraProductos.setText("");
        txtNombreProductos.setText("");
        txtProductoInventario.setText("");
        txtPrecioProductos.setText("");
    }//GEN-LAST:event_btnSalirProductosActionPerformed

    private void btnAtrasDetalleCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasDetalleCompraMouseClicked
        jpnDetalleCompra.setVisible(false);
        jpnCompras.setVisible(true);
    }//GEN-LAST:event_btnAtrasDetalleCompraMouseClicked

    private void btnAtrasDetalleCompraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasDetalleCompraMouseEntered
        btnAtrasDetalleCompra.setIcon(new ImageIcon(getClass().getResource("/iconos/atrasB.png")));
    }//GEN-LAST:event_btnAtrasDetalleCompraMouseEntered

    private void btnAtrasDetalleCompraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasDetalleCompraMouseExited
        btnAtrasDetalleCompra.setIcon(new ImageIcon(getClass().getResource("/iconos/atras.png")));
    }//GEN-LAST:event_btnAtrasDetalleCompraMouseExited

    private void btnAgregarVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarVentaMouseClicked
        // TODO add your handling code here:
        jpnAgregarVenta.setVisible(true);
        jpnListaVentas.setVisible(false);
    }//GEN-LAST:event_btnAgregarVentaMouseClicked

    private void btnAgregarVentaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarVentaMouseEntered
        // TODO add your handling code here:
        btnAgregarVenta.setIcon(new ImageIcon(getClass().getResource("/iconos/agregar2B.png")));
    }//GEN-LAST:event_btnAgregarVentaMouseEntered

    private void btnAgregarVentaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarVentaMouseExited
        // TODO add your handling code here:
        btnAgregarVenta.setIcon(new ImageIcon(getClass().getResource("/iconos/agregar2.png")));
    }//GEN-LAST:event_btnAgregarVentaMouseExited

    private void btnAgregarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarVentaActionPerformed
        model0 = (DefaultTableModel) tblProductosVender.getModel();
        model0.setRowCount(0);
        filas = 0;
        txtNoDocVenta.setText("");
        txtClienteVenta.setText("");
        txtNITVenta.setText("");
        txtNRCVenta.setText("");
        txtDireccionVenta.setText("");
        txtGiroVenta.setText("");
        txtCodigoBarraVender.setText("");
        txtNombreProductoVender.setText("");
        txtCantidadVender.setText("");
        txtSumaVenta.setText("");
        txtIvaVenta.setText("");
        txtTotalVenta.setText("");
        cmbTipoVenta.setEnabled(true);
        cmbTipoPrecioVenta.setEnabled(true);
        cmbSucursalVenta.setEnabled(true);

        //NUEVA COMPRA - VARIOS DETALLES
        //NUEVO ID
        ControladorVenta VEnta = new ControladorVenta();
        int idVenta = 0;
        try {
            idVenta = VEnta.ObtenerIdVenta();
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (idVenta == 0) {
            txtIdVenta.setText("1");
        } else {
            txtIdVenta.setText(Integer.toString(idVenta));
        }
        

        //FECHA DEL MOMENTO
        java.util.Date date = new Date();
        Object param = new java.sql.Timestamp(date.getTime());
        //txtFecha.setText(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(param));
        txt_fecha_venta.setDate(date);
        //java.util.Date date = new Date();
        //Object param = new java.sql.Timestamp(date.getTime());
        //txtFecha.setText(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(param));
        //txt_fecha_compra.setDate(date);
        //LISTADO DE TIPOS DE PRECIOS EN COMBOBOX
        try {
            String[] cm = new String[]{"IdTipoPrecio", "Nombre", "Utilidad"};
            ArrayList<Object> listaPrecios = new ArrayList();
            ComboBoxModel modelo;
            cmbTipoPrecioVenta.removeAllItems();

            conection cn = new conection();
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarTodos("tipoprecio", cm);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cmbTipoPrecioVenta.addItem(rs.getString("Nombre"));
                }
                cn.Desconectar();
            } catch (Exception e) {
                //ERROR!!!
            }
        } catch (Exception e) {
        }
        //LISTADO DE SUCURSALES EN COMBOBOX
        try {
            String[] cm = new String[]{"Nombre"};
            ArrayList<Object> listaSucursales = new ArrayList();
            ComboBoxModel modelo;
            cmbSucursalVenta.removeAllItems();

            conection cn = new conection();
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarTodos("sucursal", cm);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cmbSucursalVenta.addItem(rs.getString("Nombre"));
                }
                cn.Desconectar();
            } catch (Exception e) {
                //ERROR!!!
            }
        } catch (Exception e) {
        }

        cmbSucursalVenta.setSelectedIndex(1);
        cmbSucursalVenta.setSelectedIndex(0);
        cmbTipoVenta.setSelectedIndex(1);
        cmbTipoVenta.setSelectedIndex(2);
        cmbTipoPrecioVenta.setSelectedIndex(1);
        cmbTipoPrecioVenta.setSelectedIndex(0);
    }//GEN-LAST:event_btnAgregarVentaActionPerformed

    private void btnVerDetalleVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerDetalleVentaMouseClicked
        jpnDetalleVenta.setVisible(true);
        jpnListaVentas.setVisible(false);
    }//GEN-LAST:event_btnVerDetalleVentaMouseClicked

    private void btnVerDetalleVentaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerDetalleVentaMouseEntered
        // TODO add your handling code here:
        btnVerDetalleVenta.setIcon(new ImageIcon(getClass().getResource("/iconos/detalles2B.png")));
    }//GEN-LAST:event_btnVerDetalleVentaMouseEntered

    private void btnVerDetalleVentaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerDetalleVentaMouseExited
        // TODO add your handling code here:
        btnVerDetalleVenta.setIcon(new ImageIcon(getClass().getResource("/iconos/detalles2.png")));
    }//GEN-LAST:event_btnVerDetalleVentaMouseExited

    private void btnVerDetalleVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetalleVentaActionPerformed
        // Ver un detalle de Venta....
        try {
            //LLEVAR LA INFO AL PANEL DE DETALLE VENTA
            txtIdDetalleVenta.setText(tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 0).toString());
            txtTipoVentaDetalleVenta.setText(tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 3).toString());
            txtSucursalDetalleVenta.setText(tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 2).toString());
            txtClienteDetalleVenta.setText(tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 5).toString());
            txtTipoPrecioDetalleVenta.setText(tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 4).toString());
            txtFechaDetalleVenta.setText(tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 1).toString());
            txtIVADetalleVenta.setText("$ " + tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 6).toString());
            txtTotalGravadoDetalleVenta.setText("$ " + tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 7).toString());
            txtTotalDetalleVenta.setText("$ " + tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 8).toString());
            txtDireccionDetalleVenta.setText(tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 9).toString());
            txtGiroDetalleVenta.setText(tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 10).toString());
            txtNITDetalleVenta.setText(tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 11).toString());
            txtNRCDetalleVenta.setText(tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 12).toString());
            txtNoDocDetalleVenta.setText(tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 13).toString());

            //LLENAR TABlA DE DETALLE VENTA
            String[] cm = new String[]{"CodBarra", "IdVenta", "Cantidad", "PrecioUnitario"};
            iList p = new iList(new ListasTablas("IdVenta", tblListaVentas.getValueAt(tblListaVentas.getSelectedRow(), 0)));
            ArrayList<Object> listaDetalleVentas = new ArrayList();
            DefaultTableModel modelo = new DefaultTableModel();
            conection cn = new conection();
            try {
                cn.Conectar();
                PreparedStatement ps = cn.BuscarRegistro("detalleventa", cm, p);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    listaDetalleVentas.add(rs.getString("CodBarra"));
                    listaDetalleVentas.add(rs.getString("IdVenta"));
                    listaDetalleVentas.add(rs.getString("Cantidad"));
                    listaDetalleVentas.add(rs.getString("PrecioUnitario"));
                }
                cn.Desconectar();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Algo paso llenando la tabla de detalle venta " + e.getMessage());
            }
            ArrayList<DetalleVenta> listaDetalleVenta = (ArrayList) listaDetalleVentas;
            Object[] fila = new Object[4];

            String[] detalleventas = new String[]{"CodBarra", "IdCompra", "Cantidad", "Precio Unitario"};
            modelo.setColumnIdentifiers(detalleventas);
            Iterator<DetalleVenta> venta = listaDetalleVenta.iterator();
            while (venta.hasNext()) {
                fila[0] = venta.next();
                fila[1] = venta.next();
                fila[2] = venta.next();
                fila[3] = venta.next();
                modelo.addRow(fila);

            }
            tblDetalleVenta.setModel(modelo);
            System.out.println("Lleno DetalleVenta!...creo");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleccione una venta");
        }
    }//GEN-LAST:event_btnVerDetalleVentaActionPerformed

    private void btnGuardarModificarProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarModificarProductoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarModificarProductoMouseEntered

    private void btnGuardarModificarProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarModificarProductoMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarModificarProductoMouseExited

    private void btnGuardarModificarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificarProductoActionPerformed
        DecimalFormat cuatrodigitos = new DecimalFormat("0.0000");
        conection cn = new conection();
        try {
            cn.Conectar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        String id = "";
        try {
            id = cn.BuscarId("Sucursal", "IdSucursal", "Nombre", txtNuevaSucursalProd.getText());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "waaaat" + ex.getMessage());
        }
        Producto producto = new Producto(txtNuevoCodigoBarra.getText(), txtNuevoNombreProducto.getText(), Integer.parseInt(txtNuevoInventarioProducto.getText()),
                Double.parseDouble(cuatrodigitos.format(Double.parseDouble(txtNuevoCostoProducto.getText()))), Integer.parseInt(id));
        try {
            ControladorProducto.Modificar(producto);
            JOptionPane.showMessageDialog(rootPane, "Modificado");

            jtblProductos.removeAll();
            btnBuscarProducto.doClick();

            jpnModificarProducto.setVisible(false);
            jpnProductos.setVisible(true);
        } catch (ErrorTienda ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        btnBuscarProducto.doClick();
    }//GEN-LAST:event_btnGuardarModificarProductoActionPerformed

    private void btnAtrasModificarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModificarProductoMouseClicked
        jpnModificarProducto.setVisible(false);
        jpnProductos.setVisible(true);
    }//GEN-LAST:event_btnAtrasModificarProductoMouseClicked

    private void btnAtrasModificarProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModificarProductoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasModificarProductoMouseEntered

    private void btnAtrasModificarProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModificarProductoMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasModificarProductoMouseExited

    private void txtNuevoCostoProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoCostoProductoKeyTyped
        validacion.CostoProducto(evt, txtNuevoCostoProducto.getText());
    }//GEN-LAST:event_txtNuevoCostoProductoKeyTyped

    private void txtNuevoCodigoBarraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoCodigoBarraKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoCodigoBarraKeyTyped

    private void txtNuevoNombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoNombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoNombreProductoActionPerformed

    private void txtNuevoNombreProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoNombreProductoKeyTyped
        int longitud = txtNuevoNombreProducto.getText().length();
        validacion.NombreProducto(evt, longitud);
    }//GEN-LAST:event_txtNuevoNombreProductoKeyTyped

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // Crear compra y el detalle de compra de esa compra...
        DecimalFormat dosdigitos = new DecimalFormat("0.00");
        DecimalFormat cuatrodigitos = new DecimalFormat("0.0000");
        try {
            //Hacer la compra primeramente
            int idcompra = Integer.parseInt(txtIdCompra.getText());
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String dateFormatted = sdf.format(txt_fecha_compra.getDate());                            //.getDate()
            Date date = formatter.parse(dateFormatted);

            System.out.println("FECHAAAA: " + date);
            Proveedor proveedor = new Proveedor();
            Sucursal sucursal = new Sucursal();
            Double total = Double.parseDouble(txtTotal.getText());
            ArrayList<DetalleCompra> articulos;

            //Id PROVEEDOR SELECCIONADO
            String prov = cmbProveedor.getSelectedItem().toString();
            try {
                String[] cm = new String[]{"IdProveedor", "Nombre", "Telefono", "Direccion", "NIT"};
                iList p = new iList(new ListasTablas("Nombre", prov));

                conection cn = new conection();
                try {
                    cn.Conectar();
                    PreparedStatement ps = cn.BuscarRegistro("proveedor", cm, p);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        proveedor.idProveedor = Integer.parseInt(rs.getString("IdProveedor"));
                        proveedor.nombre = rs.getString("Nombre");
                        proveedor.telefono = rs.getString("Telefono");
                        proveedor.direccion = rs.getString("Direccion");
                        proveedor.nit = rs.getString("NIT");
                    }
                    cn.Desconectar();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error interno buscando al proveedor " + e.getMessage());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error externo buscando al proveedor " + e.getMessage());
            }
            //Id SUCURSAL SELECCIONADA
            String suc = cmbSucursalCompra.getSelectedItem().toString();
            try {
                String[] cm = new String[]{"IdSucursal", "Nombre", "Telefono", "Direccion"};
                iList p = new iList(new ListasTablas("Nombre", suc));

                conection cn = new conection();
                try {
                    cn.Conectar();
                    PreparedStatement ps = cn.BuscarRegistro("sucursal", cm, p);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        sucursal.idSucursal = Integer.parseInt(rs.getString("IdSucursal"));
                        sucursal.nombre = rs.getString("Nombre");
                        sucursal.telefono = rs.getString("Telefono");
                        sucursal.direccion = rs.getString("Direccion");
                    }
                    cn.Desconectar();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error interno buscando la sucursal " + e.getMessage());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error externo buscando la sucursal " + e.getMessage());
            }
            String tipoC = "";
            if (cmbTipoCompra.getSelectedItem().toString().equals("Libre")) {
                tipoC = "L";
            } else if (cmbTipoCompra.getSelectedItem().toString().equals("Credito Fiscal")) {
                tipoC = "C";
            } else if (cmbTipoCompra.getSelectedItem().toString().equals("Factura")) {
                tipoC = "F";
            }
            double suma = Double.parseDouble(txtSuma.getText());
            double ivaFinal = Double.parseDouble(txtIva.getText());
            double percepcionFinal = Double.parseDouble(txtPercepcion.getText());
            double granTotal = Double.parseDouble(txtTotal.getText());
            System.out.println("A AGREGAR COMPRA");
            //NUEVO OBJETO COMPRA - AGREGAR A BASE
            //int idCompra, Date fecha, Proveedor proveedor, int idSucursal, char tipoCompra, String numDocumento, double subTotal, double IVA, double percepcion, double total, ArrayList<DetalleCompra> articulo
            Compra compra = new Compra(idcompra, date, proveedor, sucursal.idSucursal, tipoC, txtNumDocCompra.getText(), suma, ivaFinal, percepcionFinal, granTotal);
            ControladorCompra.Agregar(compra);

            //DETALLE COMPRA
            /**
             * 1. Iterar el TableModel por cada tupla del tableModel 2. Crear
             * objeto detalle venta, enviando idCompra 3. Ejecutar Agregar de
             * Detalle Compra*
             */
            TableModel model = tblCompra.getModel();

            for (int row = 0; row < model.getRowCount(); row++) {
                Producto produ = new Producto();
                produ.CodBarra = model.getValueAt(row, 0).toString();
                produ.nombre = model.getValueAt(row, 1).toString();
                produ.inventario = Double.parseDouble(model.getValueAt(row, 2).toString());
                produ.costo = Double.parseDouble(model.getValueAt(row, 3).toString());
                produ.idSucursal = sucursal.idSucursal;
                System.out.println(produ.CodBarra);

                if (ControladorProducto.Obtener(produ.CodBarra).CodBarra == null) {
                    System.out.println("Hey no trae nada hey " + ControladorProducto.Obtener(produ.CodBarra).CodBarra);

                    try {
                        ControladorProducto.Agregar(produ);

                        jtblProductos.removeAll();
                        LlenarProducto("");
                        btnBuscarProducto.doClick();

                        DetalleCompra articulo = new DetalleCompra();
                        articulo.producto = produ;
                        articulo.cantidad = produ.inventario;
                        articulo.costoUnitario = produ.costo;

                        conection cn = new conection();

                        try {
                            cn.Conectar();
                            iList p = new iList(new ListasTablas("CodBarra", produ.CodBarra));
                            p.add(new ListasTablas("IdCompra", idcompra));
                            p.add(new ListasTablas("Cantidad", produ.inventario));
                            p.add(new ListasTablas("CostoUnitario", produ.costo));
                            p.add(new ListasTablas("IdSucursal", produ.idSucursal));

                            cn.AgregarRegistro("detallecompra", p, false);

                        } catch (Exception e) {
                            throw new ErrorTienda("Agregar Detalle Compra 1", e.getMessage());
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    conection cn = new conection();
                    Double inventarioGlobal = 0.0;
                    try {
                        cn.Conectar();
                        inventarioGlobal = cn.InventarioGlobal(produ.CodBarra);
                    } catch (Exception e) {
                    }
                    System.out.println("Hey si trae algo hey BARRA" + ControladorProducto.Obtener(produ.CodBarra).CodBarra);

                    Producto actProdu = new Producto();

                    actProdu.CodBarra = produ.CodBarra;
                    actProdu.nombre = produ.nombre;
                    actProdu.idSucursal = sucursal.idSucursal;
                    actProdu.inventario = ControladorProducto.ObtenerInventario(produ.CodBarra, produ.idSucursal).cantidad + produ.inventario;
                    System.out.println("INVENTARIO A INGRESAR:" + actProdu.inventario);
                    Double cantTotalActual = inventarioGlobal * ControladorProducto.Obtener(produ.CodBarra).costo;
                    System.out.println("CANTIDAD TOTAL ACTUAL:" + cantTotalActual);

                    Double cantTotalNueva = produ.inventario * produ.costo;
                    System.out.println("CANTIDAD TOTAL NUEVA:" + cantTotalNueva);
                    actProdu.costo = Double.parseDouble(cuatrodigitos.format((cantTotalActual + cantTotalNueva) / (inventarioGlobal + produ.inventario)));
                    System.out.println("COSTO A INGRESAR:" + actProdu.costo);
                    ControladorProducto.Modificar(actProdu);
                    jtblProductos.removeAll();
                    LlenarProducto("");
                    btnBuscarProducto.doClick();

                    DetalleCompra articulo = new DetalleCompra();
                    articulo.producto = produ;
                    articulo.cantidad = produ.inventario;
                    articulo.costoUnitario = produ.costo;

                    try {
                        cn.Conectar();
                        iList p = new iList(new ListasTablas("CodBarra", produ.CodBarra));
                        p.add(new ListasTablas("IdCompra", idcompra));
                        p.add(new ListasTablas("Cantidad", produ.inventario));
                        p.add(new ListasTablas("CostoUnitario", produ.costo));
                        p.add(new ListasTablas("IdSucursal", produ.idSucursal));

                        cn.AgregarRegistro("detallecompra", p, false);

                    } catch (Exception e) {
                        throw new ErrorTienda("Agregar Detalle Compra 2", e.getMessage());
                    }
                }

            }

            //FIN
            JOptionPane.showMessageDialog(null, "Compra registrada");
            jpnRegistroCompra.setVisible(false);
            jpnCompras.setVisible(true);

            model0 = (DefaultTableModel) this.tblCompra.getModel();
            model0.setRowCount(0);
            filas = 0;

            LlenarCompra();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nope " + e.getMessage());
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        jpnCompras.setVisible(true);
        jpnRegistroCompra.setVisible(false);
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarMouseExited

    private void txtIdCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCompraActionPerformed

    private void btnAgregarProd1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProd1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarProd1MouseClicked

    private void btnAgregarProd1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProd1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarProd1MouseEntered

    private void btnAgregarProd1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProd1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarProd1MouseExited

    private void btnAgregarProd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProd1ActionPerformed
//        Compra compra = new Compra();
        txtNomProd.setEditable(true);
//        DetalleCompra objetoDetalle = new DetalleCompra();.
//        objetoDetalle.
//        ArrayList<DetalleCompra> listaCompra = compra.AgregarItem(objetoDetalle);

        /*dc.producto.CodBarra= this.txtCodBarraProd.getText();

        dc.costoUnitario= Double.parseDouble(this.txtCostoProd.getText());
        dc.cantidad= Integer.parseInt(this.txtCantidad.getText());

        try {
            compra.AgregarItem(dc);
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        if (!this.txtCodBarraProd.getText().equals("") && !this.txtNomProd.getText().equals("") && !this.txtCantidad.getText().equals("") && !this.txtCostoProd.getText().equals("")) {
            conection cn = new conection();
            String iva = null;
            try {
                cn.Conectar();
                iva = cn.ValorParametro("iva");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            boolean encontrado = false;
            String n = "";
            DecimalFormat dosdigitos = new DecimalFormat("0.00");
            DecimalFormat cuatrodigitos = new DecimalFormat("0.0000");
            totalC = 0;

            TableModel model = tblCompra.getModel();
            for (int row = 0; row < model.getRowCount(); row++) {
                if (this.txtCodBarraProd.getText().equals(model.getValueAt(row, 0).toString())) {
                    encontrado = true;
                    //totalC = Double.parseDouble(dosdigitos.format(totalC - Double.parseDouble(model.getValueAt(row, 4).toString())));
                    model.setValueAt(this.txtNomProd.getText(), row, 1);
                    double actCantidad = Double.parseDouble(this.txtCantidad.getText()) + Double.parseDouble(model.getValueAt(row, 2).toString());
                    model.setValueAt(actCantidad, row, 2);
                    Double costoNuevo = Double.parseDouble(this.txtCostoProd.getText()) * Double.parseDouble(this.txtCantidad.getText());
                    Double costoActual = Double.parseDouble(model.getValueAt(row, 2).toString()) * Double.parseDouble(model.getValueAt(row, 3).toString());
                    Double totalUnidades = Double.parseDouble(this.txtCantidad.getText()) + Double.parseDouble(model.getValueAt(row, 2).toString());
                    Double actCosto = Double.parseDouble(cuatrodigitos.format((costoNuevo + costoActual) / totalUnidades));
                    model.setValueAt(actCosto, row, 3);
                    //model0.setValueAt(dosdigitos.format(((Integer.parseInt(model.getValueAt(row, 2).toString()) * Double.parseDouble(model.getValueAt(row, 3).toString()) * Double.parseDouble(iva)))), row, 4);
                    if (cmbTipoCompra.getSelectedIndex() == 2) {
                        model0.setValueAt(cuatrodigitos.format(((Double.parseDouble(model.getValueAt(row, 2).toString()) * Double.parseDouble(model.getValueAt(row, 3).toString()) * Double.parseDouble(iva)))), row, 4);
                    } else {
                        model0.setValueAt(0, row, 4);
                    }
                    model0.setValueAt(cuatrodigitos.format(((Double.parseDouble(model.getValueAt(row, 2).toString()) * Double.parseDouble(model.getValueAt(row, 3).toString())) + Double.parseDouble(model.getValueAt(row, 4).toString()))), row, 5);

//                    model0.setValueAt(dosdigitos.format(((Integer.parseInt(model.getValueAt(row, 2).toString()) * Double.parseDouble(model.getValueAt(row, 3).toString())) + Double.parseDouble(model.getValueAt(row, 4).toString()))), row, 5);
//                    n = String.valueOf(dosdigitos.format(Double.parseDouble(model.getValueAt(row, 4).toString())));
//                    totalC += Double.parseDouble(n);
                    cmbTipoCompra.setEnabled(false);
                    this.txtCodBarraProd.setText("");
                    this.txtNomProd.setText("");
                    this.txtCantidad.setText("");
                    this.txtCostoProd.setText("");
                }
            }

            if (encontrado == false) {
                model0 = (DefaultTableModel) this.tblCompra.getModel();
                model0.addRow(new Object[filas]);
                for (int i = 0; i < this.tblCompra.getColumnCount() - 1; i++) {
                    model0.setValueAt(this.txtCodBarraProd.getText(), filas, 0);
                    model0.setValueAt(this.txtNomProd.getText(), filas, 1);
                    model0.setValueAt(this.txtCantidad.getText(), filas, 2);
                    model0.setValueAt(cuatrodigitos.format(Double.parseDouble(this.txtCostoProd.getText())), filas, 3);
                    //model0.setValueAt(dosdigitos.format(((Integer.parseInt(this.txtCantidad.getText()) * Double.parseDouble(this.txtCostoProd.getText())) * Double.parseDouble(iva))), filas, 4);

                    if (cmbTipoCompra.getSelectedIndex() == 2) {
                        model0.setValueAt(cuatrodigitos.format(((Double.parseDouble(this.txtCantidad.getText()) * Double.parseDouble(this.txtCostoProd.getText())) * Double.parseDouble(iva))), filas, 4);
                    } else {
                        model0.setValueAt(0, filas, 4);
                    }
                    model0.setValueAt(cuatrodigitos.format(((Double.parseDouble(model.getValueAt(filas, 2).toString()) * Double.parseDouble(model.getValueAt(filas, 3).toString())) + Double.parseDouble(model.getValueAt(filas, 4).toString()))), filas, 5);
                }
                /*try {
                    double tot=compra.CalcularTotal();
                    this.txtTotal.setText(String.valueOf(tot));
                } catch (ErrorTienda ex) {
                    Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }*/

//                n = String.valueOf(dosdigitos.format(Double.parseDouble(model0.getValueAt(filas, 4).toString())));
//                totalC += Double.parseDouble(n);
                filas++;
                cmbTipoCompra.setEnabled(false);
                this.txtCodBarraProd.setText("");
                this.txtNomProd.setText("");
                this.txtCantidad.setText("");
                this.txtCostoProd.setText("");
            }
            //TOTALES

            switch (cmbTipoCompra.getSelectedItem().toString()) {
                case "Libre":
                    for (int row = 0; row < model.getRowCount(); row++) {
                        totalC = Double.parseDouble(dosdigitos.format(totalC + Double.parseDouble(model.getValueAt(row, 5).toString())));
                    }
                    txtTotal.setText("" + totalC);
                    txtSuma.setText("" + totalC);
                    txtIva.setText("0");
                    txtPercepcion.setText("0");
                    break;
                case "Credito Fiscal":
                    for (int row = 0; row < model.getRowCount(); row++) {
                        totalC = Double.parseDouble(dosdigitos.format(totalC + Double.parseDouble(model.getValueAt(row, 5).toString())));
                    }
                    txtSuma.setText("" + dosdigitos.format(totalC));
                    double ivaFinal = totalC * Double.parseDouble(iva);
                    txtIva.setText("" + dosdigitos.format(ivaFinal));
                    String per = cn.ValorParametro("percepcion");
                    double percepcion = totalC * Double.parseDouble(per);
                    txtPercepcion.setText("" + dosdigitos.format(percepcion));
                    double granTotal = totalC + ivaFinal + percepcion;
                    txtTotal.setText("" + dosdigitos.format(granTotal));
                    break;
                case "Factura":
                    for (int row = 0; row < model.getRowCount(); row++) {
                        totalC = Double.parseDouble(dosdigitos.format(totalC + Double.parseDouble(model.getValueAt(row, 5).toString())));
                    }
                    txtSuma.setText("" + dosdigitos.format(totalC));
                    txtTotal.setText("" + dosdigitos.format(totalC));
                    txtIva.setText("0");
                    txtPercepcion.setText("0");
                    break;
            }
            txtCodBarraProd.requestFocus();

            //this.txtTotal.setText(String.valueOf(totalC));
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese todos los datos");
        }
    }//GEN-LAST:event_btnAgregarProd1ActionPerformed

    private void btnEliminarprodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarprodMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarprodMouseEntered

    private void btnEliminarprodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarprodMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarprodMouseExited

    private void btnEliminarprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarprodActionPerformed
        try {
            conection cn = new conection();
            String iva = null;
            try {
                cn.Conectar();
                iva = cn.ValorParametro("iva");
            } catch (Exception e) {
            }
            DefaultTableModel model = (DefaultTableModel) tblCompra.getModel();
            model.removeRow(tblCompra.getSelectedRow());
            DecimalFormat dosdigitos = new DecimalFormat("0.00");
            Double totalC = 0.0;
            switch (cmbTipoCompra.getSelectedItem().toString()) {
                case "Libre":
                    for (int row = 0; row < model.getRowCount(); row++) {
                        totalC = totalC + Double.parseDouble(dosdigitos.format(Double.parseDouble(model.getValueAt(row, 5).toString())));
                    }
                    txtTotal.setText("" + totalC);
                    break;
                case "Credito Fiscal":
                    for (int row = 0; row < model.getRowCount(); row++) {
                        totalC = totalC + Double.parseDouble(dosdigitos.format(Double.parseDouble(model.getValueAt(row, 5).toString())));
                    }
                    txtSuma.setText("" + dosdigitos.format(totalC));
                    double ivaFinal = totalC * Double.parseDouble(iva);
                    txtIva.setText("" + dosdigitos.format(ivaFinal));
                    String per = cn.ValorParametro("percepcion");
                    double percepcion = totalC * Double.parseDouble(per);
                    txtPercepcion.setText("" + dosdigitos.format(percepcion));
                    double granTotal = totalC + ivaFinal + percepcion;
                    txtTotal.setText("" + dosdigitos.format(granTotal));
                    break;
                case "Factura":
                    for (int row = 0; row < model.getRowCount(); row++) {
                        totalC = totalC + Double.parseDouble(dosdigitos.format(Double.parseDouble(model.getValueAt(row, 5).toString())));
                    }
                    txtSuma.setText("" + totalC);
                    txtTotal.setText("" + totalC);
                    break;
            }
            filas--;
            if (tblCompra.getRowCount() == 0) {
                cmbTipoCompra.setEnabled(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto " + e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarprodActionPerformed

    private void btnModificarTPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarTPMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarTPMouseClicked

    private void btnModificarTPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarTPMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarTPMouseEntered

    private void btnModificarTPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarTPMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarTPMouseExited

    private void btnModificarTPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarTPActionPerformed
        try {
            int c = Integer.parseInt(tblTP.getValueAt(tblTP.getSelectedRow(), 0).toString());
            if (c == 1) {
                txtNomPar.setEditable(false);
            }
            txtNomPar.setText(tblTP.getValueAt(tblTP.getSelectedRow(), 1).toString());
            txtUtPar.setText(tblTP.getValueAt(tblTP.getSelectedRow(), 2).toString());

            txtIdPar.setText(tblTP.getValueAt(tblTP.getSelectedRow(), 0).toString());
            jpnTipoPrecio.setVisible(false);
            jpnModificarPrecio.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleccione un Tipo de precio");
        }
    }//GEN-LAST:event_btnModificarTPActionPerformed

    private void btnBuscarTPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarTPMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarTPMouseEntered

    private void btnBuscarTPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarTPMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarTPMouseExited

    private void btnBuscarTPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTPActionPerformed
        System.out.println("LLENAR Parametro Buscar" + "");
        DefaultTableModel modelo = new DefaultTableModel();
        ArrayList<TipoPrecio> tp = new ArrayList();
        Object[] fila = new Object[3];
        try {
            tp = ControladorTipoPrecio.Buscar(txtBuscaTipoPrecio.getText());
            String[] tps = new String[]{"IdTipoPrecio", "Nombre", "Utilidad"};
            modelo.setColumnIdentifiers(tps);
            Iterator<TipoPrecio> prov = tp.iterator();
            while (prov.hasNext()) {
                fila[0] = prov.next();
                fila[1] = prov.next();
                fila[2] = prov.next();

                modelo.addRow(fila);
                tblTP.setModel(modelo);
            }
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarTPActionPerformed

    private void btnAtrasDetalleCompra1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasDetalleCompra1MouseClicked
        jpnDetalleVenta.setVisible(false);
        jpnListaVentas.setVisible(true);
    }//GEN-LAST:event_btnAtrasDetalleCompra1MouseClicked

    private void btnAtrasDetalleCompra1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasDetalleCompra1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasDetalleCompra1MouseEntered

    private void btnAtrasDetalleCompra1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasDetalleCompra1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasDetalleCompra1MouseExited

    private void txtEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyTyped
        int longitud = txtEmail.getText().length();
        validacion.Email(evt, longitud);
    }//GEN-LAST:event_txtEmailKeyTyped

    private void cmbProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbProveedorActionPerformed

    private void btnGuardarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarMouseDragged

    private void txtNRCProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNRCProveedorKeyTyped
        int longitud = txtNRCProveedor.getText().length();
        validacion.NRC(evt, longitud);
    }//GEN-LAST:event_txtNRCProveedorKeyTyped

    private void txtEmailProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailProveedorKeyTyped
        int longitud = txtEmailProveedor.getText().length();
        validacion.Email(evt, longitud);
    }//GEN-LAST:event_txtEmailProveedorKeyTyped

    private void cmbSucursales1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSucursales1ItemStateChanged
        btnBuscarProducto.doClick();
    }//GEN-LAST:event_cmbSucursales1ItemStateChanged

    private void cmbSucursal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSucursal2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSucursal2ActionPerformed

    private void txtCodBarraProductosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBarraProductosFocusLost

    }//GEN-LAST:event_txtCodBarraProductosFocusLost

    private void btnBuscarProdCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarProdCompraMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarProdCompraMouseClicked

    private void btnBuscarProdCompraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarProdCompraMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarProdCompraMouseEntered

    private void btnBuscarProdCompraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarProdCompraMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarProdCompraMouseExited

    private void btnBuscarProdCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProdCompraActionPerformed

        BuscarProductoComprar();
    }//GEN-LAST:event_btnBuscarProdCompraActionPerformed

    private void txtProveedorDetalleCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProveedorDetalleCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProveedorDetalleCompraActionPerformed

    private void txtSucursalDetalleCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSucursalDetalleCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSucursalDetalleCompraActionPerformed

    private void txtNumDocumentoDetalleCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocumentoDetalleCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumDocumentoDetalleCompraActionPerformed

    private void txtTipoCompraDetalleCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoCompraDetalleCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoCompraDetalleCompraActionPerformed

    private void cmbTipoCompraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTipoCompraItemStateChanged
        String tipoCompra = cmbTipoCompra.getSelectedItem().toString();
        switch (tipoCompra) {
            case "Libre":
                tblCompra.getColumnModel().getColumn(4).setMinWidth(0);
                tblCompra.getColumnModel().getColumn(4).setMaxWidth(0);
                tblCompra.getColumnModel().getColumn(4).setWidth(0);
                txtSuma.setVisible(false);
                txtSuma.setText(txtTotal.getText());
                lblSuma.setVisible(false);
                txtIva.setVisible(false);
                txtIva.setText("0");
                lblIva.setVisible(false);
                txtPercepcion.setVisible(false);
                lblPercepcion.setVisible(false);
                txtPercepcion.setText("0");
                lblIva.setVisible(false);
                break;
            case "Factura":
                tblCompra.getColumnModel().getColumn(4).setMinWidth(0);
                tblCompra.getColumnModel().getColumn(4).setMaxWidth(0);
                tblCompra.getColumnModel().getColumn(4).setWidth(0);
//                tblCompra.getColumnModel().getColumn(4).setMinWidth(tblCompra.getColumnModel().getColumn(3).getWidth() - 15);
//                tblCompra.getColumnModel().getColumn(4).setMaxWidth(tblCompra.getColumnModel().getColumn(3).getWidth() - 15);
//                tblCompra.getColumnModel().getColumn(4).setWidth(tblCompra.getColumnModel().getColumn(3).getWidth() - 15);
                txtSuma.setVisible(true);
                lblSuma.setVisible(true);
                txtIva.setVisible(false);
                txtIva.setText("0");
                txtPercepcion.setVisible(false);
                lblPercepcion.setVisible(false);
                lblIva.setVisible(false);
                break;
            case "Credito Fiscal":
                tblCompra.getColumnModel().getColumn(4).setMinWidth(0);
                tblCompra.getColumnModel().getColumn(4).setMaxWidth(0);
                tblCompra.getColumnModel().getColumn(4).setWidth(0);
                tblCompra.getColumnModel().getColumn(4).sizeWidthToFit();
                txtSuma.setVisible(true);
                lblSuma.setVisible(true);
                txtIva.setVisible(true);
                lblIva.setVisible(true);
                txtPercepcion.setVisible(true);
                txtPercepcion.setEditable(true);
                lblPercepcion.setVisible(true);
                break;
            default:
                tblCompra.getColumnModel().getColumn(4).setMinWidth(0);
                tblCompra.getColumnModel().getColumn(4).setMaxWidth(0);
                tblCompra.getColumnModel().getColumn(4).setWidth(0);
                txtSuma.setVisible(false);
                lblSuma.setVisible(false);
                txtIva.setVisible(false);
                lblIva.setVisible(false);
                txtPercepcion.setVisible(false);
                lblPercepcion.setVisible(false);
                break;
        }
    }//GEN-LAST:event_cmbTipoCompraItemStateChanged

    private void btnGuardarSucMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarSucMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarSucMouseEntered

    private void btnGuardarSucMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarSucMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarSucMouseExited

    private void btnGuardarSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarSucActionPerformed
        Sucursal Suc = new Sucursal();
        Suc.idSucursal = Integer.parseInt(txtModIdSuc.getText());
        Suc.nombre = txtModNombreSuc.getText();
        Suc.direccion = txtModDirSuc.getText();
        Suc.telefono = txtTelSuc.getText();
        try {
            ControladorSucursal.Modificar(Suc);
            JOptionPane.showMessageDialog(rootPane, "Modificado");

            tblSucursal.removeAll();
            LlenarSucursal();

            jpnModificarSucursal.setVisible(false);
            jpnSucursal.setVisible(true);
        } catch (ErrorTienda ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        LlenarProveedor();
    }//GEN-LAST:event_btnGuardarSucActionPerformed

    private void btnAtrasModificarProducto1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModificarProducto1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasModificarProducto1MouseClicked

    private void btnAtrasModificarProducto1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModificarProducto1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasModificarProducto1MouseEntered

    private void btnAtrasModificarProducto1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModificarProducto1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasModificarProducto1MouseExited

    private void btnAtrasModificarProducto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasModificarProducto1ActionPerformed
        jpnSucursal.setVisible(true);
        jpnModificarSucursal.setVisible(false);
    }//GEN-LAST:event_btnAtrasModificarProducto1ActionPerformed

    private void btnAgregarSucMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarSucMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarSucMouseEntered

    private void btnAgregarSucMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarSucMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarSucMouseExited

    private void btnAgregarSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarSucActionPerformed

        Sucursal Suc = new Sucursal();
        Suc.idSucursal = Integer.parseInt(txtIdSuc.getText());
        Suc.nombre = txtNombreSuc.getText();
        Suc.direccion = txtDireccionSuc.getText();
        Suc.telefono = txtTelefonoSuc.getText();
        try {
            ControladorSucursal.agregarSucursal(Suc);
            JOptionPane.showMessageDialog(rootPane, "Agregado");
            this.txtNombreSuc.setText("");
            this.txtDireccionSuc.setText("");
            this.txtTelefonoSuc.setText("");
            this.txtNIT.setText("");
            txtIdSuc.setText("");

            tblSucursal.removeAll();
            LlenarSucursal();
            apagado2();
            jpnNuevaSucursal.setVisible(false);
            jpnSucursal.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }//GEN-LAST:event_btnAgregarSucActionPerformed

    private void btnAtrasSucMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasSucMouseEntered

    }//GEN-LAST:event_btnAtrasSucMouseEntered

    private void btnAtrasSucMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasSucMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasSucMouseExited

    private void btnAtrasSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasSucActionPerformed
        jpnNuevaSucursal.setVisible(false);
        jpnSucursal.setVisible(true);

    }//GEN-LAST:event_btnAtrasSucActionPerformed

    private void txtTelefonoSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoSucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoSucActionPerformed

    private void txtTelefonoSucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoSucKeyTyped
        int longitud = txtTelefonoSuc.getText().length();
        validacion.Tel(evt, longitud);
    }//GEN-LAST:event_txtTelefonoSucKeyTyped

    private void btnSucursalNuevaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSucursalNuevaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSucursalNuevaMouseEntered

    private void btnSucursalNuevaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSucursalNuevaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSucursalNuevaMouseExited

    private void btnSucursalNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSucursalNuevaActionPerformed
        jpnNuevaSucursal.setVisible(true);
        jpnSucursal.setVisible(false);
        txtNombreSuc.setText("");
        txtDireccionSuc.setText("");
        txtTelefonoSuc.setText("");

        ControladorSucursal CSucursal = new ControladorSucursal();
        int idSuc = 0;
        try {
            idSuc = CSucursal.ObtenerIdSucursal();
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.txtIdSuc.setText(Integer.toString(idSuc));
    }//GEN-LAST:event_btnSucursalNuevaActionPerformed

    private void btnSucursalBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSucursalBuscarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSucursalBuscarMouseEntered

    private void btnSucursalBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSucursalBuscarMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSucursalBuscarMouseExited

    private void btnSucursalBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSucursalBuscarActionPerformed
        System.out.println("LLENAR Sucursal Buscar" + "");
        DefaultTableModel modelo = new DefaultTableModel();
        ArrayList<Sucursal> sucursal = new ArrayList();
        Object[] fila = new Object[4];
        try {
            sucursal = ControladorSucursal.Buscar(txtSucursalBuscar.getText());
            String[] sucursales = new String[]{"IdSucursal", "Nombre", "Direccion", "Telefono"};
            modelo.setColumnIdentifiers(sucursales);
            Iterator<Sucursal> suc = sucursal.iterator();
            while (suc.hasNext()) {
                fila[0] = suc.next();
                fila[1] = suc.next();
                fila[2] = suc.next();
                fila[3] = suc.next();

                modelo.addRow(fila);
                tblSucursal.setModel(modelo);
            }
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSucursalBuscarActionPerformed

    private void btnModSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModSucursalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModSucursalMouseClicked

    private void btnModSucursalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModSucursalMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModSucursalMouseEntered

    private void btnModSucursalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModSucursalMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModSucursalMouseExited

    private void btnModSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModSucursalActionPerformed
        try {
            txtModNombreSuc.setText(tblSucursal.getValueAt(tblSucursal.getSelectedRow(), 1).toString());
            txtModDirSuc.setText(tblSucursal.getValueAt(tblSucursal.getSelectedRow(), 2).toString());
            txtTelSuc.setText(tblSucursal.getValueAt(tblSucursal.getSelectedRow(), 3).toString());
            txtModIdSuc.setText(tblSucursal.getValueAt(tblSucursal.getSelectedRow(), 0).toString());
            jpnSucursal.setVisible(false);
            jpnModificarSucursal.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleccione una Sucursal");
        }
        int c = tblSucursal.getRowCount();
        System.out.println("numero de filas: " + c);
    }//GEN-LAST:event_btnModSucursalActionPerformed

    private void btnSucursalEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSucursalEliminarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSucursalEliminarMouseEntered

    private void btnSucursalEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSucursalEliminarMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSucursalEliminarMouseExited

    private void btnSucursalEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSucursalEliminarActionPerformed
        if (tblSucursal.getSelectedRow() != -1) {
            int c = tblSucursal.getRowCount();
            if (c == 1) {
                JOptionPane.showMessageDialog(null, "Necesita al menos una sucursal para trabajar");
            } else {
                try {
                    Sucursal s = new Sucursal();
                    s.idSucursal = Integer.parseInt(tblSucursal.getValueAt(tblSucursal.getSelectedRow(), 0).toString());
                    ControladorSucursal.eliminarSucursal(s);

                    tblSucursal.removeAll();
                    LlenarSucursal();

                    JOptionPane.showMessageDialog(null, "Sucursal Eliminada");

                } catch (ErrorTienda e) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar Sucursal");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "seleccione una sucursal");

        }

    }//GEN-LAST:event_btnSucursalEliminarActionPerformed

    private void txtSucursalBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSucursalBuscarActionPerformed

    }//GEN-LAST:event_txtSucursalBuscarActionPerformed

    private void txtSucursalBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSucursalBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("LLENAR Sucursal Buscar" + "");
            DefaultTableModel modelo = new DefaultTableModel();
            ArrayList<Sucursal> sucursal = new ArrayList();
            Object[] fila = new Object[4];
            try {
                sucursal = ControladorSucursal.Buscar(txtSucursalBuscar.getText());
                String[] sucursales = new String[]{"IdSucursal", "Nombre", "Direccion", "Telefono"};
                modelo.setColumnIdentifiers(sucursales);
                Iterator<Sucursal> prov = sucursal.iterator();
                while (prov.hasNext()) {
                    fila[0] = prov.next();
                    fila[1] = prov.next();
                    fila[2] = prov.next();
                    fila[3] = prov.next();

                    modelo.addRow(fila);
                    tblSucursal.setModel(modelo);
                }
            } catch (ErrorTienda ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtSucursalBuscarKeyPressed

    private void btnAbrirSucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirSucMouseClicked
        apagado();

        apagado2();
        jpnSucursal.setVisible(true);
        LlenarSucursal();
    }//GEN-LAST:event_btnAbrirSucMouseClicked

    private void btnAbrirTPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirTPMouseClicked
        apagado();

        apagado2();
        jpnTipoPrecio.setVisible(true);
    }//GEN-LAST:event_btnAbrirTPMouseClicked

    private void btnGuardarParMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarParMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarParMouseEntered

    private void btnGuardarParMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarParMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarParMouseExited

    private void btnGuardarParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarParActionPerformed
        TipoPrecio tp = new TipoPrecio();
        tp.idTipoPrecio = Integer.parseInt(txtIdPar.getText());
        tp.nombre = txtNomPar.getText();
        tp.utilidad = Double.parseDouble(txtUtPar.getText());

        try {
            ControladorTipoPrecio.Modificar(tp);
            JOptionPane.showMessageDialog(rootPane, "Modificado");

            tblTP.removeAll();
            LlenarTipoPrecio();

            jpnModificarPrecio.setVisible(false);
            jpnTipoPrecio.setVisible(true);
        } catch (ErrorTienda ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        LlenarProveedor();
    }//GEN-LAST:event_btnGuardarParActionPerformed

    private void btnAtrasModParMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModParMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasModParMouseClicked

    private void btnAtrasModParMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModParMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasModParMouseEntered

    private void btnAtrasModParMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModParMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasModParMouseExited

    private void btnAtrasModParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasModParActionPerformed
        jpnModificarPrecio.setVisible(false);
        jpnTipoPrecio.setVisible(true);
    }//GEN-LAST:event_btnAtrasModParActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void btnVenderMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVenderMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVenderMouseEntered

    private void btnVenderMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVenderMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVenderMouseExited

    private void btnEliminarProductoVentaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProductoVentaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarProductoVentaMouseEntered

    private void btnEliminarProductoVentaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProductoVentaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarProductoVentaMouseExited

    private void btnAgregarProductoVentaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProductoVentaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarProductoVentaMouseEntered

    private void btnAgregarProductoVentaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProductoVentaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarProductoVentaMouseExited

    private void btnAbrirTPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirTPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAbrirTPActionPerformed

    private void btnAgregarSuc1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarSuc1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarSuc1MouseEntered

    private void btnAgregarSuc1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarSuc1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarSuc1MouseExited

    private void btnAgregarSuc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarSuc1ActionPerformed
        TipoPrecio TP = new TipoPrecio();
        TP.idTipoPrecio = Integer.parseInt(txtIdTP.getText());
        TP.nombre = txtNombrePrecio.getText();
        TP.utilidad = Double.parseDouble(txtUtilidadPrecio.getText());
        try {
            ControladorTipoPrecio.agregarTipoPrecio(TP);
            JOptionPane.showMessageDialog(rootPane, "Agregado");

            tblTP.removeAll();
            LlenarTipoPrecio();
            apagado2();
            jpnNuevoPrecio.setVisible(false);
            jpnTipoPrecio.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnAgregarSuc1ActionPerformed

    private void btnAtrasSuc1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasSuc1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasSuc1MouseEntered

    private void btnAtrasSuc1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasSuc1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasSuc1MouseExited

    private void btnAtrasSuc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasSuc1ActionPerformed
        jpnNuevoPrecio.setVisible(false);
        jpnTipoPrecio.setVisible(true);
    }//GEN-LAST:event_btnAtrasSuc1ActionPerformed

    private void btnTPNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTPNuevoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTPNuevoMouseEntered

    private void btnTPNuevoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTPNuevoMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTPNuevoMouseExited

    private void btnTPNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTPNuevoActionPerformed
        jpnNuevoPrecio.setVisible(true);
        jpnTipoPrecio.setVisible(false);
        txtNombrePrecio.setText("");
        txtUtilidadPrecio.setText("");

        ControladorTipoPrecio CTipoPrecio = new ControladorTipoPrecio();
        int idTP = 0;
        try {
            idTP = CTipoPrecio.ObtenerIdTipoPrecio();
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.txtIdTP.setText(Integer.toString(idTP));
    }//GEN-LAST:event_btnTPNuevoActionPerformed

    private void btnTPEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTPEliminarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTPEliminarMouseEntered

    private void btnTPEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTPEliminarMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTPEliminarMouseExited

    private void btnTPEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTPEliminarActionPerformed
        if (tblTP.getSelectedRow() != -1) {
            int c = Integer.parseInt(tblTP.getValueAt(tblTP.getSelectedRow(), 0).toString());
            if (c == 1) {
                JOptionPane.showMessageDialog(null, "No puede Eliminar este Tipo de Precio");
            } else {
                try {
                    TipoPrecio tp = new TipoPrecio();
                    tp.idTipoPrecio = Integer.parseInt(tblTP.getValueAt(tblTP.getSelectedRow(), 0).toString());
                    ControladorTipoPrecio.eliminarTipoPrecio(tp);

                    tblTP.removeAll();
                    LlenarTipoPrecio();

                    JOptionPane.showMessageDialog(null, "Tipo de precio Eliminado");

                } catch (ErrorTienda e) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar tipo de precio");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "seleccione un tipo de precio");

        }
    }//GEN-LAST:event_btnTPEliminarActionPerformed

    private void btnModificarParametroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarParametroMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarParametroMouseClicked

    private void btnModificarParametroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarParametroMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarParametroMouseEntered

    private void btnModificarParametroMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarParametroMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarParametroMouseExited

    private void btnModificarParametroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarParametroActionPerformed
        try {
            txtNomPar1.setText(tblParametro.getValueAt(tblParametro.getSelectedRow(), 1).toString());
            txtValorPar.setText(tblParametro.getValueAt(tblParametro.getSelectedRow(), 2).toString());
            txtIdPar1.setText(tblParametro.getValueAt(tblParametro.getSelectedRow(), 0).toString());
            jpnConfiguracion.setVisible(false);
            jpnModificarParametro.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleccione un Parametro");
        }
    }//GEN-LAST:event_btnModificarParametroActionPerformed

    private void btnBuscarParametroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarParametroMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarParametroMouseEntered

    private void btnBuscarParametroMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarParametroMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarParametroMouseExited

    private void btnBuscarParametroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarParametroActionPerformed
        System.out.println("LLENAR Parametro Buscar" + "");
        DefaultTableModel modelo = new DefaultTableModel();
        ArrayList<Parametro> parametro = new ArrayList();
        Object[] fila = new Object[3];
        try {
            parametro = Parametro.Buscar(txtBuscarParametro.getText());
            String[] parametros = new String[]{"IdParametro", "Nombre", "Valor"};
            modelo.setColumnIdentifiers(parametros);
            Iterator<Parametro> par = parametro.iterator();
            while (par.hasNext()) {
                fila[0] = par.next();
                fila[1] = par.next();
                fila[2] = par.next();

                modelo.addRow(fila);
                tblParametro.setModel(modelo);
            }
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarParametroActionPerformed

    private void btnGuardarPar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarPar1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarPar1MouseEntered

    private void btnGuardarPar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarPar1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarPar1MouseExited

    private void btnGuardarPar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPar1ActionPerformed
        Parametro par = new Parametro();
        par.idParametro = Integer.parseInt(txtIdPar1.getText());
        par.valor = txtValorPar.getText();
        try {
            Parametro.Modificar(par);
            JOptionPane.showMessageDialog(rootPane, "Modificado");

            tblParametro.removeAll();
            LlenarParametros();

            jpnModificarParametro.setVisible(false);
            jpnConfiguracion.setVisible(true);
        } catch (ErrorTienda ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        LlenarParametros();
    }//GEN-LAST:event_btnGuardarPar1ActionPerformed

    private void btnAtrasModPar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModPar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasModPar1MouseClicked

    private void btnAtrasModPar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModPar1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasModPar1MouseEntered

    private void btnAtrasModPar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModPar1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasModPar1MouseExited

    private void btnAtrasModPar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasModPar1ActionPerformed
        jpnModificarParametro.setVisible(false);
        jpnConfiguracion.setVisible(true);
    }//GEN-LAST:event_btnAtrasModPar1ActionPerformed

    private void btnAbrirParaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirParaMouseClicked
        apagado();

        apagado2();
        jpnConfiguracion.setVisible(true);
    }//GEN-LAST:event_btnAbrirParaMouseClicked

    private void btnAbrirParaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirParaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAbrirParaActionPerformed

    private void btnAbrirSucMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirSucMouseEntered
        if (!sucursales) {
            Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnAbrirSuc);
        }
    }//GEN-LAST:event_btnAbrirSucMouseEntered

    private void btnAbrirSucMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirSucMouseExited
        if (!sucursales) {
            Animacion.Animacion.mover_izquierda(0, -126, 1, 2, btnAbrirSuc);
        }
    }//GEN-LAST:event_btnAbrirSucMouseExited

    private void btnAbrirTPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirTPMouseEntered
        if (!precios) {
            Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnAbrirTP);
        }
    }//GEN-LAST:event_btnAbrirTPMouseEntered

    private void btnAbrirTPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirTPMouseExited
        if (!precios) {
            Animacion.Animacion.mover_izquierda(0, -126, 1, 2, btnAbrirTP);
        }
    }//GEN-LAST:event_btnAbrirTPMouseExited

    private void btnAbrirParaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirParaMouseEntered
        if (!configuracion) {
            Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnAbrirPara);
        }
    }//GEN-LAST:event_btnAbrirParaMouseEntered

    private void btnAbrirParaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirParaMouseExited
        if (!configuracion) {
            Animacion.Animacion.mover_izquierda(0, -126, 1, 2, btnAbrirPara);
        }
    }//GEN-LAST:event_btnAbrirParaMouseExited

    private void txtDireccionProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionProveedorKeyTyped
        int longitud = txtDireccionProveedor.getText().length();
        validacion.Direccion(evt, longitud);
    }//GEN-LAST:event_txtDireccionProveedorKeyTyped

    private void txtNuevoDireccionProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoDireccionProveedorKeyTyped
        int longitud = txtNuevoDireccionProveedor.getText().length();
        validacion.Direccion(evt, longitud);
    }//GEN-LAST:event_txtNuevoDireccionProveedorKeyTyped

    private void txtCodBarraProductosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarraProductosKeyTyped
        int longitud = txtCodBarraProductos.getText().length();
        validacion.CodigoBarra(evt, longitud);
    }//GEN-LAST:event_txtCodBarraProductosKeyTyped

    private void txtNombreProductosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductosKeyTyped
        int longitud = txtNombreProductos.getText().length();
        validacion.NombreProducto(evt, longitud);
    }//GEN-LAST:event_txtNombreProductosKeyTyped

    private void txtPrecioProductosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProductosKeyTyped
        validacion.CostoProducto(evt, txtPrecioProductos.getText());
    }//GEN-LAST:event_txtPrecioProductosKeyTyped

    private void txtProductoInventarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductoInventarioKeyTyped
        validacion.CostoProducto(evt, txtProductoInventario.getText());
    }//GEN-LAST:event_txtProductoInventarioKeyTyped

    private void txtNuevoInventarioProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoInventarioProductoKeyTyped
        validacion.CostoProducto(evt, txtNuevoInventarioProducto.getText());
    }//GEN-LAST:event_txtNuevoInventarioProductoKeyTyped

    private void txtNombreSucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreSucKeyTyped
        int longitud = txtNombreSuc.getText().length();
        validacion.Nombre(evt, longitud);
    }//GEN-LAST:event_txtNombreSucKeyTyped

    private void txtDireccionSucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionSucKeyTyped
        int longitud = txtDireccionSuc.getText().length();
        validacion.Direccion(evt, longitud);
    }//GEN-LAST:event_txtDireccionSucKeyTyped

    private void txtModNombreSucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtModNombreSucKeyTyped

    }//GEN-LAST:event_txtModNombreSucKeyTyped

    private void txtModDirSucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtModDirSucKeyTyped

    }//GEN-LAST:event_txtModDirSucKeyTyped

    private void txtTelSucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelSucKeyTyped
        int longitud = txtTelSuc.getText().length();
        validacion.Tel(evt, longitud);
    }//GEN-LAST:event_txtTelSucKeyTyped

    private void txtNombrePrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombrePrecioKeyTyped
        int longitud = txtNombrePrecio.getText().length();
        validacion.Nombre(evt, longitud);
    }//GEN-LAST:event_txtNombrePrecioKeyTyped

    private void txtUtilidadPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUtilidadPrecioKeyTyped
        validacion.CostoProducto(evt, txtUtilidadPrecio.getText());
    }//GEN-LAST:event_txtUtilidadPrecioKeyTyped

    private void txtNomParKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomParKeyTyped
        int longitud = txtNomPar.getText().length();
        validacion.Nombre(evt, longitud);
    }//GEN-LAST:event_txtNomParKeyTyped

    private void txtUtParKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUtParKeyTyped
        validacion.CostoProducto(evt, txtUtPar.getText());
    }//GEN-LAST:event_txtUtParKeyTyped

    private void txtValorParKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorParKeyTyped
        int longitud = txtValorPar.getText().length();
        validacion.LongitudLetras(evt, longitud, 29);
    }//GEN-LAST:event_txtValorParKeyTyped

    private void txtCodBarraProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarraProdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscarProdCompra.doClick();
        }
    }//GEN-LAST:event_txtCodBarraProdKeyPressed

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtCostoProd.requestFocus();
        }
    }//GEN-LAST:event_txtCantidadKeyPressed

    private void txtCostoProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoProdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnAgregarProd1.doClick();
        }
    }//GEN-LAST:event_txtCostoProdKeyPressed

    private void txtNumDocCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumDocCompraKeyTyped

        validacion.Numeros(evt);
    }//GEN-LAST:event_txtNumDocCompraKeyTyped

    private void txtCostoProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoProdKeyTyped
        validacion.CostoProducto(evt, txtCostoProd.getText());
    }//GEN-LAST:event_txtCostoProdKeyTyped

    private void txtCodBarraProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarraProdKeyTyped
        int longitud = txtCodBarraProd.getText().length();
        validacion.CodigoBarra(evt, longitud);
    }//GEN-LAST:event_txtCodBarraProdKeyTyped

    private void txtNomProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomProdKeyTyped
        int longitud = txtNomProd.getText().length();
        validacion.NombreProducto(evt, longitud);
    }//GEN-LAST:event_txtNomProdKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        validacion.CostoProducto(evt, txtCantidad.getText());
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtClienteVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteVentaKeyTyped
        int longitud = txtClienteVenta.getText().length();
        validacion.Nombre(evt, longitud);
    }//GEN-LAST:event_txtClienteVentaKeyTyped

    private void txtNITVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNITVentaKeyTyped
        int longitud = txtNITVenta.getText().length();
        validacion.Nit(evt, longitud);
    }//GEN-LAST:event_txtNITVentaKeyTyped

    private void txtNRCVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNRCVentaKeyTyped
        int longitud = txtNRCVenta.getText().length();
        validacion.NRC(evt, longitud);
    }//GEN-LAST:event_txtNRCVentaKeyTyped

    private void txtDireccionVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionVentaKeyTyped
        int longitud = txtDireccionVenta.getText().length();
        validacion.Direccion(evt, longitud);
    }//GEN-LAST:event_txtDireccionVentaKeyTyped

    private void txtGiroVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiroVentaKeyTyped
        int longitud = txtGiroVenta.getText().length();
        validacion.Direccion(evt, longitud);
    }//GEN-LAST:event_txtGiroVentaKeyTyped

    private void txtCodigoBarraVenderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBarraVenderKeyTyped
        int longitud = txtCodigoBarraVender.getText().length();
        validacion.CodigoBarra(evt, longitud);
    }//GEN-LAST:event_txtCodigoBarraVenderKeyTyped

    private void txtNombreProductoVenderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoVenderKeyTyped
        int longitud = txtNombreProductoVender.getText().length();
        validacion.NombreProducto(evt, longitud);
    }//GEN-LAST:event_txtNombreProductoVenderKeyTyped

    private void txtCantidadVenderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVenderKeyTyped

        validacion.CostoProducto(evt, txtCantidadVender.getText());
    }//GEN-LAST:event_txtCantidadVenderKeyTyped

    private void btnAgregarProductoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoVentaActionPerformed
        // TODO add your handling code here:
        totalC = 0;
        conection cn = new conection();
        String iva = null;
        try {
            cn.Conectar();
            iva = cn.ValorParametro("iva");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        if (txtNombreProductoVender.getText().isEmpty() || txtCantidadVender.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Valores incompletos o producto no válido");
        } else {
            if (Double.parseDouble(txtCantidadVender.getText()) == 0) {
                JOptionPane.showMessageDialog(null, "No es posible guardar cantidad 0");
            } else {
                //Ahora si... a agregar el producto al model

                //CREANDO PRODUCTO A GUARDAR
                ControladorProducto cp = new ControladorProducto();
                Producto producto = new Producto();
                DecimalFormat dosdigitos = new DecimalFormat("0.00");
                DecimalFormat cuatrodigitos = new DecimalFormat("0.0000");

                //ID SUCURSAL SELECCIONADA
                int idSucursalSeleccionada = 0;
                String suc = cmbSucursalVenta.getSelectedItem().toString();
                try {
                    String[] cm = new String[]{"IdSucursal", "Nombre", "Direccion", "Telefono"};
                    iList p = new iList(new ListasTablas("Nombre", suc));

                    try {
                        cn.Conectar();
                        PreparedStatement ps = cn.BuscarRegistro("Sucursal", cm, p);
                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {
                            idSucursalSeleccionada = Integer.parseInt(rs.getString("IdSucursal"));

                        }
                        cn.Desconectar();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error interno buscando la sucursal " + e.getMessage());
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error externo buscando la sucursal " + e.getMessage());
                }

                try {
                    cn.Conectar();
                    producto = cp.Obtener(txtCodigoBarraVender.getText());
                    //Parametro par = new Parametro();
                    //String para = par.ObtenerUtilidad().valor;
                    Double utilidad = cn.utilidadPrecio(cmbTipoPrecioVenta.getSelectedItem().toString());
                    //System.out.println(para);
                    System.out.println("utili " + utilidad);
                    //producto.costo = (producto.costo * (Double.parseDouble(dosdigitos.format(Double.parseDouble(para))) / 100) + producto.costo);
                    producto.costo = Double.parseDouble(cuatrodigitos.format((producto.costo / (1 - utilidad))));
                    double Total = 0;

                    DefaultTableModel model = (DefaultTableModel) tblProductosVender.getModel();
                    //Evaluar inventario dependiendo de la cantidad...
                    System.out.println("Inventario de producto= " + producto.inventario);
                    if (cn.inventarioEnSucursalbyProducto(txtCodigoBarraVender.getText(), idSucursalSeleccionada) >= Double.parseDouble(txtCantidadVender.getText())) {

                        boolean found = false;
                        for (int row = 0; row < model.getRowCount(); row++) {
                            if (txtCodigoBarraVender.getText().equals(model.getValueAt(row, 0))) {
                                found = true;
                                //Actualizar
                                double suma = Double.parseDouble(txtCantidadVender.getText()) + Double.parseDouble(model.getValueAt(row, 2).toString());
                                System.out.println("Suma: " + suma);
                                if (cn.inventarioEnSucursalbyProducto(txtCodigoBarraVender.getText(), idSucursalSeleccionada) >= (Double.parseDouble(txtCantidadVender.getText()) + Double.parseDouble(model.getValueAt(row, 2).toString()))) {
                                    model.setValueAt(Integer.parseInt(model.getValueAt(row, 2).toString()) + Double.parseDouble(txtCantidadVender.getText()), row, 2);
                                    model.setValueAt(cuatrodigitos.format(Double.parseDouble(model.getValueAt(row, 2).toString()) * producto.costo), row, 4);
                                    if (cmbTipoVenta.getSelectedIndex() == 2) {
                                        model.setValueAt(cuatrodigitos.format(((Double.parseDouble(model.getValueAt(row, 2).toString()) * Double.parseDouble(model.getValueAt(row, 3).toString()) * Double.parseDouble(iva)))), row, 4);
                                    } else {
                                        model.setValueAt(0, row, 4);
                                    }
                                    model.setValueAt(cuatrodigitos.format(((Double.parseDouble(model.getValueAt(row, 2).toString()) * Double.parseDouble(model.getValueAt(row, 3).toString())) + Double.parseDouble(model.getValueAt(row, 4).toString()))), row, 5);

                                } else {
                                    JOptionPane.showMessageDialog(null, "El inventario de este producto no puede satisfacer la cantidad necesaria");
                                }
                            }
                        }

                        if (!found) {
                            model.addRow(new Object[filas]);
                            for (int row = 0; row < model.getColumnCount(); row++) {
                                //Agregar
                                model.setValueAt(producto.CodBarra, filas, 0);
                                model.setValueAt(producto.nombre, filas, 1);
                                model.setValueAt(txtCantidadVender.getText(), filas, 2);
                                model.setValueAt(producto.costo, filas, 3);
                                if (cmbTipoVenta.getSelectedIndex() == 2) {
                                    model.setValueAt(cuatrodigitos.format(((Double.parseDouble(model.getValueAt(filas, 2).toString()) * Double.parseDouble(model.getValueAt(filas, 3).toString()) * Double.parseDouble(iva)))), filas, 4);
                                } else {
                                    model.setValueAt(0, filas, 4);
                                }
                                model.setValueAt(cuatrodigitos.format(((Double.parseDouble(model.getValueAt(filas, 2).toString()) * Double.parseDouble(model.getValueAt(filas, 3).toString())) + Double.parseDouble(model.getValueAt(filas, 4).toString()))), filas, 5);
                                //model.setValueAt(dosdigitos.format(((Integer.parseInt(txtCantidadVender.getText()) * producto.costo))), filas, 4);
                            }
                            filas++;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El inventario de este producto no puede satisfacer la cantidad necesaria");
                    }

                    //TOTAL 
                    switch (cmbTipoVenta.getSelectedItem().toString()) {
                        case "Libre":
                            for (int row = 0; row < model.getRowCount(); row++) {
                                totalC = Double.parseDouble(dosdigitos.format(totalC + Double.parseDouble(model.getValueAt(row, 5).toString())));
                            }
                            txtTotalVenta.setText("" + totalC);
                            txtSumaVenta.setText("" + totalC);
                            txtIvaVenta.setText("0");
                            break;
                        case "Credito Fiscal":
                            for (int row = 0; row < model.getRowCount(); row++) {
                                totalC = Double.parseDouble(dosdigitos.format(totalC + Double.parseDouble(model.getValueAt(row, 5).toString())));
                            }
                            txtSumaVenta.setText("" + dosdigitos.format(totalC));
                            System.out.println("IVAAAA: " + iva);
                            double ivaFinal = totalC * Double.parseDouble(iva);
                            System.out.println("IVAA FINAAAL " + ivaFinal);
                            txtIvaVenta.setText("" + dosdigitos.format(ivaFinal));
                            double granTotal = totalC + ivaFinal;
                            txtTotalVenta.setText("" + dosdigitos.format(granTotal));
                            break;
                        case "Factura":
                            for (int row = 0; row < model.getRowCount(); row++) {
                                totalC = Double.parseDouble(dosdigitos.format(totalC + Double.parseDouble(model.getValueAt(row, 5).toString())));
                            }
                            txtSumaVenta.setText("" + dosdigitos.format(totalC));
                            txtTotalVenta.setText("" + dosdigitos.format(totalC));
                            txtIvaVenta.setText("0");
                            ;
                            break;
                    }

                    txtCodigoBarraVender.setText("");
                    txtNombreProductoVender.setText("");
                    txtCantidadVender.setText("1");
                    cmbTipoPrecioVenta.setEnabled(false);
                    cmbTipoVenta.setEnabled(false);
                    cmbSucursalVenta.setEnabled(false);
                    txtCodigoBarraVender.requestFocus();

                    if (model.getRowCount() == 0) {
                        cmbTipoPrecioVenta.setEnabled(true);
                        cmbTipoVenta.setEnabled(true);
                        cmbSucursalVenta.setEnabled(true);
                    }

                } catch (ErrorTienda ex) {
                    JOptionPane.showMessageDialog(null, "No se encontró el producto: " + txtCodigoBarraVender.getText() + " " + ex.getMessage());
                } catch (Exception ex) {
                    Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }//GEN-LAST:event_btnAgregarProductoVentaActionPerformed

    private void txtCodigoBarraVenderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBarraVenderKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BuscarProducto();
            txtCantidadVender.requestFocus();
            txtCantidadVender.setText("1");
            txtCantidadVender.selectAll();
        }

    }//GEN-LAST:event_txtCodigoBarraVenderKeyPressed

    private void btnEliminarProductoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoVentaActionPerformed
        try {
            DefaultTableModel model = (DefaultTableModel) tblProductosVender.getModel();
            model.removeRow(tblProductosVender.getSelectedRow());
            DecimalFormat dosdigitos = new DecimalFormat("0.00");
            Double Total = 0.0;
            for (int row = 0; row < model.getRowCount(); row++) {
                Total = Total + Double.parseDouble(dosdigitos.format(Double.parseDouble(model.getValueAt(row, 4).toString())));
            }
            txtTotalVenta.setText("" + Total);
            filas--;
            if (model.getRowCount() == 0) {
                cmbTipoPrecioVenta.setEnabled(true);
                cmbTipoVenta.setEnabled(true);
                cmbSucursalVenta.setEnabled(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto " + e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarProductoVentaActionPerformed

    private void cmbTipoVentaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTipoVentaItemStateChanged
        String tipoVenta = cmbTipoVenta.getSelectedItem().toString();
        switch (tipoVenta) {
            case "Libre":
                tblProductosVender.getColumnModel().getColumn(4).setMinWidth(0);
                tblProductosVender.getColumnModel().getColumn(4).setMaxWidth(0);
                tblProductosVender.getColumnModel().getColumn(4).setWidth(0);
                txtSumaVenta.setVisible(false);
                txtSumaVenta.setText(txtTotal.getText());
                lblSumaVenta.setVisible(false);
                txtIvaVenta.setVisible(false);
                txtIvaVenta.setText("0");
                lblIvaVenta.setVisible(false);
                txtGiroVenta.setVisible(false);
                txtNRCVenta.setVisible(false);
                txtNITVenta.setVisible(false);
                lblNITVenta.setVisible(false);
                lblgiro.setVisible(false);
                lblnrc.setVisible(false);
                lblIvaVenta.setVisible(false);
                break;
            case "Factura":
                tblProductosVender.getColumnModel().getColumn(4).setMinWidth(0);
                tblProductosVender.getColumnModel().getColumn(4).setMaxWidth(0);
                tblProductosVender.getColumnModel().getColumn(4).setWidth(0);
//                tblProductosVender.getColumnModel().getColumn(4).setMinWidth(tblProductosVender.getColumnModel().getColumn(3).getWidth() - 15);
//                tblProductosVender.getColumnModel().getColumn(4).setMaxWidth(tblProductosVender.getColumnModel().getColumn(3).getWidth() - 15);
//                tblProductosVender.getColumnModel().getColumn(4).setWidth(tblProductosVender.getColumnModel().getColumn(3).getWidth() - 15);
                txtSumaVenta.setVisible(true);
                lblSumaVenta.setVisible(true);
                txtIvaVenta.setVisible(false);
                txtIvaVenta.setText("0");
                txtGiroVenta.setVisible(false);
                this.txtNRCVenta.setVisible(false);
                lblgiro.setVisible(false);
                lblnrc.setVisible(false);
                lblIvaVenta.setVisible(false);
                txtNITVenta.setVisible(false);
                lblNITVenta.setVisible(false);
                break;
            case "Credito Fiscal":
                tblProductosVender.getColumnModel().getColumn(4).setMinWidth(0);
                tblProductosVender.getColumnModel().getColumn(4).setMaxWidth(0);
                tblProductosVender.getColumnModel().getColumn(4).setWidth(0);
                tblProductosVender.getColumnModel().getColumn(4).sizeWidthToFit();
                txtSumaVenta.setVisible(true);
                lblSumaVenta.setVisible(true);
                txtIvaVenta.setVisible(true);
                lblIvaVenta.setVisible(true);
                txtGiroVenta.setVisible(true);
                this.txtNRCVenta.setVisible(true);
                lblgiro.setVisible(true);
                lblnrc.setVisible(true);
                txtNITVenta.setVisible(true);
                lblNITVenta.setVisible(true);
                break;
            default:
                tblProductosVender.getColumnModel().getColumn(4).setMinWidth(0);
                tblProductosVender.getColumnModel().getColumn(4).setMaxWidth(0);
                tblProductosVender.getColumnModel().getColumn(4).setWidth(0);
                txtSumaVenta.setVisible(false);
                lblSumaVenta.setVisible(false);
                txtIvaVenta.setVisible(false);
                lblIvaVenta.setVisible(false);
                break;
        }
    }//GEN-LAST:event_cmbTipoVentaItemStateChanged

    private void btnVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVenderActionPerformed
        // A VENDER!!!
        //if (!txtClienteVenta.getText().isEmpty()) {
        conection cn = new conection();
        ControladorVenta venta = new ControladorVenta();
        Venta nventa = new Venta();

        try {
            //Relajo de Fecha
            //java.util.Date date = new Date();
            //Object param = new java.sql.Timestamp(date.getTime());
            //txtFecha.setText(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(param));
            //DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            //Date date2 = new Date();
            //date2 = formatter.parse(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(param));

            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String dateFormatted = sdf.format(txt_fecha_venta.getDate());                            //.getDate()
            Date date2 = formatter.parse(dateFormatted);

            //ID SUCURSAL SELECCIONADA
            int idSucursalSeleccionada = 0;
            String suc = cmbSucursalVenta.getSelectedItem().toString();
            try {
                String[] cm = new String[]{"IdSucursal", "Nombre", "Direccion", "Telefono"};
                iList p = new iList(new ListasTablas("Nombre", suc));

                try {
                    cn.Conectar();
                    PreparedStatement ps = cn.BuscarRegistro("Sucursal", cm, p);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        idSucursalSeleccionada = Integer.parseInt(rs.getString("IdSucursal"));

                    }
                    cn.Desconectar();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error interno buscando la sucursal " + e.getMessage());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error externo buscando la sucursal " + e.getMessage());
            }

            //ID TIPOPRECIO SELECCIONADA
            int idTipoPrecio = 0;
            String tp = cmbTipoPrecioVenta.getSelectedItem().toString();
            try {
                String[] cm = new String[]{"IdTipoPrecio", "Nombre", "Utilidad"};
                iList p = new iList(new ListasTablas("Nombre", tp));

                try {
                    cn.Conectar();
                    PreparedStatement ps = cn.BuscarRegistro("tipoprecio", cm, p);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        idTipoPrecio = Integer.parseInt(rs.getString("IdTipoPrecio"));

                    }
                    cn.Desconectar();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error interno buscando el tipoprecio " + e.getMessage());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error externo buscando el tipoprecio " + e.getMessage());
            }
            //Objeto Venta
            nventa.idVenta = Integer.parseInt(txtIdVenta.getText());
            nventa.idSucursal = idSucursalSeleccionada;
            switch (cmbTipoVenta.getSelectedItem().toString()) {
                case "Libre":
                    nventa.tipoVenta = "L";
                    break;
                case "Credito Fiscal":
                    nventa.tipoVenta = "C";
                    break;
                case "Factura":
                    nventa.tipoVenta = "F";
                    break;
                default:
                    break;
            }
            System.out.println("TIPO VENTA: " + nventa.tipoVenta);
            nventa.idTipoPrecio = idTipoPrecio;
            nventa.cliente = txtClienteVenta.getText();
            nventa.fecha = date2;
            nventa.IVA = Double.parseDouble(txtIvaVenta.getText());
            nventa.totalGrabado = Double.parseDouble(txtSumaVenta.getText());
            nventa.total = Double.parseDouble(txtTotalVenta.getText());
            nventa.direccion = txtDireccionVenta.getText();
            nventa.giro = txtGiroVenta.getText();
            nventa.NIT = txtNITVenta.getText();
            nventa.NRC = txtNRCVenta.getText();
            nventa.numDocumento = txtNoDocVenta.getText();
            nventa.articulo = new ArrayList<DetalleVenta>();
//                nventa.articulo =    ------------FALTA ARTICULOS?? R/ NO, se llena en DetalleVenta
//                venta agregada:
            venta.Agregar(nventa);

            //CONTINUAR CON EL DETALLE_VENTA
            //Recorrer la tabla - tblProductosVender
            DefaultTableModel model = (DefaultTableModel) tblProductosVender.getModel();
            ArrayList<DetalleVenta> articulos = new ArrayList<DetalleVenta>();
            for (int row = 0; row < model.getRowCount(); row++) {
                //Agregar
                Producto producto = new Producto();
                producto.CodBarra = model.getValueAt(row, 0).toString();

                System.out.println("DATOS> Cantidad>" + Double.parseDouble(model.getValueAt(row, 2).toString()) + " PrecioU> " + Double.parseDouble(model.getValueAt(row, 3).toString()));

                DetalleVenta dv = new DetalleVenta(producto, Double.parseDouble(model.getValueAt(row, 2).toString()), Double.parseDouble(model.getValueAt(row, 3).toString()));
                System.out.println("DEtalleVenta " + dv.cantidad + " " + dv.PrecioUnitario + " " + dv.producto.CodBarra);
                System.out.println("ART: " + nventa.articulo);
                nventa.articulo.add(dv);
            }
            System.out.println("LLEgue");
            agregarDetalleVenta(nventa);

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error Parse guardando venta " + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error SQL guardando venta " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error guardando venta " + ex.getMessage() + " " + ex.getCause());
        }

//        } else {
//            JOptionPane.showMessageDialog(null, "Escriba el nombre de un cliente");
//        }
    }//GEN-LAST:event_btnVenderActionPerformed

    private void txtNoDocVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoDocVentaKeyTyped

        validacion.Numeros(evt);
    }//GEN-LAST:event_txtNoDocVentaKeyTyped

    private void txtNoDocVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoDocVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtClienteVenta.requestFocus();
        }
    }//GEN-LAST:event_txtNoDocVentaKeyPressed

    private void txtClienteVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtNITVenta.requestFocus();
        }
    }//GEN-LAST:event_txtClienteVentaKeyPressed

    private void txtNITVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNITVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtNRCVenta.requestFocus();
        }
    }//GEN-LAST:event_txtNITVentaKeyPressed

    private void txtNRCVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNRCVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtDireccionVenta.requestFocus();
        }
    }//GEN-LAST:event_txtNRCVentaKeyPressed

    private void txtDireccionVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtGiroVenta.requestFocus();
        }
    }//GEN-LAST:event_txtDireccionVentaKeyPressed

    private void txtNombreProductoVenderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoVenderKeyPressed

    }//GEN-LAST:event_txtNombreProductoVenderKeyPressed

    private void txtCantidadVenderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVenderKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnAgregarProductoVenta.doClick();
        }
    }//GEN-LAST:event_txtCantidadVenderKeyPressed

    private void txt_fecha_compraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fecha_compraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fecha_compraActionPerformed

    private void txtPercepcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPercepcionKeyTyped
        validacion.CostoProducto(evt, txtPercepcion.getText());
        if (txtPercepcion.getText().equals("")) {
            txtPercepcion.setText("0");
        }

        DecimalFormat dosdigitos = new DecimalFormat("0.00");

        String per = txtPercepcion.getText();
        double granTotal = Double.parseDouble(txtSuma.getText()) + Double.parseDouble(txtIva.getText()) + Double.parseDouble(txtPercepcion.getText());
        txtTotal.setText("" + dosdigitos.format(granTotal));
    }//GEN-LAST:event_txtPercepcionKeyTyped

    private void cmbTipoPrecioVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoPrecioVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipoPrecioVentaActionPerformed

    private void txtBuscaTipoPrecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscaTipoPrecioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscarTP.doClick();
        }
    }//GEN-LAST:event_txtBuscaTipoPrecioKeyPressed

    private void txtBuscarParametroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarParametroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscarParametro.doClick();
        }
    }//GEN-LAST:event_txtBuscarParametroKeyPressed

    private void txtPercepcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPercepcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPercepcionActionPerformed

    private void txtNuevoNRCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoNRCKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoNRCKeyTyped

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        jpnListaVentas.setVisible(false);

        llena_comboAño();
        LlenarTablaMes(cmbMes.getSelectedItem().toString(), cmbAño.getSelectedItem().toString());
        jpnReporteMesVentas2.setVisible(true);
    }//GEN-LAST:event_btnReportActionPerformed

    private void txtNuevaSucursalProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevaSucursalProdKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevaSucursalProdKeyTyped

    private void txtProductosBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductosBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscarProducto.doClick();
        }
    }//GEN-LAST:event_txtProductosBuscarKeyPressed

    private void btnAtrasReporteVenta2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasReporteVenta2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasReporteVenta2MouseClicked

    private void btnAtrasReporteVenta2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasReporteVenta2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasReporteVenta2MouseEntered

    private void btnAtrasReporteVenta2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasReporteVenta2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasReporteVenta2MouseExited

    private void btnAtrasReporteVenta2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasReporteVenta2ActionPerformed
        jpnReporteMesVentas2.setVisible(false);
        jpnListaVentas.setVisible(true);
    }//GEN-LAST:event_btnAtrasReporteVenta2ActionPerformed

    private void cmbMesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMesItemStateChanged
        if (cmbAño.getItemCount() != 0 && cmbMes.getItemCount() != 0) {
            LlenarTablaMes(cmbMes.getSelectedItem().toString(), cmbAño.getSelectedItem().toString());
        }
    }//GEN-LAST:event_cmbMesItemStateChanged

    private void cmbAñoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbAñoItemStateChanged
        if (cmbAño.getItemCount() != 0) {
            llena_comboMes(cmbAño.getSelectedItem().toString());
            if (cmbAño.getItemCount() != 0 && cmbMes.getItemCount() != 0) {
                LlenarTablaMes(cmbMes.getSelectedItem().toString(), cmbAño.getSelectedItem().toString());
            }
        }
    }//GEN-LAST:event_cmbAñoItemStateChanged

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed

        String reporte = "C:\\Users\\Rogelio Alvarez\\Desktop\\VERSION FINAL TIENDA\\tiendads2\\src\\reportes\\reporteVentas.jasper";
        System.out.println(reporte);
        Map parametros = new HashMap();
        conection cn = new conection();

        String Mes = "";
        String mes = cmbMes.getSelectedItem().toString();

        if (mes.equalsIgnoreCase("Enero")) {
            Mes = "1";
        } else if (mes.equalsIgnoreCase("Febrero")) {
            Mes = "2";
        } else if (mes.equalsIgnoreCase("Marzo")) {
            Mes = "3";
        } else if (mes.equalsIgnoreCase("Abril")) {
            Mes = "4";
        } else if (mes.equalsIgnoreCase("Mayo")) {
            Mes = "5";
        } else if (mes.equalsIgnoreCase("Junio")) {
            Mes = "6";
        } else if (mes.equalsIgnoreCase("Julio")) {
            Mes = "7";
        } else if (mes.equalsIgnoreCase("Agosto")) {
            Mes = "8";
        } else if (mes.equalsIgnoreCase("Septiembre")) {
            Mes = "9";
        } else if (mes.equalsIgnoreCase("Octubre")) {
            Mes = "10";
        } else if (mes.equalsIgnoreCase("Noviembre")) {
            Mes = "11";
        } else if (mes.equalsIgnoreCase("Diciembre")) {
            Mes = "12";
        }
        parametros.put("mes", Mes);
        parametros.put("año", cmbAño.getSelectedItem().toString());

        JasperPrint informe;
        try {
            informe = JasperFillManager.fillReport(reporte, parametros, cn.getConexion());

            JasperViewer jv = new JasperViewer(informe, false);
            JasperViewer.viewReport(informe, false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN REPORTE: " + ex.getMessage());
        }

    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnGenerar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerar1ActionPerformed

        String reporte = "C:\\Users\\Rogelio Alvarez\\Desktop\\VERSION FINAL PRUEBA\\tiendads2\\src\\reportes\\reporteCompras2.jasper";
        System.out.println(reporte);
        Map parametros = new HashMap();
        conection cn = new conection();

        String Mes = "";
        String mes = cmbMes1.getSelectedItem().toString();

        if (mes.equalsIgnoreCase("Enero")) {
            Mes = "1";
        } else if (mes.equalsIgnoreCase("Febrero")) {
            Mes = "2";
        } else if (mes.equalsIgnoreCase("Marzo")) {
            Mes = "3";
        } else if (mes.equalsIgnoreCase("Abril")) {
            Mes = "4";
        } else if (mes.equalsIgnoreCase("Mayo")) {
            Mes = "5";
        } else if (mes.equalsIgnoreCase("Junio")) {
            Mes = "6";
        } else if (mes.equalsIgnoreCase("Julio")) {
            Mes = "7";
        } else if (mes.equalsIgnoreCase("Agosto")) {
            Mes = "8";
        } else if (mes.equalsIgnoreCase("Septiembre")) {
            Mes = "9";
        } else if (mes.equalsIgnoreCase("Octubre")) {
            Mes = "10";
        } else if (mes.equalsIgnoreCase("Noviembre")) {
            Mes = "11";
        } else if (mes.equalsIgnoreCase("Diciembre")) {
            Mes = "12";
        }
        parametros.put("mes", Mes);
        parametros.put("año", cmbAño1.getSelectedItem().toString());

        JasperPrint informe;
        try {
            informe = JasperFillManager.fillReport(reporte, parametros, cn.getConexion());

            JasperViewer jv = new JasperViewer(informe, false);
            JasperViewer.viewReport(informe, false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR EN REPORTE: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnGenerar1ActionPerformed

    private void btnAtrasReporteCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasReporteCompraMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasReporteCompraMouseClicked

    private void btnAtrasReporteCompraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasReporteCompraMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasReporteCompraMouseEntered

    private void btnAtrasReporteCompraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasReporteCompraMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasReporteCompraMouseExited

    private void btnAtrasReporteCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasReporteCompraActionPerformed
        jpnReporteMesCompras.setVisible(false);
        jpnCompras.setVisible(true);
    }//GEN-LAST:event_btnAtrasReporteCompraActionPerformed

    private void cmbMes1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMes1ItemStateChanged
        if (cmbAño1.getItemCount() != 0 && cmbMes1.getItemCount() != 0) {
            LlenarTablaMesCompra(cmbMes1.getSelectedItem().toString(), cmbAño1.getSelectedItem().toString());
        }
    }//GEN-LAST:event_cmbMes1ItemStateChanged

    private void cmbAño1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbAño1ItemStateChanged
        if (cmbAño1.getItemCount() != 0) {
            llena_comboMesCompra(cmbAño1.getSelectedItem().toString());
            if (cmbAño1.getItemCount() != 0 && cmbMes1.getItemCount() != 0) {
                LlenarTablaMesCompra(cmbMes1.getSelectedItem().toString(), cmbAño1.getSelectedItem().toString());
            }
        }
    }//GEN-LAST:event_cmbAño1ItemStateChanged

    private void reporteComprabtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reporteComprabtnActionPerformed
        jpnCompras.setVisible(false);

        llena_comboAñoCompra();
        LlenarTablaMesCompra(cmbMes1.getSelectedItem().toString(), cmbAño1.getSelectedItem().toString());
        jpnReporteMesCompras.setVisible(true);
    }//GEN-LAST:event_reporteComprabtnActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProductosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFRPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFRPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFRPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFRPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFRPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirPara;
    private javax.swing.JButton btnAbrirSuc;
    private javax.swing.JButton btnAbrirTP;
    private javax.swing.JButton btnAgregarCompra;
    private javax.swing.JButton btnAgregarNuevoProducto;
    private javax.swing.JButton btnAgregarProd1;
    private javax.swing.JButton btnAgregarProductoVenta;
    private javax.swing.JButton btnAgregarProveedor;
    private javax.swing.JButton btnAgregarSuc;
    private javax.swing.JButton btnAgregarSuc1;
    private javax.swing.JButton btnAgregarVenta;
    private javax.swing.JButton btnAtrasDetalleCompra;
    private javax.swing.JButton btnAtrasDetalleCompra1;
    private javax.swing.JButton btnAtrasModPar;
    private javax.swing.JButton btnAtrasModPar1;
    private javax.swing.JButton btnAtrasModificarProducto;
    private javax.swing.JButton btnAtrasModificarProducto1;
    private javax.swing.JButton btnAtrasModificarProveedor;
    private javax.swing.JButton btnAtrasProveedores;
    private javax.swing.JButton btnAtrasReporteCompra;
    private javax.swing.JButton btnAtrasReporteVenta2;
    private javax.swing.JButton btnAtrasSuc;
    private javax.swing.JButton btnAtrasSuc1;
    private javax.swing.JButton btnBuscarParametro;
    private javax.swing.JButton btnBuscarProdCompra;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnBuscarTP;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCompras;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnEliminarProductoVenta;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarprod;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnGenerar1;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarModificarProducto;
    private javax.swing.JButton btnGuardarModificarProveedor;
    private javax.swing.JButton btnGuardarPar;
    private javax.swing.JButton btnGuardarPar1;
    private javax.swing.JButton btnGuardarProveedor;
    private javax.swing.JButton btnGuardarSuc;
    private javax.swing.JLabel btnHome;
    private javax.swing.JButton btnModSucursal;
    private javax.swing.JButton btnModificarParametro;
    private javax.swing.JButton btnModificarProducto;
    private javax.swing.JButton btnModificarProveedor;
    private javax.swing.JButton btnModificarTP;
    private javax.swing.JButton btnNuevoProducto;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedores;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnSalirProductos;
    private javax.swing.JButton btnSucursalBuscar;
    private javax.swing.JButton btnSucursalEliminar;
    private javax.swing.JButton btnSucursalNueva;
    private javax.swing.JButton btnTPEliminar;
    private javax.swing.JButton btnTPNuevo;
    private javax.swing.JButton btnVender;
    private javax.swing.JButton btnVentas;
    private javax.swing.JButton btnVerDetalle;
    private javax.swing.JButton btnVerDetalleVenta;
    private javax.swing.ButtonGroup btngFiltroProductos;
    private javax.swing.JComboBox<String> cmbAño;
    private javax.swing.JComboBox<String> cmbAño1;
    private javax.swing.JComboBox<String> cmbMes;
    private javax.swing.JComboBox<String> cmbMes1;
    private javax.swing.JComboBox cmbProveedor;
    private javax.swing.JComboBox cmbSucursal2;
    private javax.swing.JComboBox cmbSucursalCompra;
    private javax.swing.JComboBox cmbSucursalVenta;
    private javax.swing.JComboBox cmbSucursales1;
    private javax.swing.JComboBox cmbTipoCompra;
    private javax.swing.JComboBox cmbTipoPrecioVenta;
    private javax.swing.JComboBox cmbTipoVenta;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator100;
    private javax.swing.JSeparator jSeparator101;
    private javax.swing.JSeparator jSeparator102;
    private javax.swing.JSeparator jSeparator103;
    private javax.swing.JSeparator jSeparator104;
    private javax.swing.JSeparator jSeparator107;
    private javax.swing.JSeparator jSeparator108;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator32;
    private javax.swing.JSeparator jSeparator34;
    private javax.swing.JSeparator jSeparator35;
    private javax.swing.JSeparator jSeparator37;
    private javax.swing.JSeparator jSeparator38;
    private javax.swing.JSeparator jSeparator39;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator40;
    private javax.swing.JSeparator jSeparator41;
    private javax.swing.JSeparator jSeparator42;
    private javax.swing.JSeparator jSeparator43;
    private javax.swing.JSeparator jSeparator44;
    private javax.swing.JSeparator jSeparator45;
    private javax.swing.JSeparator jSeparator46;
    private javax.swing.JSeparator jSeparator47;
    private javax.swing.JSeparator jSeparator48;
    private javax.swing.JSeparator jSeparator49;
    private javax.swing.JSeparator jSeparator50;
    private javax.swing.JSeparator jSeparator51;
    private javax.swing.JSeparator jSeparator52;
    private javax.swing.JSeparator jSeparator53;
    private javax.swing.JSeparator jSeparator56;
    private javax.swing.JSeparator jSeparator57;
    private javax.swing.JSeparator jSeparator60;
    private javax.swing.JSeparator jSeparator61;
    private javax.swing.JSeparator jSeparator62;
    private javax.swing.JSeparator jSeparator63;
    private javax.swing.JSeparator jSeparator64;
    private javax.swing.JSeparator jSeparator65;
    private javax.swing.JSeparator jSeparator66;
    private javax.swing.JSeparator jSeparator67;
    private javax.swing.JSeparator jSeparator68;
    private javax.swing.JSeparator jSeparator69;
    private javax.swing.JSeparator jSeparator70;
    private javax.swing.JSeparator jSeparator71;
    private javax.swing.JSeparator jSeparator72;
    private javax.swing.JSeparator jSeparator73;
    private javax.swing.JSeparator jSeparator74;
    private javax.swing.JSeparator jSeparator75;
    private javax.swing.JSeparator jSeparator76;
    private javax.swing.JSeparator jSeparator77;
    private javax.swing.JSeparator jSeparator78;
    private javax.swing.JSeparator jSeparator79;
    private javax.swing.JSeparator jSeparator82;
    private javax.swing.JSeparator jSeparator83;
    private javax.swing.JSeparator jSeparator84;
    private javax.swing.JSeparator jSeparator85;
    private javax.swing.JSeparator jSeparator86;
    private javax.swing.JSeparator jSeparator87;
    private javax.swing.JSeparator jSeparator88;
    private javax.swing.JSeparator jSeparator89;
    private javax.swing.JSeparator jSeparator90;
    private javax.swing.JSeparator jSeparator91;
    private javax.swing.JSeparator jSeparator92;
    private javax.swing.JSeparator jSeparator93;
    private javax.swing.JSeparator jSeparator94;
    private javax.swing.JSeparator jSeparator95;
    private javax.swing.JSeparator jSeparator96;
    private javax.swing.JSeparator jSeparator97;
    private javax.swing.JSeparator jSeparator98;
    private javax.swing.JSeparator jSeparator99;
    private javax.swing.JPanel jpnAgregarProv;
    private javax.swing.JPanel jpnAgregarVenta;
    private javax.swing.JPanel jpnBarraMenu;
    private javax.swing.JPanel jpnBarraSuperior;
    private javax.swing.JPanel jpnCompras;
    private javax.swing.JPanel jpnConfiguracion;
    private javax.swing.JPanel jpnCuarto;
    private javax.swing.JPanel jpnDetalleCompra;
    private javax.swing.JPanel jpnDetalleVenta;
    private javax.swing.JPanel jpnListaVentas;
    private javax.swing.JPanel jpnModificarParametro;
    private javax.swing.JPanel jpnModificarPrecio;
    private javax.swing.JPanel jpnModificarProducto;
    private javax.swing.JPanel jpnModificarProveedor;
    private javax.swing.JPanel jpnModificarSucursal;
    private javax.swing.JPanel jpnNuevaSucursal;
    private javax.swing.JPanel jpnNuevoPrecio;
    private javax.swing.JPanel jpnNuevoProducto;
    private javax.swing.JPanel jpnPrimero;
    private javax.swing.JPanel jpnPrincipal;
    private javax.swing.JPanel jpnProductos;
    private javax.swing.JPanel jpnProveedores;
    private javax.swing.JPanel jpnQuinto;
    private javax.swing.JPanel jpnRegistroCompra;
    private javax.swing.JPanel jpnReporteMesCompras;
    private javax.swing.JPanel jpnReporteMesVentas2;
    private javax.swing.JPanel jpnSegundo;
    private javax.swing.JPanel jpnSubMenu;
    private javax.swing.JPanel jpnSucursal;
    private javax.swing.JPanel jpnTercero;
    private javax.swing.JPanel jpnTipoPrecio;
    private javax.swing.JTable jtblProductos;
    private javax.swing.JLabel lbl10;
    private javax.swing.JLabel lbl11;
    private javax.swing.JLabel lbl12;
    private javax.swing.JLabel lbl13;
    private javax.swing.JLabel lbl14;
    private javax.swing.JLabel lbl15;
    private javax.swing.JLabel lbl16;
    private javax.swing.JLabel lbl17;
    private javax.swing.JLabel lbl21;
    private javax.swing.JLabel lbl22;
    private javax.swing.JLabel lbl23;
    private javax.swing.JLabel lbl24;
    private javax.swing.JLabel lbl25;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl31;
    private javax.swing.JLabel lbl32;
    private javax.swing.JLabel lbl33;
    private javax.swing.JLabel lbl34;
    private javax.swing.JLabel lbl35;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lbl41;
    private javax.swing.JLabel lbl42;
    private javax.swing.JLabel lbl43;
    private javax.swing.JLabel lbl44;
    private javax.swing.JLabel lbl45;
    private javax.swing.JLabel lbl5;
    private javax.swing.JLabel lbl6;
    private javax.swing.JLabel lbl7;
    private javax.swing.JLabel lbl8;
    private javax.swing.JLabel lbl9;
    private javax.swing.JLabel lblBotonCerrar;
    private javax.swing.JLabel lblCantidad2;
    private javax.swing.JLabel lblCodBarraProd10;
    private javax.swing.JLabel lblCodBarraProd18;
    private javax.swing.JLabel lblCodBarraProd21;
    private javax.swing.JLabel lblCodBarraProd22;
    private javax.swing.JLabel lblCodBarraProd23;
    private javax.swing.JLabel lblCodBarraProd24;
    private javax.swing.JLabel lblCodBarraProd3;
    private javax.swing.JLabel lblCodBarraProd6;
    private javax.swing.JLabel lblCodBarraProd9;
    private javax.swing.JLabel lblCostoProd2;
    private javax.swing.JLabel lblFecha2;
    private javax.swing.JLabel lblFecha3;
    private javax.swing.JLabel lblIdCompra2;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblIvaVenta;
    private javax.swing.JLabel lblListadoCompras;
    private javax.swing.JLabel lblListadoVentas;
    private javax.swing.JLabel lblListadoVentas3;
    private javax.swing.JLabel lblListadoVentas4;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblMitad;
    private javax.swing.JLabel lblMitad2;
    private javax.swing.JLabel lblMitad3;
    private javax.swing.JLabel lblMitad4;
    private javax.swing.JLabel lblMitad5;
    private javax.swing.JLabel lblNITVenta;
    private javax.swing.JLabel lblNomProd2;
    private javax.swing.JLabel lblPercepcion;
    private javax.swing.JLabel lblProveedor2;
    private javax.swing.JLabel lblProveedores3;
    private javax.swing.JLabel lblProveedores4;
    private javax.swing.JLabel lblProveedores6;
    private javax.swing.JLabel lblProveedores7;
    private javax.swing.JLabel lblProveedores8;
    private javax.swing.JLabel lblSucursalProd;
    private javax.swing.JLabel lblSuma;
    private javax.swing.JLabel lblSumaVenta;
    private javax.swing.JLabel lblTotal7;
    private javax.swing.JLabel lblTotalVenta;
    private javax.swing.JLabel lblVentas;
    private javax.swing.JLabel lblVentas3;
    private javax.swing.JLabel lblVentas4;
    private javax.swing.JLabel lblgiro;
    private javax.swing.JLabel lblnrc;
    private org.edisoncor.gui.panel.PanelCurves panelCurves1;
    private org.edisoncor.gui.panel.PanelCurves panelCurves2;
    private javax.swing.JButton reporteComprabtn;
    private javax.swing.JTable tblCompra;
    private javax.swing.JTable tblCompras;
    private javax.swing.JTable tblDetalleCompra;
    private javax.swing.JTable tblDetalleVenta;
    private javax.swing.JTable tblListaComprasMes;
    private javax.swing.JTable tblListaVentas;
    private javax.swing.JTable tblListaVentasMes2;
    private javax.swing.JTable tblParametro;
    private javax.swing.JTable tblProductosVender;
    private javax.swing.JTable tblProveedores;
    private javax.swing.JTable tblSucursal;
    private javax.swing.JTable tblTP;
    private javax.swing.JTextField txtBuscaTipoPrecio;
    private javax.swing.JTextField txtBuscarParametro;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCantidadVender;
    private javax.swing.JTextField txtClienteDetalleVenta;
    private javax.swing.JTextField txtClienteVenta;
    private javax.swing.JTextField txtCodBarraProd;
    private javax.swing.JTextField txtCodBarraProductos;
    private javax.swing.JTextField txtCodigoBarraVender;
    private javax.swing.JTextField txtCostoProd;
    private javax.swing.JTextField txtDireccionDetalleVenta;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtDireccionSuc;
    private javax.swing.JTextField txtDireccionVenta;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmailProveedor;
    private javax.swing.JTextField txtFechaDetalleCompra;
    private javax.swing.JTextField txtFechaDetalleVenta;
    private javax.swing.JTextField txtGiroDetalleVenta;
    private javax.swing.JTextField txtGiroVenta;
    private javax.swing.JTextField txtIDProveedor;
    private javax.swing.JTextField txtIDProveedor1;
    private javax.swing.JTextField txtIVADetalleCompra;
    private javax.swing.JTextField txtIVADetalleVenta;
    private javax.swing.JTextField txtIdCompra;
    private javax.swing.JTextField txtIdDetalleCompra;
    private javax.swing.JTextField txtIdDetalleVenta;
    private javax.swing.JTextField txtIdPar;
    private javax.swing.JTextField txtIdPar1;
    private javax.swing.JTextField txtIdSuc;
    private javax.swing.JTextField txtIdTP;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtIvaVenta;
    private javax.swing.JTextField txtModDirSuc;
    private javax.swing.JTextField txtModIdSuc;
    private javax.swing.JTextField txtModNombreSuc;
    private javax.swing.JTextField txtNIT;
    private javax.swing.JTextField txtNITDetalleVenta;
    private javax.swing.JTextField txtNITVenta;
    private javax.swing.JTextField txtNRCDetalleVenta;
    private javax.swing.JTextField txtNRCProveedor;
    private javax.swing.JTextField txtNRCVenta;
    private javax.swing.JTextField txtNoDocDetalleVenta;
    private javax.swing.JTextField txtNoDocVenta;
    private javax.swing.JTextField txtNomPar;
    private javax.swing.JTextField txtNomPar1;
    private javax.swing.JTextField txtNomProd;
    private javax.swing.JTextField txtNombrePrecio;
    private javax.swing.JTextField txtNombreProductoVender;
    private javax.swing.JTextField txtNombreProductos;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtNombreSuc;
    private javax.swing.JTextField txtNuevaSucursalProd;
    private javax.swing.JTextField txtNuevoCodigoBarra;
    private javax.swing.JTextField txtNuevoCostoProducto;
    private javax.swing.JTextField txtNuevoDireccionProveedor;
    private javax.swing.JTextField txtNuevoInventarioProducto;
    private javax.swing.JTextField txtNuevoNIT;
    private javax.swing.JTextField txtNuevoNRC;
    private javax.swing.JTextField txtNuevoNombreProducto;
    private javax.swing.JTextField txtNuevoNombreProveedor;
    private javax.swing.JTextField txtNuevoTelefonoProveedor;
    private javax.swing.JTextField txtNumDocCompra;
    private javax.swing.JTextField txtNumDocumentoDetalleCompra;
    private javax.swing.JTextField txtPercepcion;
    private javax.swing.JTextField txtPercepcionDetalleCompra;
    private javax.swing.JTextField txtPrecioProductos;
    private javax.swing.JTextField txtProductoInventario;
    private javax.swing.JTextField txtProductosBuscar;
    private javax.swing.JTextField txtProductosBuscar1;
    private javax.swing.JTextField txtProveedorDetalleCompra;
    private javax.swing.JTextField txtSubTotalDetalleCompra;
    private javax.swing.JTextField txtSucursalBuscar;
    private javax.swing.JTextField txtSucursalDetalleCompra;
    private javax.swing.JTextField txtSucursalDetalleVenta;
    private javax.swing.JTextField txtSuma;
    private javax.swing.JTextField txtSumaVenta;
    private javax.swing.JTextField txtTelSuc;
    private javax.swing.JTextField txtTelefonoProveedor;
    private javax.swing.JTextField txtTelefonoSuc;
    private javax.swing.JTextField txtTipoCompraDetalleCompra;
    private javax.swing.JTextField txtTipoPrecioDetalleVenta;
    private javax.swing.JTextField txtTipoVentaDetalleVenta;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalDetalleCompra;
    private javax.swing.JTextField txtTotalDetalleVenta;
    private javax.swing.JTextField txtTotalGravadoDetalleVenta;
    private javax.swing.JTextField txtTotalVenta;
    private javax.swing.JTextField txtUtPar;
    private javax.swing.JTextField txtUtilidadPrecio;
    private javax.swing.JTextField txtValorPar;
    private com.toedter.calendar.JDateChooser txt_fecha_compra;
    private com.toedter.calendar.JDateChooser txt_fecha_venta;
    // End of variables declaration//GEN-END:variables

    private void setVisible(JPopupMenu MenuEmergente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void BuscarProducto() {

        ControladorProducto cp = new ControladorProducto();
        Producto producto = new Producto();

        try {
            txtNombreProductoVender.setText("");
            producto = cp.Obtener(txtCodigoBarraVender.getText());
            if (producto.nombre == null) {
                JOptionPane.showMessageDialog(null, "Este producto no existe, por favor ingrese un codigo de barras valido");
                txtCodigoBarraVender.requestFocus();
                txtCodigoBarraVender.selectAll();
            } else {
                txtNombreProductoVender.setText(producto.nombre);
            }

        } catch (ErrorTienda ex) {
            JOptionPane.showMessageDialog(null, "No se encontró el producto: " + txtCodigoBarraVender.getText() + " " + ex.getMessage());
        }
    }

    private void BuscarProductoComprar() {
        if (!this.txtCodBarraProd.getText().equals("")) {

            Producto producto = new Producto();
            ControladorProducto cn = new ControladorProducto();
            try {
                producto = cn.Obtener(txtCodBarraProd.getText());

                System.out.println("producto " + producto.nombre);
                if (producto.nombre != null) {
                    txtNomProd.setText(producto.nombre);
                    txtNomProd.setEditable(false);
                    txtCantidad.setText("1");
                    txtCantidad.requestFocus();
                    txtCantidad.selectAll();
                } else {
                    JOptionPane.showMessageDialog(null, "Este producto no existe");
                    txtNomProd.setText("");
                    txtNomProd.setEditable(true);
                    txtNomProd.requestFocus();
                }

            } catch (ErrorTienda ex) {
                JOptionPane.showMessageDialog(null, "Error " + ex.getMessage());
            }
        }
    }

    private void agregarDetalleVenta(Venta nventa) {

        System.out.println("LLEGUE AQUI");
        conection cn = new conection();
        try {
            cn.Conectar();

            //AGREGAR A DETALLE VENTA...
            for (int i = 0; i < nventa.articulo.size(); i++) {
                System.out.println("UNIT " + nventa.articulo.get(i).PrecioUnitario);
                System.out.println("CANTIDAD " + nventa.articulo.get(i).cantidad);
                System.out.println("PRODUCTO " + nventa.articulo.get(i).producto.CodBarra);

                iList p = new iList(new ListasTablas("IdVenta", nventa.idVenta));
                p.add(new ListasTablas("CodBarra", nventa.articulo.get(i).producto.CodBarra));
                p.add(new ListasTablas("Cantidad", nventa.articulo.get(i).cantidad));
                p.add(new ListasTablas("PrecioUnitario", nventa.articulo.get(i).PrecioUnitario));
                p.add(new ListasTablas("IdSucursal", nventa.idSucursal));

                cn.AgregarRegistro("detalleventa", p, false);
            }

            //ACTUALIZAR INVENTARIO...
            try {
                ControladorVenta cv = new ControladorVenta();
                cv.ActualizarInventario(nventa.articulo, nventa.idSucursal);
                jtblProductos.removeAll();
                LlenarProducto("");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en agregar Detalle de Venta interno");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error agregando Detalles de Venta " + ex.getMessage());
        }

        JOptionPane.showMessageDialog(null, "Se ha agregado la venta correctamente");

        jpnAgregarVenta.setVisible(false);
        jpnListaVentas.setVisible(true);

        model0 = (DefaultTableModel) this.tblProductosVender.getModel();
        model0.setRowCount(0);
        filas = 0;

        LlenarVenta();
    }
}
