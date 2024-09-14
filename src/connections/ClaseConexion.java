package connections;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

;

/**
 *
 * @author Roger Alvarez
 *
 */
public abstract class ClaseConexion {

    public Connection con;
    private Component Parent;

    public Component getParent() {
        return Parent;
    }

    public void setParent(Component Parent) {
        this.Parent = Parent;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public boolean AgregarRegistro(String NombreTabla, iList campos, boolean Merge) throws Exception {
        boolean agregado;
        String sql1 = ") VALUES(", sql = "INSERT INTO " + NombreTabla + "(";
        int y = 1;//para agregar elementos a la cadena

        for (ListasTablas campo : campos.getAll()) {
            sql += ((y > 1) ? ", " : "") + campo.getCampo();
            sql1 += ((y > 1) ? ", " : "") + "?";
            y++;
        }
        PreparedStatement ps;
        y = 1;
        sql += sql1 + ")";
        if (Merge) {
            sql += agregarMergeSentencia("mysql", campos);
        }
        ps = con.prepareStatement(sql);
        for (ListasTablas campo : campos.getAll()) {
            ps.setObject(y, campo.getValor());
            y++;
        }
        int m = 1;
        if (Merge) {
            for (ListasTablas o : campos.getAll()) {
                if (m > 1) {
                    ps.setObject(y, o.getValor());
                    y++;
                }
                m++;
            }
        }
        y = ps.executeUpdate();
        agregado = y > 0;
        System.out.println("SQL DE AGREGAR COMPRA" + sql);
        System.out.println("PREPARED STATEMENT DE AGREGAR COMPRA" + ps.toString());
        return agregado;
    }

    public boolean ModificarRegistro(String NombreTabla, iList Nuevoscampos, iList Ncondiciones) throws Exception {
        boolean modificado;
        String sql = "UPDATE " + NombreTabla + " SET ";
        PreparedStatement ps;
        int y = 1;//para agregar elementos a la cadena
        for (ListasTablas Nuevoscampo : Nuevoscampos.getAll()) {
            sql += ((y > 1) ? ", " : "") + Nuevoscampo.getCampo() + "=?";
            y++;
        }
        sql += " WHERE ";
        y = 1;//para agregar elementos a la cadena
        for (ListasTablas Ncondicione : Ncondiciones.getAll()) {
            sql += ((y > 1) ? " AND " : "") + Ncondicione.getCampo() + "=?";
            y++;
        }
        y = 1;
        System.out.println("SQL UPDATE: " + sql);
        ps = (PreparedStatement) con.prepareStatement(sql);
        for (ListasTablas Nuevoscampo : Nuevoscampos.getAll()) {
            ps.setObject(y, Nuevoscampo.getValor());
            y++;
        }
        for (ListasTablas Ncondicione : Ncondiciones.getAll()) {
            ps.setObject(y, Ncondicione.getValor());
            y++;
        }
        y = ps.executeUpdate();
        System.out.println("SQL PS: " + ps.toString());
        modificado = y > 0;
        return modificado;
    }//listo

    public abstract void CrearConexion() throws Exception;

    public void IniciarTransaccion() throws Exception {
        con.setAutoCommit(false);
    }

    public void comit() throws Exception {
        con.commit();
        con.setAutoCommit(true);
    }

    public void RollBack() throws Exception {
        con.rollback();
        con.setAutoCommit(true);
    }

    public void Conectar() throws Exception {
        this.CrearConexion();
    }

    public boolean Eliminar(String NombreTabla, iList condiciones) throws Exception {
        boolean borrado;
        PreparedStatement ps;
        String sql = "DELETE FROM " + NombreTabla + " WHERE ";
        int y = 1;//para agregar elementos a la cadena
        for (ListasTablas condicion : condiciones.getAll()) {
            sql += ((y > 1) ? " and " : "") + condicion.getCampo() + "=?";
            y++;
        }
        y = 1;
        System.out.println(sql);
        ps = (PreparedStatement) con.prepareStatement(sql);
        for (ListasTablas condicion : condiciones.getAll()) {
            ps.setObject(y, condicion.getValor());
            y++;
        }
        y = ps.executeUpdate();
        borrado = y > 0;
        return borrado;
    }

    public void Desconectar() throws Exception {
        con.close();
    }

    public PreparedStatement BuscarRegistro(String NombreTabla, String[] CamposAMostrar, iList condiciones) throws Exception {
        String sql = "SELECT";
        int u = 1;
        for (String f : CamposAMostrar) {
            sql += ((u > 1) ? ", " : " ") + f;
            u++;
        }
        sql += " FROM " + NombreTabla;
        if (condiciones.size() > 0) {
            sql += " WHERE";
        }
        u = 1;
        for (ListasTablas condicion : condiciones.getAll()) {
            sql += ((u > 1) ? " AND " : " ") + condicion.getCampo() + "=?";
            u++;
        }
        u = 1;
        PreparedStatement ps = con.prepareStatement(sql);
        for (ListasTablas condicion : condiciones.getAll()) {
            ps.setObject(u, condicion.getValor());
            u++;
        }
        System.out.println(sql);
        return ps;
    }

    public PreparedStatement BuscarRegistroLike(String NombreTabla, String NombreCampo, String valor) throws SQLException {

        String sql = "";

        sql = "SELECT IdSucursal, Nombre, Direccion, Telefono FROM " + NombreTabla + " WHERE " + NombreCampo + " LIKE '" + valor + "%'";

        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("SQL " + sql);
        return ps;
    }

    public PreparedStatement BuscarRegistroMes(String mes, String dia) throws SQLException {
        String sql = "";

        sql = "SELECT * FROM venta WHERE cast(Fecha as date) BETWEEN '2017-" + mes + "-01' AND '2017-" + mes + "-" + dia + "'";
        System.out.println("SQL 1 " + sql);
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("PSSS: " + ps);
        System.out.println("SQL 2 " + sql);
        return ps;
    }

    public PreparedStatement BuscarRegistroLikeTP(String NombreTabla, String NombreCampo, String valor) throws SQLException {

        String sql = "";

        sql = "SELECT IdTipoPrecio, Nombre, Utilidad FROM " + NombreTabla + " WHERE " + NombreCampo + " LIKE '" + valor + "%'";

        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("SQL " + sql);
        return ps;
    }

    public PreparedStatement BuscarRegistroLikePar(String NombreTabla, String NombreCampo, String valor) throws SQLException {

        String sql = "";

        sql = "SELECT IdParametro, Nombre, Valor FROM " + NombreTabla + " WHERE " + NombreCampo + " LIKE '" + valor + "%'";

        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("SQL " + sql);
        return ps;
    }

    public PreparedStatement BuscarProductoLike(String valor) throws SQLException {

        String sql = "";

        sql = "SELECT CodBarra, Nombre, Costo FROM Producto WHERE CodBarra LIKE '" + valor + "%'" + " OR Nombre LIKE '" + valor + "%'";

        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("SQL " + sql);
        return ps;
    }

    public PreparedStatement BuscarTodosEnSucursal(int idSucursal) throws SQLException {

        String sql = "";

        sql = "SELECT P.CodBarra, I.Cantidad, P.Costo, P.Nombre, I.IdSucursal FROM Producto P INNER JOIN Inventario I on P.CodBarra = I.CodBarra ORDER BY CodBarra ";
        //WHERE I.IdSucursal = " + idSucursal + "
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("SQL " + sql);
        return ps;
    }

    public PreparedStatement BuscarTodosEnSucursal(int idSucursal, String buscar) throws SQLException {

        String sql = "";

        sql = "SELECT P.CodBarra, I.Cantidad, P.Costo, P.Nombre, I.IdSucursal FROM Producto P INNER JOIN Inventario I on P.CodBarra = I.CodBarra WHERE P.Nombre LIKE '%" + buscar + "%' OR P.CodBarra LIKE '" + buscar + "%'";
        //I.IdSucursal = " + idSucursal + " AND 
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("SQL " + sql);
        return ps;
    }

    public String BuscarId(String tabla, String campoId, String campo, String value) throws SQLException {
        String id = null;
        String sql = "";

        sql = "SELECT " + campoId + " FROM " + tabla + " WHERE " + campo + " = '" + value + "'";
        System.out.println("SQL ROGELIO " + sql);
        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            id = rs.getString(campoId);
        }
        if (id == null) {
            id = "0";
        }
        return id;
    }

    public PreparedStatement BuscarRegistroAutomatXTexto(String NombreTabla, String[] CamposAMostrar, iList condiciones) throws Exception {
        String sql = "SELECT ";
        int u = 1;
        for (String f : CamposAMostrar) {
            sql += ((u > 1) ? ", " : " ") + f;
            u++;
        }
        sql += " FROM " + NombreTabla + " WHERE ";
        u = 1;
        for (ListasTablas condicion : condiciones.getAll()) {
            sql += ((u > 1) ? ", " : " ") + condicion.getCampo() + " LIKE '" + condicion.getValor() + "'%";
            u++;
        }
        System.out.println(sql);
        PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
        u = 1;
        for (ListasTablas condicion : condiciones.getAll()) {
            ps.setString(u, condicion.getValor() + "%");
            System.out.println("ClaseConexionPS" + ps.toString());
            u++;
        }
        System.out.println("ClaseConexionPS" + ps.toString());
        return ps;
    }

    public ClaseConexion() {
    }

    public PreparedStatement BuscarIdMax(String NombreCampo, String NombreTabla) throws Exception {
//        String sql="SELECT COUNT(*) from " + NombreTabla+";"
//            + "";
        String sql = "";

        sql = "SELECT MAX(" + NombreCampo + ") + 1 FROM " + NombreTabla;

        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println(sql);
        return ps;
    }

    private String agregarMergeSentencia(String servidor, iList campos) {
        String sql = "";
        switch (servidor) {
            case "mysql":
                sql += " ON DUPLICATE KEY UPDATE";
                int y = 1;
                for (ListasTablas o : campos.getAll()) {
                    if (y > 1) {
                        sql += ((y > 2) ? ", " : " ") + o.getCampo() + "=?";
                    }
                    y++;
                }
        }
        return sql;
    }

    private void asignarValoresAPS(iList campos, PreparedStatement ps, int cont) throws SQLException {
        int y = 1;
        cont++;
        for (ListasTablas o : campos.getAll()) {
            if (y > 1) {
                ps.setObject(cont, o.getValor());
                cont++;
            }
            y++;
        }
    }

    //metodo para buscar todos los registros de una tabla
    public PreparedStatement BuscarTodos(String NombreTabla, String[] CamposAMostrar) throws Exception {
        String sql = "SELECT";
        int u = 1;
        for (String f : CamposAMostrar) {
            sql += ((u > 1) ? ", " : " ") + f;
            u++;
        }
        sql += " FROM " + NombreTabla;
        u = 1;
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println(sql);
        return ps;
    }

    public PreparedStatement BuscarTodosCV(String NombreTabla, String[] CamposAMostrar) throws Exception {
        String sql = "SELECT";
        int u = 1;
        for (String f : CamposAMostrar) {
            sql += ((u > 1) ? ", " : " ") + f;
            u++;
        }
        sql += " FROM " + NombreTabla + " ORDER BY Fecha DESC";
        u = 1;
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println(sql);
        return ps;
    }

    public String nombreProveedor(String IdProveedor) {
        String nombreProveedor = null;
        String sql = "SELECT Nombre FROM proveedor WHERE IdProveedor = " + IdProveedor;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nombreProveedor = rs.getString("Nombre");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en nombreProveedor en ClaseConexion");
        }

        return nombreProveedor;
    }

    public String nombreSucursal(String IdSucursal) {
        String nombreSucursal = null;
        String sql = "SELECT Nombre FROM sucursal WHERE IdSucursal = " + IdSucursal;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nombreSucursal = rs.getString("Nombre");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en nombreSucursal en ClaseConexion");
        }

        return nombreSucursal;
    }

    public String ValorParametro(String nombreParametro) {
        String valorParametro = null;
        String sql = "SELECT Valor FROM parametro WHERE Nombre = '" + nombreParametro + "'";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                valorParametro = rs.getString("Valor");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en valorParametro en ClaseConexion" + ex.getMessage());
        }

        return valorParametro;
    }

    public String nombrePrecio(String idPrecio) {
        String nombrePrecio = null;
        int iDPrecio = Integer.parseInt(idPrecio);
        String sql = "SELECT Nombre FROM tipoprecio WHERE IdTipoPrecio = '" + iDPrecio + "'";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nombrePrecio = rs.getString("Nombre");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en NombrePrecio en ClaseConexion" + ex.getMessage());
        }

        return nombrePrecio;
    }

    public Double inventarioEnSucursalbyProducto(String idProducto, int idSucursal) {
        Double cantidad = 0.0;
        String sql = "SELECT Cantidad FROM inventario WHERE CodBarra = '" + idProducto + "' AND idSucursal = " + idSucursal;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cantidad = rs.getDouble("Cantidad");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en NombrePrecio en inventarioEnSucursalbyProducto" + ex.getMessage());
        }

        return cantidad;
    }

    public Double InventarioGlobal(String codBarra) {
        Double inventarioG = 0.0;
        String sql = "SELECT SUM(Cantidad) as Cantidad FROM inventario WHERE CodBarra = '" + codBarra + "'";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                inventarioG += rs.getDouble("Cantidad");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en inventarioGlobal en ClaseConexion" + ex.getMessage());
        }

        return inventarioG;
    }

    public Double utilidadPrecio(String nombrePrecio) {
        Double utilidad = null;
        String sql = "SELECT Utilidad FROM tipoprecio WHERE Nombre = '" + nombrePrecio + "'";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                utilidad = rs.getDouble("Utilidad");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en utilidadPrecio en ClaseConexion" + ex.getMessage());
        }

        return utilidad;
    }

    public Boolean revisarInventario(String codBarra, int idSucursal) {
        Boolean encontrado = false;

        String sql = "SELECT * FROM inventario WHERE codBarra = '" + codBarra + "' AND idSucursal = " + idSucursal;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            encontrado = rs.isBeforeFirst();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en utilidadPrecio en ClaseConexion" + ex.getMessage());
        }

        return encontrado;
    }

    public boolean insertarInventario(String codBarra, int idSucursal, double cantidad) {
        boolean agregado;
        int y = 1;
        String sql = "INSERT INTO inventario (CodBarra, IdSucursal, Cantidad) VALUES ('" + codBarra + "', " + idSucursal + ", " + cantidad + ")";
        System.out.println("SQL INV INSERT: " + sql);
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            y = ps.executeUpdate();
            System.out.println("PSSSSS: " + y);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en insertarInventario en ClaseConexion" + ex.getMessage());
        }
        agregado = y > 0;
        return agregado;
    }

    public PreparedStatement BuscarMes(String año) throws SQLException {
        String sql = "";

        sql = "SELECT DISTINCT MONTH(venta.Fecha) FROM venta WHERE YEAR(venta.Fecha)=" + año + " ORDER BY Fecha ASC";
        System.out.println("SQL 1 " + sql);
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("PSSS: " + ps);

        return ps;
    }

    public PreparedStatement BuscarAño() throws SQLException {
        String sql = "";

        sql = "SELECT DISTINCT YEAR(venta.Fecha) FROM venta ORDER BY Fecha DESC";
        System.out.println("SQL 1 " + sql);
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("PSSS: " + ps);

        return ps;
    }

    public PreparedStatement BuscarRegistroVentas(String mes, String año) throws SQLException {
        String sql = "";

        sql = "SELECT * FROM venta WHERE MONTH(venta.Fecha)=" + mes + " AND YEAR(venta.Fecha)=" + año;
        System.out.println("SQL 1 " + sql);
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("PSSS: " + ps);
        System.out.println("SQL 2 " + sql);
        return ps;
    }
    
     public PreparedStatement BuscarMesCompra(String año) throws SQLException {
        String sql = "";

        sql = "SELECT DISTINCT MONTH(compra.Fecha) FROM compra WHERE YEAR(compra.Fecha)=" + año + " ORDER BY Fecha ASC";
        System.out.println("SQL 1 " + sql);
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("PSSS: " + ps);

        return ps;
    }

    public PreparedStatement BuscarAñoCompra() throws SQLException {
        String sql = "";

        sql = "SELECT DISTINCT YEAR(compra.Fecha) FROM compra ORDER BY Fecha DESC";
        System.out.println("SQL 1 " + sql);
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("PSSS: " + ps);

        return ps;
    }

    public PreparedStatement BuscarRegistroCompras(String mes, String año) throws SQLException {
        String sql = "";

        sql = "SELECT * FROM compra WHERE MONTH(compra.Fecha)=" + mes + " AND YEAR(compra.Fecha)=" + año;
        System.out.println("SQL 1 " + sql);
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("PSSS: " + ps);
        System.out.println("SQL 2 " + sql);
        return ps;
    }
}
