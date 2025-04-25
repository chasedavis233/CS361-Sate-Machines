package tm;

/**
 * Interface that extends TMBaseInterface to include transition-specific methods
 * for a deterministic Turing Machine.
 */
public interface TMInterface extends TMBaseInterface {
    /**
     * Add a transition to the machine's transition table.
     *
     * @param state      the current state
     * @param symbol     the current tape symbol being read
     * @param transition the transition to apply from this state-symbol pair
     */
    void addTransition(int state, int symbol, Transition transition);

    /**
     * Gets the transition for a given state and symbol pair.
     *
     * @param state  the current state
     * @param symbol the tape symbol being read
     * @return the corresponding transition, or null if not defined
     */
    Transition getTransition(int state, int symbol);
}