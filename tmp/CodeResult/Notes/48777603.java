package net.diamondmine.mcftprofiler;

import static net.diamondmine.mcftprofiler.Util.fixColor;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Handler for notes.
 * 
 * @author Jon la Cour
 * @version 1.4.0
 */
public class Notes extends McftProfiler {

    /**
     * Send the profile lines to the requesting player.
     * 
     * @param name
     *            Who to lookup.
     * @param sender
     *            The requesting player.
     * @since 1.3.0
     */
    public static void send(final String name, final CommandSender sender) {
        fetchNotes(name, sender);
    }

    /**
     * Send the profile lines to the requesting player.
     * 
     * @param name
     *            Who to lookup.
     * @param sender
     *            The requesting player.
     * @param player
     *            The sender's Player object
     * @since 1.3.0
     */
    public static void send(final String name, final CommandSender sender, final Player player) {
        if (name.equals(player.getName())) {
            if (p.has(player, "mcftprofiler.status.*") || p.has(player, "mcftprofiler.status.self")) {
                if (p.has(player.getWorld(), name, "mcftprofiler.staff")) {
                    if (p.has(player, "mcftprofiler.status.*") || p.has(player, "mcftprofiler.status.staff")) {
                        fetchNotes(name, sender);
                    } else {
                        sender.sendMessage(ChatColor.GOLD + "You don't have permission to do that!");
                    }
                } else {
                    fetchNotes(name, sender);
                }
            }
        } else {
            if (p.has(player.getWorld(), name, "mcftprofiler.staff")) {
                if (p.has(player, "mcftprofiler.status.staff")) {
                    fetchNotes(name, sender);
                } else {
                    sender.sendMessage(ChatColor.GOLD + "You don't have permission to do that!");
                }
            } else {
                fetchNotes(name, sender);
            }
        }
    }

    /**
     * Sends a player another player's notes.
     * 
     * @param sender
     *            The command sender.
     * @param args
     *            The command arguments.
     * @param cmdname
     *            The name of the command used.
     * @since 1.2.4
     */
    public static void note(final CommandSender sender, final String[] args, final String cmdname) {
        boolean mainnode = false, console = false;
        Player player = null;
        String pname = null;
        if (sender instanceof Player) {
            player = (Player) sender;
            pname = player.getName();
            if (p.has(player, "mcftprofiler.*")) {
                mainnode = true;
            }
        } else {
            pname = "Console";
            mainnode = true;
            console = true;
        }
        if (mainnode || p.has(player, "mcftprofiler.note.*") || p.has(player, "mcftprofiler.note.add")) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.GOLD + "Not enough arguments!");
                sender.sendMessage(ChatColor.RED + "/" + cmdname + " " + ChatColor.GRAY + "name note " + ChatColor.YELLOW + "Adds a note to a user.");
            } else if (args.length == 1) {
                sender.sendMessage(ChatColor.GOLD + "Not enough arguments!");
                sender.sendMessage(ChatColor.RED + "/" + cmdname + " " + args[0] + ChatColor.GRAY + " note" + ChatColor.YELLOW + " Adds a note to " + args[0]
                        + ".");
            } else if (args.length > 1) {
                String name = args[0];
                String note = "";
                for (int i = 1; i < args.length; i++) {
                    String s = args[i];
                    if (note.length() > 0) {
                        note += " ";
                    }
                    note += s;
                }
                if (Bukkit.getServer().getPlayer(name) != null) {
                    Player user = Bukkit.getServer().getPlayer(name);
                    name = user.getName();
                }
                String option = args[1];
                String remove = "";
                for (int i = 2; i < args.length; i++) {
                    String s = args[i];
                    if (remove.length() > 0) {
                        remove += " ";
                    }
                    remove += s;
                }
                if (option.equals("-delete") || option.equals("-d") || option.equals("-remove") || option.equals("-r")) {
                    if (console || p.has(player, "mcftprofiler.note.delete") || p.has(player, "mcftprofiler.note.remove")) {
                        removeNote(name, remove, sender);
                    }
                } else {
                    addNote(name, note, sender, pname, true);
                }
            }
        } else {
            sender.sendMessage(ChatColor.GOLD + "You don't have permission to do that!");
        }
    }

    /**
     * This adds a note to a player.
     * 
     * @param name
     *            The user to add a note to.
     * @param note
     *            The note to add.
     * @param sender
     *            Command sender
     * @param pname
     *            Command sender's name
     * @param response
     *            Send command sender a response.
     * @since 1.1.4
     */
    public static void addNote(final String name, final String note, final CommandSender sender, final String pname, final boolean response) {
        PreparedStatement query = null;
        ResultSet result = null;
        try {
            query = database.db.prepare("SELECT profileid, username FROM " + database.prefix + "profiles WHERE username = ? LIMIT 10");
            query.setString(1, name);
            result = query.executeQuery();

            if (result.next()) {
                if (response) {
                    sender.sendMessage(ChatColor.GOLD + "Added note to " + name + ".");
                }
                query.close();
                result.close();

                query = database.db.prepare("INSERT INTO " + database.prefix
                        + "notes (noteid, username, time, staff, note) VALUES (NULL, ?, CURRENT_TIMESTAMP, ?, ?)");
                query.setString(1, name);
                query.setString(2, pname);
                query.setString(3, note);
                query.executeUpdate();
            } else {
                if (response) {
                    sender.sendMessage(ChatColor.GOLD + "Sorry, we don't know that user. You entered " + name + ".");
                    sender.sendMessage(ChatColor.GOLD + "Your note was: " + ChatColor.GRAY + note);
                }
            }
        } catch (Exception e) {
            log("Error while adding note to user: " + e.getLocalizedMessage(), "warning");
        } finally {
            try {
                query.close();
                result.close();
            } catch (Exception e) {
                log("Error closing query while adding note to user: " + e.getLocalizedMessage(), "severe");
            }
        }
    }

    /**
     * This removes a note from a player.
     * 
     * @param name
     *            The user to remove a note from.
     * @param remove
     *            A piece of a note to be removed.
     * @param sender
     *            Command sender
     * @since 1.1.4
     */
    public static void removeNote(final String name, final String remove, final CommandSender sender) {
        PreparedStatement query = null;
        ResultSet result = null;
        try {
            query = database.db.prepare("SELECT noteid, note FROM " + database.prefix + "notes WHERE username LIKE ? AND note LIKE ? LIMIT 1");
            query.setString(1, name);
            query.setString(2, "%" + remove + "%");
            result = query.executeQuery();

            boolean results = result.next();
            String noteid = result.getString("noteid");
            String note = result.getString("note");

            if (results) {
                query.close();
                result.close();

                database.db.query("DELETE FROM " + database.prefix + "notes WHERE noteid = '" + noteid + "';");
                sender.sendMessage(ChatColor.GOLD + "Note removed: " + ChatColor.GRAY + note);
            } else {
                sender.sendMessage(ChatColor.GOLD + "Sorry, we couldn't find a note containing: " + ChatColor.GRAY + remove);
            }
        } catch (Exception e) {
            log("Error while removing note from user: " + e.getLocalizedMessage() + ". Try being more specific about the note.", "warning");
        } finally {
            try {
                query.close();
                result.close();
            } catch (Exception e) {
                log("Error closing query while removing note from user: " + e.getLocalizedMessage(), "severe");
            }
        }
    }

    /**
     * This fetches notes on a player.
     * 
     * @param name
     *            The user to fetch notes from
     * @param sender
     *            Command sender
     * @since 1.0.0
     */
    public static void fetchNotes(final String name, final CommandSender sender) {
        FileConfiguration config = Configuration.getConfiguration();
        int maxnotes = config.getInt("profile.notes.amount-per-page", 5);
        try {
            ResultSet notes = database.db.query("SELECT noteid, username, time, staff, note FROM " + database.prefix + "notes WHERE username LIKE '" + name
                    + "' ORDER BY time DESC LIMIT " + maxnotes + ";");
            if (notes.next()) {
                do {
                    String staff = notes.getString("staff");
                    String note = notes.getString("note");
                    SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yy h:mma ");

                    try {
                        formatDate.setTimeZone(TimeZone.getTimeZone(config.getString("profile.notes.timezone")));
                    } catch (Exception e) {
                        log("Invalid TimeZone '" + config.getString("profile.notes.timezone") + "'. Please check configuration.", "severe");
                    }

                    Date date = new Date(Timestamp.valueOf(notes.getString("time")).getTime());
                    String time = formatDate.format(date);
                    
                    World world = Bukkit.getServer().getWorlds().get(0);
                    String rank = c.getPrimaryGroup(world, staff);
                    String prefix = fixColor(c.getGroupPrefix(world, rank));

                    sender.sendMessage(ChatColor.RED + time + ChatColor.WHITE + note + " " + prefix + "[" + staff + "]");
                } while (notes.next());
            }
            notes.close();
        } catch (Exception e) {
            log("Error while fetching user notes: " + e.getLocalizedMessage(), "severe");
        }
    }

}
