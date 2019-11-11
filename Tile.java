public class Tile {
    public String value;
    public Tile() {
        // we can change this value to "X" or "O".
        this.value = " ";
    }



    public String getValue() {
        return this.value;
    }
    public void setValue(String val) {
        this.value = val;
    }
}