package quaks.by.ntmcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static quaks.by.ntmcore.NTMcore.scoreboard;
import static quaks.by.ntmcore.NTMcore.team;

public class NameTagHideListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        apply(e.getPlayer());
    }
    public void apply(Player p) {
        if (team.getPlayers().contains(p)) {
            team.addPlayer(p);
            p.setScoreboard(scoreboard);
        }
    }
}
