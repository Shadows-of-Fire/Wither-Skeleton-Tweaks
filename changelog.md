## 11.0.1
* Fixed an issue where Immolation Blades, combined with other damage amplifications, could cause enemies to die twice.

## 11.0.0
* Ported to Minecraft 26.1.2

## 10.1.1
* Updated to Placebo 9.9.0.

## 10.1.0
* Modernized the Immolation Blade. The blade now only has a single variant with a new (very appropriate) texture.
  * The old textures were from uh... 2016. They had a good run.
  * Existing blades will be migrated to the new one.
* Fixed the Immolation Blade not being enchantable.
* Fixed the Immolation Blade having 4.0 attack speed (and ignoring the attack speed config).

## 10.0.2
* Fixed recipes not loading due to the path change from `recipes` to `recipe`.

## 10.0.1
* Smoong: Added Korean translation.
* RuyaSavascisi: Added Turkish translation.

## 10.0.0
* Updated to Minecraft 1.21.1.

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