package edu.virginia.engine.display;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObject {

	/* All DisplayObject have a unique id */
	private String id;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;

	private Point position;

	private Integer rotation;

	private Point pivotPoint;

	private Boolean visible;

	private Float alpha;

	private Float oldAlpha;

	private Double scaleX;

    private Double scaleY;

	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
	    this.setId(id);
	    Point start_position = new Point(0,0);
	    this.setPosition(start_position);
	    Point pivot = new Point(0,0);
	    this.setPivotPoint(pivot);
	    this.setRotation(0);
	    this.setVisible(true);
	    this.setAlpha(1.0f);
	    this.setOldAlpha(0.0f);
	    this.setScaleX(1.0);
        this.setScaleY(1.0);
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
        Point start_position = new Point(0,0);
        this.setPosition(start_position);
        Point pivot = new Point(0,0);
        this.setPivotPoint(pivot);
        this.setRotation(0);
        this.setVisible(true);
        this.setAlpha(1.0f);
        this.setOldAlpha(0.0f);
        this.setScaleX(1.0);
        this.setScaleY(1.0);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Point getPosition() {
		return this.position;
	}

	public void setPosition(Point p) {
		this.position = p;
	}

	public Integer getRotation() {
		return this.rotation;
	}

	public void setRotation (Integer r) {
		this.rotation = r;
	}

	public Point getPivotPoint() {
		return pivotPoint;
	}

	public void setPivotPoint(Point pp) {
		this.pivotPoint = pp;
	}

	public boolean getVisible() {
	    return this.visible;
    }

    public void setVisible(Boolean b) {
	    this.visible = b;
    }

    public float getAlpha() {
	    return this.alpha;
    }

    public void setAlpha(Float a) {
	    this.alpha = a;
    }

    public float getOldAlpha() {
        return this.oldAlpha;
    }

    public void setOldAlpha(Float a) {
        this.oldAlpha = a;
    }

    public double getScaleX() {
	    return this.scaleX;
    }

    public void setScaleX(Double d) {
	    this.scaleX = d;
    }

    public double getScaleY() {
        return this.scaleY;
    }

    public void setScaleY(Double d) {
        this.scaleY = d;
    }

	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
	}


	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	protected void update(ArrayList<Integer> pressedKeys) {
		
	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {
		
		if (displayImage != null) {
			
			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);

			/* Actually draw the image, perform the pivot point translation here */
            if(visible) {
                g2d.drawImage(displayImage, 0, 0,
                        (int) (getUnscaledWidth()),
                        (int) (getUnscaledHeight()), null);
            }
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
		}
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
        g2d.translate(this.getPosition().x, this.getPosition().y);
        g2d.rotate(Math.toRadians(this.getRotation()), this.getPivotPoint().x,
                this.getPivotPoint().y);
        g2d.scale(this.getScaleX(), this.getScaleY());
        float curAlpha;
        this.oldAlpha = curAlpha = ((AlphaComposite)
                g2d.getComposite()).getAlpha();
        g2d.setComposite(AlphaComposite.getInstance(3, curAlpha *
                this.getAlpha()));
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(3,
                this.getOldAlpha()));
        double new_x = 1.0/this.getScaleX();
        double new_y = 1.0/this.getScaleY();
        g2d.scale(new_x, new_y);
        g2d.rotate(Math.toRadians(-this.getRotation()), this.getPivotPoint().x,
                this.getPivotPoint().y);
        g2d.translate(-this.getPosition().x, -this.getPosition().y);
	}

}
