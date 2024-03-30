package com.mspawar.instaclone.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mspawar.instaclone.fragment.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter {
    private  int behavior;
    private ProfileFragment PageFragment;
    private List<Fragment> fragmentsList;
    private List<String> titleList;


    public ViewPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragmentsList=new ArrayList<Fragment>();
        titleList=new ArrayList<String>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);

    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    public String getPageTitle(int position)
    {
        return titleList.get(position);
    }
    public void addFragment(Fragment fragment,String title)
    {
        fragmentsList.add(fragment);
        titleList.add(title);
    }
}
