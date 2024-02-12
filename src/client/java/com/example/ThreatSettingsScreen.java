package com.example;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ThreatSettingsScreen extends Screen {

    private BiomeListWidget biomeListWidget;
    private final Screen parent;

    public ThreatSettingsScreen(Screen parent) {
        super(Text.literal("Threat Music Settings"));
        this.parent = parent;
    }

    @Override
    public void close() {
        ModSounds.saveBiomeRegionKeys();
        //stop the threat music
        //get the music tracker
        MusicTracker musicTracker = client.getMusicTracker();
        //stop the threat music
        musicTracker.stop();
        client.setScreen(parent);
    }

    @Override
    protected void init() {
        super.init();
        biomeListWidget = new BiomeListWidget(client, width, height, 32, height - 32);

        ModSounds.biomeRegionKeys.keySet().forEach(biomeId ->
                biomeListWidget.addBiomeEntry(new BiomeListEntry(biomeId.toString())));

        addDrawableChild(biomeListWidget);

        //Close screen
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> {
            ModSounds.saveBiomeRegionKeys();
            //stop the threat music
            MusicTracker musicTracker = client.getMusicTracker();
            //stop the threat music
            musicTracker.stop();
            this.client.setScreen(this.parent);
        }).dimensions(this.width / 2 - 155 + 160, this.height - 29, 150, 20).build());

        //Reset the region keys to their default values
        addDrawableChild(ButtonWidget.builder(Text.literal("Reset"), button -> {
            //for each biome, set the region key to the default value using defaultBiomeRegionKeys
            ModSounds.defaultBiomeRegionKeys.forEach((biomeId, regionKey) -> {
                ModSounds.biomeRegionKeys.put(biomeId, regionKey);
                ModSounds.savedBiomeRegionKeys.put(biomeId, regionKey);
            });
            //refresh the screen to update the buttons
            this.client.setScreen(new ThreatSettingsScreen(this.parent));
        }).dimensions(this.width / 2 - 155, this.height - 29, 150, 20).build());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        return biomeListWidget.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        biomeListWidget.render(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    private static String getUserFriendlyBiomeName(Identifier biomeId) {
        String translationKey = "biome." + biomeId.getNamespace() + "." + biomeId.getPath();
        String translatedName = I18n.translate(translationKey);
        return translatedName.isEmpty() ? biomeId.toString() : translatedName;
    }

    public class BiomeListWidget extends ElementListWidget<BiomeListEntry> {
      public BiomeListWidget(MinecraftClient client, int width, int height, int top, int bottom) {
          super(client, width, height, top, bottom, 25);
      }
  
      public void addBiomeEntry(BiomeListEntry entry) {
          this.addEntry(entry);
      }

        // @Override
        // protected boolean isSelectedEntry(int index) {
        //     return true;
        // }

        @Override
        public int getRowWidth() {
            return super.getRowWidth()+52;
        }
    }

    private class BiomeListEntry extends ElementListWidget.Entry<BiomeListEntry> {
        private final Text label;
        private final ButtonWidget cycleRegionButton;
        private final ButtonWidget extraButton;
        private List<String> regionKeys;
        private static final Identifier SPEAKER_ICON_TEXTURE = new Identifier("modid", "textures/gui/speaker_icon.png");

        public BiomeListEntry(String biomeId) {
            this.label = Text.literal(getUserFriendlyBiomeName(new Identifier(biomeId)));
            this.cycleRegionButton = ButtonWidget.builder(Text.literal(ModSounds.biomeRegionKeys.get(biomeId)), button -> {
                cycleRegion(biomeId);
            })
            .position(0, 0)
            .size(100, 20)
            .build();

            // New extra button initialization with a placeholder action
            //src\main\resources\assets\modid\textures\Speaker_Icon.png

            this.extraButton = new TexturedButtonWidget(
                0, // Use the x position defined above
                0, // Use the y position defined above
                20, // Button width
                20, // Button height
                0,   // U start
                0,   // V start
                20,   // Hovered V Offset (if your texture has a hover state, otherwise 0)
                SPEAKER_ICON_TEXTURE, // Texture Identifier
                20, // Texture width (should match the actual texture size)
                40, // Texture height (should match the actual texture size)
                button -> { /* Your press action code here */ 
                    //get the region
                    String key = ModSounds.biomeRegionKeys.get(biomeId);
                    //print the key
                    System.out.println("key: " + key);
                    Region demo = ModSounds.regions.get(key);
                    System.out.println("demo: " + demo);
                    ExampleModClient.getThreatTracker().playDemo(demo, client);
                    //print the threat level
                    
                },
                Text.literal("Tooltip") // Tooltip text
            );

            

            //regionKeys are the keyset of the biomeRegionKeys map
            regionKeys = ModSounds.regions.keySet().stream().toList();
        }

        private void cycleRegion(String biomeId) {
            // switch to the next region, cycling back to the first if necessary
            int currentIndex = regionKeys.indexOf(ModSounds.biomeRegionKeys.get(biomeId));
            int nextIndex = (currentIndex + 1) % regionKeys.size();
            ModSounds.biomeRegionKeys.put(biomeId, regionKeys.get(nextIndex));
            ModSounds.savedBiomeRegionKeys.put(biomeId, regionKeys.get(nextIndex));
            cycleRegionButton.setMessage(Text.literal(regionKeys.get(nextIndex)));
        }

        @Override
        public List<? extends Element> children() {
            return List.of(cycleRegionButton, extraButton);
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return List.of(cycleRegionButton, extraButton);
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float delta) {
            int middleX = width / 2;

            extraButton.setY(y);
            extraButton.setX(x);

            // Update the cycleRegionButton's position
            cycleRegionButton.setY(y);
            cycleRegionButton.setX(x + 25);

            extraButton.render(context, mouseX, mouseY, delta);
            cycleRegionButton.render(context, mouseX, mouseY, delta);

            int labelX = middleX;
            // + 20;
            int labelY = y + (entryHeight - 8) / 2;
            context.drawTextWithShadow(textRenderer, label, labelX, labelY, 0xFFFFFF);
        }
    }
}