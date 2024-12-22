package de.vatinmc.cursoranchor.config;

import de.vatinmc.cursoranchor.client.CursorAnchorClient;
import de.vatinmc.cursoranchor.util.HandledScreenType;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CursorAnchorConfig {
    private static final Logger LOGGER = CursorAnchorClient.LOGGER;
    public static final Map<HandledScreenType, Boolean> options = new LinkedHashMap<>();
    public static String dirPath = "config/" + CursorAnchorClient.MOD_ID;
    public static String cfgPath = dirPath + "/config.txt";

    public static void loadConfig(){
        try {
            File dir = new File(dirPath);
            if(dir.mkdir()){
                LOGGER.info("Directory {} created.", dir.getAbsolutePath());
            }

            try {
                File config = new File(cfgPath);
                if(config.createNewFile()){
                    LOGGER.info("Config file {} created.", config.getAbsolutePath());
                    Path configPath = Path.of(config.getAbsolutePath());
                    for(HandledScreenType screenType : HandledScreenType.values()){
                        String line = screenType.toString() + ":" + 1 + "\n";
                        Files.write(configPath, line.getBytes(), StandardOpenOption.APPEND);
                        options.put(screenType, true);
                    }
                } else {
                    options.clear();
                    Scanner cfgScanner = new Scanner(config);
                    while (cfgScanner.hasNextLine()){
                        String line = cfgScanner.nextLine();
                        String[] details = line.split(":", 2);

                        HandledScreenType screenType = HandledScreenType.valueOf(details[0]);
                        boolean on = !details[1].equals("0");

                        options.put(screenType,on);
                    }
                    for(HandledScreenType screenType : HandledScreenType.values()){
                        if(!options.containsKey(screenType))
                            options.put(screenType,true);
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Loading/Creating config file failed.");
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveConfig(){
        try {
            File dir = new File(dirPath);
            if(dir.mkdir()){
                LOGGER.info("Directory {} created. Where did it go?", dir.getAbsolutePath());
            }
            try {
                File config = new File(cfgPath);
                Path configPath = Path.of(config.getAbsolutePath());

                config.delete();
                if(config.createNewFile()){
                    LOGGER.info("Config file {} updated.", config.getAbsolutePath());
                    for(Map.Entry<HandledScreenType, Boolean> option : options.entrySet()){
                        String screenType = option.getKey().toString();
                        int on = 1;
                        if(!option.getValue())
                            on = 0;
                        String line = screenType + ":" + on + "\n";
                        Files.write(configPath, line.getBytes(), StandardOpenOption.APPEND);
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Updating config file failed.");
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}