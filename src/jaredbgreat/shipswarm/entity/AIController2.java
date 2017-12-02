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
public class AIController2 extends AbstractAIController {
    private static final double baseSpeed = 5.0d;
    private double speed;
    private final Vec2d fake;

    public AIController2(AbstractEntityAlien alien, Random r) {
        super(alien, r);
        speed = baseSpeed + (((double)EntityAlien.swarmDifficulty()) / 10.0d);
        cooldown = 15;
        fake = new Vec2d(0.0d, speed);
    }
    
    
    public void calcSpeed() {
        difficulty = (double)EntityAlien.swarmDifficulty();
        speed = baseSpeed + ((difficulty) / 10);
    }

    @Override
    public Vec2d getDirection() {
        return fake;
    }
    

    @Override
    public int codedAction() {
        if(fire()) return 1;
        return 0;
    }
    
}
