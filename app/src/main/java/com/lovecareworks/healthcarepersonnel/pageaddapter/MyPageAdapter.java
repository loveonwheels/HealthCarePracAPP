package com.lovecareworks.healthcarepersonnel.pageaddapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MyPageAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    List<Fragment> list = new ArrayList<Fragment>();



    public MyPageAdapter(FragmentManager fragmentManager,List<Fragment> fraglist) {
        super(fragmentManager);
    list = fraglist;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return list.size();
    }

    // Returns the fragment to display for that page


    public void addfragment(Fragment newfragement){
    list.add(newfragement);
     }


        @Override
    public Fragment getItem(int position) {
            return list.get(position);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Request " + (position+1)+"/"+String.valueOf(list.size());
    }


}