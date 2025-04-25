package tm;

import java.util.*;

/**
 * Represents a deterministic Turing Machine.
 * Stores the number of states, the size of the tape alphabet,
 * and a 2D array of transitions indexed by [state][symbol].
 */
public class TuringMachine implements TMInterface {
    /** Total number of states in the machine */
    private final int numStates;

    /** Total number of symbols in the tape alphabet (includes blank) */
    private final int tapeAlphabetSize;

    /** Transition table: transitions[state][symbol] = corresponding transition */
    private final Transition[][] transitions;

    /**
     * Constructs a new Turing Machine with the given number of states and tape
     * alphabet size.
     *
     * @param numStates        the number of states
     * @param tapeAlphabetSize the number of tape symbols (including blank)
     */
    public TuringMachine(int numStates, int tapeAlphabetSize) {
        this.numStates = numStates;
        this.tapeAlphabetSize = tapeAlphabetSize;
        this.transitions = new Transition[numStates][tapeAlphabetSize];
    }

    /**
     * Adds a transition to the transition table.
     *
     * @param state      the current state
     * @param symbol     the current tape symbol being read
     * @param transition the transition to store
     */
    @Override
    public void addTransition(int state, int symbol, Transition transition) {
        transitions[state][symbol] = transition;
    }

    /**
     * Retrieves the transition for a given state and tape symbol.
     *
     * @param state  the current state
     * @param symbol the current tape symbol being read
     * @return the stored transition
     */
    @Override
    public Transition getTransition(int state, int symbol) {
        return transitions[state][symbol];
    }

    /**
     * Gets the halting state, which is defined as the highest-numbered state.
     *
     * @return the halting state ID
     */
    @Override
    public int getHaltingState() {
        return numStates - 1;
    }

    /**
     * Gets the number of states in the machine.
     *
     * @return the number of states
     */
    @Override
    public int getNumStates() {
        return numStates;
    }

    /**
     * Gets the size of the input alphabet (not including blank).
     *
     * @return input alphabet size
     */
    @Override
    public int getInputAlphabetSize() {
        return tapeAlphabetSize - 1;
    }
}
