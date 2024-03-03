package com.example.aplikasi_kasir;

public class ItemBelanja {
    private String nama;
    private int jumlah;
    private int harga;
    private int admin;

    public ItemBelanja(String nama, int jumlah, int harga, int admin) {
        this.nama = nama;
        this.jumlah = jumlah;
        this.harga = harga;
        this.admin = admin;
    }

    public String getNama() {
        return nama;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getHarga() {
        return harga;
    }
    public int getAdmin(){
        return admin;
    }
}
