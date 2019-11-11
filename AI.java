public class AI {
	public double learningRate;
    public double exploration;
    public double decay;
    public double discount;
    public int[] currentState;

	public AI() {
        this.learningRate = 0.3;
        this.exploration = 0.9;
        this.decay = 0.01;
        this.discount = 0.01;
        
    }

    // this method changes the utility values of each tile, based on if we are rewarding it
    // or punishing it.
    public void reward(int value) {


    }

    // this method updates utility values.
    public int temporalDifference(int reward) {
        // need to implement.
        this.exploration = Math.max(this.exploration - decay, 0.25);
        return 0;
    }




}