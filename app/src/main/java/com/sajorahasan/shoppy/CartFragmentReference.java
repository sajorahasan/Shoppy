//package com.sajorahasan.shoppy;
//
//
//import android.app.Fragment;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.sajorahasan.shoppy.adapter.CartAdapter;
//import com.sajorahasan.shoppy.model.Bean;
//import com.sajorahasan.shoppy.model.ShoppyBean;
//
//import java.util.ArrayList;
//
//import customfonts.MyTextView;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//import static com.sajorahasan.shoppy.Constants.BASE_URL;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class CartFragment extends Fragment {
//    private static final String TAG = "CartFragment";
//
//    private MyTextView contCheckout, cart_total, price_product, qty_product;
//    private RecyclerView mRecyclerView;
//    private String uId;
//    private SharedPreferences pref;
//    private CartAdapter adapter;
//    private ArrayList<Bean> myDataSource;
//
//
//    public CartFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_cart, container, false);
//
//        contCheckout = (MyTextView) view.findViewById(R.id.contCheckout);
//        cart_total = (MyTextView) view.findViewById(R.id.cart_total);
//
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.cart_recycler_view);
//        //mRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(layoutManager);
//
//        contCheckout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), PaymentActivity.class);
//                getActivity().startActivity(i);
//            }
//        });
//
//        //Creating a shared preference
//        pref = getActivity().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        uId = pref.getString(Constants.SNO, "");
//
//        loadCart();
//
//        return view;
//    }
//
//    private void loadCart() {
//        RequestInterface requestInterface = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build().create(RequestInterface.class);
//
//        Call<ShoppyBean> call = requestInterface.getCart(uId);
//        call.enqueue(new Callback<ShoppyBean>() {
//            @Override
//            public void onResponse(Call<ShoppyBean> call, Response<ShoppyBean> response) {
//                myDataSource = new ArrayList<>(response.body().getCart());
//                adapter = new CartAdapter(myDataSource, getActivity());
//                mRecyclerView.setAdapter(adapter);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ShoppyBean> call, Throwable t) {
//
//            }
//        });
//    }
//}
