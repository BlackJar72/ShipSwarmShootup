package jaredbgreat.shipswarm.ui;

import jaredbgreat.arcade.ui.input.IKeyTranslator;
import jaredbgreat.arcade.util.math.Bits;
import jaredbgreat.shipswarm.game.Game;
import java.awt.event.KeyEvent;

/**
 *
 * @author jared
 */
public class KeyDownTranslator implements IKeyTranslator {

    @Override
    public int translate(KeyEvent e) {
        if(Game.game.blockInput()) {
            return 0;
        }
        int key = e.getKeyCode();
        int out = 0;
        if(Game.game.isInGame()) {
            if((key == KeyEvent.VK_D) || (key == KeyEvent.VK_RIGHT)) {
                out = Bits.setBit(out, 1);
            }
            if((key == KeyEvent.VK_A) || (key == KeyEvent.VK_LEFT)) {
                out = Bits.setBit(out, 2);
            }
            if(((key == KeyEvent.VK_W) || (key == KeyEvent.VK_UP) 
                    || (key == KeyEvent.VK_ENTER) || (key == KeyEvent.VK_SPACE))) {
                out = Bits.setBit(out, 3);
            }
            if(key == KeyEvent.VK_PAUSE) {
                Game.game.togglePause();
            }
        } else {
            if((key != KeyEvent.VK_ESCAPE)) {
                Game.game.startGame();
            }            
        }
        return out;
    }
    
    
    private int setBit(int in, int bit) {
        return in | (1 << (bit - 1));
    }
    
}
