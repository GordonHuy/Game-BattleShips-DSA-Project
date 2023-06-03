package Version_5;

public class Ship {
    private String name;
    private int size;
    private int hitPoints;
    private int[][] location;
    private static int nextNumber = 1;
    private int number;

    public Ship(String name, int size, int[][] location) {
        setName(name);
        setSize(size);
        setHitPoints(size);
        setLocation(location);
        number = nextNumber++;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name can't be null or empty");
        }
        this.name = name.trim();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size value is not valid");
        }
        this.size = size;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        if (hitPoints < 0 || hitPoints > size) {
            throw new IllegalArgumentException("Hit points value is not valid");
        }
        this.hitPoints = hitPoints;
    }

    public int[][] getLocation() {
        return location;
    }

    public void setLocation(int[][] location) {
        if (location == null || location.length != size || location[0].length != 2) {
            throw new IllegalArgumentException("Invalid location array");
        }
        this.location = location;
    }

    public boolean isHorizontal() {
        int[] firstLocation = location[0];
        int[] secondLocation = location[1];
        return firstLocation[0] == secondLocation[0];
    }

    public boolean isSunk() {
        return hitPoints == 0;
    }

    public void hit() {
        hitPoints--;
    }

    public int getNumHits() {
        return size - hitPoints;
    }

    public String getType() {
        return this.name;
    }

    public int getHits() {
        return size - hitPoints;
    }
}
