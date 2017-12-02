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
 * @author jared
 */
public class AIController1 extends AbstractAIController {
    private static final double baseSpeed = 3.0d;
    private double speed;
    private final Vec2d target, ttarget, velocity;
    private final Vec2d tmp, ttmp;
    
    
    public AIController1(AbstractEntityAlien alien, Random r) {
        super(alien, r);
        speed = baseSpeed + (((double)EntityAlien.swarmDifficulty()) / 10);
        target  = new Vec2d(rng.nextDouble() * 448, rng.nextDouble() * 288);
        ttarget = new Vec2d(rng.nextDouble() * 448, rng.nextDouble() * 288);
        velocity = new Vec2d(0.0d, 4.0d);
        cooldown = rng.nextInt(60) + 15;
        tmp = new Vec2d();
        ttmp = new Vec2d();
    }
    
    
    public void calcSpeed() { 
        difficulty = (double)EntityAlien.swarmDifficulty();
        speed = baseSpeed + ((difficulty) / 10);
    }
    

    @Override
    public Vec2d getDirection() {
        Vec2d.sub(target, owner.getLoc(), tmp);
        if(tmp.sqLength() < 25.5d) {            
            target.set(rng.nextDouble() * 448, rng.nextDouble() * 288);
        }
        Vec2d.sub(ttarget, target, ttmp);
        if(ttmp.sqLength() < 25.0d) {            
            ttarget.set(rng.nextDouble() * 448, rng.nextDouble() * 288);
        }
        ttmp.setLength(speed);
        target.add(ttmp);
        tmp.normalize();
        velocity.add(tmp);
        velocity.setLength(speed);
        return velocity; 
    }
    

    @Override
    public int codedAction() {
        if(fire()) return 1;
        return 0;
    }
    
}
