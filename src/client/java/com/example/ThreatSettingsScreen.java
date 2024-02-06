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
import net.minecraft.client.resource.language.I18n;
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
        client.setScreen(parent);
    }

    @Override
    protected void init() {
        super.init();
        biomeListWidget = new BiomeListWidget(client, width + 50, height, 32, height - 32);

        ModSounds.biomeRegionKeys.keySet().forEach(biomeId ->
                biomeListWidget.addBiomeEntry(new BiomeListEntry(biomeId.toString())));

        addDrawableChild(biomeListWidget);

        //Close screen
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> {
            ModSounds.saveBiomeRegionKeys();
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
          this.addEntry(entry); // This calls the protected addEntry method
      }
  }

    private class BiomeListEntry extends ElementListWidget.Entry<BiomeListEntry> {
        private final Text label;
        private final ButtonWidget button;
        private List<String> regionKeys;

        public BiomeListEntry(String biomeId) {
            this.label = Text.literal(getUserFriendlyBiomeName(new Identifier(biomeId)));
            this.button = ButtonWidget.builder(Text.literal(ModSounds.biomeRegionKeys.get(biomeId)), button -> {
                cycleRegion(biomeId);
            })
            .position(0, 0)
            .size(100, 20)
            .build();

            regionKeys = ModSounds.regions.keySet().stream().toList();
        }

        private void cycleRegion(String biomeId) {
            // switch to the next region, cycling back to the first if necessary
            int currentIndex = regionKeys.indexOf(ModSounds.biomeRegionKeys.get(biomeId));
            int nextIndex = (currentIndex + 1) % regionKeys.size();
            ModSounds.biomeRegionKeys.put(biomeId, regionKeys.get(nextIndex));
            ModSounds.savedBiomeRegionKeys.put(biomeId, regionKeys.get(nextIndex));
            button.setMessage(Text.literal(regionKeys.get(nextIndex)));
        }

        @Override
        public List<? extends Element> children() {
            return List.of(button);
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return List.of(button);
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float delta) {
            int middleX = width / 2;
            button.setY(y);
            button.setX(x);
            button.render(context, mouseX, mouseY, delta);

            int labelX = middleX + 20;
            int labelY = y + (entryHeight - 8) / 2;
            
            context.drawTextWithShadow(textRenderer, label, labelX, labelY, 0xFFFFFF);
        }
    }
}