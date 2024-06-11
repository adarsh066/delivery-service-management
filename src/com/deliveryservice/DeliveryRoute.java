package com.deliveryservice;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * 
 * @Author: AADARSH PATIL
 * Date: 6/11/2024
 * 
 * DeliveryRoute is a custom implementation of a list that manages the sequence
 * of delivery points. It extends AbstractSequentialList to provide a doubly
 * linked list-backed list.
 *
 * @param <E> the type of elements in this list
 */
public class DeliveryRoute<E> extends AbstractSequentialList<E> {
	private Node<E> head; // Head of the list
	private Node<E> tail; // Tail of the list
	private int size; // Current size of the list

	/**
	 * Node class represents each element in the doubly linked list.
	 *
	 * @param <E> the type of element
	 */
	private static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;

		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

	/**
	 * Constructs an empty DeliveryRoute.
	 */
	public DeliveryRoute() {
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Returns a list iterator over the elements in this list (in proper sequence),
	 * starting at the specified position in the list.
	 *
	 * @param index index of the first element to be returned from the list iterator
	 * @return a ListIterator of the elements in this list (in proper sequence),
	 *         starting at the specified position in the list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new ListItr(index);
	}

	/**
	 * ListItr class provides an iterator for the list.
	 */
	private class ListItr implements ListIterator<E> {
		private Node<E> lastReturned;
		private Node<E> next;
		private int nextIndex;

		ListItr(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
			}
			if (index == size) {
				next = null;
				nextIndex = size;
			} else {
				next = node(index);
				nextIndex = index;
			}
		}

		public boolean hasNext() {
			return nextIndex < size;
		}

		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastReturned = next;
			next = next.next;
			nextIndex++;
			return lastReturned.item;
		}

		public boolean hasPrevious() {
			return nextIndex > 0;
		}

		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			lastReturned = next = (next == null) ? tail : next.prev;
			nextIndex--;
			return lastReturned.item;
		}

		public int nextIndex() {
			return nextIndex;
		}

		public int previousIndex() {
			return nextIndex - 1;
		}

		public void remove() {
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			Node<E> lastNext = lastReturned.next;
			Node<E> lastPrev = lastReturned.prev;
			if (lastPrev == null) {
				head = lastNext;
			} else {
				lastPrev.next = lastNext;
				lastReturned.prev = null;
			}
			if (lastNext == null) {
				tail = lastPrev;
			} else {
				lastNext.prev = lastPrev;
				lastReturned.next = null;
			}
			lastReturned.item = null;
			lastReturned = null;
			size--;
			nextIndex--;
		}

		public void set(E e) {
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			lastReturned.item = e;
		}

		public void add(E e) {
			lastReturned = null;
			if (next == null) {
				Node<E> newNode = new Node<>(tail, e, null);
				if (tail == null) {
					head = newNode;
				} else {
					tail.next = newNode;
				}
				tail = newNode;
			} else {
				Node<E> newNode = new Node<>(next.prev, e, next);
				next.prev = newNode;
				if (newNode.prev == null) {
					head = newNode;
				} else {
					newNode.prev.next = newNode;
				}
			}
			size++;
			nextIndex++;
		}
	}

	/**
	 * Returns the node at the specified position in the list.
	 *
	 * @param index the index of the node to return
	 * @return the node at the specified position in the list
	 */
	Node<E> node(int index) {
		if (index < (size >> 1)) {
			Node<E> x = head;
			for (int i = 0; i < index; i++) {
				x = x.next;
			}
			return x;
		} else {
			Node<E> x = tail;
			for (int i = size - 1; i > index; i--) {
				x = x.prev;
			}
			return x;
		}
	}

	/**
	 * Returns the number of elements in this list.
	 *
	 * @return the number of elements in this list
	 */
	@Override
	public int size() {
		return size;
	}
}