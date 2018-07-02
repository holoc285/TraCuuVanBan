package com.holoc284_v001.tracuuvanban.login.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.holoc284_v001.tracuuvanban.R;
import com.holoc284_v001.tracuuvanban.activity.MainActivity;
import com.holoc284_v001.tracuuvanban.login.presenter.LoginPresenter;
import com.holoc284_v001.tracuuvanban.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ViewLogin{

    Button btnLogin;
    EditText edtUserName;
    EditText edtPassword;
    LoginPresenter loginPresenter;
    View viewCauHinh;
    public static String domain;
    public static TextView text;
    public static SharedPreferences domainCH;
    public static EditText editText;
    ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        
        if (isConnected()){


            //checkNetworkStatus();
            pg = new ProgressDialog(this);
            pg.setMessage("Vui lòng đợi ...");
            //pg.show();
            domainCH = getSharedPreferences("domain",MODE_PRIVATE);

            domain = domainCH.getString("domainDonVi","");


            viewCauHinh = findViewById(R.id.imageViewCauHinh);
            viewCauHinh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(LoginActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                    diaLogCauHinh();
                }
            });

            //Toast.makeText(this, domain, Toast.LENGTH_SHORT).show();
            loginPresenter = new LoginPresenter(this, this, domain.trim());
            loginPresenter.noLogin();

            btnLogin = (Button) findViewById(R.id.btnLogin);
            edtUserName = (EditText) findViewById(R.id.edtUser);
            edtPassword = (EditText) findViewById(R.id.edtPassword);

            btnLogin.setOnClickListener(this);
        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Không có kết nối Internet \n Vui lòng kiểm tra kết nối và thử lại!!!");
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
    /*
    Kiem tra ket noi internet
     */
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {

        String userName = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        //Toast.makeText(this, userName+password, Toast.LENGTH_SHORT).show();
        String passwordSecure = StringToSHA1(password).trim();
        if (userName.isEmpty() && password.isEmpty()){
            Toast.makeText(this, "Bạn phải nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }else {
            if (domainCH.getString("domainDonVi","").isEmpty()){
                Toast.makeText(this, "Bạn cần phải cấu hình domain trước khi đăng nhập!", Toast.LENGTH_SHORT).show();
            }
            else{
                domain = domainCH.getString("domainDonVi","");
                //loginPresenter = new LoginPresenter(this, this, domain.trim());
                //new LoginModel(this);
                    pg.show();
//                try {
//                    pg.show();
//                    //Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                //new LoginPresenter(this, this, domain.trim()).checkLogin(userName, passwordSecure);
                //loginPresenter.noLogin();
                loginPresenter.checkLogin(userName, passwordSecure);
                pg.dismiss();
            }
        }
//        if (domainCH.getString("domainDonVi","").isEmpty()){
//            Toast.makeText(this, "Bạn cần phải cấu hình domain trước khi đăng nhập!", Toast.LENGTH_SHORT).show();
//        }

    }

    private boolean checkNetworkStatus(){
        boolean status = false;
        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {

            // notify user you are online
            status = true;
            Toast.makeText(this, "Online", Toast.LENGTH_SHORT).show();

        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            // notify user you are not online
            status = false;
            Toast.makeText(this, "Online", Toast.LENGTH_SHORT).show();
        }
        return status;
    }

    public static String StringToSHA1(String textToHash) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String encoded = Base64.encodeToString(digest.digest(textToHash.getBytes(StandardCharsets.UTF_8)),0);
        return encoded;
    }

    private void diaLogCauHinh(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_cauhinh);
        editText = dialog.findViewById(R.id.editTextCauHinh);
        Button btnXacNhan = dialog.findViewById(R.id.buttonXacNhanCauHinh);
        Button btnHuy = dialog.findViewById(R.id.buttonHuyCauHinh);

        editText.setText(domainCH.getString("domainDonVi",""));

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editText.getText().toString().trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập vào địa chỉ domain cấu hình!", Toast.LENGTH_SHORT).show();
                }else {
                    String domainUpdate = editText.getText().toString().trim();
                    SharedPreferences.Editor editor = domainCH.edit();
                    editor.putString("domainDonVi", domainUpdate);
                    editor.commit();
                    domain = domainCH.getString("domainDonVi","");
                    Toast.makeText(LoginActivity.this, "Cấu hình thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    domain = domainCH.getString("domainDonVi","");
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }

    @Override
    public void loginSuccess(int userId, String userName) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Utils.KEY_INTENT_USER_ID, userId);
        intent.putExtra(Utils.KEY_INTENT_USER_NAME, userName);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFail() {
        Toast.makeText(this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
    }

}
