/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.shipswarm.entity;

import jaredbgreat.arcade.entity.Entity;
import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.shipswarm.game.Game;

/**
 *
 * @author jared
 */
public class EntitySpawnFog extends Entity {
    private boolean shrinking;
    private final int frames;
    public static final EntitySpawnFog spawnfog = new EntitySpawnFog();
    
    
    private EntitySpawnFog() {
        controller = new InactiveController();
        graphic = Graphic.registry.getID("respawn");
        frames = 5;
        locX = 224;
        locY = 542;
    }
    
    
    public EntitySpawnFog initFog() {
        frame = 0;
        shrinking = false;
        return this;
    }
    
    
    @Override
    public void update(double delta) {
        if(shrinking) {
            frame--;
            if(frame < 1) {
                Game.game.kill(this);
            }
        } else {
            frame++;
            if(frame > frames) {
                shrinking = true;
            }
        }
    }
    
    
    @Override
    public boolean isCollideable() {
        return false;
    }
}