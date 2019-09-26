package com.example.jcruz.lectura11.Model;

public class DBusuario {

    private long id;
    private int idportal;
    private String nombre;
    private String userlogin;
    private String passwlogin;
    private String imei;
    private String token;


    public DBusuario( long id, int idportal,String nombre, String userlogin, String passwlogin, String imei, String token){
        this.id=id;    this.idportal=idportal;   this.nombre=nombre;this.userlogin=userlogin;
        this.passwlogin=passwlogin; this.imei=imei; this.token=token;
    }

    public DBusuario(){}


    public void Setdatusuario( int idportal,String nombre, String userlogin, String passwlogin, String imei,  String token ){
        this.idportal=idportal;   this.nombre=nombre;this.userlogin=userlogin;
        this.passwlogin=passwlogin; this.imei=imei;  this.token=token; }

    public long getid() { return id; }
    public void setid(long id) { this.id = id;}

    public int getidportal() {return idportal; }
    public void setidportal(int idportal) {this.idportal = idportal;  }

    public String getnombre() {return nombre;  }
    public void setnombre(String nombre) {  this.nombre = nombre;   }

    public String getuserlogin() {return userlogin;  }
    public void setuserlogin(String userlogin) {  this.userlogin = userlogin;   }

    public String getpasswlogin() {return passwlogin;  }
    public void setpasswlogin(String passwlogin) {  this.passwlogin = passwlogin;   }


    public String getimei() {return imei;  }
    public void setimei(String imei) {  this.imei = imei;   }

    public String gettoken() {return token;  }
    public void settoken(String token) {  this.token = token;   }


}