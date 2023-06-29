package com.blix.sixsiege.item;

import com.blix.sixsiege.SixSiege;
import com.blix.sixsiege.item.custom.AnimatedItem;
import com.blix.sixsiege.sound.ModSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    //Primaries
    public static final Item M59081 = registerItem("m59081",
            new Item(new FabricItemSettings()));
    public static final Item L85A2 = registerItem("l85a2",
            new AnimatedItem(new FabricItemSettings().maxCount(1), "l85a2",
                    47, 29, 25, 2, 48, 31,
                    ModSounds.L85A2_SHOOT, ModSounds.L85A2_RELOAD));
    public static final Item AR33 = registerItem("ar33",
            new Item(new FabricItemSettings()));
    public static final Item G36C = registerItem("g36c",
            new Item(new FabricItemSettings()));
    public static final Item R4_C = registerItem("r4_c",
            new Item(new FabricItemSettings()));
    public static final Item M1014 = registerItem("m1014",
            new Item(new FabricItemSettings()));
    public static final Item P556XI = registerItem("556xi",
            new Item(new FabricItemSettings()));
    public static final Item F2 = registerItem("f2",
            new Item(new FabricItemSettings()));
    public static final Item P417 = registerItem("417",
            new Item(new FabricItemSettings()));
    public static final Item SG_CQB = registerItem("sg_cqb",
            new Item(new FabricItemSettings()));
    public static final Item LE_ROC_SHIELD = registerItem("le_roc_shield",
            new Item(new FabricItemSettings()));
    public static final Item OTS_03 = registerItem("ots_03",
            new Item(new FabricItemSettings()));
    public static final Item BALLISTIC_SHIELD = registerItem("ballistic_shield",
            new Item(new FabricItemSettings()));
    public static final Item P6P41 = registerItem("6p41",
            new Item(new FabricItemSettings()));
    public static final Item AK_12 = registerItem("ak_12",
            new Item(new FabricItemSettings()));
    public static final Item G52_TACTICAL_SHIELD = registerItem("g52_tactical_shield",
            new Item(new FabricItemSettings()));
    public static final Item AUG_A2 = registerItem("aug_a2",
            new Item(new FabricItemSettings()));
    public static final Item P552_COMMANDO = registerItem("552_commando",
            new Item(new FabricItemSettings()));
    public static final Item G8A1 = registerItem("g8a1",
            new Item(new FabricItemSettings()));
    public static final Item C8_SFW = registerItem("c8_sfw",
            new Item(new FabricItemSettings()));
    public static final Item CAMRS = registerItem("camrs",
            new Item(new FabricItemSettings()));
    public static final Item MK17_CQB = registerItem("mk17_cqb",
            new Item(new FabricItemSettings()));
    public static final Item SR_25 = registerItem("sr_25",
            new Item(new FabricItemSettings()));
    public static final Item PARA_308 = registerItem("para_308",
            new Item(new FabricItemSettings()));
    public static final Item M249 = registerItem("m249",
            new Item(new FabricItemSettings()));
    public static final Item TYPE_89 = registerItem("type_89",
            new Item(new FabricItemSettings()));
    public static final Item SUPERNOVA = registerItem("supernova",
            new Item(new FabricItemSettings()));
    public static final Item C7E = registerItem("c73",
            new Item(new FabricItemSettings()));
    public static final Item PDW9 = registerItem("pdw9",
            new Item(new FabricItemSettings()));
    public static final Item ITA12L = registerItem("ita12l",
            new Item(new FabricItemSettings()));
    public static final Item T_95_LSW = registerItem("t_95_lsw",
            new Item(new FabricItemSettings()));
    public static final Item SIX12 = registerItem("six12",
            new Item(new FabricItemSettings()));
    public static final Item LMG_E = registerItem("lmg_e",
            new Item(new FabricItemSettings()));
    public static final Item M762 = registerItem("m762",
            new Item(new FabricItemSettings()));
    public static final Item MK_14_EBR = registerItem("mk_14_ebr",
            new Item(new FabricItemSettings()));
    public static final Item BOSG_12_2 = registerItem("bosg_12_2",
            new Item(new FabricItemSettings()));
    public static final Item V308 = registerItem("v308",
            new Item(new FabricItemSettings()));
    public static final Item SPEAR__308 = registerItem("spear__308",
            new Item(new FabricItemSettings()));
    public static final Item SASG_12 = registerItem("sasg_12",
            new Item(new FabricItemSettings()));
    public static final Item AR_15_50 = registerItem("ar_15_50",
            new Item(new FabricItemSettings()));
    public static final Item M4 = registerItem("m4",
            new Item(new FabricItemSettings()));
    public static final Item AK_74M = registerItem("ak_74m",
            new Item(new FabricItemSettings()));
    public static final Item ARX200 = registerItem("arx200",
            new Item(new FabricItemSettings()));
    public static final Item F90 = registerItem("f90",
            new Item(new FabricItemSettings()));
    public static final Item M249_SAW = registerItem("m249_saw",
            new Item(new FabricItemSettings()));
    public static final Item FMG_9 = registerItem("fmg_9",
            new Item(new FabricItemSettings()));
    public static final Item SIX12_SD = registerItem("six12_sd",
            new Item(new FabricItemSettings()));
    public static final Item CSRX_300 = registerItem("csrx_300",
            new Item(new FabricItemSettings()));
    public static final Item SC3000K = registerItem("sc3000k",
            new Item(new FabricItemSettings()));
    public static final Item MP7 = registerItem("mp7",
            new Item(new FabricItemSettings()));
    public static final Item POF_9 = registerItem("pof_9",
            new Item(new FabricItemSettings()));
    public static final Item MP5K = registerItem("mp5k",
            new Item(new FabricItemSettings()));
    public static final Item UMP45 = registerItem("ump45",
            new Item(new FabricItemSettings()));
    public static final Item MP5 = registerItem("mp5",
            new Item(new FabricItemSettings()));
    public static final Item P90 = registerItem("p90",
            new Item(new FabricItemSettings()));
    public static final Item P9X19VSN = registerItem("9x19vsn",
            new Item(new FabricItemSettings()));
    public static final Item DP27 = registerItem("dp27",
            new Item(new FabricItemSettings()));
    public static final Item M870 = registerItem("m870",
            new Item(new FabricItemSettings()));
    public static final Item P416_C_CARBINE = registerItem("416_c_carbine",
            new Item(new FabricItemSettings()));
    public static final Item SUPER_90 = registerItem("super_90",
            new Item(new FabricItemSettings()));
    public static final Item P9MM_C1 = registerItem("9mm_c1",
            new Item(new FabricItemSettings()));
    public static final Item MPX = registerItem("mpx",
            new Item(new FabricItemSettings()));
    public static final Item SPAS_12 = registerItem("spas_12",
            new Item(new FabricItemSettings()));
    public static final Item M12 = registerItem("m12",
            new Item(new FabricItemSettings()));
    public static final Item SPAS_15 = registerItem("spas_15",
            new Item(new FabricItemSettings()));
    public static final Item MP5SD = registerItem("mp5sd",
            new Item(new FabricItemSettings()));
    public static final Item VECTOR__45_ACP = registerItem("vector__45_acp",
            new Item(new FabricItemSettings()));
    public static final Item T_5_SMG = registerItem("t_5_smg",
            new Item(new FabricItemSettings()));
    public static final Item SCORPION_EVO_3_A1 = registerItem("scorpion_evo_3_a1",
            new Item(new FabricItemSettings()));
    public static final Item FO_12 = registerItem("fo_12",
            new Item(new FabricItemSettings()));
    public static final Item K1A = registerItem("k1a",
            new Item(new FabricItemSettings()));
    public static final Item ALDA_5_56 = registerItem("alda_5_56",
            new Item(new FabricItemSettings()));
    public static final Item ACS12 = registerItem("acs12",
            new Item(new FabricItemSettings()));
    public static final Item MX4_STORM = registerItem("mx4_storm",
            new Item(new FabricItemSettings()));
    public static final Item CCE_SHIELD = registerItem("cce_shield",
            new Item(new FabricItemSettings()));
    public static final Item AUG_A3 = registerItem("aug_a3",
            new Item(new FabricItemSettings()));
    public static final Item TCSG12 = registerItem("tcsg12",
            new Item(new FabricItemSettings()));
    public static final Item COMMANDO_9 = registerItem("commando_9",
            new Item(new FabricItemSettings()));
    public static final Item P10_RONI = registerItem("p10_roni",
            new Item(new FabricItemSettings()));
    public static final Item UZK50GI = registerItem("uzk50gi",
            new Item(new FabricItemSettings()));

    //Secondaries
    public static final Item P226_MK_25 = registerItem("p226_mk_25",
            new Item(new FabricItemSettings()));
    public static final Item M45_MEUSOC = registerItem("m45_meusoc",
            new Item(new FabricItemSettings()));
    public static final Item S5_7_USG = registerItem("5_7_usg",
            new Item(new FabricItemSettings()));
    public static final Item P9 = registerItem("p9",
            new Item(new FabricItemSettings()));
    public static final Item LFP586 = registerItem("lfp586",
            new Item(new FabricItemSettings()));
    public static final Item PMM = registerItem("pmm",
            new Item(new FabricItemSettings()));
    public static final Item GONNE_6 = registerItem("gonne_6",
            new Item(new FabricItemSettings()));
    public static final Item BEARING_9 = registerItem("bearing_9",
            new Item(new FabricItemSettings()));
    public static final Item GSH_18 = registerItem("gsh_18",
            new Item(new FabricItemSettings()));
    public static final Item P12 = registerItem("p12",
            new Item(new FabricItemSettings()));
    public static final Item MK1_9MM = registerItem("mk1_9mm",
            new Item(new FabricItemSettings()));
    public static final Item D_50 = registerItem("d_50",
            new Item(new FabricItemSettings()));
    public static final Item PRB92 = registerItem("prb92",
            new Item(new FabricItemSettings()));
    public static final Item P229 = registerItem("p229",
            new Item(new FabricItemSettings()));
    public static final Item USP40 = registerItem("usp40",
            new Item(new FabricItemSettings()));
    public static final Item ITA12S = registerItem("ita12s",
            new Item(new FabricItemSettings()));
    public static final Item Q_929 = registerItem("q_929",
            new Item(new FabricItemSettings()));
    public static final Item RG15 = registerItem("rg15",
            new Item(new FabricItemSettings()));
    public static final Item SMG_12 = registerItem("smg_12",
            new Item(new FabricItemSettings()));
    public static final Item C75_AUTO = registerItem("c75_auto",
            new Item(new FabricItemSettings()));
    public static final Item S1911_TACOPS = registerItem("1911_tacops",
            new Item(new FabricItemSettings()));
    public static final Item _44_MAG_SEMI_AUTO = registerItem("_44_mag_semi_auto",
            new Item(new FabricItemSettings()));
    public static final Item SUPER_SHORTY = registerItem("super_shorty",
            new Item(new FabricItemSettings()));
    public static final Item SDP_9MM = registerItem("sdp_9mm",
            new Item(new FabricItemSettings()));
    public static final Item SMG_11 = registerItem("smg_11",
            new Item(new FabricItemSettings()));
    public static final Item SPSMG9 = registerItem("spsmg9",
            new Item(new FabricItemSettings()));
    public static final Item BAILIFF_410 = registerItem("bailiff_410",
            new Item(new FabricItemSettings()));
    public static final Item LUISON = registerItem("luison",
            new Item(new FabricItemSettings()));
    public static final Item KERATOS__357 = registerItem("keratos__357",
            new Item(new FabricItemSettings()));
    public static final Item P_10C = registerItem("p-10c",
            new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(SixSiege.MOD_ID, name), item);
    }

    public static void registerModItems() {
        SixSiege.LOGGER.info("Registering Mod Items for " + SixSiege.MOD_ID);
    }

}
