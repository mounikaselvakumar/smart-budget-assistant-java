import java.util.*;

// Item Class
class Item {
    String name;
    int price;
    String category;

    Item(String name, int price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
}

// User Class
class User {
    String username;
    int budget;

    User(String username, int budget) {
        this.username = username;
        this.budget = budget;
    }
}

// Cart Class
class Cart {
    ArrayList<Item> items = new ArrayList<>();
    int total = 0;

    void addItem(Item item) {
        items.add(item);
        total += item.price;
    }

    void viewCart() {
        System.out.println("\nItems in Cart:");
        for (Item i : items) {
            System.out.println(i.name + " - ₹" + i.price);
        }
        System.out.println("Total: ₹" + total);
    }
}

// Main Class
public class Main {

    static HashMap<String, Integer> coupons = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Sample Data
        ArrayList<Item> store = new ArrayList<>();
        store.add(new Item("Shoes", 300, "Clothing"));
        store.add(new Item("Shirt", 200, "Clothing"));
        store.add(new Item("Watch", 250, "Accessories"));
        store.add(new Item("Bag", 150, "Accessories"));

        // Coupons
        coupons.put("SAVE10", 10);
        coupons.put("SAVE20", 20);

        System.out.print("Enter Username: ");
        String name = sc.nextLine();

        System.out.print("Enter Budget: ");
        int budget = sc.nextInt();

        User user = new User(name, budget);
        Cart cart = new Cart();
        ArrayList<Item> wishlist = new ArrayList<>();

        while (true) {
            System.out.println("\n1.Add Item\n2.View Cart\n3.Suggestions\n4.Wishlist\n5.Apply Coupon\n6.Exit");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    for (int i = 0; i < store.size(); i++) {
                        System.out.println(i + ". " + store.get(i).name + " ₹" + store.get(i).price);
                    }
                    System.out.print("Choose item index: ");
                    int index = sc.nextInt();

                    Item selected = store.get(index);

                    if (cart.total + selected.price <= user.budget) {
                        cart.addItem(selected);
                        System.out.println("Added to cart!");
                    } else {
                        System.out.println("Budget exceeded! Try suggestions.");
                    }
                    break;

                case 2:
                    cart.viewCart();
                    break;

                case 3:
                    suggestItems(store, user.budget);
                    break;

                case 4:
                    System.out.print("Enter item index to add to wishlist: ");
                    int w = sc.nextInt();
                    wishlist.add(store.get(w));
                    System.out.println("Added to wishlist!");
                    break;

                case 5:
                    System.out.print("Enter coupon: ");
                    sc.nextLine();
                    String code = sc.nextLine();

                    if (coupons.containsKey(code)) {
                        int discount = coupons.get(code);
                        int saved = (cart.total * discount) / 100;
                        cart.total -= saved;
                        System.out.println("Coupon applied! Saved ₹" + saved);
                    } else {
                        System.out.println("Invalid coupon!");
                    }
                    break;

                case 6:
                    System.out.println("Thank you!");
                    return;
            }
        }
    }

    // PriorityQueue Suggestion
    static void suggestItems(ArrayList<Item> store, int budget) {
        PriorityQueue<Item> pq = new PriorityQueue<>(
                (a, b) -> a.price - b.price
        );

        pq.addAll(store);

        int total = 0;
        System.out.println("\nSuggested items within budget:");

        while (!pq.isEmpty() && total + pq.peek().price <= budget) {
            Item item = pq.poll();
            total += item.price;
            System.out.println(item.name + " ₹" + item.price);
        }
    }
}