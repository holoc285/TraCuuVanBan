package com.holoc284_v001.tracuuvanban.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.holoc284_v001.tracuuvanban.activity.DanhSachVBActivity;
import com.holoc284_v001.tracuuvanban.activity.MainActivity;
import com.holoc284_v001.tracuuvanban.R;
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
public class FragmentVanBanDaXuLy extends Fragment {
    public static ArrayList<TraCuu> arrayListTraCuu;
    public static TraCuuAdapter adapterTraCuu;
    ListView listView;
    TextView txtThongBao;

    int page = 1;
    View footerview;
    boolean isLoading = false;
    mHandler mhandler;
    boolean limitData = false;
    ProgressDialog progressDialog;

    int pos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracuu,container,false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Vui lòng đợi ...");
        progressDialog.show();

        arrayListTraCuu = new ArrayList();
        listView = view.findViewById(R.id.listViewTraCuu);
        adapterTraCuu = new TraCuuAdapter(getActivity(),arrayListTraCuu);

        listView.setAdapter(adapterTraCuu);
        getDanhSachVanBanDen(page);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                pos = position;
                Intent intent = new Intent(getActivity(), DanhSachVBActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ViTri", position);
                bundle.putSerializable("VanBan", arrayListTraCuu);
                intent.putExtras(bundle);
                startActivity(intent);
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
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount !=0 && isLoading == false &&limitData == false && arrayListTraCuu.size() >=5){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });

        //Set Thong Bao ListView Null Hay Khong
        txtThongBao = view.findViewById(R.id.textViewThongBaoNull);
        txtThongBao.setVisibility(View.INVISIBLE);
        return view;
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
                    getDanhSachVanBanDen(++page);
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

    private void getDanhSachVanBanDen (int page){
        String url = "http://"+Path.LOCALHOST+"/api/tracuuvanban/danhSachVbDaXL/"+page+"/"+ MainActivity.userId;
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

                            int vanBanDenId = jsonObject.getInt("vbden_id");

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
                            boolean hoanThanh = jsonObject.getBoolean("vbden_hoanthanh");

                            String noiDungTrichYeu = jsonObject.getString("vbden_trichyeu");

//                        String donViTiepNhan = jsonObject.getString("cunght_organizationten");
                            arrayListTraCuu.add(new TraCuu(vanBanDenId,soDen,soHieuGoc+" "+noiDungTrichYeu,coQuanBanHanh,Day(ngayBanHanh),Day(ngayHieuLuc),nguoiGui,loaiVanBan,hanXlToanVanBan,yKienButPhe,true));
                            //Toast.makeText(getActivity(), arrayListTraCuu.get(1).getNoiDungTrichYeu(), Toast.LENGTH_SHORT).show();
                        }
                        adapterTraCuu.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();

                }else {
                    progressDialog.dismiss();
                    limitData = true;
                    listView.removeFooterView(footerview);
                    //Toast.makeText(getActivity(), "Đã hết dữ liệu!", Toast.LENGTH_SHORT).show();
                    dialogThongBao();
                    adapterTraCuu.notifyDataSetChanged();
                }

                if (arrayListTraCuu.size()==0){
                    txtThongBao.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
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

        dialog.create().show();
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
