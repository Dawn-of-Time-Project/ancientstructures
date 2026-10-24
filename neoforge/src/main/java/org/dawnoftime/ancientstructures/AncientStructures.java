package org.dawnoftime.ancientstructures;

import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.dawnoftime.ancientstructures.advancement.StructureDiscoveryTrigger;

@Mod(Constants.MOD_ID)
public class AncientStructures {

    private static final DeferredRegister<CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(Registries.TRIGGER_TYPE, Constants.MOD_ID);

    static {
        TRIGGERS.register("structure_discovery", () -> StructureDiscoveryTrigger.INSTANCE);
    }

    public AncientStructures(IEventBus modEventBus) {
        TRIGGERS.register(modEventBus);
    }
}
