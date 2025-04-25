package fa.nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fa.State;

/**
 * Represents a state in a Non-Deterministic Finite Automaton (NFA).
 * Each state can have multiple transitions on the same symbol,
 * including epsilon transitions.
 */
public class NFAState extends State {
    private Map<Character, Set<NFAState>> transitions;
    private boolean isFinal;

    /**
     * Constructs an NFA state with a given name.
     *
     * @param name the name of the state
     */
    public NFAState(String name) {
        super(name);
        this.transitions = new HashMap<>();
        this.isFinal = false;
    }

    /**
     * Adds a transition from this state to another state on a given symbol.
     *
     * @param symbol  the transition symbol (can be an input character or 'e' for epsilon)
     * @param toState the state to transition to
     */
    public void addTransition(char symbol, NFAState toState) {
        transitions.computeIfAbsent(symbol, k -> new HashSet<>()).add(toState);
    }

    /**
     * Retrieves the set of states reachable from this state on a given symbol.
     *
     * @param symbol the transition symbol
     * @return the set of states reachable on the given symbol, or an empty set if none exist
     */
    public Set<NFAState> getTransitions(char symbol) {
        return transitions.getOrDefault(symbol, new HashSet<>());
    }

    /**
     * Sets this state as a final state.
     *
     * @param isFinal true if this state should be marked as final, false otherwise
     */
    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    /**
     * Checks whether this state is a final state.
     *
     * @return true if this state is final, false otherwise
     */
    public boolean isFinal() {
        return isFinal;
    }
}
