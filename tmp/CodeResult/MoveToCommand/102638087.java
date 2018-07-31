package nrgrobot;

import nrgrobot.cmd.*;

/**
 * A collection of autonomous routines that are purely for testing various functions of the robot.
 *
 * @author Paul
 */
public class AutonomousTests
{
    public static final double X_TUNNEL_MOUTH = 162;
    public static final double Y_TUNNEL_MOUTH = 182;

    // Use turnTester to find the minimum motor speed that will break friction and turn the robot
    public static CommandBase[] turnTester =
    {
        new TimedTurnCommand(2000, .41),
        new WaitCommand (1000),
        new TimedTurnCommand(2000, .39),
        new WaitCommand (1000),
        new TimedTurnCommand(2000, .37),
        new WaitCommand (1000),
        new TimedTurnCommand(2000, .35),
        new WaitCommand (1000),
        new TimedTurnCommand(2000, .34),
        new WaitCommand (1000),
        new TimedTurnCommand(2000, .33),
        new WaitCommand (1000),
        new TimedTurnCommand(2000, .32),
        new WaitCommand (1000),
        new TimedTurnCommand(2000, .31),
        new WaitCommand (1000),
        new TimedTurnCommand(2000, .30),
        new WaitCommand (1000),
        new TimedTurnCommand(2000, .29),
        new WaitCommand (1000),
        new TimedTurnCommand(2000, .28)
    };

    // Use motorTester to find the minimum motor speed that will break friction and move the robot
    public static CommandBase[] motorTester =
    {
        new SetPositionCommand(0,0),
        new TimedMoveCommand(3000, 1),
        new WaitCommand(750),
        new TimedMoveCommand(3000, -1),
        new SetPositionCommand(0,0),
        new TimedMoveCommand(3000, .9),
        new WaitCommand(750),
        new TimedMoveCommand(3000, -.9),
        new SetPositionCommand(0,0),
        new TimedMoveCommand(3000, .8),
        new WaitCommand(750),
        new TimedMoveCommand(3000, -.8),
        new SetPositionCommand(0,0),
        new TimedMoveCommand(3000, .7),
        new WaitCommand(750),
        new TimedMoveCommand(3000, -.7),
        new SetPositionCommand(0,0),
        new TimedMoveCommand(3000, .6),
        new WaitCommand(750),
        new TimedMoveCommand(3000, -.6),
        new SetPositionCommand(0,0),
        new TimedMoveCommand(3000, .5),
        new WaitCommand(750),
        new TimedMoveCommand(3000, -.5),
        new SetPositionCommand(0,0),
        new TimedMoveCommand(3000, .4),
        new WaitCommand(750),
        new TimedMoveCommand(3000, -.4),
        new SetPositionCommand(0,0),
        new TimedMoveCommand(3000, .3),
        new WaitCommand(750),
        new TimedMoveCommand(3000, -.3),
        new SetPositionCommand(0,0),
        new TimedMoveCommand(3000, .25),
        new WaitCommand(750),
        new TimedMoveCommand(3000, -.25),
        new SetPositionCommand(0,0),
        new TimedMoveCommand(3000, .2),
        new WaitCommand(750),
        new TimedMoveCommand(3000, -.2),
        new SetPositionCommand(0,0),
        new TimedMoveCommand(3000, .15),
        new WaitCommand(750),
        new TimedMoveCommand(3000, -.15),
    };

    // Commands for nearField, dotsRow 1-3, one ball straight ahead
    public static CommandBase[] cmdsKickTester =
    {
        new WindCommand(50.0),
        new SetPositionCommand(42, 13),
        new DistanceMove(10, 0.5),
        new KickCommand(),
        new WindCommand(50.0),
        new KickCommand(),
        new WindCommand(50.0),
        new KickCommand(),
        new WindCommand(50.0),
        new KickCommand(),
        new WindCommand(50.0),
        new KickCommand(),
    };

    // Tests gyro accuracy -- see if robot has the same heading after 4 clockwise revolutions,
    // and again after 4 counter-clockwise revolutions.
    public static CommandBase[] cmdsTestingGyro =
    {
        //new SetPIDEnabledCommand(true),
        new TurnCommand(4*360),
        new WaitCommand(5000),
        new TurnCommand(-4*360)
    };

    // Tests ArcMoveCommand command
    public static CommandBase[] cmdsTestingArc =
    {
        new SetPIDEnabledCommand(true),
        new ArcMoveCommand(360, 48.0, 0.8),
        new WaitCommand(1000),
        new ArcMoveCommand(-360, 48.0, -0.8)
    };

    // Moves robot with 45 degree heading, then 135, then, 225, then 315 (should arrive at where it started).
    public static CommandBase[] cmdsTestingDiamond =
    {
	new SetPositionCommand(0, 0),
	new MoveToCommand(40, 40),
	new WaitCommand(4000),
	new MoveToCommand(80, 0),
	new WaitCommand(4000),
	new MoveToCommand(40, -40),
	new WaitCommand(4000),
	new MoveToCommand(0, 0),
	new WaitCommand(4000)
    };

    // Goes in a square clockwise forward, then goes same path but counter-clockwise and backwards.
    // Switches to high gear and goes forward, then switches to low gear.
    public static CommandBase[] cmdsSquaresTest =
    {
        new TimedMoveCommand(1500, 0.85),  // move forward 1500 ms, 85% motorSpeed
        new TurnCommand(90),
        new TimedMoveCommand(1500, 0.85),
        new TurnCommand(90),
        new TimedMoveCommand(1500, 0.85),
        new TurnCommand(90),
        new TimedMoveCommand(1500, 0.85),
        new TurnCommand(90),
        new WaitCommand(2000),

        new TurnCommand(-90),
        new TimedMoveCommand(1500, -0.85),
        new TurnCommand(-90),
        new TimedMoveCommand(1500, -0.85),
        new TurnCommand(-90),
        new TimedMoveCommand(1500, -0.85),
        new TurnCommand(-90),
        new TimedMoveCommand(1500, -0.85),

        new SetGearCommand(SetGearCommand.HIGH_GEAR),
        new TimedMoveCommand(1000, 0.50),
        new WaitCommand(2000),
        new SetGearCommand(SetGearCommand.LOW_GEAR),
        new WaitCommand(1000)
    };

    // Centers on target and moves forward, repeats, and then just centers.
    public static CommandBase[] cmdsTargetTrack =
    {
        new CenterOnTargetCommand(),
        new WaitCommand(1000),
        new TurnCommand(8),
        new TimedMoveCommand(1000, 1.0),
        new CenterOnTargetCommand(),
        new WaitCommand(1000),
        new TurnCommand(-8),
        new TimedMoveCommand(1000, 1.0),
        new CenterOnTargetCommand()
    };

    public static CommandBase[] cmdsHeadingTester =
    {
        new TurnToHeadingCommand(120),
        new TimedMoveCommand(24, 1),
        new TurnToHeadingCommand(240),
        new TimedMoveCommand(24, 1),
        new TurnToHeadingCommand(360),
        new TimedMoveCommand(24, 1)
    };

    public static CommandBase[] cmdsStraightPIDTester1 =
    {
        new DistanceMove(36, 1),
        new TurnCommand(10),
        new DistanceMove(36, 1),
        new TurnCommand(10),
        new DistanceMove(36, -1),
        new TurnCommand(10),
        new DistanceMove(36, -1)
    };

    public static CommandBase[] cmdsStraightPIDTester2 =
    {
        new DistanceMove(100, 0.5),
        new WaitCommand(100),
        new DistanceMove(100, -1.0),
        new WaitCommand(100),
        new DistanceMove(100, 0.75)
    };

    public static CommandBase[] cmdsPositionTrackerTest =
    {
        new SetPIDEnabledCommand(true),
        new SetPositionCommand(42, 13),
        new MoveToCommand(42, 73)
        //new TimedMoveCommand(2000, 1.0),
        //new ArcMoveCommand(90.0, 50, 1.0),
        //new TimedMoveCommand(5000, 1.0),
    };

    // Test commands to try to drive through the tunnel
    public static CommandBase[] cmdsTunnel =
    {
        new SetGearCommand(SetGearCommand.LOW_GEAR),
        new SetPositionCommand(42, 13),
        new MoveToCommand(13 * 12 + 8, 133),
        new TurnToHeadingCommand(180.0),
        new TimedMoveCommand(1000, 1.0)
    };

    // Try to kick 3 balls in dotRow 3 thru the tunnel (note: does not work well if balls get loft!)
    public static CommandBase[] cmdsDotsRow3KickThruTunnel =
    {
        new SetPositionCommand(42+36+36, 13),
        new WindCommand(50.0),
        new WaitCommand(500),
        new DistanceMove(42-13+3, 0.8),     // +3 to make sure we fully capture the ball
        new TurnTowardCommand(X_TUNNEL_MOUTH, Y_TUNNEL_MOUTH),
        new KickCommand(),
        new WindCommand(50.0),
        new TurnToHeadingCommand(0),
        new DistanceMove(36, 0.8),
        new TurnTowardCommand(X_TUNNEL_MOUTH, Y_TUNNEL_MOUTH),
        new KickCommand(),
        new WindCommand(50.0),
        new TurnToHeadingCommand(0),
        new DistanceMove(36, 0.7),
        new TurnTowardCommand(X_TUNNEL_MOUTH, Y_TUNNEL_MOUTH),
        new KickCommand(),
        new TurnToHeadingCommand(90),
        new DistanceMove(36, 1.0)
    };
}
