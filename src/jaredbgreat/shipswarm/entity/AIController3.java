/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.shipswarm.entity;

import jaredbgreat.arcade.util.math.Vec2d;
import java.util.Random;

/**
 *
 * @author Jared Blackburn
 */
public class AIController3 extends AbstractAIController {
    private final Vec2d dir;
    
    
    public AIController3(AbstractEntityAlien alien, Random r) {
        super(alien, r);
        dir = new Vec2d(2, 0);
        cooldown = 30;
    }
    
    
    @Override
    public Vec2d getDirection() {
        return dir;
    }
    
    
    @Override
    public boolean fire() {
        if(EntityPlayer.player.isDead()) {            
            cooldown = 30;
        } else if(cooldown-- < 1) {
            cooldown = 30;
            return true;
        }
        return false;
    }
    
    
    public void setDir(int in) {
        if(in > 0) {
            dir.setX(2.0d);
        } else {
            dir.setX(-2.0d);
        }
    }
    

    @Override
    public int codedAction() {
        if(fire()) return 1;
        return 0;
    }
    
}
