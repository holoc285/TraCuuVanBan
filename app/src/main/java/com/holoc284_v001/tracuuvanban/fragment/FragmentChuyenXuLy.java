package com.holoc284_v001.tracuuvanban.fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.holoc284_v001.tracuuvanban.R;
import com.holoc284_v001.tracuuvanban.activity.DanhSachUserActivity;
import com.holoc284_v001.tracuuvanban.activity.DanhSachVBActivity;
import com.holoc284_v001.tracuuvanban.activity.MainActivity;
import com.holoc284_v001.tracuuvanban.model.NguoiNhan;
import com.holoc284_v001.tracuuvanban.model.TraCuu;
import com.holoc284_v001.tracuuvanban.model.User;
import com.holoc284_v001.tracuuvanban.sqlite.SQLiteQueryUser;
import com.holoc284_v001.tracuuvanban.utils.Path;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by holoc on 6/14/2018.
 */

public class FragmentChuyenXuLy extends Fragment {
    TextView txtSoVanBanLuanChuyen, txtUserNhanLuanChuyen;
    EditText edtNoiDungLuanChuyen, edtHanXyLy, edtButPheLuanChuyen;
    Button btnLuanChuyen;
    View view;
    ArrayList<NguoiNhan> list;
    ArrayList<NguoiNhan> nhanArrayList;
    ArrayList<NguoiNhan> danhSachNhanDaChon;
    JSONArray jsonArrayDsUserIdDaChon;
    ArrayList<TraCuu> a;
    int position;
    List<User> listUser;
    String res=null;
    JSONArray jsUser;
    //List<User> listUser;
    DanhSachVBActivity activity;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chuyenxyly, container, false);
        AnhXa();

        activity = (DanhSachVBActivity) getActivity();
        position = activity.getPosition();
        a = activity.getArray();

        listUser = activity.getUsers();


        txtUserNhanLuanChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DanhSachUserActivity.class);
                startActivityForResult(intent,1000);
            }
        });

        txtUserNhanLuanChuyen.setText("Chọn nơi nhận văn bản");
        txtSoVanBanLuanChuyen.setText("Số đến: " + a.get(position).getMaVB());
        SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");
        edtHanXyLy.setText(dinhDangNgay.format(Calendar.getInstance().getTime()));

        edtHanXyLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay();
            }
        });

        nhanArrayList = getNguoiNhan(false);
        btnLuanChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (res==null | edtNoiDungLuanChuyen.length()==0 | edtButPheLuanChuyen.length()==0){
                    Toast.makeText(getActivity(), "Vui lòng nhập đủ thông tin luân chuyển!", Toast.LENGTH_SHORT).show();
                }else if (activity.isConnected()==false){
                    activity.checkConnection();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==1000 && data!=null){
            res = data.getStringExtra("jsonUser").toString();
            //Log.e("test", data.getSerializableExtra("json").toString());
            if (res.equals("No")){
                txtUserNhanLuanChuyen.setText("Chọn nơi nhận văn bản");
            }else {
                txtUserNhanLuanChuyen.setText("Người nhận:\n");
                txtUserNhanLuanChuyen.append(data.getSerializableExtra("json").toString());
            }
            try {
                jsUser = new JSONArray(res);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.e("aaa",jsUser.toString());
            OnClickLuanChuyen();
        }
    }
    
    private void OnClickLuanChuyen(){
        btnLuanChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (res.equals("No") | edtNoiDungLuanChuyen.getText().toString().isEmpty() |
                        edtButPheLuanChuyen.getText().toString().isEmpty()){
                    activity.checkConnection();
                    Toast.makeText(getActivity(), "Vui lòng nhập đủ thông tin luân chuyển!", Toast.LENGTH_SHORT).show();
                }else {
                    if (activity.isConnected()) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                postChuyenXuLy();

                            }
                        });
                        thread.start();
                        Toast.makeText(getActivity(), "Luân chuyển thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("result", "OK");
                        getActivity().setResult(2000,intent);
                        getActivity().finish();
                    }else {
                        activity.checkConnection();
                    }
                }
            }
        });
    }

    private void ChonNgay() {
        final Calendar calendar1 = Calendar.getInstance();
        int ngay = calendar1.get(Calendar.DATE);
        int thang = calendar1.get(Calendar.MONTH);
        int nam = calendar1.get(Calendar.YEAR);
        DatePickerDialog date = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar1.set(year,month,dayOfMonth);
                SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");
                edtHanXyLy.setText(dinhDangNgay.format(calendar1.getTime()));
            }
        },nam,thang,ngay);
        date.show();
    }

    private void AnhXa() {
        txtSoVanBanLuanChuyen = view.findViewById(R.id.textViewSoVanBanLuanChuyen);
        txtUserNhanLuanChuyen = view.findViewById(R.id.textViewUserNhanLuanChuyen);
        edtNoiDungLuanChuyen = view.findViewById(R.id.editTextNoiDungLuanChuyen);
        edtButPheLuanChuyen = view.findViewById(R.id.editTextButPheLuanChuyen);
        btnLuanChuyen = view.findViewById(R.id.buttonHoanThanhLuanChuyen);
        edtHanXyLy = view.findViewById(R.id.editTextHanXuLyTiepTheo);
        
    }

    private void postChuyenXuLy(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("vbdenId",""+a.get(position).getVanBanDenId());
            jsonObject.put("vbdenLcId",""+a.get(position).getVbden_lc_id());
            jsonObject.put("noiDungLuanChuyen",edtNoiDungLuanChuyen.getText().toString());
            jsonObject.put("butPheLuanChuyen",edtButPheLuanChuyen.getText().toString());
            jsonObject.put("userId", MainActivity.userId);
            jsonObject.put("hanXuLy",edtHanXyLy.getText().toString());
            jsonObject.put("nguoiChuyen",MainActivity.userName);
            jsonObject.put("dsNguoiNhan", jsUser);
            jsonObject.put("donViUser", a.get(position).getIdCoQuan()+"");
            //jsonObject.put("statusHoanThanh", daXL);
            //Toast.makeText(getActivity(), jsonObject.toString(), Toast.LENGTH_LONG).show();
            Log.e("aaaa", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            OkHttpClient client = new OkHttpClient();

            //Toast.makeText(getActivity(), Path.LOCALHOST, Toast.LENGTH_SHORT).show();
            Log.e("Domain", Path.LOCALHOST);
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(mediaType,jsonObject.toString());
            Request request = new Request.Builder()
                    .url("http://"+Path.LOCALHOST+"/api/tracuuvanban/postLuanChuyenVanBan")
                    .post(body)
                    .build();
            Log.e("Url", "http://"+Path.LOCALHOST+"/api/tracuuvanban/postLuanChuyenVanBan");
            Response response = client.newCall(request).execute();
            Log.e("result",response.toString());
            //Toast.makeText(getActivity(), "Thành công" + response.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<NguoiNhan> getNguoiNhan(boolean isSelect){
        list = new ArrayList<>();
        SQLiteQueryUser sqLiteQueryUser = SQLiteQueryUser.getINSTANCE(getActivity());

        listUser = sqLiteQueryUser.getAllUser();
        for (int i=0; i< listUser.size();i++){
            NguoiNhan nguoiNhan = new NguoiNhan();
            nguoiNhan.setSeleted(isSelect);
            nguoiNhan.setEmail(listUser.get(i).getName());
            list.add(nguoiNhan);
        }
        return list;
    }
}
