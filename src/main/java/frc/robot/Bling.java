package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;

public class Bling {
    private NetworkTableInstance    inst;
    private NetworkTable            networkTable;
    private StringPublisher         cmdPub;

    public Bling() {
        inst = NetworkTableInstance.getDefault();
        networkTable = inst.getTable("Bling");
        cmdPub = networkTable.getStringTopic("command").publish();
    }

    public void sendCmd(String cmd) {
        cmdPub.set(cmd);
    }

    public void sendRobotInit() {
        sendCmd( "Pattern=RainbowCycle,Color=Rainbow,Speed=Medium");
    }
    public void sendScanner() {
        sendCmd( "Pattern=Scanner,Color=Blue,Speed=Medium");
    }
    public void sendFirefly() {
        sendCmd( "Pattern=Firefly,Color=Rainbow,Speed=Medium");
    }
    
}
