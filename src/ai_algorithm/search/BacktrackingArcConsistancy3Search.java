package ai_algorithm.search;

import ai_algorithm.Frontier;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import ai_algorithm.problems.mapColoring.MapColoringProblem;
import ai_algorithm.problems.mapColoring.MapColoringState;
import ai_algorithm.problems.mapColoring.Pair;
import application.debugger.Debugger;

import java.util.*;

public class BacktrackingArcConsistancy3Search extends SearchAlgorithm{

    MapColoringProblem mcp;

    public BacktrackingArcConsistancy3Search() {
        super();
    }

    public BacktrackingArcConsistancy3Search(MapColoringProblem problem) { // KA OB NÖTIG
        super(problem);
    }

    @Override
    public Path search() {
        SearchNode start = new SearchNode(null, problem.getInitialState(), 0, null);
        Debugger.pause();

        Frontier frontier = new Frontier();
        Debugger.pause();

        if (this.problem.isGoalState(start.getState())) {
            return start.getPath();
        }
        frontier.add(start);
        Debugger.pause();
        while (!frontier.isEmpty()) {
            SearchNode node = frontier.removeFirst();
            Debugger.pause();
            System.out.println(node);
            if (problem.isGoalState(node.getState())) {
                return node.getPath();
            }
            for (SearchNode child : node.expand()) {
                State state = child.getState();
                if (problem.isGoalState(state)) {
                    Debugger.pause("Finished");
                    return child.getPath();
                }
                if (!contains2(node, state)) {
                    frontier.add(child);
                }
            }
        }

        Debugger.pause("No Sulution found");
//        return backtrack(start);
        return null;
    }

    private Path backtrack(SearchNode node){
        Frontier frontier = new Frontier();
        MapColoringProblem csp;
        csp = (MapColoringProblem) this.problem;
        Debugger.pause();
        if (this.problem.isGoalState(node.getState())) {
            return node.getPath();
        }
        frontier.add(node);
        Debugger.pause();
        if (this.problem instanceof MapColoringProblem) {
            MapColoringState state = (MapColoringState) node.getState();
            if (state.getAssignments().size() == csp.getVariables().size()) {
                return node.getPath();
            }
            State nextVariable = selectUnassignedVariable(node);
            List<String> values = state.getDomain(nextVariable.toString());
            for (String value : values) {
                if (isConsistent(value, node.getState())) {
                    Map<String, String> newAssignments = new HashMap<>(state.getAssignments());
                    newAssignments.put(nextVariable.toString(), value);
                    Map<String, List<String>> newDomain = new HashMap<>();
                    if (ArcConsistency3(csp.getContraints(), csp.getDomain(), newAssignments)) {
                        return backtrack(new SearchNode(node, new MapColoringState(csp, newDomain, newAssignments), 0, null));
                    }
                }
            }
        }
        return null;
    }

    public boolean contains2(SearchNode sn, State state) {
        return sn.getPath().getVisitedStates().contains(state);
    }

    private boolean isConsistent(String value, State state) {
        // TODO: Implement
        return true;
    }

    private State selectUnassignedVariable(SearchNode node) {
        // TODO: Implement
        return null;
    }

    public boolean ArcConsistency3(List<Pair<String, String>> contraints,
                              Map<String, List<String>> domain, Map<String, String> assignments) {
        while (!contraints.isEmpty()) {
            Pair<String, String> arc = contraints.remove(0);
            if (revise(arc.getFirst(), arc.getSecond(), domain, assignments)) {
                int dIIndex = domain.get(arc.getFirst()).indexOf(assignments.get(arc.getFirst()));
                if (domain.get(arc.getFirst()).isEmpty()) {
                    System.out.println("No Solution");
                    return false;
                }
                List<String> neighbors = new ArrayList<>((Collection) contraints.get(dIIndex));
                neighbors.remove(arc.getSecond());
                for (String xk : neighbors) {
                    contraints.add(new Pair<>(xk, arc.getFirst()));
                }
            }
        }
        return true;
    }

    private boolean revise(String first, String second, Map<String,
            List<String>> domain, Map<String, String> assignments) {
        boolean revised = false;
        List<String> currDomain = domain.get(first);
        List<String> newValues = new ArrayList<>(currDomain.size());
        Map<String, String> assignment = new HashMap<>();
        for (String vi : currDomain) {
            assignment.put(first, vi);
            for (String vj : domain.get(second)) {
                assignment.put(second, vj);
                if (assignments.containsKey(first) && assignments.containsKey(second)) {
                    if (assignments.get(first).equals(vi) && assignments.get(second).equals(vj)) {
                        newValues.add(vi);
                        break;
                    }
                }
            }
        }
        if (newValues.size() < currDomain.size()) {
            domain.put(first, newValues);
            revised = true;
        }
        return revised;
    }

}

/*
 * Copyright (c) 2024 Alexander Ultsch
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
