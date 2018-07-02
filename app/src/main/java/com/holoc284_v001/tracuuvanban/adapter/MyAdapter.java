package com.holoc284_v001.tracuuvanban.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.holoc284_v001.tracuuvanban.fragment.FragmentChuyenXuLy;
import com.holoc284_v001.tracuuvanban.fragment.FragmentThongTinVanBan;
import com.holoc284_v001.tracuuvanban.fragment.FragmentXuLyVanBan;

/**
 * Created by holoc on 8/5/2017.
 */

public class MyAdapter extends FragmentStatePagerAdapter{
    String ten[] = {"FragmentThongTinVanBan","Fragment 2", "Fragmment 3"};
    //int hinh[] = {R.drawable.search,R.drawable.send};
    FragmentThongTinVanBan fragmentThongTinVanBan;

    FragmentXuLyVanBan fragmentXuLyVanBan;

    FragmentChuyenXuLy fragmentChuyenXuLy;

    public MyAdapter(FragmentManager fm) {
        super(fm);
        fragmentThongTinVanBan = new FragmentThongTinVanBan();
        fragmentXuLyVanBan = new FragmentXuLyVanBan();
        fragmentChuyenXuLy = new FragmentChuyenXuLy();
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            //fragmentThongTinVanBan = new FragmentThongTinVanBan();
            return fragmentThongTinVanBan;
        }else if (position==1){
            return fragmentXuLyVanBan;
        }else if (position==2){
            return fragmentChuyenXuLy;
        }
        return null;
    }

    @Override
    public int getCount() {
        return ten.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title="";

        switch (position){
            case 0:
                title = "Thông tin văn bản";
                break;
            case 1:
                title = "Góp ý";
                break;
            case 2:
                title = "Chuyển xử lý";
                break;
        }
        return title;
    }
}
