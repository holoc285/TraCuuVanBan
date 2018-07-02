package com.holoc284_v001.tracuuvanban.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.holoc284_v001.tracuuvanban.activity.MainActivity;
import com.holoc284_v001.tracuuvanban.R;

import java.util.ArrayList;

/**
 * Created by holoc on 5/21/2018.
 */

public class FragmentBieuDo extends Fragment {

    ArrayList<Entry> yvalues;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //getThongKe();
        //getTK();
        View view  = inflater.inflate(R.layout.fragment_bieudo,container,false);
        MainActivity activity = (MainActivity) getActivity();


        if (activity.tongVb()!=null && activity.daXuLy()!=null && activity.chuaXuLy()!=null){

            int tong = Integer.parseInt(activity.tongVb());
            int dxl = Integer.parseInt(activity.daXuLy());
            int cxl = Integer.parseInt(activity.chuaXuLy());
            float d = (float) dxl / tong * 100;
            float c = (float) cxl / tong * 100;
            PieChart pieChart = view.findViewById(R.id.myPieChart);
            pieChart.setUsePercentValues(true);
            yvalues = new ArrayList<Entry>();
            yvalues.add(new Entry(d, 0));
            yvalues.add(new Entry(c, 1));

            PieDataSet dataSet = new PieDataSet(yvalues, "Tổng số văn bản "+ activity.tongVb());

            ArrayList<String> xVals = new ArrayList<>();

            xVals.add("Đã xử lý " + activity.daXuLy());
            xVals.add("Chưa xử lý " + activity.chuaXuLy());

            PieData data = new PieData(xVals, dataSet);

            data.setValueFormatter(new PercentFormatter());

            pieChart.setData(data);

            dataSet.setColors(new int[]{Color.GREEN,Color.YELLOW});

            pieChart.setDescription("Thống kê văn bản đến");
            pieChart.setDrawHoleEnabled(false);
            data.setValueTextSize(15f);
        }
        //Toast.makeText(getActivity(),ac.tongVb(), Toast.LENGTH_SHORT).show();
        return view;
    }

//    public void getTK(){
//        String url = "http://"+ Path.LOCALHOST+"/api/hoso/get";
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if(response!=null) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        tongvb = jsonObject.getString("tongsovb").replace("[", "").replace("]", "");
//                        daxuly = jsonObject.getString("daxuly").replace("[", "").replace("]", "");
//                        chuaxuly = jsonObject.getString("chuaxuly").replace("[", "").replace("]", "");
//
//                        int tong = Integer.parseInt(tongvb);
//                        int dxl = Integer.parseInt(daxuly);
//                        int cxl = Integer.parseInt(chuaxuly);
//                        d = (float) dxl / tong * 100;
//                        c = (float) cxl / tong * 100;
//                        //yvalues.add(new Entry(d,0));
//                        //yvalues.add(new Entry(c,1));
//                        Toast.makeText(getActivity(), d + " " + c, Toast.LENGTH_SHORT).show();
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    //progressDialog.dismiss();
//                }else {
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        requestQueue.add(stringRequest);
//    }
}
