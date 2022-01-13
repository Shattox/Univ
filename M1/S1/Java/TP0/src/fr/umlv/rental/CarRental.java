package fr.umlv.rental;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarRental {
    private final List<Transport> transports = new ArrayList<>();

    public void add(Transport transport) {
        Objects.requireNonNull(transport);
        transports.add(transport);
    }

    public void remove(Transport transport) {
        Objects.requireNonNull(transport);
        if (!transports.contains(transport)) {
            throw new IllegalStateException();
        }
        transports.remove(transport);
    }

    // Méthode avec StringBuilder
    /*
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Car car: transports) {
            sb.append(car).append("\n");
        }
        return sb.toString().trim();
    }*/

    @Override
    public String toString() {
        return transports.stream()
            .map(Transport::toString)
            .collect(Collectors.joining("\n"));
    }

    public List<Transport> findAllByYear(int date) {
        return transports.stream()
            .filter(transport -> transport.date() == date)
            .toList();
    }
    
    private void checkAge(int currentDate, int date) {
    	if ((currentDate - date) < 0) {
    		throw new IllegalArgumentException("Age < 0");
    	}
    }
    
    public long insuranceCostAt(int currentDate) {
    	return transports.stream()
    			.mapToLong(transport -> {
    				checkAge(currentDate, transport.date());
    				return transport.insuranceCostAt(currentDate);})
    			.sum();
    }
    
    public Optional<Car> findACarByModel(String model) {
    	Objects.requireNonNull(model);
    	return transports.stream()
    			.flatMap(transport -> transport instanceof Car car &&
    					car.model().equals(model) ?
    					Stream.of(new Car(car.model(), car.date())) : null
    			)
    			.findFirst();
    }
}
