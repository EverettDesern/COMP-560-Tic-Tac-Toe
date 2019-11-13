import java.util.*;

    public class AI {
	public double learningRate;
    public double exploration;
    public double decay;
    public double discount;
    public int[] currentState;
    public HashMap<String, float[][][]> states;
    public ArrayList<HashMap<String, Point>> state_order;
    public ArrayList<Point> actions;
    public int wins;
    public boolean print;

	public AI() {
        this.learningRate = 0.5;
        this.wins = 0;
        this.exploration = 0.9;
        this.decay = 0.01;
        this.discount = 0.9;
        states = new HashMap<>();
        state_order = new ArrayList<>();
        actions = new ArrayList<>();
        print = false;

    }

    // this method serializes the board object. We use the serialization as a key in our HashMap.
    public String serialize(Board board) {
        String s = "";
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {
                    s += board.gameBoard[i][j][p];
                }
            }
        }
        return s;
    }

    // this method assigns utility values to each tile in the board. It does this by using a reinforcement learning
    // technique called "Temporal Difference Learning".
    public void reward(float value) {
        // if no more states or actions, return nothing.
        if(this.state_order.size() == 0 && this.actions.size() == 0) {
            return;
        }

        // grab a state and an action from the stacks.
        HashMap<String, Point> map1 = this.state_order.get(this.state_order.size()-1);
        this.state_order.remove(this.state_order.size()-1);

        String newState = "";
        for(String b : map1.keySet()) {
            newState = b;
        }
        Point newAction = map1.get(newState);

        // make a new 3D matrix with all 0.0s.
        float[][][] zero = new float[4][4][4];
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {
                    zero[i][j][p] = (float) 0.0;
                }
            }
        }

        //initialize the hashmap value with 0.0.
        this.states.put(newState, zero);
        float[][][] nextState = new float[4][4][4];
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {
                    nextState[i][j][p] = zero[i][j][p];
                }
            }
        }


        // change the value of the action coordinates.
        nextState[newAction.i][newAction.j][newAction.p] = value;

        // put this new value into the hashmap.
        this.states.replace(newState, nextState);

        // do while the stack is not empty.
        while(this.state_order.size() > 0) {
            // pop things from the stack
            HashMap<String, Point> mapp = this.state_order.get(this.state_order.size()-1);
            this.state_order.remove(this.state_order.size()-1);
    
            String state = "";
            for(String b : mapp.keySet()) {
                state = b;
            }
            Point action = mapp.get(state);

            // change the reward value by multiplying it by 1.5.. this is an arbitrary number.
            value *= 1.5;


            // if states contains this board
            // this section of code calls our temporal difference learning method and updates
            // the value into our 3D array consisting of ultity values.
            if(this.states.containsKey(state)) {
                float[][][] result = temporalDifference(value, state, newState);
                double use = result[newAction.i][newAction.j][newAction.p];
                value += use;
                float[][][] nexttState = this.states.get(state);
                nexttState[action.i][action.j][action.p] = value;
                // replace the old entry with a new one containing our new utility value.
                this.states.replace(state, nexttState);
            } else {
                float[][][] zeroo = new float[4][4][4];
                for(int i = 0; i < 4; i++) {
                    for(int j = 0; j < 4; j++) {
                        for(int p = 0; p < 4; p++) {
                            zeroo[i][j][p] = (float) 0.0;
                        }
                    }
                }
                // initialize the hashmap entry with a zero matrix.
                this.states.put(state, zeroo);
                float[][][] result = temporalDifference(value, state, newState);
                float use = result[newAction.i][newAction.j][newAction.p];
                value = use;
                float[][][] nexttState = this.states.get(state);
                nexttState[action.i][action.j][action.p] = value;
                this.states.replace(state, nexttState);
            }
            // update pointers
            newState = state;
            newAction = action;
        }
    }
    

    // this method updates utility values.
    // the forula for a temporal difference learning method is 
    // X = X + a(v(G) - S).
    // Where:
    // X is the 3D matrix that holds the final utility values after this formula.
    // a is a scalar learning rate.
    // G is a 3D matrix consisting of the "current" state we are looking at/updating a reward value to.
    // v is a scalar representing the "reward" value that we need to assign.
    // S is a 3D matrix consisting of the "previous" state that we need to subtract the current state from.
    public float[][][] temporalDifference(float reward, String key, String newKey) {
        float[][][] state = new float[4][4][4];
        float[][][] updated = new float[4][4][4];
        float[][][] actual = this.states.get(newKey);
        
        // checks if hashmap contains the matrix.
        if(states.containsKey(key)) {
            state = states.get(key);
        } else {
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                    for(int p = 0; p < 4; p++) {
                        state[i][j][p] = (float)0.0;
                    }
                }
            }
        }
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {
                    actual[i][j][p] = (float) (actual[i][j][p] * reward);
                }
            }
        }
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {
                    updated[i][j][p] = actual[i][j][p] - state[i][j][p];
                }
            }
        }
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {
                    updated[i][j][p] = (float) (updated[i][j][p] * this.learningRate);
                }
            }
        }

        // updates the exploration rate.
        this.exploration = Math.max(this.exploration - decay, 0.3);
        return updated;
    }

    // this method adds to our stack.
    public void setState(Board old, Point action) {
        HashMap<String, Point> addition = new HashMap<>();
        String s = serialize(old);
        addition.put(s,action);
        this.state_order.add(addition);
    }

    // this method is used when the AI makes a new move. It either explores or exploits the board.
    public Point selectMove(Board board) {
        double number = Math.random();
        String s = serialize(board);
        boolean explore;
        if(number < this.exploration) {
            explore = true;
        } else {
            explore = false;
        }
        Point action;
        if(explore || !this.states.containsKey(s)) {
            action = explore(board, 0);
        } else {
            action = exploit(board);
        }
        setState(board, action);
        return action;
    }

    // this the exploration in a markov decision process. It looks at all of the values in the
    // board and picks one to see if it improves our utility function.
    public Point explore(Board board, int depth) {
        List<Point> coords = new ArrayList<>();
        Board pseudo = new Board(4);
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {
                    if(board.gameBoard[i][j][p] == 0) {
                        coords.add(new Point(i, j, p));
                    }
                    pseudo.gameBoard[i][j][p] = board.gameBoard[i][j][p];
                }
            }
        }
        Random rand = new Random();
        Point random = coords.get(rand.nextInt(coords.size()));
        String key = serialize(pseudo);
        if(board.currentPlayer == 0) {
            pseudo.gameBoard[random.i][random.j][random.p] = 1;
        } else {
            pseudo.gameBoard[random.i][random.j][random.p] = 2;
        }
        if(!this.states.containsKey(key) || depth == 64) {
            return random;
        }
        depth++;

        return explore(board, depth);
    }

    // this method prints each training instance.
    public void printValues(Board board) {
        String s = "0000000000000000000000000000000000000000000000000000000000000000";
        float[][][] value = this.states.get(s);
            for (int i = 0; i < 4; i++) {
                System.out.println("DIMENSION: " + (i+1));
                for (int j = 0; j < 4; j++) {
                    for (int p = 0; p < 4; p++) {
                        System.out.print(value[i][j][p]);
                        System.out.print(" ");
                    }
                    System.out.println(" ");
                }
                System.out.println(" ");
            }
    }

    // this method is the exploitation in a markov decision process. It calculates the ultity values
    // of each tile and chooses the best one.
    
    public Point exploit(Board board) {
        String s = serialize(board);
        float[][][] value = this.states.get(s);
        HashMap<Point, Float> link = new HashMap<>();
        List<String> coords = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {
                    if(board.gameBoard[i][j][p] == 0) {
                        String temp = "";
                        temp += i;
                        temp += j;
                        temp += p;
                        coords.add(temp);
                    }
                }
            }
        }
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int p = 0; p < 4; p++) {
                    String temp = "";
                    temp += i;
                    temp += j;
                    temp += p;
                    if(coords.contains(temp)) {
                        Point f = new Point(i,j,p);
                        link.put(f, value[i][j][p]);
                    }
                }
            }
        }
        double max = Collections.max(link.values());
        List<Point> indexes = new ArrayList<>();
        for (Map.Entry<Point, Float> entry : link.entrySet()) {
            if (entry.getValue()==max) {
                indexes.add(entry.getKey());
            }
        }
        Random rand = new Random();
        String random = coords.get(rand.nextInt(coords.size()));
        int v = Character.getNumericValue(random.charAt(0));
        int c = Character.getNumericValue(random.charAt(1));
        int g = Character.getNumericValue(random.charAt(2));
        Point z = new Point(v,c,g);
        return z;

    }




}