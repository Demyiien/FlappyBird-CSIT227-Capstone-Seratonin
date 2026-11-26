import flapibirb.controller.GameFacade;

// Entry point: delegates startup and session flow to GameFacade
public class Main {
    public static void main(String[] args) {
        GameFacade gameFacade = new GameFacade();
        gameFacade.startSession();
    }
}
