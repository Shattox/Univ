package fr.umlv.rental;

import java.util.Objects;

public record Car(String model, int date) implements Transport {
    public Car {
        Objects.requireNonNull(model);
    }

    @Override
    public String toString() {
        return model + " " + date;
    }

    @Override
    public int date() {
        return date;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Car other &&
            model.equals(other.model());
    }

    @Override
    public int hashCode() {
        return model.hashCode();
    }

    @Override
    public long insuranceCostAt(int currentDate) {
        return (currentDate - date) < 10 ? 200 : 500;
    }
}
