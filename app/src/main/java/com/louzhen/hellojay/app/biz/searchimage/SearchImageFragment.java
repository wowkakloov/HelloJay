package com.louzhen.hellojay.app.biz.searchimage;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.louzhen.hellojay.app.R;
import com.louzhen.hellojay.app.api.ImageBean;
import com.louzhen.hellojay.app.util.AppContext;

import java.util.List;

/**
 * Created by louzhen on 16/10/14.
 */

public class SearchImageFragment extends Fragment implements ISearchImageView {

    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SearchImagePresenter searchImagePresenter;
    private View mSearchLayout, mSearchBtn;
    private EditText mSearchInput;
    private int lastVisibleItem = 0;
    private int firstVisibleItem = 0;
    private String mSearchWord = "周杰伦";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_image_layout, container, false);
        initView(rootView);
        searchImagePresenter = new SearchImagePresenter(this);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout.measure(0, 0);
        mSwipeRefreshLayout.setRefreshing(true);
        searchImagePresenter.getData(mSearchWord, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        searchImagePresenter.detached();
        searchImagePresenter = null;
    }

    private void initView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mImageAdapter = new ImageAdapter(getActivity());
        mImageAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, String url) {
                Intent intent = new Intent(getActivity(), ImagePreviewActivity.class);
                intent.putExtra("url", url);

                if (Build.VERSION.SDK_INT >= 21) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "transitonImage");
                    getActivity().startActivity(intent, options.toBundle());
                } else {
                    getActivity().startActivity(intent);
                }
            }
        });
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mImageAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh_view);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.refresh_color_1, R.color.refresh_color_2,
                R.color.refresh_color_3, R.color.refresh_color_4);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchImagePresenter.getData(mSearchWord, true);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mImageAdapter.getItemCount()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    searchImagePresenter.getData(mSearchWord, false);
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE && firstVisibleItem == 0) {
                    mSearchLayout.setVisibility(View.VISIBLE);
                } else {
                    mSearchLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                layoutManager.findLastVisibleItemPositions(lastPositions);
                int[] firstPositions = layoutManager.findFirstVisibleItemPositions(null);
                lastVisibleItem = findMax(lastPositions);
                firstVisibleItem = findMin(firstPositions);
            }
        });
        mSearchLayout = rootView.findViewById(R.id.search_layout);
        mSearchInput = (EditText) rootView.findViewById(R.id.search_input);
        rootView.findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable editable = mSearchInput.getText();
                if (editable != null && !editable.toString().equals("")) {
                    mSearchWord = editable.toString();
                    mSwipeRefreshLayout.setRefreshing(true);
                    searchImagePresenter.getData(mSearchWord, true);
                } else {
                    Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int findMin(int[] lastPositions) {
        int min = lastPositions[0];
        for (int value : lastPositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    @Override
    public void update(List<ImageBean> imageBeanList, int page) {
        if (page == 0) {
            mImageAdapter.setImages(imageBeanList);
        } else {
            mImageAdapter.addImages(imageBeanList);
        }
        mImageAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(AppContext.appContext, msg + "", Toast.LENGTH_SHORT).show();
    }
}
