package com.xiaojiujiu.ui.freshnews;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.Log;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshBase;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshListFragment;
import com.xiaojiujiu.entity.FreshNews;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.FreshNewsAdapter;

/**
 * 新鲜事列表
 * Created by beanu on 13-6-3.
 */
public class FreshNewsListFragment extends PullToRefreshListFragment implements PullToRefreshBase.OnRefreshListener<ListView>,
        PullToRefreshBase.OnLastItemVisibleListener {

    public static FreshNewsListFragment newInstance() {
        FreshNewsListFragment fragment = new FreshNewsListFragment();
        return fragment;
    }

    private FreshNewsAdapter mAdapter;
    private List<FreshNews> mFreshNewsList = new ArrayList<FreshNews>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new FreshNewsAdapter(getSherlockActivity(), mFreshNewsList);
        getListView().setAdapter(mAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                Intent intent = new Intent(getSherlockActivity().getApplicationContext(), FreshNewsDetailActivity.class);
                startActivity(intent);
                UIUtil.intentSlidIn(getSherlockActivity());
            }
        });

        pullToRefreshListView.setOnRefreshListener(this);
        pullToRefreshListView.setOnLastItemVisibleListener(this);
        if (getCurrentState(savedInstanceState) == FIRST_TIME_START) {
            pullToRefreshListView.setRefreshing();
            showListView(false);
        }
    }

    @Override
    public void onLastItemVisible() {

    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        Log.d("onRefresh");
        AjaxParams params = new AjaxParams();
        params.put("op", "weiboList");
        params.put("userID", "1");
        params.put("pageSize", "3");
        params.put("pageIndex", "1");
        Arad.http.get("http://www.x99local.com/WeiboHandler.ashx", params, new AjaxCallBack<String>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String t) {

                try {
                    JSONObject response = new JSONObject(t);
                    String resCode = response.getString("resCode");
                    if (resCode != null && resCode.equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("WeiBoList");
                        ArrayList<FreshNews> _list = new ArrayList<FreshNews>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            // Coupon c =
                            // AppHolder.getInstance().objectMapper.readValue(item.toString(),
                            // Coupon.class);
                            FreshNews news = new FreshNews();
                            news.setId(item.getInt("WeiBoID"));
                            news.setStoreName(item.getString("ShopName"));
                            news.setContent(item.getString("WeiBoContent"));
                            news.setTime(item.getString("SendTime"));
                            _list.add(news);
                        }

                        // 如果有重复的去掉重复的，然后在加上最新的信息
                        for (FreshNews newest : _list) {
                            for (FreshNews older : mFreshNewsList) {
                                if (newest.getId() == older.getId()) {
                                    mFreshNewsList.remove(older);
                                    break;
                                }
                            }
                        }
                        mFreshNewsList.addAll(0, _list);
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // catch (JsonParseException e) {
                // e.printStackTrace();
                // } catch (JsonMappingException e) {
                // e.printStackTrace();
                // } catch (IOException e) {
                // e.printStackTrace();
                // }
                finally {
                    pullToRefreshListView.onRefreshComplete();
                    showListView(true);
                }
            }

            @Override
            public void onFailure(Throwable t, String strMsg) {
                pullToRefreshListView.onRefreshComplete();
                showListView(true);
            }

        });
    }
}
