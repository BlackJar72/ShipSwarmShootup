//Copyright (c) Jared Blackburn 2017
package jaredbgreat.shipswarm;

import jaredbgreat.arcade.loader.AudioLoader;
import jaredbgreat.arcade.loader.ImageLoader;
import jaredbgreat.arcade.ui.MainWindow;
import jaredbgreat.arcade.ui.graphics.Font;
import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.ui.input.InputAgregator;
import jaredbgreat.arcade.ui.input.KeyInput;
import jaredbgreat.arcade.ui.input.MouseInput;
import jaredbgreat.shipswarm.entity.EntityPlayer;
import jaredbgreat.shipswarm.entity.InputController;
import jaredbgreat.shipswarm.game.Game;
import jaredbgreat.shipswarm.ui.GamePanel;
import jaredbgreat.shipswarm.ui.KeyDownTranslator;
import jaredbgreat.shipswarm.ui.KeyUpTranslator;

/**
 *
 * @author Jared Blackburn
 */
public class ShipSwarm {
    private volatile static MainWindow window;
    private volatile static Game game;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        init();
        run();
        cleanup();        
    }
    
    
    private static void init() {
        ImageLoader.initGraphics();
        AudioLoader.initAudio();
        Graphic.init(480, 640);
        Font.init();
        InputAgregator.init(new KeyInput(new KeyDownTranslator(), 
                new KeyUpTranslator()), 
                new MouseInput(), 
                InputController.in);
        window = MainWindow.getMainWindow(480, 640);
        window.addPanel("game", new GamePanel());
        window.setGamePanel("game");
        game = (Game)Game.getGame();
    }
    
    
    private static void run() {
        EntityPlayer.initPlayer();
        game.start(window);
    }
    
    
    private static void cleanup() {
        window.cleanup();
        Graphic.cleanup();
    }
    
}
