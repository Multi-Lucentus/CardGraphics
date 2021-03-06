package edu.ou.cs.cg.project.scene;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import edu.ou.cs.cg.utilities.*;


/**
 * The Room Class
 * Represents a Cubic room with walls and floor
 * Also handles the static elements displayed in the room represented by inner classes
 *
 * @author Parker Brandt
 */
public class Room extends Node {

    //****************************************
    // Private Variables
    //****************************************
    private final Lamp lamp;
    private final Window window;
    private final Door door;
    private Shelf[] shelves;


    //****************************************
    // Constructors
    //****************************************

    /**
     * Constructor add textures with
     * @param textures
     */
    public Room(Texture[] textures) {
        super(textures);

        // Create the lamp for the room that emits a yellow-whitish color
        //new float[]{1.0f, 1.6f, 204.0f/255.0f, 0.0f}
        lamp = new Lamp(textures, new float[]{160.0f/255.0f, 160.0f/255.0f, 160.0f/255.0f, 1.0f});
        super.add(lamp);

        // Create the window
        window = new Window(textures);
        super.add(window);

        // Create a door for the left side of the room
        door = new Door(textures);
        super.add(door);

        // Create three shelves to display the cards on
        shelves = new Shelf[3];
        shelves[0] = new Shelf(textures);
        shelves[0].pushTransform(new Transform.Translate(0.5f, 1.5f, -0.7f));
        shelves[0].pushTransform(new Transform.Scale(0.5f, 0.07f, 0.5f));
        super.add(shelves[0]);

        shelves[1] = new Shelf(textures);
        shelves[1].pushTransform(new Transform.Translate(0.5f, 5.0f, -0.7f));
        shelves[1].pushTransform(new Transform.Scale(0.5f, 0.07f, 0.5f));
        super.add(shelves[1]);

        shelves[2] = new Shelf(textures);
        shelves[2].pushTransform(new Transform.Translate(0.5f, 8.5f, -0.7f));
        shelves[2].pushTransform(new Transform.Scale(0.5f, 0.07f, 0.5f));
        super.add(shelves[2]);

        // Adjust the layout of the room
        pushTransform(new Transform.Scale(1.5f, 1.0f, 2.0f));
        pushTransform(new Transform.Translate(-0.7f, 0.5f, 1.0f));

    }


    //****************************************
    // Node Override Methods
    //****************************************

    @Override
    protected void change(GL2 gl) { }

    @Override
    protected void depict(GL2 gl) {

        // Create cube to represent the room
        // Fill each face with a texture
        Cube.fillFace(gl, 1, getTexture(0));
        Cube.fillFace(gl, 2, getTexture(0));
        Cube.fillFace(gl, 3, getTexture(0));
        Cube.fillFace(gl, 4, getTexture(0));
        Cube.fillFace(gl, 5, getTexture(1));
    }


    //****************************************
    // Inner Classes
    //****************************************

    /**
     * An inner class used to represent a window in the room
     * Should use a transformed version of a cube
     */
    public static class Window extends Node {

        //****************************************
        // Constructors
        //****************************************
        public Window(Texture[] textures) {
            super(textures);

            pushTransform(new Transform.Scale(0.01f, 0.5f, 0.4f));
            pushTransform(new Transform.Translate(0.95f, 0.25f, 0.1f));
        }


        //****************************************
        // Node Override Methods
        //****************************************
        @Override
        protected void change(GL2 gl) { }

        @Override
        protected void depict(GL2 gl) {

            // Use the city texture with a curtain over it
            Cube.fillFace(gl, 2, getTexture(9));

            Cube.fillFace(gl, 3, getTexture(10));
        }
    }


    /**
     * An inner class used to represent a door in the room
     * A modified cube
     */
    public static class Door extends Node {

        //****************************************
        // Constructors
        //****************************************
        public Door(Texture[] textures) {
            super(textures);

            // Scale and move the door
            pushTransform(new Transform.Scale(0.05f, 0.7f, 0.2f));
            pushTransform(new Transform.Translate(-0.02f, -0.01f, 0.1f));
        }


        //****************************************
        // Node Override Methods
        //****************************************
        @Override
        protected void depict(GL2 gl) {

            // Draw the door as a cube using a wooden texture
            Cube.fillFace(gl, 0, getTexture(1));
            Cube.fillFace(gl, 2, getTexture(11));
        }
    }


    /**
     * An inner class used to represent a lamp in the room
     * Will act as the light source for the room
     */
    public static class Lamp extends Node {

        //****************************************
        // Private Variables
        //****************************************
        private final float[] emit;


        //****************************************
        // Constructors
        //****************************************
        public Lamp(Texture[] textures, float[] emit) {
            super(textures);

            // Initialize variables
            this.emit = emit;

            // Move the cylinder to the back left corner
            pushTransform(new Transform.Translate(0.0f, 0.0f, -1.0f));
            pushTransform(new Transform.Rotate(0.0f, 0.0f, 1.0f, 90.0f));
        }


        //****************************************
        // Node Override Methods
        //****************************************
        @Override
        protected void change(GL2 gl) { }

        @Override
        protected void depict(GL2 gl) {
            Lighting.setMaterial(gl, null, null, null, null, emit);
        }
    }

    /**
     * Inner class to represent the shelves in the program
     */
    public static class Shelf extends Node {

        //****************************************
        // Constructors
        //****************************************
        public Shelf(Texture[] textures) {
            super(textures);
        }


        //****************************************
        // Node Override Methods
        //****************************************
        @Override
        protected void change(GL2 gl) { }

        @Override
        protected void depict(GL2 gl) {
            // Use the same wood texture as the floor to texture the shelves
            for(int i = 0; i < 6; i++)
                Cube.fillFace(gl, i, getTexture(1));
        }
    }
}
