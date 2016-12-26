package com.sajorahasan.shoppy.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sajorahasan.shoppy.R;

/**
 * Created by apple on 18/03/16.
 */
public class TabPaymentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_payment, container, false);
    }
}