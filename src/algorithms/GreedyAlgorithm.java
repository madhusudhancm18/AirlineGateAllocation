package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Flight;
import model.Gate;

/**
 * Greedy Algorithm implementation for the gate allocation problem.
 * Sorts flights by arrival time and assigns them to the first available gate.
 */
public class GreedyAlgorithm {

    /**
     * Result class to wrap the allocation details and performance metrics.
     */
    public static class Result {
        private final List<Gate> gates;
        private final long executionTimeNs;

        public Result(List<Gate> gates, long executionTimeNs) {
            this.gates = gates;
            this.executionTimeNs = executionTimeNs;
        }

        public List<Gate> getGates() {
            return gates;
        }

        public int getGatesUsed() {
            return gates.size();
        }

        public long getExecutionTimeNs() {
            return executionTimeNs;
        }
    }

    /**
     * Runs the greedy gate allocation algorithm.
     *
     * @param flights the list of flights to allocate
     * @return the result containing gates and performance metrics
     */
    public Result solve(List<Flight> flights) {
        if (flights == null || flights.isEmpty()) {
            return new Result(new ArrayList<>(), 0);
        }

        long startTime = System.nanoTime();

        // Copy and sort flights by arrival time
        List<Flight> sortedFlights = new ArrayList<>(flights);
        Collections.sort(sortedFlights);

        List<Gate> gates = new ArrayList<>();

        for (Flight flight : sortedFlights) {
            boolean assigned = false;
            // Try to assign to the first available existing gate
            for (Gate gate : gates) {
                if (gate.isAvailable(flight)) {
                    gate.assignFlight(flight);
                    assigned = true;
                    break;
                }
            }
            // If no gate is available, create a new one
            if (!assigned) {
                Gate newGate = new Gate(gates.size() + 1);
                newGate.assignFlight(flight);
                gates.add(newGate);
            }
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        return new Result(gates, duration);
    }
}
