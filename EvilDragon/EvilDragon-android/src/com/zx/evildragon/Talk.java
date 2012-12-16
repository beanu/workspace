package com.zx.evildragon;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechError;
import com.iflytek.speech.SynthesizerPlayer;
import com.iflytek.speech.SynthesizerPlayerListener;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.zx.evildragon.net.ITalk;

public class Talk implements ITalk {

	static final String appid = "50c68d22";
	String text = "";
	SynthesizerPlayer player;
	SynthesizerPlayerListener synbgListener;

	RecognizerDialog isrDialog;
	RecognizerDialogListener recognizeListener;

	SimSimiRestClientUsage client;
	AsyncHttpResponseHandler responseHandler;

	RecongBack callback;
	
	Context context;
	public Talk(Context context) {
		this.context=context;
		client = new SimSimiRestClientUsage();

		// 转写回调监听器.
		recognizeListener = new RecognizerDialogListener() {
			// 识别结果回调接口
			public void onResults(ArrayList<RecognizerResult> results, boolean isLast) {
				// 一般情况下会通过onResults接口多次返回结果,完整的识别内容是多次结果的累加.
				for (int i = 0; i < results.size(); i++) {
					text += results.get(i).text;
				}
				// 会话结束回调接口.
				
			}

			public void onEnd(SpeechError error) {
				
				// error为null表示会话成功,可在此处理text结果,error不为null,表示发生错误,对话框停留在错误页面
				if (null == error) {
					Gdx.app.debug("debug", "问："+text);
					client.userRequest(text, responseHandler);
				} else {
					// TODO error
				}
			}
		};

		isrDialog = new RecognizerDialog(context, "appid=" + appid);
		isrDialog.setEngine("sms", "vad_bos=4000,vad_eos =700", null);
		isrDialog.setListener(recognizeListener);
		isrDialog.showErrorView(false, true);

		player = SynthesizerPlayer.createSynthesizerPlayer(context, "appid=" + appid);
		player.setVoiceName("vixx");
		player.setVolume(100);
		synbgListener = new SynthesizerPlayerListener() {

			@Override
			public void onBufferPercent(int arg0, int arg1, int arg2) {
				// 缓冲回调,通知当前缓冲进度

			}

			@Override
			public void onEnd(SpeechError arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPlayBegin() {
				// 播放开始回调,表示已获得第一块缓冲区音频开始播放
			}

			@Override
			public void onPlayPaused() {
				// 暂停通知,表示缓冲数据播放完成,后续的音频数据正在获取

			}

			@Override
			public void onPlayPercent(int arg0, int arg1, int arg2) {
				// 播放回调,通知当前播放进度

			}

			@Override
			public void onPlayResumed() {
				// 暂停通知后重新获取到后续音频,表示重新开始播放

			}

		};

		responseHandler = new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray timeline) {
			}

			@Override
			public void onSuccess(JSONObject response) {
				try {
					String res = response.getString("response");
					player.playText(res, "tts_buffer_time=2000", synbgListener);
					callback.end(res);
					Log.d("debug", "答："+res);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				super.onSuccess(response);
			}

		};
	}

	@Override
	public void recognition(final RecongBack callback) {
		((Activity)context).runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Talk.this.callback=callback;
				text="";
				isrDialog.show();
//				isrDialog.hide();
				
			}
		});

	}

}
