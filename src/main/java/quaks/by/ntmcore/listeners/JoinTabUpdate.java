package quaks.by.ntmcore.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import github.scarsz.discordsrv.DiscordSRV;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import quaks.by.ntmcore.NTMcore;
import quaks.by.ntmcore.enums.RoleManager;

public class JoinTabUpdate implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(RoleManager.ADMINISTRATOR.isMe(e.getPlayer().getName())){
            Bukkit.getScheduler().scheduleSyncDelayedTask(DiscordSRV.getPlugin(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player "+e.getPlayer().getName()+" prefix "+RoleManager.ADMINISTRATOR.getFormattedPrefix()));
        }else if(RoleManager.MODERATOR.isMe(e.getPlayer().getName())){
            Bukkit.getScheduler().scheduleSyncDelayedTask(DiscordSRV.getPlugin(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player "+e.getPlayer().getName()+" prefix "+RoleManager.MODERATOR.getFormattedPrefix()));
        }else if(RoleManager.HELPER.isMe(e.getPlayer().getName())){
            Bukkit.getScheduler().scheduleSyncDelayedTask(DiscordSRV.getPlugin(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player "+e.getPlayer().getName()+" prefix "+RoleManager.HELPER.getFormattedPrefix()));
        }else if(RoleManager.EVENT_MANAGER.isMe(e.getPlayer().getName())){
            Bukkit.getScheduler().scheduleSyncDelayedTask(DiscordSRV.getPlugin(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player "+e.getPlayer().getName()+" prefix "+RoleManager.EVENT_MANAGER.getFormattedPrefix()));
        }else{
            Bukkit.getScheduler().scheduleSyncDelayedTask(DiscordSRV.getPlugin(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player "+e.getPlayer().getName()+" prefix &r"));
        }
        if(e.getPlayer().getScoreboardTags().contains("global.off")){
            Bukkit.getScheduler().scheduleSyncDelayedTask(DiscordSRV.getPlugin(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nametagedit player "+e.getPlayer().getName()+" suffix &8`&r"));
        }else{
            Bukkit.getScheduler().scheduleSyncDelayedTask(DiscordSRV.getPlugin(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nametagedit player "+e.getPlayer().getName()+" suffix &r"));
        }
        PacketContainer pc = NTMcore.protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        pc.getChatComponents().write(0, WrappedChatComponent.fromJson("{\"text\":\"ɴᴛᴍ ᴘʀᴏᴊᴇᴄᴛ\",\"color\":\"#00bfff\"}")).write(1, WrappedChatComponent.fromJson(PlaceholderAPI.setPlaceholders(e.getPlayer(),"[\"\",{\"text\":\"Онлайн\",\"color\":\"#9EFF86\"},{\"text\":\" :\",\"color\":\"gray\"},{\"text\":\" %server_online%\",\"color\":\"gold\"},{\"text\":\"/\",\"color\":\"gray\"},{\"text\":\"%server_max_players%\",\"color\":\"gold\"}]")));
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            try {
                NTMcore.protocolManager.sendServerPacket(p.getPlayer(), pc);
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(DiscordSRV.getPlugin(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte reload"));
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        PacketContainer pc = NTMcore.protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        pc.getChatComponents().write(0, WrappedChatComponent.fromJson("{\"text\":\"ɴᴛᴍ ᴘʀᴏᴊᴇᴄᴛ\",\"color\":\"#00bfff\"}")).write(1, WrappedChatComponent.fromJson(PlaceholderAPI.setPlaceholders(e.getPlayer(),"[\"\",{\"text\":\"Онлайн\",\"color\":\"#9EFF86\"},{\"text\":\" :\",\"color\":\"gray\"},{\"text\":\" "+(Bukkit.getOnlinePlayers().size() -1)+"\",\"color\":\"gold\"},{\"text\":\"/\",\"color\":\"gray\"},{\"text\":\"%server_max_players%\",\"color\":\"gold\"}]")));
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            try {
                NTMcore.protocolManager.sendServerPacket(p.getPlayer(), pc);
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }
    }
}
