package com.johnestebanap.xpiration_date_manager.ui.alertas;

public class ItemList {

    private String codigohistorial;
    private String codigoProducto;
    private String fechaCaducidad;
    private String nombreProducto;
    private String codigoCaducidad;
    private String estadoAlerta;


    public ItemList(String codigohistorial, String codigoProducto, String fechaCaducidad, String nombreProducto, String codigoCaducidad, String estadoAlerta){

        this.codigohistorial = codigohistorial;
        this.codigoProducto = codigoProducto;
        this.fechaCaducidad= fechaCaducidad;
        this.nombreProducto = nombreProducto;
        this.codigoCaducidad= codigoCaducidad;
        this.estadoAlerta = estadoAlerta;

    }

    public String getCodigohistorial(){
        return codigohistorial;
    }
    public String getCodigoProducto(){
        return codigoProducto;
    }
    public String getFechaCaducidad(){ return fechaCaducidad;}
    public String getNombreProducto(){
        return nombreProducto;
    }
    public String getCodigoCaducidad(){
        return codigoCaducidad;
    }
    public String getEstadoAlerta(){
        return estadoAlerta;
    }

  }
