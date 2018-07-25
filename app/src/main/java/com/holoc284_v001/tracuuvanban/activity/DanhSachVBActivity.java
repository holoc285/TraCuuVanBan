package com.holoc284_v001.tracuuvanban.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.holoc284_v001.tracuuvanban.R;
import com.holoc284_v001.tracuuvanban.adapter.AdapterVBDaXuLy;
import com.holoc284_v001.tracuuvanban.adapter.ConnectivityReceiver;
import com.holoc284_v001.tracuuvanban.adapter.MyAdapter;
import com.holoc284_v001.tracuuvanban.adapter.MyApplication;
import com.holoc284_v001.tracuuvanban.model.TraCuu;
import com.holoc284_v001.tracuuvanban.model.User;
import com.holoc284_v001.tracuuvanban.sqlite.SQLiteQueryUser;

import java.util.ArrayList;
import java.util.List;

import static com.holoc284_v001.tracuuvanban.activity.MainActivity.userName;

public class DanhSachVBActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

        ViewPager viewPager;
        TabLayout tabLayout;
        public static String res;
        ArrayList<TraCuu> a;
        public static Context context;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_danh_sach_vb);
            context = getApplicationContext();
            getArray();
            if (a.get(getPosition()).isTrangThaiXuLy()){
                //Van Ban Da Xu Ly
                initViewDaXuLy();
            }else {
                initViewChuaXuLy();
                //FragmentThongTinVanBan.txtTapTinDinhKem.setText("Loại văn bản: "+ a.get(getPosition()).getLoaiVanBan());
            }

        }
        private void initViewChuaXuLy() {
            viewPager = (ViewPager) findViewById(R.id.viewPager);
            tabLayout = (TabLayout) findViewById(R.id.tabLayout);
            viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
            tabLayout.setupWithViewPager(viewPager);
        }

        private void initViewDaXuLy() {
            viewPager = (ViewPager) findViewById(R.id.viewPager);
            tabLayout = (TabLayout) findViewById(R.id.tabLayout);
            viewPager.setAdapter(new AdapterVBDaXuLy(getSupportFragmentManager()));
            tabLayout.setupWithViewPager(viewPager);
        }
        public ArrayList<TraCuu> getArray(){
            Bundle bundle = getIntent().getExtras();
            //int i = bundle.getInt("ViTri");
            a = (ArrayList) bundle.getSerializable("VanBan");

            return a;
        }



        public int getPosition(){
            Bundle bundle = getIntent().getExtras();
            int i = bundle.getInt("ViTri");
            return i;
        }

        public List getUsers(){
            final SQLiteQueryUser sqLiteQueryUser = SQLiteQueryUser.getINSTANCE(this);

            List<User> users = sqLiteQueryUser.getUserID(userName);
            //txtUser.setText(users.get(0).getUserName());
            //txtName.setText(users.get(0).getName());
            //txtAddress.setText(users.get(0).getEmail());
            return users;
        }

    public void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    public boolean isConnected(){
        boolean isConnected = ConnectivityReceiver.isConnected();
        return isConnected;
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
