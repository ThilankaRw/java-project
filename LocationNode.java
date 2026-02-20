public class LocationNode {

    public String cityName;

    public LocationNode left;

    public LocationNode right;

    public LocationNode(String cityName) {
        if (cityName == null || cityName.isBlank()) {
            throw new IllegalArgumentException("City name must not be null or blank.");
        }
        this.cityName = cityName.trim();
        this.left = null;
        this.right = null;
    }

    @Override
    public String toString() {
        return "LocationNode{cityName='" + cityName + "'}";
    }
}
