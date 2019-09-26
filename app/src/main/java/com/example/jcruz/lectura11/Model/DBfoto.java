package com.example.jcruz.lectura11.Model;

public class DBfoto {
    private long id;
    private int idodt;
    private int idlec;
    private String contrato;
    private String nombre;
    private String base64;
    private String fecha;

    public DBfoto( long id,
                   int idodt,int idlec, String contrato, String nombre,String fecha, String base64){
        this.id=id;    this.idodt=idodt;   this.idlec=idlec;this.nombre=nombre;
        this.base64=base64;this.fecha=fecha;this.contrato=contrato;
    }
    public DBfoto(){}
    public void SetdataFoto( int idodt,int idlec, String contrato, String nombre,String fecha, String base64 ){
        this.idodt=idodt;   this.idlec=idlec;this.nombre=nombre; this.contrato=contrato;
        this.base64=base64;this.fecha=fecha; }

    public long getid() { return id; }
    public void setid(long id) { this.id = id;}

    public int getidodt() {return idodt; }
    public void setidodt(int idodt) {this.idodt = idodt;  }

    public int getidlec() {return idlec;  }
    public void setidlec(int idlec) {  this.idlec = idlec;   }

    public String getcontrato() {return contrato;  }
    public void setcontrato(String contrato) {  this.contrato = contrato;   }

    public String getnombre() {return nombre;  }
    public void setnombre(String nombre) {  this.nombre = nombre;   }

    public String getbase64() {return base64;  }
    public void setbase64(String base64) {  this.base64 = base64;   }
    public String getfecha() {return fecha;  }
    public void setfecha(String fecha) {  this.fecha = fecha;   }
}