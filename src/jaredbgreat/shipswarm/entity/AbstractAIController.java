/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.shipswarm.entity;


import jaredbgreat.arcade.entity.IController;
import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.util.math.Collision;
import jaredbgreat.arcade.util.math.Vec2d;
import java.util.Random;

/**
 *
 * @author jared
 */
public abstract class AbstractAIController implements IController {    
    protected final AbstractEntityAlien owner;
    protected final Random rng;
    protected int cooldown;
    protected double difficulty;
    
    
    public AbstractAIController(AbstractEntityAlien alien, Random r) {
        owner = alien;
        rng = r;
        cooldown = rng.nextInt(60) + 15;
    }
    
    
    

    @Override
    public boolean fire() {
        if(EntityPlayer.player.isDead()) {
            return false;
        }
        if(cooldown > 0) {
            cooldown--;
            return false;            
        }
        Vec2d oloc = owner.getLoc();
        Vec2d ploc = EntityPlayer.player.getLoc();
        if((Math.abs(oloc.getX() - ploc.getX()) < 0.25d) && rng.nextBoolean()) {
            setCooldowns();
            return true;
        }
        double ow = Graphic.registry.get(owner.getGraphic())
                .getImage().getWidth();
        double pw = Graphic.registry.get(EntityPlayer.player.getGraphic())
                .getImage().getWidth();
        if(Collision.lineOverlap(oloc.getX(), oloc.getX() 
                + ow, ploc.getX(), ploc.getX() + pw)) {
            setCooldowns();
            return rng.nextBoolean();            
        }
        // Someimes try to lead shots
        double toBottom = (576 - oloc.getY()) / 9;
        double toAlign  = (oloc.getX() - ploc.getX()) / EntityPlayer.player
                .getVelocity().getX();
        if((Math.abs(toBottom - toAlign) < difficulty) 
                && (rng.nextInt(12) < EntityAlien.swarmDifficulty())) {
            setCooldowns();
            return true;
        }        
        return false;
    }
    
    
    private void setCooldowns() {
            cooldown = Math.max(15, rng.nextInt(60) + rng.nextInt(60) + 60 
                    - (5 * EntityAlien.swarmDifficulty()));        
    }
    
}
