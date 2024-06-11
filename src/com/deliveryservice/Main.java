package com.deliveryservice;

import java.util.Scanner;

/**
 * 
 * @Author: AADARSH PATIL
 * Date: 6/11/2024
 * 
 * Main class to run the delivery service management application.
 */
public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		OrderList<String> orders = new OrderList<>();
		DeliveryRoute<String> deliveryRoute = new DeliveryRoute<>();
		InventoryCache<String, String> inventoryCache = new InventoryCache<>(5);

		while (true) {
			System.out.println("\nDelivery Service Management");
			System.out.println("1. Add Order");
			System.out.println("2. Process Order");
			System.out.println("3. Add Delivery Point");
			System.out.println("4. Remove Delivery Point");
			System.out.println("5. Add Inventory Item");
			System.out.println("6. Retrieve Inventory Item");
			System.out.println("7. Exit");
			System.out.print("Choose an option: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) {
			case 1:
				System.out.print("Enter order: ");
				String order = scanner.nextLine();
				orders.add(order);
				System.out.println("Order added.");
				break;
			case 2:
				if (orders.size() > 0) {
					System.out.println("Processed order: " + orders.remove(0));
				} else {
					System.out.println("No orders to process.");
				}
				break;
			case 3:
				System.out.print("Enter delivery point: ");
				String deliveryPoint = scanner.nextLine();
				deliveryRoute.add(deliveryPoint);
				System.out.println("Delivery point added.");
				break;
			case 4:
				System.out.print("Enter delivery point index to remove: ");
				int deliveryPointIndex = scanner.nextInt();
				scanner.nextLine(); // Consume newline
				try {
					deliveryRoute.remove(deliveryPointIndex);
					System.out.println("Delivery point removed.");
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Invalid index.");
				}
				break;
			case 5:
				System.out.print("Enter inventory item key: ");
				String key = scanner.nextLine();
				System.out.print("Enter inventory item value: ");
				String value = scanner.nextLine();
				inventoryCache.put(key, value);
				System.out.println("Inventory item added.");
				break;
			case 6:
				System.out.print("Enter inventory item key: ");
				String searchKey = scanner.nextLine();
				String retrievedValue = inventoryCache.get(searchKey);
				if (retrievedValue != null) {
					System.out.println("Retrieved item: " + retrievedValue);
				} else {
					System.out.println("Item not found in cache.");
				}
				break;
			case 7:
				System.out.println("Exiting...");
				scanner.close();
				System.exit(0);
				break;
			default:
				System.out.println("Invalid option. Please try again.");
			}
		}
	}
}