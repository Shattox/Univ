package fr.umlv.rental;

public record Camel(int date) implements Transport {
    @Override
    public String toString() {
        return "camel " + date;
    }

    @Override
    public int date() {
        return date;
    }
    
    @Override
    public long insuranceCostAt(int currentDate) {
    	return (currentDate - date) * 100;
    }
}
