package com.zx.evildragon.net;

/**
 * This interface defines a dialogue specification. The process of the dialogue
 * is 1. Initiate dialogue 2. Recognition dialogue content, return text 3.
 * Processing returns text
 * 
 * @author beanu
 * 
 */
public interface ITalk {
	public interface CallBack {
		/**说完话后，识别开始*/
		public void recognitionBegin();

		/**识别结束，返回识别文字*/
		public void recognitionEnd(String text,boolean success);

		/**AI相应回答，返回结果*/
		public void response(String text,boolean success);

		/**合成语音开始*/
		public void synthesizerBegin();

		/**合成语音结束*/
		public void synthesizerEnd(boolean success);
	}

	/**
	 * Recognition dialogue content
	 */
	public void recognition(CallBack callback);
}
