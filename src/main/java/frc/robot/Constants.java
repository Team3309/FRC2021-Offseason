// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;

import edu.wpi.first.wpilibj.DriverStation;
import friarLib2.utility.INIConfigFile;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final byte[] TWENTY_TWENTY_ROBOT_MAC_ADDR = {};
    public static final byte[] TWENTY_TWENTY_ONE_ROBOT_MAC_ADDR = {};

    public static INIConfigFile robotCfg;

    static {
        try {
            byte[] rioMac = NetworkInterface.getByName("eth0").getHardwareAddress();
            if (Arrays.equals(rioMac, TWENTY_TWENTY_ONE_ROBOT_MAC_ADDR)) {
                robotCfg = new INIConfigFile("2021-offseason-robot.ini");
            } else if (Arrays.equals(rioMac, TWENTY_TWENTY_ROBOT_MAC_ADDR)) {
                robotCfg = new INIConfigFile("2020-robot-cfg.ini");
            } else {
                StringBuilder foundMAC = new StringBuilder();
                for (byte macOctet : rioMac) {
                    foundMAC.append(String.format("0x%02X", macOctet));
                    foundMAC.append(" ");
                }
                DriverStation.reportError("Running on unknown roboRIO with MAC " + foundMAC, false);
                System.err.println("Running on unknown roboRIO with MAC " + foundMAC);
                System.exit(-1); // make the world stop
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}