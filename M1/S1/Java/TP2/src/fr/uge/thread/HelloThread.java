package fr.uge.thread;

import java.util.ArrayList;

public class HelloThread {
	private static Runnable code(int thread) {
		return () -> {
			for (var i = 0; i < 5000; i++) {
				System.out.println("thread " + thread + " " + i);
			}
		};
	}
	
	private static void addThreads(ArrayList<Thread> threads, int nbThread) {
		for (var i = 0; i < nbThread; i++) {
			threads.add(new Thread(code(i)));
			threads.get(i).start();
		}
	}
	
	public static void main(String[] args) {
		final int nbThread = 4;
		
		var threads = new ArrayList<Thread>();
		
		addThreads(threads, nbThread);
	}
}
