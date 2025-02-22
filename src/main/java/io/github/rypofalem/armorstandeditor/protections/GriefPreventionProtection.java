package io.github.rypofalem.armorstandeditor.protections;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;


public class GriefPreventionProtection implements Protection  {

    private boolean gpEnabled;
    private GriefPrevention griefPrevention = null;

    public GriefPreventionProtection(){
        gpEnabled = Bukkit.getPluginManager().isPluginEnabled("GriefPrevention");

        if(!gpEnabled) return;
        griefPrevention = (GriefPrevention) Bukkit.getPluginManager().getPlugin("GriefPrevention");
    }

    public boolean checkPermission(Block block, Player player){
        if(!gpEnabled) return true;
        if(player.hasPermission("asedit.ignoreProtection.griefPrevention")) return true;

        Location blockLoc = block.getLocation();

        if(GriefPrevention.instance.claimsEnabledForWorld(blockLoc.getWorld())){

            Claim landClaim = griefPrevention.dataStore.getClaimAt(blockLoc, false, null);
            Material blockMat = block.getType();

            if(landClaim != null && landClaim.allowEdit(player) != null && landClaim.allowBuild(player,blockMat) != null){
                player.sendMessage(ChatColor.RED + landClaim.allowEdit(player));
                player.sendMessage(ChatColor.RED + landClaim.allowBuild(player, blockMat));
                return false;
            }
        } else{
            return true;
        }

        return true;


    }
}
