package application.Java_files.Model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Library {

    private ShoppingCart shoppingCart;

    // Constructor to initialize the Library with a ShoppingCart
    public Library() {
        this.shoppingCart = new ShoppingCart();
    }

    // Library menu for user interaction
    public void libraryMenu() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("Welcome to Reading Room!");
        System.out.println("========================================");

        boolean exitFromReadingRoom = false; // Flag to control the loop for menu options

        // Loop to display the menu and handle user choices
        while (!exitFromReadingRoom) {
            System.out.println("\nChoose an option:");
            System.out.println("1. List all books");
            System.out.println("2. Search for books");
            System.out.println("3. Add a book to the shopping cart");
            System.out.println("4. View Shopping Cart");
            System.out.println("5. Remove a book from the shopping cart");
            System.out.println("6. Clear all items from your cart");
            System.out.println("7. Proceed to Checkout");
            System.out.println("8. Quit");
            System.out.print("Please Enter your choice: ");

            try {
                int choice = userInput.nextInt(); // Get the user's choice
                userInput.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        ReadingRoom.listAllBooks(); // List all books
                        break;
                    case 2:
                        ReadingRoom.searchForBooks(); // Search for books
                        break;
                    case 3:
                        shoppingCart.addToCart(); // Add a book to the shopping cart
                        break;
                    case 4:
                        shoppingCart.displayCart(); // View the shopping cart
                        break;
                    case 5:
                        shoppingCart.removeBook(); // Remove a book from the cart
                        break;
                    case 6:
                        shoppingCart.clearCart(); // Clear all items from the cart
                        break;
                    case 7:
                        if (shoppingCart.proceedToCheckout()) {
                            exitFromReadingRoom = true;
                            System.out.println("Thank you for your purchase. See you soon!");
                        }
                        break;
                    case 8:
                        exitFromReadingRoom = true; // Quit the application
                        System.out.println("Thank you for visiting the Reading Room. See you soon!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                userInput.nextLine(); // Clear the invalid input
            }
        }

        userInput.close(); // Close the Scanner to avoid resource leaks
    }

    // Main method to run the application
    public static void main(String[] args) {
        Library library = new Library();
        library.libraryMenu();
    }
}
