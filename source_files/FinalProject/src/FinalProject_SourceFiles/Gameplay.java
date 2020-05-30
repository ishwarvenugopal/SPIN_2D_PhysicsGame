package FinalProject_SourceFiles;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

public class Gameplay {

    /* Adapted originally from 'BasicPhysicsEngineUsingJBox2D' created by Michael Fairbank on 2016-02-05
     * Restructured into current form by Ishwar Venugopal on 2020-04-04
     */

    public static final int SCREEN_HEIGHT = 840;
    public static final int SCREEN_WIDTH = 840;
    public static final Dimension FRAME_SIZE = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
    public static final float WORLD_WIDTH=30;//metres
    public static final float WORLD_HEIGHT=SCREEN_HEIGHT*(WORLD_WIDTH/SCREEN_WIDTH);// meters - keeps world dimensions in same aspect ratio as screen dimensions, so that circles get transformed into circles as opposed to ovals
    public static final float GRAVITY=9.8f;

    public static World world; // WORLD coordinate of Box2D for all barriers and other objects

    // sleep time between two drawn frames in milliseconds
    public static final int DELAY = 20;
    public static final int NUM_EULER_UPDATES_PER_SCREEN_REFRESH=10;
    // estimate for time between two frames in seconds
    public static final float DELTA_T = DELAY / 1000.0f;

    public List<BasicParticle> particles;
    public List<BasicRectangle> rectangles;
    public List<AnchoredBarrier> barriers;

    public static enum LayoutMode {BLANK,SPIN};

    public Gameplay(float vx, float vy) {

        world = new World(new Vec2(0, -GRAVITY));// create Box2D container for everything
        world.setContinuousPhysics(true);

        particles = new ArrayList<BasicParticle>();
        rectangles=new ArrayList<BasicRectangle>() ;
        barriers = new ArrayList<AnchoredBarrier>();
        LayoutMode layout=LayoutMode.SPIN;

        float rollingFriction=.02f;
        float r=0.5f; //radius

        particles.add(new BasicParticle(WORLD_WIDTH/2+WORLD_WIDTH/4 - WORLD_WIDTH/16 + 0.5f,WORLD_HEIGHT/8 + 1.75f + r + 0.5f,0,0, r, Color.RED,0.5f,rollingFriction));

        //Lower left flipper

        rectangles.add(new BasicRectangle(WORLD_WIDTH/4 - WORLD_WIDTH/16 + 0.5f ,WORLD_HEIGHT/8 + 0.25f,0,0,0.5f,0.5f,Color.BLUE,10,rollingFriction,false,0.9f));
        rectangles.add(new ControllableRectangle(WORLD_WIDTH/4 - WORLD_WIDTH/16 + 0.5f ,WORLD_HEIGHT/8 + 0.25f,0,0,10,0.5f,Color.BLUE,5,rollingFriction,true,0.9f));

        BasicRectangle p1 = rectangles.get(0);
        BasicRectangle p2 = rectangles.get(1);
        RevoluteJointDef jointDef=new RevoluteJointDef();
        jointDef.bodyA = p1.body;
        jointDef.bodyB = p2.body;
        jointDef.collideConnected = false; // this means we don't want these two connected bodies to have collision detection.
        jointDef.localAnchorA=new Vec2(0,0); // the coordinates of the pivot in bodyB
        jointDef.localAnchorB=new Vec2(0,0); // the coordinates of the pivot in bodyA
        world.createJoint(jointDef);

        //Lower right flipper

        rectangles.add(new BasicRectangle(WORLD_WIDTH/2+WORLD_WIDTH/4 - WORLD_WIDTH/16 + 0.5f,WORLD_HEIGHT/8 + 1.75f,0,0,0.5f,0.5f,Color.BLUE,10,rollingFriction,false,0.9f));
        rectangles.add(new ControllableRectangle(WORLD_WIDTH/2+WORLD_WIDTH/4 - WORLD_WIDTH/16 + 0.5f,WORLD_HEIGHT/8 + 1.75f,0,0,4.5f,0.5f,Color.BLUE,60,rollingFriction,true,2f));

        BasicRectangle p3 = rectangles.get(2);
        BasicRectangle p4 = rectangles.get(3);
        RevoluteJointDef jointDef2=new RevoluteJointDef();
        jointDef2.bodyA = p3.body;
        jointDef2.bodyB = p4.body;
        jointDef2.collideConnected = false; // this means we don't want these two connected bodies to have collision detection.
        jointDef2.localAnchorA=new Vec2(0,0); // the coordinates of the pivot in bodyB
        jointDef2.localAnchorB=new Vec2(0,0); // the coordinates of the pivot in bodyA
        world.createJoint(jointDef2);

        //Top right flipper

        rectangles.add(new BasicRectangle(WORLD_WIDTH/2 + WORLD_WIDTH/4,WORLD_HEIGHT/2 + WORLD_HEIGHT/8,0,0,0.5f,0.5f,Color.BLUE,10,rollingFriction,false,0.9f));
        rectangles.add(new ControllableRectangle(WORLD_WIDTH/2 + WORLD_WIDTH/4,WORLD_HEIGHT/2 + WORLD_HEIGHT/8,0,0,10,0.5f,Color.BLUE,5,rollingFriction,true,0.9f));

        BasicRectangle p5 = rectangles.get(4);
        BasicRectangle p6 = rectangles.get(5);
        RevoluteJointDef jointDef3=new RevoluteJointDef();
        jointDef3.bodyA = p5.body;
        jointDef3.bodyB = p6.body;
        jointDef3.collideConnected = false; // this means we don't want these two connected bodies to have collision detection.
        jointDef3.localAnchorA=new Vec2(0,0); // the coordinates of the pivot in bodyB
        jointDef3.localAnchorB=new Vec2(0,0); // the coordinates of the pivot in bodyA
        world.createJoint(jointDef3);

        barriers = new ArrayList<AnchoredBarrier>();

        switch (layout) {

            case BLANK:{

                break;
            }
            case SPIN:{

                //Solid barrier at the bottom

                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 - WORLD_WIDTH/8, WORLD_HEIGHT/8, WORLD_WIDTH/2 - WORLD_WIDTH/8 , WORLD_HEIGHT/8 + 0.5f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 - WORLD_WIDTH/8,WORLD_HEIGHT/8 + 0.5f,WORLD_WIDTH/2 + WORLD_WIDTH/8, WORLD_HEIGHT/8 + 0.5f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + WORLD_WIDTH/8, WORLD_HEIGHT/8 + 0.5f, WORLD_WIDTH/2 + WORLD_WIDTH/8, WORLD_HEIGHT/8, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + WORLD_WIDTH/8, WORLD_HEIGHT/8, WORLD_WIDTH/2 - WORLD_WIDTH/8, WORLD_HEIGHT/8, Color.BLACK));

                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 - WORLD_WIDTH/8 + 0.05f, WORLD_HEIGHT/8 +0.05f, WORLD_WIDTH/2 - WORLD_WIDTH/8+0.05f , WORLD_HEIGHT/8 + 0.5f-0.05f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 - WORLD_WIDTH/8 + 0.05f,WORLD_HEIGHT/8 + 0.5f-0.05f,WORLD_WIDTH/2 + WORLD_WIDTH/8-0.05f, WORLD_HEIGHT/8 + 0.5f-0.05f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + WORLD_WIDTH/8 - 0.05f, WORLD_HEIGHT/8 + 0.5f-0.05f, WORLD_WIDTH/2 + WORLD_WIDTH/8-0.05f, WORLD_HEIGHT/8+0.05f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + WORLD_WIDTH/8 - 0.05f, WORLD_HEIGHT/8+0.05f, WORLD_WIDTH/2 - WORLD_WIDTH/8+0.05f, WORLD_HEIGHT/8+0.05f, Color.BLACK));

                // Barriers at the upper left corner

                barriers.add(new AnchoredBarrier_StraightLine(0.5f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8, 0.5f , WORLD_WIDTH*0.97f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(0.5f,WORLD_HEIGHT*0.97f,1f, WORLD_HEIGHT*0.97f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(1f, WORLD_HEIGHT*0.97f, 1f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(1f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8, 0.5f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8, Color.BLACK));

                barriers.add(new AnchoredBarrier_StraightLine(0.5f+0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8+0.05f, 0.5f+0.05f, WORLD_WIDTH*0.97f-0.05f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(0.5f+0.05f,WORLD_HEIGHT*0.97f-0.05f,1f-0.05f, WORLD_HEIGHT*0.97f-0.05f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(1f-0.05f, WORLD_HEIGHT*0.97f-0.05f-0.5f, 1f-0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8+0.05f+0.5f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(1f-0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8+0.05f, 0.5f+0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8+0.05f, Color.BLACK));

                barriers.add(new AnchoredBarrier_StraightLine(1f, WORLD_HEIGHT*0.97f - 0.5f, 1f , WORLD_HEIGHT*0.97f, Color.WHITE));
                barriers.add(new AnchoredBarrier_StraightLine(1f,WORLD_HEIGHT*0.97f,WORLD_WIDTH/2 + WORLD_WIDTH/8, WORLD_HEIGHT*0.97f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + WORLD_WIDTH/8, WORLD_HEIGHT*0.97f, WORLD_WIDTH/2 + WORLD_WIDTH/8, WORLD_HEIGHT*0.97f -0.5f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + WORLD_WIDTH/8, WORLD_HEIGHT*0.97f -0.5f, 1f, WORLD_HEIGHT*0.97f -0.5f, Color.BLACK));

                barriers.add(new AnchoredBarrier_StraightLine(1f+0.05f, WORLD_HEIGHT*0.97f - 0.5f+0.05f, 1f +0.05f, WORLD_HEIGHT*0.97f-0.05f, Color.WHITE));
                barriers.add(new AnchoredBarrier_StraightLine(1f+0.05f,WORLD_HEIGHT*0.97f-0.05f,WORLD_WIDTH/2 + WORLD_WIDTH/8-0.05f, WORLD_HEIGHT*0.97f-0.05f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + WORLD_WIDTH/8-0.05f, WORLD_HEIGHT*0.97f-0.05f, WORLD_WIDTH/2 + WORLD_WIDTH/8-0.05f, WORLD_HEIGHT*0.97f -0.5f+0.05f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + WORLD_WIDTH/8-0.05f, WORLD_HEIGHT*0.97f -0.5f+0.05f, 1f+0.05f, WORLD_HEIGHT*0.97f -0.5f+0.05f, Color.BLACK));

                // Platform for the flag

                barriers.add(new AnchoredBarrier_StraightLine(1f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8, 1f , WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f, Color.WHITE));
                barriers.add(new AnchoredBarrier_StraightLine(1f,WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f,WORLD_WIDTH/4, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/4, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f, WORLD_WIDTH/4, WORLD_HEIGHT/2 + WORLD_HEIGHT/8, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/4, WORLD_HEIGHT/2 + WORLD_HEIGHT/8, 1f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8, Color.BLACK));

                barriers.add(new AnchoredBarrier_StraightLine(1f+0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8+0.05f, 1f +0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f-0.05f, Color.WHITE));
                barriers.add(new AnchoredBarrier_StraightLine(1f+0.05f,WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f-0.05f,WORLD_WIDTH/4-0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f-0.05f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/4-0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f-0.05f, WORLD_WIDTH/4-0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8+0.05f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/4-0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8+0.05f, 1f+0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8+0.05f, Color.BLACK));

                // Flag pole

                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f, WORLD_WIDTH/8 , WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f, Color.GREEN));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8,WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f,WORLD_WIDTH/8+0.2f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f, Color.GREEN));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8 + 0.2f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f, WORLD_WIDTH/8 + 0.2f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f, Color.GREEN));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8 + 0.2f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f, WORLD_WIDTH/8, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f, Color.GREEN));

                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8 + 0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f + 0.05f, WORLD_WIDTH/8 + 0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f - 0.05f, Color.GREEN));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8 + 0.05f,WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f - 0.05f,WORLD_WIDTH/8+0.2f - 0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f - 0.05f, Color.GREEN));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8 + 0.2f - 0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f - 0.05f, WORLD_WIDTH/8 + 0.2f - 0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f + 0.05f, Color.GREEN));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8 + 0.2f - 0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f + 0.05f, WORLD_WIDTH/8 + 0.05f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f + 0.05f, Color.GREEN));

                // Flag (triangle)

                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8+0.2f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 3f, WORLD_WIDTH/8 +0.2f , WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f, Color.GREEN));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8+0.2f,WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f,WORLD_WIDTH/8+1.2f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 3.5f, Color.GREEN));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8 + 1.2f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 3.5f, WORLD_WIDTH/8 + 0.2f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 3f, Color.GREEN));

                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8+0.2f + 0.1f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 3f + 0.2f, WORLD_WIDTH/8 +0.2f + 0.1f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f - 0.2f, Color.GREEN));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8+0.2f + 0.1f,WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f - 0.2f,WORLD_WIDTH/8+1.2f - 0.1f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 3.5f, Color.GREEN));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/8 + 1.2f - 0.1f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 3.5f, WORLD_WIDTH/8 + 0.2f + 0.1f, WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 3f + 0.2f, Color.GREEN));

                // Thorns at top-left corner (Inverted triangles)

                barriers.add(new AnchoredBarrier_StraightLine(1.5f, WORLD_WIDTH*0.97f - 2.5f, 1f , WORLD_WIDTH*0.97f - 0.5f, Color.RED));
                barriers.add(new AnchoredBarrier_StraightLine(1f,WORLD_WIDTH*0.97f - 0.5f,2f, WORLD_WIDTH*0.97f - 0.5f, Color.RED));
                barriers.add(new AnchoredBarrier_StraightLine(2f, WORLD_WIDTH*0.97f - 0.5f, 1.5f, WORLD_WIDTH*0.97f - 2.5f, Color.RED));

                barriers.add(new AnchoredBarrier_StraightLine(2.5f, WORLD_WIDTH*0.97f - 2.5f, 2f , WORLD_WIDTH*0.97f - 0.5f, Color.RED));
                barriers.add(new AnchoredBarrier_StraightLine(2f,WORLD_WIDTH*0.97f - 0.5f,3f, WORLD_WIDTH*0.97f - 0.5f, Color.RED));
                barriers.add(new AnchoredBarrier_StraightLine(3f, WORLD_WIDTH*0.97f - 0.5f, 2.5f, WORLD_WIDTH*0.97f - 2.5f, Color.RED));

                barriers.add(new AnchoredBarrier_StraightLine(3.5f, WORLD_WIDTH*0.97f - 2.5f, 3f , WORLD_WIDTH*0.97f - 0.5f, Color.RED));
                barriers.add(new AnchoredBarrier_StraightLine(3f,WORLD_WIDTH*0.97f - 0.5f,4f, WORLD_WIDTH*0.97f - 0.5f, Color.RED));
                barriers.add(new AnchoredBarrier_StraightLine(4f, WORLD_WIDTH*0.97f - 0.5f, 3.5f, WORLD_WIDTH*0.97f - 2.5f, Color.RED));

                // Thorns in the middle (Upward triangles with platform)

                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 - 1.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6,WORLD_WIDTH/2 - 1.5f , WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 - 1.5f,WORLD_HEIGHT/2 - WORLD_HEIGHT/6+ 0.5f,WORLD_WIDTH/2 + 1.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + 1.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6+ 0.5f, WORLD_WIDTH/2 + 1.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + 1.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6, WORLD_WIDTH/2 - 1.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6, Color.BLACK));

                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 - 1.5f+0.05f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6+0.05f,WORLD_WIDTH/2 - 1.5f+0.05f , WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f-0.05f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 - 1.5f+0.05f,WORLD_HEIGHT/2 - WORLD_HEIGHT/6+ 0.5f-0.05f,WORLD_WIDTH/2 + 1.5f-0.05f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f-0.05f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + 1.5f-0.05f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6+ 0.5f-0.05f, WORLD_WIDTH/2 + 1.5f-0.05f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6+0.05f, Color.BLACK));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + 1.5f-0.05f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6+0.05f, WORLD_WIDTH/2 - 1.5f+0.05f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6+0.05f, Color.BLACK));

                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 - 1.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, WORLD_WIDTH/2 - 1f , WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 1.5f, Color.RED));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 - 1f,WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 1.5f,WORLD_WIDTH/2 - 0.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, Color.RED));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 - 0.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, WORLD_WIDTH/2 - 1.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, Color.RED));

                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 - 0.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, WORLD_WIDTH/2 , WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 1.5f, Color.RED));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2,WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 1.5f,WORLD_WIDTH/2 + 0.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, Color.RED));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + 0.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, WORLD_WIDTH/2 - 0.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, Color.RED));

                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + 0.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, WORLD_WIDTH/2 + 1f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 1.5f, Color.RED));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + 1f,WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 1.5f,WORLD_WIDTH/2 + 1.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, Color.RED));
                barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH/2 + 1.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, WORLD_WIDTH/2 + 0.5f, WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f, Color.RED));

                break;
            }
        }
    }

    public static int convertWorldXtoScreenX(float worldX) {
        return (int) (worldX/WORLD_WIDTH*SCREEN_WIDTH);
    }

    public static int convertWorldYtoScreenY(float worldY) {
        // minus sign in here is because screen coordinates are upside down.
        return (int) (SCREEN_HEIGHT-(worldY/WORLD_HEIGHT*SCREEN_HEIGHT));
    }

    public static float convertWorldLengthToScreenLength(float worldLength) {
        return (worldLength/WORLD_WIDTH*SCREEN_WIDTH);
    }

    public void update() {

        int VELOCITY_ITERATIONS=NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
        int POSITION_ITERATIONS=NUM_EULER_UPDATES_PER_SCREEN_REFRESH;

        for (BasicParticle p:particles) {
            // give the objects an opportunity to add any bespoke forces, e.g. rolling friction
            p.notificationOfNewTimestep();
            if(p.checkGameover()){
                p.gameover=false;
                p.makeBallStatic(WORLD_WIDTH/2,WORLD_WIDTH/8 + 1.5f);
                PopUpScreen.infoBox("Oops!! Click on 'Reset' to try again!", "GAME OVER");
                break;
            }
            if(p.checkVictory()){
                p.victory=false;
                p.makeBallStatic(WORLD_WIDTH/2,WORLD_WIDTH/8 + 1.5f);
                PopUpScreen.infoBox("Congratulations!! Click on 'Reset' to play again!", "YOU WON!!");
            }
        }
        for (BasicRectangle r:rectangles){
            r.notificationOfNewTimestep();
        }
        world.step(DELTA_T, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
    }
}


