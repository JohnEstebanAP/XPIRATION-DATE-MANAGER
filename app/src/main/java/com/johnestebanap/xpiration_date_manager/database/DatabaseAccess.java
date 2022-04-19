package com.johnestebanap.xpiration_date_manager.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    //private constructor so that  object creation from outside the class is avoided
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    //to return the single instance of detabase
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    //to open the database
    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    //closing the database connection
    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    //now lets create a mehod to query and return the result from database

    //ve vill query for addres by passing name
    public String getAddress(String name) {
        c = db.rawQuery("SELECT tipoUsuario from TipoUsuario where cod_tipo_usuario =" + name, new String[]{});
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {
            String address = c.getString(0);
            buffer.append("" + address);
        }
        return buffer.toString();
    }

    public String[] getLogin(String user, String correo) {
        c = db.rawQuery(" SELECT nom_usuario, ced_usuario, correo_usuario, contrasenia_usuario from Usuario where ced_usuario =" + user + " or correo_usuario = '" + correo + "'", new String[]{});
        String[] valores = new String[4];
        int[] losValores = new int[10];
        while (c.moveToNext()) {

            String nom_usuario = c.getString(0);
            String ced_usuario = c.getString(1);
            String correo_usuario = c.getString(2);
            String contrasenia_usuario = c.getString(3);
            valores[0] = nom_usuario;
            valores[1] = ced_usuario;
            valores[2] = correo_usuario;
            valores[3] = contrasenia_usuario;
        }
        return valores;
    }

    //Consultas con los usuarios
    public void insertarUsuario(String cedula, String nombre, String apellido, String correo, String contrasenia, String telefono, String cod_tipo_usuario) {
        db.execSQL("INSERT INTO Usuario (ced_usuario, nom_usuario, apellido_usuario, correo_usuario, contrasenia_usuario,telefono_usuario, cod_tipo_usuario)" +
                "VALUES(" + cedula + ", '" + nombre + "', '" + apellido + "', '" + correo + "', '" + contrasenia + "', " + telefono + ", " + cod_tipo_usuario + ")");
    }

    public void updateUsuario(String cedula, String nombre, String apellido, String correo, String contrasenia, String telefono, String cod_tipo_usuario) {
        db.execSQL("UPDATE Usuario SET ced_usuario = " + cedula + ", nom_usuario ='" + nombre + "', apellido_usuario = '" + apellido + "', correo_usuario = '" + correo + "', contrasenia_usuario = '" + contrasenia + "', telefono_usuario = " + telefono + ", cod_tipo_usuario = " + cod_tipo_usuario + " WHERE ced_usuario = " + cedula + "");
    }

    public void deleteUsuario(String nombre, String apellido) {
        db.execSQL("DELETE FROM Usuario WHERE nom_usuario = '" + nombre + "' AND apellido_usuario = '" + apellido + "'");
    }
    //Eliminar un registro

    public String[] getDatosUser(int cantidadUsuarios, String columna) {
        c = db.rawQuery("SELECT " + columna + " FROM Usuario", new String[]{});
        String[] valores = new String[cantidadUsuarios];
        int i = 0;
        while (c.moveToNext()) {
            String apellido_usuario = c.getString(0);
            valores[i] = apellido_usuario;
            i++;
        }
        return valores;
    }

    public String getcontrasenia(String cedula) {
        c = db.rawQuery("SELECT contrasenia_usuario FROM Usuario WHERE ced_usuario =" + cedula + "", null);
        c.moveToNext();
        String contrasenia = c.getString(0);
        return contrasenia;
    }

    public String[] getTipoUsuario() {
        c = db.rawQuery("SELECT tipoUsuario FROM TipoUsuario", new String[]{});
        String[] valores = new String[6];
        int i = 0;
        while (c.moveToNext()) {
            String tipo_usuario = c.getString(0);
            valores[i] = tipo_usuario;
            i++;
        }
        return valores;
    }

    public String[] getUsuarios(String cedula) {
        c = db.rawQuery("SELECT ced_usuario, nom_usuario, apellido_usuario, correo_usuario, telefono_usuario, cod_tipo_usuario, contrasenia_usuario FROM Usuario WHERE ced_usuario = " + cedula + "", null);

        /*String[] valores = new String[7];
        int i = 0;

            while (c.moveToNext()) {
                String datos_usuario = c.getString(i);
                valores[i] = datos_usuario;
                i++;
            }

        return valores;*/

        String[] valores = new String[7];
        while (c.moveToNext()) {

            String ced_usuario = c.getString(0);
            String nom_usuario = c.getString(1);
            String apellido_usuario = c.getString(2);
            String correo_usuario = c.getString(3);
            String telefono_usuario = c.getString(4);
            String cod_tipo_usuario = c.getString(5);
            String contrasenia_usuario = c.getString(6);
            valores[0] = ced_usuario;
            valores[1] = nom_usuario;
            valores[2] = apellido_usuario;
            valores[3] = correo_usuario;
            valores[4] = telefono_usuario;
            valores[5] = cod_tipo_usuario;
            valores[6] = contrasenia_usuario;
        }
        return valores;

    }

    public int getUsuariosCantidad(String cedula) {
        c = db.rawQuery("select count(*) from Usuario where ced_usuario =" + cedula + "", null);
        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;
    }

    //Consultas con los productos

    public String[] getProducto(String cadena) {
        c = db.rawQuery("SELECT cod_Producto, nom_Producto, fecha_caducidad, cod_estanteria FROM Producto WHERE fecha_caducidad='" + cadena + "'", new String[]{});
        String[] valores = new String[4];
        int[] losValores = new int[10];
        while (c.moveToNext()) {

            String cod_Producto = c.getString(0);
            String nom_Producto = c.getString(1);
            String fecha_caducidad = c.getString(2);
            String cod_estanteria = c.getString(3);
            valores[0] = cod_Producto;
            valores[1] = nom_Producto;
            valores[2] = fecha_caducidad;
            valores[3] = cod_estanteria;
        }
        return valores;
    }

    public String[] getProducto2(String codigo) {
        c = db.rawQuery("SELECT cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad, fecha_ingreso_Producto," +
                "cod_estadoPro, cod_estanteria, cod_caducidad FROM Producto WHERE cod_Producto = (SELECT cod_Producto FROM HistorialAlertas where cod_Producto = " + codigo + ") ", new String[]{});
        String[] valores = new String[10];
        while (c.moveToNext()) {

            String cod_Producto = c.getString(0);
            String nom_Producto = c.getString(1);
            String nombre_marca = c.getString(2);
            String cantidad = c.getString(3);
            String fecha_Producto = c.getString(4);
            String fecha_caducidad = c.getString(5);
            String fecha_ingreso_Producto = c.getString(6);
            String cod_estadoPro = c.getString(7);
            String cod_estanteria = c.getString(8);
            String cod_caducidad = c.getString(9);
            valores[0] = cod_Producto;
            valores[1] = nom_Producto;
            valores[2] = nombre_marca;
            valores[3] = cantidad;
            valores[4] = fecha_Producto;
            valores[5] = fecha_caducidad;
            valores[6] = fecha_ingreso_Producto;
            valores[7] = cod_estadoPro;
            valores[8] = cod_estanteria;
            valores[9] = cod_caducidad;
        }
        return valores;
    }

    public String[] getCaducidad() {
        c = db.rawQuery("SELECT cod_caducidad FROM Producto", new String[]{});
        String[] valores = new String[25];
        while (c.moveToNext()) {
            String cod_caducidad = c.getString(8);
            valores[0] = cod_caducidad;
        }
        return valores;
    }

    public void updateEstadoAlerta(String producto, String codigo) {
        db.execSQL("UPDATE Producto SET cod_estadoPro = " + codigo + " WHERE cod_Producto = " + producto + "");
    }

    public void updateEstadoPro(String producto, String codigo) {
        db.execSQL("UPDATE Producto SET cod_estadoPro = " + codigo + " WHERE cod_Producto = " + producto + "");
    }

    public void deleteProducto(String codigo) {
        int codigoInt = Integer.parseInt(codigo);
        int cantidad = cantidadProductos(codigoInt);
        if (cantidad == 0) {
            db.execSQL("DELETE FROM Producto WHERE cod_Producto = " + codigo + "");
        } else {
            db.execSQL("UPDATE Producto SET cantidad = (cantidad-cantidad) WHERE cod_Producto = " + codigo + "");
        }
    }

    public int cantidadProductos(int codigo) {
        c = db.rawQuery("SELECT cantidad FROM Producto WHERE cod_Producto = " + codigo + "", null);

        c.moveToFirst();
        int cantidad = c.getInt(0);

        return cantidad;
    }

    public String[][] fechaCaducidad(int cantidad) {
        c = db.rawQuery("SELECT fecha_caducidad, cod_Producto FROM Producto", null);
        //String[][] valores = new String[cantidad][2];
        String arr[][] = new String[cantidad][2];

        for (int i = 0; i < 2; i++) {
            c.moveToFirst();
            //while(c.isAfterLast()== false) {
            for (int j = 0; j < cantidad; j++) {
                arr[j][i] = c.getString(i);
                c.moveToNext();
            }
        }
        return arr;
    }

    public int CantidadNotificaciones2() {
        c = db.rawQuery("SELECT count(*) FROM Producto inner join HistorialAlertas on Producto.cod_Producto  = HistorialAlertas.cod_Producto  WHERE HistorialAlertas.cod_estado_alertas = 1", null);

        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;
    }

    public String[] SelectCodProducto() {

        int cantidad = CantidadNotificaciones2();
        c = db.rawQuery("SELECT Producto.cod_Producto FROM Producto inner join HistorialAlertas on Producto.cod_Producto  = HistorialAlertas.cod_Producto  WHERE HistorialAlertas.cod_estado_alertas = 1", null);
        String[] valores = new String[cantidad];
        while (c.moveToNext()) {
            String cod_Producto = c.getString(0);
            valores[0] = cod_Producto;
        }
        return valores;
    }



    public int cantidadCodProductoRepetido(String numero) {
        c = db.rawQuery("SELECT count(*) FROM Producto inner join HistorialAlertas on Producto.cod_Producto  = HistorialAlertas.cod_Producto  WHERE HistorialAlertas.cod_caducidad_alertas = "+numero+"", null);

        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;
    }

    public String[][] SelectCodProductoRepetido(String numero) {

        int cantidad = cantidadCodProductoRepetido(numero);
        c = db.rawQuery("SELECT Producto.cod_Producto, HistorialAlertas.cod_historial FROM Producto inner join HistorialAlertas on Producto.cod_Producto  = HistorialAlertas.cod_Producto  WHERE HistorialAlertas.cod_caducidad_alertas = "+numero+"", null);
        String[][] valores = new String[cantidad][2];
        for (int i = 0; i < 2; i++) {
            c.moveToFirst();
            for (int j = 0; j < cantidad; j++) {
                valores[j][i] = c.getString(i);
                c.moveToNext();
            }
        }
        return valores;
    }

    public void updateCaducidadAlertas(String cod_historial) {
        db.execSQL("UPDATE HistorialAlertas SET cod_caducidad_alertas = 2 WHERE cod_historial = "+cod_historial+"");
    }

//Consulta supervisor

    public void tipoSupervisor(String cedula) {
        db.execSQL("UPDATE Usuario SET cod_tipo_usuario = 2 WHERE ced_usuario = "+cedula+"");
    }
    public void otorgarPuesto(String nombre, String apellido) {
        db.execSQL("UPDATE Usuario SET cod_tipo_usuario = 4 WHERE nom_usuario = '"+nombre+"' AND apellido_usuario = '"+apellido+"'");
    }

    //Consulta con los permisos

    public void deletePermiso(String nombre, String apellido, int codigo) {
        db.execSQL("DELETE FROM UsuarioPermiso WHERE UsuarioPermiso.ced_usuario = " +
                "(SELECT Usuario.ced_usuario FROM Usuario WHERE nom_usuario = '" + nombre + "' AND apellido_usuario" +
                " = '" + apellido + "' and  UsuarioPermiso.cod_permiso = " + codigo + ")");
    }

    public String[] selectCedula(String nombre, String apellido, int cantidad) {
        c = db.rawQuery("SELECT ced_usuario FROM UsuarioPermiso" +
                "WHERE UsuarioPermiso.ced_usuario =" +
                "(SELECT Usuario.ced_usuario FROM Usuario WHERE nom_usuario = '" + nombre + "' AND apellido_usuario" +
                "= '" + apellido + "')", null);

        String[] valores = new String[cantidad];
        while (c.moveToNext()) {
            String ced_usuario = c.getString(0);
            valores[0] = ced_usuario;
        }
        return valores;
    }

    public int selectCedula2(String nombre, String apellido) {
        c = db.rawQuery("SELECT Usuario.ced_usuario FROM Usuario WHERE nom_usuario = '" + nombre + "' AND apellido_usuario = '" + apellido + "'", null);

        c.moveToFirst();
        int cedula = c.getInt(0);

        return cedula;
    }

    public void insertPermiso(int cedula, int codigo) {
        db.execSQL("INSERT INTO UsuarioPermiso (ced_usuario,cod_permiso)" +
                "VALUES (" + cedula + "," + codigo + ")");
    }


    public void updatePermiso(String cadena) {
        c = db.rawQuery("INSERT UsuarioPermiso" +
                "        SET cod_permiso = [5]" +
                "        WHERE ced_usuario.UsuarioPermiso =" +
                "                (SELECT ced_usuario.Usuario FROM Usuario WHERE nom_usuario ='" + cadena + "'", new String[]{});
    }

    public int contarPermisos(String nombre, String apellido) {
        c = db.rawQuery("SELECT count(*) from UsuarioPermiso inner join Usuario on UsuarioPermiso.ced_usuario  = " +
                "Usuario.ced_usuario WHERE nom_usuario = '" + nombre + "' AND apellido_usuario = '" + apellido + "'", null);

        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;
    }

    public int[] selectPermisos(String nombre, String apellido, int cantidad) {
        c = db.rawQuery("SELECT UsuarioPermiso.cod_permiso from UsuarioPermiso inner join Usuario on UsuarioPermiso.ced_usuario  = Usuario.ced_usuario WHERE nom_usuario = '" + nombre + "' AND apellido_usuario = '" + apellido + "'", null);

        int[] valores = new int[cantidad];
        int i = 0;
        while (c.moveToNext()) {
            int cod_permiso = c.getInt(0);
            valores[i] = cod_permiso;
            i++;
        }
        return valores;
    }

    public int Cantidaregistros() {
        c = db.rawQuery("SELECT count(*)  FROM Producto INNER JOIN EstadoProducto on Producto.cod_estadoPro = EstadoProducto.cod_estadoPro INNER JOIN Caducidad on Producto.cod_caducidad  = Caducidad.cod_caducidad INNER JOIN ProductoProveedor on Producto.cod_Producto = ProductoProveedor.cod_Producto INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor INNER JOIN Estanteria on Producto.cod_estanteria= Estanteria.cod_estanteria", null);

        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;
    }

    public int totalProductos() {
        c = db.rawQuery("SELECT count(*)  FROM Producto", null);

        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;
    }


    public int cantidaUsuarios() {
        c = db.rawQuery("SELECT count(*)  FROM Usuario", null);

        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;
    }


    public String[][] getAllProductos(int columnas, int cantidaRegistros) {
        c = db.rawQuery("SELECT Producto.cod_Producto, Producto.nom_Producto, Producto.nombre_marca, Producto.cantidad, Proveedor.cod_proveedor,Producto.cod_estanteria, Estanteria.cod_pasillo, Producto.fecha_Producto, Producto.fecha_caducidad, Producto.fecha_ingreso_Producto, EstadoProducto.tipo_estado , Producto.cod_caducidad, Caducidad.tipo_caducidad FROM Producto INNER JOIN EstadoProducto on Producto.cod_estadoPro = EstadoProducto.cod_estadoPro INNER JOIN Caducidad on Producto.cod_caducidad  = Caducidad.cod_caducidad INNER JOIN ProductoProveedor on Producto.cod_Producto = ProductoProveedor.cod_Producto INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor INNER JOIN Estanteria on Producto.cod_estanteria= Estanteria.cod_estanteria", null);
        String[] valores = new String[cantidaRegistros * columnas];

        String arr[][] = new String[cantidaRegistros][columnas];

        for (int i = 0; i < columnas; i++) {
            c.moveToFirst();
            //while(c.isAfterLast()== false) {
            for (int j = 0; j < cantidaRegistros; j++) {
                arr[j][i] = c.getString(i);
                c.moveToNext();
            }
        }
        return arr;
    }


    public String[][] productosFecha(int columnas, int cantidaRegistros, String fecha1, String fecha2) {
        c = db.rawQuery("SELECT Producto.cod_Producto, Producto.nom_Producto, Producto.nombre_marca, Producto.cantidad, Proveedor.cod_proveedor,Producto.cod_estanteria, Estanteria.cod_pasillo, Producto.fecha_Producto, Producto.fecha_caducidad, Producto.fecha_ingreso_Producto, EstadoProducto.tipo_estado  , Producto.cod_caducidad, Caducidad.tipo_caducidad FROM Producto INNER JOIN EstadoProducto on Producto.cod_estadoPro = EstadoProducto.cod_estadoPro INNER JOIN Caducidad on Producto.cod_caducidad  = Caducidad.cod_caducidad INNER JOIN ProductoProveedor on Producto.cod_Producto = ProductoProveedor.cod_Producto INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor INNER JOIN Estanteria on Producto.cod_estanteria= Estanteria.cod_estanteria WHERE fecha_caducidad BETWEEN '" + fecha1 + "' and '" + fecha2 + "'", null);
        String[] valores = new String[cantidaRegistros * columnas];

        String arr[][] = new String[cantidaRegistros][columnas];

        for (int i = 0; i < columnas; i++) {
            c.moveToFirst();
            //while(c.isAfterLast()== false) {
            for (int j = 0; j < cantidaRegistros; j++) {
                arr[j][i] = c.getString(i);
                c.moveToNext();
            }
        }
        return arr;
    }


    public int cantidadProductosFecha(String fecha1, String fecha2) {
        c = db.rawQuery("SELECT count(*)  FROM Producto INNER JOIN EstadoProducto on Producto.cod_estadoPro = EstadoProducto.cod_estadoPro INNER JOIN Caducidad on Producto.cod_caducidad  = Caducidad.cod_caducidad INNER JOIN ProductoProveedor on Producto.cod_Producto = ProductoProveedor.cod_Producto INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor INNER JOIN Estanteria on Producto.cod_estanteria= Estanteria.cod_estanteria where  fecha_caducidad BETWEEN '" + fecha1 + "' and '" + fecha2 + "'", null);

        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;

    }


    public String[][] productosFechamenos3(int columnas, int cantidaRegistros) {
        c = db.rawQuery("SELECT Producto.cod_Producto, Producto.nom_Producto, Producto.nombre_marca, Producto.cantidad, Proveedor.cod_proveedor,Producto.cod_estanteria, Estanteria.cod_pasillo, Producto.fecha_Producto, Producto.fecha_caducidad, Producto.fecha_ingreso_Producto, EstadoProducto.tipo_estado  , Producto.cod_caducidad, Caducidad.tipo_caducidad FROM Producto INNER JOIN EstadoProducto on Producto.cod_estadoPro = EstadoProducto.cod_estadoPro INNER JOIN Caducidad on Producto.cod_caducidad  = Caducidad.cod_caducidad INNER JOIN ProductoProveedor on Producto.cod_Producto = ProductoProveedor.cod_Producto INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor INNER JOIN Estanteria on Producto.cod_estanteria= Estanteria.cod_estanteria WHERE Producto.cod_caducidad = 2", null);
        String[] valores = new String[cantidaRegistros * columnas];

        String arr[][] = new String[cantidaRegistros][columnas];

        for (int i = 0; i < columnas; i++) {
            c.moveToFirst();
            //while(c.isAfterLast()== false) {
            for (int j = 0; j < cantidaRegistros; j++) {
                arr[j][i] = c.getString(i);
                c.moveToNext();
            }
        }
        return arr;
    }


    public int cantidadProductosFechamenos3() {
        c = db.rawQuery("SELECT count(*)  FROM Producto INNER JOIN EstadoProducto on Producto.cod_estadoPro = EstadoProducto.cod_estadoPro INNER JOIN Caducidad on Producto.cod_caducidad  = Caducidad.cod_caducidad INNER JOIN ProductoProveedor on Producto.cod_Producto = ProductoProveedor.cod_Producto INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor INNER JOIN Estanteria on Producto.cod_estanteria= Estanteria.cod_estanteria where Producto.cod_caducidad = 2 ", null);

        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;

    }

    public String[][] productosFechaProximo(int columnas, int cantidaRegistros) {
        c = db.rawQuery("SELECT Producto.cod_Producto, Producto.nom_Producto, Producto.nombre_marca, Producto.cantidad,Proveedor.cod_proveedor,Producto.cod_estanteria, Estanteria.cod_pasillo, Producto.fecha_Producto, Producto.fecha_caducidad, Producto.fecha_ingreso_Producto, EstadoProducto.tipo_estado  , Producto.cod_caducidad, Caducidad.tipo_caducidad FROM Producto INNER JOIN EstadoProducto on Producto.cod_estadoPro = EstadoProducto.cod_estadoPro INNER JOIN Caducidad on Producto.cod_caducidad  = Caducidad.cod_caducidad INNER JOIN ProductoProveedor on Producto.cod_Producto = ProductoProveedor.cod_Producto INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor INNER JOIN Estanteria on Producto.cod_estanteria= Estanteria.cod_estanteria WHERE Producto.cod_caducidad = 3", null);
        String[] valores = new String[cantidaRegistros * columnas];

        String arr[][] = new String[cantidaRegistros][columnas];

        for (int i = 0; i < columnas; i++) {
            c.moveToFirst();
            //while(c.isAfterLast()== false) {
            for (int j = 0; j < cantidaRegistros; j++) {
                arr[j][i] = c.getString(i);
                c.moveToNext();
            }
        }
        return arr;
    }


    public int cantidadProductosFechaProximo() {
        c = db.rawQuery("SELECT count(*)  FROM Producto INNER JOIN EstadoProducto on Producto.cod_estadoPro = EstadoProducto.cod_estadoPro INNER JOIN Caducidad on Producto.cod_caducidad  = Caducidad.cod_caducidad INNER JOIN ProductoProveedor on Producto.cod_Producto = ProductoProveedor.cod_Producto INNER JOIN Proveedor on ProductoProveedor.cod_proveedor = Proveedor.cod_proveedor INNER JOIN Estanteria on Producto.cod_estanteria= Estanteria.cod_estanteria where Producto.cod_caducidad = 3 ", null);

        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;
    }


    public String[] getProductoScaner(String cod_producto) {
        c = db.rawQuery("select cod_Producto,nom_Producto,cod_estanteria,fecha_caducidad from Producto where cod_Producto =" + cod_producto + "", null);
        String[] producto = new String[4];

        //c.moveToNext()
        c.moveToFirst();
        producto[0] = c.getString(0);
        producto[1] = c.getString(1);
        producto[2] = c.getString(2);
        producto[3] = c.getString(3);

        return producto;
    }

    public int getProductoScanerCantidad(String cod_producto) {
        c = db.rawQuery("select count(*) from Producto where cod_Producto =" + cod_producto + "", null);
        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;
    }


    public void updateProductoScaner(String cod_Producto, String nom_Producto, String cod_estanteria, String fecha_caducidad) {
        db.execSQL("UPDATE Producto SET  cod_Producto= " + cod_Producto + " ,nom_Producto = '" + nom_Producto + "',cod_estanteria = " + cod_estanteria + ", fecha_caducidad='" + fecha_caducidad + "' where cod_Producto =" + cod_Producto + "");
    }

    public void insertProductoScanerRegistro(String cod_Producto, String nom_Producto, String nombre_marca, String cantidad, String fecha_Producto, String fecha_caducidad, String fecha_ingreso_Producto, String cod_estanteria) {
        db.execSQL("INSERT INTO Producto(cod_Producto, nom_Producto, nombre_marca, cantidad, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)" +
                "VALUES  (" + cod_Producto + ",'" + nom_Producto + "', '" + nombre_marca + "', " + cantidad + ",'" + fecha_Producto + "', '" + fecha_caducidad + "','" + fecha_ingreso_Producto + "', 4, " + cod_estanteria + ", 4)");
    }

    public void insertarProducto(String codigo, String nombre, String marca, String fecha, String caducidad, String ingreso, String estado, String estanteria, String cod_caducidad) {
        c = db.rawQuery("INSERT INTO Producto (cod_Producto, nom_Producto, nombre_marca, fecha_Producto, fecha_caducidad,fecha_ingreso_Producto, cod_estadoPro, cod_estanteria, cod_caducidad)" +
                "VALUES(" + codigo + ", " + nombre + ", " + marca + ", " + fecha + ", " + caducidad + ", " + ingreso + ", " + estado + ", " + estanteria + ", " + cod_caducidad + ")", new String[]{});
    }

    public void insertProductoProveedor(String cod_Producto, String cod_proveedor) {
        db.execSQL("Insert Into ProductoProveedor(cod_Producto,cod_proveedor) values(" + cod_Producto + "," + cod_proveedor + ")");
    }


    public String[] getDatosTabla(int cantidadregistros, String columna, String tabla) {
        c = db.rawQuery("SELECT " + columna + " FROM " + tabla + "", new String[]{});
        String[] valores = new String[cantidadregistros];
        int i = 0;
        while (c.moveToNext()) {
            String dato = c.getString(0);
            valores[i] = dato;
            i++;
        }
        return valores;
    }

    public int cantidadDatosTabla(String tabla) {
        c = db.rawQuery("SELECT count(*)  FROM " + tabla + "", null);

        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;
    }


    public String getCodProvedor(String nom_proveedor) {
        c = db.rawQuery("select cod_proveedor from  Proveedor where nom_proveedor='" + nom_proveedor + "'", new String[]{});
        String cod = "0";
        while (c.moveToNext()) {
            cod = c.getString(0);
        }
        return cod;
    }

    //Consulta con las notificaciones

    public void insertHistorialAlertas(String cod_historial, String fechaHoy, String cod_Producto, String cod_caducidad, String cod_estado) {
        db.execSQL("INSERT INTO HistorialAlertas (cod_historial, fecha_historial, cod_Producto, cod_caducidad_alertas, cod_estado_alertas)" +
                "    VALUES(" + cod_historial + ",'" + fechaHoy + "', " + cod_Producto + "," + cod_caducidad + ", " + cod_estado + ")");
    }

    public void updateEstadoHistorialAlertas(String cod_historial) {
        db.execSQL("UPDATE HistorialAlertas SET cod_estado_alertas = 1 WHERE cod_historial = " + cod_historial + "");
    }

    public void insertUsuarioHistorialAlertas(String fechaHoy, String cod_historial, String cedula, String cambioEstado_producto) {
        db.execSQL("insert into UsuarioHistorialAlertas(fecha_atencionNotificacion, cod_historial, ced_usuario, cambioEstado_producto)" +
                "VALUES ('" + fechaHoy + "'," + cod_historial + ", " + cedula + "," + cambioEstado_producto + ")");
    }

    public int cantidadAlertas2() {
        c = db.rawQuery("SELECT COUNT(*) FROM HistorialAlertas WHERE cod_estado_alertas = 2", null);

        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;
    }


    //Consulta Historial de alertas

    public String[][] getAllHistorialAlertas(int columnas, int cantidaRegistros) {
        c = db.rawQuery("select " +
                "HistorialAlertas.cod_historial, HistorialAlertas.cod_Producto, Producto.fecha_caducidad," +
                " Producto.nom_Producto, HistorialAlertas.cod_caducidad_alertas," +
                "HistorialAlertas.cod_estado_alertas from HistorialAlertas inner join  Producto  " +
                "on HistorialAlertas.cod_Producto  = Producto.cod_Producto  WHERE cod_estado_alertas = 2 ORDER BY HistorialAlertas.cod_caducidad_alertas asc", null);

        // String [] valores = new String[cantidaRegistros*columnas];

        String arr[][] = new String[cantidaRegistros][columnas];

        for (int i = 0; i < columnas; i++) {
            c.moveToFirst();
            //while(c.isAfterLast()== false) {
            for (int j = 0; j < cantidaRegistros; j++) {
                arr[j][i] = c.getString(i);
                c.moveToNext();
            }
        }
        return arr;
    }

    //Graficas

    public int cantidadGraficoLineas() {
        c = db.rawQuery("select count(*) from (SELECT COUNT(*) From UsuarioHistorialAlertas  inner join Usuario on UsuarioHistorialAlertas.ced_usuario = Usuario.ced_usuario group by Usuario.ced_usuario)", null);

        int cantidad = 0;
        while (c.moveToNext()) {
            cantidad = c.getInt(0);
        }
        return cantidad;
    }


    public String[][] graficoLineas() {
        c = db.rawQuery("SELECT Producto.cod_Producto, HistorialAlertas.cod_historial FROM Producto inner join HistorialAlertas on Producto.cod_Producto  = HistorialAlertas.cod_Producto  WHERE HistorialAlertas.cod_caducidad_alertas = 2", null);

        int columnas = 2;
     //   int cantidaRegistros = cantidadGraficoLineas();
        int cantidad = cantidadCodProductoRepetido("2");

        String arr[][] = new String[cantidad][columnas];

        for (int i = 0; i < columnas; i++) {
            c.moveToFirst();
            for (int j = 0; j < cantidad; j++) {
                arr[j][i] = c.getString(i);
                c.moveToNext();
            }
        }
        return arr;
/*
        String[] valores = new String[cantidaRegistros];
        int i = 0;
        while (c.moveToNext()) {
            String dato = c.getString(0);
            valores[i] = dato;
            i++;
        }
        return valores;*/
    }


    public String[][] graficoLineas2() {

      //  int columnas = 2;
        int cantidad = cantidadGraficoLineas();
        //int cantidad = cantidadCodProductoRepetido(numero);
        c = db.rawQuery("SELECT COUNT(*), Usuario.nom_usuario, Usuario.apellido_usuario From UsuarioHistorialAlertas  inner join Usuario on UsuarioHistorialAlertas.ced_usuario = Usuario.ced_usuario group by Usuario.ced_usuario", null);
        String[][] valores = new String[cantidad][3];
        for (int i = 0; i < 3; i++) {
            c.moveToFirst();
            for (int j = 0; j < cantidad; j++) {
                valores[j][i] = c.getString(i);
                c.moveToNext();
            }
        }
        return valores;
    }
}