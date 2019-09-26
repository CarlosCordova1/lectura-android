package com.example.jcruz.lectura11.Model;

public class DBanomalia {

    private long id;
    private String codigo;
    private String descripcion;


    public DBanomalia( long id, String codigo,String descripcion){
        this.id=id;    this.codigo=codigo;   this.descripcion=descripcion;
           }

    public DBanomalia(){}


    public void Setdataanomalia(     String codigo,  String descripcion ){
        this.codigo=codigo;   this.descripcion=descripcion;  }

    public long getid() { return id; }
    public void setid(long id) { this.id = id;}

    public String getcodigo() {return codigo; }
    public void setcodigo(String codigo) {this.codigo = codigo;  }

    public String getDescripcion() {return descripcion;  }
    public void setDescripcion(String descripcion) {  this.descripcion = descripcion;   }


}
