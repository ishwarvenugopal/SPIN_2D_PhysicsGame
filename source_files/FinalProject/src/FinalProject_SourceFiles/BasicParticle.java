package FinalProject_SourceFiles;

import java.awt.*;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import static FinalProject_SourceFiles.Gameplay.*;

public class BasicParticle  {

	/* Author: Michael Fairbank
	 * Creation Date: 2016-02-05 (JBox2d version)
	 * Significant changes applied: 2020-04-04 by Ishwar Venugopal
	 */

	public final int SCREEN_RADIUS;
	private final float rollingFriction,mass;
	public final Color col;
	protected final Body body;


	public BasicParticle(float sx, float sy, float vx, float vy, float radius, Color col, float mass, float rollingFriction) {

		World w=Gameplay.world; // a Box2D object

		BodyDef bodyDef = new BodyDef();  // a Box2D object
		bodyDef.type = BodyType.DYNAMIC; // this says the physics engine is to move it automatically
		bodyDef.position.set(sx, sy);
		bodyDef.linearVelocity.set(vx, vy);
		this.body = w.createBody(bodyDef);

		CircleShape circleShape = new CircleShape();// This class is from Box2D
		circleShape.m_radius = radius;

		FixtureDef fixtureDef = new FixtureDef();// This class is from Box2D
		fixtureDef.shape = circleShape;
		fixtureDef.density = (float) (mass/(Math.PI*radius*radius));
		fixtureDef.friction = 100f;// this is surface friction;
		fixtureDef.restitution = 0.2f;
		body.createFixture(fixtureDef);

		this.rollingFriction=rollingFriction;
		this.mass=mass;
		this.SCREEN_RADIUS=(int)Math.max(Gameplay.convertWorldLengthToScreenLength(radius),1);
		this.col=col;
	}

	public void draw(Graphics2D g) {
		int x = Gameplay.convertWorldXtoScreenX(body.getPosition().x);
		int y = Gameplay.convertWorldYtoScreenY(body.getPosition().y);
		g.setColor(col);
		g.fillOval(x - SCREEN_RADIUS, y - SCREEN_RADIUS, 2 * SCREEN_RADIUS, 2 * SCREEN_RADIUS);
	}


	public void notificationOfNewTimestep() {
		if (rollingFriction>0) {
			Vec2 rollingFrictionForce=new Vec2(body.getLinearVelocity());
			rollingFrictionForce=rollingFrictionForce.mul(-rollingFriction*mass);
			body.applyForceToCenter(rollingFrictionForce);
			
		}
		
	}

	//Additional functions added to the original class //

	public boolean gameover=false;

	public boolean checkGameover() {

		//Checking world boundary conditions

		if((body.getPosition().x < 0)||(body.getPosition().x > WORLD_WIDTH))
			gameover=true;
		if((body.getPosition().y < 0)||(body.getPosition().y > WORLD_HEIGHT))
			gameover=true;

		//Checking thorn in the middle

		if((body.getPosition().x + 0.5f >= WORLD_WIDTH/2 - 1.5f)&&(body.getPosition().x + 0.5f <= WORLD_WIDTH/2 + 1.5f)&&(body.getPosition().y >= WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f)&&(body.getPosition().y <= WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 1.5f))
				gameover=true;

		if((body.getPosition().x - 0.5f <= WORLD_WIDTH/2 - 1.5f)&&(body.getPosition().x-0.5f >= WORLD_WIDTH/2 - 1.5f)&&(body.getPosition().y >= WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f)&&(body.getPosition().y <= WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 1.5f))
			gameover=true;

		if((body.getPosition().y - 0.5f <= WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 1.5f)&&(body.getPosition().y - 0.5f >= WORLD_HEIGHT/2 - WORLD_HEIGHT/6 + 0.5f)&&(body.getPosition().x >= WORLD_WIDTH/2 - 1.5f)&&(body.getPosition().x <= WORLD_WIDTH/2 + 1.5f))
			gameover=true;

		//Checking thorn at the top

		if((body.getPosition().y + 0.5f >= WORLD_WIDTH*0.97f - 2.5f)&&(body.getPosition().y + 0.5f <= WORLD_WIDTH*0.97f - 0.5f)&&(body.getPosition().x >= 1f)&&(body.getPosition().x <= 4f))
			gameover=true;

		if((body.getPosition().x - 0.5f <= 4f)&&(body.getPosition().x - 0.5f >= 1f)&&(body.getPosition().y >= WORLD_WIDTH*0.97f - 2.5f)&&(body.getPosition().y <= WORLD_WIDTH*0.97f - 0.5f))
			gameover=true;

		return  gameover;
	}

	public boolean victory=false;

	public boolean checkVictory(){

		if((body.getPosition().x + 0.5f >= WORLD_WIDTH/8 - 2f)&&(body.getPosition().x + 0.5f <= WORLD_WIDTH/8+1.2f)&&(body.getPosition().y >= WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f)&&(body.getPosition().y <= WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f))
			victory=true;

		if((body.getPosition().x - 0.5f <= WORLD_WIDTH/8+1.2f)&&(body.getPosition().x-0.5f >= WORLD_WIDTH/8 - 2f)&&(body.getPosition().y >= WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f)&&(body.getPosition().y <= WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4f))
			victory=true;

		if((body.getPosition().y - 0.5f <= WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 4.1f)&&(body.getPosition().y - 0.5f >= WORLD_HEIGHT/2 + WORLD_HEIGHT/8 + 0.5f)&&(body.getPosition().x >= WORLD_WIDTH/8 - 2f)&&(body.getPosition().x <= WORLD_WIDTH/8+1.2f))
			victory=true;

		return victory;
	}

	public void makeBallStatic(float x, float y) {
		this.body.setTransform(new Vec2(x,y),0);
		this.body.setType(BodyType.STATIC);
	}
	
}
