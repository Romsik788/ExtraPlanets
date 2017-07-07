package com.mjr.extraplanets.planets.Neptune.worldgen;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraftforge.fml.common.FMLLog;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.planets.Neptune.worldgen.village.StructureComponentVillageField;
import com.mjr.extraplanets.planets.Neptune.worldgen.village.StructureComponentVillageField2;
import com.mjr.extraplanets.planets.Neptune.worldgen.village.StructureComponentVillageHouse;
import com.mjr.extraplanets.planets.Neptune.worldgen.village.StructureComponentVillagePathGen;
import com.mjr.extraplanets.planets.Neptune.worldgen.village.StructureComponentVillageRoadPiece;
import com.mjr.extraplanets.planets.Neptune.worldgen.village.StructureComponentVillageStartPiece;
import com.mjr.extraplanets.planets.Neptune.worldgen.village.StructureComponentVillageTorch;
import com.mjr.extraplanets.planets.Neptune.worldgen.village.StructureComponentVillageWoodHut;
import com.mjr.extraplanets.planets.Neptune.worldgen.village.StructureVillageStartNeptune;

public class MapGenVillageNeptune extends MapGenStructure {
	public static List<Biome> villageSpawnBiomes = Arrays.asList(new Biome[] { BiomeGenNeptune.neptune });
	private final int terrainType;
	private static boolean initialized;

	static {
		try {
			MapGenVillageNeptune.initiateStructures();
		} catch (Throwable e) {

		}
	}

	public static void initiateStructures() throws Throwable {
		if (!MapGenVillageNeptune.initialized) {
			MapGenStructureIO.registerStructure(StructureVillageStartNeptune.class, "NeptuneVillage");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageField.class, "NeptuneField1");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageField2.class, "NeptuneField2");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageHouse.class, "NeptuneHouse");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageRoadPiece.class, "NeptuneRoadPiece");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillagePathGen.class, "NeptunePath");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageTorch.class, "NeptuneTorch");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageStartPiece.class, "NeptuneWell");
			MapGenStructureIO.registerStructureComponent(StructureComponentVillageWoodHut.class, "NeptuneWoodHut");
		}

		MapGenVillageNeptune.initialized = true;
	}

	public MapGenVillageNeptune() {
		this.terrainType = 0;
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int i, int j) {
		final byte numChunks = 32;
		final byte offsetChunks = 8;
		final int oldi = i;
		final int oldj = j;

		if (i < 0) {
			i -= numChunks - 1;
		}

		if (j < 0) {
			j -= numChunks - 1;
		}

		int randX = i / numChunks;
		int randZ = j / numChunks;
		final Random var7 = this.world.setRandomSeed(i, j, 10387312);
		randX *= numChunks;
		randZ *= numChunks;
		randX += var7.nextInt(numChunks - offsetChunks);
		randZ += var7.nextInt(numChunks - offsetChunks);

		return oldi == randX && oldj == randZ;

	}
	
    @Override
    public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos, boolean p_180706_3_)
    {
        this.world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, 32, 8, 10387312, false, 100, p_180706_3_);
    }

	@Override
	protected StructureStart getStructureStart(int par1, int par2) {
		if (Config.DEBUG_MODE)
			FMLLog.info("Generating Neptune Village at x" + par1 * 16 + " z" + par2 * 16);
		return new StructureVillageStartNeptune(this.world, this.rand, par1, par2, this.terrainType);
	}

	@Override
	public String getStructureName() {
		return "NeptuneVillage";
	}
}