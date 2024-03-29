package leshy;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import leshy.cards.abstracts.AbstractCreatureCard;
import leshy.potions.*;
import leshy.powers.SacrificePower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import leshy.cards.*;
import leshy.characters.Leshy;
import leshy.relics.*;
import leshy.util.IDCheckDontTouchPls;
import leshy.util.TextureLoader;
import leshy.variables.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Properties;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 3 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class LeshyMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        OnStartBattleSubscriber{
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(LeshyMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Leshy";
    private static final String AUTHOR = "Wensber"; // And pretty soon - You!
    private static final String DESCRIPTION = "Character mod for the character Leshy from Inscryption.";

    public static boolean fullSetUpdated = false;

    public static int avian = 0;
    public static int canine = 0;
    public static int hooved = 0;
    public static int insect = 0;
    public static int reptile = 0;
    public static int amalgam = 0;
    public static int fullSets = 0;

    public static boolean cawCaw = false;

    public static boolean wolfSkull = false;
    public static boolean bullfighter = false;

    public static boolean sapling = false;

    public static boolean blood = false;
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);
    
    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
    
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
  
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "leshyResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "leshyResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "leshyResources/images/512/bg_power_default_gray.png";
    
    private static final String ENERGY_ORB_DEFAULT_GRAY = "leshyResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "leshyResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "leshyResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "leshyResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "leshyResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "leshyResources/images/1024/card_default_gray_orb.png";
    
    // Character assets
    private static final String THE_DEFAULT_BUTTON = "leshyResources/images/charSelect/leshyButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "leshyResources/images/charSelect/leshyPortrait.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "leshyResources/images/char/leshyCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "leshyResources/images/char/leshyCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "leshyResources/images/char/leshyCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "leshyResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "leshyResources/images/char/leshyCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "leshyResources/images/char/leshyCharacter/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeUIPath(String resourcePath) {
        return getModID() + "Resources/images/ui/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================


    public static HashMap<String, TextureAtlas.AtlasRegion> leshyAtlasRegions = new HashMap<>();

    public static HashMap<String, String> leshyAtlasPaths = new HashMap<>();

    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    
    public LeshyMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */
      
        setModID("leshy");
        // cool
        // TODO: NOW READ THIS!!!!!!!!!!!!!!!:
        
        // 1. Go to your resources folder in the project panel, and refactor> rename theDefaultResources to
        // yourModIDResources.
        
        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of theDefault with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        
        // 3. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theDefault. They get loaded before getID is a thing.
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + Leshy.Enums.LESHY_BROWN.toString());
        
        BaseMod.addColor(Leshy.Enums.LESHY_BROWN, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        /*theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        logger.info("Done adding mod settings");


        addLeshyAtlasPaths();

        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = LeshyMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = LeshyMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = LeshyMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        LeshyMod defaultmod = new LeshyMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + Leshy.Enums.LESHY.toString());
        
        BaseMod.addCharacter(new Leshy("Leshy", Leshy.Enums.LESHY), THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, Leshy.Enums.LESHY);
        
        receiveEditPotions();
        logger.info("Added " + Leshy.Enums.LESHY.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================
        
        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"

        
        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)

        //CardHelper.getColor(209.0f, 53.0f, 18.0f);

        BaseMod.addPotion(HoggyBankPotion.class, CardHelper.getColor(255.0f, 255.0f, 255.0f), CardHelper.getColor(100.0f, 100.0f, 100.0f), CardHelper.getColor(0.0f, 0.0f, 0.0f), HoggyBankPotion.POTION_ID, Leshy.Enums.LESHY);
        BaseMod.addPotion(MagickalBleachPotion.class, CardHelper.getColor(255.0f, 255.0f, 255.0f), CardHelper.getColor(255.0f, 255.0f, 255.0f), CardHelper.getColor(255.0f, 255.0f, 255.0f), MagickalBleachPotion.POTION_ID, Leshy.Enums.LESHY);
        BaseMod.addPotion(HarpiesBirdlegFanPotion.class, CardHelper.getColor(165.0f, 42.0f, 42.0f), CardHelper.getColor(100.0f, 100.0f, 100.0f), null, HarpiesBirdlegFanPotion.POTION_ID, Leshy.Enums.LESHY);
        BaseMod.addPotion(WiseclockPotion.class, CardHelper.getColor(0.0f, 0.0f, 255.0f), CardHelper.getColor(0.0f, 0.0f, 200.0f), CardHelper.getColor(0.0f, 0.0f, 100.0f), WiseclockPotion.POTION_ID, Leshy.Enums.LESHY);
        BaseMod.addPotion(NanoArmorGeneratorPotion.class, CardHelper.getColor(0.0f, 0.0f, 150.0f), CardHelper.getColor(0.0f, 0.0f, 50.0f), null, NanoArmorGeneratorPotion.POTION_ID, Leshy.Enums.LESHY);
        BaseMod.addPotion(ToothPotion.class, CardHelper.getColor(255.0f, 255.0f, 255.0f), CardHelper.getColor(255.0f, 255.0f, 255.0f), null, ToothPotion.POTION_ID, Leshy.Enums.LESHY);

        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        //Starter
        BaseMod.addRelicToCustomPool(new WoodcarverRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new SquirrelDeckRelic(), Leshy.Enums.LESHY_BROWN);

        //Common
        BaseMod.addRelicToCustomPool(new RedMushroomRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new AcornRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new PetFoodRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new TombstoneRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new DogTreatRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new BoulderRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new RecruitmentPosterRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new MycologistMaskRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new ExoticPetFoodRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new GhostFoodRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new GreatKrakenRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new ArtOfSquirrelRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new SquirrelTeaSetRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new BoneShieldRelic(), Leshy.Enums.LESHY_BROWN);

        //Uncommon
        BaseMod.addRelicToCustomPool(new ProspectorMaskRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new TrenchCoatRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new PileOfTeethRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new TinyHeadbandRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new PaintbrushRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new PileOfMeatRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new ChimeraStatueRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new ChickenEggRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new BottledOpossumRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new WolfSkullRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new BullfighterCapeRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new GreenShellRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new MantisClawRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new RavenFeatherRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new BloodBagRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new SugarCubesRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new BearMedallionRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new PliersRelic(), Leshy.Enums.LESHY_BROWN);

        //Rare
        BaseMod.addRelicToCustomPool(new AnglerMaskRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new CloverRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new TheMoonRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new TotemPoleRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new HeraldOfTheScurryRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new CawCawFaceRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new OldShellRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new CameraRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new BloodstoneRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new BoneLordsHornRelic(), Leshy.Enums.LESHY_BROWN);

        //Shop
        BaseMod.addRelicToCustomPool(new TrapperMaskRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new TraderMaskRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new SnorkelRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new WhiteRockRelic(), Leshy.Enums.LESHY_BROWN);

        //Boss
        BaseMod.addRelicToCustomPool(new MachineModificationRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new BoonOfGoatsBloodRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new CandlestickRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new InfestedPeltRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new BottledSquirrelRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new SquirrelArmorRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new BallOfSquirrelsRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new LimoncelloRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new QuillRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new SaplingRelic(), Leshy.Enums.LESHY_BROWN);

        //Event
        BaseMod.addRelicToCustomPool(new MagicEyeRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new GoatEyeRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new BoonOfTheBoneLordRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new OldDataRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new DeadSurvivorsRelic(), Leshy.Enums.LESHY_BROWN);

        BaseMod.addRelicToCustomPool(new InfestationRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new DiscriminationRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new ScavengerRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new BearTrapRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new GrudgeRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new DrownedRelic(), Leshy.Enums.LESHY_BROWN);
        BaseMod.addRelicToCustomPool(new StarvationRelic(), Leshy.Enums.LESHY_BROWN);


        UnlockTracker.markRelicAsSeen(WoodcarverRelic.ID);
        UnlockTracker.markRelicAsSeen(RedMushroomRelic.ID);
        UnlockTracker.markRelicAsSeen(AcornRelic.ID);
        UnlockTracker.markRelicAsSeen(PetFoodRelic.ID);
        UnlockTracker.markRelicAsSeen(TombstoneRelic.ID);
        UnlockTracker.markRelicAsSeen(DogTreatRelic.ID);
        UnlockTracker.markRelicAsSeen(BoulderRelic.ID);
        UnlockTracker.markRelicAsSeen(RecruitmentPosterRelic.ID);
        UnlockTracker.markRelicAsSeen(ProspectorMaskRelic.ID);
        UnlockTracker.markRelicAsSeen(MycologistMaskRelic.ID);
        UnlockTracker.markRelicAsSeen(TrenchCoatRelic.ID);
        UnlockTracker.markRelicAsSeen(GreatKrakenRelic.ID);
        UnlockTracker.markRelicAsSeen(PileOfTeethRelic.ID);
        UnlockTracker.markRelicAsSeen(TinyHeadbandRelic.ID);
        UnlockTracker.markRelicAsSeen(PaintbrushRelic.ID);
        UnlockTracker.markRelicAsSeen(BoonOfTheBoneLordRelic.ID);
        UnlockTracker.markRelicAsSeen(AnglerMaskRelic.ID);
        UnlockTracker.markRelicAsSeen(CloverRelic.ID);
        UnlockTracker.markRelicAsSeen(ChickenEggRelic.ID);
        UnlockTracker.markRelicAsSeen(TheMoonRelic.ID);
        UnlockTracker.markRelicAsSeen(TrapperMaskRelic.ID);
        UnlockTracker.markRelicAsSeen(TraderMaskRelic.ID);
        UnlockTracker.markRelicAsSeen(SnorkelRelic.ID);
        UnlockTracker.markRelicAsSeen(WhiteRockRelic.ID);
        UnlockTracker.markRelicAsSeen(MachineModificationRelic.ID);
        UnlockTracker.markRelicAsSeen(BoonOfGoatsBloodRelic.ID);
        UnlockTracker.markRelicAsSeen(CandlestickRelic.ID);
        UnlockTracker.markRelicAsSeen(InfestedPeltRelic.ID);
        UnlockTracker.markRelicAsSeen(BottledSquirrelRelic.ID);
        UnlockTracker.markRelicAsSeen(SquirrelArmorRelic.ID);
        UnlockTracker.markRelicAsSeen(BallOfSquirrelsRelic.ID);
        UnlockTracker.markRelicAsSeen(MagicEyeRelic.ID);
        UnlockTracker.markRelicAsSeen(QuillRelic.ID);
        UnlockTracker.markRelicAsSeen(CameraRelic.ID);
        UnlockTracker.markRelicAsSeen(TotemPoleRelic.ID);
        UnlockTracker.markRelicAsSeen(LimoncelloRelic.ID);
        UnlockTracker.markRelicAsSeen(PileOfMeatRelic.ID);
        UnlockTracker.markRelicAsSeen(OldShellRelic.ID);
        UnlockTracker.markRelicAsSeen(HeraldOfTheScurryRelic.ID);
        UnlockTracker.markRelicAsSeen(ChimeraStatueRelic.ID);
        UnlockTracker.markRelicAsSeen(OldDataRelic.ID);
        UnlockTracker.markRelicAsSeen(ExoticPetFoodRelic.ID);
        UnlockTracker.markRelicAsSeen(CawCawFaceRelic.ID);
        UnlockTracker.markRelicAsSeen(DeadSurvivorsRelic.ID);
        UnlockTracker.markRelicAsSeen(WolfSkullRelic.ID);
        UnlockTracker.markRelicAsSeen(BullfighterCapeRelic.ID);
        UnlockTracker.markRelicAsSeen(GreenShellRelic.ID);
        UnlockTracker.markRelicAsSeen(MantisClawRelic.ID);
        UnlockTracker.markRelicAsSeen(RavenFeatherRelic.ID);
        UnlockTracker.markRelicAsSeen(BloodstoneRelic.ID);
        UnlockTracker.markRelicAsSeen(GhostFoodRelic.ID);
        UnlockTracker.markRelicAsSeen(SaplingRelic.ID);
        UnlockTracker.markRelicAsSeen(ArtOfSquirrelRelic.ID);
        UnlockTracker.markRelicAsSeen(SquirrelTeaSetRelic.ID);
        UnlockTracker.markRelicAsSeen(BoneShieldRelic.ID);
        UnlockTracker.markRelicAsSeen(InfestationRelic.ID);
        UnlockTracker.markRelicAsSeen(DiscriminationRelic.ID);
        UnlockTracker.markRelicAsSeen(ScavengerRelic.ID);
        UnlockTracker.markRelicAsSeen(BearTrapRelic.ID);
        UnlockTracker.markRelicAsSeen(GrudgeRelic.ID);
        UnlockTracker.markRelicAsSeen(DrownedRelic.ID);
        UnlockTracker.markRelicAsSeen(StarvationRelic.ID);
        UnlockTracker.markRelicAsSeen(BoneLordsHornRelic.ID);
        UnlockTracker.markRelicAsSeen(BloodBagRelic.ID);
        UnlockTracker.markRelicAsSeen(SugarCubesRelic.ID);
        UnlockTracker.markRelicAsSeen(BearMedallionRelic.ID);
        UnlockTracker.markRelicAsSeen(PliersRelic.ID);

        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variabls");
        // Add the Custom Dynamic variabls
        BaseMod.addDynamicVariable(new LeshySecondMagicNumber());
        BaseMod.addDynamicVariable(new Attack());
        BaseMod.addDynamicVariable(new Health());


        
        logger.info("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        //Special
        BaseMod.addCard(new Squirrel());
        BaseMod.addCard(new Aquasquirrel());
        BaseMod.addCard(new BaitBucket());
        BaseMod.addCard(new GoldNugget());
        BaseMod.addCard(new Hydra());
        BaseMod.addCard(new Mothman());
        BaseMod.addCard(new StrangePupa());
        BaseMod.addCard(new Smoke());
        BaseMod.addCard(new UndeadCat());
        BaseMod.addCard(new StuntedWolf());
        BaseMod.addCard(new FilmRoll());
        BaseMod.addCard(new StrangeFrog());
        BaseMod.addCard(new LeapingTrap());
        BaseMod.addCard(new GreatWhite());
        BaseMod.addCard(new Bee());
        BaseMod.addCard(new PackRat());
        BaseMod.addCard(new PeltLice());
        BaseMod.addCard(new TheMoon());
        BaseMod.addCard(new Boulder());
        BaseMod.addCard(new Rabbit());
        BaseMod.addCard(new Vertebrae());
        BaseMod.addCard(new Dam());
        BaseMod.addCard(new SkeletonCrew());
        BaseMod.addCard(new Knife());
        BaseMod.addCard(new Starvation());

        BaseMod.addCard(new Glitch());
        BaseMod.addCard(new Roadkill());

        BaseMod.addCard(new AvianTotem());
        BaseMod.addCard(new CanineTotem());
        BaseMod.addCard(new HoovedTotem());
        BaseMod.addCard(new InsectTotem());
        BaseMod.addCard(new ReptileTotem());

        BaseMod.addCard(new WisdomAirborne());
        BaseMod.addCard(new WisdomAmorphous());
        BaseMod.addCard(new WisdomAntSpawner());
        BaseMod.addCard(new WisdomArmored());
        BaseMod.addCard(new WisdomBeesWithin());
        BaseMod.addCard(new WisdomBifurcated());
        BaseMod.addCard(new WisdomBoneKing());
        BaseMod.addCard(new WisdomBoneDigger());
        BaseMod.addCard(new WisdomBroodParasite());
        BaseMod.addCard(new WisdomDoubleStrike());
        BaseMod.addCard(new WisdomFecundity());
        BaseMod.addCard(new WisdomFledgling());
        BaseMod.addCard(new WisdomGuardian());
        BaseMod.addCard(new WisdomHoarder());
        BaseMod.addCard(new WisdomLeader());
        BaseMod.addCard(new WisdomManyLives());
        BaseMod.addCard(new WisdomMightyLeap());
        BaseMod.addCard(new WisdomRabbitHole());
        BaseMod.addCard(new WisdomRampager());
        BaseMod.addCard(new WisdomSharpQuills());
        BaseMod.addCard(new WisdomStinky());
        BaseMod.addCard(new WisdomTouchOfDeath());
        BaseMod.addCard(new WisdomTrifurcated());
        BaseMod.addCard(new WisdomTrinketBearer());
        BaseMod.addCard(new WisdomUnkillable());
        BaseMod.addCard(new WisdomWaterborne());
        BaseMod.addCard(new WisdomWorthySacrifice());
        BaseMod.addCard(new WisdomDamBuilder());

        BaseMod.addCard(new BoonOfTheAmbidextrous());
        BaseMod.addCard(new BoonOfTheMagpiesEye());
        BaseMod.addCard(new BoonOfTheBoneLord());
        BaseMod.addCard(new BoonOfGoatsBlood());
        BaseMod.addCard(new BoonOfTheForest());

        BaseMod.addCard(new OldDataHead());
        BaseMod.addCard(new OldDataLeft());
        BaseMod.addCard(new OldDataTop());
        BaseMod.addCard(new OldDataSacrifice());
        BaseMod.addCard(new OldDataStatic());

        BaseMod.addCard(new Cody());
        BaseMod.addCard(new David());
        BaseMod.addCard(new Kaminski());
        BaseMod.addCard(new Kaycee());
        BaseMod.addCard(new Louis());
        BaseMod.addCard(new Reginald());
        BaseMod.addCard(new LukeCarder());
        BaseMod.addCard(new Oct19());
        BaseMod.addCard(new Sean());
        BaseMod.addCard(new Tamara());

        //Basic
        BaseMod.addCard(new Sparrow());
        BaseMod.addCard(new CagedWolf());
        BaseMod.addCard(new Elk());
        BaseMod.addCard(new Stinkbug());
        BaseMod.addCard(new Bullfrog());
        BaseMod.addCard(new Stoat());


        //Common
        BaseMod.addCard(new WorkerAnt());
        BaseMod.addCard(new FlyingAnt());
        BaseMod.addCard(new RabbitPelt());
        BaseMod.addCard(new Porcupine());
        BaseMod.addCard(new Bat());
        BaseMod.addCard(new Target());
        BaseMod.addCard(new GrandFir());
        BaseMod.addCard(new Amalgam());
        BaseMod.addCard(new StrangeFrogs());
        BaseMod.addCard(new RavenEgg());
        BaseMod.addCard(new Opossum());
        BaseMod.addCard(new Wolf());
        BaseMod.addCard(new ElkFawn());
        BaseMod.addCard(new WildBull());
        BaseMod.addCard(new WolfCub());
        BaseMod.addCard(new Skunk());
        BaseMod.addCard(new RiverOtter());
        BaseMod.addCard(new Mole());
        BaseMod.addCard(new Raven());
        BaseMod.addCard(new Coyote());
        BaseMod.addCard(new Mantis());
        BaseMod.addCard(new Beaver());
        BaseMod.addCard(new RiverSnapper());

        //Uncommon
        BaseMod.addCard(new FledglingTotem());
        BaseMod.addCard(new BoneKingTotem());
        BaseMod.addCard(new StinkyTotem());
        BaseMod.addCard(new SharpQuillsTotem());
        BaseMod.addCard(new AntQueen());
        BaseMod.addCard(new WolfPelt());
        BaseMod.addCard(new StruckGold());
        BaseMod.addCard(new Alpha());
        BaseMod.addCard(new Kingfisher());
        BaseMod.addCard(new Grizzly());
        BaseMod.addCard(new TurkeyVulture());
        BaseMod.addCard(new Dagger());
        BaseMod.addCard(new Adder());
        BaseMod.addCard(new DireWolfPup());
        BaseMod.addCard(new Bloodhound());
        BaseMod.addCard(new RatKing());
        BaseMod.addCard(new GuardianTotem());
        BaseMod.addCard(new TrialOfWisdom());
        BaseMod.addCard(new TrialOfKin());
        BaseMod.addCard(new BifurcatedTotem());
        BaseMod.addCard(new TrialOfBlood());
        BaseMod.addCard(new HandTentacle());
        BaseMod.addCard(new DeathtouchTotem());
        BaseMod.addCard(new Tadpole());
        BaseMod.addCard(new Pronghorn());
        BaseMod.addCard(new BellTentacle());
        BaseMod.addCard(new MirrorTentacle());
        BaseMod.addCard(new Beehive());
        BaseMod.addCard(new GoFish());
        BaseMod.addCard(new MightyLeapTotem());
        BaseMod.addCard(new WaterborneTotem());
        BaseMod.addCard(new PackMule());
        BaseMod.addCard(new Warren());
        BaseMod.addCard(new Skink());
        BaseMod.addCard(new Geck());
        BaseMod.addCard(new Amoeba());
        BaseMod.addCard(new Cuckoo());
        BaseMod.addCard(new Rattler());
        BaseMod.addCard(new MooseBuck());
        BaseMod.addCard(new RingWorm());
        BaseMod.addCard(new AirborneTotem());

        //Rare
        BaseMod.addCard(new Brawl());
        BaseMod.addCard(new Ouroboros());
        BaseMod.addCard(new Lammergeier());
        BaseMod.addCard(new TooFastTooSoon());
        BaseMod.addCard(new GoldenPelt());
        BaseMod.addCard(new MoleMan());
        BaseMod.addCard(new CuriousEgg());
        BaseMod.addCard(new StrangeLarva());
        BaseMod.addCard(new RedHart());
        BaseMod.addCard(new TrialOfRarity());
        BaseMod.addCard(new FieldMice());
        BaseMod.addCard(new HoarderTotem());
        BaseMod.addCard(new Cat());
        BaseMod.addCard(new MudTurtle());
        BaseMod.addCard(new DireWolf());
        BaseMod.addCard(new FecundityTotem());
        BaseMod.addCard(new UnkillableTotem());
        BaseMod.addCard(new BlackGoat());
        BaseMod.addCard(new MantisGod());
        BaseMod.addCard(new Cockroach());
        BaseMod.addCard(new LongElk());
        BaseMod.addCard(new Magpie());
        BaseMod.addCard(new Urayuli());


        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // This is so that they are all "seen" in the library, for people who like to look at the card list
        // before playing your mod.

        UnlockTracker.unlockCard(Squirrel.ID);
        UnlockTracker.unlockCard(Aquasquirrel.ID);
        UnlockTracker.unlockCard(Stoat.ID);
        UnlockTracker.unlockCard(WorkerAnt.ID);
        UnlockTracker.unlockCard(CagedWolf.ID);
        UnlockTracker.unlockCard(Urayuli.ID);
        UnlockTracker.unlockCard(Kingfisher.ID);
        UnlockTracker.unlockCard(Stinkbug.ID);
        UnlockTracker.unlockCard(Cat.ID);
        UnlockTracker.unlockCard(RatKing.ID);
        UnlockTracker.unlockCard(Target.ID);
        UnlockTracker.unlockCard(Brawl.ID);
        UnlockTracker.unlockCard(BlackGoat.ID);
        UnlockTracker.unlockCard(Grizzly.ID);
        UnlockTracker.unlockCard(Cockroach.ID);
        UnlockTracker.unlockCard(CanineTotem.ID);
        UnlockTracker.unlockCard(BifurcatedTotem.ID);
        UnlockTracker.unlockCard(AvianTotem.ID);
        UnlockTracker.unlockCard(InsectTotem.ID);
        UnlockTracker.unlockCard(ReptileTotem.ID);
        UnlockTracker.unlockCard(FledglingTotem.ID);
        UnlockTracker.unlockCard(BoneKingTotem.ID);
        UnlockTracker.unlockCard(HoarderTotem.ID);
        UnlockTracker.unlockCard(StinkyTotem.ID);
        UnlockTracker.unlockCard(SharpQuillsTotem.ID);
        UnlockTracker.unlockCard(Porcupine.ID);
        UnlockTracker.unlockCard(FieldMice.ID);
        UnlockTracker.unlockCard(Bloodhound.ID);
        UnlockTracker.unlockCard(Ouroboros.ID);
        UnlockTracker.unlockCard(MantisGod.ID);
        UnlockTracker.unlockCard(Magpie.ID);
        UnlockTracker.unlockCard(AntQueen.ID);
        UnlockTracker.unlockCard(Raven.ID);
        UnlockTracker.unlockCard(RavenEgg.ID);
        UnlockTracker.unlockCard(Lammergeier.ID);
        UnlockTracker.unlockCard(Amalgam.ID);
        UnlockTracker.unlockCard(MoleMan.ID);
        UnlockTracker.unlockCard(FlyingAnt.ID);
        UnlockTracker.unlockCard(Skink.ID);
        UnlockTracker.unlockCard(RiverSnapper.ID);
        UnlockTracker.unlockCard(Geck.ID);
        UnlockTracker.unlockCard(GreatWhite.ID);
        UnlockTracker.unlockCard(BaitBucket.ID);
        UnlockTracker.unlockCard(TooFastTooSoon.ID);
        UnlockTracker.unlockCard(Bullfrog.ID);
        UnlockTracker.unlockCard(Tadpole.ID);
        UnlockTracker.unlockCard(Adder.ID);
        UnlockTracker.unlockCard(RabbitPelt.ID);
        UnlockTracker.unlockCard(WolfPelt.ID);
        UnlockTracker.unlockCard(GoldenPelt.ID);
        UnlockTracker.unlockCard(Sparrow.ID);
        UnlockTracker.unlockCard(RingWorm.ID);
        UnlockTracker.unlockCard(PeltLice.ID);
        UnlockTracker.unlockCard(GoldNugget.ID);
        UnlockTracker.unlockCard(StruckGold.ID);
        UnlockTracker.unlockCard(CuriousEgg.ID);
        UnlockTracker.unlockCard(Hydra.ID);
        UnlockTracker.unlockCard(Cuckoo.ID);
        UnlockTracker.unlockCard(GoFish.ID);
        UnlockTracker.unlockCard(Alpha.ID);
        UnlockTracker.unlockCard(PackRat.ID);
        UnlockTracker.unlockCard(Bat.ID);
        UnlockTracker.unlockCard(TheMoon.ID);
        UnlockTracker.unlockCard(StuntedWolf.ID);
        UnlockTracker.unlockCard(FilmRoll.ID);
        UnlockTracker.unlockCard(LeapingTrap.ID);
        UnlockTracker.unlockCard(StrangeFrog.ID);
        UnlockTracker.unlockCard(DireWolfPup.ID);
        UnlockTracker.unlockCard(DireWolf.ID);
        UnlockTracker.unlockCard(GrandFir.ID);
        UnlockTracker.unlockCard(Wolf.ID);
        UnlockTracker.unlockCard(TurkeyVulture.ID);
        UnlockTracker.unlockCard(Dagger.ID);
        UnlockTracker.unlockCard(Skunk.ID);
        UnlockTracker.unlockCard(Bee.ID);
        UnlockTracker.unlockCard(Beehive.ID);
        UnlockTracker.unlockCard(MirrorTentacle.ID);
        UnlockTracker.unlockCard(StrangeFrogs.ID);
        UnlockTracker.unlockCard(RedHart.ID);
        UnlockTracker.unlockCard(TrialOfRarity.ID);
        UnlockTracker.unlockCard(TrialOfBlood.ID);
        UnlockTracker.unlockCard(HandTentacle.ID);
        UnlockTracker.unlockCard(DeathtouchTotem.ID);
        UnlockTracker.unlockCard(Mantis.ID);
        UnlockTracker.unlockCard(Opossum.ID);
        UnlockTracker.unlockCard(MudTurtle.ID);
        UnlockTracker.unlockCard(Elk.ID);
        UnlockTracker.unlockCard(MooseBuck.ID);
        UnlockTracker.unlockCard(ElkFawn.ID);
        UnlockTracker.unlockCard(Coyote.ID);
        UnlockTracker.unlockCard(WildBull.ID);
        UnlockTracker.unlockCard(Pronghorn.ID);
        UnlockTracker.unlockCard(WolfCub.ID);
        UnlockTracker.unlockCard(BellTentacle.ID);
        UnlockTracker.unlockCard(HoovedTotem.ID);
        UnlockTracker.unlockCard(SkeletonCrew.ID);
        UnlockTracker.unlockCard(UndeadCat.ID);
        UnlockTracker.unlockCard(StrangePupa.ID);
        UnlockTracker.unlockCard(Mothman.ID);
        UnlockTracker.unlockCard(Boulder.ID);
        UnlockTracker.unlockCard(MightyLeapTotem.ID);
        UnlockTracker.unlockCard(WaterborneTotem.ID);
        UnlockTracker.unlockCard(FecundityTotem.ID);
        UnlockTracker.unlockCard(UnkillableTotem.ID);
        UnlockTracker.unlockCard(PackMule.ID);
        UnlockTracker.unlockCard(GuardianTotem.ID);
        UnlockTracker.unlockCard(RiverOtter.ID);
        UnlockTracker.unlockCard(Mole.ID);
        UnlockTracker.unlockCard(Amoeba.ID);
        UnlockTracker.unlockCard(Warren.ID);
        UnlockTracker.unlockCard(StrangeLarva.ID);
        UnlockTracker.unlockCard(TrialOfKin.ID);
        UnlockTracker.unlockCard(TrialOfWisdom.ID);
        UnlockTracker.unlockCard(Rattler.ID);
        UnlockTracker.unlockCard(Vertebrae.ID);
        UnlockTracker.unlockCard(LongElk.ID);
        UnlockTracker.unlockCard(Dam.ID);
        UnlockTracker.unlockCard(Beaver.ID);
        UnlockTracker.unlockCard(Glitch.ID);
        UnlockTracker.unlockCard(Knife.ID);
        UnlockTracker.unlockCard(AirborneTotem.ID);
        UnlockTracker.unlockCard(Roadkill.ID);
        UnlockTracker.unlockCard(OldDataHead.ID);
        UnlockTracker.unlockCard(OldDataLeft.ID);
        UnlockTracker.unlockCard(OldDataTop.ID);
        UnlockTracker.unlockCard(Starvation.ID);
        UnlockTracker.unlockCard(OldDataStatic.ID);
        UnlockTracker.unlockCard(OldDataSacrifice.ID);
        UnlockTracker.unlockCard(Cody.ID);
        UnlockTracker.unlockCard(David.ID);
        UnlockTracker.unlockCard(Kaminski.ID);
        UnlockTracker.unlockCard(Kaycee.ID);
        UnlockTracker.unlockCard(Louis.ID);
        UnlockTracker.unlockCard(Reginald.ID);
        UnlockTracker.unlockCard(LukeCarder.ID);
        UnlockTracker.unlockCard(Oct19.ID);
        UnlockTracker.unlockCard(Sean.ID);
        UnlockTracker.unlockCard(Tamara.ID);

        
        logger.info("Done adding cards!");
    }
    
    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/leshyMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/leshyMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/leshyMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/leshyMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/leshyMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/leshyMod-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/leshyMod-Orb-Strings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/eng/leshyMod-UI-Strings.json");
        
        logger.info("Done edittting strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/leshyMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }


    public static AbstractCreatureCard getSquirrel(){
        for(AbstractRelic r : AbstractDungeon.player.relics){
            if(r instanceof SnorkelRelic)
                return new Aquasquirrel();
        }
        return new Squirrel();
    }


    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {

        if(AbstractDungeon.player instanceof Leshy){

            SacrificePower sp = new SacrificePower(AbstractDungeon.player, AbstractDungeon.player, 0);
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, sp));

            cawCaw = false;
            wolfSkull = false;
            bullfighter = false;
            sapling = false;
            blood = false;
            for(AbstractRelic r : AbstractDungeon.player.relics){
                if (r instanceof CawCawFaceRelic)
                    cawCaw = true;
                if (r instanceof WolfSkullRelic)
                    wolfSkull = true;
                if (r instanceof BullfighterCapeRelic)
                    bullfighter = true;
                if (r instanceof SaplingRelic)
                    sapling = true;
                if (r instanceof BloodBagRelic)
                    blood = true;
            }

            updateFullSets();

            for (int i = 0; i < (AbstractDungeon.player.energy.energyMaster - Leshy.ENERGY_PER_TURN); i++)
                if (AbstractDungeon.player.hand.group.size() < (10 - AbstractDungeon.player.gameHandSize))
                    AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(getSquirrel()));

        }

    }

    public static void updateFullSets(){

        fullSetUpdated = true;

        CardGroup masterDeck = AbstractDungeon.player.masterDeck;

        canine = 0;
        avian = 0;
        hooved = 0;
        insect = 0;
        reptile = 0;
        amalgam = 0;

        for(AbstractCard c : masterDeck.group){
            if(c instanceof AbstractCreatureCard){
                switch (((AbstractCreatureCard) c).tribe){
                    case CANINE:
                        canine += 1;
                        break;
                    case AVIAN:
                        avian += 1;
                        break;
                    case HOOVED:
                        hooved += 1;
                        break;
                    case ANT:
                    case INSECT:
                        insect += 1;
                        break;
                    case REPTILE:
                        reptile += 1;
                        break;
                    case AMALGAM:
                        amalgam += 1;
                        break;
                    case NONE:
                        if(cawCaw)
                            avian++;
                        break;
                }
            }
        }

        int tempAvian = avian;
        int tempCanine = canine;
        int tempHooved = hooved;
        int tempInsect = insect;
        int tempReptile = reptile;
        int tempAmalgam = amalgam;

        fullSets = 0;

        while(true){

            if(tempAvian > 0){
                tempAvian -= 1;
            }else if(tempAmalgam > 0){
                tempAmalgam -= 1;
            } else {
                break;
            }

            if(tempCanine > 0){
                tempCanine -= 1;
            }else if(tempAmalgam > 0){
                tempAmalgam -= 1;
            } else {
                break;
            }

            if(tempHooved > 0){
                tempHooved -= 1;
            }else if(tempAmalgam > 0){
                tempAmalgam -= 1;
            } else {
                break;
            }

            if(tempInsect > 0){
                tempInsect -= 1;
            }else if(tempAmalgam > 0){
                tempAmalgam -= 1;
            } else {
                break;
            }

            if(tempReptile > 0){
                tempReptile -= 1;
            }else if(tempAmalgam > 0){
                tempAmalgam -= 1;
            } else {
                break;
            }

            fullSets += 1;

        }
    }

    public void addLeshyAtlasPaths(){
        leshyAtlasPaths.put("blood", "leshyResources/images/512/card_blood_orb.png");
        leshyAtlasPaths.put("bone", "leshyResources/images/512/card_bone_orb.png");
        leshyAtlasPaths.put("attack", "leshyResources/images/512/card_attack.png");
        leshyAtlasPaths.put("health", "leshyResources/images/512/card_health.png");

        leshyAtlasPaths.put("bloodstain", "leshyResources/images/512/mycologists_bloodstain.png");
        leshyAtlasPaths.put("stitches", "leshyResources/images/512/mycologists_stitches.png");
        leshyAtlasPaths.put("cracks", "leshyResources/images/512/mycologists_cracks.png");

        leshyAtlasPaths.put("static 1", "leshyResources/images/512/static_1.png");
        leshyAtlasPaths.put("static 2", "leshyResources/images/512/static_2.png");
        leshyAtlasPaths.put("static 3", "leshyResources/images/512/static_3.png");
        leshyAtlasPaths.put("static 4", "leshyResources/images/512/static_4.png");
    }

    public static TextureAtlas.AtlasRegion getLeshyAtlasRegion(String key){
        TextureAtlas.AtlasRegion atlas = leshyAtlasRegions.get(key);
        if(atlas != null)
            return atlas;

        Texture texture = ImageMaster.loadImage(leshyAtlasPaths.get(key));
        int tw = texture.getWidth();
        int th = texture.getHeight();
        atlas = new TextureAtlas.AtlasRegion(texture, 0, 0, tw, th);
        leshyAtlasRegions.put(key, atlas);
        return atlas;
    }

}




