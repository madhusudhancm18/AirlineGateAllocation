package model;

/**
 * Model class representing a flight with scheduling details.
 * Encapsulates flight number, arrival time, and departure time.
 */
public class Flight implements Comparable<Flight> {
    private String flightNumber;
    private int arrivalTime;
    private int departureTime;

    /**
     * Constructs a Flight with validation.
     *
     * @param flightNumber  the unique identifier of the flight
     * @param arrivalTime   the arrival time (0-23 hours)
     * @param departureTime the departure time (0-23 hours)
     * @throws IllegalArgumentException if invalid times are provided
     */
    public Flight(String flightNumber, int arrivalTime, int departureTime) {
        setFlightNumber(flightNumber);
        setTimes(arrivalTime, departureTime);
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight number cannot be empty.");
        }
        this.flightNumber = flightNumber.trim().toUpperCase();
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets arrival and departure times with consistency validation.
     *
     * @param arrivalTime   the arrival time
     * @param departureTime the departure time
     */
    public void setTimes(int arrivalTime, int departureTime) {
        if (arrivalTime < 0 || arrivalTime > 23) {
            throw new IllegalArgumentException("Arrival time must be between 0 and 23.");
        }
        if (departureTime < 0 || departureTime > 23) {
            throw new IllegalArgumentException("Departure time must be between 0 and 23.");
        }
        if (departureTime <= arrivalTime) {
            throw new IllegalArgumentException("Departure time must be strictly after arrival time.");
        }
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    /**
     * Checks if this flight's interval overlaps with another flight.
     * Two flights overlap if they share any time interval.
     */
    public boolean overlapsWith(Flight other) {
        if (other == null) return false;
        return this.arrivalTime < other.departureTime && other.arrivalTime < this.departureTime;
    }

    @Override
    public int compareTo(Flight other) {
        if (this.arrivalTime != other.arrivalTime) {
            return Integer.compare(this.arrivalTime, other.arrivalTime);
        }
        // Tie-breaker: sort by departure time (earlier departure first)
        return Integer.compare(this.departureTime, other.departureTime);
    }

    @Override
    public String toString() {
        return String.format("%s [Arr: %02d:00, Dep: %02d:00]", flightNumber, arrivalTime, departureTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Flight other = (Flight) obj;
        return this.flightNumber.equals(other.flightNumber);
    }

    @Override
    public int hashCode() {
        return flightNumber.hashCode();
    }
}
