package fr.uge.thread;

import java.util.ArrayList;

public class HelloThreadJoin {	
	private static Runnable code(int threadNum) {
		return () -> {
			for (var i = 0; i < 5000; i++) {
				System.out.println("thread " + threadNum + " " + i);
			}
		};
	}
	
	private static void addThreads(ArrayList<Thread> threads, int nbThread) {
		for (var i = 0; i < nbThread; i++) {
			threads.add(new Thread(code(i)));
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
		
		addThreads(threads, nbThread);
		deathThreads(threads);
		
		System.out.println("Le programme est fini");
	}
}
