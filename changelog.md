## 9.1.0
* Removed "Shard Value" config option. The fragment->skull recipe is now a json.
* EndilCrafter: Added Japanese translation.

## 9.0.1
* Removed forge dependency line from the mods.toml and marked as Forge and NeoForge for CF.
  * The dependency will be added back and the Forge marker will be removed once CF supports Neo correctly.

## 9.0.0
* Updated to 1.20.1

## 8.0.4
* Actually fixed the worldgen crash in all circumstances.
  * It turns out `Mob#convertTo` actually adds the new entity to the world, which breaks things during worldgen.

## 8.0.3
* Fixed a worldgen crash.

## 8.0.2
* Added code to fire LivingConversionEvent.Post after the skeletons are converted.

## 8.0.1
* Changed conversion code to use Mob#convertTo instead of creating a brand new entity.
  * This should allow me to track conversions better for things such as Gateways to Eternity.
* FITFC: Added Brazillian translation.

## 8.0.0
* Updated to 1.19.2

## 7.1.3
* Updated to the new Placebo Recipe System.
  * Should finally fix weird issues with the fragment recipe being invalid / broken.
* Altegar: Added Ukranian translation.

## 7.1.2
* Fixed the Immolation Blade attack speed config.
  * The default config value has been updated, check / regenerate your config files!

## 7.1.1
* Rebuilt for 1.18.2

## 7.1.0
* Rewrote the config to use the Placebo Config system instead of the TOML.
  * This means all existing configs will be invalidated!
* Added config options for Immolation Blade stats.
* Converted Immolation Blade recipes to json.

## 7.0.1
* Fixed a parse error with the loot modifier.

## 7.0.0
* Update to 1.18.1

## 6.0.0
* Update to 1.17.1

## 5.4.1
* Fixed a crashed caused by not null-checking something in a loot modifier.

## 5.4.0
* Updated to official mappings and ForgeFramework
* Changed the immolation skull drop and the fragment drop to use global loot modifiers.