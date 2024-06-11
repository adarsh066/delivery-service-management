package com.deliveryservice;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Author: AADARSH PATIL
 * Date: 6/11/2024
 * 
 * InventoryCache is a custom implementation of an LRU (Least Recently Used)
 * cache using a doubly linked list and a hash map for managing inventory items.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public class InventoryCache<K, V> {
	private final int capacity; // Maximum capacity of the cache
	private final Map<K, Node<K, V>> map; // Map to store key-node pairs
	private final CustomLinkedList<K, V> list; // Doubly linked list to store the order of elements

	/**
	 * Constructs an InventoryCache with the specified capacity.
	 *
	 * @param capacity the maximum capacity of the cache
	 */
	public InventoryCache(int capacity) {
		this.capacity = capacity;
		this.map = new HashMap<>();
		this.list = new CustomLinkedList<>();
	}

	/**
	 * Retrieves the value associated with the specified key. Moves the accessed
	 * element to the head of the list.
	 *
	 * @param key the key whose associated value is to be returned
	 * @return the value associated with the specified key, or null if the key does
	 *         not exist
	 */
	public V get(K key) {
		Node<K, V> node = map.get(key);
		if (node == null) {
			return null;
		}
		list.moveToHead(node);
		return node.value;
	}

	/**
	 * Adds a key-value pair to the cache. If the cache is full, evicts the least
	 * recently used element.
	 *
	 * @param key   the key with which the specified value is to be associated
	 * @param value the value to be associated with the specified key
	 */
	public void put(K key, V value) {
		Node<K, V> node = map.get(key);
		if (node == null) {
			node = new Node<>(key, value);
			map.put(key, node);
			list.addToHead(node);
			if (map.size() > capacity) {
				Node<K, V> tail = list.removeTail();
				map.remove(tail.key);
			}
		} else {
			node.value = value;
			list.moveToHead(node);
		}
	}

	/**
	 * Node class represents each element in the doubly linked list used by
	 * InventoryCache.
	 *
	 * @param <K> the type of keys maintained by this node
	 * @param <V> the type of mapped values
	 */
	private static class Node<K, V> {
		K key;
		V value;
		Node<K, V> prev;
		Node<K, V> next;

		Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	/**
	 * CustomLinkedList class represents a doubly linked list used by InventoryCache
	 * to maintain the order of elements based on their usage.
	 *
	 * @param <K> the type of keys maintained by this list
	 * @param <V> the type of mapped values
	 */
	private static class CustomLinkedList<K, V> {
		private Node<K, V> head;
		private Node<K, V> tail;

		/**
		 * Adds the specified node to the head of the list.
		 *
		 * @param node the node to be added to the head of the list
		 */
		void addToHead(Node<K, V> node) {
			if (head == null) {
				head = tail = node;
			} else {
				node.next = head;
				head.prev = node;
				head = node;
			}
		}

		/**
		 * Moves the specified node to the head of the list.
		 *
		 * @param node the node to be moved to the head of the list
		 */
		void moveToHead(Node<K, V> node) {
			if (node == head) {
				return;
			}
			if (node == tail) {
				tail = tail.prev;
				tail.next = null;
			} else {
				node.prev.next = node.next;
				node.next.prev = node.prev;
			}
			node.next = head;
			node.prev = null;
			head.prev = node;
			head = node;
		}

		/**
		 * Removes and returns the node at the tail of the list.
		 *
		 * @return the node at the tail of the list
		 */
		Node<K, V> removeTail() {
			if (tail == null) {
				return null;
			}
			Node<K, V> removed = tail;
			if (tail == head) {
				head = tail = null;
			} else {
				tail = tail.prev;
				tail.next = null;
			}
			removed.prev = null;
			return removed;
		}
	}
}