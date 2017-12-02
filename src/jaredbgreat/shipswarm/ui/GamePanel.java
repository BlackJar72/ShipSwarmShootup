package jaredbgreat.shipswarm.ui;

import jaredbgreat.arcade.entity.Entity;
import jaredbgreat.arcade.ui.AbstractGamePanel;
import jaredbgreat.arcade.ui.graphics.Font;
import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.shipswarm.entity.EntityAlien;
import jaredbgreat.shipswarm.entity.EntityPlayer;
import jaredbgreat.shipswarm.game.Game;

/**
 *
 * @author jared
 */
public class GamePanel extends AbstractGamePanel {
    private final int board;
    
    public GamePanel() {
        board = Graphic.registry.getID("stars");
    }

    @Override
    public void drawGame() {
        Graphic.draw(board, 0, 0, 0);
        for(int i = Entity.listSize() - 1; i > -1; i--) {
            Entity.get(i).draw();
        }
        
        // I could cache strings, etc., but profiling show this little effect 
        // on garbage collection, which is already at an insignificant level.
        Font.drawString("Score: " + EntityPlayer.player.getScore(), 16,  608);
        Font.drawString(" Wave: " + EntityAlien.swarmNumber(),      16,  580);
        Font.drawString("Lives: " + EntityPlayer.player.getLives(), 272, 580);
        
        if(Game.game.isGameOver()) {
            Font.drawString("Game Over", 144, 276);
        }}
    
}
