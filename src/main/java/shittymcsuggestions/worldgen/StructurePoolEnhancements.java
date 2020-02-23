package shittymcsuggestions.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;
import shittymcsuggestions.mixin.StructurePoolAccessor;

public class StructurePoolEnhancements {

    private static void registerEnhancement(String poolId, StructurePoolElement element, int count) {
        StructurePoolAccessor pool = (StructurePoolAccessor) StructurePoolBasedGenerator.REGISTRY.get(new Identifier(poolId));
        pool.getElements().clear();
        pool.setElementCounts(ImmutableList.of());
        pool.setElementCounts(ImmutableList.<Pair<StructurePoolElement, Integer>>builder()
                .addAll(pool.getElementCounts())
                .add(new Pair<>(element, count))
                .build());
        for (int i = 0; i < count; i++) {
            pool.getElements().add(element.setProjection(pool.getProjection()));
        }
    }

    @SuppressWarnings("deprecation")
    public static void register() {
        PlainsVillageData.initialize();
        SnowyVillageData.initialize();
        SavannaVillageData.initialize();
        DesertVillageData.initialize();
        TaigaVillageData.initialize();

        registerEnhancement("village/plains/houses", new SinglePoolElement("shittymcsuggestions:mcdonalds"), 10);
        registerEnhancement("village/snowy/houses", new SinglePoolElement("shittymcsuggestions:mcdonalds"), 10);
        registerEnhancement("village/savanna/houses", new SinglePoolElement("shittymcsuggestions:mcdonalds"), 10);
        registerEnhancement("village/desert/houses", new SinglePoolElement("shittymcsuggestions:mcdonalds"), 10);
        registerEnhancement("village/taiga/houses", new SinglePoolElement("shittymcsuggestions:mcdonalds"), 10);
    }

}
