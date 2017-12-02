/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.shipswarm.entity;

import jaredbgreat.arcade.entity.IController;
import jaredbgreat.arcade.util.math.Vec2d;

/**
 *
 * @author jared
 */
public class InactiveController implements IController {
    public final Vec2d stillness = new Vec2d(0, 0);

    
    @Override
    public Vec2d getDirection() {
        return stillness;
    }
    

    @Override
    public boolean fire() {
        return false;
    }
    

    @Override
    public int codedAction() {
        return 0;
    }
    
}
