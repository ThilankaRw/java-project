/**
 * LocationNode.java
 *
 * Represents a single node in the Binary Search Tree (BST) used by the
 * Smart City Route Planner. Each node stores the name of a city location
 * and holds references to its left and right child nodes.
 *
 * <p>Nodes are ordered alphabetically (case-insensitive) within the BST,
 * allowing fast lookups and sorted traversal of city names.</p>
 *
 * <p>Usage example:</p>
 * <pre>
 *   LocationNode node = new LocationNode("Colombo");
 *   System.out.println(node.cityName); // Colombo
 * </pre>
 *
 * @author  Smart City Route Planner Team
 * @version 1.0
 */
public class LocationNode {

    /** The name of the city stored in this node. */
    public String cityName;

    /** Reference to the left child node (alphabetically smaller city name). */
    public LocationNode left;

    /** Reference to the right child node (alphabetically larger city name). */
    public LocationNode right;

    /**
     * Constructs a new LocationNode with the given city name.
     * Left and right child references are initialized to {@code null}.
     *
     * @param cityName the name of the city to store in this node;
     *                 must not be {@code null} or empty
     * @throws IllegalArgumentException if {@code cityName} is null or blank
     */
    public LocationNode(String cityName) {
        if (cityName == null || cityName.isBlank()) {
            throw new IllegalArgumentException("City name must not be null or blank.");
        }
        this.cityName = cityName.trim();
        this.left     = null;
        this.right    = null;
    }

    /**
     * Returns a string representation of this node showing the stored city name.
     *
     * @return the city name stored in this node
     */
    @Override
    public String toString() {
        return "LocationNode{cityName='" + cityName + "'}";
    }
}
