package com.example.jcruz.lectura11;

public class Config {
    private String API="https://www.aguakan.com/git/api/webt.php?urlget=lct/1.0/mobile";

    private String APIFoto="https://www.aguakan.com/git/api/imglectura/?getimg";
   // private String API="https://www.aguakan.com/git/api/webt.php?urlget=lct/1.0/mobile";
    //public Config( String api){   this.api=api;    }

    public Config(){}
    public String getAPI() { return API; }
    public String getAPIfoto() { return APIFoto; }


}