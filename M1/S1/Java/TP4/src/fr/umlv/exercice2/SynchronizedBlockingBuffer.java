package fr.umlv.exercice2;

import java.util.ArrayDeque;

public class SynchronizedBlockingBuffer {
	private final ArrayDeque<String> buffer;
	private final int capacity;
	
	public SynchronizedBlockingBuffer(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("capacity < 1");
		}
		this.capacity = capacity;
		this.buffer = new ArrayDeque<>(capacity);
	}
	
	public void put(String message) throws InterruptedException {
		synchronized (buffer) {
			while (buffer.size() == capacity) {
				buffer.wait();
			}
			buffer.addLast(message);
			buffer.notifyAll();
		}
	}
	
	public String take() throws InterruptedException {
		synchronized (buffer) {
			while (buffer.size() == 0) {
				buffer.wait();
			}
			buffer.notifyAll();
			return buffer.removeFirst();
		}
	}
}
