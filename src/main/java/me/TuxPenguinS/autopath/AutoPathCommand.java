package me.TuxPenguinS.autopath;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoPathCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            /*if (command.getName().equals("setAutoPathMaterials"))
            {
            }
             */
            if (!player.isOp())
            {
                AutoPathUtils.sendErrorMessage(player, "OP required for this command.");
                return true;
            }
            if (command.getName().equals("AutoPath"))
            {
                if (!AutoPathUtils.enabledForPlayer.getOrDefault(player.getDisplayName(), false))
                {
                    AutoPathUtils.enabledForPlayer.put(player.getDisplayName(), true);
                    player.sendMessage("AutoPath Enabled");
                }

                else
                {
                    AutoPathUtils.enabledForPlayer.put(player.getDisplayName(), false);
                    player.sendMessage("AutoPath Disabled");
                }


            } else if (command.getName().equals("AutoBridge"))
            {
            if (!AutoPathUtils.bridgeModeEnabledForPlayer.getOrDefault(player.getDisplayName(), false))
            {
                AutoPathUtils.bridgeModeEnabledForPlayer.put(player.getDisplayName(), true);
                player.sendMessage("AutoPath bridge mode Enabled");
            }

            else
            {
                AutoPathUtils.bridgeModeEnabledForPlayer.put(player.getDisplayName(), false);
                player.sendMessage("AutoPath bridge mode Disabled");
            }

        }
        else if (command.getName().equals("AutoPathMaterial"))
            {
                if (args.length < 3)
                {
                    AutoPathUtils.sendErrorMessage(player, "At least 3 materials required for this command.");
                } else
                {
                    Material[] materials = new Material[args.length];
                    for (int i = 0; i<= args.length -1; i++)
                    {
                        try
                        {
                            String materialname = args[i];
                            materialname = materialname.replaceAll("LEGACY_", "").replaceAll("_LEGACY", "");
                            materials[i] = Material.valueOf(materialname);
                            if (!materials[i].isBlock())
                            {
                                player.sendMessage(ChatColor.RED + args[i] + " ERROR");
                                player.sendMessage("This material isn't a placeable block.");
                                return true;
                            } else {
                                player.sendMessage(ChatColor.GREEN + materialname + " OK");
                            }
                        } catch (Exception e)
                        {
                            player.sendMessage(ChatColor.RED + args[i] + "ERROR");
                            player.sendMessage(e.getMessage());
                        }

                    }
                    AutoPathUtils.pathMaterialForPlayer.put(player.getDisplayName(), materials);
                }
            }
        }
        return true;
    }
}