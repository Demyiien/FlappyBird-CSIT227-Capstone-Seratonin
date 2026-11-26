package flapibirb.controller;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private static final Map<String, Clip> clips = new HashMap<>();

    public static void load(String name, String path) {
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path))) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            Clip previous = clips.put(name, clip);
            if (previous != null) {
                previous.close();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
            System.err.println("Could not load sound (" + path + "): " + e.getMessage());
        }
    }

    public static void play(String name) {
        Clip clip = clips.get(name);
        if (clip == null) return;

        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0); // rewind to start
        clip.start();
    }
}