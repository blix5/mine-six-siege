package com.blix.sixsiege.sound;

import com.blix.sixsiege.SixSiege;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static SoundEvent L85A2_SHOOT = registerSoundEvent("l85a2_shoot");
    public static SoundEvent L85A2_RELOAD = registerSoundEvent("l85a2_reload");

    public static SoundEvent BARRICADE_BREAK = registerSoundEvent("barricade_break");
    public static SoundEvent BARRICADE_PLACE = registerSoundEvent("barricade_place");
    public static SoundEvent BARRICADE_HIT = registerSoundEvent("barricade_hit");

    public static final BlockSoundGroup BARRICADE_SOUNDS = new BlockSoundGroup(1f, 1f,
            ModSounds.BARRICADE_BREAK, SoundEvents.BLOCK_WOOD_STEP, ModSounds.BARRICADE_PLACE,
            ModSounds.BARRICADE_HIT, SoundEvents.BLOCK_WOOD_FALL);

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(SixSiege.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

}
