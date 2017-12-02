/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.shipswarm.entity;

import jaredbgreat.arcade.entity.Entity;
import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.ui.sound.Sound;
import jaredbgreat.arcade.util.math.Vec2d;
import jaredbgreat.shipswarm.game.Game;

/**
 *
 * @author jared
 */
public final class EntityPlayer extends Entity {
    public static EntityPlayer player;
    private final int deathimg;
    private int fireCooldown;
    private int graceperiod;
    private int lives;
    private int respawnTimer;
    private int score;
    private final int firesound;
    private final int spawnsound;
    private boolean dead;
    
    
    private EntityPlayer() {
        graphic = Graphic.registry.getID("player");
        deathimg = Graphic.registry.getID("player-die");
        firesound = Sound.registry.getID("fire");
        spawnsound = Sound.registry.getID("respawn");
        reset();
    }
    
    
    public void reset() {
        locX = 224;
        locY = 542;
        frame = 0;
        graceperiod = 0;
        controller = InputController.in;
        fireCooldown = 0;
        graceperiod = 0;
        lives = 3;
        respawnTimer = 0;
        score = 0;
        dead = false;
    }
    
    
    public static void initPlayer() {
        player = new EntityPlayer();
    }
    
    
    @Override
    public void update(double delta) {
        super.update(delta);
        if(graceperiod > 0 ) {
            graceperiod--;
        }
        if(fireCooldown > 0) {
            fireCooldown--;
        } else if(controller.fire()) {
            fireCooldown = 15;
            EntityProjectile proj; 
            proj = EntityProjectile.pool.getObject()
                    .initProjectile(locX + 15d, locY, 
                        new Vec2d(0.0d, -9.0d), true);
            Game.game.spawn(proj);
            Sound.registry.get(firesound).play();
        }
    }
    
    
    @Override
    public void collide(Entity other) {
        other.collide(this);
    }
    
    
    public Vec2d getVelocity() {
        return controller.getDirection();
    }
    
    
    public void addLife() {
        lives++;
    }
    
    
    public void decrementLives() {
        lives--;
        dead = true;
        if(lives > 0) {
            respawnTimer = (Game.BASE_FPS * 3) / 2; // One seconde
        } else {
            Game.game.endGame();
        }
    }
    
    
    public void respawn() { 
        if(respawnTimer > 0) {
            if(--respawnTimer < 1) {  
                Sound.registry.get(spawnsound).play();
                graceperiod = Game.BASE_FPS;
                locX = 224;
                locY = 542;
                dead = false;                
                Game.game.spawn(this);               
                Game.game.spawn(EntitySpawnFog.spawnfog.initFog());
            } 
        }
    }
    
    public void incrementScore(int amount) {
        // Don't score after the game is over
        if(lives > 0) {
            score += amount;
        }
    }
    
    
    public boolean isDead() {
        return dead;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }
    
    
    @Override
    public void die() {  
        if(graceperiod > 0) {
            return;
        }
        Game.game.spawn(EntityExplosion.pool.getObject()
                .initExplosion(locX, locY));
        Game.game.spawn(EntityShipDying.pool.getObject()
                .initDeath(locX, locY,  deathimg));
        decrementLives();
        Game.game.kill(this);
    }
    
    
    @Override
    protected void cleanup() {/*Do nothing for player entity*/}
    
}
