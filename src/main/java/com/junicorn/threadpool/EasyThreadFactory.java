package com.junicorn.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class EasyThreadFactory implements ThreadFactory {

	private static final String MARK = "EasyThreadPool-Worker-";
	
	private AtomicInteger id = new AtomicInteger();
	
	private boolean isDaemon = false;
	
	@Override
	public Thread newThread(Runnable runnable) {
		Thread thread = new Thread(runnable, MARK + id.incrementAndGet());
		thread.setDaemon(isDaemon);
		
		return thread;
	}
}
