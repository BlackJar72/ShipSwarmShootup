package jaredbgreat.shipswarm.ui;

import jaredbgreat.arcade.ui.input.IKeyTranslator;
import jaredbgreat.arcade.util.math.Bits;
import jaredbgreat.shipswarm.game.Game;
import java.awt.event.KeyEvent;

/**
 *
 * @author Jared Blackburn
 */
public class KeyUpTranslator implements IKeyTranslator {

    @Override
    public int translate(KeyEvent e) {
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
        }
        return out;
    }
    
}
