package jaredbgreat.shipswarm.game;

import jaredbgreat.arcade.entity.Entity;
import jaredbgreat.arcade.game.BaseGame;
import jaredbgreat.arcade.game.ILoopElement;
import jaredbgreat.arcade.ui.MainWindow;
import jaredbgreat.arcade.ui.StartPanel;
import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.ui.input.InputAgregator;
import jaredbgreat.arcade.ui.input.clickzone.ActiveGraphicZone;
import jaredbgreat.arcade.ui.input.clickzone.IZoneAction;
import jaredbgreat.arcade.util.math.Collision;
import jaredbgreat.shipswarm.entity.EntityAlien;
import jaredbgreat.shipswarm.entity.EntityPlayer;
import jaredbgreat.shipswarm.ui.AboutPanel;
import java.awt.event.MouseEvent;

/**
 *
 * @author jared
 */
public class Game extends BaseGame { 
    private InputAgregator in;
    
    
    private Game() {
        super();        
        in = InputAgregator.getInputAgregator();
        
        update = new ILoopElement() {
            @Override
            public void update() {
                Entity.updateAll(delta);
            }
        };
        
        render = new ILoopElement() {
            @Override
            public void update() {
                window.draw();
            }
        };
        
        physical = new ILoopElement() {
            @Override
            public void update() {
                Collision.testCollision();
            }
        };
        
        spawning = new ILoopElement() {
            @Override
            public void update() {
                doKills();
                EntityPlayer.player.respawn();
                doSpawns();
            }
        };
        
        input = new ILoopElement() {
            @Override
            public void update() {
                in.update();
            }
        };
        
        setupWindows();
    }
    
    
    public static BaseGame getGame() {
        if(game == null) {
            game = new Game();
        }
        return game;
    }
    
    
    @Override
    public void startGame() {
        super.startGame();
        EntityPlayer.player.reset();
        Entity.add(EntityPlayer.player);
        EntityAlien.reset();
        EntityAlien.spawnSwarm();        
    }
    
    
    private void setupWindows() {
        AboutPanel about = new AboutPanel();
        MainWindow.getMainWindow().addPanel("about", about);
        StartPanel start 
                = (StartPanel)MainWindow.getMainWindow().getPanel("start");
        ActiveGraphicZone widget 
                = new ActiveGraphicZone(0, 0, Graphic.registry.getID("play"));
        start.addWidget(widget);
        about.addWidget(widget);
        widget.setAction(new IZoneAction() {
            @Override
            public void activate(MouseEvent e) {
                startGame();
            }
        });
        widget = new ActiveGraphicZone(416, 0, Graphic.registry.getID("info"));
        start.addWidget(widget);
        widget.setAction(new IZoneAction() {
            @Override
            public void activate(MouseEvent e) {
                MainWindow.getMainWindow().setPanel("about");
            }
        });
        widget = new ActiveGraphicZone(416, 0, Graphic.registry.getID("back"));
        about.addWidget(widget);
        widget.setAction(new IZoneAction() {
            @Override
            public void activate(MouseEvent e) {                
                MainWindow.getMainWindow().setPanel("start");
            }
        });
    }
}
