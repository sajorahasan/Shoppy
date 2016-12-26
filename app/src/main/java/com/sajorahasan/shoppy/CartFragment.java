package com.sajorahasan.shoppy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import customfonts.MyTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private MyTextView contCheckout;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        contCheckout = (MyTextView) view.findViewById(R.id.contCheckout);

        contCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CheckoutActivity.class);
                getActivity().startActivity(i);
            }
        });


        return view;
    }

}
