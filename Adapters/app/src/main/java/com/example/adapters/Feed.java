package com.example.adapters;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class Feed extends Fragment implements FeedAdapter.OnClickListener, View.OnClickListener {
    FloatingActionButton buttonAdd;
    private View head;
    //private TextView curUser;
    private RecyclerView recycler;
    private FeedAdapter adapter;

    private List<Post> posts;

    SharedPreferences sp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        //
        buttonAdd = getView().findViewById(R.id.buttonAdd);
        head = getView().findViewById(R.id.head);
        //curUser = getView().findViewById(R.id.cur_user);
        buttonAdd.setOnClickListener(this);
        head.setOnClickListener(this);
        //curUser.setOnClickListener(this);

        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        // curUser.setText(sp.getString("username", "admin"));

        posts = new ArrayList<>();

        adapter = new FeedAdapter(posts, this);
        recycler = getView().findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
    }

    public void addPost(Post post)
    {
        posts.add(0, post);
        adapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = (LinearLayoutManager) recycler
                .getLayoutManager();
        layoutManager.scrollToPositionWithOffset(0, 0);
    }

    @Override
    public void onClick(View view)
    {
        // OnAddButtonListener listener = (OnAddButtonListener) getActivity();
        if (view.getId() == R.id.buttonAdd)
        {
            //listener.onAddButtonClicked(buttonAdd);

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            PostCreator frag = new PostCreator();

            ft.replace(R.id.top_layout, frag, "fragment_pick");
            ft.addToBackStack(null);

            ft.commit();
            buttonAdd.setClickable(true);
        }
        if (view.getId() == R.id.head)
        {
            RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext())
            {
                @Override
                protected int getVerticalSnapPreference()
                {
                    return LinearSmoothScroller.SNAP_TO_START;
                }
            };
            smoothScroller.setTargetPosition(0);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recycler
                    .getLayoutManager();
            layoutManager.startSmoothScroll(smoothScroller);
        }

    }

    @Override
    public void onItemClick(int position)
    {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        FullPost frag = new FullPost();

        ft.replace(R.id.top_layout, frag, "fragment_post");
        ft.addToBackStack(null);

        ft.commit();
        fm.executePendingTransactions();

        frag.fillPost(posts.get(position));
    }
}