package fr.umlv.conc.exercice3;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.VarHandle;
import java.util.HashSet;

public class RandomNumberGenerator {
	private volatile long x;
	private final static VarHandle varHandle;
	
	static {
		Lookup lookup = MethodHandles.lookup();
		try {
			varHandle = lookup.findVarHandle(RandomNumberGenerator.class, "x", long.class);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new AssertionError(e);
		}
	}
  
	public RandomNumberGenerator(long seed) {
		if (seed == 0) {
			throw new IllegalArgumentException("seed == 0");
		}
		x = seed;
	}
	
	public static long nextValue(long previous) {
		previous ^= previous >>> 12;
		previous ^= previous << 25;
		previous ^= previous >>> 27;
		return previous;
	}
	
	public long next() {  // Marsaglia's XorShift
		for (;;) {
			var x = this.x;
			var newX = nextValue(x);
			if (varHandle.compareAndSet(this, x, newX)) {
				return newX * 2685821657736338717L;
			}
		}
	}
  
	public static void main(String[] args) throws InterruptedException {
		var set0 = new HashSet<Long>();
		var set1 = new HashSet<Long>();
		var set2 = new HashSet<Long>();
    	var rng0 = new RandomNumberGenerator(1);
    	var rng = new RandomNumberGenerator(1);

    	for (var i = 0; i < 10_000; i++) {
    		set0.add(rng0.next());
    	}

    	var t = new Thread(() -> {
    		for (var i = 0; i < 5_000; i++) {
    			set1.add(rng.next());
    		}
    	});
    	t.start();
		for (var i = 0; i < 5_000; i++) {
		  set2.add(rng.next());
		}
		t.join();
		
		System.out.println("set1: " + set1.size() + ", set2: " + set2.size());
		
		set1.addAll(set2);
		System.out.println("union (should be 10 000): " + set1.size());
		
		System.out.println("intersection (should be true): " + set0.containsAll(set1));
		
		set0.removeAll(set1);
		System.out.println("intersection (should be 0): " + set0.size());
  }
}
