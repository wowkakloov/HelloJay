package com.louzhen.hellojay.app.biz.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.louzhen.hellojay.app.R;
import com.louzhen.hellojay.app.api.NeteaseNewBean;
import com.louzhen.hellojay.app.util.AppContext;

import java.util.List;

/**
 * Created by louzhen on 16/10/14.
 */

public class NewsFragment extends Fragment implements INewsListView{

    private NewsListPresenter mNewsListPresenter;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int lastVisibleItem = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_list_layout, container, false);
        initView(rootView);

        mNewsListPresenter = new NewsListPresenter(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout.measure(0, 0);
        mSwipeRefreshLayout.setRefreshing(true);
        mNewsListPresenter.getData(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNewsListPresenter.detached();
        mNewsListPresenter = null;
    }

    private void initView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mNewsAdapter = new NewsAdapter(getActivity());
        mNewsAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, String url) {
                Intent intent = new Intent(getActivity(), NewDetailActivity.class);
                intent.putExtra("url", url);
                getActivity().startActivity(intent);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mNewsAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh_view);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.refresh_color_1, R.color.refresh_color_2,
                R.color.refresh_color_3, R.color.refresh_color_4);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNewsListPresenter.getData(true);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mNewsAdapter.getItemCount()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mNewsListPresenter.getData(false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void update(List<NeteaseNewBean> neteaseNewBeen, int page) {
        if (page == 0) {
            mNewsAdapter.setNews(neteaseNewBeen);
        } else {
            mNewsAdapter.addNews(neteaseNewBeen);
        }
        mNewsAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(AppContext.appContext, msg + "", Toast.LENGTH_SHORT).show();
    }
}
