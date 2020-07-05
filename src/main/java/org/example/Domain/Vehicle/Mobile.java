package org.example.Domain.Vehicle;

public interface Mobile {

    double accelerate(double speed, double durationInHours);

    String getName();

    boolean canMove();

    double getTotalTraveledDistance();
}
