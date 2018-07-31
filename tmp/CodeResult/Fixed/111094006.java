/*
 * Fixed Position Servo
 * Reviewed AG 11/2/2009 -- OK
 */
package com.grt192.actuator;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import edu.wpi.first.wpilibj.Servo;

/**
 *
 * @author Student
 */
public class GRTServo extends Actuator {

    private Servo servo;

    public GRTServo(int channel) {
        servo = new Servo(channel);
    }

    public void executeCommand(Command c) {
        double value = c.getValue();
        if (value > 0.0 && value < 360.0) {
            servo.setAngle(c.getValue());
        }
    }

    public void halt() {
        servo.setAngle(90);
    }

    public String toString() {
        return "Servo";
    }
}
