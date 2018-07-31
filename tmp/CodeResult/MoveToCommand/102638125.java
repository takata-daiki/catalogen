package nrgrobot.cmd;

import nrgrobot.*;
import com.sun.squawk.util.MathUtils;

/**
 * This class commands the robot to move to the given position using PositionTracker
 * @author Austin, Hokwen
 */
public class MoveToCommand extends CommandBase
{
    private double desiredX, desiredY;
    private double motorSpeed;
    private boolean doneTurning;
    private TurnToHeadingCommand turnCmd;
    private DistanceMove moveCmd;

    /**
     *
     * @param desiredX What X value we want to go to.
     * @param desiredY What Y value we want to go to.
     */
    public MoveToCommand(double desiredX, double desiredY)
    {
        this.desiredX = desiredX;
        this.desiredY = desiredY;
        this.motorSpeed = 1.0;
    }

    /**
     *
     * @param desiredX What X value we want to go to.
     * @param desiredY What Y value we want to go to.
     * @param motorSpeed What speed to run the motors whilst moving.
     */
    public MoveToCommand(double desiredX, double desiredY, double motorSpeed)
    {
        this.desiredX = desiredX;
        this.desiredY = desiredY;
        this.motorSpeed = motorSpeed;
    }

    /**
     * Constructs and initializes a turn command and constructs move command.
     */
    public void init()
    {
        double dX = desiredX - PositionTracker.getX();
        double dY = desiredY - PositionTracker.getY();
        double distanceToGo = Math.sqrt(dX * dX + dY * dY);
        //double currentHeading = Sensors.gyro.getAngle();
        double desiredHeading = 90.0 - Math.toDegrees(MathUtils.atan(dY / dX));
        if (dX < 0)
            desiredHeading += 180.0;
        //double angleToTurnClockwise = MathHelper.convertToSmallestRelativeAngle(desiredHeading - currentHeading);
        //turnCmd = new TurnCommand(angleToTurnClockwise);
        turnCmd = new TurnToHeadingCommand(desiredHeading);
        moveCmd = new DistanceMove(distanceToGo, motorSpeed);
        turnCmd.init();
        Debug.println(Debug.AUTOCMD, "Initializing turn command. ");
        doneTurning = false;
    }

    public boolean run()
    {
        if (!doneTurning)
        {
            // doneTurning is true if turnCmd is finished (true).
            doneTurning = turnCmd.run();
            if (!doneTurning)
                return false; // don't move forward until we're done turning
            Debug.println(Debug.AUTOCMD, "Ending turn command. ");
            // Initializes the move command after the turn commaand ends.
            moveCmd.init();
            Debug.println(Debug.AUTOCMD, "Initializing move command. ");
        }
        return moveCmd.run();
        // When moveCmd is done, MoveToCommand is done. 
    }
}
