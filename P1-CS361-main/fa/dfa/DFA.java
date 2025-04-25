package fa.dfa; 

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import fa.State;

/**
 * DFA class that includes all of the logic in order
 * to have a functioning DFA.
 * 
 * @author Chase Davis and Dylan Hiersekorn
 */
public class DFA implements DFAInterface {

    private Map<String, State> states;
    private Map<String, Map<Character, State>> transitions;
    private Set<Character> sigma;
    private State startState;
    private Set<State> finalStates;

    /**
     * Constructor that instantiates variables using linked hash maps and sets.
     */
    public DFA() {
        states = new LinkedHashMap<>();
        transitions = new LinkedHashMap<>();
        sigma = new LinkedHashSet<>();
        finalStates = new LinkedHashSet<>();
    }

    @Override
    public boolean addState(String name) {
        if (states.containsKey(name)) {
            return false;
        }
        DFAState newState = new DFAState(name);
        states.put(name, newState);
        transitions.put(name, new LinkedHashMap<>());
        return true;
    }

    @Override
    public boolean setFinal(String name) {
        State state = states.get(name);
        if (state == null) {
            return false;
        }
        finalStates.add(state);
        return true;
    }

    @Override
    public boolean setStart(String name) {
        State state = states.get(name);
        if (state == null) {
            return false;
        }
        startState = state;
        return true;
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        if (startState == null) {
            return false;
        }
        State currentState = startState;
        for (char c : s.toCharArray()) {
            if (!transitions.get(currentState.getName()).containsKey(c)) {
                return false;
            }
            currentState = transitions.get(currentState.getName()).get(c);
        }
        return finalStates.contains(currentState);
    }

    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    @Override
    public State getState(String name) {
        return states.get(name);
    }

    @Override
    public boolean isFinal(String name) {
        State state = states.get(name);
        return state != null && finalStates.contains(state);
    }

    @Override
    public boolean isStart(String name) {
        return startState != null && startState.getName().equals(name);
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        if (!states.containsKey(fromState) || !states.containsKey(toState) || !sigma.contains(onSymb)) {
            return false;
        }
        transitions.get(fromState).put(onSymb, states.get(toState));
        return true;
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        DFA newDFA = new DFA();
        for (String stateName : states.keySet()) {
            newDFA.addState(stateName);
        }
        for (Character c : sigma) {
            newDFA.addSigma(c);
        }
        newDFA.setStart(startState.getName());
        for (State state : finalStates) {
            newDFA.setFinal(state.getName());
        }
        for (String fromState : transitions.keySet()) {
            for (Map.Entry<Character, State> entry : transitions.get(fromState).entrySet()) {
                char originalSymbol = entry.getKey();
                char swappedSymbol = (originalSymbol == symb1) ? symb2
                        : (originalSymbol == symb2) ? symb1 : originalSymbol;
                newDFA.addTransition(fromState, entry.getValue().getName(), swappedSymbol);
            }
        }
        return newDFA;
    }

    /**
     * Creates a string for the DFA
     * 
     * @return String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Q = { ").append(String.join(" ", states.keySet())).append(" }\n");
        sb.append("Sigma = { ").append(sigma.stream().map(String::valueOf).collect(Collectors.joining(" ")))
                .append(" }\n");

        sb.append("delta =\n ");
        for (Character c : sigma) {
            sb.append("\t").append(c);
        }
        sb.append("\n");

        for (String stateName : states.keySet()) {
            sb.append(stateName);
            for (Character c : sigma) {
                State nextState = transitions.getOrDefault(stateName, new LinkedHashMap<>()).get(c);
                sb.append("\t").append(nextState != null ? nextState.getName() : "");
            }
            sb.append("\n");
        }

        sb.append("q0 = ").append(startState != null ? startState.getName() : "").append("\n");

        sb.append("F = { ");
        sb.append(finalStates.stream().map(State::getName).collect(Collectors.joining(" ")));
        sb.append(" }\n");

        return sb.toString();
    }

}