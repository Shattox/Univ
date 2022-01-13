package fr.umlv.exercice1.question4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class ArrayBQueue {
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
	
	public static void main(String[] args) {
		var threads = new ArrayList<Thread>();
		var timestamps = List.of(1, 4);
		var nbThread = 2;
		
		var arrayBQueue = new ArrayBlockingQueue<String>(nbThread);

		IntStream.range(0, nbThread).forEach(i -> {
			threads.add(new Thread(producer(arrayBQueue, i, timestamps.get(i))));
		});

		for (var i = 0; i < nbThread; i++) {
			threads.get(i).start();
		}
	}
}
