package com.sajorahasan.shoppy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sajorahasan.shoppy.tab.TabPaymentFragment;
import com.sajorahasan.shoppy.tab.TabReviewFragment;
import com.sajorahasan.shoppy.tab.TabShippingFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabShippingFragment tab1 = new TabShippingFragment();
                return tab1;
            case 1:
                TabReviewFragment tab2 = new TabReviewFragment();
                return tab2;
            case 2:
                TabPaymentFragment tab3 = new TabPaymentFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}