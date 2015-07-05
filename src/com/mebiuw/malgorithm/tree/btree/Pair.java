package com.mebiuw.malgorithm.tree.btree;

public class Pair<K extends Comparable<K>,V> implements Comparable<K> {
	private K key;
	private V value;

	public Pair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	@Override
	public int compareTo(K o) {
		return key.compareTo(o);
	}

	

}
