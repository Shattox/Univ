package fr.uge.thread;

import java.util.ArrayList;
import java.util.List;

public class ThreadSafeList {
	private final List<Integer> list = new ArrayList<>();
	
	public void add(int thread) {
		synchronized(list) {
			list.add(thread);
		}
	}
	
	public int size() {
		synchronized(list) {
			return list.size();
		}
	}
}
