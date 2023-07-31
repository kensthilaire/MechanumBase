package frc.robot;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.IntegerSubscriber;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class RemoteControl {
    private NetworkTable        m_networkTable;
    private DoubleSubscriber    m_leftX;
    private DoubleSubscriber    m_leftY;
    private DoubleSubscriber    m_rightX;
    private IntegerSubscriber   m_aButton;
    private IntegerSubscriber   m_bButton;
    private IntegerSubscriber   m_xButton;
    private IntegerSubscriber   m_yButton;



    public RemoteControl(NetworkTableInstance inst) {
        m_networkTable = inst.getTable("RobotRemoteControl");
        m_leftX = m_networkTable.getDoubleTopic("LeftJoystickX").subscribe(0.0);
        m_leftY = m_networkTable.getDoubleTopic("LeftJoystickY").subscribe(0.0);
        m_rightX = m_networkTable.getDoubleTopic("RightJoystickX").subscribe(0.0);

        m_aButton = m_networkTable.getIntegerTopic("ButtonA").subscribe(0);
        m_bButton = m_networkTable.getIntegerTopic("ButtonB").subscribe(0);
        m_xButton = m_networkTable.getIntegerTopic("ButtonX").subscribe(0);
        m_yButton = m_networkTable.getIntegerTopic("ButtonY").subscribe(0);
        
    }
    public double getLeftX() {
        return m_leftX.get();
    }
    public double getLeftY() {
        return m_leftY.get();
    }
    public double getRightX() {
        return m_rightX.get();
    }

    public boolean getAButtonPressed() {
        boolean isPressed = false;
        if ( m_aButton.get() == 1 )
            isPressed = true;
        return isPressed;
    }
    public boolean getBButtonPressed() {
        boolean isPressed = false;
        if ( m_bButton.get() == 1 )
            isPressed = true;
        return isPressed;
    }

    public boolean getXButtonPressed() {
        boolean isPressed = false;
        if ( m_xButton.get() == 1 )
            isPressed = true;
        return isPressed;
    }
    public boolean getYButtonPressed() {
        boolean isPressed = false;
        if ( m_yButton.get() == 1 )
            isPressed = true;
        return isPressed;
    }


}