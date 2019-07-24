package me.machinemaker.damagemultipliers.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.Arrays;
import java.util.List;

public class EntityDamageByEntity implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) { return; }
        Player p = (Player) e.getEntity();

        Double multiplier = null;
        boolean found = false;
        for (PermissionAttachmentInfo permission : p.getEffectivePermissions()) {
            List<String> perm = Arrays.asList(permission.getPermission().split("\\."));
            if (perm.size() < 3 || perm.size() > 4 || !perm.get(0).equalsIgnoreCase("damagemultipliers")) { continue; }
            String[] permType = perm.get(1).split("_");
            if (permType.length < 2 || e.getDamager().getType() != getType(permType[1])) { continue; }
            try {
                multiplier = Double.parseDouble(String.join(".", perm.subList(2, perm.size())));
                found = true;
                break;
            } catch(NumberFormatException ex) {
                System.out.println("[DamageMultipliers] ERROR: Possible issue with permissions!");
            }
        }
        if (!found) { return; }
//        System.out.println("B (E): " + e.getFinalDamage());
        Double finalDamage = e.getFinalDamage() * multiplier;
        e.setDamage(finalDamage);
//        System.out.println("A (E): " + finalDamage);
    }

    private EntityType getType(String type) {
        for (EntityType e: EntityType.values()) {
            if (e.name().equalsIgnoreCase(type))
                return e;
        }
        return null;
    }
}
