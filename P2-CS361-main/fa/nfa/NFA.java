package fa.nfa;

import fa.FAInterface;
import java.util.*;

/**
 * Represents a Non-Deterministic Finite Automata (NFA) with states,
 * transitions, an alphabet, and accepting states.
 */
public class NFA implements NFAInterface {
    private Set<NFAState> states;
    private Set<Character> alphabet;
    private NFAState startState;
    private Set<NFAState> finalStates;

    /**
     * Constructs an empty NFA.
     */
    public NFA() {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        finalStates = new HashSet<>();
    }

    @Override
    public boolean addState(String name) {
        if (getState(name) != null)
            return false;
        states.add(new NFAState(name));
        return true;
    }

    @Override
    public boolean setFinal(String name) {
        NFAState state = getState(name);
        if (state == null)
            return false;
        finalStates.add(state);
        return true;
    }

    @Override
    public boolean setStart(String name) {
        NFAState state = getState(name);
        if (state == null)
            return false;
        startState = state;
        return true;
    }

    @Override
    public void addSigma(char symbol) {
        alphabet.add(symbol);
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        NFAState from = getState(fromState);
        if (from == null || (!alphabet.contains(onSymb) && onSymb != 'e'))
            return false;

        for (String to : toStates) {
            NFAState toState = getState(to);
            if (toState == null)
                return false;
            from.addTransition(onSymb, toState);
        }
        return true;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return from.getTransitions(onSymb);
    }

    /**
     * Computes the eclosure of a given state.
     * 
     * @param s The state for which to compute the eclosure.
     * @return A set of states reachable via epsilon transitions.
     */
    public Set<NFAState> eClosure(NFAState s) {
        Set<NFAState> closure = new HashSet<>();
        Stack<NFAState> stack = new Stack<>();
        stack.push(s);

        while (!stack.isEmpty()) {
            NFAState state = stack.pop();
            if (!closure.contains(state)) {
                closure.add(state);
                stack.addAll(state.getTransitions('e'));
            }
        }
        return closure;
    }

    @Override
    public boolean accepts(String s) {
        Set<NFAState> currentStates = eClosure(startState);

        for (char c : s.toCharArray()) {
            Set<NFAState> nextStates = new HashSet<>();
            for (NFAState state : currentStates) {
                for (NFAState nextState : state.getTransitions(c)) {
                    nextStates.addAll(eClosure(nextState));
                }
            }
            currentStates = nextStates;
        }

        for (NFAState state : currentStates) {
            if (finalStates.contains(state))
                return true;
        }
        return false;
    }

    @Override
    public int maxCopies(String s) {
        int max = eClosure(startState).size();
        Set<NFAState> currentStates = eClosure(startState);

        for (char c : s.toCharArray()) {
            Set<NFAState> nextStates = new HashSet<>();
            for (NFAState state : currentStates) {
                for (NFAState nextState : state.getTransitions(c)) {
                    nextStates.addAll(eClosure(nextState));
                }
            }
            max = Math.max(max, nextStates.size());
            currentStates = nextStates;
        }
        return max;
    }

    @Override
    public boolean isDFA() {
        for (NFAState state : states) {
            if (!state.getTransitions('e').isEmpty()) {
                return false;
            }
            for (char symbol : alphabet) {
                if (state.getTransitions(symbol).size() > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Set<Character> getSigma() {
        return alphabet;
    }

    @Override
    public NFAState getState(String name) {
        for (NFAState state : states) {
            if (state.getName().equals(name))
                return state;
        }
        return null;
    }

    @Override
    public boolean isFinal(String name) {
        return finalStates.contains(getState(name));
    }

    @Override
    public boolean isStart(String name) {
        return startState != null && startState.getName().equals(name);
    }
}
