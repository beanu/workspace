package com.zx.evildragon.net;

/**
 * This interface defines a dialogue specification. The process of the dialogue is
 * 1. Initiate dialogue 
 * 2. Recognition dialogue content, return text 
 * 3. Processing returns text
 * 
 * @author beanu
 * 
 */
public interface ITalk {
	public interface RecognitionCallBack {
		public void before();
		public void begin();
		public void end(String text);
	}

	/**
	 * Recognition dialogue content
	 */
	public void recognition(RecognitionCallBack callback);
}
