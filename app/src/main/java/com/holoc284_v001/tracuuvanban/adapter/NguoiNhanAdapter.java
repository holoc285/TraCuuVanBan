package com.holoc284_v001.tracuuvanban.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.holoc284_v001.tracuuvanban.R;
import com.holoc284_v001.tracuuvanban.model.NguoiNhan;

import java.util.List;

/**
 * Created by holoc on 6/12/2018.
 */

public class NguoiNhanAdapter extends BaseAdapter {

    Context context;
    List<NguoiNhan> nguoiNhanList;
     CheckBox checkBox;

    public NguoiNhanAdapter(Context context, List<NguoiNhan> nguoiNhanList) {
        this.context = context;
        this.nguoiNhanList = nguoiNhanList;
    }

    @Override
    public int getCount() {
        return nguoiNhanList.size();
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

        view = inflater.inflate(R.layout.item_nguoinhan_dialog, null);
        TextView txtNguoiNhan = view.findViewById(R.id.textViewNguoiNhan);
        //ImageView imgCheck = view.findViewById(R.id.imageViewCheck);
        checkBox = view.findViewById(R.id.checkboxNguoiNhan);


        final NguoiNhan nguoiNhan = nguoiNhanList.get(i);

        txtNguoiNhan.setText(nguoiNhan.getEmail());
        checkBox.setChecked(nguoiNhan.isSeleted());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nguoiNhan.isSeleted()){
                    nguoiNhan.setSeleted(false);
                }else {
                    nguoiNhan.setSeleted(true);
                }
            }
        });
       //imgCheck.setImageResource(nguoiNhan.getStatusCheck());

        return view;
    }
}
