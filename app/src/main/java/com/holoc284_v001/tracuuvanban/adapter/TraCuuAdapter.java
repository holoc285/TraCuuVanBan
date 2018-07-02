package com.holoc284_v001.tracuuvanban.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.holoc284_v001.tracuuvanban.R;
import com.holoc284_v001.tracuuvanban.model.TraCuu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by holoc on 5/22/2018.
 */

public class TraCuuAdapter extends BaseAdapter {

    Context context;
    List<TraCuu> traCuuList;
    private ArrayList<TraCuu> arraylistOrigin=null;

    public TraCuuAdapter() {
    }

    public TraCuuAdapter(Context context, List<TraCuu> traCuuList) {
        this.context = context;
        this.traCuuList = traCuuList;
        this.arraylistOrigin = new ArrayList<TraCuu>();
        this.arraylistOrigin.addAll(traCuuList);
    }

    @Override
    public int getCount() {
        return traCuuList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_listview,null);
        TextView txtMaVB = view.findViewById(R.id.textViewMaVanBan);
        TextView txtNd = view.findViewById(R.id.textViewNd);
        TextView txtCoQuan = view.findViewById(R.id.textViewCoQuan);
        TextView txtNgayBanHanh = view.findViewById(R.id.textViewNgayBanHanh);
        ImageView imgHoanThanh = view.findViewById(R.id.imageViewHoanThanh);

        //imgHoanThanh.setVisibility(View.INVISIBLE);
        TraCuu traCuu = traCuuList.get(i);


        if (traCuu.isTrangThaiXuLy()){
            //imgHoanThanh.setVisibility(View.VISIBLE);
            imgHoanThanh.setImageResource(R.drawable.file);
            notifyDataSetChanged();
        }else {
            imgHoanThanh.setImageResource(R.drawable.file_un);
            //imgHoanThanh.setVisibility(View.INVISIBLE);
            //notifyDataSetChanged();
        }

        txtMaVB.setText("Số đến\n"+traCuu.getMaVB());
        //Set ...
        txtNd.setMaxLines(1);
        txtNd.setEllipsize(TextUtils.TruncateAt.END);
        txtNd.setText(traCuu.getNoiDungTrichYeu());
        txtCoQuan.setText("Đơn vị tiếp nhận: "+traCuu.getCoQuan());
        txtCoQuan.setMaxLines(1);
        txtCoQuan.setEllipsize(TextUtils.TruncateAt.END);
        txtNgayBanHanh.setText("Ngày ban hành: "+traCuu.getNgayBanHanh());

        return view;
    }

    public void filteredList(ArrayList<TraCuu> filteredList){
        traCuuList = filteredList;
        notifyDataSetChanged();
    }
    public void updateListView(){
        notifyDataSetChanged();
    }



}
