public class Tile {
    public int value;
    public Tile() {
        // we can change this value to "X" or "O".
        this.value = 0;
    }

    public int getValue() {
        return this.value;
    }
    public void setValue(int val) {
        this.value = val;
    }
}