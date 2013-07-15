package com.xiaojiujiu.dao;

import java.io.IOException;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.utils.JsonUtil;
import com.beanu.arad.utils.MessageUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.Coupon;
import com.xiaojiujiu.entity.CouponItem;
import com.xiaojiujiu.entity.ECard;
import com.xiaojiujiu.entity.ECardItem;
import com.xiaojiujiu.ui.HttpUtil;

public class EcardDetailDao {

	private ECard ecard;
	private ECardItem item;

	public EcardDetailDao(ECardItem item) {
		this.item = item;
	}

	public void setItem(ECardItem item) {
		this.item = item;
	}

	public void getDetailInfo(final IDataListener<ECard> listener) {
		AjaxParams params = new AjaxParams();
		params.put("op", "eCardInfo");
		params.put("eCardID", item.getItemID() + "");
		params.put("imei", Arad.app.deviceInfo.getDeviceID());
		params.put("userID", AppHolder.getInstance().user.getMemberID() + "");

		Arad.http.get(Constant.URL_ECARD, params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {

				try {
					JsonNode node = HttpUtil.handleResult(t);
					ecard = JsonUtil.node2pojo(node.findValue("eCardInfo"), ECard.class);
					listener.onSuccess(ecard);
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AradException e) {
					MessageUtil.showShortToast(Arad.app.getApplicationContext(), e.getMessage());
				}

			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				listener.onFailure(ecard, t, strMsg);
			}

		});
	}

	/**
	 * 收藏 or 取消收藏 collect =true opType=1 collect =false opType=0
	 * */
	public void collect(boolean collect, final IDataListener<String> listener) {

		try {
			if (collect) {
				ecard.setIsFavorite(1);
				Arad.db.save(ecard);
				Arad.db.save(item);
			} else {
				ecard.setIsFavorite(0);
				Arad.db.delete(ecard);
				Arad.db.delete(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//TODO 这里还没有写到这个接口
		AjaxParams params = new AjaxParams();
		params.put("op", "collectCoupon");
		params.put("opType", collect ? "1" : "0");
		params.put("couponID", item.getItemID() + "");
		params.put("userID", AppHolder.getInstance().user.getMemberID() + "");

		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {

				try {
					HttpUtil.handleResult(t);
					listener.onSuccess("");
				} catch (AradException e) {
					listener.onFailure("", e, e.getMessage());
					MessageUtil.showShortToast(Arad.app.getApplicationContext(), e.getMessage());
				}

			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				listener.onFailure("", t, strMsg);
				MessageUtil.showShortToast(Arad.app.getApplicationContext(),
						Arad.app.getResources().getString(R.string.network_error));
			}

		});
	}
}
