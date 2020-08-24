package com.ayoubamrani.colocationfacile;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentsList=new ArrayList<Fragment>();
    private ArrayList<String> fragmentsListTitles=new ArrayList<String>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }


    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
        //return fragmentsListTitles.get(position);
    }

    public void addFragment(Fragment fragment, String title)
    {
        fragmentsList.add(fragment);
        fragmentsListTitles.add(title);
    }
}
