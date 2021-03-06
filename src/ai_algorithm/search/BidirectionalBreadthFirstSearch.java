package ai_algorithm.search;

import java.util.List;

import ai_algorithm.ExploredSet;
import ai_algorithm.Frontier;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import application.debugger.Debugger;
/**
 * 
 * @author Severin
 *
 * Bidirektionale suche nach Russell and Norvig
 *
 */
public class BidirectionalBreadthFirstSearch extends SearchAlgorithm {

	@Override
	public Path search() {
		if (problem.getGoalState() == null) {// TODO Auto-generated method stub
			Debugger.consolePrintln("Goal is not known! Abord!");
			return null;
		}

		SearchNode startF = new SearchNode(null, problem.getInitialState(), 0, null);
		SearchNode goalB = new SearchNode(null, problem.getGoalState(), 0, null);

		Frontier frontierStartF = new Frontier();
		frontierStartF.add(startF);

		Frontier frontierGoalB = new Frontier();
		frontierGoalB.add(goalB);

		ExploredSet reachendStartF = new ExploredSet();
		reachendStartF.add(startF);

		ExploredSet reachedGoalB = new ExploredSet();
		reachedGoalB.add(goalB);

		Path solution = null;

		boolean terminated = false;
		boolean toggle = false;
		while (!frontierGoalB.isEmpty() && !frontierStartF.isEmpty() && !terminated) {

			if (toggle = !toggle) {
				var d = proceed("f", frontierStartF, reachendStartF, reachedGoalB) == null ? false
						: (terminated = true);

			} else {
				var d = proceed("b", frontierGoalB, reachedGoalB, reachendStartF) == null ? false : (terminated = true);
			}

		}

		return startF.getPath();

	}
	
	
	private List<String> proceed(String dir, Frontier frontier, ExploredSet reached, ExploredSet reachedOther) {
		SearchNode searchnode = frontier.removeFirst();
		for (SearchNode child : searchnode.expand()) {
			if (!reached.contains(child.getState())) {
				reached.add(child);
				frontier.add(child);
				if (reachedOther.contains(child.getState())) {
					Debugger.consolePrintln("!!!!!!!!!!!!!!!!!!!!!!!! FOUND !!!!!!!!!!!!!!!!!!!");
					return (List<String>) join(child.getPath(), reachedOther.get(child.getState()).getPath());
				}
			}
		}
		return null;

	}

	private List<String> join(Path a, Path b) {
		List<String> actionA = a.getPathActions();
		List<String> actionB = b.getPathActions();

		actionA.addAll(actionB);
		return actionA;

	}

}

/*
 * Copyright (c) 2022 Severin Dippold
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */