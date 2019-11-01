package com.app.haircutuser.mybooking.fragment.upcoming;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.haircutuser.R;
import com.app.haircutuser.mybooking.Booking;
import com.app.haircutuser.mybooking.BookingClickListener;

import java.util.ArrayList;

public class UpcomingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private OnFragmentInteractionListener mListener;
    RecyclerView mRecyclerView;
    ArrayList<Booking> list = new ArrayList<>();

    public UpcomingFragment() {
        // Required empty public constructor
    }

    public static UpcomingFragment newInstance(String param1/*, String param2*/) {
        UpcomingFragment fragment = new UpcomingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            list = (ArrayList<Booking>) getArguments().getSerializable("upcoming_booking_list");

            if (list.size() > 0)
                setAdapter();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        mRecyclerView = view.findViewById(R.id.mRecyclerView);

        setRecyclerView();

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    private void setRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void setAdapter() {
        UpcomingAdapter mAdapter = new UpcomingAdapter(list, getContext(), (BookingClickListener)getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }
}
