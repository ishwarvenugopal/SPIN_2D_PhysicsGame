package FinalProject_SourceFiles;

import java.awt.*;

public class ControllableRectangle extends BasicRectangle {

    /* Author: Ishwar Venugopal
     * Creation Date: 2020-04-04
     */

    public ControllableRectangle(float sx, float sy, float vx, float vy, float length, float width, Color col, float mass, float rollingFriction,boolean dynnamic,float angdamp) {
        super(sx, sy, vx, vy, length, width, col, mass, rollingFriction,dynnamic,angdamp);
    }

    public void notificationOfNewTimestep() {

        if (BasicKeyListener.isLeftArrowPressed()){
            body.applyTorque(1000);
        }
        if (BasicKeyListener.isRightArrowPressed()) {
            body.applyTorque(-1000);
        }
    }

}
