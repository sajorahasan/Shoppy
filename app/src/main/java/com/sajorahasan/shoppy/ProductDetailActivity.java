package com.sajorahasan.shoppy;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sajorahasan.shoppy.model.Pojo;
import com.sajorahasan.shoppy.service.HttpRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ProductDetailActivity";

    private String pro_id, image, name, price, qty, desc;
    static int prod_id;

    private ImageView pro_image;
    private Button btnADD, btnSUB;
    private TextView pro_name, pro_price, pro_qty, pro_desc, addToCart, tvQty, tvCatName, tvProductTotal;
    int quantity = 1;
    int avlbl_qty = 1;
    private String total_Amount;
    private String uId;
    private SharedPreferences pref;

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
        tvCatName = (TextView) findViewById(R.id.tvCatName);
        tvQty = (TextView) findViewById(R.id.tvQty);
        tvProductTotal = (TextView) findViewById(R.id.tvProductTotal);
        btnADD = (Button) findViewById(R.id.btnPlus);
        btnSUB = (Button) findViewById(R.id.btnMinus);

        //Fetching and storing category name
        String cat_name = getIntent().getStringExtra("cat_name");
        tvCatName.setText(cat_name);

        pojoArrayList = new ArrayList<Pojo>();

        //fetching product id
        pro_id = getIntent().getStringExtra("product_id");

        prod_id = Integer.parseInt(pro_id);

        btnADD.setOnClickListener(this);
        btnSUB.setOnClickListener(this);
        addToCart.setOnClickListener(this);

        new loadData().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlus:
                if (quantity < avlbl_qty) {
                    quantity++;
                    tvQty.setText(String.valueOf(quantity));
                    total_Amount = String.valueOf(quantity * Integer.parseInt(pro_price.getText().toString()));
                    tvProductTotal.setText(total_Amount);
                } else {
                    Toast.makeText(this, "Item out of stock", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnMinus:
                if (quantity > 1) {
                    quantity--;
                    tvQty.setText(String.valueOf(quantity));
                    total_Amount = String.valueOf(quantity * Integer.parseInt(pro_price.getText().toString()));
                    tvProductTotal.setText(total_Amount);
                } else {
                    tvQty.setText(String.valueOf(quantity));
                    total_Amount = String.valueOf(quantity * Integer.parseInt(pro_price.getText().toString()));
                    tvProductTotal.setText(total_Amount);
                }

                break;
            case R.id.addToCart:

                //Creating a shared preference
                pref = ProductDetailActivity.this.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                uId = pref.getString(Constants.SNO, "");

                Log.d(TAG, "onCreate: User " + uId + pref.getString(Constants.SNO, ""));

                new addToCart().execute();

                break;
        }

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
            tvProductTotal.setText(price);
            avlbl_qty = Integer.parseInt(pro_qty.getText().toString());
        }
    }

    private class addToCart extends AsyncTask<String, Void, String> {
        @SuppressWarnings("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {

                HttpRequest req = new HttpRequest(Constants.BASE_URL_APP + Constants.ADD_TO_CART);

                HashMap<String, String> params = new HashMap<>();
                params.put("u_id", uId);
                params.put("p_id", String.valueOf(pro_id));
                params.put("p_name", name);
                params.put("p_image", image);
                params.put("p_quantity", String.valueOf(tvQty.getText()));
                params.put("p_price", price);
                params.put("p_total", String.valueOf(tvProductTotal.getText()));

                String result = req.prepare(HttpRequest.Method.POST).withData(params).sendAndReadString();

                JSONObject jsonObject = new JSONObject(result);

                data = jsonObject.getString("status");


                Log.d(TAG, "doInBackground: " + result);
                Log.d(TAG, "doInBackground: " + data);

                return data;

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.equals("success")) {
                Toast.makeText(getApplicationContext(), "Successfully Added to Cart", Toast.LENGTH_LONG).show();
//                Intent i = new Intent(getApplicationContext(), CartFragment.class);
//                i.putExtra("uid", uId);
//                i.putExtra("p_total", tvProductTotal.getText().toString());
//                startActivity(i);

            } else {
                Toast.makeText(getApplicationContext(), "Something is wrong!!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
