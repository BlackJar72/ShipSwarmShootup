package jaredbgreat.shipswarm.ui;

import jaredbgreat.arcade.ui.AbstractClickablePanel;
import jaredbgreat.arcade.ui.graphics.Graphic;

/**
 *
 * @author jared
 */
public class AboutPanel extends AbstractClickablePanel {
    int graphic;
    
    public AboutPanel() {
        graphic = Graphic.registry.getID("infopage");
    }

    @Override
    public void drawGame() {
        Graphic.draw(graphic, 0, 0, 0);
        drawWidgets();
    }
    
}
