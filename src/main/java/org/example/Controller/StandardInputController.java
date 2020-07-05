package org.example.Controller;


import java.util.InputMismatchException;

public class StandardInputController {

    public int getPlayerCountFromUser() {
        System.out.println("Please enter number of players:");
        return ScannerUtils.readNextSingleInt();
    }

    public int getTrackNumberFromUser() {
        System.out.println("Please select a track:");
        return ScannerUtils.readNextSingleInt();
    }

    public String getVehicleMakeFromUser() {
        System.out.println("Please enter vehicle make:");
        return ScannerUtils.SCANNER.nextLine();
    }

    public double getAccelerationSpeedFromUser() {
        System.out.println("Please enter acceleration speed:");
        try {
            return ScannerUtils.readNextSingleDouble();
        } catch (InputMismatchException e) {
            System.out.println("You have entered an invalid value. Please try again.");

            // recursion
            return getAccelerationSpeedFromUser();
        }
    }
}
