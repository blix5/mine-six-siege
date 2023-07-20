package com.blix.sixsiege.item;

import com.blix.sixsiege.SixSiege;
import com.blix.sixsiege.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static ItemGroup R6S_PRIMARY = Registry.register(Registries.ITEM_GROUP, new Identifier(SixSiege.MOD_ID, "r6s_primary"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.r6s_primary"))
                    .icon(() -> new ItemStack(ModItems.L85A2)).entries((displayContext, entries) -> {
                        //entries.add(ModItems.M59081);
                        entries.add(ModItems.L85A2);
                        /*entries.add(ModItems.AR33);
                        entries.add(ModItems.G36C);
                        entries.add(ModItems.R4_C);
                        entries.add(ModItems.M1014);
                        entries.add(ModItems.P556XI);
                        entries.add(ModItems.F2);
                        entries.add(ModItems.P417);
                        entries.add(ModItems.SG_CQB);
                        entries.add(ModItems.LE_ROC_SHIELD);
                        entries.add(ModItems.OTS_03);
                        entries.add(ModItems.BALLISTIC_SHIELD);
                        entries.add(ModItems.P6P41);
                        entries.add(ModItems.AK_12);
                        entries.add(ModItems.G52_TACTICAL_SHIELD);
                        entries.add(ModItems.AUG_A2);
                        entries.add(ModItems.P552_COMMANDO);
                        entries.add(ModItems.G8A1);
                        entries.add(ModItems.C8_SFW);
                        entries.add(ModItems.CAMRS);
                        entries.add(ModItems.MK17_CQB);
                        entries.add(ModItems.SR_25);
                        entries.add(ModItems.PARA_308);
                        entries.add(ModItems.M249);
                        entries.add(ModItems.TYPE_89);
                        entries.add(ModItems.SUPERNOVA);
                        entries.add(ModItems.C7E);
                        entries.add(ModItems.PDW9);
                        entries.add(ModItems.ITA12L);
                        entries.add(ModItems.T_95_LSW);
                        entries.add(ModItems.SIX12);
                        entries.add(ModItems.LMG_E);
                        entries.add(ModItems.M762);
                        entries.add(ModItems.MK_14_EBR);
                        entries.add(ModItems.BOSG_12_2);
                        entries.add(ModItems.V308);
                        entries.add(ModItems.SPEAR__308);
                        entries.add(ModItems.SASG_12);
                        entries.add(ModItems.AR_15_50);
                        entries.add(ModItems.M4);
                        entries.add(ModItems.AK_74M);
                        entries.add(ModItems.ARX200);
                        entries.add(ModItems.F90);
                        entries.add(ModItems.M249_SAW);
                        entries.add(ModItems.FMG_9);
                        entries.add(ModItems.SIX12_SD);
                        entries.add(ModItems.CSRX_300);
                        entries.add(ModItems.SC3000K);
                        entries.add(ModItems.MP7);
                        entries.add(ModItems.POF_9);
                        entries.add(ModItems.MP5K);
                        entries.add(ModItems.UMP45);
                        entries.add(ModItems.MP5);
                        entries.add(ModItems.P90);
                        entries.add(ModItems.P9X19VSN);
                        entries.add(ModItems.DP27);
                        entries.add(ModItems.M870);
                        entries.add(ModItems.P416_C_CARBINE);
                        entries.add(ModItems.SUPER_90);
                        entries.add(ModItems.P9MM_C1);
                        entries.add(ModItems.MPX);
                        entries.add(ModItems.SPAS_12);
                        entries.add(ModItems.M12);
                        entries.add(ModItems.SPAS_15);
                        entries.add(ModItems.MP5SD);
                        entries.add(ModItems.VECTOR__45_ACP);
                        entries.add(ModItems.T_5_SMG);
                        entries.add(ModItems.SCORPION_EVO_3_A1);
                        entries.add(ModItems.FO_12);
                        entries.add(ModItems.K1A);
                        entries.add(ModItems.ALDA_5_56);
                        entries.add(ModItems.ACS12);
                        entries.add(ModItems.MX4_STORM);
                        entries.add(ModItems.CCE_SHIELD);
                        entries.add(ModItems.AUG_A3);
                        entries.add(ModItems.TCSG12);
                        entries.add(ModItems.COMMANDO_9);
                        entries.add(ModItems.P10_RONI);
                        entries.add(ModItems.UZK50GI);*/
                    }).build());

    public static ItemGroup R6S_SECONDARY = Registry.register(Registries.ITEM_GROUP, new Identifier(SixSiege.MOD_ID, "r6s_secondary"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.r6s_secondary"))
                    .icon(() -> new ItemStack(ModItems.P226_MK_25)).entries((displayContext, entries) -> {
                        /*entries.add(ModItems.P226_MK_25);
                        entries.add(ModItems.M45_MEUSOC);
                        entries.add(ModItems.S5_7_USG);
                        entries.add(ModItems.P9);
                        entries.add(ModItems.LFP586);
                        entries.add(ModItems.PMM);
                        entries.add(ModItems.GONNE_6);
                        entries.add(ModItems.BEARING_9);
                        entries.add(ModItems.GSH_18);
                        entries.add(ModItems.P12);
                        entries.add(ModItems.MK1_9MM);
                        entries.add(ModItems.D_50);
                        entries.add(ModItems.PRB92);
                        entries.add(ModItems.P229);
                        entries.add(ModItems.USP40);
                        entries.add(ModItems.ITA12S);
                        entries.add(ModItems.Q_929);
                        entries.add(ModItems.RG15);
                        entries.add(ModItems.SMG_12);
                        entries.add(ModItems.C75_AUTO);
                        entries.add(ModItems.S1911_TACOPS);
                        entries.add(ModItems._44_MAG_SEMI_AUTO);
                        entries.add(ModItems.SUPER_SHORTY);
                        entries.add(ModItems.SDP_9MM);
                        entries.add(ModItems.SMG_11);
                        entries.add(ModItems.SPSMG9);
                        entries.add(ModItems.BAILIFF_410);
                        entries.add(ModItems.LUISON);
                        entries.add(ModItems.KERATOS__357);
                        entries.add(ModItems.P_10C);*/
                    }).build());

    public static ItemGroup R6S_MISC = Registry.register(Registries.ITEM_GROUP, new Identifier(SixSiege.MOD_ID, "r6s_misc"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.r6s_misc"))
                    .icon(() -> new ItemStack(ModBlocks.BARRICADE_SINGLE)).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.BARRICADE_SINGLE);
                        entries.add(ModBlocks.BARRICADE_MIDDLE);
                        entries.add(ModBlocks.BARRICADE_LEFT);
                        entries.add(ModBlocks.BARRICADE_RIGHT);
                        entries.add(ModItems.KNIFE);
                    }).build());

    public static void registerItemGroups() {

    }

}
