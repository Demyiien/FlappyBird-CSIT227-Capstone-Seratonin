//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import flapibirb.view.Game;
import flapibirb.view.StartLeaderboardDialog;

import java.sql.SQLOutput;

class GameInstance {

    private static StartLeaderboardDialog SLDInstance= null;
    private static Game GInstance = null;
    private GameInstance() {
    }

    public static StartLeaderboardDialog getInstanceOfSLD() {
        if (SLDInstance == null) {
            SLDInstance = new StartLeaderboardDialog();
            SLDInstance.setVisible(true);
            return SLDInstance;
        }

        return null;
    }

    public static Game getInstanceOfGame() {
        if (GInstance == null) {
            GInstance = new Game();
            GInstance.setVisible(true);
            return GInstance;
        }

        return null;
    }

}


public class Main {
    public static void main(String[] args) {
        StartLeaderboardDialog SLD = GameInstance.getInstanceOfSLD();
        while (true) {
            assert SLD != null;
            if (!SLD.isVisible()) break;
            if (SLD.isStarted()) {
                GameInstance.getInstanceOfGame();
            }
        }


    }
}