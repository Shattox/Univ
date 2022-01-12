package fr.umlv.rental;

public sealed interface Transport permits Car, Camel {
    int date();
    long insuranceCostAt(int currentDate);
}
