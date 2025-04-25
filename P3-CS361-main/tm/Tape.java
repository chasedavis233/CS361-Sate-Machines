package tm;

import java.util.*;

/**
 * Represents the bi-infinite tape of a Turing Machine.
 * Uses a HashMap to simulate infinite cells in both directions.
 * Keeps track of all visited cells so that only meaningful output is printed.
 */
public class Tape {
    /**
     * Stores tape content with position as key and symbol as value.
     */
    private final Map<Integer, Integer> tape = new HashMap<>();

    /**
     * Keeps track of all positions that have been read from or written to.
     */
    private final Set<Integer> visited = new HashSet<>();

    /**
     * Reads the symbol at a given tape position.
     * If the position was never written to, returns blank (0).
     *
     * @param position the tape cell to read
     * @return symbol at the position, or 0 if blank
     */
    public int read(int position) {
        return tape.getOrDefault(position, 0);
    }

    /**
     * Writes a symbol at a given position on the tape.
     * Marks the position as visited.
     *
     * @param position the cell to write to
     * @param symbol   the symbol to write
     */
    public void write(int position, int symbol) {
        tape.put(position, symbol);
        visited.add(position);
    }

    /**
     * Returns a string of all symbols on the tape that were visited.
     * This only includes the range from the smallest to the largest visited index.
     *
     * @return a string of tape symbols in visited range
     */
    public String getVisitedTapeContents() {
        if (visited.isEmpty()) {
            return "";
        }

        int min = Collections.min(visited);
        int max = Collections.max(visited);

        StringBuilder sb = new StringBuilder();
        for (int i = min; i <= max; i++) {
            if (visited.contains(i)) {
                sb.append(read(i));
            }
        }
        return sb.toString();
    }
}
