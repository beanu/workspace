package com.xiaojiujiu.ui.ecard;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.Log;
import com.beanu.arad.utils.MessageUtil;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.dao.EcardDetailDao;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.entity.ECard;
import com.xiaojiujiu.entity.ECardItem;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.widget.LongButton;

/**
 * 电子会员卡 详情界面
 * 
 * @author beanu
 * 
 */
@EActivity(R.layout.ecard_detail_activity)
public class ECardDetailActivity extends MyActivity implements IDataListener<String> {
	EcardDetailDao dao;

	@ViewById ImageView ecard_detail_big_image;
	@ViewById TextView ecard_detail_score;
	@ViewById TextView ecard_detail_title;
	@ViewById TextView ecard_detail_level;
	@ViewById TextView ecard_detail_discount;

	@ViewById TextView ecard_detail_content;
	@ViewById TextView ecard_detail_effectiveTime;
	@ViewById LongButton ecard_detail_seeMore;
	@ViewById LongButton ecard_detail_shops;

	@ViewById TextView nearby_shop_name;
	@ViewById TextView nearby_shop_address;
	@ViewById TextView nearby_shop_distance;
	@ViewById ImageView nearby_shop_phone;
	@ViewById LongButton ecard_detail_promotion;

	@ViewById ProgressBar ecard_detail_progress;
	@ViewById ScrollView ecard_detail_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		enableSlideGestureDetector(true);
		setSlidingEventListener(new SlidingEventListener() {

			@Override
			public void rightSlidingEvent() {
				if (!isFinishing())
					finish();
				UIUtil.intentSlidOut(ECardDetailActivity.this);
				Log.d("right sliding");
			}

			@Override
			public void leftSlidingEvent() {
				Log.d("left sliding");

			}
		});
		if (savedInstanceState == null) {
			Bundle b = getIntent().getExtras();
			dao = new EcardDetailDao((ECardItem) b.getSerializable("item"));
		}
	}

	@AfterViews
	void init() {
		dao.getDetailInfo(this);
	}

	@Override
	public void onSuccess(String result) {
		refreshUI();
	}

	@Override
	public void onFailure(String result, Throwable t, String strMsg) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem collectMenuItem = menu.add(Menu.NONE, R.id.menu_collect, Menu.NONE, "收藏");
		if (dao.getEcard().getIsFavorite() == 0)
			collectMenuItem.setIcon(R.drawable.menu_unfav);
		else
			collectMenuItem.setIcon(R.drawable.menu_fav);
		collectMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		MenuItem shareMenuItem = menu.add(Menu.NONE, R.id.menu_share, Menu.NONE, "分享");
		shareMenuItem.setIcon(R.drawable.menu_share);
		shareMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_collect:
			if (AppHolder.getInstance().user.getMemberName() == null) {
				MessageUtil.showShortToast(getApplicationContext(), "请先登录");
			} else {
				if (dao.getEcard().getIsFavorite() == 0)
					dao.collect(true, new IDataListener<String>() {

						@Override
						public void onSuccess(String result) {
							item.setIcon(R.drawable.menu_fav);
							MessageUtil.showShortToast(getApplicationContext(), "收藏成功");
						}

						@Override
						public void onFailure(String result, Throwable t, String strMsg) {
							// TODO Auto-generated method stub

						}
					});
				else
					dao.collect(false, new IDataListener<String>() {

						@Override
						public void onSuccess(String result) {
							item.setIcon(R.drawable.menu_unfav);
							MessageUtil.showShortToast(getApplicationContext(), "取消收藏");
						}

						@Override
						public void onFailure(String result, Throwable t, String strMsg) {
							// TODO Auto-generated method stub

						}
					});
			}

			break;
		case R.id.menu_share:
			AndroidUtil.shareText(this, dao.getEcard().getItemTitle(), dao.getEcard().geteCardDesc() + " #小九九");
			break;

		}
		super.onOptionsItemSelected(item);
		return false;
	}

	private void refreshUI() {
		ECard ecard = dao.getEcard();
		Arad.imageLoader.display(ecard.getItemImageUrl(), ecard_detail_big_image);

		ecard_detail_score.setText(String.valueOf(ecard.getJiFen()));
		ecard_detail_title.setText(ecard.getItemTitle());
		ecard_detail_level.setText(ecard.getCardGrade());
		ecard_detail_discount.setText(ecard.getAwardInfo());

		ecard_detail_content.setText(ecard.getRuleStr());
		ecard_detail_effectiveTime.setText(ecard.getExpirationTime());
		// ecard_detail_seeMore.setOnClickListener(l);
		// ecard_detail_shops;

		nearby_shop_name.setText(ecard.getNearestShopName());
		nearby_shop_address.setText(ecard.getNearestShopAddress());
		nearby_shop_distance.setText(String.valueOf(ecard.getNearestShopDistance()));
		// nearby_shop_phone;
		// ecard_detail_promotion;
		showContent();
	}

	private void showContent() {
		ecard_detail_progress.setVisibility(View.GONE);
		ecard_detail_layout.setVisibility(View.VISIBLE);
	}
}
