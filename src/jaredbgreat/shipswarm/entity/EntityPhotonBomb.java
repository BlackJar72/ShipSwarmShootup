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
public class EntityPhotonBomb extends Entity {
    private Vec2d velocity;
    private static final ObjectFactory<EntityPhotonBomb> factory 
            = new ObjectPool.ObjectFactory<EntityPhotonBomb>() {
        @Override
        public EntityPhotonBomb create() {
            return new EntityPhotonBomb();
        }
    };
    public static final ObjectPool<EntityPhotonBomb> pool 
            = new ObjectPool(factory, 64);
    
    private EntityPhotonBomb() {
        graphic = Graphic.registry.getID("bomb");
    }
    
    
    public EntityPhotonBomb initBomb(double x, double y, Vec2d vel) {
        locX = x;
        locY = y;
        velocity = vel;
        frame = 0;
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
        if(other instanceof EntityPlayer) {
            other.die();
            die();
        }
    }
    
    
    @Override
    public void die() {
        pool.free(this);
        Game.game.spawn(EntityExplosion.pool.getObject()
                .initExplosion(locX - 13.0d,locY - 13.0d));
        super.die();
        
    }
    
}
