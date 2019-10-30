package com.app.baseproject.shared;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.app.baseproject.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1, title;
    private String mParam2;
    private SearchView searchView;
    private ArrayAdapter adapter;
    ImageButton btn_close;
    int current_position = 0;
    TextView tv_title;
    ArrayList<Search> list;

    SearchEventListener searchEventListener;
    RecyclerView rv_search;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        tv_title = view.findViewById(R.id.tv_title);
        searchView = view.findViewById(R.id.searchView);
        rv_search = view.findViewById(R.id.rv_search);
        btn_close = view.findViewById(R.id.btn_close);

        searchView.findFocus();

        btn_close.setOnClickListener(v -> {
            closeFragment();
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        searchEventListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        title = getArguments().getString("title");
        tv_title.setText(title);

        list = (ArrayList<Search>) getArguments().getSerializable("saloon_list");

        setListener();

        setRecyclerView();

        setAdapter(list);

        try {
            searchEventListener = (SearchEventListener) getActivity();
        } catch (Exception e) {
            throw new ClassCastException(e.getMessage() + " must implement searchEventListener");
        }
    }


    public void closeFragment() {
        getActivity().getFragmentManager().popBackStack();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv_search.setLayoutManager(mLayoutManager);
    }

    public void setAdapter(ArrayList<Search> list) {
        SearchAdapter mAdapter = new SearchAdapter(this,list);
        rv_search.setAdapter(mAdapter);
    }


    private void setListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setNewList(newText);
                return false;
            }
        });
    }

    private void setNewList(String newText) {
        ArrayList<Search> newList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().toLowerCase().contains(newText.toLowerCase())) {
                newList.add(list.get(i));
                current_position = i;
            }
        }

        setAdapter(newList);

    }

}
