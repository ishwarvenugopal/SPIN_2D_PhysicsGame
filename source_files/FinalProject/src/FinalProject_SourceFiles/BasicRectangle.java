package FinalProject_SourceFiles;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class BasicRectangle {

    /* Author: Ishwar Venugopal
     * Creation Date: 2020-04-04
     */

    public final float ratioOfScreenScaleToWorldScale;
    public final float rollingFriction,mass;
    public final Color col;
    protected final Body body;
    private final Path2D.Float polygonPath;
    public final float length,width;

    public BasicRectangle(float sx, float sy, float vx, float vy, float length, float width, Color col, float mass, float rollingFriction,boolean dynamic,float angdamp){

        this.length=length;
        this.width=width;

        World w=Gameplay.world; // a Box2D object

        BodyDef bodyDef = new BodyDef();  // a Box2D object

        if (dynamic)
            bodyDef.type = BodyType.DYNAMIC; // this says the physics engine is to move it automatically
        else
            bodyDef.type = BodyType.STATIC;

        bodyDef.position.set(sx, sy);
        bodyDef.linearVelocity.set(vx, vy);
        bodyDef.angularDamping = angdamp;
        this.body = w.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        Vec2[] vertices =new Vec2[4];
        vertices[0]=new Vec2(-length/2,-width/2);
        vertices[1]=new Vec2(+length/2,-width/2);
        vertices[2]=new Vec2(+length/2,+width/2);
        vertices[3]=new Vec2(-length/2,+width/2);

        shape.set(vertices, 4);

        Path2D.Float p=new Path2D.Float();

        p.moveTo(vertices[0].x, vertices[0].y);
        p.lineTo(vertices[1].x, vertices[1].y);
        p.lineTo(vertices[2].x, vertices[2].y);
        p.lineTo(vertices[3].x, vertices[3].y);
        p.closePath();
        this.polygonPath=p;

        FixtureDef fixtureDef = new FixtureDef();// This class is from Box2D
        fixtureDef.shape = shape;
        fixtureDef.density = (float) (mass/((float) 4)/2f*(length*width)*Math.sin(2*Math.PI/4));
        fixtureDef.friction = 0.1f;// this is surface friction;
        fixtureDef.restitution = 0.5f;
        body.createFixture(fixtureDef);

        this.rollingFriction=rollingFriction;
        this.mass=mass;
        this.ratioOfScreenScaleToWorldScale=Gameplay.convertWorldLengthToScreenLength(1);
        this.col=col;
    }

    public void draw(Graphics2D g) {

        g.setColor(col);
        Vec2 position = body.getPosition();
        float angle = body.getAngle();
        AffineTransform af = new AffineTransform();
        af.translate(Gameplay.convertWorldXtoScreenX(position.x), Gameplay.convertWorldYtoScreenY(position.y));
        af.scale(ratioOfScreenScaleToWorldScale, -ratioOfScreenScaleToWorldScale);// there is a minus in here because screenworld is flipped upsidedown compared to physics world
        af.rotate(angle);
        Path2D.Float p = new Path2D.Float (polygonPath,af);
        g.fill(p);
    }

    public void notificationOfNewTimestep() {
        if (rollingFriction>0) {
            Vec2 rollingFrictionForce=new Vec2(body.getLinearVelocity());
            rollingFrictionForce=rollingFrictionForce.mul(-rollingFriction*mass);
            body.applyForceToCenter(rollingFrictionForce);
        }

    }

}
