package com.p1nero.epicfightbow.gameassets;

import com.p1nero.epicfightbow.EpicFightBowMod;
import com.p1nero.epicfightbow.skills.MortisBowInnateSkill;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;

@Mod.EventBusSubscriber(modid = EpicFightBowMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EFBowSkills {
    public static Skill MORTIS_INNATE;

    @SubscribeEvent
    public static void buildSkills(SkillBuildEvent event) {
        SkillBuildEvent.ModRegistryWorker registryWorker = event.createRegistryWorker(EpicFightBowMod.MOD_ID);
        MORTIS_INNATE = registryWorker.build("mortis_innate", MortisBowInnateSkill::new, MortisBowInnateSkill.createWeaponInnateBuilder()
                .setResource(Skill.Resource.NONE)
                .setActivateType(Skill.ActivateType.TOGGLE));
    }
}
