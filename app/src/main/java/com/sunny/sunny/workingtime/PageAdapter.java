package com.sunny.sunny.workingtime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Sunny on 26/11/2017.
 */

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                DetailsFragment detailsFragment = new DetailsFragment();
                return detailsFragment;

            case 1:
                ClockFragment clockFragment = new ClockFragment();
                return  clockFragment;

            case 2:
                MyHoursFragment myHoursFragment = new MyHoursFragment();
                return myHoursFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
