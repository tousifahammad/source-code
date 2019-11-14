package com.app.cubeapparels.master_entry.fragment.company;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.cubeapparels.R;
import com.app.cubeapparels.addorder.Company;

import java.util.ArrayList;

public class CompanyFragment extends Fragment {
    RecyclerView mRecyclerView;
    ArrayList<Company> list = new ArrayList<>();

    public CompanyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company, container, false);

        mRecyclerView = view.findViewById(R.id.mRecyclerView);

        setRecyclerView();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            list = (ArrayList<Company>) getArguments().getSerializable("company_list");
            Log.d("1111", "list : " + list);

            if (list.size() > 0)
                setAdapter();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void setRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void setAdapter() {
        CompanyAdapter mAdapter = new CompanyAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
    }

}
