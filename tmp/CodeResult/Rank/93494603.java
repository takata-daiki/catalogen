package gearworks.core.common;

import org.bukkit.ChatColor;

public enum Rank {

    DEVELOPER(0, "Dev", ChatColor.RED),
    OWNER(0, "Owner", ChatColor.DARK_RED),
    ADMIN(1, "Admin", ChatColor.RED),
    MODERATOR(2, "Mod", ChatColor.GOLD),
    HELPER(3, "Helper", ChatColor.GREEN),
    MAPDEV(3, "Mapper", ChatColor.AQUA),
    DEITY(4, "Deity", ChatColor.LIGHT_PURPLE),
    MEMBER(5, "Member", ChatColor.GRAY),
    GUEST(6, "Guest", ChatColor.DARK_GRAY);

    private int priority;
    private String name;
    private ChatColor color;

    private Rank (int priority, String name, ChatColor color){
        this.priority = priority;
        this.name = name;
        this.color = color;
    }

    /**
     *
     * @param playerRank
     * @param requiredRank
     * @return true if player has the required rank or above, otherwise fals
     */
    public static boolean has (Rank playerRank, Rank requiredRank){
        if (playerRank.getPriority () <= requiredRank.getPriority ()){
            return true;
        }

        return false;
    }

    public static Rank getRankByName (final String rankName){
        for (final Rank rank : Rank.values ()){
            if (rank.getRankName ().toLowerCase ().equals (rankName.toLowerCase ())){
                return rank;
            }
        }

        return null;
    }

    public int getPriority (){
        return priority;
    }

    public String getRankName (){
        return name;
    }

    public ChatColor getColor (){
        return color;
    }

}
