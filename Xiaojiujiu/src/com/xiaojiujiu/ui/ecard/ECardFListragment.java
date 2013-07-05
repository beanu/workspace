package com.xiaojiujiu.ui.ecard;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.Log;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshBase;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshBase.OnLastItemVisibleListener;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshListFragment;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshListView;
import com.xiaojiujiu.R;
import com.xiaojiujiu.entity.ECard;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.ECardListAdapter;

/**
 * 电子会员卡列表页面
 * 
 * @author beanu
 * 
 */
public class ECardFListragment extends PullToRefreshListFragment implements OnRefreshListener<ListView>,
		OnLastItemVisibleListener {

	public static ECardFListragment newInstance(String typeId, int position) {
		// Bundle args = new Bundle();
		// args.putString(TYPEID, typeId);
		// args.putInt(POSITION, position);
		ECardFListragment fragment = new ECardFListragment();
		// fragment.setArguments(args);
		return fragment;
	}

	private ECardListAdapter mAdapter;
	private List<ECard> mECardList = new ArrayList<ECard>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ecard_list_fragment, container, false);
		empty = (TextView) view.findViewById(R.id.empty);
		progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.listView);

		footerView = inflater.inflate(R.layout.pull_to_refresh_listview_footer_layout, null);
		getListView().addFooterView(footerView);
		getListView().setHeaderDividersEnabled(false);
		dismissFooterView();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new ECardListAdapter(getSherlockActivity(), mECardList);
		getListView().setAdapter(mAdapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				Intent intent = new Intent(getSherlockActivity().getApplicationContext(), ECardDetailActivity.class);
				startActivity(intent);
				UIUtil.intentSlidIn(getSherlockActivity());
			}
		});

		pullToRefreshListView.setOnRefreshListener(this);
		pullToRefreshListView.setOnLastItemVisibleListener(this);
		if (getCurrentState(savedInstanceState) == FIRST_TIME_START) {
			pullToRefreshListView.setRefreshing(false);
			showListView(false);
		}else{
			showListView(true);
		}
	}

	@Override
	public void onLastItemVisible() {

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		Log.d("onRefresh");
		AjaxParams params = new AjaxParams();
		params.put("op", "searchByTypeAndDistance");
		params.put("cityID", "1");
		params.put("radius", "10000");
		params.put("shopFirstCateID", "11");
		params.put("shopSecondCateID", "12");
		params.put("couponTypeID", "15");
		params.put("imei", "863363014570588");
		params.put("orderType", "2");
		params.put("lng", "117.15863");
		params.put("lat", "36.65231");
		params.put("pageSize", "3");
		params.put("pageIndex", "1");
		Arad.http.get("http://www.x99local.com/ECardHandler.ashx", params, new AjaxCallBack<String>() {

			@Override
			public void onStart() {

			}

			@Override
			public void onSuccess(String t) {

				try {
					JSONObject response = new JSONObject(t);
					String resCode = response.getString("resCode");
					if (resCode != null && resCode.equals("1")) {
						JSONArray jsonArray = response.getJSONArray("CouponList");
						ArrayList<ECard> _list = new ArrayList<ECard>();
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i);
							// Coupon c =
							// AppHolder.getInstance().objectMapper.readValue(item.toString(),
							// Coupon.class);
							ECard eCard = new ECard();
							eCard.seteCardID(item.getInt("ECardID"));
							eCard.seteCardTitle(item.getString("ECardTitle"));
							eCard.seteCardDesc(item.getString("ECardDesc"));
							eCard.setSmallImageUrl(item.getString("SmallImageUrl"));
							_list.add(eCard);
						}

						// 如果有重复的去掉重复的，然后在加上最新的信息
						for (ECard newest : _list) {
							for (ECard older : mECardList) {
								if (newest.geteCardID() == older.geteCardID()) {
									mECardList.remove(older);
									break;
								}
							}
						}
						mECardList.addAll(0, _list);
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
