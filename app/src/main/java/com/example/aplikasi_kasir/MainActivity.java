package com.example.aplikasi_kasir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ItemBelanja> itemBelanja = new ArrayList<>();
    private RadioButton radioBarang, membership;
    private EditText etKodeBarang,etJumlahBarang;
    private LinearLayout formBarang,formBon;
    private TextView total,tvAdmin,tvDiskon;
    private Button btnSubmit;
    private boolean isMembershipActive = false; // Tambahkan variabel untuk melacak status membership

    // Array untuk menampung TextView untuk setiap detail barang
    private TextView[] namaBarangTextViews;
    private TextView[] hargaBarangTextViews;
    private TextView[] jumlahBarangTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioBarang = findViewById(R.id.radioBarang);
        etKodeBarang = findViewById(R.id.etKodeBarang);
        etJumlahBarang = findViewById(R.id.etJumlahBarang);
        formBarang = findViewById(R.id.formBarang);
        formBon = findViewById(R.id.formBon);
        total = findViewById(R.id.total);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvAdmin = findViewById(R.id.admin);
        tvDiskon = findViewById(R.id.diskon);
        membership = findViewById(R.id.membership);

        // Menginisialisasi array untuk menampung TextView untuk setiap detail barang
        namaBarangTextViews = new TextView[]{findViewById(R.id.nama), findViewById(R.id.nama2), findViewById(R.id.nama3)};
        hargaBarangTextViews = new TextView[]{findViewById(R.id.harga), findViewById(R.id.harga2), findViewById(R.id.harga3)};
        jumlahBarangTextViews = new TextView[]{findViewById(R.id.jumlah), findViewById(R.id.jumlah2), findViewById(R.id.jumlah3)};

        formBarang.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        membership.setVisibility(View.GONE);
        formBon.setVisibility(View.GONE);

        radioBarang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    formBarang.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    membership.setVisibility(View.VISIBLE);
                    formBon.setVisibility(View.VISIBLE);

                    btnSubmit.setOnClickListener(submit -> {
                        String inputKodeBarang = etKodeBarang.getText().toString();
                        String inputJumlahBarang = etJumlahBarang.getText().toString();

                        etKodeBarang.setText("");
                        etJumlahBarang.setText("");

                        if (!inputKodeBarang.isEmpty() && !inputJumlahBarang.isEmpty()) {
                            int kodeBarang = Integer.parseInt(inputKodeBarang);
                            int jumlahBarang = Integer.parseInt(inputJumlahBarang);
                            String namaBarang = "";
                            int admin = 0;

                            int hargaBarang = 0;

                            // Mendapatkan harga berdasarkan kode barang
                            if (kodeBarang == 12345) {
                                admin = 2500;
                                hargaBarang = (200000 * jumlahBarang + admin);
                                namaBarang = "Meja";
                            } else if (kodeBarang == 123456) {
                                admin = 2500;
                                hargaBarang = (210000 * jumlahBarang + admin);
                                namaBarang = "Kursi";
                            } else if (kodeBarang == 1234567) {
                                admin = 2500;
                                hargaBarang = (220000 * jumlahBarang + admin);
                                namaBarang = "Lampu";
                            }

                            // Membuat objek ItemBelanja dan menambahkannya ke dalam list
                            ItemBelanja barang = new ItemBelanja(namaBarang, jumlahBarang, hargaBarang, admin);
                            itemBelanja.add(barang);

                            // Menampilkan detail barang di nota
                            for (int i = 0; i < itemBelanja.size(); i++) {
                                namaBarangTextViews[i].setText(itemBelanja.get(i).getNama());
                                hargaBarangTextViews[i].setText(String.valueOf(itemBelanja.get(i).getHarga()-admin));
                                jumlahBarangTextViews[i].setText(String.valueOf(itemBelanja.get(i).getJumlah()));
                            }

                            // Menghitung total belanjaan
                            int totalHarga = 0;
                            for (ItemBelanja item : itemBelanja) {
                                totalHarga += item.getHarga();
                            }

                            // Menghitung total biaya administrasi
                            int totalAdmin = 0;
                            for (ItemBelanja item : itemBelanja) {
                                totalAdmin += item.getAdmin();
                            }

                            // Menghitung diskon 5% jika membership aktif
                            double diskon = isMembershipActive ? 0.05 * totalHarga : 0;

                            // Total pembayaran
                            int totalPembayaran = totalHarga + totalAdmin - (int)diskon;

                            // Menampilkan total di TextView total
                            total.setText(String.valueOf("Rp "+totalPembayaran));

                            tvAdmin.setText(String.valueOf(totalAdmin));
                            tvDiskon.setText(isMembershipActive ? "5%" : "0%"); // Sesuaikan teks diskon
                            formBon.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    formBarang.setVisibility(View.GONE);
                }
            }
        });

        // Tambahkan listener untuk tombol membership
        membership.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Perbarui status membership
                isMembershipActive = isChecked;
            }
        });
    }
}
