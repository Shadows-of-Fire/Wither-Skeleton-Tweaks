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