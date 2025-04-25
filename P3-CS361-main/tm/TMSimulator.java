package tm;

import java.io.*;
import java.util.*;

/**
 * Simulates a deterministic Turing Machine with a bi-infinite tape.
 * Parses an input file that defines the TM and the input string,
 * simulates execution, and prints visited tape cells to standard output.
 */
public class TMSimulator {

    /**
     * The main entry point for the Turing Machine Simulator.
     * Reads TM definition from file, simulates execution, and prints result.
     *
     * @param args a single-element array with the path to the TM input file
     * @throws IOException if file reading fails or format is incorrect
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java tm.TMSimulator <input_file>");
            return;
        }

        // Read and parse input file
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        int numStates = Integer.parseInt(br.readLine().trim());
        int sigmaSize = Integer.parseInt(br.readLine().trim());
        int tapeAlphabetSize = sigmaSize + 1; // tape alphabet includes blank (0)

        TuringMachine tm = new TuringMachine(numStates, tapeAlphabetSize);

        // Read transitions for all non-halting states
        for (int state = 0; state < numStates - 1; state++) {
            for (int symbol = 0; symbol < tapeAlphabetSize; symbol++) {
                String line;
                while ((line = br.readLine()) != null && line.trim().isEmpty())
                    ;

                if (line == null) {
                    throw new IOException("Unexpected EOF in transitions");
                }

                String[] parts = line.split(",");
                int nextState = Integer.parseInt(parts[0].trim());
                int writeSymbol = Integer.parseInt(parts[1].trim());
                char direction = parts[2].trim().charAt(0);

                tm.addTransition(state, symbol, new Transition(nextState, writeSymbol, direction));
            }
        }

        // Read the input string (can be empty)
        String input = br.readLine();
        br.close();

        // Initialize tape with input string
        Tape tape = new Tape();
        if (input != null && !input.isEmpty()) {
            for (int i = 0; i < input.length(); i++) {
                tape.write(i, input.charAt(i) - '0');
            }
        }

        int state = 0; // start state
        int head = 0; // head position

        // Simulate TM until it reaches halting state
        while (state != tm.getHaltingState()) {
            int current = tape.read(head);
            Transition t = tm.getTransition(state, current);
            tape.write(head, t.writeSymbol);
            head += (t.direction == 'R') ? 1 : -1;
            state = t.nextState;
        }

        // Output the contents of all visited tape cells
        System.out.println(tape.getVisitedTapeContents());
    }
}
