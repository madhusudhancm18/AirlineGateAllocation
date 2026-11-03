package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model class representing an airport gate that can host multiple non-overlapping flights.
 */
public class Gate {
    private int gateNumber;
    private List<Flight> assignedFlights;

    /**
     * Constructs a Gate with a specific gate number.
     *
     * @param gateNumber the identifier number for the gate
     */
    public Gate(int gateNumber) {
        this.gateNumber = gateNumber;
        this.assignedFlights = new ArrayList<>();
    }

    public int getGateNumber() {
        return gateNumber;
    }

    public List<Flight> getAssignedFlights() {
        return Collections.unmodifiableList(assignedFlights);
    }

    /**
     * Checks if this gate is available for a given flight without overlap.
     *
     * @param flight the flight to check
     * @return true if the gate can host the flight without collision, false otherwise
     */
    public boolean isAvailable(Flight flight) {
        if (flight == null) return false;
        for (Flight assigned : assignedFlights) {
            if (assigned.overlapsWith(flight)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Assigns a flight to this gate.
     *
     * @param flight the flight to assign
     * @return true if assignment was successful, false if there was an overlap
     */
    public boolean assignFlight(Flight flight) {
        if (isAvailable(flight)) {
            assignedFlights.add(flight);
            // Maintain sorted order of flights by arrival time
            Collections.sort(assignedFlights);
            return true;
        }
        return false;
    }

    /**
     * Removes a flight from this gate.
     *
     * @param flight the flight to remove
     * @return true if the flight was found and removed
     */
    public boolean removeFlight(Flight flight) {
        return assignedFlights.remove(flight);
    }

    /**
     * Clears all flights assigned to this gate.
     */
    public void clear() {
        assignedFlights.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gate ").append(gateNumber).append(": [");
        for (int i = 0; i < assignedFlights.size(); i++) {
            sb.append(assignedFlights.get(i).getFlightNumber());
            if (i < assignedFlights.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
