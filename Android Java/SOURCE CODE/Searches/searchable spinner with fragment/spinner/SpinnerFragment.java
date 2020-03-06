package com.app.havejee.spinner;

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


import com.app.havejee.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1, title;
    private String mParam2;
    private List<String> list_name;
    //private List<String> list_code;
    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter adapter;
    ImageButton btn_close;
    int current_position = 0;
    TextView tv_title;

//    public interface onSomeEventListener {
//        public void someEvent(String s);
//    }

    SpinnerEventListener searchEventListener;

    public SpinnerFragment() {
        // Required empty public constructor
    }

    public static SpinnerFragment newInstance(String param1, String param2) {
        SpinnerFragment fragment = new SpinnerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_spinner, container, false);

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
        //list_code = getArguments().getStringArrayList("code_list");

        setListView();

        setListener();

        //Log.d("1111", "list_name: " + list_name);
        //Log.d("1111", "list_code: " + list_code);

        try {
            searchEventListener = (SpinnerEventListener) getActivity();
        } catch (Exception e) {
            throw new ClassCastException(e.getMessage() + " must implement searchEventListener");
        }
    }

    private void setListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /**When Click on Submit Button then Close the Search and Clear the Focus**/
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<String> newList = new ArrayList<>();
                /**For Loop to Check String in List one by one**/
                for (int i = 0; i < list_name.size(); i++) {
                    /**If typed string match with the data in a List then add it into new List**/
                    if (list_name.get(i).toLowerCase().contains(newText.toLowerCase())) {
                        newList.add(list_name.get(i));
                        current_position = i;
                    }
                    /**After Perparing a newList, Set the List into ListView**/
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

        /**When Click on Item**/
        listView.setOnItemClickListener((parent, view, position, id) -> {
            searchEventListener.onItemClicked(adapter.getItem(position).toString(), position);
            closeFragment();
        });

        /**When Long Click on Item**/
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Snackbar.make(view, "Long CLicked on : " + adapter.getItem(position).toString(), Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });

    }


    private void closeFragment() {
        getActivity().getFragmentManager().popBackStack();
    }

}
