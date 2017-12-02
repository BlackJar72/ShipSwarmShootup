//Copyright (c) Jared Blackburn 2017
//An arcade space shooter
package jaredbgreat.shipswarm.entity;

import jaredbgreat.arcade.entity.IInputController;
import jaredbgreat.arcade.util.math.Bits;
import jaredbgreat.arcade.util.math.Vec2d;

/**
 *
 * @author jared
 */
public class InputController implements IInputController {
    public static final InputController in = new InputController();
    private static final double baseSpeed = 5.0d;
    private final Vec2d velocity = new Vec2d(0.0f, 0.0f);
    private boolean firing;

    @Override
    public Vec2d getDirection() {
        return velocity;
    }
    
    
    @Override
    public void update(int in) {
        double speed = 0.0d;
        // Unlike with Macy Mae, here conflicting direction cancel.
        if(Bits.checkBit(in, 1)) {
            speed += baseSpeed;
            //System.out.println("Pressing right");
        }
        if(Bits.checkBit(in, 2)) {
            speed -= baseSpeed;
            //System.out.println("Pressing left");
        }
        velocity.setX(speed);
        firing = Bits.checkBit(in, 3);
    }

    @Override
    public boolean fire() {
        return firing;
    }
    

    @Override
    public int codedAction() {
        if(fire()) return 1;
        return 0;
    }
    
    
    public void clear() {
        velocity.setX(0.0f);
        firing = false;
    }
    
}
