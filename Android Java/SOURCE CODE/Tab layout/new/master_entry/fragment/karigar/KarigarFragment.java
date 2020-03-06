package com.app.cubeapparels.master_entry.fragment.karigar;

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
import com.app.cubeapparels.allocate_order.Karigar;

import java.util.ArrayList;

public class KarigarFragment extends Fragment {
    RecyclerView mRecyclerView;
    ArrayList<Karigar> list = new ArrayList<>();

    public KarigarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_karigar, container, false);

        mRecyclerView = view.findViewById(R.id.mRecyclerView);

        setRecyclerView();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            list = (ArrayList<Karigar>) getArguments().getSerializable("karigar_list");
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
        KarigarAdapter mAdapter = new KarigarAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
    }

}
