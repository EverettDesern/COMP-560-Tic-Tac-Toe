import java.util.*;
    
public class AI {
	public double learningRate;
    public double exploration;
    public double decay;
    public double discount;
    public int[] currentState;
    public HashMap<double[][][], double[][][]> states;
    public ArrayList<double[][][]> state_order;
    public ArrayList<Point> actions;

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
    public void reward(double value) {
        // if no more states or actions, return nothing.
        if(this.state_order.size() == 0 && this.actions.size() == 0) {
            return;
        }

        // grab a state and an action from the stacks.
        double[][][] newState = this.state_order.get(this.state_order.size()-1);
        this.state_order.remove(this.state_order.size()-1);

        Point newAction = this.actions.get(this.actions.size()-1);
        this.actions.remove(this.actions.size()-1);

        // make a new 3D matrix with all 0.0s.
        double[][][] zero = new double[4][4][4];
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {
                    zero[i][j][p] = 0.0;
                }
            }
        }

        //initialize the hashmap value with 0.0.
        this.states.put(newState, zero);
        double[][][] nextState = new double[4][4][4];

        // copy the values of newState into nextState.
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {
                    nextState[i][j][p] = newState[i][j][p];
                }
            }
        }
        // change the value of the action coordinates.
        nextState[newAction.i][newAction.j][newAction.p] = value;

        // put this new value into the hashmap.
        this.states.replace(newState, nextState);

        while(this.state_order.size() > 1) {

            // pop things from the stack
            double[][][] state = this.state_order.get(this.state_order.size()-1);
            this.state_order.remove(this.state_order.size()-1);
    
            Point action = this.actions.get(this.actions.size()-1);
            this.actions.remove(this.actions.size()-1);

            // change the reward value by decaying it.
            value *= this.discount;

            // if states contains this board
            if(this.states.containsKey(state)) {
                double[][][] result = temporalDifference(value, state, newState);
                double use = result[newAction.i][newAction.j][newAction.p];
                value += use;
                double[][][] nexttState = new double[4][4][4];
                for(int i = 0; i < 4; i++) {
                    for(int j = 0; j < 4; j++) {
                        for(int p = 0; p < 4; p++) {
                            nexttState[i][j][p] = state[i][j][p];
                        }
                    }
                }
                nexttState[action.i][action.j][action.p] = value;
                this.states.replace(state, nexttState);
            } else {
                this.states.put(state, zero);
                double[][][] result = temporalDifference(value, state, newState);
                double use = result[newAction.i][newAction.j][newAction.p];
                value = use;
                double[][][] nexttState = new double[4][4][4];
                for(int i = 0; i < 4; i++) {
                    for(int j = 0; j < 4; j++) {
                        for(int p = 0; p < 4; p++) {
                            nexttState[i][j][p] = state[i][j][p];
                        }
                    }
                }
                nexttState[action.i][action.j][action.p] = value;
                this.states.replace(state, nexttState);
            }
            newState = state;
            newAction = action;
        }
    }
    

    // this method updates utility values.
    public double[][][] temporalDifference(double reward, double[][][] key, double[][][] newKey) {
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

    public void setState(double[][][] old, Point action) {
        this.state_order.add(old);
        this.actions.add(action);
    }

    public Point selectMove(Board board) {
        double number = Math.random();
        boolean explore;
        if(number < this.exploration) {
            explore = true;
        } else {
            explore = false;
        }
        Point action;
        if(explore || !this.states.containsKey(board)) {
            action = explore(board, 0);
        } else {
            action = exploit(board);
        }
        setState(board, action);
        return action;
    }

    public Point explore(Board board, int depth) {

    }

    public Point exploit(Board board) {
        double[][][] value = this.states.get(board);
    }




}