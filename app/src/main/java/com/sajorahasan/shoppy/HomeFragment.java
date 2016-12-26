package com.sajorahasan.shoppy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.sajorahasan.shoppy.adapter.CatAdapter;
import com.sajorahasan.shoppy.custom.SliderLayoutDashBoard;
import com.sajorahasan.shoppy.model.Pojo;
import com.sajorahasan.shoppy.service.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sajora on 11-12-2016.
 */

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener {
    private static final String TAG = "HomeFragment";

    SliderLayoutDashBoard mDemoSlider;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Calling the RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.cat_recycler_view);
        //recyclerView.setHasFixedSize(true);

        // The number of columns
        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);

        new loadCategory().execute();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    private class loadCategory extends AsyncTask {
        private String response;
        ArrayList<Pojo> pojoArrayList = new ArrayList<>();

        @Override
        protected Object doInBackground(Object[] objects) {

            // category API url
            String Category = Constants.CategoryAPI + "?accesskey=" + Constants.AccessKey;

            try {
                HttpRequest req = new HttpRequest(Constants.BASE_URL_APP + Category);

                response = req.prepare(HttpRequest.Method.POST).sendAndReadString();

                JSONObject object2 = new JSONObject(response);
                JSONArray array = object2.getJSONArray("data");


                for (int i = 0; i < array.length(); i++) {
                    Pojo p = new Pojo();
                    //JSONObject object1 = array.getJSONObject(i);
                    JSONObject object = array.getJSONObject(i);
                    JSONObject object1 = object.getJSONObject("Category");

                    int id = object1.getInt("Category_ID");
                    String name = object1.getString("Category_name");
                    String image = object1.getString("Category_image");

                    p.setCat_id(id);
                    p.setCat_name(name);
                    p.setCat_image(image);

                    pojoArrayList.add(p);
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            CatAdapter catAdapter = new CatAdapter(getActivity(), pojoArrayList);
            recyclerView.setAdapter(catAdapter);
        }
    }
}
