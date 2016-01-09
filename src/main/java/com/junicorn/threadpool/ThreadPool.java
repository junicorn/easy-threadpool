package com.junicorn.threadpool;

public interface ThreadPool {
	
	boolean execute(Runnable task);
	
	void shutdown();
	
}
