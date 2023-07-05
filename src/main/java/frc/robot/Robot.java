// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

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

  }

  @Override
  public void teleopPeriodic() {

    // Use the controller Left Y axis for forward movement, Left X axis for lateral
    // movement, and Right X axis for rotation.
    m_robotDrive.driveCartesian(-m_driverController.getLeftY(), 
                                m_driverController.getLeftX(), 
                                -m_driverController.getRightX());

    if ( m_driverController.getAButtonPressed() )
      m_shifterSolenoid.set(kForward);
    else if ( m_driverController.getBButtonPressed() )
      m_shifterSolenoid.set(kReverse);

  }
}
