package com.xiaojiujiu.ui;

import org.json.JSONException;
import org.json.JSONObject;

import com.beanu.arad.error.AradException;

public class HttpUtil {

	public static void handleError(String result) throws AradException {

		try {
			JSONObject object = new JSONObject(result);
			String statue = object.getString("resCode");
			if (statue != null && statue.equals("1")) {

			} else {
				AradException e = new AradException();
				e.setError_code(statue);
				throw e;
			}

		} catch (JSONException e) {
			e.printStackTrace();
			AradException exception = new AradException(e.getMessage());
			throw exception;
		}
	}
}
