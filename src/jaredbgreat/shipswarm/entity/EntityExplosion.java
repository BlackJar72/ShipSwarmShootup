/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.shipswarm.entity;

import jaredbgreat.arcade.entity.Entity;
import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.ui.sound.Sound;
import jaredbgreat.arcade.util.memory.ObjectPool;
import jaredbgreat.arcade.util.memory.ObjectPool.ObjectFactory;
import jaredbgreat.shipswarm.game.Game;

/**
 *
 * @author jared
 */
public class EntityExplosion extends Entity {
    private boolean shrinking;
    private final int frames;
    private static final ObjectFactory<EntityExplosion> factory 
            = new ObjectFactory<EntityExplosion>() {
        @Override
        public EntityExplosion create() {
            return new EntityExplosion();
        }
    };
    public static final ObjectPool<EntityExplosion> pool 
            = new ObjectPool(factory, 64);
    
    
    private EntityExplosion() {
        controller = new InactiveController();
        graphic = Graphic.registry.getID("explosion");
        frames = 5;
    }
    
    
    public EntityExplosion initExplosion(double x, double y) {
        locX = x;
        locY = y;
        frame = 0;
        shrinking = false;
        Sound.registry.getFromName("explosion").play();
        return this;
    }
    
    
    @Override
    public boolean isCollideable() {
        return false;
    }
    
    
    @Override
    public void update(double delta) {
        if(shrinking) {
            frame--;
            if(frame < 1) {
                Game.game.kill(this);
                pool.free(this);
            }
        } else {
            frame++;
            if(frame > frames) {
                shrinking = true;
            }
        }
    }    
}
