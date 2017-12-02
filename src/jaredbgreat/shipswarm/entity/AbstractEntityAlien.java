/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.shipswarm.entity;

import jaredbgreat.arcade.entity.Entity;
import jaredbgreat.arcade.ui.sound.Sound;
import jaredbgreat.arcade.util.math.Vec2d;
import jaredbgreat.shipswarm.game.Game;

/**
 *
 * @author jared
 */
public abstract class AbstractEntityAlien extends Entity {
    protected boolean onScreen;
    protected final int deathImage;
    protected int firesound;
    
    
    public AbstractEntityAlien(int death) {
        deathImage = death;
    }
    
    
    
    @Override
    public void update(double delta) {
        if(onScreen) {
            super.update(delta);
        } else {
            Vec2d velocity = controller.getDirection();
            locX = (int)Math.min(448, Math.max(0, locX + (delta * velocity.getX())));
            locY = locY + (delta * velocity.getY());
            onScreen = locY > 0;
        }
        if(onScreen && controller.fire()) {            
            EntityProjectile proj; 
            proj = EntityProjectile.pool.getObject()
                    .initProjectile(locX + 15d, locY + 20, 
                        new Vec2d(0.0d, 9.0d), false);
            Game.game.spawn(proj);
            Sound.registry.get(firesound).play();
        }
    }
    
    
    @Override
    public void collide(Entity other) {
        if(other instanceof EntityProjectile) {
            other.collide(this);
        }
    }
    
    
    @Override
    public void die() {          
        Game.game.spawn(EntityExplosion.pool.getObject()
                .initExplosion(locX, locY));
        Game.game.spawn(EntityShipDying.pool.getObject()
                .initDeath(locX, locY,  deathImage));
        Game.game.kill(this);
    }
}
