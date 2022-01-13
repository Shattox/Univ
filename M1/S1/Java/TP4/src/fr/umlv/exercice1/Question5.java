package fr.umlv.exercice1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class Question5 {
	private static Runnable producer(BlockingQueue<String> bQueue, int id, int timestamp) {
		return () -> {
			for (;;) {
				try {
					bQueue.put("hello " + id);
					Thread.sleep(timestamp);
				} catch (InterruptedException e) {
					return;
				}
			}
		};
	}
	
	private static Runnable consumer(BlockingQueue<String> bQueue) {
		return () -> {
			for (;;) {
				try {
					System.out.println(bQueue.take());
				} catch (InterruptedException e) {
					return;
				}
			}
		};
	}
	
	public static void main(String[] args) {
		var threads = new ArrayList<Thread>();
		var timestamps = List.of(2, 3, 5, 12, 19);
		var nbThread = 5;
		
		var arrayBQueue = new ArrayBlockingQueue<String>(nbThread);

		// producer
		IntStream.range(0, nbThread).forEach(i -> {
			threads.add(new Thread(producer(arrayBQueue, i, timestamps.get(i))));
		});
		
		for (var i = 0; i < nbThread; i++) {
			threads.get(i).start();
		}
		
		// consumer
		IntStream.range(0, nbThread).forEach(i -> {
			new Thread(consumer(arrayBQueue)).start();
		});
	}
}
