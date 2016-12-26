package com.sajorahasan.shoppy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.sajorahasan.shoppy.adapter.ProductAdapter;
import com.sajorahasan.shoppy.model.Pojo;
import com.sajorahasan.shoppy.service.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {
    private static final String TAG = "ProductActivity";

    private ExpandableHeightGridView gridview;
    private ArrayList<Pojo> pojoArrayList;
    private ProductAdapter productAdapter;

    String cat_id;
    static int pro_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        gridview = (ExpandableHeightGridView) findViewById(R.id.gridview);
        gridview.setExpanded(true);

        pojoArrayList = new ArrayList<Pojo>();

        cat_id = getIntent().getStringExtra("cat_id");

        //Fetching and storing category name
        String cat_name = getIntent().getStringExtra("cat_name");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(cat_name);

        Toast.makeText(this, "Category id " + cat_id, Toast.LENGTH_SHORT).show();

        new loadProduct().execute();

        gridview.setAdapter(productAdapter);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    private class loadProduct extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            // menu API url
            String ProductAPI = Constants.ProductAPI + "?accesskey=" + Constants.AccessKey + "&category_id=" + cat_id;
            Log.d(TAG, "onPostExecute: category id " + cat_id);
            try {
                HttpRequest req = new HttpRequest(Constants.BASE_URL_APP + ProductAPI);

                Log.d(TAG, "doInBackground: url " + ProductAPI);

                JSONObject jObject = req.prepare(HttpRequest.Method.POST).sendAndReadJSON();

                //JSONObject jObject = new JSONObject(response);
                JSONArray jArray = jObject.getJSONArray("data");

                for (int i = 0; i < jArray.length(); i++) {
                    Pojo p = new Pojo();
                    JSONObject jsonObject = jArray.getJSONObject(i);

                    JSONObject product = jsonObject.getJSONObject("Menu");

                    pro_id = product.getInt("Menu_ID");
                    String name = product.getString("Menu_name");
                    String image = product.getString("Menu_image");

                    p.setProduct_id(pro_id);
                    p.setProduct_image(image);
                    p.setProduct_title(name);

                    Log.d(TAG, "onPostExecute: image " + image);

                    pojoArrayList.add(p);
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return pojoArrayList;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            productAdapter = new ProductAdapter(ProductActivity.this, pojoArrayList);
            gridview.setAdapter(productAdapter);
        }
    }
}
