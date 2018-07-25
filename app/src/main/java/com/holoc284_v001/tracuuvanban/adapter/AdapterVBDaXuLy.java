package com.holoc284_v001.tracuuvanban.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.holoc284_v001.tracuuvanban.fragment.FragmentThongTinVanBan;
import com.holoc284_v001.tracuuvanban.fragment.FragmentXuLyVanBan;

/**
 * Created by holoc on 8/5/2017.
 */

public class AdapterVBDaXuLy extends FragmentStatePagerAdapter{
    String ten[] = {"Thông tin văn bản","Góp ý"};
    FragmentThongTinVanBan fragmentThongTinVanBan;
    FragmentXuLyVanBan fragmentXuLyVanBan;
//    FragmentChuyenXuLy fragmentChuyenXuLy;

    public AdapterVBDaXuLy(FragmentManager fm) {
        super(fm);
        fragmentThongTinVanBan = new FragmentThongTinVanBan();
        fragmentXuLyVanBan = new FragmentXuLyVanBan();
//        fragmentChuyenXuLy = new FragmentChuyenXuLy();

    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            //fragmentThongTinVanBan = new FragmentThongTinVanBan();
            return fragmentThongTinVanBan;
        }else if (position==1){
            return fragmentXuLyVanBan;
//        }else if (position==2){
//            return fragmentChuyenXuLy;
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
                title = ten[0];
                break;
            case 1:
                title = ten[1];
                break;
//            case 2:
//                title = "Chuyển xử lý";
//                break;
        }
        return title;
    }
}
