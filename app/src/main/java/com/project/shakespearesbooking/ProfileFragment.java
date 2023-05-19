package com.project.shakespearesbooking;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project.shakespearesbooking.Database.MyDatabaseHelper;
import com.project.shakespearesbooking.models.UserModel;

public class ProfileFragment extends Fragment {

    TextView proUserTv, proEmailTv;
    Button signOutBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        proUserTv = root.findViewById(R.id.proUserTv);
        proEmailTv = root.findViewById(R.id.proEmailTv);
        signOutBtn = root.findViewById(R.id.signOutBtn);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");


        MyDatabaseHelper db = new MyDatabaseHelper(getContext());
        UserModel user = db.getUser(getContext(), email);

        proUserTv.setText(user.username);
        proEmailTv.setText(user.email);

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin", false); // Set the value to false
                editor.putString("email", ""); // Set the value to false
                editor.apply();
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
                getActivity().finishAffinity();
            }
        });

        return root;
    }
}