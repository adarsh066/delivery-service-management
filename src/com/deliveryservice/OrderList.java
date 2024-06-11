package com.deliveryservice;

import java.util.AbstractList;
import java.util.Arrays;

/**
 * 
 * @Author: AADARSH PATIL
 * Date: 6/11/2024
 * 
 * OrderList is a custom implementation of a list that manages customer orders.
 * It extends AbstractList to provide a dynamic array-backed list.
 *
 * @param <E> the type of elements in this list
 */
public class OrderList<E> extends AbstractList<E> {
	private Object[] elements; // Array to store elements
	private int size; // Current size of the list

	/**
	 * Constructs an empty OrderList with an initial capacity of 10.
	 */
	public OrderList() {
		elements = new Object[10];
		size = 0;
	}

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param index index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	@Override
	public E get(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		return (E) elements[index];
	}

	/**
	 * Appends the specified element to the end of this list.
	 *
	 * @param e element to be appended to this list
	 * @return true (as specified by {@link Collection#add})
	 */
	@Override
	public boolean add(E e) {
		if (size == elements.length) {
			elements = Arrays.copyOf(elements, size * 2); // Resize array if needed
		}
		elements[size++] = e;
		return true;
	}

	/**
	 * Removes the element at the specified position in this list. Shifts any
	 * subsequent elements to the left.
	 *
	 * @param index the index of the element to be removed
	 * @return the element that was removed from the list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	@Override
	public E remove(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		E oldValue = (E) elements[index];
		int numMoved = size - index - 1;
		if (numMoved > 0) {
			System.arraycopy(elements, index + 1, elements, index, numMoved);
		}
		elements[--size] = null; // Clear to let GC do its work
		return oldValue;
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