package flapibirb.controller;

import flapibirb.view.Game;
import flapibirb.view.StartLeaderboardDialog;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

// Facade that coordinates the start dialog, game window, audio, and leaderboard
public class GameFacade {

    private static final int MIN_BIRB_CHOICE = 1;
    private static final int MAX_BIRB_CHOICE = 5;

    private String currentPlayerName;
    private int currentBirbChoice = MIN_BIRB_CHOICE;
    private Game currentGame;
    private boolean soundsLoaded = false;
    private boolean sessionEnded = false;

    // Opens the start dialog, validates input, preloads audio, and launches the game
    public void startSession() {
        // Make sure all Swing UI work runs on the Event Dispatch Thread
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::startSession);
            return;
        }

        // Modal dialog blocks here until the player enters a name and clicks Play
        StartLeaderboardDialog dialog = new StartLeaderboardDialog();
        dialog.setVisible(true);

        if (!dialog.isStarted()) {
            return;
        }

        String playerName = dialog.getPlayerName();
        int selectedBirb = dialog.getSelectedBirb();
        if (!validateSessionInput(playerName, selectedBirb)) {
            return;
        }

        currentPlayerName = playerName.trim();
        currentBirbChoice = selectedBirb;
        sessionEnded = false;

        preloadSounds();

        currentGame = new Game(currentPlayerName, currentBirbChoice, this);
        currentGame.setVisible(true);
        currentGame.toFront();
        currentGame.requestFocus();
    }

    // Stops timers, plays death SFX, saves the score, and returns to the start dialog
    public void endSession(int score) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> endSession(score));
            return;
        }

        // Prevent ending the same session twice
        if (sessionEnded) {
            return;
        }
        sessionEnded = true;

        if (currentGame != null) {
            currentGame.stopTimers();

            // Trigger bird death (and its SFX) if the caller hasn't already
            if (currentGame.getBird() != null && currentGame.getBird().isAlive()) {
                currentGame.getBird().die();
            }

            currentGame.cleanupAndDispose();
            currentGame = null;
        } else {
            SoundManager.play("hit");
            SoundManager.play("die");
        }

        if (currentPlayerName != null && !currentPlayerName.isBlank()) {
            StartLeaderboardDialog.saveScore(currentPlayerName, score);
        }

        // Reopen the start/leaderboard dialog for the next round
        SwingUtilities.invokeLater(this::startSession);
    }

    // Loads all game sound effects once per app run
    public void preloadSounds() {
        if (soundsLoaded) {
            return;
        }
        SoundManager.load("fly", "assets/sounds/flappy-bird-fly.wav");
        SoundManager.load("point", "assets/sounds/flappy-bird-point.wav");
        SoundManager.load("hit", "assets/sounds/flappy-bird-die-punch.wav");
        SoundManager.load("die", "assets/sounds/flappy-bird-die.wav");
        soundsLoaded = true;
    }

    // Double-checks player name and bird index before starting a session
    private boolean validateSessionInput(String playerName, int selectedBirb) {
        if (playerName == null || playerName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Please enter a valid player name.",
                    "Invalid Player",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        if (selectedBirb < MIN_BIRB_CHOICE || selectedBirb > MAX_BIRB_CHOICE) {
            JOptionPane.showMessageDialog(
                    null,
                    "Please select a valid bird option.",
                    "Invalid Bird Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        return true;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public int getCurrentBirbChoice() {
        return currentBirbChoice;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public boolean isSoundsLoaded() {
        return soundsLoaded;
    }
}

