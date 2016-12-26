package com.sajorahasan.shoppy;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sajorahasan.shoppy.model.ServerRequest;
import com.sajorahasan.shoppy.model.ServerResponse;
import com.sajorahasan.shoppy.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ProfileFragment";

    private AppCompatButton btnChangePwd;
    private TextView tv_message;
    private EditText et_old_password, et_new_password;
    private AlertDialog dialog;
    private SharedPreferences pref;
    private ProgressBar progress;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Initializing Views
        btnChangePwd = (AppCompatButton) view.findViewById(R.id.btnChangePwd);

        pref = getActivity().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);


        btnChangePwd.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangePwd:
                showDialog();
                break;
        }
    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);

        et_old_password = (EditText) view.findViewById(R.id.et_old_password);
        et_new_password = (EditText) view.findViewById(R.id.et_new_password);
        tv_message = (TextView) view.findViewById(R.id.tv_message);
        progress = (ProgressBar) view.findViewById(R.id.progress);

        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = et_old_password.getText().toString();
                String new_password = et_new_password.getText().toString();
                if (!old_password.isEmpty() && !new_password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    changePasswordProcess(pref.getString(Constants.EMAIL, ""), old_password, new_password);

                } else {

                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("Fields are empty");
                }
            }
        });
    }

    private void changePasswordProcess(String email, String old_password, String new_password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setOld_password(old_password);
        user.setNew_password(new_password);

        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.CHANGE_PASSWORD_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if (resp.getResult().equals(Constants.SUCCESS)) {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.GONE);
                    dialog.dismiss();
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                } else {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(resp.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, "failed");
                progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(t.getLocalizedMessage());

            }
        });
    }
}
