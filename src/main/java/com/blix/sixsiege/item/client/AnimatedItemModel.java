package com.blix.sixsiege.item.client;

import com.blix.sixsiege.SixSiege;
import com.blix.sixsiege.item.custom.AnimatedItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedItemModel extends GeoModel<AnimatedItem> {

    @Override
    public Identifier getModelResource(AnimatedItem animatable) {
        return new Identifier(SixSiege.MOD_ID, "geo/" + animatable.getLocalpath() + ".geo.json");
    }

    @Override
    public Identifier getTextureResource(AnimatedItem animatable) {
        return new Identifier(SixSiege.MOD_ID, "textures/item/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public Identifier getAnimationResource(AnimatedItem animatable) {
        return new Identifier(SixSiege.MOD_ID, "animations/" + animatable.getLocalpath() + ".animation.json");
    }

}
