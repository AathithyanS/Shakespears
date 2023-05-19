package com.project.shakespearesbooking;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.shakespearesbooking.Adapters.ShowsRvAdapter;
import com.project.shakespearesbooking.Database.MyDatabaseHelper;
import com.project.shakespearesbooking.TestData.ShowData;
import com.project.shakespearesbooking.models.ShowModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView showRv;
    private ShowsRvAdapter showRvAdapter;
    AutoCompleteTextView searchEt;
    MyDatabaseHelper dbHelper;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_home, container, false);

        searchEt = rootview.findViewById(R.id.searchEt);

        dbHelper = new MyDatabaseHelper(getContext());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isFirstTime = sharedPreferences.getBoolean("is_first_time", true);

        if (isFirstTime) {
            insertIniValues(getContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_first_time", false);
            editor.apply();
        }

        ArrayList<ShowModel> shows = dbHelper.getAllShows(getContext());

        showRvAdapter = new ShowsRvAdapter(shows, getActivity());

        List<String> searchValues = new ArrayList<>();
        for (ShowModel show : shows){
            searchValues.add(show.showTitle);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, searchValues);
        searchEt.setAdapter(adapter);

        showRv = rootview.findViewById(R.id.showsRv);
        showRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        showRv.setAdapter(showRvAdapter);

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                ArrayList<ShowModel> searchShows = dbHelper.getSearchShows(getContext(), searchText);
                showRvAdapter = new ShowsRvAdapter(searchShows, getActivity());
                showRv.setAdapter(showRvAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        return rootview;
    }

    private void insertIniValues(Context context){
        ShowData sd = new ShowData();
        sd.addData();
        for (ShowModel s : sd.shows){
            dbHelper.insertShowData(s);
        }
    }

}