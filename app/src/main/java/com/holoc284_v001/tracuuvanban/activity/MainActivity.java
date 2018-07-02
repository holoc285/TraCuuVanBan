package com.holoc284_v001.tracuuvanban.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.holoc284_v001.tracuuvanban.adapter.ConnectivityReceiver;
import com.holoc284_v001.tracuuvanban.adapter.MyApplication;
import com.holoc284_v001.tracuuvanban.R;
import com.holoc284_v001.tracuuvanban.fragment.FragmentBieuDo;
import com.holoc284_v001.tracuuvanban.fragment.FragmentInfo;
import com.holoc284_v001.tracuuvanban.fragment.FragmentTraCuu;
import com.holoc284_v001.tracuuvanban.fragment.FragmentVanBanDaXuLy;
import com.holoc284_v001.tracuuvanban.login.view.LoginActivity;
import com.holoc284_v001.tracuuvanban.model.User;
import com.holoc284_v001.tracuuvanban.sqlite.SQLiteQueryUser;
import com.holoc284_v001.tracuuvanban.utils.Path;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    public static String text;
    SearchView searchView;
    MenuItem menuItem;
    TextView txtUser, txtName, txtAddress;
    public static String userName;
    public static int userId;
    public static Context context;
    public static Activity activity;

    Fragment fragment = null;

    public String tongvb=null;
    public String daxuly=null;
    public String chuaxuly = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isConnected()){

            checkConnection();
            context = getApplicationContext();

            activity = this;

            getTK();

            //Toast.makeText(this, Path.LOCALHOST, Toast.LENGTH_SHORT).show();

            loadFragment(new FragmentTraCuu());

            drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            navigationView = (NavigationView) findViewById(R.id.navigationViewTTKH);
            toolbar = (Toolbar) findViewById(R.id.toolBarC);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.myBTN);


            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.danhSachVB:
                            getTK();
                            checkConnection();
                            fragment = new FragmentTraCuu();
                            if (menuItem.isVisible()!= true) {
                                menuItem.setVisible(true);
                            }
                            break;
                        case R.id.traCuu:
                            checkConnection();
                            fragment = new FragmentVanBanDaXuLy();
                            if (menuItem.isVisible()!= true) {
                                menuItem.setVisible(true);
                            }
                            break;
                        case R.id.bieuDo:
                            checkConnection();
                            getTK();
                            fragment = new FragmentBieuDo();
                            menuItem.setVisible(false);
                            break;
//                    case user:
//                        fragment = new fragmentUser();
//                        menuItem.setVisible(false);
//                        break;
                        case R.id.info:
                            checkConnection();
                            fragment = new FragmentInfo();
                            menuItem.setVisible(false);
                            break;
                    }
                    return loadFragment(fragment);
                }
            });

            getInfoUser();
            daXuLy();
            tongVb();
            chuaXuLy();

        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Không có kết nối Internet, Vui lòng kiểm tra kết nối và thử lại!!!");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startActivity(startMain);
                    finish();
                }
            });
            Dialog d = dialog.create();
            d.show();
            d.setCanceledOnTouchOutside(false);
        }
    }


    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void getInfoUser(){

        userName = getIntent().getStringExtra("userName");

        userId = getIntent().getIntExtra("userID", 0);

        txtUser = (TextView) findViewById(R.id.textViewUserNav);
        txtName = (TextView) findViewById(R.id.textViewDiaChiNav);
        txtAddress = (TextView) findViewById(R.id.textViewPhoneNav);

        Button btnExit = (Button) findViewById(R.id.buttonExit);


        final SQLiteQueryUser sqLiteQueryUser = SQLiteQueryUser.getINSTANCE(this);

        List<User> users = sqLiteQueryUser.getUserID(userName);
        txtUser.setText(users.get(0).getUserName());
        txtName.setText(users.get(0).getName());
        txtAddress.setText(users.get(0).getEmail());

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqLiteQueryUser.delete();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        menuItem = menu.findItem(R.id.action_search);
        //menuItem.setVisible(true);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint("Tìm kiếm văn bản đến");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //FragmentTraCuu.filter(newText);
                if (FragmentTraCuu.arrayListTraCuu !=null){
                    FragmentTraCuu.filterL(newText);
                }
                if (FragmentVanBanDaXuLy.arrayListTraCuu !=null){
                    FragmentVanBanDaXuLy.filterL(newText);
                }
                return true;
            }

        });
        return true;
    }


    public boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getFragmentManager()//
                    .beginTransaction()
                    .replace(R.id.frameContent,fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public String tongVb(){
        return tongvb;
    }
    public String daXuLy(){
        return daxuly;
    }
    public String chuaXuLy(){
        return chuaxuly;
    }

    public void getTK(){
        String url = "http://"+Path.LOCALHOST+"/api/tracuuvanban/thongke";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        tongvb = jsonObject.getString("tongsovb").replace("[", "").replace("]", "");
                        daxuly = jsonObject.getString("daxuly").replace("[", "").replace("]", "");
                        chuaxuly = jsonObject.getString("chuaxuly").replace("[", "").replace("]", "");

                        int tong = Integer.parseInt(tongvb);
                        int dxl = Integer.parseInt(daxuly);
                        int cxl = Integer.parseInt(chuaxuly);
                        float d = (float) dxl / tong * 100;
                        float c = (float) cxl / tong * 100;
                        //yvalues.add(new Entry(d,0));
                        //yvalues.add(new Entry(c,1));
                        //Toast.makeText(MainActivity.this, d + " " + c, Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //progressDialog.dismiss();
                }else {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
    }





    // Method to manually check connection status
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
