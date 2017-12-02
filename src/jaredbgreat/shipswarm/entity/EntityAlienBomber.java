/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.shipswarm.entity;

import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.ui.sound.Sound;
import jaredbgreat.arcade.util.math.Vec2d;
import jaredbgreat.shipswarm.game.Game;
import java.util.Random;

/**
 *
 * @author jared
 */
public class EntityAlienBomber extends AbstractEntityAlien {
    private static EntityAlienBomber saucer;
    private int dir;
    private final  Random rng;
    
    
    private EntityAlienBomber() {
        super(Graphic.registry.getID("alien3-die"));      
        firesound = Sound.registry.getID("photonbomb");
        rng = Game.game.getRNG();
        controller = new AIController3(this, rng);
        locX = 0;
        locY = 0;
        graphic = Graphic.registry.getID("alien3");
        frame = 0;
        dir = -1;
    }
    
    
    @Override
    public void update(double delta) {
        locX += (delta * controller.getDirection().getX());
        if(((dir > 0) && (locX > 480)) || ((dir < 0) && (locX < -32))) {
            Game.game.kill(this);
        }
        if(controller.fire()) {
            EntityPhotonBomb proj; 
            proj = EntityPhotonBomb.pool.getObject()
                    .initBomb(locX + 15d, locY + 15d, new Vec2d(0.0d, 7.0d));
            Game.game.spawn(proj);            
            Sound.registry.get(firesound).play();
        }
    }
    
    
    public static EntityAlienBomber getSaucer() {
        if(saucer == null) {
            saucer = new EntityAlienBomber();
        }
        saucer.dir *= -1;
        saucer.locY = 270 + saucer.rng.nextInt(36);
        if(saucer.dir > 0) {
            saucer.locX = (double)(-128 - saucer.rng.nextInt(512));
        } else {
            saucer.locX = (double)(688 + saucer.rng.nextInt(512));
        }
        ((AIController3)saucer.controller).setDir(saucer.dir);
        return saucer;
    }
    
    
    @Override
    public void die() {
        super.die();
        EntityPlayer.player.incrementScore(150);
    }
    
    
    
    
}
