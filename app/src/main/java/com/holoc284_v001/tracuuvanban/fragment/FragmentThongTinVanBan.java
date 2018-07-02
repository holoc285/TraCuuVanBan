package com.holoc284_v001.tracuuvanban.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.holoc284_v001.tracuuvanban.activity.DanhSachVBActivity;
import com.holoc284_v001.tracuuvanban.R;
import com.holoc284_v001.tracuuvanban.model.TraCuu;

import java.util.ArrayList;

/**
 * Created by holoc on 8/5/2017.
 */

public class FragmentThongTinVanBan extends Fragment {
    private TextView txtDonViTiepNhan, txtNguoiGui,
            txtNgayBanHanh,txtHieuLuc, txtHanXLToanVanBan,
                    txtYKienButPhe,txtNoiDungTrichYeu,
                    txtTapTinDinhKem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,container,false);
        txtDonViTiepNhan = view.findViewById(R.id.textViewDonViTiepNhan);
        txtNguoiGui = view.findViewById(R.id.textViewNguoiGui);
        txtNgayBanHanh = view.findViewById(R.id.textViewNgayBH);
        txtHieuLuc = view.findViewById(R.id.textViewHieuLuc);
        txtHanXLToanVanBan = view.findViewById(R.id.textViewHanXuLyToanVanBan);
        txtYKienButPhe = view.findViewById(R.id.textViewYKienButPhe);
        txtNoiDungTrichYeu = view.findViewById(R.id.textViewSoHieuGocTrichYeu);
        txtTapTinDinhKem = view.findViewById(R.id.textViewTapTinDinhKem);

        DanhSachVBActivity activity = (DanhSachVBActivity) getActivity();
        activity.checkConnection();
        int position = activity.getPosition();
        ArrayList<TraCuu> a = activity.getArray();
        //Toast.makeText(activity, a.get(position).getMaVB(), Toast.LENGTH_SHORT).show();
        txtDonViTiepNhan.setText("Đơn vị tiếp nhận:\n"+a.get(position).getCoQuan());
        txtNguoiGui.setText("Người gửi: "+a.get(position).getNguoiGui());
        txtNgayBanHanh.setText("Ngày ban hành: "+a.get(position).getNgayBanHanh());
        txtHieuLuc.setText("Hiệu lực: "+a.get(position).getNgayHieuLuc());
        txtHanXLToanVanBan.setText("Hạn xử lý: "+a.get(position).getHanXlToanVanBan());
        txtYKienButPhe.setText("Ý kiến bút phê:\n"+a.get(position).getyKienButPhe());
        txtNoiDungTrichYeu.setText("Nội dung trích yếu:\n"+a.get(position).getNoiDungTrichYeu());
        txtTapTinDinhKem.setText("Loại văn bản: "+a.get(position).getLoaiVanBan());

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
