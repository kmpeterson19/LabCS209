package edu.virginia.engine.display;

public class Animation {

    private String id;

    private Integer startFrame;

    private Integer endFrame;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setStartFrame(Integer i) { this.startFrame = i; }

    public Integer getStartFrame { return startFrame; }

    public void setEndFrame(Integer j) { this.endFrame = j; }

    public Integer getEndFrame { return endFrame; }

    public Animation(String id, Integer startFrame, Integer endFrame) {
        this.setId(id);
        this.setStartFrame(startFrame);
        this.setEndFrame(endFrame);
    }
}
