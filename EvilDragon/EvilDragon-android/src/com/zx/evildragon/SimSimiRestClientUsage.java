package com.zx.evildragon;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

class SimSimiRestClientUsage {
	//http://api.simsimi.com/request.p?key=your key&lc=en&ft=1.0&text=hi
	
    public void userRequest(String text,AsyncHttpResponseHandler responseHandler) {
    	
    	RequestParams params = new RequestParams();
    	params.put("key", "0aad7ec1-b70e-42c9-88c1-ccbf4bdcea2c");
    	params.put("lc", "ch");
    	params.put("ft", "1.0");
    	params.put("text", text);
    	
        SimSimiRestClient.get("", params, responseHandler);
    }
}