package com.sajorahasan.shoppy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.sajorahasan.shoppy.adapter.ListbaseAdapter;
import com.sajorahasan.shoppy.custom.ChildAnimationExample;
import com.sajorahasan.shoppy.custom.SliderLayoutDashBoard;
import com.sajorahasan.shoppy.model.Beanclass;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryFragment extends Fragment implements BaseSliderView.OnSliderClickListener {
    SliderLayoutDashBoard mDemoSlider;

    private ListView list;
    private ArrayList<Beanclass> Bean;
    private ListbaseAdapter baseAdapter;


    private int[] IMAGE = {R.drawable.slidertea, R.drawable.slider2, R.drawable.slider3,};

    private String[] TITLE = {"Great Deal", "Great Deal", "Great Deal"};

    private String[] SUBTITLE = {"Grab it!", "Grab it!", "Grab it!"};

    private String[] SHOP = {"Shop Now", "Shop Now", "Shop Now"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        //Initializing View's
        list = (ListView) view.findViewById(R.id.list);

        Bean = new ArrayList<Beanclass>();

        for (int i = 0; i < TITLE.length; i++) {

            Beanclass bean = new Beanclass(IMAGE[i], TITLE[i], SUBTITLE[i], SHOP[i]);
            Bean.add(bean);

        }

        baseAdapter = new ListbaseAdapter(getActivity(), Bean) {

        };

        list.setAdapter(baseAdapter);


//        ******slider***********


        mDemoSlider = (SliderLayoutDashBoard) view.findViewById(R.id.slider);

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("1", R.drawable.s1);
        file_maps.put("2", R.drawable.s2);
        file_maps.put("3", R.drawable.s3);


        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    //  .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);


            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayoutDashBoard.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayoutDashBoard.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new ChildAnimationExample());
        mDemoSlider.setDuration(4000);
        //mDemoSlider.addOnPageChangeListener(this);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        //getActivity().setTitle("Menu 1");
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}



