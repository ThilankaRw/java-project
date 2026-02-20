public class BinarySearchTree {

    private LocationNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(String name) {
        validateName(name);
        root = insertRecursive(root, name.trim());
    }

    public boolean search(String name) {
        validateName(name);
        return searchRecursive(root, name.trim());
    }

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

    public boolean isEmpty() {
        return root == null;
    }

    public void printInOrder() {
        if (isEmpty()) {
            System.out.println("[BST] The tree is empty.");
            return;
        }
        System.out.print("[BST] Cities (alphabetical order): ");
        inOrderTraversal(root);
        System.out.println();
    }

    private LocationNode insertRecursive(LocationNode node, String name) {
        if (node == null) {
            System.out.println("[BST] Inserted city: \"" + name + "\"");
            return new LocationNode(name);
        }

        int cmp = name.compareToIgnoreCase(node.cityName);

        if (cmp < 0) {
            node.left = insertRecursive(node.left, name);
        } else if (cmp > 0) {
            node.right = insertRecursive(node.right, name);
        } else {
            System.out.println("[BST] Duplicate ignored: \"" + name + "\" already exists.");
        }

        return node;
    }

    private boolean searchRecursive(LocationNode node, String name) {
        if (node == null) {
            return false;
        }

        int cmp = name.compareToIgnoreCase(node.cityName);

        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return searchRecursive(node.left, name);
        } else {
            return searchRecursive(node.right, name);
        }
    }

    private LocationNode deleteRecursive(LocationNode node, String name) {
        if (node == null) {
            return null;
        }

        int cmp = name.compareToIgnoreCase(node.cityName);

        if (cmp < 0) {
            node.left = deleteRecursive(node.left, name);

        } else if (cmp > 0) {
            node.right = deleteRecursive(node.right, name);

        } else {

            if (node.left == null && node.right == null) {
                return null;
            }

            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            LocationNode successor = findMin(node.right);

            node.cityName = successor.cityName;

            node.right = deleteRecursive(node.right, successor.cityName);
        }

        return node;
    }

    private LocationNode findMin(LocationNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

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

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("City name must not be null or blank.");
        }
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        System.out.println("=== Smart City Route Planner – BST Demo ===\n");

        System.out.println("-- Inserting cities --");
        bst.insert("Kandy");
        bst.insert("Colombo");
        bst.insert("Galle");
        bst.insert("Matara");
        bst.insert("Jaffna");
        bst.insert("Anuradhapura");
        bst.insert("Colombo");
        bst.insert("Galle");

        System.out.println();
        bst.printInOrder();

        System.out.println("\n-- Searching cities --");
        String[] toSearch = { "Galle", "Matara", "Badulla", "colombo" };
        for (String city : toSearch) {
            System.out.printf("Search \"%s\": %s%n", city, bst.search(city) ? "FOUND" : "NOT FOUND");
        }

        System.out.println("\n-- Deleting cities --");
        bst.delete("Kandy");
        bst.delete("Jaffna");
        bst.delete("Badulla");
        System.out.println();
        bst.printInOrder();

        System.out.println("\n=== Demo complete ===");
    }
}
