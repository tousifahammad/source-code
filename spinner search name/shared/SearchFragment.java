package com.app.cubeapparels.shared;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.app.cubeapparels.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private String title;
    private List<String> list_name;
    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter adapter;
    ImageButton btn_close;
    int current_position = 0;
    TextView tv_title;

    SearchEventListener searchEventListener;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_string, container, false);

        tv_title = view.findViewById(R.id.tv_title);
        searchView = view.findViewById(R.id.searchView);
        listView = view.findViewById(R.id.listView);
        btn_close = view.findViewById(R.id.btn_close);

        //prepareData();
        //setListener();
        searchView.findFocus();

        btn_close.setOnClickListener(v -> {
            //close fragment
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

        list_name = getArguments().getStringArrayList("name_list");
        title = getArguments().getString("title");
        tv_title.setText(title);

        setListView();

        setListener();

        //Log.d("1111", "list_name: " + list_name);
        //Log.d("1111", "list_code: " + list_code);

        try {
            searchEventListener = (SearchEventListener) getActivity();
        } catch (Exception e) {
            throw new ClassCastException(e.getMessage() + " must implement searchEventListener");
        }
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
                List<String> newList = new ArrayList<>();
                for (int i = 0; i < list_name.size(); i++) {
                    if (list_name.get(i).toLowerCase().contains(newText.toLowerCase())) {
                        newList.add(list_name.get(i));
                        current_position = i;
                    }
                    adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, newList);
                    listView.setAdapter(adapter);
                }
                return false;
            }
        });
    }

    private void setListView() {
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list_name);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            searchEventListener.searchEvent(adapter.getItem(position).toString());
            closeFragment();
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            return true;
        });

    }


    private void closeFragment() {
        getActivity().getFragmentManager().popBackStack();
    }

}
