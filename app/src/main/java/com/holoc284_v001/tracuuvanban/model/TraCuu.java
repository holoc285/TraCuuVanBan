package com.holoc284_v001.tracuuvanban.model;

import java.io.Serializable;

/**
 * Created by holoc on 5/22/2018.
 */

public class TraCuu implements Serializable {
    private int vanBanDenId;
    private String maVB;
    private String noiDungTrichYeu;
    private String coQuan;
    private String ngayBanHanh;
    private String ngayHieuLuc;
    private String nguoiGui;
    private String loaiVanBan;
    private String hanXlToanVanBan;
    private String yKienButPhe;
    private boolean trangThaiXuLy;



    public TraCuu(String maVB, String noiDungTrichYeu, String coQuan, String ngayBanHanh) {
        this.maVB = maVB;
        this.noiDungTrichYeu = noiDungTrichYeu;
        this.coQuan = coQuan;
        this.ngayBanHanh = ngayBanHanh;
    }

    public TraCuu(int vanBanDenId, String maVB, String noiDungTrichYeu, String coQuan, String ngayBanHanh, String ngayHieuLuc, String nguoiGui, String loaiVanBan, String hanXlToanVanBan, String yKienButPhe, boolean trangThaiXuLy) {
        this.vanBanDenId = vanBanDenId;
        this.maVB = maVB;
        this.noiDungTrichYeu = noiDungTrichYeu;
        this.coQuan = coQuan;
        this.ngayBanHanh = ngayBanHanh;
        this.ngayHieuLuc = ngayHieuLuc;
        this.nguoiGui = nguoiGui;
        this.loaiVanBan = loaiVanBan;
        this.hanXlToanVanBan = hanXlToanVanBan;
        this.yKienButPhe = yKienButPhe;
        this.trangThaiXuLy = trangThaiXuLy;
    }

    public int getVanBanDenId() {
        return vanBanDenId;
    }

    public void setVanBanDenId(int vanBanDenId) {
        this.vanBanDenId = vanBanDenId;
    }

    public String getMaVB() {
        return maVB;
    }

    public void setMaVB(String maVB) {
        this.maVB = maVB;
    }

    public String getNoiDungTrichYeu() {
        return noiDungTrichYeu;
    }

    public void setNoiDungTrichYeu(String noiDungTrichYeu) {
        this.noiDungTrichYeu = noiDungTrichYeu;
    }

    public String getCoQuan() {
        return coQuan;
    }

    public void setCoQuan(String coQuan) {
        this.coQuan = coQuan;
    }

    public String getNgayBanHanh() {
        return ngayBanHanh;
    }

    public void setNgayBanHanh(String ngayBanHanh) {
        this.ngayBanHanh = ngayBanHanh;
    }

    public String getNgayHieuLuc() {
        return ngayHieuLuc;
    }

    public void setNgayHieuLuc(String ngayHieuLuc) {
        this.ngayHieuLuc = ngayHieuLuc;
    }

    public String getNguoiGui() {
        return nguoiGui;
    }

    public void setNguoiGui(String nguoiGui) {
        this.nguoiGui = nguoiGui;
    }

    public String getLoaiVanBan() {
        return loaiVanBan;
    }

    public void setLoaiVanBan(String loaiVanBan) {
        this.loaiVanBan = loaiVanBan;
    }

    public String getHanXlToanVanBan() {
        return hanXlToanVanBan;
    }

    public void setHanXlToanVanBan(String hanXlToanVanBan) {
        this.hanXlToanVanBan = hanXlToanVanBan;
    }

    public String getyKienButPhe() {
        return yKienButPhe;
    }

    public void setyKienButPhe(String yKienButPhe) {
        this.yKienButPhe = yKienButPhe;
    }

    public boolean isTrangThaiXuLy() {
        return trangThaiXuLy;
    }

    public void setTrangThaiXuLy(boolean trangThaiXuLy) {
        this.trangThaiXuLy = trangThaiXuLy;
    }
}
