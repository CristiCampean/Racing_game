package org.example.Service;

import org.example.Controller.StandardInputController;
import org.example.Domain.Vehicle.*;
import org.example.Exception.InvalidOptionSelectedException;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private Track[] tracks = new Track[3];
    private Track selectedTrack;
    private List<Mobile> competitors = new ArrayList<>();
    private Set<Mobile> outOfRaceCompetitors = new HashSet<>();

    private boolean winnerNotKnown = true;

    private StandardInputController controller = new StandardInputController();

    public void start() throws InvalidOptionSelectedException {
        System.out.println("Welcome to the Racing Game!");
        System.out.println("\uD83D\uDE97");

        initializeTracks();

        selectedTrack = getSelectedTrack();

        initializeCompetitors();

        while (winnerNotKnown && outOfRaceCompetitors.size() < competitors.size()) {
            playOneRound();
        }

        if (winnerNotKnown) {
            System.out.println("Game over. There's no winner.");
        }

        processRankingTable();
    }

    private void processRankingTable() {
        competitors.sort(Collections.reverseOrder(new MobileComparator()));

        System.out.println("Rankings:");
        for (int i = 0; i < competitors.size(); i++) {
            System.out.println((i + 1) + ". " + competitors.get(i).getName() + ": " +
                    competitors.get(i).getTotalTraveledDistance());
        }
    }

    private void initializeCompetitors() {
        int playerCount = controller.getPlayerCountFromUser();

        for (int i = 1; i <= playerCount; i++) {
            System.out.println("Preparing player " + i + " for the race...");

            Vehicle vehicle = new Car();
            vehicle.setMake(controller.getVehicleMakeFromUser());
            vehicle.setFuelLevel(30);
            vehicle.setMaxSpeed(300);
            vehicle.setMileage(ThreadLocalRandom.current().nextDouble(9, 15));

            competitors.add(vehicle);
        }
    }

    private void playOneRound() {
        System.out.println();
        System.out.println("New round");
        System.out.println();

        // enhanced for (for-each)
        for (Mobile competitor : competitors) {
            if (!competitor.canMove()) {
                outOfRaceCompetitors.add(competitor);
                continue;
            }

            System.out.println();
            double speed = controller.getAccelerationSpeedFromUser();

            competitor.accelerate(speed, 1);

            if (competitor.getTotalTraveledDistance() >= selectedTrack.getLength()) {
                System.out.println("Congratz! The winner is: " + competitor.getName());
                winnerNotKnown = false;
                break;
            }
        }
    }

    private Track getSelectedTrack() throws InvalidOptionSelectedException {
        try {
            int optionNumber = controller.getTrackNumberFromUser();
            return tracks[optionNumber - 1];
        } catch (InputMismatchException e) {
            throw new RuntimeException("Invalid value entered.");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidOptionSelectedException();
        }
    }

    private void initializeTracks() {
        Track track1 = new Track();
        track1.setName("Street Circuit");
        track1.setLength(300);

        tracks[0] = track1;

        Track track2 = new Track();
        track2.setName("Forest Circuit");
        track2.setLength(100);

        tracks[1] = track2;

        displayTracks();
    }

    private void displayTracks() {
        System.out.println("Available tracks:");

        // classic for loop
        for (int i = 0; i < tracks.length; i++) {
            if (tracks[i] != null) {
                System.out.println(i + 1 + ". " + tracks[i].getName() + ": " + tracks[i].getLength() + " km");
            }
        }
    }
}
