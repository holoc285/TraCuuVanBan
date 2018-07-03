package com.holoc284_v001.tracuuvanban.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.holoc284_v001.tracuuvanban.R;
import com.holoc284_v001.tracuuvanban.activity.DanhSachVBActivity;
import com.holoc284_v001.tracuuvanban.model.NguoiNhan;
import com.holoc284_v001.tracuuvanban.model.TraCuu;
import com.holoc284_v001.tracuuvanban.model.User;
import com.holoc284_v001.tracuuvanban.sqlite.SQLiteQueryUser;
import com.holoc284_v001.tracuuvanban.utils.Path;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.holoc284_v001.tracuuvanban.activity.MainActivity.userName;

/**
 * Created by holoc on 8/5/2017.
 */

public class FragmentThongTinVanBan extends Fragment {
    private TextView txtDonViTiepNhan, txtNguoiGui,
            txtNgayBanHanh,txtHieuLuc, txtHanXLToanVanBan,
                    txtYKienButPhe,txtNoiDungTrichYeu;
    private TextView txtTapTinDinhKem;
    ArrayList<String> dsUserDaXL;
    List<User> listUser;
    ArrayList<NguoiNhan> list;
    int position;
    ArrayList<TraCuu> a;
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
        dsUserDaXL = new ArrayList<>();
        SQLiteQueryUser sqLiteQueryUser = SQLiteQueryUser.getINSTANCE(getActivity());
        listUser = sqLiteQueryUser.getUserID(userName);
        getNguoiNhan();


        DanhSachVBActivity activity = (DanhSachVBActivity) getActivity();
        activity.checkConnection();
        position = activity.getPosition();
        a = activity.getArray();
        getDsUserDaXuLyVB(a.get(position).getVanBanDenId());
        //Toast.makeText(activity, a.get(position).getMaVB(), Toast.LENGTH_SHORT).show();
        txtDonViTiepNhan.setText("Đơn vị tiếp nhận:\n"+a.get(position).getCoQuan());
        txtNguoiGui.setText("Người gửi: "+a.get(position).getNguoiGui());
        txtNgayBanHanh.setText("Ngày ban hành: "+a.get(position).getNgayBanHanh());
        txtHieuLuc.setText("Hiệu lực: "+a.get(position).getNgayHieuLuc());
        txtHanXLToanVanBan.setText("Hạn xử lý: "+a.get(position).getHanXlToanVanBan());
        txtYKienButPhe.setText("Ý kiến bút phê:\n"+a.get(position).getyKienButPhe());
        txtNoiDungTrichYeu.setText("Nội dung trích yếu:\n"+a.get(position).getNoiDungTrichYeu());
//        Toast.makeText(getActivity(), "" + a.get(position).isTrangThaiXuLy(), Toast.LENGTH_SHORT).show();
//        if (a.get(position).isTrangThaiXuLy()==false){
//            txtTapTinDinhKem.setText("Loại văn bản: "+ a.get(position).getLoaiVanBan());
//        }
        //txtTapTinDinhKem.setText("Loại văn bản: "+ a.get(position).getLoaiVanBan());

        //Toast.makeText(getActivity(), activity.getArrayUserXL().toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), dsUserDaXL.toString(), Toast.LENGTH_SHORT).show();
        //Log.e("user",dsUserDaXL.toString());
        return view;
    }
    private ArrayList<NguoiNhan> getNguoiNhan(){
        list = new ArrayList<>();
        SQLiteQueryUser sqLiteQueryUser = SQLiteQueryUser.getINSTANCE(getActivity());

        listUser = sqLiteQueryUser.getAllUser();
        for (int i=0; i< listUser.size();i++){
            NguoiNhan nguoiNhan = new NguoiNhan();
            nguoiNhan.setEmail(listUser.get(i).getName());
            nguoiNhan.setUserId(listUser.get(i).getUserId());
            list.add(nguoiNhan);
        }
        return list;
    }
    public ArrayList<String> getDsUserDaXuLyVB(int vbden_id){
        final String url = "http://"+ Path.LOCALHOST+"/api/tracuuvanban/danhSachUserDaXuLy/"+vbden_id;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response!=null)
                {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        //
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            int user = jsonObject.getInt("vbden_lc_xlchinh_userid");
                            String userXL;
                            for (int j =0;j< listUser.size();j++){
                                if (user == listUser.get(j).getUserId()){
                                    userXL = listUser.get(j).getName();
                                    dsUserDaXL.add(userXL);
                                }
                            }
                        }

                        if (a.get(position).isTrangThaiXuLy()){
                            txtTapTinDinhKem.setText("");
                            txtTapTinDinhKem.append("Người xử lý: "+dsUserDaXL);
                        }else {
                            txtTapTinDinhKem.setText("Loại văn bản: "+ a.get(position).getLoaiVanBan());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return dsUserDaXL;
    }


}
