//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import flapibirb.view.Game;
import flapibirb.view.StartLeaderboardDialog;

class GameInstance {

    private static StartLeaderboardDialog SLDInstance= null;
    private static Game GInstance = null;
    private GameInstance() {
    }

    public static StartLeaderboardDialog getInstanceOfSLD() {
        if (SLDInstance == null) {
            SLDInstance = new StartLeaderboardDialog();
            SLDInstance.setModal(true);
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

        while (true) {
            StartLeaderboardDialog SLD = GameInstance.getInstanceOfSLD();
            Game G = GameInstance.getInstanceOfGame();
        }
    }
}