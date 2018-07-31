package nrgrobot;

import nrgrobot.cmd.*;

/**
 * This class represents an autonmous routine that makes use of an array of commands
 * and does them in order using a state machine until they are completed
 * or the autonomous period ends.
 * @author Kenneth, Paul, Austin, Brian, Dustin
 */
public class Autonomous
{
    private CommandBase cmd;
    private int cmdIndex;
    private boolean initialized;
    private CommandBase[] cmds;

    public static final double X_TARGET = 26.0;                  // x coord of autonomous target goal
    public static final double Y_TARGET_NEAR_FIELD = 198.0;      // y coord of goal from the near field
    public static final double Y_TARGET_MID_FIELD = 34 * 12 + 4; // y coord of goal from the middle field
    public static final double Y_TARGET_FAR_FIELD = 54 * 12;     // y coord of goal from the far field
    public static final double X_CENTER_LINE = 13.5 * 12;        // x coord of center line (13.5 feet from the left field edge)

    // Note that these kick powers are not linear because of the need to bounce the ball precisely
    // between the bumps. If we could shoot from the far field to the goal without bouncing in
    // between, they could be more linear.
    private static final double KICK_POWER1 = 40.0;         // near field, row closest to goal
    private static final double KICK_POWER2 = 50.0;         // near field, middle row
    private static final double KICK_POWER3 = 55.0;         // near field, row furthest from goal
    private static final double KICK_POWER4 = 70.0;         // mid field, row closest to goal
    private static final double KICK_POWER5 = 85.0;         // mid field, middle row
    private static final double KICK_POWER6 = 85.0;         // mid field, row furthest from goal
    private static final double KICK_POWER7 = 80.0;         // far field, row closed to goal
    private static final double KICK_POWER8 = 87.0;         // far field, middle row
    private static final double KICK_POWER9 = 100.0;        // far field, row furthest from goal

    private static final double ROW_SPACING  = 36.0;        // spacing between each dot row
    private static final double COL_SPACING  = 36.0;        // spacing between each dot column
    private static final double X_COL1  = 45.0;
    private static final double X_COL2  = X_COL1 + COL_SPACING;
    private static final double X_COL3  = X_COL2 + COL_SPACING;
    private static final double Y_ROW1  = 52.0;
    private static final double Y_ROW2  = Y_ROW1 + ROW_SPACING;
    private static final double Y_ROW3  = Y_ROW2 + ROW_SPACING;
    private static final double Y_START = AutonomousSettings.ROBOT_LENGTH/2;  // y coord of the robot's center
    private static final double BACKWALL_TO_ROW1 = 56 - Y_START + 2;
    private static final double BUMP_TO_ROW1 = Y_ROW1 - Y_START + 2;  // +2 is so we fully capture the ball
    private static final double BUMP_TO_ROW2 = BUMP_TO_ROW1 + ROW_SPACING;
    private static final double BUMP_TO_ROW3 = BUMP_TO_ROW2 + ROW_SPACING;

    /*******************************************************************************************/
    // The following command lists are "helper methods" for the full autonomous routines

    // Empty command list in case you don't want to run any autonomous code at all
    public static CommandBase[] cmdsEmpty = {};

    private static CommandBase[] cmdsRepelThenKick =
    {
        new PossessorMode(Possessor.REPEL),
        new KickCommand(),
        new PossessorMode(Possessor.POSSESS)
    };
    private static CommandBase[] cmdsKickBallNearFieldRow2 =
    {
        new WindCommand(KICK_POWER2),
        new PossessorMode(Possessor.POSSESS),
        new DistanceMove(BUMP_TO_ROW2, 0.65),
        new CenterOnTargetCommand(),              // alternative to TurnToward(target)
        //new TurnTowardCommand(X_TARGET, Y_TARGET_NEAR_FIELD),
        new KickCommand(),
    };
    private static CommandBase[] cmdsKickBallsMidFieldStraightRows23 =
    {
        new WindCommand(KICK_POWER5),
        new PossessorMode(Possessor.POSSESS),
        new DistanceMove(BUMP_TO_ROW2, 0.65),
        new TurnTowardCommand(X_TARGET, Y_TARGET_MID_FIELD),
        //new CommandList(cmdsRepelThenKick),
        new KickCommand(),
        new WindCommand(KICK_POWER4),
        new TurnToHeadingCommand(0),
        new DistanceMove(ROW_SPACING, 1.0),
        new TurnTowardCommand(X_TARGET, Y_TARGET_MID_FIELD),
        //new CommandList(cmdsRepelThenKick),
        new KickCommand(),
        new WindCommand(KICK_POWER4),
    };
    private static CommandBase[] cmdsMidFieldBlockTunnel =
    {
        new MoveToCommand(X_CENTER_LINE, Y_ROW1),
        new TurnToHeadingCommand(0),                // point the robot front toward the tower
        new DistanceMove(BUMP_TO_ROW1+3, -0.4)      // back up right into mouth of tunnel
    };
    
    private static CommandBase[] cmdsGoIntoTunnel = 
    {
        new MoveToCommand(X_CENTER_LINE, Y_ROW1), // Go over to the centerline
        new TurnToHeadingCommand(0),    // Turn toward the tunnel
        new DistanceMove(60, 1.0)       // Drive partway into tunnel
    };

    private static CommandBase[] cmdsKickBallsFarFieldStraightRows123 =
    {
        new PossessorMode(Possessor.POSSESS),
        new WindCommand(KICK_POWER9),
        new WaitCommand(500),
        new DistanceMove(BACKWALL_TO_ROW1, 0.5),
        new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        //new CommandList(cmdsRepelThenKick),
        new KickCommand(),
        new WindCommand(KICK_POWER8),
        new WaitCommand(500),
        new TurnToHeadingCommand(0),
        new DistanceMove(ROW_SPACING, 0.5),
        new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        //new CommandList(cmdsRepelThenKick),
        new KickCommand(),
        new WindCommand(KICK_POWER7),
        new WaitCommand(500),
        new TurnToHeadingCommand(0),
        new DistanceMove(ROW_SPACING, 0.5),
        new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        //new CommandList(cmdsRepelThenKick),
        new KickCommand(),
        new WindCommand(KICK_POWER7),
    };

/*******************************  NEAR FIELD  ************************************************************/

    // Commands for nearField, dotsColumn 1, one ball on 2nd dot straight ahead
    public static CommandBase[] cmdsNearFieldDotsCol1 =
    {
        new SetPositionCommand(X_COL1, Y_START),
        //new CommandList(cmdsKickBallNearFieldRow2),
        new WindCommand(KICK_POWER2),
        new PossessorMode(Possessor.POSSESS),
        new DistanceMove(BUMP_TO_ROW2, 0.65),
        new TurnCommand(-10.0),
        new KickCommand(),
        new TurnToHeadingCommand(90),
        new DistanceMove(X_CENTER_LINE - X_COL1 -4, 1.0)
    };

    // Commands for nearField, dotsColumn 2, one ball on 2nd dot straight ahead
    public static CommandBase[] cmdsNearFieldDotsCol2 =
    {
        new SetPositionCommand(X_COL2, Y_START),
        //new CommandList(cmdsKickBallNearFieldRow2),
        new WindCommand(KICK_POWER2),
        new PossessorMode(Possessor.POSSESS),
        new DistanceMove(BUMP_TO_ROW2, 0.65),
        new TurnCommand(-20.0),
        new KickCommand(),
        //new MoveToCommand(X_CENTER_LINE, Y_ROW3)
        new TurnToHeadingCommand(90),
        new DistanceMove(X_CENTER_LINE - X_COL2 - 4, 1.0)
    };

    // Commands for nearField, dotsColumn 3, one ball on 2nd dot straight ahead
    public static CommandBase[] cmdsNearFieldDotsCol3 =
    {
        new SetPositionCommand(X_COL3, Y_START),
        //new CommandList(cmdsKickBallNearFieldRow2),
        new WindCommand(KICK_POWER2),
        new PossessorMode(Possessor.POSSESS),
        new DistanceMove(BUMP_TO_ROW2, 0.65),
        new TurnCommand(-30.0),
        new KickCommand(),
        //new MoveToCommand(X_CENTER_LINE, Y_ROW3)
        new TurnToHeadingCommand(90),
        new DistanceMove(X_CENTER_LINE - X_COL3 - 4, 1.0)
    };

/*******************************  MID FIELD  ************************************************************/

    // Commands for midField, dotsColumn 1, balls on 2nd and 3rd dots straight ahead
    public static CommandBase[] cmdsMidFieldDotsCol1 =
    {
        new SetPositionCommand(X_COL1, Y_START),
        //new CommandList(cmdsKickBallsMidFieldStraightRows23),
        new WindCommand(KICK_POWER5),
        new PossessorMode(Possessor.POSSESS),
        new DistanceMove(BUMP_TO_ROW2, 0.65),
        //new TurnTowardCommand(X_TARGET, Y_TARGET_MID_FIELD),
        new TurnCommand(-2.12),
        //new CommandList(cmdsRepelThenKick),
        new KickCommand(),
        new WindCommand(KICK_POWER4),
        new TurnToHeadingCommand(0),
        new DistanceMove(ROW_SPACING, 1.0),
        //new TurnTowardCommand(X_TARGET, Y_TARGET_MID_FIELD),
        new TurnCommand(-2.39),
        //new CommandList(cmdsRepelThenKick),
        new KickCommand(),
        new WindCommand(KICK_POWER4),
        new TurnToHeadingCommand(90),
        new DistanceMove(X_CENTER_LINE - X_COL1 - 4, 1.0),
        //new CommandList(cmdsMidFieldBlockTunnel)
    };

    // Commands for midField, dotsColumn 2, balls on 2nd and 3rd dots straight ahead
    public static CommandBase[] cmdsMidFieldDotsCol2 =
    {
        new SetPositionCommand(X_COL2, Y_START),
        //new CommandList(cmdsKickBallsMidFieldStraightRows23),
        new WindCommand(KICK_POWER5),
        new PossessorMode(Possessor.POSSESS),
        new DistanceMove(BUMP_TO_ROW2, 0.65),
        //new TurnTowardCommand(X_TARGET, Y_TARGET_MID_FIELD),
        new TurnCommand(-8.43),
        //new CommandList(cmdsRepelThenKick),
        new KickCommand(),
        new WindCommand(KICK_POWER4),
        new TurnToHeadingCommand(0),
        new DistanceMove(ROW_SPACING, 1.0),
        //new TurnTowardCommand(X_TARGET, Y_TARGET_MID_FIELD),
        new TurnCommand(-9.46),
        //new CommandList(cmdsRepelThenKick),
        new KickCommand(),
        new WindCommand(KICK_POWER4),
        new TurnToHeadingCommand(90),
        new DistanceMove(X_CENTER_LINE - X_COL2 - 4, 1.0),
        //new CommandList(cmdsMidFieldBlockTunnel)
    };

    // Commands for midField, dotsColumn 3, balls on 2nd and 3rd dots straight ahead
    public static CommandBase[] cmdsMidFieldDotsCol3 =
    {
        new SetPositionCommand(X_COL3, Y_START),
        new CommandList(cmdsKickBallsMidFieldStraightRows23),
        new TurnToHeadingCommand(90),
        new DistanceMove(X_CENTER_LINE - X_COL3 - 4, 1.0),
        //new CommandList(cmdsMidFieldBlockTunnel)
    };

/*******************************  FAR FIELD  ************************************************************/

    // Commands for farField, dotsColumn 1, three balls straight ahead
    public static CommandBase[] cmdsFarFieldDotsCol1 =
    {
        new SetPositionCommand(X_COL1, Y_START),
        //new CommandList(cmdsKickBallsFarFieldStraightRows123),
        new PossessorMode(Possessor.POSSESS),
        new WindCommand(KICK_POWER9),
        new WaitCommand(300),
        new DistanceMove(BACKWALL_TO_ROW1, 0.5),
        //new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new TurnCommand(-1.19),
        new CommandList(cmdsRepelThenKick),
        //new KickCommand(),
        new WindCommand(KICK_POWER8),
        new TurnToHeadingCommand(0),
        new DistanceMove(ROW_SPACING, 0.5),
        //new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new TurnCommand(-1.27),
        new CommandList(cmdsRepelThenKick),
        //new KickCommand(),
        new WindCommand(KICK_POWER7),
        new TurnToHeadingCommand(0),
        new DistanceMove(ROW_SPACING, 0.5),
        //new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new TurnCommand(-1.36),
        new CommandList(cmdsRepelThenKick),
        //new KickCommand(),
        new WindCommand(KICK_POWER7),
        //end subroutine
        new TurnToHeadingCommand(90),
        new DistanceMove(42 + 36*2, 1.0), // Go over to the centerline
        new TurnToHeadingCommand(0),      // Turn toward the tunnel
        new DistanceMove(50, 1.0)         // Drive partway into tunnel
    };
    
    public static CommandBase[] cmdsFarFieldDotsCol2 =
    {
        new SetPositionCommand(X_COL2, Y_START),
        //new CommandList(cmdsKickBallsFarFieldStraightRows123),
        new PossessorMode(Possessor.POSSESS),
        new WindCommand(KICK_POWER9),
        new WaitCommand(300),
        new DistanceMove(BACKWALL_TO_ROW1, 0.5),
        //new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new TurnCommand(-4.76),
        new CommandList(cmdsRepelThenKick),
        //new KickCommand(),
        new WindCommand(KICK_POWER8),
        new TurnToHeadingCommand(0),
        new DistanceMove(ROW_SPACING, 0.5),
        //new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new TurnCommand(-5.08),
        new CommandList(cmdsRepelThenKick),
        //new KickCommand(),
        new WindCommand(KICK_POWER7),
        new TurnToHeadingCommand(0),
        new DistanceMove(ROW_SPACING, 0.5),
        //new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new TurnCommand(-5.44),
        new CommandList(cmdsRepelThenKick),
        //new KickCommand(),
        new WindCommand(KICK_POWER7),
        //end subroutine
        new TurnToHeadingCommand(90),
        new DistanceMove(42 + 36, 1.0),  // Go over to the centerline
        new TurnToHeadingCommand(0),     // Turn toward the tunnel
        new DistanceMove(60, 1.0)        // Drive partway into tunnel
    };
    
    public static CommandBase[] cmdsFarFieldDotsCol3 =
    {
        new SetPositionCommand(X_COL3, Y_START),
        //new CommandList(cmdsKickBallsFarFieldStraightRows123),
        new PossessorMode(Possessor.POSSESS),
        new WindCommand(KICK_POWER9),
        new WaitCommand(300),
        new DistanceMove(BACKWALL_TO_ROW1, 0.5),
        //new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new TurnCommand(-8.30),
        new CommandList(cmdsRepelThenKick),
        //new KickCommand(),
        new WindCommand(KICK_POWER8),
        new TurnToHeadingCommand(0),
        new DistanceMove(ROW_SPACING, 0.5),
        //new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new TurnCommand(-8.84),
        new CommandList(cmdsRepelThenKick),
        //new KickCommand(),
        new WindCommand(KICK_POWER7),
        new TurnToHeadingCommand(0),
        new DistanceMove(ROW_SPACING, 0.5),
        //new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new TurnCommand(-9.46),
        new CommandList(cmdsRepelThenKick),
        //new KickCommand(),
        new WindCommand(KICK_POWER7),
        //end subroutine
        new TurnToHeadingCommand(90),
        new DistanceMove(42, 1.0),      // Go over to the centerline
        new TurnToHeadingCommand(0),    // Turn toward the tunnel
        new DistanceMove(75, 1.0)       // Drive partway into tunnel
    };

    /********************************************************************************/
    // The command array that gets used in Team948Robot class in autonomous mode if
    // the autonomous selector switches malfunction or are disabled.

    // Manderson's Note to self: this is where the default autonomous routine is set
    public static CommandBase[] cmdsDefault = cmdsNearFieldDotsCol1;

    /********************************************************************************/

    /**
     *
     * @param cmds The array of commands we want the robot to perform (listed above)
     */
    public Autonomous(CommandBase[] cmds)
    {
        this.cmds = cmds;
        initialized = false;
        cmdIndex = 0;
        if (cmds.length > 0)
            cmd = cmds[cmdIndex];
        else
            cmd = null;
    }

    public boolean commandsCompleted()
    {
        return (cmd == null);
    }
    /**
     *  Runs through the commands that were set in the constructor.
     */
    public void update()
    {
        // Does nothing if done.
        if (cmd == null)
            return;
        // Initializes commands if not already.
        if (!initialized)
        {
            Debug.println(Debug.CMDDISPATCH, cmd);
            cmd.init();
            initialized = true;
        }
        // Ensure we don't get a penalty for crossing completely over the midline
	if (PositionTracker.getX() > (X_CENTER_LINE + 3))
	{
	    NRGDrive.drivePID.stop();
	    Debug.println(Debug.CMDDISPATCH, "Out of bounds penalty avoided. Stopped autonomous.");
	    cmd = null;
	}
	else if (cmd.run())     // Run the command
        {
            // The current command just finished, so go to next command.
            initialized = false;
            cmdIndex++;
            // Checks whether it is on last command or not. 
            if (cmdIndex < cmds.length)
            {
                cmd = cmds[cmdIndex];
                Debug.println(Debug.CMDDISPATCH, "Autonomous: Moving to next command, index " + cmdIndex + ".");
            }
            else
            {
                Debug.println(Debug.CMDDISPATCH, "Autonomous: No more commands.");
                cmd = null;
            }
        }
    }
}