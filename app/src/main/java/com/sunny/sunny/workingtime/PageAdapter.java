package com.sunny.sunny.workingtime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Sunny on 26/11/2017.
 */

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PageAdapter(FragmentManager fm) {
        super(fm);
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
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        switch (position){

            case 0:
                title = "פרטים";
                break;

            case 1:
                title = "שעון נוכחות";
                break;

            case 2:
                title = "השעות שלי";
                break;
        }
        return title;
    }
}
