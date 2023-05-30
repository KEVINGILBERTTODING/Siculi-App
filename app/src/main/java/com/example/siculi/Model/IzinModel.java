package com.example.siculi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IzinModel implements Serializable {


        @SerializedName("id")
        private String id;

        @SerializedName("id_karyawan")
        private String idKaryawan;

        @SerializedName("atasan")
        private String atasan;

        @SerializedName("waktu_pergi")
        private String waktuPergi;

        @SerializedName("waktu_pulang")
        private String waktuPulang;

        @SerializedName("keperluan")
        private String keperluan;

        @SerializedName("tgl_ijin")
        private String tanggalIzin;

        @SerializedName("status")
        private String status;

        @SerializedName("jenis")
        private String jenis;

        @SerializedName("supervisor")
        private String supervisor;

        @SerializedName("nama")
        private String nama;

        @SerializedName("nik")
        private String nik;

        public IzinModel(String id, String idKaryawan, String atasan, String waktuPergi, String waktuPulang, String keperluan, String tanggalIzin, String status, String jenis, String supervisor, String nama, String nik) {
            this.id = id;
            this.idKaryawan = idKaryawan;
            this.atasan = atasan;
            this.waktuPergi = waktuPergi;
            this.waktuPulang = waktuPulang;
            this.keperluan = keperluan;
            this.tanggalIzin = tanggalIzin;
            this.status = status;
            this.jenis = jenis;
            this.supervisor = supervisor;
            this.nama = nama;
            this.nik = nik;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdKaryawan() {
            return idKaryawan;
        }

        public void setIdKaryawan(String idKaryawan) {
            this.idKaryawan = idKaryawan;
        }

        public String getAtasan() {
            return atasan;
        }

        public void setAtasan(String atasan) {
            this.atasan = atasan;
        }

        public String getWaktuPergi() {
            return waktuPergi;
        }

        public void setWaktuPergi(String waktuPergi) {
            this.waktuPergi = waktuPergi;
        }

        public String getWaktuPulang() {
            return waktuPulang;
        }

        public void setWaktuPulang(String waktuPulang) {
            this.waktuPulang = waktuPulang;
        }

        public String getKeperluan() {
            return keperluan;
        }

        public void setKeperluan(String keperluan) {
            this.keperluan = keperluan;
        }

        public String getTanggalIzin() {
            return tanggalIzin;
        }

        public void setTanggalIzin(String tanggalIzin) {
            this.tanggalIzin = tanggalIzin;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getJenis() {
            return jenis;
        }

        public void setJenis(String jenis) {
            this.jenis = jenis;
        }

        public String getSupervisor() {
            return supervisor;
        }

        public void setSupervisor(String supervisor) {
            this.supervisor = supervisor;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getNik() {
            return nik;
        }

        public void setNik(String nik) {
            this.nik = nik;
        }
    }

