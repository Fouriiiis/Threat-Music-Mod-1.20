package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.mixin.client.GetBossBarsMixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.thread.MessageListener;
import net.minecraft.world.biome.Biome;




public class ModSounds {

    // Define the Region outside of the registerSounds() method so it can be accessed globally.
    

    //current region
    public static Region currentRegion;

    //map of regions indexed by name
    public static Map<String, Region> regions = new HashMap<String, Region>();

    //map of regions indexed by list of biomes
    public static Map<List<String>, Region> biomeRegions = new HashMap<List<String>, Region>();


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier("modid", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        SoundEvent th_cc_arps = registerSoundEvent("th_cc_arps");
        SoundEvent th_cc_bass = registerSoundEvent("th_cc_bass");
        SoundEvent th_cc_gutterbass = registerSoundEvent("th_cc_gutterbass");
        SoundEvent th_cc_guttervox = registerSoundEvent("th_cc_guttervox");
        SoundEvent th_cc_kick = registerSoundEvent("th_cc_kick");
        SoundEvent th_cc_noise = registerSoundEvent("th_cc_noise");
        SoundEvent th_cc_perc1 = registerSoundEvent("th_cc_perc1");
        SoundEvent th_cc_perc2 = registerSoundEvent("th_cc_perc2");
        SoundEvent th_cc_snare = registerSoundEvent("th_cc_snare");
        SoundEvent th_cc_vox = registerSoundEvent("th_cc_vox");
        SoundEvent th_dm_bass = registerSoundEvent("th_dm_bass");
        SoundEvent th_dm_kick = registerSoundEvent("th_dm_kick");
        SoundEvent th_dm_lead = registerSoundEvent("th_dm_lead");
        SoundEvent th_dm_noise = registerSoundEvent("th_dm_noise");
        SoundEvent th_dm_shaker = registerSoundEvent("th_dm_shaker");
        SoundEvent th_dm_snare = registerSoundEvent("th_dm_snare");
        SoundEvent th_gw_bass = registerSoundEvent("th_gw_bass");
        SoundEvent th_gw_kick = registerSoundEvent("th_gw_kick");
        SoundEvent th_gw_lead = registerSoundEvent("th_gw_lead");
        SoundEvent th_gw_noise = registerSoundEvent("th_gw_noise");
        SoundEvent th_gw_perc1 = registerSoundEvent("th_gw_perc1");
        SoundEvent th_gw_perc2 = registerSoundEvent("th_gw_perc2");
        SoundEvent th_gw_shake = registerSoundEvent("th_gw_shake");
        SoundEvent th_gw_vox = registerSoundEvent("th_gw_vox");
        SoundEvent th_gw_weird = registerSoundEvent("th_gw_weird");
        SoundEvent th_hi_bass = registerSoundEvent("th_hi_bass");
        SoundEvent th_hi_kick = registerSoundEvent("th_hi_kick");
        SoundEvent th_hi_noise = registerSoundEvent("th_hi_noise");
        SoundEvent th_hi_perc1 = registerSoundEvent("th_hi_perc1");
        SoundEvent th_hi_render = registerSoundEvent("th_hi_render");
        SoundEvent th_hi_shaker = registerSoundEvent("th_hi_shaker");
        SoundEvent th_hi_snare = registerSoundEvent("th_hi_snare");
        SoundEvent th_hi_vox = registerSoundEvent("th_hi_vox");
        SoundEvent th_hi_weird = registerSoundEvent("th_hi_weird");
        SoundEvent th_hr_bass = registerSoundEvent("th_hr_bass");
        SoundEvent th_hr_hat1 = registerSoundEvent("th_hr_hat1");
        SoundEvent th_hr_hat2 = registerSoundEvent("th_hr_hat2");
        SoundEvent th_hr_kick = registerSoundEvent("th_hr_kick");
        SoundEvent th_hr_lead = registerSoundEvent("th_hr_lead");
        SoundEvent th_hr_noise = registerSoundEvent("th_hr_noise");
        SoundEvent th_hr_pad = registerSoundEvent("th_hr_pad");
        SoundEvent th_hr_perc = registerSoundEvent("th_hr_perc");
        SoundEvent th_hr_snare = registerSoundEvent("th_hr_snare");
        SoundEvent th_hr_weird = registerSoundEvent("th_hr_weird");
        SoundEvent th_lc_dayarp = registerSoundEvent("th_lc_dayarp");
        SoundEvent th_lc_dayatmos = registerSoundEvent("th_lc_dayatmos");
        SoundEvent th_lc_daybreaks = registerSoundEvent("th_lc_daybreaks");
        SoundEvent th_lc_daykick = registerSoundEvent("th_lc_daykick");
        SoundEvent th_lc_dayperc = registerSoundEvent("th_lc_dayperc");
        SoundEvent th_lc_dayshaker = registerSoundEvent("th_lc_dayshaker");
        SoundEvent th_lc_daysub = registerSoundEvent("th_lc_daysub");
        SoundEvent th_lc_daysynth = registerSoundEvent("th_lc_daysynth");
        SoundEvent th_lc_daytom = registerSoundEvent("th_lc_daytom");
        SoundEvent th_lc_nightatmos = registerSoundEvent("th_lc_nightatmos");
        SoundEvent th_lc_nighthat = registerSoundEvent("th_lc_nighthat");
        SoundEvent th_lc_nightkick = registerSoundEvent("th_lc_nightkick");
        SoundEvent th_lc_nightnoise = registerSoundEvent("th_lc_nightnoise");
        SoundEvent th_lc_nightperc = registerSoundEvent("th_lc_nightperc");
        SoundEvent th_lc_nightshaker = registerSoundEvent("th_lc_nightshaker");
        SoundEvent th_lc_nightsub = registerSoundEvent("th_lc_nightsub");
        SoundEvent th_lc_nightsynth = registerSoundEvent("th_lc_nightsynth");
        SoundEvent th_lc_nighttom = registerSoundEvent("th_lc_nighttom");
        SoundEvent th_lf_arps = registerSoundEvent("th_lf_arps");
        SoundEvent th_lf_bass = registerSoundEvent("th_lf_bass");
        SoundEvent th_lf_chords = registerSoundEvent("th_lf_chords");
        SoundEvent th_lf_kick = registerSoundEvent("th_lf_kick");
        SoundEvent th_lf_noise = registerSoundEvent("th_lf_noise");
        SoundEvent th_lf_perc1 = registerSoundEvent("th_lf_perc1");
        SoundEvent th_lf_perc2 = registerSoundEvent("th_lf_perc2");
        SoundEvent th_lf_shaker = registerSoundEvent("th_lf_shaker");
        SoundEvent th_lf_snare = registerSoundEvent("th_lf_snare");
        SoundEvent th_lm_arps = registerSoundEvent("th_lm_arps");
        SoundEvent th_lm_bass = registerSoundEvent("th_lm_bass");
        SoundEvent th_lm_kick = registerSoundEvent("th_lm_kick");
        SoundEvent th_lm_noise = registerSoundEvent("th_lm_noise");
        SoundEvent th_lm_pad = registerSoundEvent("th_lm_pad");
        SoundEvent th_lm_perc1 = registerSoundEvent("th_lm_perc1");
        SoundEvent th_lm_perc2 = registerSoundEvent("th_lm_perc2");
        SoundEvent th_lm_snare = registerSoundEvent("th_lm_snare");
        SoundEvent th_lm_weird = registerSoundEvent("th_lm_weird");
        SoundEvent th_oe_arp = registerSoundEvent("th_oe_arp");
        SoundEvent th_oe_bass = registerSoundEvent("th_oe_bass");
        SoundEvent th_oe_flow = registerSoundEvent("th_oe_flow");
        SoundEvent th_oe_kickperc = registerSoundEvent("th_oe_kickperc");
        SoundEvent th_oe_lead = registerSoundEvent("th_oe_lead");
        SoundEvent th_oe_noise = registerSoundEvent("th_oe_noise");
        SoundEvent th_oe_perc2 = registerSoundEvent("th_oe_perc2");
        SoundEvent th_oe_waves = registerSoundEvent("th_oe_waves");
        SoundEvent th_si_arps = registerSoundEvent("th_si_arps");
        SoundEvent th_si_bass = registerSoundEvent("th_si_bass");
        SoundEvent th_si_kick = registerSoundEvent("th_si_kick");
        SoundEvent th_si_noise = registerSoundEvent("th_si_noise");
        SoundEvent th_si_panic = registerSoundEvent("th_si_panic");
        SoundEvent th_si_perc1 = registerSoundEvent("th_si_perc1");
        SoundEvent th_si_shaker = registerSoundEvent("th_si_shaker");
        SoundEvent th_si_snare = registerSoundEvent("th_si_snare");
        SoundEvent th_si_weird = registerSoundEvent("th_si_weird");
        SoundEvent th_sl_arps = registerSoundEvent("th_sl_arps");
        SoundEvent th_sl_bass = registerSoundEvent("th_sl_bass");
        SoundEvent th_sl_kick = registerSoundEvent("th_sl_kick");
        SoundEvent th_sl_lead = registerSoundEvent("th_sl_lead");
        SoundEvent th_sl_noise = registerSoundEvent("th_sl_noise");
        SoundEvent th_sl_perc1 = registerSoundEvent("th_sl_perc1");
        SoundEvent th_sl_perc2 = registerSoundEvent("th_sl_perc2");
        SoundEvent th_sl_snare = registerSoundEvent("th_sl_snare");
        SoundEvent th_ss_bass = registerSoundEvent("th_ss_bass");
        SoundEvent th_ss_kick = registerSoundEvent("th_ss_kick");
        SoundEvent th_ss_lead = registerSoundEvent("th_ss_lead");
        SoundEvent th_ss_noise = registerSoundEvent("th_ss_noise");
        SoundEvent th_ss_pop = registerSoundEvent("th_ss_pop");
        SoundEvent th_su_arps = registerSoundEvent("th_su_arps");
        SoundEvent th_su_bass = registerSoundEvent("th_su_bass");
        SoundEvent th_su_hits = registerSoundEvent("th_su_hits");
        SoundEvent th_su_kick = registerSoundEvent("th_su_kick");
        SoundEvent th_su_lead = registerSoundEvent("th_su_lead");
        SoundEvent th_su_noise = registerSoundEvent("th_su_noise");
        SoundEvent th_su_perc1 = registerSoundEvent("th_su_perc1");
        SoundEvent th_su_shaker = registerSoundEvent("th_su_shaker");
        SoundEvent th_vs_arps = registerSoundEvent("th_vs_arps");
        SoundEvent th_vs_bass = registerSoundEvent("th_vs_bass");
        SoundEvent th_vs_kick = registerSoundEvent("th_vs_kick");
        SoundEvent th_vs_noise = registerSoundEvent("th_vs_noise");
        SoundEvent th_vs_perc1 = registerSoundEvent("th_vs_perc1");
        SoundEvent th_vs_perc2 = registerSoundEvent("th_vs_perc2");
        SoundEvent th_vs_shaker = registerSoundEvent("th_vs_shaker");
        SoundEvent th_vs_synth = registerSoundEvent("th_vs_synth");
        SoundEvent th_vs_weird = registerSoundEvent("th_vs_weird");

        //Layer : TH_CC - KICK, TH_CC - PERC1, TH_CC - NOISE
        //Layer : TH_CC - KICK, TH_CC - PERC2, TH_CC - PERC1, TH_CC - NOISE
        //Layer : TH_CC - KICK, TH_CC - PERC2, TH_CC - PERC1, TH_CC - NOISE, TH_CC - SNARE, {Chimney Canopy}TH_CC - BASS, {The Gutter}TH_CC_-_GUTTERBASS, {Chimney Canopy}TH_CC - VOX, {The Gutter}TH_CC_-_GUTTERVOX
        //Layer : TH_CC - KICK, {Chimney Canopy}TH_CC - ARPS, TH_CC - PERC1, TH_CC - NOISE, TH_CC - SNARE, {Chimney Canopy}TH_CC - BASS, {The Gutter}TH_CC_-_GUTTERBASS, TH_CC - PERC2, {Chimney Canopy}TH_CC - VOX, {The Gutter}TH_CC_-_GUTTERVOX
        //Layer : {Chimney Canopy}TH_CC - BASS, {The Gutter}TH_CC_-_GUTTERBASS, {Chimney Canopy}TH_CC - VOX, {The Gutter}TH_CC_-_GUTTERVOX
        regions.put("cc", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_cc_kick);
                    add(th_cc_perc1);
                    add(th_cc_noise);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_cc_kick);
                    add(th_cc_perc2);
                    add(th_cc_perc1);
                    add(th_cc_noise);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_cc_kick);
                    add(th_cc_perc2);
                    add(th_cc_perc1);
                    add(th_cc_noise);
                    add(th_cc_snare);
                    add(th_cc_bass);
                    add(th_cc_vox);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_cc_kick);
                    add(th_cc_arps);
                    add(th_cc_perc1);
                    add(th_cc_noise);
                    add(th_cc_snare);
                    add(th_cc_bass);
                    add(th_cc_perc2);
                    add(th_cc_vox);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_cc_bass);
                    add(th_cc_vox);
                }});
            }}
        ));

        regions.put("ccG", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_cc_kick);
                    add(th_cc_perc1);
                    add(th_cc_noise);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_cc_kick);
                    add(th_cc_perc2);
                    add(th_cc_perc1);
                    add(th_cc_noise);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_cc_kick);
                    add(th_cc_perc2);
                    add(th_cc_perc1);
                    add(th_cc_noise);
                    add(th_cc_snare);
                    add(th_cc_gutterbass);
                    add(th_cc_guttervox);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_cc_kick);
                    add(th_cc_arps);
                    add(th_cc_perc1);
                    add(th_cc_noise);
                    add(th_cc_snare);
                    add(th_cc_gutterbass);
                    add(th_cc_perc2);
                    add(th_cc_guttervox);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_cc_gutterbass);
                    add(th_cc_guttervox);
                }});
            }}
        ));

        //Layer : TH_DM - NOISE, TH_DM - BASS, TH_DM - KICK
        //Layer : TH_DM - NOISE, TH_DM - BASS, TH_DM - KICK, TH_DM - SNARE
        //Layer : TH_DM - NOISE, TH_DM - BASS, TH_DM - KICK, TH_DM - SNARE, TH_DM - LEAD, TH_DM - SHAKER
        //Layer : TH_DM - NOISE, TH_DM - BASS, TH_DM - KICK, TH_DM - SNARE, TH_DM - LEAD, TH_DM - SHAKER
        regions.put("dm", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_dm_noise);
                    add(th_dm_bass);
                    add(th_dm_kick);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_dm_noise);
                    add(th_dm_bass);
                    add(th_dm_kick);
                    add(th_dm_snare);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_dm_noise);
                    add(th_dm_bass);
                    add(th_dm_kick);
                    add(th_dm_snare);
                    add(th_dm_lead);
                    add(th_dm_shaker);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_dm_noise);
                    add(th_dm_bass);
                    add(th_dm_kick);
                    add(th_dm_snare);
                    add(th_dm_lead);
                    add(th_dm_shaker);
                }});
            }}
        ));

        //Layer : TH_GW - KICK, TH_GW - PERC1, TH_GW - SHAKE
        //Layer : TH_GW - KICK, TH_GW - PERC1, TH_GW - SHAKE, TH_GW - BASS
        //Layer : TH_GW - KICK, TH_GW - PERC1, TH_GW - SHAKE, TH_GW - BASS, TH_GW - NOISE, TH_GW - WEIRD, TH_GW - VOX
        //Layer : TH_GW - KICK, TH_GW - PERC1, TH_GW - SHAKE, TH_GW - BASS, TH_GW - NOISE, TH_GW - WEIRD, TH_GW - VOX
        //Layer : TH_GW - LEAD
        regions.put("gw", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_gw_kick);
                    add(th_gw_perc1);
                    add(th_gw_shake);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_gw_kick);
                    add(th_gw_perc1);
                    add(th_gw_shake);
                    add(th_gw_bass);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_gw_kick);
                    add(th_gw_perc1);
                    add(th_gw_shake);
                    add(th_gw_bass);
                    add(th_gw_noise);
                    add(th_gw_weird);
                    add(th_gw_vox);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_gw_kick);
                    add(th_gw_perc1);
                    add(th_gw_shake);
                    add(th_gw_bass);
                    add(th_gw_noise);
                    add(th_gw_weird);
                    add(th_gw_vox);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_gw_lead);
                }});
            }}
        ));

        //Layer : TH_HI - KICK, TH_HI - PERC1, TH_HI - SHAKER
        //Layer : TH_HI - KICK, TH_HI - PERC1, TH_HI - SHAKER, TH_HI - SNARE
        //Layer : TH_HI - KICK, TH_HI - PERC1, TH_HI - SHAKER, TH_HI - SNARE, TH_HI - BASS, TH_HI - WEIRD, TH_HI - NOISE
        //Layer : TH_HI - KICK, TH_HI - PERC1, TH_HI - SHAKER, TH_HI - SNARE, TH_HI - BASS, TH_HI - WEIRD, TH_HI - NOISE
        //Layer : TH_HI - VOX
        regions.put("hi", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_hi_kick);
                    add(th_hi_perc1);
                    add(th_hi_shaker);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_hi_kick);
                    add(th_hi_perc1);
                    add(th_hi_shaker);
                    add(th_hi_snare);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_hi_kick);
                    add(th_hi_perc1);
                    add(th_hi_shaker);
                    add(th_hi_snare);
                    add(th_hi_bass);
                    add(th_hi_weird);
                    add(th_hi_noise);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_hi_kick);
                    add(th_hi_perc1);
                    add(th_hi_shaker);
                    add(th_hi_snare);
                    add(th_hi_bass);
                    add(th_hi_weird);
                    add(th_hi_noise);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_hi_vox);
                }});
            }}
        ));

        //Layer : TH_HR - KICK
        //Layer : TH_HR - KICK, TH_HR - HAT1, TH_HR - HAT2, TH_HR - NOISE
        //Layer : TH_HR - KICK, TH_HR - HAT1, TH_HR - HAT2, TH_HR - NOISE, TH_HR - BASS, TH_HR - SNARE
        //Layer : TH_HR - PERC
        //Layer : TH_HR - LEAD
        //Layer : TH_HR - KICK, TH_HR - HAT1, TH_HR - HAT2, TH_HR - NOISE, TH_HR - BASS, TH_HR - WEIRD
        //Layer : TH_HR - PAD
        regions.put("hr", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_hr_kick);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_hr_kick);
                    add(th_hr_hat1);
                    add(th_hr_hat2);
                    add(th_hr_noise);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_hr_kick);
                    add(th_hr_hat1);
                    add(th_hr_hat2);
                    add(th_hr_noise);
                    add(th_hr_bass);
                    add(th_hr_snare);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_hr_perc);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_hr_lead);
                }});
                put(100, new ArrayList<SoundEvent>() {{
                    add(th_hr_kick);
                    add(th_hr_hat1);
                    add(th_hr_hat2);
                    add(th_hr_noise);
                    add(th_hr_bass);
                    add(th_hr_weird);
                }});
                put(120, new ArrayList<SoundEvent>() {{
                    add(th_hr_pad);
                }});
            }}
        ));

        //Layer : {D}TH_LC - DAYKICK, {D}TH_LC - DAYATMOS, {D}TH_LC - DAYSHAKER, {N}TH_LC - NIGHTKICK, {N}TH_LC - NIGHTATMOS, {N}TH_LC - NIGHTSHAKER
        //Layer : {D}TH_LC - DAYKICK, {D}TH_LC - DAYATMOS, {D}TH_LC - DAYSHAKER, {D}TH_LC - DAYPERC, {N}TH_LC - NIGHTKICK, {N}TH_LC - NIGHTATMOS, {N}TH_LC - NIGHTSHAKER, {N}TH_LC - NIGHTPERC
        //Layer : {D}TH_LC - DAYKICK, {D}TH_LC - DAYATMOS, {D}TH_LC - DAYSHAKER, {D}TH_LC - DAYPERC, {D}TH_LC - DAYSUB, {D}TH_LC - DAYBREAKS, {D}TH_LC - DAYARP, {D}TH_LC - DAYSYNTH, {N}TH_LC - NIGHTKICK, {N}TH_LC - NIGHTATMOS, {N}TH_LC - NIGHTSHAKER, {N}TH_LC - NIGHTPERC, {N}TH_LC - NIGHTSUB, {N}TH_LC - NIGHTNOISE, {N}TH_LC - NIGHTHAT, {N}TH_LC - NIGHTSYNTH
        //Layer : {D}TH_LC - DAYKICK, {D}TH_LC - DAYATMOS, {D}TH_LC - DAYSHAKER, {D}TH_LC - DAYPERC, {D}TH_LC - DAYSUB, {D}TH_LC - DAYBREAKS, {D}TH_LC - DAYARP, {D}TH_LC - DAYSYNTH, {D}TH_LC - DAYTOM, {N}TH_LC - NIGHTKICK, {N}TH_LC - NIGHTATMOS, {N}TH_LC - NIGHTSHAKER, {N}TH_LC - NIGHTPERC, {N}TH_LC - NIGHTSUB, {N}TH_LC - NIGHTNOISE, {N}TH_LC - NIGHTHAT, {N}TH_LC - NIGHTSYNTH, {N}TH_LC - NIGHTTOM
        //Layer : {D}TH_LC - DAYATMOS, {D}TH_LC - DAYSUB, {D}TH_LC - DAYBREAKS, {N}TH_LC - NIGHTATMOS, {N}TH_LC - NIGHTSUB, {N}TH_LC - NIGHTNOISE
        regions.put("lc", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_lc_daykick);
                    add(th_lc_dayatmos);
                    add(th_lc_dayshaker);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_lc_daykick);
                    add(th_lc_dayatmos);
                    add(th_lc_dayshaker);
                    add(th_lc_dayperc);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_lc_daykick);
                    add(th_lc_dayatmos);
                    add(th_lc_dayshaker);
                    add(th_lc_dayperc);
                    add(th_lc_daysub);
                    add(th_lc_daybreaks);
                    add(th_lc_dayarp);
                    add(th_lc_daysynth);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_lc_daykick);
                    add(th_lc_dayatmos);
                    add(th_lc_dayshaker);
                    add(th_lc_dayperc);
                    add(th_lc_daysub);
                    add(th_lc_daybreaks);
                    add(th_lc_dayarp);
                    add(th_lc_daysynth);
                    add(th_lc_daytom);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_lc_dayatmos);
                    add(th_lc_daysub);
                    add(th_lc_daybreaks);
                }});
            }}
        ));

        regions.put("lcN", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_lc_nightkick);
                    add(th_lc_nightatmos);
                    add(th_lc_nightshaker);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_lc_nightkick);
                    add(th_lc_nightatmos);
                    add(th_lc_nightshaker);
                    add(th_lc_nightperc);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_lc_nightkick);
                    add(th_lc_nightatmos);
                    add(th_lc_nightshaker);
                    add(th_lc_nightperc);
                    add(th_lc_nightsub);
                    add(th_lc_nightnoise);
                    add(th_lc_nighthat);
                    add(th_lc_nightsynth);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_lc_nightkick);
                    add(th_lc_nightatmos);
                    add(th_lc_nightshaker);
                    add(th_lc_nightperc);
                    add(th_lc_nightsub);
                    add(th_lc_nightnoise);
                    add(th_lc_nighthat);
                    add(th_lc_nightsynth);
                    add(th_lc_nighttom);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_lc_nightatmos);
                    add(th_lc_nightsub);
                    add(th_lc_nightnoise);
                }});
            }}
        ));

        //Layer : TH_LF - KICK, TH_LF - SHAKER
        //Layer : TH_LF - KICK, TH_LF - PERC2, TH_LF - PERC1, TH_LF - SHAKER
        //Layer : TH_LF - KICK, TH_LF - PERC2, TH_LF - PERC1, TH_LF - SHAKER, TH_LF - BASS
        //Layer : TH_LF - KICK, TH_LF - PERC2, TH_LF - PERC1, TH_LF - SHAKER, TH_LF - SNARE, TH_LF - BASS, TH_LF - ARPS, TH_LF - NOISE
        //Layer : TH_LF - KICK, TH_LF - PERC2, TH_LF - PERC1, TH_LF - SHAKER, TH_LF - SNARE, TH_LF - BASS, TH_LF - ARPS, TH_LF - NOISE
        regions.put("lf", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_lf_kick);
                    add(th_lf_shaker);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_lf_kick);
                    add(th_lf_perc2);
                    add(th_lf_perc1);
                    add(th_lf_shaker);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_lf_kick);
                    add(th_lf_perc2);
                    add(th_lf_perc1);
                    add(th_lf_shaker);
                    add(th_lf_bass);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_lf_kick);
                    add(th_lf_perc2);
                    add(th_lf_perc1);
                    add(th_lf_shaker);
                    add(th_lf_snare);
                    add(th_lf_bass);
                    add(th_lf_arps);
                    add(th_lf_noise);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_lf_kick);
                    add(th_lf_perc2);
                    add(th_lf_perc1);
                    add(th_lf_shaker);
                    add(th_lf_snare);
                    add(th_lf_bass);
                    add(th_lf_arps);
                    add(th_lf_noise);
                }});
            }}
        ));

        //Layer : TH_LM - KICK, TH_LM - BASS, TH_LM - NOISE, TH_LM - PAD, TH_LM - PERC1
        //Layer : TH_LM - KICK, TH_LM - BASS, TH_LM - NOISE, TH_LM - PAD, TH_LM - PERC1, TH_LM - ARPS
        //Layer : TH_LM - KICK, TH_LM - BASS, TH_LM - NOISE, TH_LM - PAD, TH_LM - PERC1, TH_LM - ARPS, TH_LM - PERC2
        //Layer : TH_LM - KICK, TH_LM - BASS, TH_LM - NOISE, TH_LM - PAD, TH_LM - PERC1, TH_LM - ARPS, TH_LM - PERC2, TH_LM - SNARE
        //Layer : TH_LM - KICK, TH_LM - BASS, TH_LM - NOISE, TH_LM - PAD, TH_LM - PERC1, TH_LM - ARPS, TH_LM - PERC2, TH_LM - SNARE, TH_LM - WEIRD
        regions.put("lm", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_lm_kick);
                    add(th_lm_bass);
                    add(th_lm_noise);
                    add(th_lm_pad);
                    add(th_lm_perc1);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_lm_kick);
                    add(th_lm_bass);
                    add(th_lm_noise);
                    add(th_lm_pad);
                    add(th_lm_perc1);
                    add(th_lm_arps);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_lm_kick);
                    add(th_lm_bass);
                    add(th_lm_noise);
                    add(th_lm_pad);
                    add(th_lm_perc1);
                    add(th_lm_arps);
                    add(th_lm_perc2);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_lm_kick);
                    add(th_lm_bass);
                    add(th_lm_noise);
                    add(th_lm_pad);
                    add(th_lm_perc1);
                    add(th_lm_arps);
                    add(th_lm_perc2);
                    add(th_lm_snare);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_lm_kick);
                    add(th_lm_bass);
                    add(th_lm_noise);
                    add(th_lm_pad);
                    add(th_lm_perc1);
                    add(th_lm_arps);
                    add(th_lm_perc2);
                    add(th_lm_snare);
                    add(th_lm_weird);
                }});
            }}
        ));

        //Layer : TH_OE - NOISE, {N}TH_OE - FLOW
        //Layer : TH_OE - NOISE, TH_OE - KICKPERC, {N}TH_OE - FLOW
        //Layer : TH_OE - NOISE, TH_OE - KICKPERC, {Sunken Pier}TH_OE - BASS, {Outer Expanse|Journey's End}TH_OE - PERC2, {N}TH_OE - FLOW
        //Layer : TH_OE - NOISE, TH_OE - KICKPERC, {Sunken Pier}TH_OE - BASS, {Outer Expanse|Journey's End}TH_OE - PERC2, {Sunken Pier}TH_OE - WAVES, {N}TH_OE - FLOW
        //Layer : TH_OE - NOISE, TH_OE - KICKPERC, {Sunken Pier}TH_OE - BASS, {Outer Expanse|Journey's End}TH_OE - PERC2, {Sunken Pier}TH_OE - WAVES, {D}TH_OE - LEAD, {D}TH_OE - ARP, {N}TH_OE - FLOW
        //Layer : TH_OE - NOISE, TH_OE - KICKPERC, {Sunken Pier}TH_OE - BASS, {Outer Expanse|Journey's End}TH_OE - PERC2, {Sunken Pier}TH_OE - WAVES, {D}TH_OE - LEAD, {D}TH_OE - ARP, {N}TH_OE - FLOW
        regions.put("oe", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                    add(th_oe_perc2);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                    add(th_oe_perc2);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                    add(th_oe_perc2);
                    add(th_oe_lead);
                    add(th_oe_arp);
                }});
                put(100, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                    add(th_oe_perc2);
                    add(th_oe_lead);
                    add(th_oe_arp);
                }});
            }}
        ));

        regions.put("oeN", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_flow);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                    add(th_oe_flow);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                    add(th_oe_perc2);
                    add(th_oe_flow);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                    add(th_oe_perc2);
                    add(th_oe_flow);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                    add(th_oe_perc2);
                    add(th_oe_flow);
                }});
                put(100, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                    add(th_oe_perc2);
                    add(th_oe_flow);
                }});
            }}
        ));

        regions.put("oeS", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                    add(th_oe_bass);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                    add(th_oe_bass);
                    add(th_oe_waves);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                    add(th_oe_bass);
                    add(th_oe_waves);
                    add(th_oe_lead);
                    add(th_oe_arp);
                }});
                put(100, new ArrayList<SoundEvent>() {{
                    add(th_oe_noise);
                    add(th_oe_kickperc);
                    add(th_oe_bass);
                    add(th_oe_waves);
                    add(th_oe_lead);
                    add(th_oe_arp);
                }});
            }}
        ));

        //Layer : TH_SI - KICK, TH_SI - PERC1 
        //Layer : TH_SI - KICK, TH_SI - PERC1, TH_SI - SHAKER
        //Layer : TH_SI - KICK, TH_SI - PERC1, TH_SI - NOISE, TH_SI - SHAKER, TH_SI - WEIRD
        //Layer : TH_SI - KICK, TH_SI - PERC1, TH_SI - NOISE, TH_SI - SHAKER, TH_SI - ARPS, TH_SI - BASS, TH_SI - WEIRD, TH_SI -SNARE
        //Layer : TH_SI - PERC1, TH_SI - NOISE, TH_SI - SHAKER, TH_SI - ARPS, TH_SI - BASS, TH_SI - WEIRD, TH_SI -SNARE
        //Layer : TH_SI - PANIC
        regions.put("si", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_si_kick);
                    add(th_si_perc1);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_si_kick);
                    add(th_si_perc1);
                    add(th_si_shaker);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_si_kick);
                    add(th_si_perc1);
                    add(th_si_noise);
                    add(th_si_shaker);
                    add(th_si_weird);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_si_kick);
                    add(th_si_perc1);
                    add(th_si_noise);
                    add(th_si_shaker);
                    add(th_si_arps);
                    add(th_si_bass);
                    add(th_si_weird);
                    add(th_si_snare);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_si_perc1);
                    add(th_si_noise);
                    add(th_si_shaker);
                    add(th_si_arps);
                    add(th_si_bass);
                    add(th_si_weird);
                    add(th_si_snare);
                }});
                put(100, new ArrayList<SoundEvent>() {{
                    add(th_si_panic);
                }});
            }}
        ));

        //Layer : TH_SL - KICK, TH_SL - BASS
        //Layer : TH_SL - KICK, TH_SL - BASS, TH_SL - PERC2, TH_SL - ARPS
        //Layer : TH_SL - KICK, TH_SL - BASS, TH_SL - PERC2, TH_SL - ARPS, TH_SL - NOISE, TH_SL - SNARE
        //Layer : TH_SL - KICK, TH_SL - BASS, TH_SL - PERC2, TH_SL - ARPS, TH_SL - NOISE, TH_SL - SNARE, TH_SL - PERC2, TH_SL - LEAD
        //Layer : TH_SL - KICK, TH_SL - BASS, TH_SL - PERC2, TH_SL - ARPS, TH_SL - NOISE, TH_SL - SNARE, TH_SL - PERC2, TH_SL - LEAD
        regions.put("sl", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_sl_kick);
                    add(th_sl_bass);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_sl_kick);
                    add(th_sl_bass);
                    add(th_sl_perc2);
                    add(th_sl_arps);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_sl_kick);
                    add(th_sl_bass);
                    add(th_sl_perc2);
                    add(th_sl_arps);
                    add(th_sl_noise);
                    add(th_sl_snare);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_sl_kick);
                    add(th_sl_bass);
                    add(th_sl_perc2);
                    add(th_sl_arps);
                    add(th_sl_noise);
                    add(th_sl_snare);
                    add(th_sl_perc2);
                    add(th_sl_lead);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_sl_kick);
                    add(th_sl_bass);
                    add(th_sl_perc2);
                    add(th_sl_arps);
                    add(th_sl_noise);
                    add(th_sl_snare);
                    add(th_sl_perc2);
                    add(th_sl_lead);
                }});
            }}
        ));

        //Layer : TH_SS - NOISE, TH_SS - BASS, TH_SS - KICK
        //Layer : TH_SS - NOISE, TH_SS - BASS, TH_SS - KICK, TH_SS - POP
        //Layer : TH_SS - NOISE, TH_SS - BASS, TH_SS - KICK, TH_SS - POP, TH_SS - LEAD
        //Layer : TH_SS - NOISE, TH_SS - BASS, TH_SS - KICK, TH_SS - POP, TH_SS - LEAD

        regions.put("ss", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_ss_noise);
                    add(th_ss_bass);
                    add(th_ss_kick);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_ss_noise);
                    add(th_ss_bass);
                    add(th_ss_kick);
                    add(th_ss_pop);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_ss_noise);
                    add(th_ss_bass);
                    add(th_ss_kick);
                    add(th_ss_pop);
                    add(th_ss_lead);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_ss_noise);
                    add(th_ss_bass);
                    add(th_ss_kick);
                    add(th_ss_pop);
                    add(th_ss_lead);
                }});
            }}
        ));

        // Layer : TH_SU - KICK, TH_SU - SHAKER
        // Layer : TH_SU - KICK, TH_SU - SHAKER, TH_SU - PERC1, TH_SU - NOISE
        // Layer : TH_SU - KICK, TH_SU - SHAKER, TH_SU - PERC1, TH_SU - NOISE, TH_SU - HITS, TH_SU - BASS
        // Layer : TH_SU - KICK, TH_SU - SHAKER, TH_SU - PERC1, TH_SU - ARPS, TH_SU - NOISE, TH_SU - HITS, TH_SU - BASS
        // Layer : TH_SU - LEAD, TH_SU - BASS
        regions.put("su", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_su_kick);
                    add(th_su_shaker);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_su_kick);
                    add(th_su_shaker);
                    add(th_su_perc1);
                    add(th_su_noise);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_su_kick);
                    add(th_su_shaker);
                    add(th_su_perc1);
                    add(th_su_noise);
                    add(th_su_hits);
                    add(th_su_bass);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_su_kick);
                    add(th_su_shaker);
                    add(th_su_perc1);
                    add(th_su_arps);
                    add(th_su_noise);
                    add(th_su_hits);
                    add(th_su_bass);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_su_lead);
                    add(th_su_bass);
                }});
            }}
        ));

        //Layer : TH_VS - KICK, TH_VS - SHAKER
        //Layer : TH_VS - KICK, TH_VS - NOISE, TH_VS - SHAKER, TH_VS - BASS, TH_VS - PERC1
        //Layer : TH_VS - KICK, TH_VS - NOISE, TH_VS - SHAKER, TH_VS - BASS, TH_VS - ARPS, TH_VS - PERC1, TH_VS - PERC2
        //Layer : TH_VS - KICK, TH_VS - NOISE, TH_VS - SHAKER, TH_VS - BASS, TH_VS - ARPS, TH_VS - PERC1, TH_VS - PERC2, TH_VS - WEIRD
        //Layer : TH_VS - SYNTH
        regions.put("vs", new Region(
            new HashMap<Integer, List<SoundEvent>>() {{
                put(0, new ArrayList<SoundEvent>() {{
                    add(th_vs_kick);
                    add(th_vs_shaker);
                }});
                put(20, new ArrayList<SoundEvent>() {{
                    add(th_vs_kick);
                    add(th_vs_noise);
                    add(th_vs_shaker);
                    add(th_vs_bass);
                    add(th_vs_perc1);
                }});
                put(40, new ArrayList<SoundEvent>() {{
                    add(th_vs_kick);
                    add(th_vs_noise);
                    add(th_vs_shaker);
                    add(th_vs_bass);
                    add(th_vs_arps);
                    add(th_vs_perc1);
                    add(th_vs_perc2);
                }});
                put(60, new ArrayList<SoundEvent>() {{
                    add(th_vs_kick);
                    add(th_vs_noise);
                    add(th_vs_shaker);
                    add(th_vs_bass);
                    add(th_vs_arps);
                    add(th_vs_perc1);
                    add(th_vs_perc2);
                    add(th_vs_weird);
                }});
                put(80, new ArrayList<SoundEvent>() {{
                    add(th_vs_synth);
                }});
            }}
        ));

        



        //add regions to biomeRegions
        //lm for all warm oceans
        biomeRegions.put(new ArrayList<String>() {{
            add("Warm Ocean");
            add("Lukewarm Ocean");
            add("Deep Warm Ocean");
            add("Deep Lukewarm Ocean");
        }}, regions.get("lm"));

        //sl for all other oceans
        biomeRegions.put(new ArrayList<String>() {{
            add("Ocean");
            add("Deep Ocean");
            add("Cold Ocean");
            add("Deep Cold Ocean");
            add("Frozen Ocean");
            add("Deep Frozen Ocean");
        }}, regions.get("sl"));

        //ss is for all end biomes
        biomeRegions.put(new ArrayList<String>() {{
            add("The End");
            add("Small End Islands");
            add("End Midlands");
            add("End Highlands");
            add("End Barrens");
        }}, regions.get("ss"));

        //oeS for lush caves
        biomeRegions.put(new ArrayList<String>() {{
            add("Lush Caves");
        }}, regions.get("oeS"));

        //dm for deep dark
        biomeRegions.put(new ArrayList<String>() {{
            add("Deep Dark");
        }}, regions.get("dm"));

        //oe for jungle biomes
        biomeRegions.put(new ArrayList<String>() {{
            add("Jungle");
            add("Jungle Hills");
            add("Jungle Edge");
            add("Modified Jungle");
            add("Modified Jungle Edge");
            add("Bamboo Jungle");
            add("Bamboo Jungle Hills");
        }}, regions.get("oe"));

        //vs for dripstone caves
        biomeRegions.put(new ArrayList<String>() {{
            add("Dripstone Caves");
        }}, regions.get("vs"));

        //hr is for all neather biomes
        biomeRegions.put(new ArrayList<String>() {{
            add("Nether Wastes");
            add("Soul Sand Valley");
            add("Crimson Forest");
            add("Warped Forest");
            add("Basalt Deltas");
        }}, regions.get("hr"));

        //gw for swamp biomes
        biomeRegions.put(new ArrayList<String>() {{
            add("Swamp");
            add("Swamp Hills");
            add("Mangrove Swamp");
        }}, regions.get("gw"));

        //hi for desert biomes
        biomeRegions.put(new ArrayList<String>() {{
            add("Desert");
            add("Desert Hills");
            add("Desert Lakes");
            add("Badlands");
            add("Badlands Plateau");
            add("Eroded Badlands");
            add("Modified Badlands Plateau");
            add("Modified Wooded Badlands Plateau");
            add("Wooded Badlands Plateau");
        }}, regions.get("hi"));



    }

    public static Region changeRegion(MinecraftClient client) {
        // Get the current biome at the player's position
        RegistryEntry<Biome> currentBiomeEntry = client.world.getBiome(client.player.getBlockPos());
        String currentBiomeName = I18n.translate(getBiomeName(currentBiomeEntry));
    
        // Print the current biome to the chat
        client.player.sendMessage(Text.of(currentBiomeName), false);

        Region currentRegion = regions.get("su");

        for (Map.Entry<List<String>, Region> entry : biomeRegions.entrySet()) {
            if (entry.getKey().contains(currentBiomeName)) {
                currentRegion = entry.getValue();
            }
        }

        if (currentRegion == regions.get("oe")) {
            //check if it is day or night
            if (isDay(client)) {
                currentRegion = regions.get("oe");
            } else {
                currentRegion = regions.get("oeN");
            }
        }

        BossBarHud bossBarHud = client.inGameHud.getBossBarHud();
        Map<UUID, ClientBossBar> bossBars = ((GetBossBarsMixin) bossBarHud).getBossBars();

        //iterate through and check if the strings contain Raid
        for (Map.Entry<UUID, ClientBossBar> entry : bossBars.entrySet()) {
            if (entry.getValue().getName().getString().contains("Raid")) {
                //check if it is day or night
                if (isDay(client)) {
                    currentRegion = regions.get("lc");
                } else {
                    currentRegion = regions.get("lcN");
                }
            }
        }

        return currentRegion;
    }
    
    

    public static String getBiomeName(RegistryEntry<Biome> biome) {
        return I18n.translate(getBiomeTranslationKey(biome));
    }

    private static String getBiomeTranslationKey(RegistryEntry<Biome> biome) {
        return biome.getKeyOrValue().map(
            (biomeKey) -> "biome." + biomeKey.getValue().getNamespace() + "." + biomeKey.getValue().getPath(),
            (biomeValue) -> "[unregistered " + biomeValue + "]" // For unregistered biome
        );
    }

    public static boolean isDay(MinecraftClient client) {
        //client.world.isDay() doesn't work
        return client.world.getTimeOfDay() % 24000 < 12000;
    }
}
