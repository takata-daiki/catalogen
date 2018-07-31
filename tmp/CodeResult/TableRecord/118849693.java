package com.cniska.dungeon.core;

import com.cniska.dungeon.util.XMLReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Data manager.
 * Allows for loading and storing data from the database.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class DataManager
{
	// ----------
	// Properties
	// ----------

	private static final DataManager instance = new DataManager();

	private static final String XML_ARMOR = "/com/cniska/dungeon/resources/data/Armor.xml";
	private static final String XML_CONSUMABLES = "/com/cniska/dungeon/resources/data/Consumables.xml";
	private static final String XML_HOSTILES = "/com/cniska/dungeon/resources/data/Hostiles.xml";
	private static final String XML_LOOT = "/com/cniska/dungeon/resources/data/Loot.xml";
	private static final String XML_MAPS = "/com/cniska/dungeon/resources/data/Maps.xml";
	private static final String XML_PLAYER = "/com/cniska/dungeon/resources/data/Player.xml";
	private static final String XML_POWERUP = "/com/cniska/dungeon/resources/data/Powerups.xml";
	private static final String XML_TILES = "/com/cniska/dungeon/resources/data/Tiles.xml";
	private static final String XML_TREASURES = "/com/cniska/dungeon/resources/data/Treasures.xml";
	private static final String XML_WEAPONS = "/com/cniska/dungeon/resources/data/Weapons.xml";

	private HashMap<String, ArmorRecord> armorRecords;
	private HashMap<String, ConsumableRecord> consumableRecords;
	private HashMap<String, MapRecord> mapRecords;
	private HashMap<String, HostileRecord> hostileRecords;
	private HashMap<String, LootTableRecord> lootTableRecords;
	private HashMap<String, PowerupTableRecord> powerupTableRecords;
	private HashMap<String, TilesetRecord> tilesetRecords;
	private HashMap<String, TreasureRecord> treasureRecords;
	private HashMap<String, WeaponRecord> weaponRecords;
	private PlayerRecord playerRecord;

	private volatile boolean loaded = false;

	// -------
	// Methods
	// -------

	/**
	 * Creates the manager.
	 * Private to enforce the singleton pattern.
	 */
	private DataManager()
	{
		super();

		armorRecords = new HashMap<String, ArmorRecord>();
		consumableRecords = new HashMap<String, ConsumableRecord>();
		mapRecords = new HashMap<String, MapRecord>();
		hostileRecords = new HashMap<String, HostileRecord>();
		lootTableRecords = new HashMap<String, LootTableRecord>();
		powerupTableRecords = new HashMap<String, PowerupTableRecord>();
		tilesetRecords = new HashMap<String, TilesetRecord>();
		treasureRecords = new HashMap<String, TreasureRecord>();
		weaponRecords = new HashMap<String, WeaponRecord>();
	}

	/**
	 * Returns the single instance of this object.
	 * @return The instance.
	 */
	public static DataManager getInstance()
	{
		return instance;
	}

	/**
	 * Loads the data from the database.
	 */
	public void loadData()
	{
		loadArmor();
		loadConsumables();
		loadMaps();
		loadHostiles();
		loadTreasures();
		loadLootTables();
		loadPowerupTables();
		loadTiles();
		loadWeapons();
		loadPlayer();

		loaded = true;
	}

	/**
	 * Loads the armor records.
	 */
	private void loadArmor()
	{
		XMLReader reader = new XMLReader(XML_ARMOR);
		Document document = reader.getDocument();

		NodeList armor = document.getElementsByTagName("armor");
		int armorCount = armor.getLength();

		for (int i = 0; i < armorCount; i++)
		{
			Node armorNode = armor.item(i);

			if (armorNode != null && armorNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element armorElement = (Element) armorNode;
				ArmorRecord record = new ArmorRecord();
				record.name = reader.getTagValue("name", armorElement);
				record.image = reader.getTagValue("image", armorElement);
				record.quality = reader.getTagValue("quality", armorElement);
				record.itemLevel = Integer.parseInt(reader.getTagValue("itemLevel", armorElement));
				record.armorValue = Integer.parseInt(reader.getTagValue("armorValue", armorElement));
				armorRecords.put(armorElement.getAttribute("id"), record);
			}
		}
	}

	/**
	 * Loads the consumable records.
	 */
	private void loadConsumables()
	{
		XMLReader reader = new XMLReader(XML_CONSUMABLES);
		Document document = reader.getDocument();

		NodeList consumables = document.getElementsByTagName("consumable");
		int consumableCount = consumables.getLength();

		for (int i = 0; i < consumableCount; i++)
		{
			Node consumableNode = consumables.item(i);

			if (consumableNode != null && consumableNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element consumableElement = (Element) consumableNode;
				ConsumableRecord record = new ConsumableRecord();
				record.name = reader.getTagValue("name", consumableElement);
				record.quality = reader.getTagValue("quality", consumableElement);
				record.itemLevel = Integer.parseInt(reader.getTagValue("itemLevel", consumableElement));
				consumableRecords.put(consumableElement.getAttribute("id"), record);
			}
		}
	}

	/**
	 * Loads the map records.
	 */
	private void loadMaps()
	{
		XMLReader reader = new XMLReader(XML_MAPS);
		Document document = reader.getDocument();
		
		NodeList maps = document.getElementsByTagName("map");
		int mapCount = maps.getLength();

		for (int i = 0; i < mapCount; i++)
		{
			Node mapNode = maps.item(i);

			if (mapNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element mapElement = (Element) mapNode;
				MapRecord mapRecord = new MapRecord();
				mapRecord.id = mapElement.getAttribute("id");
				mapRecord.width = Integer.parseInt(mapElement.getAttribute("width"));
				mapRecord.height = Integer.parseInt(mapElement.getAttribute("height"));

				// Load the hostiles for this map.

				NodeList hostilesNodeList = mapElement.getElementsByTagName("hostiles");
				if (hostilesNodeList.getLength() > 0)
				{
					Node node = hostilesNodeList.item(0);

					if (node != null && node.getNodeType() == Node.ELEMENT_NODE)
					{
						Element element = (Element) node;
						mapRecord.hostileCount = Integer.parseInt(element.getAttribute("count"));

						NodeList characters = element.getElementsByTagName("hostile");
						int characterCount = characters.getLength();

						for (int j = 0; j < characterCount; j++)
						{
							Node characterNode = characters.item(j);

							if (characterNode != null && characterNode.getNodeType() == Node.ELEMENT_NODE)
							{
								Element characterElement = (Element) characterNode;
								CharacterEncounterRecord characterRecord = new CharacterEncounterRecord();
								characterRecord.type = characterElement.getAttribute("type");
								characterRecord.id = Integer.parseInt(characterElement.getAttribute("id"));
								characterRecord.level = Integer.parseInt(characterElement.getAttribute("level"));
								characterRecord.maxCount = Integer.parseInt(characterElement.getAttribute("maxCount"));
								characterRecord.oddment = Integer.parseInt(characterElement.getAttribute("oddment"));
								mapRecord.hostileRecords.add(characterRecord);
							}
						}
					}
				}

				// Load the treasures for this encounter.

				NodeList treasureNodeList = mapElement.getElementsByTagName("treasures");
				if (treasureNodeList.getLength() > 0)
				{
					Node node = treasureNodeList.item(0);

					if (node != null && node.getNodeType() == Node.ELEMENT_NODE)
					{
						Element element = (Element) node;
						mapRecord.treasureCount = Integer.parseInt(element.getAttribute("count"));

						NodeList treasure = element.getElementsByTagName("treasure");
						int treasureCount = treasure.getLength();

						for (int j = 0; j < treasureCount; j++)
						{
							Node treasureNode = treasure.item(j);

							if (treasureNode != null && treasureNode.getNodeType() == Node.ELEMENT_NODE)
							{
								Element treasureElement = (Element) treasureNode;
								TreasureEncounterRecord treasureRecord = new TreasureEncounterRecord();
								treasureRecord.id = Integer.parseInt(treasureElement.getAttribute("id"));
								treasureRecord.oddment = Integer.parseInt(treasureElement.getAttribute("oddment"));
								mapRecord.treasureRecords.add(treasureRecord);
							}
						}
					}
				}

				mapRecords.put(mapElement.getAttribute("id"), mapRecord);
			}
		}
	}

	/**
	 * Loads the hostile records.
	 */
	private void loadHostiles()
	{
		XMLReader reader = new XMLReader(XML_HOSTILES);
		Document document = reader.getDocument();

		NodeList hostiles = document.getElementsByTagName("hostile");
		int hostileCount = hostiles.getLength();

		for (int i = 0; i < hostileCount; i++)
		{
			Node hostileNode = hostiles.item(i);

			if (hostileNode != null && hostileNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element hostileElement = (Element) hostileNode;
				HostileRecord hostileRecord = new HostileRecord();
				hostileRecord.name = reader.getTagValue("name", hostileElement);
				hostileRecord.image = reader.getTagValue("image", hostileElement);

				// Parse the constant stats.
				hostileRecord.attackSpeed = Integer.parseInt(reader.getTagValue("attackSpeed", hostileElement));
				hostileRecord.movementSpeed = Integer.parseInt(reader.getTagValue("movementSpeed", hostileElement));
				hostileRecord.viewRange = Integer.parseInt(reader.getTagValue("viewRange", hostileElement));

				// Parse the base stats which increase per level.
				NodeList statsNodeList = hostileElement.getElementsByTagName("stats");

				if (statsNodeList.getLength() > 0)
				{
					Node statsNode = statsNodeList.item(0);

					if (statsNode != null && statsNode.getNodeType() == Node.ELEMENT_NODE)
					{
						Element statsElement = (Element) statsNode;

						// Parse the hostile health.

						hostileRecord.health = Integer.parseInt(reader.getTagValue("health", statsElement));
						NodeList healthNodeList = statsElement.getElementsByTagName("health");

						if (healthNodeList.getLength() > 0)
						{
							Node healthNode = healthNodeList.item(0);

							if (healthNode != null && healthNode.getNodeType() == Node.ELEMENT_NODE)
							{
								Element healthElement = (Element) healthNode;
								String incAttr = healthElement.getAttribute("increase");
								hostileRecord.healthInc = incAttr != "" ? Float.parseFloat(incAttr) : null;
							}
						}

						// Parse the hostile min and max damage.

						NodeList minDamageNodeList = statsElement.getElementsByTagName("minDamage");
						hostileRecord.minDamage = Integer.parseInt(reader.getTagValue("minDamage", statsElement));

						if (minDamageNodeList.getLength() > 0)
						{
							Node node = minDamageNodeList.item(0);

							if (node != null && node.getNodeType() == Node.ELEMENT_NODE)
							{
								Element element = (Element) node;
								String incAttr = element.getAttribute("increase");
								hostileRecord.minDamageInc = incAttr != "" ? Float.parseFloat(incAttr) : null;
							}
						}

						NodeList maxDamageNodeList = statsElement.getElementsByTagName("maxDamage");
						hostileRecord.maxDamage = Integer.parseInt(reader.getTagValue("maxDamage", statsElement));

						if (maxDamageNodeList.getLength() > 0)
						{
							Node node = maxDamageNodeList.item(0);

							if (node != null && node.getNodeType() == Node.ELEMENT_NODE)
							{
								Element element = (Element) node;
								String incAttr = element.getAttribute("increase");
								hostileRecord.maxDamageInc = incAttr != "" ? Float.parseFloat(incAttr) : null;
							}
						}

						// Parse the hostile experience value.

						NodeList expValueNodeList = statsElement.getElementsByTagName("maxDamage");
						hostileRecord.expValue = Integer.parseInt(reader.getTagValue("expValue", statsElement));

						if (expValueNodeList.getLength() > 0)
						{
							Node node = expValueNodeList.item(0);

							if (node != null && node.getNodeType() == Node.ELEMENT_NODE)
							{
								Element element = (Element) node;
								String incAttr = element.getAttribute("increase");
								hostileRecord.expValueInc = incAttr != "" ? Float.parseFloat(incAttr) : null;
							}
						}
					}
				}

				// Parse the min and max levels.
				hostileRecord.minLevel = Integer.parseInt(reader.getTagValue("minLevel", hostileElement));
				hostileRecord.maxLevel = Integer.parseInt(reader.getTagValue("maxLevel", hostileElement));

				// Parse the loot and powerup tables.
				hostileRecord.lootTable = reader.getTagValue("lootTable", hostileElement);
				hostileRecord.powerupTable = reader.getTagValue("powerupTable", hostileElement);

				hostileRecords.put(hostileElement.getAttribute("id"), hostileRecord);
			}
		}
	}

	/**
	 * Loads the treasure records.
	 */
	private void loadTreasures()
	{
		XMLReader reader = new XMLReader(XML_TREASURES);
		Document document = reader.getDocument();

		NodeList treasure = document.getElementsByTagName("treasure");
		int treasureCount = treasure.getLength();

		for (int i = 0; i < treasureCount; i++)
		{
			Node treasureNode = treasure.item(i);

			if (treasureNode != null && treasureNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element treasureElement = (Element) treasureNode;
				TreasureRecord treasureRecord = new TreasureRecord();
				treasureRecord.name = reader.getTagValue("name", treasureElement);
				treasureRecord.image = reader.getTagValue("image", treasureElement);
				treasureRecord.lootTable = reader.getTagValue("lootTable", treasureElement);
				treasureRecords.put(treasureElement.getAttribute("id"), treasureRecord);
			}
		}
	}

	/**
	 * Loads the loot table records.
	 */
	private void loadLootTables()
	{
		XMLReader reader = new XMLReader(XML_LOOT);
		Document document = reader.getDocument();

		NodeList lootTables = document.getElementsByTagName("lootTable");
		final int tableCount = lootTables.getLength();

		for (int i = 0; i < tableCount; i++)
		{
			Node tableNode = lootTables.item(i);

			if (tableNode != null && tableNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element tableElement = (Element) tableNode;
				LootTableRecord tableRecord = new LootTableRecord();

				NodeList loot = tableElement.getChildNodes();
				int lootCount = loot.getLength();

				for (int j = 0; j < lootCount; j++)
				{
					Node lootNode = loot.item(j);

					if (lootNode != null && lootNode.getNodeType() == Node.ELEMENT_NODE)
					{
						Element lootElement = (Element) lootNode;
						LootRecord lootRecord = new LootRecord();
						lootRecord.type = lootElement.getAttribute("type");
						lootRecord.id = Integer.parseInt(lootElement.getAttribute("id"));
						lootRecord.oddment = Integer.parseInt(lootElement.getAttribute("oddment"));
						tableRecord.loot.add(lootRecord);
					}
				}

				lootTableRecords.put(tableElement.getAttribute("id"), tableRecord);
			}
		}
	}

	/**
	 * Loads the powerup table records.
	 */
	private void loadPowerupTables()
	{
		XMLReader reader = new XMLReader(XML_POWERUP);
		Document document = reader.getDocument();

		NodeList powerupTables = document.getElementsByTagName("powerupTable");
		final int tableCount = powerupTables.getLength();

		for (int i = 0; i < tableCount; i++)
		{
			Node tableNode = powerupTables.item(i);

			if (tableNode != null && tableNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element tableElement = (Element) tableNode;
				PowerupTableRecord tableRecord = new PowerupTableRecord();

				NodeList powerups = tableElement.getChildNodes();
				int lootCount = powerups.getLength();

				for (int j = 0; j < lootCount; j++)
				{
					Node powerupNode = powerups.item(j);

					if (powerupNode != null && powerupNode.getNodeType() == Node.ELEMENT_NODE)
					{
						Element powerupElement = (Element) powerupNode;
						PowerupRecord powerupRecord = new PowerupRecord();
						powerupRecord.id = Integer.parseInt(powerupElement.getAttribute("id"));
						powerupRecord.oddment = Integer.parseInt(powerupElement.getAttribute("oddment"));
						tableRecord.powerups.add(powerupRecord);
					}
				}

				powerupTableRecords.put(tableElement.getAttribute("id"), tableRecord);
			}
		}
	}

	/**
	 * Loads the tile records.
	 */
	private void loadTiles()
	{
		XMLReader reader = new XMLReader(XML_TILES);
		Document document = reader.getDocument();

		NodeList tilesets = document.getElementsByTagName("tileset");
		final int tilesetCount = tilesets.getLength();

		for (int i = 0; i < tilesetCount; i++)
		{
			Node tilesetNode = tilesets.item(i);

			if (tilesetNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element tilesetElement = (Element) tilesetNode;
				TilesetRecord tilesetRecord = new TilesetRecord();

				NodeList tiles = document.getElementsByTagName("tile");
				final int tileCount = tiles.getLength();

				for (int j = 0; j < tileCount; j++)
				{
					Node tileNode = tiles.item(j);

					if (tileNode.getNodeType() == Node.ELEMENT_NODE)
					{
						Element tileElement = (Element) tileNode;
						TileRecord tileRecord = new TileRecord();
						tileRecord.name = reader.getTagValue("name", tileElement);
						tileRecord.priority = Integer.parseInt(reader.getTagValue("priority", tileElement));

						NodeList images = tileElement.getElementsByTagName("image");
						int imageCount = images.getLength();

						for (int k = 0; k < imageCount; k++)
						{
							Node imageNode = images.item(k);

							if (imageNode.getNodeType() == Node.ELEMENT_NODE)
							{
								Element imageElement = (Element) imageNode;
								ImageRecord imageRecord = new ImageRecord();
								imageRecord.filename = imageElement.getChildNodes().item(0).getNodeValue();
								imageRecord.oddment = Integer.parseInt(imageElement.getAttribute("oddment"));
								tileRecord.images.add(imageRecord);
								tilesetRecord.tiles.put(tileElement.getAttribute("id"), tileRecord);
							}
						}
					}
				}

				tilesetRecords.put(tilesetElement.getAttribute("id"), tilesetRecord);
			}
		}
	}

	/**
	 * Loads the player record.
	 */
	private void loadPlayer()
	{
		XMLReader reader = new XMLReader(XML_PLAYER);
		Document document = reader.getDocument();

		NodeList player = document.getElementsByTagName("player");
		int playerCount = player.getLength();

		if (playerCount > 0)
		{
			Node playerNode = player.item(0);

			if (playerNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element playerElement = (Element) playerNode;
				PlayerRecord playerRecord = new PlayerRecord();

				// Parse the images.
				NodeList imageNodeList = playerElement.getElementsByTagName("image");
				final int imageCount = imageNodeList.getLength();

				for (int i = 0; i < imageCount; i++)
				{
					Node imageNode = imageNodeList.item(i);

					if (imageNode != null && imageNode.getNodeType() == Node.ELEMENT_NODE)
					{
						imageNode.getChildNodes();
						Element imageElement = (Element) imageNode;
						playerRecord.images.add(imageElement.getFirstChild().getNodeValue());
					}
				}

				// Parse the constant stats.
				playerRecord.attackSpeed = Integer.parseInt(reader.getTagValue("attackSpeed", playerElement));
				playerRecord.movementSpeed = Integer.parseInt(reader.getTagValue("movementSpeed", playerElement));
				playerRecord.viewRange = Integer.parseInt(reader.getTagValue("viewRange", playerElement));

				// Parse the player stats.

				NodeList statsNodeList = playerElement.getElementsByTagName("stats");

				if (statsNodeList.getLength() > 0)
				{
					Node statsNode = statsNodeList.item(0);

					if (statsNode != null && statsNode.getNodeType() == Node.ELEMENT_NODE)
					{
						Element statsElement = (Element) statsNode;

						// Parse the player health.

						playerRecord.health = Integer.parseInt(reader.getTagValue("health", statsElement));
						NodeList healthNodeList = statsElement.getElementsByTagName("health");

						if (healthNodeList.getLength() > 0)
						{
							Node healthNode = healthNodeList.item(0);

							if (healthNode != null && healthNode.getNodeType() == Node.ELEMENT_NODE)
							{
								Element healthElement = (Element) healthNode;
								String incAttr = healthElement.getAttribute("increase");
								playerRecord.healthInc = incAttr != "" ? Float.parseFloat(incAttr) : null;
							}
						}

						playerRecord.minDamage = Integer.parseInt(reader.getTagValue("minDamage", statsElement));
						playerRecord.maxDamage = Integer.parseInt(reader.getTagValue("maxDamage", statsElement));
					}
				}

				// Parse the min and max levels.
				playerRecord.minLevel = Integer.parseInt(reader.getTagValue("minLevel", playerElement));
				playerRecord.maxLevel = Integer.parseInt(reader.getTagValue("maxLevel", playerElement));

				// Parse the experience.
				NodeList experienceNodeList = playerElement.getElementsByTagName("experience");

				if (experienceNodeList.getLength() > 0)
				{
					Node experienceNode = experienceNodeList.item(0);

					if (experienceNode != null && experienceNode.getNodeType() == Node.ELEMENT_NODE)
					{
						Element experienceElement = (Element) experienceNode;
						playerRecord.expFirstLevel = Integer.parseInt(experienceElement.getAttribute("firstLevel"));
						playerRecord.expInc = Float.parseFloat(experienceElement.getAttribute("increase"));
					}
				}

				// Parse the starting items.
				NodeList items = playerElement.getElementsByTagName("item");
				int equipmentCount = items.getLength();

				for (int i = 0; i < equipmentCount; i++)
				{
					Node itemNode = items.item(i);

					if (itemNode.getNodeType() == Node.ELEMENT_NODE)
					{
						Element itemElement = (Element) itemNode;
						ItemRecord itemRecord = new ItemRecord();
						itemRecord.type = itemElement.getAttribute("type");
						itemRecord.id = itemElement.getAttribute("id");
						playerRecord.items.add(itemRecord);
					}
				}

				this.playerRecord = playerRecord;
			}
		}
	}

	/**
	 * Loads the weapon records.
	 */
	private void loadWeapons()
	{
		XMLReader reader = new XMLReader(XML_WEAPONS);
		Document document = reader.getDocument();

		NodeList weapons = document.getElementsByTagName("weapon");
		int weaponCount = weapons.getLength();

		for (int i = 0; i < weaponCount; i++)
		{
			Node weaponNode = weapons.item(i);

			if (weaponNode != null && weaponNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element weaponElement = (Element) weaponNode;
				WeaponRecord record = new WeaponRecord();
				record.name = reader.getTagValue("name", weaponElement);
				record.image = reader.getTagValue("image", weaponElement);
				record.quality = reader.getTagValue("quality", weaponElement);
				record.itemLevel = Integer.parseInt(reader.getTagValue("itemLevel", weaponElement));
				record.minDamage = Integer.parseInt(reader.getTagValue("minDamage", weaponElement));
				record.maxDamage = Integer.parseInt(reader.getTagValue("maxDamage", weaponElement));
				record.attackSpeed = Integer.parseInt(reader.getTagValue("attackSpeed", weaponElement));
				record.critMultiplier = Integer.parseInt(reader.getTagValue("critMultiplier", weaponElement));
				weaponRecords.put(weaponElement.getAttribute("id"), record);
			}
		}
	}

	/**
	 * Returns the record for a specific armor.
	 * @param id The armor id.
	 * @return The record.
	 */
	public ArmorRecord getArmorRecord(String id)
	{
		return loaded ? armorRecords.get(id) : null;
	}

	/**
	 * Returns the record for a specific consumable.
	 * @param id The consumable id.
	 * @return The record.
	 */
	public ConsumableRecord getConsumableRecord(String id)
	{
		return loaded ? consumableRecords.get(id) : null;
	}

	/**
	 * Returns the record for a specific map.
	 * @param id The encounter id.
	 * @return The record
	 */
	public MapRecord getMapRecord(String id)
	{
		return loaded ? mapRecords.get(id) : null;
	}

	/**
	 * Returns the player record.
	 * @return The record.
	 */
	public PlayerRecord getPlayerRecord()
	{
		return loaded ? playerRecord : null;
	}

	/**
	 * Returns the record for a specific loot table.
	 * @param id The loot table id.
	 * @return The record.
	 */
	public LootTableRecord getLootTableRecord(String id)
	{
		return loaded ? lootTableRecords.get(id) : null;
	}

	/**
	 * Returns the record for a specific powerup table.
	 * @param id The powerup table id.
	 * @return The record.
	 */
	public PowerupTableRecord getPowerupTableRecord(String id)
	{
		return loaded ? powerupTableRecords.get(id) : null;
	}

	/**
	 * Returns the record for a specific hostile.
	 * @param id The hostile id.
	 * @return The record.
	 */
	public HostileRecord getHostileRecord(String id)
	{
		return loaded ? hostileRecords.get(id) : null;
	}

	/**
	 * Returns the record for a specific tile.
	 * @param tilesetId The tileset id.
	 * @param id The tile id.
	 * @return The record.
	 */
	public TileRecord getTileRecord(String tilesetId, String id)
	{
		if (loaded)
		{
			TilesetRecord tilesetRecord = tilesetRecords.get(tilesetId);

			if (tilesetRecord != null)
			{
				return tilesetRecord.tiles.get(id);
			}
		}

		return null;
	}

	/**
	 * Returns the record for a specific treasure.
	 * @param id The treasure id.
	 * @return The record.
	 */
	public TreasureRecord getTreasureRecord(String id)
	{
		return loaded ? treasureRecords.get(id) : null;
	}

	/**
	 * Returns the record for a specific weapon.
	 * @param id The weapon id.
	 * @return The record.
	 */
	public WeaponRecord getWeaponRecord(String id)
	{
		return loaded ? weaponRecords.get(id) : null;
	}

	// -------------
	// Inner classes
	// -------------

	/**
	 * Game object record class.
	 * This class represents a single game object.
	 */
	public class GameObjectRecord
	{
		public String id;
		public String name;
		public String image;
	}

	/**
	 * Character record class.
	 * This class represents a single character record.
	 */
	public class CharacterRecord extends GameObjectRecord
	{
		public int attackSpeed;
		public int movementSpeed;
		public int viewRange;
		public int health;
		public int minDamage;
		public int maxDamage;
		public int minLevel;
		public int maxLevel;
		public float healthInc;
		public float minDamageInc;
		public float maxDamageInc;
	}

	/**
	 * Hostile record class.
	 * This class represents a single hostile record.
	 */
	public class HostileRecord extends CharacterRecord
	{
		public String lootTable;
		public String powerupTable;
		public int expValue;
		public float expValueInc;
	}

	/**
	 * Player record class.
	 * This class represents the player record.
	 */
	public class PlayerRecord extends CharacterRecord
	{
		public int expFirstLevel;
		public float expInc;
		public ArrayList<String> images = new ArrayList<String>();
		public ArrayList<ItemRecord> items = new ArrayList<ItemRecord>();
	}

	/**
	 * Treasure record class.
	 * This class represents a single treasure record.
	 */
	public class TreasureRecord extends GameObjectRecord
	{
		public String lootTable;
	}

	/**
	 * Item record class.
	 * This class represents a single item record.
	 */
	public class ItemRecord extends GameObjectRecord
	{
		public String type;
		public String quality;
		public int itemLevel;
	}

	/**
	 * Armor record class.
	 * This class represents a single armor record.
	 */
	public class ArmorRecord extends ItemRecord
	{
		public int armorValue;
	}

	/**
	 * Weapon record class.
	 * This class represents a single weapon record.
	 */
	public class WeaponRecord extends ItemRecord
	{
		public int minDamage;
		public int maxDamage;
		public int attackSpeed;
		public int critMultiplier;
	}

	/**
	 * Consumable record class.
	 * This class represents a single consumable record.
	 */
	public class ConsumableRecord extends ItemRecord
	{
	}

	/**
	 * Map record class.
	 * This class represents a single map record.
	 */
	public class MapRecord
	{
		public String id;
		public int width;
		public int height;
		public int hostileCount;
		public int treasureCount;
		public ArrayList<CharacterEncounterRecord> hostileRecords = new ArrayList<CharacterEncounterRecord>();
		public ArrayList<TreasureEncounterRecord> treasureRecords = new ArrayList<TreasureEncounterRecord>();
	}

	/**
	 * Loot table record class.
	 * This class represents a single loot table record.
	 */
	public class LootTableRecord
	{
		public ArrayList<LootRecord> loot = new ArrayList<LootRecord>();
	}

	/**
	 * Loot record.
	 * This class represents a single loot record.
	 */
	public class LootRecord
	{
		public String type; // armor, weapon, consumable
		public int id;
		public int oddment;
	}

	/**
	 * Powerup table record.
	 * This class represents a single powerup table record.
	 */
	public class PowerupTableRecord
	{
		public ArrayList<PowerupRecord> powerups = new ArrayList<PowerupRecord>();
	}

	/**
	 * Powerup record.
	 * This class represents a single powerup record.
	 */
	public class PowerupRecord
	{
		public int id;
		public int oddment;
	}

	/**
	 * Character record class.
	 * This class represents a single character record.
	 */
	public class CharacterEncounterRecord
	{
		public String type; // hostile, friendly
		public int id;
		public int level;
		public int maxCount;
		public int oddment;
	}

	/**
	 * Treasure encounter record class.
	 * This class represents a single treasure record.
	 */
	public class TreasureEncounterRecord
	{
		public int id;
		public int oddment;
	}

	/**
	 * Tileset record class.
	 * This class represents a single tileset record.
	 */
	public class TilesetRecord
	{
		public HashMap<String, TileRecord> tiles = new HashMap<String, TileRecord>();
	}

	/**
	 * Tile record class.
	 * This class represents a single tile record.
	 */
	public class TileRecord extends GameObjectRecord
	{
		public int priority;
		public ArrayList<ImageRecord> images = new ArrayList<ImageRecord>();
	}

	/**
	 * Image record class.
	 * This class represents a single image.
	 */
	public class ImageRecord
	{
		public String filename;
		public int oddment;
	}
}
