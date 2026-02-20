import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CityGraph {

    private final Map<String, List<String>> adjacencyList;

    private final BinarySearchTree locationValidator;

    public CityGraph(BinarySearchTree locationValidator) {
        if (locationValidator == null) {
            throw new IllegalArgumentException("locationValidator (BST) must not be null.");
        }
        this.locationValidator = locationValidator;
        this.adjacencyList = new HashMap<>();
    }

    public void addLocation(String name) {
        String city = validateAndTrim(name);

        if (!locationValidator.search(city)) {
            System.out.println("[Graph] Cannot add \"" + city
                    + "\" – not found in the BST validator. "
                    + "Insert it into the BST first.");
            return;
        }

        String existingKey = findExistingKey(city);
        if (existingKey != null) {
            System.out.println("[Graph] Location \"" + existingKey
                    + "\" already exists in the graph.");
            return;
        }

        adjacencyList.put(city, new ArrayList<>());
        System.out.println("[Graph] Added location: \"" + city + "\"");
    }

    public void removeLocation(String name) {
        String city = validateAndTrim(name);
        String existingKey = findExistingKey(city);

        if (existingKey == null) {
            System.out.println("[Graph] Cannot remove \"" + city
                    + "\" – location not found in the graph.");
            return;
        }

        List<String> neighbours = adjacencyList.get(existingKey);
        for (String neighbour : neighbours) {
            String neighbourKey = findExistingKey(neighbour);
            if (neighbourKey != null) {
                adjacencyList.get(neighbourKey).remove(existingKey);
            }
        }

        adjacencyList.remove(existingKey);
        System.out.println("[Graph] Removed location \"" + existingKey
                + "\" and all its connected roads.");
    }

    public void addRoad(String source, String destination) {
        String src = validateAndTrim(source);
        String dest = validateAndTrim(destination);

        if (src.equalsIgnoreCase(dest)) {
            System.out.println("[Graph] Cannot add road – source and destination "
                    + "are the same city: \"" + src + "\"");
            return;
        }

        String srcKey = findExistingKey(src);
        String destKey = findExistingKey(dest);

        if (srcKey == null) {
            System.out.println("[Graph] Cannot add road – \"" + src
                    + "\" is not in the graph. Add it with addLocation() first.");
            return;
        }
        if (destKey == null) {
            System.out.println("[Graph] Cannot add road – \"" + dest
                    + "\" is not in the graph. Add it with addLocation() first.");
            return;
        }

        if (adjacencyList.get(srcKey).contains(destKey)) {
            System.out.println("[Graph] Road already exists between \""
                    + srcKey + "\" and \"" + destKey + "\".");
            return;
        }

        adjacencyList.get(srcKey).add(destKey);
        adjacencyList.get(destKey).add(srcKey);
        System.out.println("[Graph] Road added: \"" + srcKey + "\" ↔ \"" + destKey + "\"");
    }

    public void removeRoad(String source, String destination) {
        String src = validateAndTrim(source);
        String dest = validateAndTrim(destination);

        String srcKey = findExistingKey(src);
        String destKey = findExistingKey(dest);

        if (srcKey == null || destKey == null) {
            System.out.println("[Graph] Cannot remove road – one or both cities "
                    + "not found in the graph.");
            return;
        }

        boolean removed = adjacencyList.get(srcKey).remove(destKey)
                | adjacencyList.get(destKey).remove(srcKey);

        if (removed) {
            System.out.println("[Graph] Road removed: \"" + srcKey
                    + "\" ↔ \"" + destKey + "\"");
        } else {
            System.out.println("[Graph] No road found between \""
                    + srcKey + "\" and \"" + destKey + "\".");
        }
    }

    public void displayConnections() {
        if (adjacencyList.isEmpty()) {
            System.out.println("[Graph] The graph is empty – no locations added yet.");
            return;
        }

        System.out.println("[Graph] Current city connections:");

        List<String> sortedCities = new ArrayList<>(adjacencyList.keySet());
        Collections.sort(sortedCities, String.CASE_INSENSITIVE_ORDER);

        int maxLen = sortedCities.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);

        for (String city : sortedCities) {
            List<String> neighbours = new ArrayList<>(adjacencyList.get(city));
            Collections.sort(neighbours, String.CASE_INSENSITIVE_ORDER);

            System.out.printf("  %-" + (maxLen + 2) + "s→  %s%n",
                    city, neighbours.isEmpty() ? "[no roads]" : neighbours);
        }
    }

    public void showAllPaths(String start) {
        String startKey = findExistingKey(validateAndTrim(start));

        if (startKey == null) {
            System.out.println("[Graph] BFS Error: \"" + start.trim()
                    + "\" is not in the graph.");
            return;
        }

        System.out.println("[Graph] BFS traversal from \"" + startKey + "\":");

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(startKey);
        visited.add(startKey);

        int step = 1;

        while (!queue.isEmpty()) {
            String current = queue.poll();
            System.out.println("  Step " + step++ + ": " + current);

            List<String> neighbours = new ArrayList<>(adjacencyList.get(current));
            Collections.sort(neighbours, String.CASE_INSENSITIVE_ORDER);

            for (String neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }

        List<String> unreachable = new ArrayList<>();
        for (String city : adjacencyList.keySet()) {
            if (!visited.contains(city)) {
                unreachable.add(city);
            }
        }

        if (!unreachable.isEmpty()) {
            Collections.sort(unreachable, String.CASE_INSENSITIVE_ORDER);
            System.out.println("  [Unreachable from \"" + startKey + "\"]: "
                    + unreachable);
        } else {
            System.out.println("  All " + visited.size()
                    + " location(s) are reachable from \"" + startKey + "\".");
        }
    }

    private String findExistingKey(String name) {
        for (String key : adjacencyList.keySet()) {
            if (key.equalsIgnoreCase(name)) {
                return key;
            }
        }
        return null;
    }

    private String validateAndTrim(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("City name must not be null or blank.");
        }
        return name.trim();
    }

    public static void main(String[] args) {

        System.out.println("=== Smart City Route Planner – CityGraph Demo ===\n");

        BinarySearchTree bst = new BinarySearchTree();
        System.out.println("-- Populating BST --");
        bst.insert("Colombo");
        bst.insert("Kandy");
        bst.insert("Galle");
        bst.insert("Matara");
        bst.insert("Jaffna");
        bst.insert("Anuradhapura");
        System.out.println();

        CityGraph graph = new CityGraph(bst);

        System.out.println("-- addLocation --");
        graph.addLocation("Colombo");
        graph.addLocation("Kandy");
        graph.addLocation("Galle");
        graph.addLocation("Matara");
        graph.addLocation("Jaffna");
        graph.addLocation("Colombo");
        graph.addLocation("Badulla");
        System.out.println();

        System.out.println("-- addRoad --");
        graph.addRoad("Colombo", "Kandy");
        graph.addRoad("Colombo", "Galle");
        graph.addRoad("Galle", "Matara");
        graph.addRoad("Kandy", "Jaffna");
        graph.addRoad("Colombo", "Kandy");
        graph.addRoad("Colombo", "Colombo");
        graph.addRoad("Colombo", "Badulla");
        System.out.println();

        System.out.println("-- displayConnections (initial) --");
        graph.displayConnections();
        System.out.println();

        System.out.println("-- removeRoad --");
        graph.removeRoad("Colombo", "Galle");
        graph.removeRoad("Kandy", "Matara");
        System.out.println();

        System.out.println("-- removeLocation --");
        graph.removeLocation("Kandy");
        graph.removeLocation("Anuradhapura");
        System.out.println();

        System.out.println("-- displayConnections (after removals) --");
        graph.displayConnections();

        System.out.println("\n=== Demo complete ===");
    }
}
