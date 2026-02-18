/**
 * BinarySearchTree.java
 *
 * A Binary Search Tree (BST) that stores city location names alphabetically
 * (case-insensitive). This class is part of the Smart City Route Planner
 * foundation and is used to validate and manage known city locations before
 * they are added to a routing graph.
 *
 * <p>
 * <b>Key properties:</b>
 * </p>
 * <ul>
 * <li>Alphabetical ordering (case-insensitive string comparison).</li>
 * <li>Duplicate city names are silently ignored (no duplicates stored).</li>
 * <li>Supports insert, search, and delete operations in O(h) time,
 * where h is the height of the tree.</li>
 * <li>An in-order traversal prints all cities in sorted alphabetical
 * order.</li>
 * </ul>
 *
 * <p>
 * <b>Usage example:</b>
 * </p>
 * 
 * <pre>
 * BinarySearchTree bst = new BinarySearchTree();
 * bst.insert("Kandy");
 * bst.insert("Colombo");
 * bst.insert("Galle");
 * bst.insert("Colombo"); // duplicate – silently ignored
 *
 * System.out.println(bst.search("Galle")); // true
 * System.out.println(bst.search("Matara")); // false
 *
 * bst.delete("Kandy");
 * bst.printInOrder(); // Colombo, Galle
 * </pre>
 *
 * @author Smart City Route Planner Team
 * @version 1.0
 * @see LocationNode
 */
public class BinarySearchTree {

    // -----------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------

    /** The root node of the BST. {@code null} when the tree is empty. */
    private LocationNode root;

    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------

    /**
     * Constructs an empty Binary Search Tree.
     * The root is initialised to {@code null}.
     */
    public BinarySearchTree() {
        this.root = null;
    }

    // -----------------------------------------------------------------------
    // Public API
    // -----------------------------------------------------------------------

    /**
     * Inserts a city name into the BST in alphabetical (case-insensitive) order.
     *
     * <p>
     * If the city name already exists in the tree, the insertion is silently
     * ignored to avoid duplicates. Leading/trailing whitespace is trimmed before
     * comparison and storage.
     * </p>
     *
     * @param name the city name to insert; must not be {@code null} or blank
     * @throws IllegalArgumentException if {@code name} is null or blank
     */
    public void insert(String name) {
        validateName(name);
        root = insertRecursive(root, name.trim());
    }

    /**
     * Searches the BST for the specified city name (case-insensitive).
     *
     * @param name the city name to search for; must not be {@code null} or blank
     * @return {@code true} if the city exists in the BST, {@code false} otherwise
     * @throws IllegalArgumentException if {@code name} is null or blank
     */
    public boolean search(String name) {
        validateName(name);
        return searchRecursive(root, name.trim());
    }

    /**
     * Deletes the specified city name from the BST (case-insensitive).
     *
     * <p>
     * If the city name is not found, the tree remains unchanged and a
     * message is printed to standard output.
     * </p>
     *
     * <p>
     * Deletion follows the standard BST delete algorithm:
     * </p>
     * <ul>
     * <li><b>Leaf node</b> – simply removed.</li>
     * <li><b>One child</b> – replaced by its child.</li>
     * <li><b>Two children</b> – replaced by its in-order successor
     * (smallest value in the right subtree), which is then deleted
     * from the right subtree.</li>
     * </ul>
     *
     * @param name the city name to delete; must not be {@code null} or blank
     * @throws IllegalArgumentException if {@code name} is null or blank
     */
    public void delete(String name) {
        validateName(name);
        String trimmed = name.trim();
        if (!search(trimmed)) {
            System.out.println("[BST] City not found – cannot delete: \"" + trimmed + "\"");
            return;
        }
        root = deleteRecursive(root, trimmed);
        System.out.println("[BST] Deleted city: \"" + trimmed + "\"");
    }

    /**
     * Returns whether the BST is empty (contains no city nodes).
     *
     * @return {@code true} if the tree is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Prints all city names stored in the BST to standard output in
     * ascending alphabetical order (in-order traversal).
     * If the tree is empty, a suitable message is printed instead.
     */
    public void printInOrder() {
        if (isEmpty()) {
            System.out.println("[BST] The tree is empty.");
            return;
        }
        System.out.print("[BST] Cities (alphabetical order): ");
        inOrderTraversal(root);
        System.out.println(); // newline after list
    }

    // -----------------------------------------------------------------------
    // Private helper methods
    // -----------------------------------------------------------------------

    /**
     * Recursively inserts a city name into the subtree rooted at {@code node}.
     *
     * <p>
     * Comparison is case-insensitive. Duplicates are rejected by returning
     * the existing node unmodified when equality is detected.
     * </p>
     *
     * @param node the current subtree root (may be {@code null})
     * @param name the trimmed city name to insert
     * @return the (potentially new) root of the subtree after insertion
     */
    private LocationNode insertRecursive(LocationNode node, String name) {
        // Base case: empty spot found – create a new node here
        if (node == null) {
            System.out.println("[BST] Inserted city: \"" + name + "\"");
            return new LocationNode(name);
        }

        int cmp = name.compareToIgnoreCase(node.cityName);

        if (cmp < 0) {
            // name is alphabetically smaller → go left
            node.left = insertRecursive(node.left, name);
        } else if (cmp > 0) {
            // name is alphabetically larger → go right
            node.right = insertRecursive(node.right, name);
        } else {
            // cmp == 0 → duplicate detected, ignore silently
            System.out.println("[BST] Duplicate ignored: \"" + name + "\" already exists.");
        }

        return node; // return unchanged node pointer on the way back up
    }

    /**
     * Recursively searches for a city name in the subtree rooted at {@code node}.
     *
     * @param node the current subtree root (may be {@code null})
     * @param name the trimmed city name to search for
     * @return {@code true} if found, {@code false} otherwise
     */
    private boolean searchRecursive(LocationNode node, String name) {
        // Base case: subtree is empty → not found
        if (node == null) {
            return false;
        }

        int cmp = name.compareToIgnoreCase(node.cityName);

        if (cmp == 0) {
            return true; // found
        } else if (cmp < 0) {
            return searchRecursive(node.left, name); // search left subtree
        } else {
            return searchRecursive(node.right, name); // search right subtree
        }
    }

    /**
     * Recursively deletes a city name from the subtree rooted at {@code node}.
     *
     * <p>
     * This method assumes the city name has already been confirmed present
     * via a prior {@link #search(String)} call.
     * </p>
     *
     * @param node the current subtree root (may be {@code null})
     * @param name the trimmed city name to delete
     * @return the (potentially new) root of the subtree after deletion
     */
    private LocationNode deleteRecursive(LocationNode node, String name) {
        if (node == null) {
            return null; // city not found (safety guard – should not reach here)
        }

        int cmp = name.compareToIgnoreCase(node.cityName);

        if (cmp < 0) {
            // Target is in the left subtree
            node.left = deleteRecursive(node.left, name);

        } else if (cmp > 0) {
            // Target is in the right subtree
            node.right = deleteRecursive(node.right, name);

        } else {
            // ---- This is the node to delete ----

            // Case 1: Leaf node (no children)
            if (node.left == null && node.right == null) {
                return null;
            }

            // Case 2a: Only right child exists
            if (node.left == null) {
                return node.right;
            }

            // Case 2b: Only left child exists
            if (node.right == null) {
                return node.left;
            }

            // Case 3: Node has two children
            // Find the in-order successor: smallest node in the right subtree
            LocationNode successor = findMin(node.right);

            // Replace this node's city name with the successor's city name
            node.cityName = successor.cityName;

            // Delete the successor from the right subtree
            node.right = deleteRecursive(node.right, successor.cityName);
        }

        return node; // return (possibly updated) node
    }

    /**
     * Finds the node with the smallest city name in the subtree rooted at
     * {@code node} by following left children until none remain.
     *
     * <p>
     * Used internally to locate the in-order successor during deletion.
     * </p>
     *
     * @param node the root of the subtree to search; must not be {@code null}
     * @return the leftmost (minimum) node in the subtree
     */
    private LocationNode findMin(LocationNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Performs an in-order (left → root → right) traversal of the subtree
     * rooted at {@code node}, printing each city name separated by commas.
     *
     * @param node the current subtree root (may be {@code null})
     */
    private void inOrderTraversal(LocationNode node) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left);
        System.out.print(node.cityName);
        if (node.right != null) {
            System.out.print(", ");
        }
        inOrderTraversal(node.right);
    }

    /**
     * Validates that a city name argument is neither {@code null} nor blank.
     *
     * @param name the city name to validate
     * @throws IllegalArgumentException if {@code name} is null or blank
     */
    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("City name must not be null or blank.");
        }
    }

    // -----------------------------------------------------------------------
    // Main – quick smoke test
    // -----------------------------------------------------------------------

    /**
     * A simple smoke-test entry point demonstrating all three BST operations.
     * Remove or replace this with your own test harness as the project grows.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        System.out.println("=== Smart City Route Planner – BST Demo ===\n");

        // --- Insert cities ---
        System.out.println("-- Inserting cities --");
        bst.insert("Kandy");
        bst.insert("Colombo");
        bst.insert("Galle");
        bst.insert("Matara");
        bst.insert("Jaffna");
        bst.insert("Anuradhapura");
        bst.insert("Colombo"); // duplicate – should be ignored
        bst.insert("Galle"); // duplicate – should be ignored

        System.out.println();
        bst.printInOrder();

        // --- Search cities ---
        System.out.println("\n-- Searching cities --");
        String[] toSearch = { "Galle", "Matara", "Badulla", "colombo" /* case-insensitive */ };
        for (String city : toSearch) {
            System.out.printf("Search \"%s\": %s%n", city, bst.search(city) ? "FOUND" : "NOT FOUND");
        }

        // --- Delete cities ---
        System.out.println("\n-- Deleting cities --");
        bst.delete("Kandy"); // node with two children
        bst.delete("Jaffna"); // leaf node
        bst.delete("Badulla"); // not present
        System.out.println();
        bst.printInOrder();

        System.out.println("\n=== Demo complete ===");
    }
}
