package jaredbgreat.shipswarm.entity;

import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.ui.sound.Sound;
import jaredbgreat.arcade.util.memory.ObjectPool;
import jaredbgreat.arcade.util.memory.ObjectPool.ObjectFactory;
import jaredbgreat.shipswarm.game.Game;
import java.util.Random;

/**
 *
 * @author jared
 */
public class EntityAlien extends AbstractEntityAlien {
    //private final  int deathImage;
    private static int num;
    private static int swarm;
    private static final ObjectFactory<EntityAlien> factory 
            = new ObjectFactory<EntityAlien>() {
        @Override
        public EntityAlien create() {
            return new EntityAlien();
        }
    };
    public static final ObjectPool<EntityAlien> pool 
            = new ObjectPool(factory, 12);
    
    
    private EntityAlien() {
        super(Graphic.registry.getID("alien1-die"));        
        firesound = Sound.registry.getID("alienfire");
        controller = new AIController1(this, Game.game.getRNG());
        graphic = Graphic.registry.getID("alien1");
        frame = 0;
    }
    
    
    public EntityAlien initAlien(int x, int y) {
        locX = x;
        locY = y;
        onScreen = false;
        ((AIController1)controller).calcSpeed();
        return this;
    }
    
    
    @Override
    public void die() {
        super.die();
        pool.free(this);
        EntityPlayer.player.incrementScore(15 
                + EntityAlien.swarmDifficulty());
        EntityAlien.decrementNum();
    }
    
    
    public static void spawnSwarm() {
        num = 8 + (swarmDifficulty() / 3);
        Random rng = Game.game.getRNG();
        for(int i = 0; i < num; i++) {
            Game.game.spawn(pool.getObject().initAlien((i * (400 / num)) + 40, 
                   -128 - rng.nextInt(256)));
        }
        swarm++;
        if(((swarm % 3) == 0) && swarm < 13) {
            EntityPlayer.player.addLife();
        }
        if(swarm > 3) {
            Game.game.spawn(EntityAlienSpeeder.pool.getObject()
                    .initSpeeder(rng.nextInt(284) + 80, 
                            -512 - rng.nextInt(1024)));
        }
        if(swarm > 15) {
            Game.game.spawn(EntityAlienSpeeder.pool.getObject()
                    .initSpeeder(rng.nextInt(284) + 80, 
                            -1536 - rng.nextInt(512)));
        }
        if((swarm > 6) && ((swarm % 3) == 0)) {
            Game.game.spawn(EntityAlienBomber.getSaucer());
        }
        
    }
    
    
    public static void reset() {
        num = 0;
        swarm = 0;
    }
    
    
    public static void decrementNum() {
        num--;
        if(num < 1) {
            spawnSwarm();
        }
    }
    
    
    public static int swarmDifficulty() {
        return Math.min(swarm, 12);
    }
    
    
    public static int swarmNumber() {
        return swarm;
    }    
    
    
}
