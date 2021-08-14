package friarLib2.utility;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import edu.wpi.first.wpilibj.Filesystem;

public class INIConfigFile {
    Wini cfgFile;

    public INIConfigFile(String fileName) throws InvalidFileFormatException, IOException {
        cfgFile = new Wini(new File(Filesystem.getDeployDirectory() + fileName));
    }

    public int getInt (String section, String key) {
        return cfgFile.get(section, key, int.class);
    }

    public double getDouble (String section, String key) {
        return cfgFile.get(section, key, double.class);
    }

    public PIDParameters getPIDGains (String section, String key) {
        String original = cfgFile.get(section, key);
        
        char[] temp = new char[original.length()];

        for (int i = 0; i < temp.length; i++) {
            if ()
            temp[i] = original.toCharArray()[i]
        }
    }
}

// ARM_PID = {1, 1, 1}
