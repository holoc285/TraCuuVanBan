package com.holoc284_v001.tracuuvanban.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.holoc284_v001.tracuuvanban.R;
import com.holoc284_v001.tracuuvanban.adapter.ConnectivityReceiver;
import com.holoc284_v001.tracuuvanban.adapter.MyApplication;
import com.holoc284_v001.tracuuvanban.adapter.NguoiNhanAdapter;
import com.holoc284_v001.tracuuvanban.model.NguoiNhan;
import com.holoc284_v001.tracuuvanban.model.User;
import com.holoc284_v001.tracuuvanban.sqlite.SQLiteQueryUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.holoc284_v001.tracuuvanban.activity.MainActivity.userName;

public class DanhSachUserActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    List<User> listUser;
    ArrayList<NguoiNhan> list;
    JSONArray jsonArrayDsUserIdDaChon;
    ArrayList<NguoiNhan> nhanArrayList;
    ArrayList<NguoiNhan> danhSachNhanDaChon;
    ListView listView;
    CheckBox checkBoxAll;
    Button btnOk;
    Button btnHuy;
    ArrayList<String> dsUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_user);

        listView = (ListView) findViewById(R.id.listDanhSachNguoiNhan2);
        checkBoxAll = (CheckBox) findViewById(R.id.checkboxAllNguoiNhan2);
        btnOk = (Button) findViewById(R.id.buttonOk2);
        btnHuy = (Button) findViewById(R.id.buttonHuy2);

        SQLiteQueryUser sqLiteQueryUser = SQLiteQueryUser.getINSTANCE(this);

        listUser = sqLiteQueryUser.getUserID(userName);

        //SET DEFAULT UNCHECKED
        nhanArrayList = getNguoiNhan(false);
        danhSachNhanDaChon = new ArrayList<NguoiNhan>();


       // Toast.makeText(getApplicationContext(), listUser.get(0).getName(), Toast.LENGTH_SHORT).show();
        NguoiNhanAdapter adapter = new NguoiNhanAdapter(this,nhanArrayList);
        listView.setAdapter(adapter);

//        findViewById(R.id.buttonTest).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), DanhSachVBActivity.class);
//
//                intent.putExtra("test","testfinish");
//                //startActivityForResult(intent,1000);
//                setResult(1000,intent);
//                finish();
//            }
//        });
        getDanhSachNguoiNhan();
    }

    private void getDanhSachNguoiNhan(){
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DanhSachVBActivity.class);
                intent.putExtra("jsonUser","No");
                setResult(1000,intent);
                finish();
            }
        });

        checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //IF CHECKBOX IS CHECKED
                checkConnection();
                if (isChecked){
                    nhanArrayList = getNguoiNhan(true);
                    NguoiNhanAdapter adapter = new NguoiNhanAdapter(getApplicationContext(),nhanArrayList);
                    listView.setAdapter(adapter);

                }
                //CHECKBOX IS NOT CHECKED
                else {
                    nhanArrayList = getNguoiNhan(false);
                    NguoiNhanAdapter adapter = new NguoiNhanAdapter(getApplicationContext(),nhanArrayList);
                    listView.setAdapter(adapter);
                }
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Vui lòng chọn người nhận luân chuyển", Toast.LENGTH_SHORT).show();
                checkConnection();
                danhSachNhanDaChon.clear();
               AlertDialog.Builder dialog1 = new AlertDialog.Builder(DanhSachUserActivity.this);
                dialog1.setMessage("Vui lòng chọn người nhận luân chuyển");
                dialog1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });



                //AlertDialog.Builder al = new AlertDialog.Builder(getApplicationContext());

                //dialog1.show();
                AlertDialog dialog2 = dialog1.create();
                //dialog2.show();

                for (int i=0;i< list.size();i++){

                    if (getNguoiNhan(false).get(i).isSeleted()==false) {
                        //Toast.makeText(getActivity(), "Vui lòng chọn người nhận luân chuyển", Toast.LENGTH_SHORT).show();
                        dialog2.show();
                        break;
                    }

                }


                //Lay danh sach nguoi da checked
                for (int i=0;i< nhanArrayList.size();i++){
                    if (nhanArrayList.get(i).isSeleted()){
                        dialog2.dismiss();
                        //dialog.dismiss();
                        //Toast.makeText(getActivity(), nhanArrayList.get(i).getEmail(), Toast.LENGTH_SHORT).show();
                        //danhSachNhan.add(nhanArrayList.get(i).getEmail());


                        danhSachNhanDaChon.add(new NguoiNhan(nhanArrayList.get(i).getUserId(),nhanArrayList.get(i).getEmail()));
                        //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getActivity(),danhSachNhanDaChon.get(0).getEmail(), Toast.LENGTH_SHORT).show();

                    }
                }
                jsonArrayDsUserIdDaChon = new JSONArray();
                dsUser = new ArrayList<String>();

                for (int i=0;i<danhSachNhanDaChon.size();i++){
                    JSONObject js = new JSONObject();
                    //Set text max 2 dong
                    //txtUserNhanLuanChuyen.setText("Đã chọn");
                    //txtUserNhanLuanChuyen.setMaxLines(2);
                    //txtUserNhanLuanChuyen.setEllipsize(TextUtils.TruncateAt.END);
                    //txtUserNhanLuanChuyen.append(danhSachNhanDaChon.get(i).getEmail() + " - ");
                    try {
                        js.put("user",danhSachNhanDaChon.get(i).getUserId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArrayDsUserIdDaChon.put(js);
                    dsUser.add(danhSachNhanDaChon.get(i).getEmail());

                }

                if (jsonArrayDsUserIdDaChon.length()==0){

                }else {
                    Intent intent = new Intent(getApplicationContext(), DanhSachVBActivity.class);

                    intent.putExtra("jsonUser",jsonArrayDsUserIdDaChon.toString());
                    intent.putExtra("json", dsUser);
                    //intent.putExtra("dsUser", dsUser);
                    setResult(1000,intent);
                    finish();
                }
                //Toast.makeText(getApplicationContext(),jsonArrayDsUserIdDaChon.toString(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private ArrayList<NguoiNhan> getNguoiNhan(boolean isSelect){
        list = new ArrayList<>();
        SQLiteQueryUser sqLiteQueryUser = SQLiteQueryUser.getINSTANCE(this);

        listUser = sqLiteQueryUser.getAllUser();
        for (int i=1; i< listUser.size();i++){
            NguoiNhan nguoiNhan = new NguoiNhan();
            nguoiNhan.setSeleted(isSelect);
            nguoiNhan.setEmail(listUser.get(i).getName());
            nguoiNhan.setUserId(listUser.get(i).getUserId());
            list.add(nguoiNhan);
        }
        return list;
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        Snackbar snackbar;
        if (isConnected) {
            message = "Đã kết nối Internet";
            color = Color.WHITE;
            snackbar = Snackbar
                    .make(findViewById(R.id.fab), message, 4000);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.dismiss();
        } else {
            message = "Không có kết nối Internet!";
            color = Color.YELLOW;
            snackbar = Snackbar
                    .make(findViewById(R.id.fab), message, 4000);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();
        }

//        View sbView = snackbar.getView();
//        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(color);
//        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
