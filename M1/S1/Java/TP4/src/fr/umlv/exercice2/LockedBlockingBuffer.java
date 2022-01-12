package fr.umlv.exercice2;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockedBlockingBuffer {
	private final ArrayDeque<String> buffer;
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition isEmpty = lock.newCondition();
	private final Condition isFull = lock.newCondition();
	private final int capacity;
	
	public LockedBlockingBuffer(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("capacity < 1");
		}
		this.capacity = capacity;
		this.buffer = new ArrayDeque<>(capacity);
	}
	
	public void put(String message) throws InterruptedException {
		lock.lock();
		try {
			while (buffer.size() == capacity) {
				isFull.await();
			}
			buffer.addLast(message);
			isEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	public String take() throws InterruptedException {
		lock.lock();
		try {
			while (buffer.size() == 0) {
				isEmpty.await();
			}
			isFull.signalAll();
			return buffer.removeFirst();
		} finally {
			lock.unlock();
		}
	}
}
