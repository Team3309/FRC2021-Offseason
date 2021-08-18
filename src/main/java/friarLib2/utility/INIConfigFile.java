package friarLib2.utility;

import java.io.File;
import java.io.IOException;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

import edu.wpi.first.wpilibj.Filesystem;

public class INIConfigFile {
    Ini cfgFile;

    public INIConfigFile(String fileName) throws InvalidFileFormatException, IOException {
        cfgFile = new Ini(new File(Filesystem.getDeployDirectory() + fileName));
    }

    public int getInt (String section, String key) {
        return cfgFile.get(section, key, int.class);
    }

    public double getDouble (String section, String key) {
        return cfgFile.get(section, key, double.class);
    }

    /**
     * Return a PIDParameters object from a value in the config file.
     * 
     * <p>The configuration value must be in the format 
     * "{<kP>, <kI>, <kD>, [kF]}" or else an array out of bounds error
     * will be thrown
     * 
     * @param section
     * @param key
     * @return a PIDParameters object
     */
    public PIDParameters getPIDGains (String section, String key) {
        // Read the value as stored in the config file
        String original = cfgFile.get(section, key);

        String temp = "";
        // Append all charachters that are not spaces or curly brackets
        // in order to get a comma-delimited list
        for (char i : original.toCharArray()) {
            if (i != ' ' && i != '{' && i != '}') {
                temp += i;
            }
        }
        String[] parsed = temp.split(",");

        // Parse the array of strings into the PIDParameters object
        PIDParameters gains = new PIDParameters();
        gains.kP = Double.parseDouble(parsed[0]);
        gains.kD = Double.parseDouble(parsed[1]);
        gains.kI = Double.parseDouble(parsed[2]);
        if (parsed.length == 4) {
            gains.kF = Double.parseDouble(parsed[3]);
        }

        return gains;
    }
}