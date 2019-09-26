package com.example.jcruz.lectura11.Model;

public class Mlect {
    private long id;
    private int idtlec;
    private int idtodt;
    private String ruta;
    private String numcpr;
    private String medidor;
    private int rng1;
    private String idtclt;
    private String idtctr;
    private int idtusrlec;
    private String fchuac;
    private String fchlec;
    private String fch_verifica;
    private int lectura;
    private int lecturaver;
    private int coordx;
    private int coordy;
    private int mcoordx;
    private int mcoordy;
    private String edocliente;
    private String gironegocio;
    private String uso;
    private String domicilio;
    private int consumoprom;
    private int lecturaant;
    private String idtanm1;
    private String idtanm2;
    private String comentario;
    private String edo;
    private int idtscru;
    private int limitedown;
    private int limiteup;

    private int limitedown2;
    private int limiteup2;

    private int ruedas;
    private String fechacorte;
    private int intentos;
    private String idedocliente;





    public Mlect( long id,
                  int idtlec,
                  int idtodt,
                  String ruta,
                  String numcpr,
                  String medidor,
                  int rng1,
                  String idtclt,
            String idtctr,
                  int idtusrlec,
                  String fchuac,
                  String fchlec,
                  String fch_verifica,
                  int lectura,
             int lecturaver,
                  int coordx,
                  int coordy,
                  int mcoordx,
                  int mcoordy,
            String edocliente,
                  String gironegocio,
                  String uso,
                  String domicilio,
                  int consumoprom,
                  int lecturaant,
                  String idtanm1,
                  String idtanm2,
                  String edo,
                  int idtscru,
                  int limitedown,
                  int limiteup,
                    int limitedown2,
                  int limiteup2,
                     int ruedas,
                       String fechacorte,
                  int intentos,
                  String idedocliente


    ){
        this.id=id;    this.idtlec=idtlec;
        this.idtodt=idtodt;    this.numcpr=numcpr;
        this.rng1=rng1;    this.idtclt=idtclt;
        this.idtctr=idtctr;    this.idtusrlec=idtusrlec;
        this.fchuac=fchuac;    this.fchlec=fchlec;
        this.fch_verifica=fch_verifica;    this.lectura=lectura;
           this.lecturaver=lecturaver;
        this.coordx=coordx;    this.coordy=coordy;
        this.mcoordx=mcoordx;    this.mcoordy=mcoordy;
        this.edocliente=edocliente;    this.gironegocio=gironegocio;
        this.domicilio=domicilio;    this.consumoprom=consumoprom;
        this.lecturaant=lecturaant;    this.idtanm1=idtanm1;
        this.idtanm2=idtanm2;    this.idtscru=idtscru;
        this.ruta=ruta; this.medidor=medidor; this.uso=uso;this.edo=edo;
        this.limitedown=limitedown;this.limiteup=limiteup;


        this.limitedown2=limitedown2;
                this.limiteup2=limiteup2;
                this.ruedas=ruedas;
                this.fechacorte=fechacorte;

        this.intentos=intentos;
                this.idedocliente=idedocliente;

    }

    public Mlect(){

    }

    public Mlect(long Id, String string, String cursorString, String s, String idtclt, String idtctr){

    }


    public void Setdatalect( //long id,
                             int idtlec,
                             int idtodt,
                             String ruta,
                             String numcpr,
                             String medidor,
                             int rng1,
                             String idtclt,
                             String idtctr,
                             int idtusrlec,
                             String fchuac,
                             //String fchlec,
                             //String fch_verifica,
                             //int lectura,
                            // int lecturaver,
                             //int coordx,
                             //int coordy,
                             //int mcoordx,
                             //int mcoordy,
                             String edocliente,
                             String gironegocio,
                             String uso,
                             String domicilio,
                             int consumoprom,
                             int lecturaant,
                             //int idtanm1,
                             //int idtanm2,
                             String edo,

                             int idtscru, int limitedown, int limiteup,

                             int limitedown2,
                             int limiteup2,
                             int ruedas,
                             String fechacorte,
                             int intentos,
                             String idedocliente
                             ){
        //this.id=id;
        this.idtlec=idtlec;
        this.idtodt=idtodt;
        this.numcpr=numcpr;
        this.rng1=rng1;
        this.idtclt=idtclt;
        this.idtctr=idtctr;
        this.idtusrlec=idtusrlec;
        this.fchuac=fchuac;
        //this.fchlec=fchlec;
       // this.fch_verifica=fch_verifica;
        //this.lectura=lectura;
       // this.lecturaver=lecturaver;
        //this.coordx=coordx;
        //this.coordy=coordy;
        //this.mcoordx=mcoordx;
        //this.mcoordy=mcoordy;
        this.edocliente=edocliente;
        this.gironegocio=gironegocio;
        this.domicilio=domicilio;
        this.consumoprom=consumoprom;
        this.lecturaant=lecturaant;
        //this.idtanm1=idtanm1;
        //this.idtanm2=idtanm2;
        this.idtscru=idtscru;

        this.uso=uso;
        this.ruta=ruta;
        this.medidor=medidor;
        this.edo=edo;
        this.limitedown=limitedown;this.limiteup=limiteup;
        this.limitedown2=limitedown2;
        this.limiteup2=limiteup2;
        this.ruedas=ruedas;
        this.fechacorte=fechacorte;
        this.intentos=intentos;
        this.idedocliente=idedocliente;

    }





    public long getid() { return id; }
    public void setid(long id) { this.id = id;}

    public int getidtlec() {return idtlec; }
    public void setidtlec(int idtlec) {this.idtlec = idtlec;  }

    public int getlectura() {return lectura; }
    public void setlectura(int lectura) {this.lectura = lectura;  }

    public String getidtclt() {return idtclt;  }
    public void setidtclt(String idtclt) {  this.idtclt = idtclt;   }
    public String getidtctr() {return idtctr;  }
    public void setidtctr(String idtctr) {  this.idtctr = idtctr;   }


    public int getidtodt() {
        return idtodt;
    }
    public void setidtodt(int idtodt) {    this.idtodt = idtodt;    }

    public String getruta() {return ruta;  }
    public void setruta(String ruta) {  this.ruta = ruta;   }

    public int getidtusrlec() {
        return idtusrlec;
    }
    public void setidtusrlec(int idtusrlec) {    this.idtusrlec = idtusrlec;    }

    public String getfchuac() {
        return fchuac;
    }
    public void setfchuac(String fchuac) {    this.fchuac = fchuac;    }

    public String getedocliente() {
        return edocliente;
    }
    public void setedocliente(String edocliente) {    this.edocliente = edocliente;    }


    public String getgironegocio() {
        return gironegocio;
    }
    public void setgironegocio(String gironegocio) {    this.gironegocio = gironegocio;    }

    public String getdomicilio() {
        return domicilio;
    }
    public void setdomicilio(String domicilio) {    this.domicilio = domicilio;    }

    public int getconsumoprom() {
        return consumoprom;
    }
    public void setconsumoprom(int consumoprom) {    this.consumoprom = consumoprom;    }

    public int getlecturaant() {
        return lecturaant;
    }
    public void setlecturaant(int lecturaant) {    this.lecturaant = lecturaant;    }

    public int getidtscru() {
        return idtscru;
    }
    public void setidtscru(int idtscru) {    this.idtscru = idtscru;    }

    public int getlimitedown() {
        return limitedown;
    }
    public void setlimitedown(int limitedown) {    this.limitedown = limitedown;    }

    public int getlimiteup() {
        return limiteup;
    }
    public void setlimiteup(int limiteup) {    this.limiteup = limiteup;    }


    public String getnumcpr() {  return numcpr;   }
    public void setnumcpr(String numcpr) {
        this.numcpr = numcpr;
    }

    public String getMedidor() {return medidor;  }
    public void setMedidor(String medidor) {  this.medidor = medidor;   }

    public int getrng1() {  return rng1;  }

    public void setrng1(int rng1) {
        this.rng1 = rng1;
    }

    public String getUso() {return uso;  }
    public void setUso(String uso) {  this.uso = uso;   }

    public String getedo() {
        return edo;
    }
    public void setedo(String edo) {    this.edo = edo;    }

    public String getidtanm1() {
        return idtanm1;
    }
    public void setidtanm1(String idtanm1) {    this.idtanm1 = idtanm1;    }

    public String getidtanm2() {
        return idtanm2;
    }
    public void setidtanm2(String idtanm2) {    this.idtanm2 = idtanm2;    }

    public String getcomentario() {
        return comentario;
    }
    public void setcomentario(String comentario) {    this.comentario = comentario;    }

    public int getlimitedown2() {
        return limitedown2;
    }
    public void setlimitedown2(int limitedown2) {    this.limitedown2 = limitedown2;    }

    public int getlimiteup2() {
        return limiteup2;
    }
    public void setlimiteup2(int limiteup2) {    this.limiteup2 = limiteup2;    }

    public int getruedas() {
        return ruedas;
    }
    public void setruedas(int ruedas) {    this.ruedas = ruedas;    }

    public String getfechacorte() {
        return fechacorte;
    }
    public void setfechacorte(String fechacorte) {    this.fechacorte = fechacorte;    }

    public String getidedocliente() {
        return idedocliente;
    }
    public void setidedocliente(String idedocliente) {    this.idedocliente = idedocliente;    }

    public int getintentos() {
        return intentos;
    }
    public void setintentos(int intentos) {    this.intentos = intentos;    }




/*
    public String gethiredate() {
        return hiredate;
    }

    public void sethiredate(String hiredate) {
        this.hiredate = hiredate;
    }
    */



    public String toString(){
        return "Lect id: "+getid()+ "\n" +
                "idtodt: "+getrng1() + " " + getid() + "\n" +
                "numcpr : "+getnumcpr() + "\n" +
                "rng1 : "+getrng1();


    }
}
