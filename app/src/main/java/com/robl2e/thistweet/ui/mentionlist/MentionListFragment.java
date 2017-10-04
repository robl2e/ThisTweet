package com.robl2e.thistweet.ui.mentionlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robl2e.thistweet.R;
import com.robl2e.thistweet.ui.home.TabPage;

public class MentionListFragment extends Fragment implements TabPage {
    public MentionListFragment() {
        // Required empty public constructor
    }

    public static MentionListFragment newInstance() {
        MentionListFragment fragment = new MentionListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mention_list, container, false);
    }

    @Override
    public void onFABClicked(View v) {

    }
}
