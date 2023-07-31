// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import edu.wpi.first.networktables.NetworkTableInstance;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Robot extends TimedRobot {
  private static final int kFrontLeftChannel = 1;
  private static final int kRearLeftChannel = 2;
  private static final int kFrontRightChannel = 4;
  private static final int kRearRightChannel = 3;

  private static final int kDriverControllerChannel = 0;

  private MecanumDrive m_robotDrive;
  private XboxController m_driverController;
  private DoubleSolenoid m_shifterSolenoid;
  private NetworkTableInstance m_inst;
  private Bling m_bling;
  private RemoteControl m_remoteController;
  private boolean m_remoteControl;

  @Override
  public void robotInit() {
    WPI_TalonSRX frontLeft = new WPI_TalonSRX(kFrontLeftChannel);
    WPI_TalonSRX rearLeft = new WPI_TalonSRX(kRearLeftChannel);
    WPI_TalonSRX frontRight = new WPI_TalonSRX(kFrontRightChannel);
    WPI_TalonSRX rearRight = new WPI_TalonSRX(kRearRightChannel);

    // Invert the right side motors.
    // You may need to change or remove this to match your robot.
    frontRight.setInverted(true);
    rearRight.setInverted(true);

    // Set the Talons to brake mode when throttle is released.
    frontLeft.setNeutralMode(NeutralMode.Brake);
    rearLeft.setNeutralMode(NeutralMode.Brake);
    frontRight.setNeutralMode(NeutralMode.Brake);
    rearRight.setNeutralMode(NeutralMode.Brake);
    
    // Instantiate the drive train instance
    m_robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);

    // Instantiate the driver controller
    m_driverController = new XboxController(kDriverControllerChannel);

    // Instantiate the solenoid instance to control the shifters and initialize the
    // shifter to a known state
    m_shifterSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,0,1);
    m_shifterSolenoid.set(kForward);

    // Get the default network tables instance to be used to pass information
    // between the robot and the raspberry Pi
    m_inst = NetworkTableInstance.getDefault();

    // Instantiate the LED (aka Bling) class that communicates via network tables
    // to the raspberry Pi that is controlling the LED strip
    m_bling = new Bling(m_inst);
    m_bling.sendRobotInit();

    // Instantiate the remote controller that is running on the raspberry Pi and used
    // to control the robot using the LIDAR sensor.
    m_remoteController = new RemoteControl(m_inst);

    // we will default to using the local joystick for robot control. May make this a
    // dashboard preference.
    m_remoteControl = false;
    
  }

  @Override
  public void teleopPeriodic() {

    // Use the controller Left Y axis for forward movement, Left X axis for lateral
    // movement, and Right X axis for rotation.

    if ( m_remoteControl == true ) {
      m_robotDrive.driveCartesian(-m_remoteController.getLeftY(), 
      m_remoteController.getLeftX(), 
      -m_remoteController.getRightX());
    } else {
      m_robotDrive.driveCartesian(-m_driverController.getLeftY(), 
      m_driverController.getLeftX(), 
      -m_driverController.getRightX());
    }

    
    if ( m_remoteController.getAButtonPressed() )
      m_shifterSolenoid.set(kForward);
    else if ( m_remoteController.getBButtonPressed() )
      m_shifterSolenoid.set(kReverse);
    
    if ( m_remoteController.getXButtonPressed() )
      m_remoteControl = false;
    if ( m_remoteController.getYButtonPressed() )
      m_remoteControl = true;
                            
    if ( m_driverController.getAButtonPressed() )
      m_shifterSolenoid.set(kForward);
    else if ( m_driverController.getBButtonPressed() )
      m_shifterSolenoid.set(kReverse);

    if ( m_driverController.getXButtonPressed() )
      m_remoteControl = false;
    if ( m_driverController.getYButtonPressed() )
      m_remoteControl = true;


  }
}
