package com.sajorahasan.shoppy;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sajorahasan.shoppy.model.Pojo;
import com.sajorahasan.shoppy.service.HttpRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {
    private static final String TAG = "ProductDetailActivity";

    private String pro_id, image, name, price, qty, desc;
    static int prod_id;

    private ImageView pro_image;
    private TextView pro_name, pro_price, pro_qty, pro_desc, addToCart;

    private ArrayList<Pojo> pojoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        //Initializing views
        pro_image = (ImageView) findViewById(R.id.ivProduct);
        pro_name = (TextView) findViewById(R.id.tvProductName);
        pro_qty = (TextView) findViewById(R.id.tvProductQuantity);
        pro_price = (TextView) findViewById(R.id.tvProductPrice);
        pro_desc = (TextView) findViewById(R.id.tvDescription);
        addToCart = (TextView) findViewById(R.id.addToCart);

        pojoArrayList = new ArrayList<Pojo>();

        //fetching product id
        pro_id = getIntent().getStringExtra("product_id");

        prod_id = Integer.parseInt(pro_id);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_container, new CartFragment());
//                ft.addToBackStack(null);
//                ft.commit();

            }
        });

        new loadData().execute();
    }


    private class loadData extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {

            // Menu detail API url
            String MenuDetailAPI = Constants.ProductDetailAPI + "?accesskey=" + Constants.AccessKey + "&menu_id=" + prod_id;
            Log.d(TAG, "onPostExecute: product id " + pro_id);

            try {
                HttpRequest req = new HttpRequest(Constants.BASE_URL_APP + MenuDetailAPI);

                Log.d(TAG, "doInBackground: url " + MenuDetailAPI);

                JSONObject jObject = req.prepare(HttpRequest.Method.POST).sendAndReadJSON();

                JSONArray jArray = jObject.getJSONArray("data");

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jsonObject = jArray.getJSONObject(i);

                    JSONObject product = jsonObject.getJSONObject("Menu_detail");

                    prod_id = product.getInt("Menu_ID");
                    name = product.getString("Menu_name");
                    image = product.getString("Menu_image");
                    price = product.getString("Price");
                    desc = product.getString("Description");
                    qty = product.getString("Quantity");

                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return pojoArrayList;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            String url = Constants.AdminPageURL;
            Picasso.with(getApplicationContext())
                    .load(url + image)
                    .fit()
                    .into(pro_image);

            pro_name.setText(name);
            pro_price.setText(price);
            pro_qty.setText(qty);
            pro_desc.setText(desc);
        }

    }
}
