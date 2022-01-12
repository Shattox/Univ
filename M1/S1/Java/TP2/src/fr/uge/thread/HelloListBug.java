package fr.uge.thread;

import java.util.ArrayList;

public class HelloListBug {
	private static Runnable code(ThreadSafeList threads) {
		return () -> {
			for (var i = 0; i < 5000; i++) {
				threads.add(i);
			}
		};
	}
	
	private static void addThreads(ThreadSafeList threadSafeList, ArrayList<Thread> threads, int nbThread) {
		for (var i = 0; i < nbThread; i++) {
			threads.add(new Thread(code(threadSafeList)));
			threads.get(i).start();
		}
	}
	
	private static void deathThreads(ArrayList<Thread> threads) throws InterruptedException {
		for (Thread thread: threads) {
			thread.join();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		final int nbThread = 4;
		
		var threads = new ArrayList<Thread>();
		var threadSafeList = new ThreadSafeList();
		
		addThreads(threadSafeList, threads, nbThread);
		deathThreads(threads);
		
		System.out.println(threadSafeList.size());
	}
}
