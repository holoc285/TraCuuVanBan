package com.holoc284_v001.tracuuvanban.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.holoc284_v001.tracuuvanban.R;
import com.holoc284_v001.tracuuvanban.activity.DanhSachVBActivity;
import com.holoc284_v001.tracuuvanban.activity.MainActivity;
import com.holoc284_v001.tracuuvanban.adapter.TraCuuAdapter;
import com.holoc284_v001.tracuuvanban.model.TraCuu;
import com.holoc284_v001.tracuuvanban.utils.Path;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by holoc on 5/22/2018.
 */
public class FragmentTraCuu extends Fragment {
    public static ArrayList<TraCuu> arrayListTraCuu;
    public static TraCuuAdapter adapterTraCuu;
    private ListView listView;
    private TextView txtThongBao;
    private int page = 1;
    private View footerview;
    private boolean isLoading = false;
    private mHandler mhandler;
    private boolean limitData = false;
    private ProgressDialog progressDialog;
    private int pos;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracuu,container,false);

        txtThongBao = view.findViewById(R.id.textViewThongBaoNull);
        refreshLayout = view.findViewById(R.id.mySwipeRefresh);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Vui lòng đợi ...");
        progressDialog.show();
        //progressDialog.setCanceledOnTouchOutside(false );
        arrayListTraCuu = new ArrayList();
        listView = view.findViewById(R.id.listViewTraCuu);
        adapterTraCuu = new TraCuuAdapter(getActivity(),arrayListTraCuu);
        //setListAdapter(adapterTraCuu);
        listView.setAdapter(adapterTraCuu);
        getDanhSachVanBan(page);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                pos = position;
                Intent intent = new Intent(getActivity(), DanhSachVBActivity.class);
                //FragmentThongTinVanBan fragmentThongTinVanBan = new FragmentThongTinVanBan();
                Bundle bundle = new Bundle();

                bundle.putInt("ViTri", position);
                bundle.putSerializable("VanBan", arrayListTraCuu);

                //fragmentThongTinVanBan.setArguments(bundle);
                intent.putExtras(bundle);
                startActivityForResult(intent, 2000);
            }
        });

        LayoutInflater inf = getActivity().getLayoutInflater();
        footerview = inf.inflate(
                R.layout.progressbar, null);
        mhandler = new mHandler();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount !=0 && isLoading == false &&limitData == false && arrayListTraCuu.size() >=7){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
        txtThongBao.setVisibility(View.INVISIBLE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new CountDownTimer(3000, 1000){

                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
//                        arrayListTraCuu.clear();
//                        getDanhSachVanBan(page);
//                        adapterTraCuu.notifyDataSetChanged();
                        MainActivity activity = (MainActivity) getActivity();
                        activity.loadFragment(new FragmentTraCuu());
                        refreshLayout.setRefreshing(false);

                    }
                }.start();
            }
        });
        refreshLayout.setColorSchemeColors(Color.GREEN,Color.CYAN, Color.RED, Color.BLUE);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2000 && data != null){
            String e =  data.getStringExtra("result").toString();
            //Toast.makeText(getActivity(), "OKKE" + e, Toast.LENGTH_SHORT).show();
            Log.e("ddd","eeeee");
            MainActivity activity = (MainActivity) getActivity();
            activity.loadFragment(new FragmentTraCuu());
        }
    }


    public static void filterL(String charText) {
        ArrayList<TraCuu> filteredList = new ArrayList<>();

        for (TraCuu  traCuu: arrayListTraCuu){
            if (traCuu.getNoiDungTrichYeu().toLowerCase().contains(charText.toLowerCase()))
            {
                filteredList.add(traCuu);
            }
        }
        adapterTraCuu.filteredList(filteredList);
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listView.addFooterView(footerview);
                    break;
                case 1:
                    getDanhSachVanBan(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mhandler.sendEmptyMessage(0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mhandler.obtainMessage(1);
            mhandler.sendMessage(message);
            super.run();
        }
    }

    private void getDanhSachVanBan(int page){
        String url = "http://"+Path.LOCALHOST+"/api/tracuuvanban/danhSachVbChuaXL/"+page+"/"+ MainActivity.userId;
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                if (response!=null && response.length() !=2)
                {
                    listView.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //Toast.makeText(getActivity(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
                            int vanBanDenId = jsonObject.getInt("vbden_id");
                            //Toast.makeText(getActivity(), vanBanDenId+"", Toast.LENGTH_SHORT).show();
                            String soDen = jsonObject.getString("vbden_soden");
                            //Xy ly so den
                            soDen = soDen.replace("$","");
                            String soHieuGoc = jsonObject.getString("vbden_sohieugoc");
                            String ngayBanHanh = jsonObject.getString("vbden_ngaybanhanh");
                            String coQuanBanHanh = jsonObject.getString("vbden_coquanbanhanh");

                            String nguoiGui = jsonObject.getString("vbden_nguoinhap");
                            String loaiVanBan = jsonObject.getString("lvb_ten");
                            String ngayHieuLuc = jsonObject.getString("vbden_ngaycohl");

                            String hanXlToanVanBan = jsonObject.getString("vbden_hanxl_toanvanban");
                            String yKienButPhe = jsonObject.getString("vbden_butphe");
                            //boolean hoanThanh = jsonObject.getBoolean("vbden_hoanthanh");

                            String noiDungTrichYeu = jsonObject.getString("vbden_trichyeu");
                            int vbden_lc_id = jsonObject.getInt("vbden_lc_id");
                            int idCoQuan = jsonObject.getInt("organizationid");
                            arrayListTraCuu.add(new TraCuu(vanBanDenId,soDen,soHieuGoc+" "+noiDungTrichYeu,coQuanBanHanh,Day(ngayBanHanh),Day(ngayHieuLuc),nguoiGui,loaiVanBan,hanXlToanVanBan,yKienButPhe,vbden_lc_id,false, idCoQuan));
                        }
                        adapterTraCuu.notifyDataSetChanged();
                        progressDialog.cancel();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else {

                    limitData = true;
                    listView.removeFooterView(footerview);
                    if (arrayListTraCuu.size()> 0){
                        dialogThongBao();
                    }
                    adapterTraCuu.notifyDataSetChanged();
                    progressDialog.cancel();
                }
                if (arrayListTraCuu.size()==0){
                    txtThongBao.setVisibility(View.VISIBLE);
                    progressDialog.cancel();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void dialogThongBao(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.activity);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Đã hết dữ liệu");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dl = dialog.create();
        dl.show();
    }

    private String Day(String ngay){
        String ngayDaXuLy;
        if (ngay.equals("null")){
            ngayDaXuLy="";
            return ngayDaXuLy;
        }else {
            //Xu ly ngay co hieu luc
            String day = ngay.substring(0, 10);
            long l = Long.valueOf(day);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(l * 1000L);
            ngayDaXuLy = format.format(date);
            return ngayDaXuLy;
        }
    }

}
