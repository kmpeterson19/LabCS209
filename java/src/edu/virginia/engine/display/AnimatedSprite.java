package edu.virginia.engine.display;

import edu.virginia.engine.util.GameClock;

import java.awt.*;
import java.util.ArrayList;


public class AnimatedSprite extends Sprite {

    private ArrayList<animation> animations;

    private Boolean playing;

    private ArrayList<Image> frames;

    private Integer currentFrame;

    private Integer startFrame;

    private Integer endFrame;

    static final int DEFAULT_ANIMATION_SPEED = 1;

    private Integer animationSpeed;

    private GameClock gameClock;

    public void setGameClock (GameClock g) {
        this.gameClock = g;
    }

    public GameClock getGameClock() {
        return gameClock;
    }

    public AnimatedSprite(String id, String fileName, Point position) {
        super(id, fileName);
        this.setPosition(position);
        GameClock start_clock = new GameClock()
        this.setGameClock(start_clock);
    }

}
