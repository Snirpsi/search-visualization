package ai_algorithm;

import java.util.List;

import ecs.GameObject;

public abstract class Problem extends GameObject{

	public abstract State getInitialState();

	public abstract boolean isGoalState(State state);

	public abstract List<String> getActions(State state);

	public abstract State getSuccessor(State state, String action);

	public abstract double getCost(State s, String action, State succ);
}
