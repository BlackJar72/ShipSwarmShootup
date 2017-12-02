package jaredbgreat.shipswarm.entity;

import jaredbgreat.arcade.entity.Entity;
import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.ui.sound.Sound;
import jaredbgreat.arcade.util.math.Vec2d;
import jaredbgreat.arcade.util.memory.ObjectPool;
import jaredbgreat.arcade.util.memory.ObjectPool.ObjectFactory;
import jaredbgreat.shipswarm.game.Game;

/**
 *
 * @author jared
 */
public class EntityAlienSpeeder extends AbstractEntityAlien {
    private static final double range = 30.0d;
    private double midline;
    private double startY;
    private static final ObjectFactory<EntityAlienSpeeder> factory 
            = new ObjectPool.ObjectFactory<EntityAlienSpeeder>() {
        @Override
        public EntityAlienSpeeder create() {
            return new EntityAlienSpeeder();
        }
    };
    public static final ObjectPool<EntityAlienSpeeder> pool 
            = new ObjectPool(factory, 12);
    

    private EntityAlienSpeeder() {
        super(Graphic.registry.getID("alien2-die"));        
        firesound = Sound.registry.getID("alienfire");
        graphic = Graphic.registry.getID("alien2");
        controller = new AIController2(this, Game.game.getRNG());
        frame = 0;
    }
    
    
    public EntityAlienSpeeder initSpeeder(int x, int y) {
        locX = x;
        locY = y;
        midline = x;
        startY = y;
        ((AIController2)controller).calcSpeed();
        return this;
    }
    
    
    
    @Override
    public void update(double delta) {
        if((locY < -32) && EntityPlayer.player.isDead()) {
            Game.game.kill(this);
            pool.free(this);
        }
        Vec2d velocity = controller.getDirection();
        locY = locY + (delta * velocity.getY());
        locX = midline + (Math.sin(Math.toRadians(locY - startY)) * range);
        if(locY > 576) {
            Game.game.kill(this);
        }
        if((locY > 0) && controller.fire()) {            
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
        super.collide(other);
        if(other instanceof EntityPlayer) {       
        Game.game.spawn(EntityExplosion.pool.getObject()
                .initExplosion(locX, locY));
        Game.game.spawn(EntityShipDying.pool.getObject()
                .initDeath(locX, locY,  deathImage));
        Game.game.kill(this);
        ((EntityPlayer)other).die();
        }
    }
        
    
    @Override
    public void die() {
        super.die();
        EntityPlayer.player.incrementScore(30 
                + (EntityAlien.swarmDifficulty() * 10));
        pool.free(this);
    }
    
    
    
    
    
}
