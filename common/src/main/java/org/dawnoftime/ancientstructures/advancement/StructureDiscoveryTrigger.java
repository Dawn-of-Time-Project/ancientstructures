package org.dawnoftime.ancientstructures.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

/**
 * Custom criterion trigger that fires when a player physically enters
 * one of the mod's structures. This bypasses the broken LocationPredicate
 * holder resolution in NeoForge for dynamic-registry structures.
 *
 * <p>Advancement JSON usage:
 * <pre>{@code
 * "my_criterion": {
 *   "trigger": "ancientstructures:structure_discovery",
 *   "conditions": {
 *     "structure": "ancientstructures:german_farm"
 *   }
 * }
 * }</pre>
 */
public class StructureDiscoveryTrigger extends SimpleCriterionTrigger<StructureDiscoveryTrigger.TriggerInstance> {

    public static final StructureDiscoveryTrigger INSTANCE = new StructureDiscoveryTrigger();
    public static final Identifier ID = Identifier.fromNamespaceAndPath("ancientstructures", "structure_discovery");

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    /**
     * Call this from an event handler whenever a player is detected inside a structure.
     *
     * @param player      the server-side player
     * @param structureId the namespaced ID of the structure (e.g. "ancientstructures:german_farm")
     */
    public void trigger(ServerPlayer player, Identifier structureId) {
        this.trigger(player, instance -> instance.matches(structureId));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Identifier structure)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                Identifier.CODEC.fieldOf("structure").forGetter(TriggerInstance::structure)
        ).apply(instance, TriggerInstance::new));

        public boolean matches(Identifier structureId) {
            return this.structure.equals(structureId);
        }
    }
}
