package com.junicorn.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

public class EasyThreadPool implements ThreadPool {
	
	private static final int DEFAULT_POOL_SIZE = Runtime.getRuntime().availableProcessors();
	
	private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
	
	private final List<Worker> workers = new ArrayList<Worker>(DEFAULT_POOL_SIZE);
	
	private final ThreadFactory threadFactory = new EasyThreadFactory();

	private int size;

	public EasyThreadPool(int size) {
		this.size = size < 1 ? DEFAULT_POOL_SIZE : size;
		
		prestartThread();
	}

	private void prestartThread() {
		for (int i = 0; i < size; i++) {
			Worker worker = new Worker();
			workers.add(worker);
			
			threadFactory.newThread(worker).start();
		}
	}
	
	@Override
	public boolean execute(Runnable task) {
		return taskQueue.offer(task);
	}

	@Override
	public void shutdown() {
		for (Worker worker : workers) {
			worker.shutdown();
		}
	}
	
	class Worker implements Runnable {
		
		private volatile boolean running = true;

		@Override
		public void run() {
			while (running) {
				Runnable task = null;
				try {
					 task = taskQueue.take();
				} catch (InterruptedException e) {
					return;
				}
				
				if (task != null) {
					try {
						task.run();
					} catch (Exception e) {
						// Task exceptrion
						e.printStackTrace();
					}
				}
			}
		}
		
		public void shutdown() {
			running = false;
		}
	}
}
