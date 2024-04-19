package dev.fouriiiis.threatmusicmod.mixin.client;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import dev.fouriiiis.threatmusicmod.CustomMobEntity;



@Mixin(LivingEntity.class)
public abstract class MobEntityMixin implements CustomMobEntity {

    //list of classes that have exceptions

    private List<Class<? extends MobEntity>> exceptions = List.of(
    PiglinEntity.class,
    SpiderEntity.class
);



    @Override
    public float getBaseThreat() {
        Float totalHP = (((LivingEntity) (Object) this).getMaxHealth() + ((LivingEntity) (Object) this).getArmor());
        float base = (float) (Math.log10((1f/6f)*(totalHP + 6f))/1.4f);
        return MathHelper.clamp(base, 0, 1);
    }

    @Override
    public float getAgro() {

        if ((((LivingEntity) (Object) this) instanceof Monster) && !(((LivingEntity) (Object) this) instanceof Angerable) && !exceptions.contains(this.getClass()) || (((LivingEntity) (Object) this) instanceof PlayerEntity)) {
            //System.out.println("Hostile mob");
            return 1.0f;
        } else if (((MobEntity) (Object) this).isAttacking()) {
            //System.out.println("Agro");
            return 1.0f;
        }
        //System.out.println("Not agro");
        return 0.0f;
    }
}
