package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.example.mixin.client.GetBossBarsMixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;




public class ModSounds {

    // Define the Region outside of the registerSounds() method so it can be accessed globally.
    

    //current region
    public static Region currentRegion;

    //map of regions indexed by name
    public static Map<String, Region> regions = new HashMap<String, Region>();

    //map of sound events indexed by name
    public static Map<String, SoundEvent> soundEvents = new HashMap<String, SoundEvent>();

    //map of regions indexed by list of biomes
    public static Map<List<String>, Region> biomeRegions = new HashMap<List<String>, Region>();


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier("modid", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
    

    public static void registerSounds() {
        String directoryPath = "assets/modid/sounds"; // Path to the sounds directory
        try (Stream<Path> paths = Files.walk(Paths.get(ClassLoader.getSystemResource(directoryPath).toURI()))) {
            paths.filter(Files::isRegularFile)
                 .filter(path -> path.toString().endsWith(".ogg"))
                 .forEach(path -> {
                     String fileName = path.getFileName().toString();
                     String soundName = fileName.substring(0, fileName.lastIndexOf('.'));
                     SoundEvent soundEvent = registerSoundEvent(soundName);
                     soundEvents.put(soundName, soundEvent);
                 });
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        
        //Layer : TH_CC - KICK, TH_CC - PERC1, TH_CC - NOISE
        //Layer : TH_CC - KICK, TH_CC - PERC2, TH_CC - PERC1, TH_CC - NOISE
        //Layer : TH_CC - KICK, TH_CC - PERC2, TH_CC - PERC1, TH_CC - NOISE, TH_CC - SNARE, {Chimney Canopy}TH_CC - BASS, {The Gutter}TH_CC_-_GUTTERBASS, {Chimney Canopy}TH_CC - VOX, {The Gutter}TH_CC_-_GUTTERVOX
        //Layer : TH_CC - KICK, {Chimney Canopy}TH_CC - ARPS, TH_CC - PERC1, TH_CC - NOISE, TH_CC - SNARE, {Chimney Canopy}TH_CC - BASS, {The Gutter}TH_CC_-_GUTTERBASS, TH_CC - PERC2, {Chimney Canopy}TH_CC - VOX, {The Gutter}TH_CC_-_GUTTERVOX
        //Layer : {Chimney Canopy}TH_CC - BASS, {The Gutter}TH_CC_-_GUTTERBASS, {Chimney Canopy}TH_CC - VOX, {The Gutter}TH_CC_-_GUTTERVOX
        regions.put("cc", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_cc_kick"), soundEvents.get("th_cc_perc1"), soundEvents.get("th_cc_noise")),
            Arrays.asList(soundEvents.get("th_cc_kick"), soundEvents.get("th_cc_perc2"), soundEvents.get("th_cc_perc1"), soundEvents.get("th_cc_noise")),
            Arrays.asList(soundEvents.get("th_cc_kick"), soundEvents.get("th_cc_perc2"), soundEvents.get("th_cc_perc1"), soundEvents.get("th_cc_noise"), soundEvents.get("th_cc_snare"), soundEvents.get("th_cc_bass"), soundEvents.get("th_cc_vox")),
            Arrays.asList(soundEvents.get("th_cc_kick"), soundEvents.get("th_cc_arps"), soundEvents.get("th_cc_perc1"), soundEvents.get("th_cc_noise"), soundEvents.get("th_cc_snare"), soundEvents.get("th_cc_bass"), soundEvents.get("th_cc_perc2"), soundEvents.get("th_cc_vox")),
            Arrays.asList(soundEvents.get("th_cc_bass"), soundEvents.get("th_cc_vox"))
        )));

        regions.put("ccG", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_cc_kick"), soundEvents.get("th_cc_perc1"), soundEvents.get("th_cc_noise")),
            Arrays.asList(soundEvents.get("th_cc_kick"), soundEvents.get("th_cc_perc2"), soundEvents.get("th_cc_perc1"), soundEvents.get("th_cc_noise")),
            Arrays.asList(soundEvents.get("th_cc_kick"), soundEvents.get("th_cc_perc2"), soundEvents.get("th_cc_perc1"), soundEvents.get("th_cc_noise"), soundEvents.get("th_cc_snare"), soundEvents.get("th_cc_gutterbass"), soundEvents.get("th_cc_guttervox")),
            Arrays.asList(soundEvents.get("th_cc_kick"), soundEvents.get("th_cc_arps"), soundEvents.get("th_cc_perc1"), soundEvents.get("th_cc_noise"), soundEvents.get("th_cc_snare"), soundEvents.get("th_cc_gutterbass"), soundEvents.get("th_cc_perc2"), soundEvents.get("th_cc_guttervox")),
            Arrays.asList(soundEvents.get("th_cc_gutterbass"), soundEvents.get("th_cc_guttervox"))
        )));


        //Layer : TH_DM - NOISE, TH_DM - BASS, TH_DM - KICK
        //Layer : TH_DM - NOISE, TH_DM - BASS, TH_DM - KICK, TH_DM - SNARE
        //Layer : TH_DM - NOISE, TH_DM - BASS, TH_DM - KICK, TH_DM - SNARE, TH_DM - LEAD, TH_DM - SHAKER
        //Layer : TH_DM - NOISE, TH_DM - BASS, TH_DM - KICK, TH_DM - SNARE, TH_DM - LEAD, TH_DM - SHAKER
        regions.put("dm", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_dm_noise"), soundEvents.get("th_dm_bass"), soundEvents.get("th_dm_kick")),
            Arrays.asList(soundEvents.get("th_dm_noise"), soundEvents.get("th_dm_bass"), soundEvents.get("th_dm_kick"), soundEvents.get("th_dm_snare")),
            Arrays.asList(soundEvents.get("th_dm_noise"), soundEvents.get("th_dm_bass"), soundEvents.get("th_dm_kick"), soundEvents.get("th_dm_snare"), soundEvents.get("th_dm_lead"), soundEvents.get("th_dm_shaker")),
            Arrays.asList(soundEvents.get("th_dm_noise"), soundEvents.get("th_dm_bass"), soundEvents.get("th_dm_kick"), soundEvents.get("th_dm_snare"), soundEvents.get("th_dm_lead"), soundEvents.get("th_dm_shaker"))
        )));

        //Layer : TH_GW - KICK, TH_GW - PERC1, TH_GW - SHAKE
        //Layer : TH_GW - KICK, TH_GW - PERC1, TH_GW - SHAKE, TH_GW - BASS
        //Layer : TH_GW - KICK, TH_GW - PERC1, TH_GW - SHAKE, TH_GW - BASS, TH_GW - NOISE, TH_GW - WEIRD, TH_GW - VOX
        //Layer : TH_GW - KICK, TH_GW - PERC1, TH_GW - SHAKE, TH_GW - BASS, TH_GW - NOISE, TH_GW - WEIRD, TH_GW - VOX
        //Layer : TH_GW - LEAD
        regions.put("gw", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_gw_kick"), soundEvents.get("th_gw_perc1"), soundEvents.get("th_gw_shake")),
            Arrays.asList(soundEvents.get("th_gw_kick"), soundEvents.get("th_gw_perc1"), soundEvents.get("th_gw_shake"), soundEvents.get("th_gw_bass")),
            Arrays.asList(soundEvents.get("th_gw_kick"), soundEvents.get("th_gw_perc1"), soundEvents.get("th_gw_shake"), soundEvents.get("th_gw_bass"), soundEvents.get("th_gw_noise"), soundEvents.get("th_gw_weird"), soundEvents.get("th_gw_vox")),
            Arrays.asList(soundEvents.get("th_gw_kick"), soundEvents.get("th_gw_perc1"), soundEvents.get("th_gw_shake"), soundEvents.get("th_gw_bass"), soundEvents.get("th_gw_noise"), soundEvents.get("th_gw_weird"), soundEvents.get("th_gw_vox")),
            Arrays.asList(soundEvents.get("th_gw_lead"))
        )));

        //Layer : TH_HI - KICK, TH_HI - PERC1, TH_HI - SHAKER
        //Layer : TH_HI - KICK, TH_HI - PERC1, TH_HI - SHAKER, TH_HI - SNARE
        //Layer : TH_HI - KICK, TH_HI - PERC1, TH_HI - SHAKER, TH_HI - SNARE, TH_HI - BASS, TH_HI - WEIRD, TH_HI - NOISE
        //Layer : TH_HI - KICK, TH_HI - PERC1, TH_HI - SHAKER, TH_HI - SNARE, TH_HI - BASS, TH_HI - WEIRD, TH_HI - NOISE
        //Layer : TH_HI - VOX
        regions.put("hi", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_hi_kick"), soundEvents.get("th_hi_perc1"), soundEvents.get("th_hi_shaker")),
            Arrays.asList(soundEvents.get("th_hi_kick"), soundEvents.get("th_hi_perc1"), soundEvents.get("th_hi_shaker"), soundEvents.get("th_hi_snare")),
            Arrays.asList(soundEvents.get("th_hi_kick"), soundEvents.get("th_hi_perc1"), soundEvents.get("th_hi_shaker"), soundEvents.get("th_hi_snare"), soundEvents.get("th_hi_bass"), soundEvents.get("th_hi_weird"), soundEvents.get("th_hi_noise")),
            Arrays.asList(soundEvents.get("th_hi_kick"), soundEvents.get("th_hi_perc1"), soundEvents.get("th_hi_shaker"), soundEvents.get("th_hi_snare"), soundEvents.get("th_hi_bass"), soundEvents.get("th_hi_weird"), soundEvents.get("th_hi_noise")),
            Arrays.asList(soundEvents.get("th_hi_vox"))
        )));

        //Layer : TH_HR - KICK
        //Layer : TH_HR - KICK, TH_HR - HAT1, TH_HR - HAT2, TH_HR - NOISE
        //Layer : TH_HR - KICK, TH_HR - HAT1, TH_HR - HAT2, TH_HR - NOISE, TH_HR - BASS, TH_HR - SNARE
        //Layer : TH_HR - PERC
        //Layer : TH_HR - LEAD
        //Layer : TH_HR - KICK, TH_HR - HAT1, TH_HR - HAT2, TH_HR - NOISE, TH_HR - BASS, TH_HR - WEIRD
        //Layer : TH_HR - PAD
        regions.put("hr", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_hr_kick")),
            Arrays.asList(soundEvents.get("th_hr_kick"), soundEvents.get("th_hr_hat1"), soundEvents.get("th_hr_hat2"), soundEvents.get("th_hr_noise")),
            Arrays.asList(soundEvents.get("th_hr_kick"), soundEvents.get("th_hr_hat1"), soundEvents.get("th_hr_hat2"), soundEvents.get("th_hr_noise"), soundEvents.get("th_hr_bass"), soundEvents.get("th_hr_snare")),
            Arrays.asList(soundEvents.get("th_hr_perc")),
            Arrays.asList(soundEvents.get("th_hr_lead")),
            Arrays.asList(soundEvents.get("th_hr_kick"), soundEvents.get("th_hr_hat1"), soundEvents.get("th_hr_hat2"), soundEvents.get("th_hr_noise"), soundEvents.get("th_hr_bass"), soundEvents.get("th_hr_weird")),
            Arrays.asList(soundEvents.get("th_hr_pad"))
        )));

        //Layer : {D}TH_LC - DAYKICK, {D}TH_LC - DAYATMOS, {D}TH_LC - DAYSHAKER, {N}TH_LC - NIGHTKICK, {N}TH_LC - NIGHTATMOS, {N}TH_LC - NIGHTSHAKER
        //Layer : {D}TH_LC - DAYKICK, {D}TH_LC - DAYATMOS, {D}TH_LC - DAYSHAKER, {D}TH_LC - DAYPERC, {N}TH_LC - NIGHTKICK, {N}TH_LC - NIGHTATMOS, {N}TH_LC - NIGHTSHAKER, {N}TH_LC - NIGHTPERC
        //Layer : {D}TH_LC - DAYKICK, {D}TH_LC - DAYATMOS, {D}TH_LC - DAYSHAKER, {D}TH_LC - DAYPERC, {D}TH_LC - DAYSUB, {D}TH_LC - DAYBREAKS, {D}TH_LC - DAYARP, {D}TH_LC - DAYSYNTH, {N}TH_LC - NIGHTKICK, {N}TH_LC - NIGHTATMOS, {N}TH_LC - NIGHTSHAKER, {N}TH_LC - NIGHTPERC, {N}TH_LC - NIGHTSUB, {N}TH_LC - NIGHTNOISE, {N}TH_LC - NIGHTHAT, {N}TH_LC - NIGHTSYNTH
        //Layer : {D}TH_LC - DAYKICK, {D}TH_LC - DAYATMOS, {D}TH_LC - DAYSHAKER, {D}TH_LC - DAYPERC, {D}TH_LC - DAYSUB, {D}TH_LC - DAYBREAKS, {D}TH_LC - DAYARP, {D}TH_LC - DAYSYNTH, {D}TH_LC - DAYTOM, {N}TH_LC - NIGHTKICK, {N}TH_LC - NIGHTATMOS, {N}TH_LC - NIGHTSHAKER, {N}TH_LC - NIGHTPERC, {N}TH_LC - NIGHTSUB, {N}TH_LC - NIGHTNOISE, {N}TH_LC - NIGHTHAT, {N}TH_LC - NIGHTSYNTH, {N}TH_LC - NIGHTTOM
        //Layer : {D}TH_LC - DAYATMOS, {D}TH_LC - DAYSUB, {D}TH_LC - DAYBREAKS, {N}TH_LC - NIGHTATMOS, {N}TH_LC - NIGHTSUB, {N}TH_LC - NIGHTNOISE
        regions.put("lc", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_lc_daykick"), soundEvents.get("th_lc_dayatmos"), soundEvents.get("th_lc_dayshaker")),
            Arrays.asList(soundEvents.get("th_lc_daykick"), soundEvents.get("th_lc_dayatmos"), soundEvents.get("th_lc_dayshaker"), soundEvents.get("th_lc_dayperc")),
            Arrays.asList(soundEvents.get("th_lc_daykick"), soundEvents.get("th_lc_dayatmos"), soundEvents.get("th_lc_dayshaker"), soundEvents.get("th_lc_dayperc"), soundEvents.get("th_lc_daysub"), soundEvents.get("th_lc_daybreaks"), soundEvents.get("th_lc_dayarp"), soundEvents.get("th_lc_daysynth")),
            Arrays.asList(soundEvents.get("th_lc_daykick"), soundEvents.get("th_lc_dayatmos"), soundEvents.get("th_lc_dayshaker"), soundEvents.get("th_lc_dayperc"), soundEvents.get("th_lc_daysub"), soundEvents.get("th_lc_daybreaks"), soundEvents.get("th_lc_dayarp"), soundEvents.get("th_lc_daysynth"), soundEvents.get("th_lc_daytom")),
            Arrays.asList(soundEvents.get("th_lc_dayatmos"), soundEvents.get("th_lc_daysub"), soundEvents.get("th_lc_daybreaks"))
        )));


        regions.put("lcN", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_lc_nightkick"), soundEvents.get("th_lc_nightatmos"), soundEvents.get("th_lc_nightshaker")),
            Arrays.asList(soundEvents.get("th_lc_nightkick"), soundEvents.get("th_lc_nightatmos"), soundEvents.get("th_lc_nightshaker"), soundEvents.get("th_lc_nightperc")),
            Arrays.asList(soundEvents.get("th_lc_nightkick"), soundEvents.get("th_lc_nightatmos"), soundEvents.get("th_lc_nightshaker"), soundEvents.get("th_lc_nightperc"), soundEvents.get("th_lc_nightsub"), soundEvents.get("th_lc_nightnoise"), soundEvents.get("th_lc_nighthat"), soundEvents.get("th_lc_nightsynth")),
            Arrays.asList(soundEvents.get("th_lc_nightkick"), soundEvents.get("th_lc_nightatmos"), soundEvents.get("th_lc_nightshaker"), soundEvents.get("th_lc_nightperc"), soundEvents.get("th_lc_nightsub"), soundEvents.get("th_lc_nightnoise"), soundEvents.get("th_lc_nighthat"), soundEvents.get("th_lc_nightsynth"), soundEvents.get("th_lc_nighttom")),
            Arrays.asList(soundEvents.get("th_lc_nightatmos"), soundEvents.get("th_lc_nightsub"), soundEvents.get("th_lc_nightnoise"))
        )));

        //Layer : TH_LF - KICK, TH_LF - SHAKER
        //Layer : TH_LF - KICK, TH_LF - PERC2, TH_LF - PERC1, TH_LF - SHAKER
        //Layer : TH_LF - KICK, TH_LF - PERC2, TH_LF - PERC1, TH_LF - SHAKER, TH_LF - BASS
        //Layer : TH_LF - KICK, TH_LF - PERC2, TH_LF - PERC1, TH_LF - SHAKER, TH_LF - SNARE, TH_LF - BASS, TH_LF - ARPS, TH_LF - NOISE
        //Layer : TH_LF - KICK, TH_LF - PERC2, TH_LF - PERC1, TH_LF - SHAKER, TH_LF - SNARE, TH_LF - BASS, TH_LF - ARPS, TH_LF - NOISE
        regions.put("lf", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_lf_kick"), soundEvents.get("th_lf_shaker")),
            Arrays.asList(soundEvents.get("th_lf_kick"), soundEvents.get("th_lf_perc2"), soundEvents.get("th_lf_perc1"), soundEvents.get("th_lf_shaker")),
            Arrays.asList(soundEvents.get("th_lf_kick"), soundEvents.get("th_lf_perc2"), soundEvents.get("th_lf_perc1"), soundEvents.get("th_lf_shaker"), soundEvents.get("th_lf_bass")),
            Arrays.asList(soundEvents.get("th_lf_kick"), soundEvents.get("th_lf_perc2"), soundEvents.get("th_lf_perc1"), soundEvents.get("th_lf_shaker"), soundEvents.get("th_lf_snare"), soundEvents.get("th_lf_bass"), soundEvents.get("th_lf_arps"), soundEvents.get("th_lf_noise")),
            Arrays.asList(soundEvents.get("th_lf_kick"), soundEvents.get("th_lf_perc2"), soundEvents.get("th_lf_perc1"), soundEvents.get("th_lf_shaker"), soundEvents.get("th_lf_snare"), soundEvents.get("th_lf_bass"), soundEvents.get("th_lf_arps"), soundEvents.get("th_lf_noise"))
        )));

        //Layer : TH_LM - KICK, TH_LM - BASS, TH_LM - NOISE, TH_LM - PAD, TH_LM - PERC1
        //Layer : TH_LM - KICK, TH_LM - BASS, TH_LM - NOISE, TH_LM - PAD, TH_LM - PERC1, TH_LM - ARPS
        //Layer : TH_LM - KICK, TH_LM - BASS, TH_LM - NOISE, TH_LM - PAD, TH_LM - PERC1, TH_LM - ARPS, TH_LM - PERC2
        //Layer : TH_LM - KICK, TH_LM - BASS, TH_LM - NOISE, TH_LM - PAD, TH_LM - PERC1, TH_LM - ARPS, TH_LM - PERC2, TH_LM - SNARE
        //Layer : TH_LM - KICK, TH_LM - BASS, TH_LM - NOISE, TH_LM - PAD, TH_LM - PERC1, TH_LM - ARPS, TH_LM - PERC2, TH_LM - SNARE, TH_LM - WEIRD
        regions.put("lm", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_lm_kick"), soundEvents.get("th_lm_bass"), soundEvents.get("th_lm_noise"), soundEvents.get("th_lm_pad"), soundEvents.get("th_lm_perc1")),
            Arrays.asList(soundEvents.get("th_lm_kick"), soundEvents.get("th_lm_bass"), soundEvents.get("th_lm_noise"), soundEvents.get("th_lm_pad"), soundEvents.get("th_lm_perc1"), soundEvents.get("th_lm_arps")),
            Arrays.asList(soundEvents.get("th_lm_kick"), soundEvents.get("th_lm_bass"), soundEvents.get("th_lm_noise"), soundEvents.get("th_lm_pad"), soundEvents.get("th_lm_perc1"), soundEvents.get("th_lm_arps"), soundEvents.get("th_lm_perc2")),
            Arrays.asList(soundEvents.get("th_lm_kick"), soundEvents.get("th_lm_bass"), soundEvents.get("th_lm_noise"), soundEvents.get("th_lm_pad"), soundEvents.get("th_lm_perc1"), soundEvents.get("th_lm_arps"), soundEvents.get("th_lm_perc2"), soundEvents.get("th_lm_snare")),
            Arrays.asList(soundEvents.get("th_lm_kick"), soundEvents.get("th_lm_bass"), soundEvents.get("th_lm_noise"), soundEvents.get("th_lm_pad"), soundEvents.get("th_lm_perc1"), soundEvents.get("th_lm_arps"), soundEvents.get("th_lm_perc2"), soundEvents.get("th_lm_snare"), soundEvents.get("th_lm_weird"))
        )));

        //Layer : TH_OE - NOISE, {N}TH_OE - FLOW
        //Layer : TH_OE - NOISE, TH_OE - KICKPERC, {N}TH_OE - FLOW
        //Layer : TH_OE - NOISE, TH_OE - KICKPERC, {Sunken Pier}TH_OE - BASS, {Outer Expanse|Journey's End}TH_OE - PERC2, {N}TH_OE - FLOW
        //Layer : TH_OE - NOISE, TH_OE - KICKPERC, {Sunken Pier}TH_OE - BASS, {Outer Expanse|Journey's End}TH_OE - PERC2, {Sunken Pier}TH_OE - WAVES, {N}TH_OE - FLOW
        //Layer : TH_OE - NOISE, TH_OE - KICKPERC, {Sunken Pier}TH_OE - BASS, {Outer Expanse|Journey's End}TH_OE - PERC2, {Sunken Pier}TH_OE - WAVES, {D}TH_OE - LEAD, {D}TH_OE - ARP, {N}TH_OE - FLOW
        //Layer : TH_OE - NOISE, TH_OE - KICKPERC, {Sunken Pier}TH_OE - BASS, {Outer Expanse|Journey's End}TH_OE - PERC2, {Sunken Pier}TH_OE - WAVES, {D}TH_OE - LEAD, {D}TH_OE - ARP, {N}TH_OE - FLOW
        regions.put("oe", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_oe_noise")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc"), soundEvents.get("th_oe_perc2")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc"), soundEvents.get("th_oe_perc2")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc"), soundEvents.get("th_oe_perc2"), soundEvents.get("th_oe_lead"), soundEvents.get("th_oe_arp")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc"), soundEvents.get("th_oe_perc2"), soundEvents.get("th_oe_lead"), soundEvents.get("th_oe_arp"))
        )));


        regions.put("oeN", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_flow")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc"), soundEvents.get("th_oe_flow")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc"), soundEvents.get("th_oe_perc2"), soundEvents.get("th_oe_flow")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc"), soundEvents.get("th_oe_perc2"), soundEvents.get("th_oe_flow")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc"), soundEvents.get("th_oe_perc2"), soundEvents.get("th_oe_flow")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc"), soundEvents.get("th_oe_perc2"), soundEvents.get("th_oe_flow"))
        )));
            

        regions.put("oeS", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_oe_noise")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc"), soundEvents.get("th_oe_bass")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc"), soundEvents.get("th_oe_bass"), soundEvents.get("th_oe_waves")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc"), soundEvents.get("th_oe_bass"), soundEvents.get("th_oe_waves"), soundEvents.get("th_oe_lead"), soundEvents.get("th_oe_arp")),
            Arrays.asList(soundEvents.get("th_oe_noise"), soundEvents.get("th_oe_kickperc"), soundEvents.get("th_oe_bass"), soundEvents.get("th_oe_waves"), soundEvents.get("th_oe_lead"), soundEvents.get("th_oe_arp"))
        )));

        //Layer : TH_SI - KICK, TH_SI - PERC1 
        //Layer : TH_SI - KICK, TH_SI - PERC1, TH_SI - SHAKER
        //Layer : TH_SI - KICK, TH_SI - PERC1, TH_SI - NOISE, TH_SI - SHAKER, TH_SI - WEIRD
        //Layer : TH_SI - KICK, TH_SI - PERC1, TH_SI - NOISE, TH_SI - SHAKER, TH_SI - ARPS, TH_SI - BASS, TH_SI - WEIRD, TH_SI -SNARE
        //Layer : TH_SI - PERC1, TH_SI - NOISE, TH_SI - SHAKER, TH_SI - ARPS, TH_SI - BASS, TH_SI - WEIRD, TH_SI -SNARE
        //Layer : TH_SI - PANIC
        regions.put("si", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_si_kick"), soundEvents.get("th_si_perc1")),
            Arrays.asList(soundEvents.get("th_si_kick"), soundEvents.get("th_si_perc1"), soundEvents.get("th_si_shaker")),
            Arrays.asList(soundEvents.get("th_si_kick"), soundEvents.get("th_si_perc1"), soundEvents.get("th_si_noise"), soundEvents.get("th_si_shaker"), soundEvents.get("th_si_weird")),
            Arrays.asList(soundEvents.get("th_si_kick"), soundEvents.get("th_si_perc1"), soundEvents.get("th_si_noise"), soundEvents.get("th_si_shaker"), soundEvents.get("th_si_arps"), soundEvents.get("th_si_bass"), soundEvents.get("th_si_weird"), soundEvents.get("th_si_snare")),
            Arrays.asList(soundEvents.get("th_si_perc1"), soundEvents.get("th_si_noise"), soundEvents.get("th_si_shaker"), soundEvents.get("th_si_arps"), soundEvents.get("th_si_bass"), soundEvents.get("th_si_weird"), soundEvents.get("th_si_snare")),
            Arrays.asList(soundEvents.get("th_si_panic"))
        )));

        //Layer : TH_SL - KICK, TH_SL - BASS
        //Layer : TH_SL - KICK, TH_SL - BASS, TH_SL - PERC2, TH_SL - ARPS
        //Layer : TH_SL - KICK, TH_SL - BASS, TH_SL - PERC2, TH_SL - ARPS, TH_SL - NOISE, TH_SL - SNARE
        //Layer : TH_SL - KICK, TH_SL - BASS, TH_SL - PERC2, TH_SL - ARPS, TH_SL - NOISE, TH_SL - SNARE, TH_SL - PERC2, TH_SL - LEAD
        //Layer : TH_SL - KICK, TH_SL - BASS, TH_SL - PERC2, TH_SL - ARPS, TH_SL - NOISE, TH_SL - SNARE, TH_SL - PERC2, TH_SL - LEAD
        regions.put("sl", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_sl_kick"), soundEvents.get("th_sl_bass")),
            Arrays.asList(soundEvents.get("th_sl_kick"), soundEvents.get("th_sl_bass"), soundEvents.get("th_sl_perc2"), soundEvents.get("th_sl_arps")),
            Arrays.asList(soundEvents.get("th_sl_kick"), soundEvents.get("th_sl_bass"), soundEvents.get("th_sl_perc2"), soundEvents.get("th_sl_arps"), soundEvents.get("th_sl_noise"), soundEvents.get("th_sl_snare")),
            Arrays.asList(soundEvents.get("th_sl_kick"), soundEvents.get("th_sl_bass"), soundEvents.get("th_sl_perc2"), soundEvents.get("th_sl_arps"), soundEvents.get("th_sl_noise"), soundEvents.get("th_sl_snare"), soundEvents.get("th_sl_perc2"), soundEvents.get("th_sl_lead")),
            Arrays.asList(soundEvents.get("th_sl_kick"), soundEvents.get("th_sl_bass"), soundEvents.get("th_sl_perc2"), soundEvents.get("th_sl_arps"), soundEvents.get("th_sl_noise"), soundEvents.get("th_sl_snare"), soundEvents.get("th_sl_perc2"), soundEvents.get("th_sl_lead"))
        )));

        //Layer : TH_SS - NOISE, TH_SS - BASS, TH_SS - KICK
        //Layer : TH_SS - NOISE, TH_SS - BASS, TH_SS - KICK, TH_SS - POP
        //Layer : TH_SS - NOISE, TH_SS - BASS, TH_SS - KICK, TH_SS - POP, TH_SS - LEAD
        //Layer : TH_SS - NOISE, TH_SS - BASS, TH_SS - KICK, TH_SS - POP, TH_SS - LEAD

        regions.put("ss", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_ss_noise"), soundEvents.get("th_ss_bass"), soundEvents.get("th_ss_kick")),
            Arrays.asList(soundEvents.get("th_ss_noise"), soundEvents.get("th_ss_bass"), soundEvents.get("th_ss_kick"), soundEvents.get("th_ss_pop")),
            Arrays.asList(soundEvents.get("th_ss_noise"), soundEvents.get("th_ss_bass"), soundEvents.get("th_ss_kick"), soundEvents.get("th_ss_pop"), soundEvents.get("th_ss_lead")),
            Arrays.asList(soundEvents.get("th_ss_noise"), soundEvents.get("th_ss_bass"), soundEvents.get("th_ss_kick"), soundEvents.get("th_ss_pop"), soundEvents.get("th_ss_lead"))
        )));

        // Layer : TH_SU - KICK, TH_SU - SHAKER
        // Layer : TH_SU - KICK, TH_SU - SHAKER, TH_SU - PERC1, TH_SU - NOISE
        // Layer : TH_SU - KICK, TH_SU - SHAKER, TH_SU - PERC1, TH_SU - NOISE, TH_SU - HITS, TH_SU - BASS
        // Layer : TH_SU - KICK, TH_SU - SHAKER, TH_SU - PERC1, TH_SU - ARPS, TH_SU - NOISE, TH_SU - HITS, TH_SU - BASS
        // Layer : TH_SU - LEAD, TH_SU - BASS
        regions.put("su", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_su_kick"), soundEvents.get("th_su_shaker")),
            Arrays.asList(soundEvents.get("th_su_kick"), soundEvents.get("th_su_shaker"), soundEvents.get("th_su_perc1"), soundEvents.get("th_su_noise")),
            Arrays.asList(soundEvents.get("th_su_kick"), soundEvents.get("th_su_shaker"), soundEvents.get("th_su_perc1"), soundEvents.get("th_su_noise"), soundEvents.get("th_su_hits"), soundEvents.get("th_su_bass")),
            Arrays.asList(soundEvents.get("th_su_kick"), soundEvents.get("th_su_shaker"), soundEvents.get("th_su_perc1"), soundEvents.get("th_su_arps"), soundEvents.get("th_su_noise"), soundEvents.get("th_su_hits"), soundEvents.get("th_su_bass")),
            Arrays.asList(soundEvents.get("th_su_lead"), soundEvents.get("th_su_bass"))
        )));

        //Layer : TH_VS - KICK, TH_VS - SHAKER
        //Layer : TH_VS - KICK, TH_VS - NOISE, TH_VS - SHAKER, TH_VS - BASS, TH_VS - PERC1
        //Layer : TH_VS - KICK, TH_VS - NOISE, TH_VS - SHAKER, TH_VS - BASS, TH_VS - ARPS, TH_VS - PERC1, TH_VS - PERC2
        //Layer : TH_VS - KICK, TH_VS - NOISE, TH_VS - SHAKER, TH_VS - BASS, TH_VS - ARPS, TH_VS - PERC1, TH_VS - PERC2, TH_VS - WEIRD
        //Layer : TH_VS - SYNTH
        regions.put("vs", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("th_vs_kick"), soundEvents.get("th_vs_shaker")),
            Arrays.asList(soundEvents.get("th_vs_kick"), soundEvents.get("th_vs_noise"), soundEvents.get("th_vs_shaker"), soundEvents.get("th_vs_bass"), soundEvents.get("th_vs_perc1")),
            Arrays.asList(soundEvents.get("th_vs_kick"), soundEvents.get("th_vs_noise"), soundEvents.get("th_vs_shaker"), soundEvents.get("th_vs_bass"), soundEvents.get("th_vs_arps"), soundEvents.get("th_vs_perc1"), soundEvents.get("th_vs_perc2")),
            Arrays.asList(soundEvents.get("th_vs_kick"), soundEvents.get("th_vs_noise"), soundEvents.get("th_vs_shaker"), soundEvents.get("th_vs_bass"), soundEvents.get("th_vs_arps"), soundEvents.get("th_vs_perc1"), soundEvents.get("th_vs_perc2"), soundEvents.get("th_vs_weird")),
            Arrays.asList(soundEvents.get("th_vs_synth"))
        )));

        regions.put("test", new Region(Arrays.asList(
            Arrays.asList(soundEvents.get("test1")),
            Arrays.asList(soundEvents.get("test2")),
            Arrays.asList(soundEvents.get("test3")),
            Arrays.asList(soundEvents.get("test4"))
        )));

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

        Region currentRegion = regions.get("test");

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
