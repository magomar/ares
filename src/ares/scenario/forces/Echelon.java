package ares.scenario.forces;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum Echelon {

//    SQUAD(0, 0),
    SECTION("..", 0, 0),
    PLATOON("...", 1, 1),
    COMPANY("|", 3, 2),
    BATTALION("||", 5, 3),
    REGIMENT("|||", 15, 4),
    BRIGADE("X", 20, 4),
    DIVISION("XX", 40, 5),
    CORPS("XXX", 80, 6),
    ARMY("XXXX", 120, 7),
    ARMY_GROUP("XXXXX", 140, 8),
    REGION("XXXXXX", 150, 9);
    private final static double SPEED_FACTOR = -0.5;
    private final static double COMMAND_FACTOR = 0.5;
    private final String symbol;
    /**
     * Number of Logistics squads needed to achieve 100% supply distribution
     */
    private final int logistics;
    /**
     * Number of command squads needed to achieve 100% C2 efficiency
     */
    private final int command;
    /**
     * Speed modifier derived from the estimated C2 complexity, which in turn is estimated as a function of the {@link #command}
     * squads required by this echelon
     */
    private final double speedModifier;
    /**
     * C2 modifier derived from estimated organization complexity, computed as a function of the {@link #command} squads required
     * by this echelon
     */
    private final double commandModifier;

    private Echelon(final String symbol,final int logistics, final int command) {
        this.symbol = symbol;
        this.logistics = logistics;
        this.command = command;
        speedModifier = 1.0 + (command - 3)
                * SPEED_FACTOR / 6.0;
        commandModifier = 1.0 + (command - 3)
                * COMMAND_FACTOR / 6.0;

    }

    public String getSymbol() {
        return symbol;
    }

    public int getCommand() {
        return command;
    }

    public double getCommandModifier() {
        return commandModifier;
    }

    public int getLogistics() {
        return logistics;
    }

    public double getSpeedModifier() {
        return speedModifier;
    }
    
    public double getModifiedTime(int time) {
        return time * speedModifier;
    }
}
