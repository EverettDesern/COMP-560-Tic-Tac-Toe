import java.util.*;
    
public class AI {
	public double learningRate;
    public double exploration;
    public double decay;
    public double discount;
    public int[] currentState;
    public HashMap<double[][][], double[][][]> states;
    public ArrayList<double[][][]> state_order;
    public ArrayList<double[][][]> actions;

	public AI() {
        this.learningRate = 0.3;
        this.exploration = 0.9;
        this.decay = 0.01;
        this.discount = 0.01;
        states = new HashMap<>();
        state_order = new ArrayList<>();
        actions = new ArrayList<>();
    }

    // this method changes the utility values of each tile, based on if we are rewarding it
    // or punishing it.
    public void reward(int value) {

        if(this.state_order.size() == 0 && this.actions.size() == 0) {
            return;
        }
        double[][][] newState = this.state_order.get(this.state_order.size()-1);
        this.state_order.remove(this.state_order.size()-1);

        double[][][] newAction = this.actions.get(this.actions.size()-1);
        this.actions.remove(this.actions.size()-1);

        double[][][] blah = new double[4][4][4];
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {
                    blah[i][j][p] = 0.0;
                }
            }
        }
        this.states.put(newState, blah);
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {
                    newAction[i][j][p] = newAction[i][j][p] * value;
                }
            }
        }

        // not sure if this is right.
        this.states.replace(newState, newAction);

        while(this.state_order.size() > 1) {
            double[][][] state = this.state_order.get(this.state_order.size()-1);
            this.state_order.remove(this.state_order.size()-1);
    
            double[][][] action = this.actions.get(this.actions.size()-1);
            this.actions.remove(this.actions.size()-1);

            value *= this.discount;

            if(this.states.containsKey(state)) {
                value += temporalDifference(value, state, newState)
            }

        }
    }
    

    // this method updates utility values.
    public double[][][] temporalDifference(int reward, double[][][] key, double[][][] newKey) {
        double[][][] state = new double[4][4][4];
        double[][][] updated = new double[4][4][4];
        
        // checks if hashmap contains the matrix.
        if(states.containsKey(key)) {
            state = states.get(key);
        } else {
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                    for(int p = 0; p < 4; p++) {
                        state[i][j][p] = 0.0;
                    }
                }
            }
        }
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {

                    // formula for temporal difference learning.
                    newKey[i][j][p] = newKey[i][j][p] * reward;
                    updated[i][j][p] = newKey[i][j][p] - state[i][j][p];
                    updated[i][j][p] = updated[i][j][p] * this.learningRate;
                }
            }
        }

        // updates the exploration rate.
        this.exploration = Math.max(this.exploration - decay, 0.3);
        return updated;
    }

    public void setState(double[][][] old, double[][][] action) {
        this.state_order.add(old);
        this.actions.add(action);
    }

    public int[] selectMove(Board board) {
        // implement this..
        return 0;
    }

    public void explore(Board board, int depth) {

    }

    public void exploit(Board board, double[][][] key) {
        double[][][] value = this.states.get(key);
    }




}