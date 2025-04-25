package tm;

/**
 * Represents a transition in a Turing Machine.
 * A transition defines the next state to move to, what symbol to write,
 * and which direction (left or right) the head should move.
 */
public class Transition {
    /** The state to transition to next */
    public final int nextState;

    /** The symbol to write on the tape */
    public final int writeSymbol;

    /** The direction to move the head ('L' or 'R') */
    public final char direction;

    /**
     * Constructs a Transition with the specified next state, symbol to write, and
     * direction.
     *
     * @param nextState   the next state to go to
     * @param writeSymbol the symbol to write on the tape
     * @param direction   the direction to move the tape head ('L' or 'R')
     */
    public Transition(int nextState, int writeSymbol, char direction) {
        this.nextState = nextState;
        this.writeSymbol = writeSymbol;
        this.direction = direction;
    }
}