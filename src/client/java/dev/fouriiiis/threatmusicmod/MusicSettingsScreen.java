package dev.fouriiiis.threatmusicmod;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class MusicSettingsScreen extends Screen {

    private final Screen parent;
    private DetectionMode currentMode = DetectionMode.SINGLE_REGION;
    private boolean isAmbientMusicEnabled = true;
    private String currentRegion;
    private List<String> regionKeys;
    private ButtonWidget regionSelectButton;
    private TexturedButtonWidget previewButton;
    private static final Identifier SPEAKER_ICON_TEXTURE = new Identifier("threatmusicmod", "textures/gui/speaker_icon.png");

    private ButtonWidget modeButton;
    private ButtonWidget biomeTagsConfigButton;
    private ButtonWidget biomeNamesConfigButton;
    private ButtonWidget ambientMusicToggleButton;
    private ButtonWidget openMusicFolderButton;

    private static final List<DetectionMode> modes = Arrays.asList(DetectionMode.values());

    public MusicSettingsScreen(Screen parent) {
        super(Text.literal("Music Settings"));
        this.parent = parent;
    }

    @Override
    public void close() {
        stopMusic();
        this.client.setScreen(parent);
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = 200;
        int buttonHeight = 20;
        int gap = 10;
        int startY = this.height / 4 - buttonHeight;  // Offset upwards by one button height

        regionKeys = ModSounds.regions.keySet().stream().collect(Collectors.toList());
        currentRegion = regionKeys.get(0);

        // Mode Selection Button
        modeButton = ButtonWidget.builder(Text.literal("Detection Mode: " + currentMode.getDisplayName()), button -> {
            cycleDetectionMode();
            updateModeDependentButtons();
        }).dimensions(this.width / 2 - buttonWidth / 2, startY, buttonWidth, buttonHeight).build();
        addDrawableChild(modeButton);

        // Preview button for the selected region
        previewButton = new TexturedButtonWidget(
            this.width / 2 - buttonWidth / 2 - 30, // X position to the left of the regionSelectButton
            startY + buttonHeight + gap, // Y position
            20, // Button width
            20, // Button height
            0,  // U start
            0,  // V start
            20,  // Hovered V Offset
            SPEAKER_ICON_TEXTURE, // Texture Identifier
            20, // Texture width
            40, // Texture height
            button -> {
                playRegionPreview(currentRegion);
            },
            Text.literal("Preview") // Tooltip text
        );
        addDrawableChild(previewButton);

        // Dynamic stacking region select button
        regionSelectButton = ButtonWidget.builder(Text.literal("Select Region: " + currentRegion), button -> {
            cycleRegion(false);
        }).dimensions(this.width / 2 - buttonWidth / 2, startY + buttonHeight + gap, buttonWidth - 30, buttonHeight).build();
        addDrawableChild(regionSelectButton);

        // Dynamic stacking biome tags config button
        biomeTagsConfigButton = ButtonWidget.builder(Text.literal("Configure Biome Tags"), button -> {
            // Open biome tags configuration screen
            // TODO: Implement biome tags configuration logic
        }).dimensions(this.width / 2 - buttonWidth / 2, 0, buttonWidth, buttonHeight).build();
        addDrawableChild(biomeTagsConfigButton);

        // Dynamic stacking biome names config button
        biomeNamesConfigButton = ButtonWidget.builder(Text.literal("Configure Biome Names"), button -> {
            // Open biome names configuration screen
            // TODO: Implement biome names configuration logic
        }).dimensions(this.width / 2 - buttonWidth / 2, 0, buttonWidth, buttonHeight).build();
        addDrawableChild(biomeNamesConfigButton);

        // Ambient Music Toggle Button (Fixed position)
        ambientMusicToggleButton = ButtonWidget.builder(Text.literal("Ambient Music: " + (isAmbientMusicEnabled ? "ON" : "OFF")), button -> {
            isAmbientMusicEnabled = !isAmbientMusicEnabled;
            ambientMusicToggleButton.setMessage(Text.literal("Ambient Music: " + (isAmbientMusicEnabled ? "ON" : "OFF")));
            // TODO: Implement ambient music toggle logic
        }).dimensions(this.width / 2 - buttonWidth / 2, startY + (buttonHeight + gap) * 4, buttonWidth, buttonHeight).build();
        addDrawableChild(ambientMusicToggleButton);

        // Open Music Folder Button (Fixed position)
        openMusicFolderButton = ButtonWidget.builder(Text.literal("Open Music Folder"), button -> {
            openMusicFolder();
        }).dimensions(this.width / 2 - buttonWidth / 2, startY + (buttonHeight + gap) * 5, buttonWidth, buttonHeight).build();
        addDrawableChild(openMusicFolderButton);

        // Done Button (Fixed position)
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> {
            stopMusic();
            this.client.setScreen(this.parent);
        }).dimensions(this.width / 2 - buttonWidth / 2, startY + (buttonHeight + gap) * 6, buttonWidth, buttonHeight).build());

        updateModeDependentButtons();
    }

    private void cycleDetectionMode() {
        int currentIndex = modes.indexOf(currentMode);
        int nextIndex = (currentIndex + 1) % modes.size();
        currentMode = modes.get(nextIndex);
        modeButton.setMessage(Text.literal("Detection Mode: " + currentMode.getDisplayName()));
        updateModeDependentButtons();
    }

    private void updateModeDependentButtons() {
        int nextY = modeButton.getY() + modeButton.getHeight() + 10;

        // Position region select button
        regionSelectButton.visible = currentMode == DetectionMode.SINGLE_REGION;
        previewButton.visible = currentMode == DetectionMode.SINGLE_REGION;

        if (regionSelectButton.visible) {
            regionSelectButton.setY(nextY);
            previewButton.setY(nextY);
            nextY += regionSelectButton.getHeight() + 10;
        }

        // Position biome tags config button
        biomeTagsConfigButton.visible = currentMode == DetectionMode.BIOME_TAGS || currentMode == DetectionMode.TAGS_AND_NAMES;
        if (biomeTagsConfigButton.visible) {
            biomeTagsConfigButton.setY(nextY);
            nextY += biomeTagsConfigButton.getHeight() + 10;
        }

        // Position biome names config button
        biomeNamesConfigButton.visible = currentMode == DetectionMode.BIOME_NAMES || currentMode == DetectionMode.TAGS_AND_NAMES;
        if (biomeNamesConfigButton.visible) {
            biomeNamesConfigButton.setY(nextY);
        }
    }

    private void cycleRegion(boolean reverse) {
        int currentIndex = regionKeys.indexOf(currentRegion);
        int nextIndex = reverse ? (currentIndex - 1) : (currentIndex + 1);

        if (nextIndex < 0) {
            nextIndex = regionKeys.size() - 1;
        } else if (nextIndex >= regionKeys.size()) {
            nextIndex = 0;
        }

        currentRegion = regionKeys.get(nextIndex);
        regionSelectButton.setMessage(Text.literal("Select Region: " + currentRegion));
    }

    private void playRegionPreview(String regionKey) {
        Region demo = ModSounds.regions.get(regionKey);
        ThreatMusicModClient.getThreatTracker().playDemo(demo, client);
    }

    private void openMusicFolder() {
        Path musicFolderPath = client.runDirectory.toPath().resolve("music");
        Util.getOperatingSystem().open(musicFolderPath.toFile());
    }

    private void stopMusic() {
        MusicTracker musicTracker = client.getMusicTracker();
        musicTracker.stop();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    private enum DetectionMode {
        SINGLE_REGION("Single Region"),
        BIOME_TAGS("Biome Tags"),
        BIOME_NAMES("Biome Names"),
        TAGS_AND_NAMES("Tags + Names");

        private final String displayName;

        DetectionMode(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return I18n.translate(displayName);
        }
    }
}
