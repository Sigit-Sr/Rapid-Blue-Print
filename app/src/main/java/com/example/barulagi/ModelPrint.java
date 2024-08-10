package com.example.barulagi;

public class ModelPrint {
    private String file;
    private String nama;
    private String alamat;
    private String pengiriman;
    private String key;

    public ModelPrint(){

    }

    public ModelPrint(String file, String nama, String alamat, String pengiriman) {
        this.file = file;
        this.nama = nama;
        this.alamat = alamat;
        this.pengiriman = pengiriman;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPengiriman() {
        return pengiriman;
    }

    public void setPengiriman(String pengiriman) {
        this.pengiriman = pengiriman;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}