package com.app.cubeapparels.master_entry.fragment.design;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.cubeapparels.R;
import com.app.cubeapparels.addorder.Design;
import com.app.cubeapparels.master_entry.MasterEntryClickListener;

import java.util.ArrayList;

public class DesignFragment extends Fragment {
    RecyclerView mRecyclerView;
    ArrayList<Design> list = new ArrayList<>();

    public DesignFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            list = (ArrayList<Design>) getArguments().getSerializable("design_list");

            if (list.size() > 0)
                setAdapter();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_desgn, container, false);

        mRecyclerView = view.findViewById(R.id.mRecyclerView);

        setRecyclerView();

        return view;
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
        DesignAdapter mAdapter = new DesignAdapter(list, getActivity(), (MasterEntryClickListener)getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }
}
