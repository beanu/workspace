package com.zx.evildragon.net;

public interface ITalk {
	public interface RecongBack {
		public void end(String text);
	}

	public void recognition(RecongBack back);
}
