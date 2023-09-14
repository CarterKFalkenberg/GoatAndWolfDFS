import java.util.ArrayList;
import java.util.Stack;

class Solver{

    enum action{G, GG, GW, WW, W}

	private final StateHolder initialState;
	
	private final StateHolder goalState;
	
	private Stack<action> solutionActions;
	private Stack<StateHolder> solutionStates;

	//----------------------------------------------------------------
	public Solver(){
		this.initialState = new StateHolder(3, 0, 3, 0, 'A');
		this.goalState = new StateHolder(0, 3, 0, 3, 'B');
		this.solutionActions = new Stack<action>();
		this.solutionStates = new Stack<StateHolder>();
	}

	//----------------------------------------------------------------
	
	/*
	 * Returns all valid actions for a given state
	 * Does not inlcude actionst that lead to failure states
	 */
	public ArrayList<action> getValidActions(StateHolder state){
		ArrayList<action> actions = new ArrayList<action>();
		StateHolder temp = new StateHolder(state);
		// check if GG is a valid move
		if (doAction(temp, action.GG).isValidState()){
			actions.add(action.GG);
		} else { // check if G is a valid move
			temp = new StateHolder(state);
			if (doAction(temp, action.G).isValidState()){
				actions.add(action.G);	
			}
		}
		temp = new StateHolder(state);
		if (doAction(temp, action.GW).isValidState()){
				actions.add(action.GW);	
		}
		temp = new StateHolder(state);
		if (doAction(temp, action.WW).isValidState()){
				actions.add(action.WW);	
		}
		temp = new StateHolder(state);
		if (doAction(temp, action.W).isValidState()){
				actions.add(action.W);	
		}
		return actions;
	}
	
	//----------------------------------------------------------------

	/*
	 * Returns resulting state from an action for a given state
	 */
	public StateHolder doAction(StateHolder state, action actionToDo){
		return state.boatLocation == 'A' ? doActionA(state, actionToDo) : doActionB(state, actionToDo);
	}

	/*
	 * Returns resulting state from an action for a given state
	 * Only use when boat location is A
	 * Returns null if ending state somehow is invalid
	 */
	public StateHolder doActionA(StateHolder inputState, action actionToDo){
		StateHolder state = new StateHolder(inputState);
		state.boatLocation = 'B';
		if (actionToDo == action.G){
			state.goatsAtA--;
			state.goatsAtB++;
		} else if (actionToDo == action.GG){
			state.goatsAtA -= 2;
			state.goatsAtB += 2;
		} else if (actionToDo == action.GW){
			state.goatsAtA--;
			state.goatsAtB++;
			state.wolvesAtA--;
			state.wolvesAtB++;
		} else if (actionToDo == action.WW){
			state.wolvesAtA -= 2;
			state.wolvesAtB += 2;
		} else {  // action.W
			state.wolvesAtA--;
			state.wolvesAtB++;
		}
		
		return state;
	}

	/*
	 * Returns resulting state from an action for a given state
	 * Only use when boat location is B
	 */
	public StateHolder doActionB(StateHolder inputState, action actionToDo){
		StateHolder state = new StateHolder(inputState);
		state.boatLocation = 'A';
		if (actionToDo == action.G){
			state.goatsAtA++;
			state.goatsAtB--;
		} else if (actionToDo == action.GG){
			state.goatsAtA += 2;
			state.goatsAtB -= 2;
		} else if (actionToDo == action.GW){
			state.goatsAtA++;
			state.goatsAtB--;
			state.wolvesAtA++;
			state.wolvesAtB--;
		} else if (actionToDo == action.WW){
			state.wolvesAtA += 2;
			state.wolvesAtB -= 2;
		} else {  // action.W
			state.wolvesAtA++;
			state.wolvesAtB--;
		}
		
		return state;
	}

	//----------------------------------------------------------------
	/*
	 * Runs DFS to find solution
	 */
	public boolean DFS(){
		return recursiveDFS(this.initialState, null);
	}

	/*
	 * Returns true if we reach the goal
	 * Adds the actions and states of the solution to:
	 * 		- this.solutionActions stack
	 * 		- this.solutionStates stack
	 */
	public boolean recursiveDFS(StateHolder state, action prevAction){
		// If we are at the goal, return the path
		if (state.equals(goalState)){
			// only would happen if the initial state = goalState
			// don't have that in our case, but add for robustness
			if (prevAction != null){
				this.solutionActions.push(prevAction);
			}
			this.solutionStates.push(state);
			return true;
		}

		// get all valid actions and loop through them doing DFS
		ArrayList<action> actions = getValidActions(state);
		for (action possibleAction : actions) {
			if (possibleAction == prevAction){
				continue;
			}
			// get result of aciton, run DFS on that new state
			StateHolder newState = doAction(state, possibleAction);
			boolean result = recursiveDFS(newState, possibleAction);
			// If result was the goal
			if (result){
				if (prevAction != null){
					this.solutionActions.push(prevAction);
				}
				this.solutionStates.push(state);
				return true;
			}
		}
		// if we never reached the goal, return false
		return false;
	}

	//----------------------------------------------------------------

	public String resultToString(){
		String result = "";
		Stack<StateHolder> stackCopy1 = (Stack)this.solutionStates.clone();
		Stack<action> stackCopy2 = (Stack)this.solutionActions.clone();
		int cost = 0;
		while(!(stackCopy1.empty())) {
			result += stackCopy1.pop().toString() + "\n";
			if (!(stackCopy2.empty())){
				result += "ACTION: " + stackCopy2.pop().toString() + "\n";
				cost++;
			}
		} 
		result += "COST: " + cost;
		return result;
	}

	public StateHolder peekStack(){
		return this.solutionStates.peek();
	}

}
