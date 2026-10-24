/************************ PROJECT ZIXI ************************/
/* Copyright (c) 2026 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/
package com.stuypulse.robot;

import com.stuypulse.robot.commands.auton.Auton;
import com.stuypulse.robot.commands.auton.AutonFactory;
import com.stuypulse.robot.constants.Field;
import com.stuypulse.robot.constants.GlobalPorts;
import com.stuypulse.robot.constants.GlobalSettings;
import com.stuypulse.robot.subsystems.elevator.Elevator;
import com.stuypulse.robot.subsystems.elevator.ElevatorIO;
import com.stuypulse.robot.subsystems.elevator.ElevatorIOReal;
import com.stuypulse.robot.subsystems.elevator.ElevatorIOSim;

import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class RobotContainer {

    // Gamepads
    public final CommandXboxController driver =
            new CommandXboxController(GlobalPorts.Gamepad.DRIVER); // model-agnostic controller
    // public final CommandGamepad operator = new CommandGamepad(GlobalPorts.Gamepad.OPERATOR);

    // Subsystems
    private final Elevator elevator;

    // Autons
    private LoggedDashboardChooser<Auton> autonChooser = new LoggedDashboardChooser<>("Auton");

    // Robot container

    public RobotContainer() {
        switch (GlobalSettings.CURRENT_MODE) {
            case REAL -> elevator = new Elevator(new ElevatorIOReal());

            case SIM -> elevator = new Elevator(new ElevatorIOSim());

            default -> elevator  = new Elevator(new ElevatorIO() {});
        }

        configureLogging();
        configureDefaultCommands();
        configureButtonBindings();
        configureAutons();

        SmartDashboard.putData("Field", Field.FIELD_2D);
    }

    /***************/
    /*** LOGGING ***/
    /***************/

    private void configureLogging() {}

    /****************/
    /*** DEFAULTS ***/
    /****************/

    private void configureDefaultCommands() {}

    /***************/
    /*** BUTTONS ***/
    /***************/

    private void configureButtonBindings() {
        driver.a().onTrue(elevator.setTop());

        driver.b().onTrue(elevator.setBottom());

        driver.x().onTrue(elevator.setMiddle());
    }

    /**************/
    /*** AUTONS ***/
    /**************/

    public void configureAutons() {
        AutonFactory autonFactory = new AutonFactory();

        autonChooser.addOption("Do Nothing", autonFactory.doNothingAuton());
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return The command to run in autonomous
     */
    public Auton getAutonomousCommand() {
			return autonChooser.get();
    }
}
