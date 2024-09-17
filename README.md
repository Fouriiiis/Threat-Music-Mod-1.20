# Threat Music Mod

Implements the threat music system from Rain World into Minecraft. 

Functions entirely on the client side and is compatible with entities from most mods. It contains all regions from the original game as well as DLC.

Also replaces Minecrafts vanilla ambient music with region specific tracks.

## Features:

- **Recreation**: Provides a (mostly) accurate recreation of the threat music system from Rain World.
- **Compatibility**: Operates client-side, compatible with entities from most mods.
- **Music from All Regions**: Includes base game and DLC regions' music.
- **Day/Night Variants**: Features different themes for day and night in certain areas.
- **Raid Music**: Contains specific themes for raids, currently set to Metropolis.
- **Dynamic Music**: Adjusts based on nearby entities and player actions.
- **Region Customization**: Permits adding custom regions via JSON and .ogg files.
- **Biome Configuration**: Allows for music settings customization per biome.

![Settings Menu for All Biomes](https://cdn.modrinth.com/data/cached_images/9d5fcd8ed1e04370f7c16dd9104cea4d81d56804.png)

- Mod settings can be found under `Music and Sound Options`
 
![Button to adjust mod settings](https://cdn.modrinth.com/data/cached_images/fc032aa3f44fbf542754eff8ce418e842ef992f5.png)

## Installation and Setup:

To use, add the mod file to your mods folder.

For adding custom music, place .ogg files and the corresponding region JSON in the `.Minecraft/ThreatMusic` directory.

The Json file follows the following layout

![JSON File Format for Adding Custom Regions](https://cdn.modrinth.com/data/cached_images/1ce932768263cae19fc7f893acb24febddc9072a.png)

1. "name": (Required) - The display name for the region in settings.
2. "layers": (Optional) - List of lists of potential tracks per layer(one will be randomly chosen for each layer upon playing).
3. "nightLayers": (Optional) - Optional tracks to play during night.
4. "music": (Optional) - Tracks to replace minecrafts vanilla random music.

For questions and support, use the RainWorld MC [Discord]([https://discord.com/invite/yHwnbP69ke](https://discord.com/invite/yHwnbP69ke))

## FAQ:
- **Will this come to forge?** 
  Perhaps, depending on demand and once ported to other versions first.
  
  Currently it will work for forge using Sintra Connector.

- **Other Versions?**
  Support for more versions is planned and will be done at some point in the future.
  
- **I cant hear the music?**
  Threat music is currently tied to the "Players" volume slider, but this may change in the future. Region ambient music is tied to the normal music slider, and if turned off will result in no ambient music.


## Special thanks:
- Ohmanit - Mod Icon
- dicedpixels
- alphappy
