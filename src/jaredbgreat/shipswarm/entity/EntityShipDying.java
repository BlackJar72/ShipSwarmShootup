/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.shipswarm.entity;

import jaredbgreat.arcade.entity.Entity;
import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.util.memory.ObjectPool;
import jaredbgreat.arcade.util.memory.ObjectPool.ObjectFactory;
import jaredbgreat.shipswarm.game.Game;

/**
 *
 * @author jared
 */
public class EntityShipDying extends Entity {
    private int frames;
    private static final ObjectFactory<EntityShipDying> factory 
            = new ObjectFactory<EntityShipDying>() {
        @Override
        public EntityShipDying create() {
            return new EntityShipDying();
        }
    };
    public static final ObjectPool<EntityShipDying> pool 
            = new ObjectPool(factory, 24);
    
    
    private EntityShipDying() {
        controller = new InactiveController();
    }
    
    
    public EntityShipDying initDeath(double x, double y, int pic) {
        locX = x;
        locY = y;
        graphic = pic;
        frame = 0;
        frames = Graphic.registry.get(pic).size() - 1; 
        return this;
    }
    
    
    @Override
    public boolean isCollideable() {
        return false;
    }
    
    
    @Override
    public void update(double delta) {
        frame++;
        if(frame > frames) {
            Game.game.kill(this);
            pool.free(this);
        }
    }
    
}
