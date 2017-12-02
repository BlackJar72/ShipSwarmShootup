/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.shipswarm.entity;

import jaredbgreat.arcade.entity.Entity;
import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.util.math.Vec2d;
import jaredbgreat.arcade.util.memory.ObjectPool;
import jaredbgreat.arcade.util.memory.ObjectPool.ObjectFactory;
import jaredbgreat.shipswarm.game.Game;

/**
 *
 * @author jared
 */
public class EntityProjectile extends Entity {
    public boolean isPlayers;
    private Vec2d velocity;
    private static final ObjectFactory<EntityProjectile> factory 
            = new ObjectFactory<EntityProjectile>() {
        @Override
        public EntityProjectile create() {
            return new EntityProjectile();
        }
    };
    public static final ObjectPool<EntityProjectile> pool 
            = new ObjectPool(factory, 64);
    
    private EntityProjectile() {
        graphic = Graphic.registry.getID("attack");
        frame = 0;
    }
    
    
    public EntityProjectile initProjectile(double x, double y, 
            Vec2d vel, boolean player) {
        locX = x;
        locY = y;
        velocity = vel;
        isPlayers = player;
        return this;
    }
    
    
    @Override
    public void update(double delta) {
        locX += (delta * velocity.getX());
        locY += (delta * velocity.getY());
        if((locX < -2) || (locX > 480) || (locY < -12) || (locY > 576)) {
            Game.game.kill(this);
            pool.free(this);
        }
    }
    
    
    @Override
    public void collide(Entity other) {
        if(isPlayers != (other instanceof EntityPlayer)) {
            other.die();
            Game.game.kill(this);
        }
    }
    
}
