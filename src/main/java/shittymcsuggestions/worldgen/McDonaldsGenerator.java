package shittymcsuggestions.worldgen;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import shittymcsuggestions.ShittyMinecraftSuggestions;
import shittymcsuggestions.mixin.accessor.LootableContainerAccessor;

import java.util.List;
import java.util.Random;

public class McDonaldsGenerator {

    private static final Identifier MCDONALDS_TEMPLATE = new Identifier(ShittyMinecraftSuggestions.MODID, "mcdonalds");

    public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces) {
        pieces.add(new Piece(manager, pos, rotation));
    }

    public static class Piece extends SimpleStructurePiece {

        private final BlockRotation rotation;

        public Piece(StructureManager structureManager, BlockPos pos, BlockRotation rotation) {
            super(ModStructures.MCDONALDS_PIECE, 0);
            this.pos = pos;
            this.rotation = rotation;
            initializeStructureData(structureManager);
        }

        public Piece(StructureManager structureManager, CompoundTag tag) {
            super(ModStructures.MCDONALDS_PIECE, tag);
            this.rotation = BlockRotation.valueOf(tag.getString("Rot"));
            initializeStructureData(structureManager);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putString("Rot", rotation.name());
        }

        private void initializeStructureData(StructureManager structureManager) {
            Structure structure = structureManager.getStructureOrBlank(MCDONALDS_TEMPLATE);
            StructurePlacementData placement = createPlacementData();
            setStructureData(structure, pos, placement);
        }

        @Override
        protected void handleMetadata(String metadata, BlockPos pos, IWorld world, Random random, BlockBox boundingBox) {
            if (metadata.startsWith("mcd_chest")) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity be = world.getBlockEntity(pos.down());
                if (be instanceof ChestBlockEntity) {
                    ChestBlockEntity chest = (ChestBlockEntity) be;
                    chest.setLootTable(((LootableContainerAccessor) chest).getLootTableId(), random.nextLong());
                }
            }
        }

        private StructurePlacementData createPlacementData() {
            return new StructurePlacementData().setRotation(rotation).setMirrored(BlockMirror.NONE).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
        }
    }

}
