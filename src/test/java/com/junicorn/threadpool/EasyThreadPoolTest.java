package com.junicorn.threadpool;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

public class EasyThreadPoolTest {

	@Test
	public void test() throws InterruptedException {
		EasyThreadPool threadPool = new EasyThreadPool(2);
		
		final CountDownLatch latch = new CountDownLatch(6);
		Runnable task1 = new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " task1");
				latch.countDown();
			}
		};

		Runnable task2 = new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " task2");
				latch.countDown();
			}
		};

		Runnable task3 = new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " task3");
				latch.countDown();
			}
		};
		
		threadPool.execute(task1);
		threadPool.execute(task1);
		threadPool.execute(task2);
		threadPool.execute(task2);
		threadPool.execute(task3);
		threadPool.execute(task3);
		
		latch.await();
		threadPool.shutdown();
	}
}
