package fr.umlv.exercice1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Question3 {
	private static Runnable producer(int id, int timestamp) {
		return () -> {
			for (;;) {
				try {
					System.out.println("hello " + id);
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

		IntStream.range(0, nbThread).forEach(i -> {
			threads.add(new Thread(producer(i, timestamps.get(i))));
		});
		
		for (var i = 0; i < nbThread; i++) {
			threads.get(i).start();
		}
	}
}
