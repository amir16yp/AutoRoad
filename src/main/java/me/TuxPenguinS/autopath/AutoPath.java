package me.TuxPenguinS.autopath;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AutoPath extends JavaPlugin implements Listener {
    @Override
    public void onEnable()
    {
        getLogger().info("AutoPath plugin enabled.");
        AutoPathCommand commandExecutor = new AutoPathCommand();
        getCommand("AutoPath").setExecutor(commandExecutor);
        getCommand("AutoBridge").setExecutor(commandExecutor);
        getCommand("AutoPathMaterial").setExecutor(commandExecutor);
        getCommand("AutoPathMaterial").setTabCompleter(new AutoPathTabCompleter());
        getServer().getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if (!player.isOp()) { return ; }
        Material[] defaultMaterials = new Material[]
                {
                        Material.GRAVEL,
                        Material.COARSE_DIRT,
                        Material.COBBLESTONE
                };
        Material[] replaceWith = AutoPathUtils.pathMaterialForPlayer.getOrDefault(player.getDisplayName(), defaultMaterials);
        if (AutoPathUtils.enabledForPlayer.getOrDefault(player.getDisplayName(), false))
        {
            Random rand = new Random();
            Location to = event.getTo();
            Block[] blocksBeneathPlayer = AutoPathUtils.getBlocksBeneathPlayer(to, player);
            for (Block block: blocksBeneathPlayer)
            {
                //System.out.println(block.getType().name());
                List<Material> stepOnMaterials = new ArrayList<Material>();
                stepOnMaterials.add(Material.GRASS_BLOCK);
                if (AutoPathUtils.bridgeModeEnabledForPlayer.get(player.getDisplayName()))
                {
                    stepOnMaterials.add(Material.AIR);
                }
                if (stepOnMaterials.contains(block.getType()))
                {
                    block.setType(replaceWith[rand.nextInt(replaceWith.length)]);
                    Block blockAbove = block.getLocation().add(0, 1, 0).getBlock();
                    if (blockAbove.getType() == Material.SNOW)
                    {
                        blockAbove.setType(Material.AIR);
                    }
                }
            }
        }
    }
}