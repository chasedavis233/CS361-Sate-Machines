package tm;

/**
 * basic interface that defines core data for a Turing Machine.
 * accessing general machine information such as
 * state count, alphabet size, and the halting state.
 */
public interface TMBaseInterface {
    /**
     * Gets the number of states in the Turing Machine.
     *
     * @return total number of states
     */
    int getNumStates();

    /**
     * Gets the size of the input alphabet not including blank symbol 0.
     *
     * @return the number of input symbols
     */
    int getInputAlphabetSize();

    /**
     * Gets the halting state's ID
     *
     * @return the halting state
     */
    int getHaltingState();
}
