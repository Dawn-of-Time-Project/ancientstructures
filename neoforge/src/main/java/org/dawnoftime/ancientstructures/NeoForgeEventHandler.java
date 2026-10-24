package org.dawnoftime.ancientstructures;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.dawnoftime.ancientstructures.advancement.CommonStructureChecker;

/**
 * NeoForge-specific event handler.
 * Registered automatically via {@link EventBusSubscriber}.
 */
@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForgeEventHandler {

    private static final int CHECK_INTERVAL = 20; // ticks (= 1 second)

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        // Server side only, once per second.
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.tickCount % CHECK_INTERVAL != 0) return;

        CommonStructureChecker.checkAndTrigger(player);
    }
}
