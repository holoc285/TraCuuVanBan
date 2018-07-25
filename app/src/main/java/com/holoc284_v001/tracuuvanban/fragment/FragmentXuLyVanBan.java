package com.holoc284_v001.tracuuvanban.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.holoc284_v001.tracuuvanban.activity.MainActivity;
import com.holoc284_v001.tracuuvanban.adapter.NguoiNhanAdapter;
import com.holoc284_v001.tracuuvanban.activity.DanhSachVBActivity;
import com.holoc284_v001.tracuuvanban.R;
import com.holoc284_v001.tracuuvanban.model.NguoiNhan;
import com.holoc284_v001.tracuuvanban.model.TraCuu;
import com.holoc284_v001.tracuuvanban.model.User;
import com.holoc284_v001.tracuuvanban.sqlite.SQLiteQueryUser;
import com.holoc284_v001.tracuuvanban.utils.Path;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FragmentXuLyVanBan extends Fragment {

    TextView txtSoVanBan;
    EditText edtNdGopY;
    Button btnHoanThanhXuLy;
    ArrayList<TraCuu> a;
    int position;
    ArrayList<NguoiNhan> list;
    ArrayList<NguoiNhan> nhanArrayList;
    ArrayList<String> danhSachNhan;
    public static Boolean trangThaiXL = false;
    DanhSachVBActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,container,false);
        txtSoVanBan = view.findViewById(R.id.textViewSoVanBanXL);
        edtNdGopY = view.findViewById(R.id.editTextNDGopYXL);
        btnHoanThanhXuLy = view.findViewById(R.id.buttonHoanThanhXuLy);

        activity = (DanhSachVBActivity) getActivity();
        activity.checkConnection();
        position = activity.getPosition();
        a = activity.getArray();

        txtSoVanBan.setText("Số đến: " + a.get(position).getMaVB());


        btnHoanThanhXuLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtNdGopY.getText().toString().trim().isEmpty()){
                    activity.checkConnection();
                    Toast.makeText(getActivity(), "Vui lòng nhập nội dung góp ý!!!", Toast.LENGTH_SHORT).show();
                }else{
                    if (activity.isConnected()) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                insertGopYVanBan();
                            }
                        });
                        thread.start();
                        Toast.makeText(getActivity(), "Đã hoàn thành góp ý", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }else {
                        activity.checkConnection();
                    }
                }
            }
        });
        return view;
    }

    private void dialogNguoiNhan(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setContentView(R.layout.dialog_reciever_layout);
        final ListView listView = (ListView) dialog.findViewById(R.id.listDanhSachNguoiNhan);
        CheckBox checkBoxAll = (CheckBox) dialog.findViewById(R.id.checkboxAllNguoiNhan);
        Button btnOk = dialog.findViewById(R.id.buttonOk);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        //SET DEFAULT UNCHECKED
        nhanArrayList = getNguoiNhan(false);

        NguoiNhanAdapter adapter = new NguoiNhanAdapter(getActivity(),nhanArrayList);
        listView.setAdapter(adapter);

        checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //IF CHECKBOX IS CHECKED
                if (isChecked){
                    nhanArrayList = getNguoiNhan(true);
                    NguoiNhanAdapter adapter = new NguoiNhanAdapter(getActivity(),nhanArrayList);
                    listView.setAdapter(adapter);

                }
                //CHECKBOX IS NOT CHECKED
                else {
                    nhanArrayList = getNguoiNhan(false);
                    NguoiNhanAdapter adapter = new NguoiNhanAdapter(getActivity(),nhanArrayList);
                    listView.setAdapter(adapter);
                }
            }
        });


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lay danh sach nguoi da checked
                for (int i=0;i< nhanArrayList.size();i++){
                    if (nhanArrayList.get(i).isSeleted()){
                        Toast.makeText(getActivity(), nhanArrayList.get(i).getEmail(), Toast.LENGTH_SHORT).show();
                    }

                }
                dialog.dismiss();

            }
        });
        dialog.show();

    }

    private ArrayList<NguoiNhan> getNguoiNhan(boolean isSelect){
        list = new ArrayList<>();
        SQLiteQueryUser sqLiteQueryUser = SQLiteQueryUser.getINSTANCE(getActivity());

        List<User> listUser = sqLiteQueryUser.getAllUser();
        for (int i=0; i< listUser.size();i++){
            NguoiNhan nguoiNhan = new NguoiNhan();
            nguoiNhan.setSeleted(isSelect);
            nguoiNhan.setEmail(listUser.get(i).getName());
            list.add(nguoiNhan);
        }

//        for(int i = 0; i < users.length; i++){
//
//            NguoiNhan nguoiNhan = new NguoiNhan();
//            nguoiNhan.setSeleted(isSelect);
//
//
//            nguoiNhan.setEmail(users[i]);
//            list.add(nguoiNhan);
//        }
        return list;
    }

    private void insertGopYVanBan(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("vbdenId",""+a.get(position).getVanBanDenId());
            jsonObject.put("noiDungGopY",edtNdGopY.getText().toString());
            jsonObject.put("userId", MainActivity.userId);
            jsonObject.put("nguoiGui",MainActivity.userName);
            jsonObject.put("donVi",a.get(position).getCoQuan());
            boolean daXL = true;
            jsonObject.put("statusHoanThanh", daXL);

            Log.e("Gopy", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(mediaType,jsonObject.toString());
            Request request = new Request.Builder()
                    .url("http://"+Path.LOCALHOST+"/api/tracuuvanban/postGopYVanBan")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            trangThaiXL = true;

            Log.d("AAAA",response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
