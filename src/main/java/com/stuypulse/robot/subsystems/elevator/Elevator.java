/************************* PROJECT HIGHGROUND *************************/
/* Copyright (c) 2026 StuyPulse Robotics. All rights reserved. */
/* Use of this source code is governed by an MIT-style license */
/* that can be found in the repository LICENSE file.           */
/***************************************************************/
package com.stuypulse.robot.subsystems.elevator;

import static edu.wpi.first.units.Units.Meters;

import com.stuypulse.robot.constants.GlobalSettings;
import com.stuypulse.robot.subsystems.elevator.ElevatorIO.ElevatorIOOutputMode;
import com.stuypulse.robot.subsystems.elevator.ElevatorIO.ElevatorIOOutputs;
import com.stuypulse.robot.util.PeriodicSubsystem;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

import org.littletonrobotics.junction.Logger;

public class Elevator extends PeriodicSubsystem {
    private final ElevatorIO io;
    private final ElevatorIOInputsAutoLogged inputs;
    private final ElevatorIOOutputs outputs;

    private final SysIdRoutine sysid;

    private ElevatorState state;

    public Elevator(ElevatorIO io) {
        this.io = io;
        inputs = new ElevatorIOInputsAutoLogged();
        outputs = new ElevatorIOOutputs();

        state = ElevatorState.BOTTOM;

        io.seedPosition(ElevatorConstants.ElevatorSettings.MIN_HEIGHT);

        sysid = new SysIdRoutine(
            new SysIdRoutine.Config(
                null,
                null,
                null,
                state -> Logger.recordOutput("Elevator/SysID State", state.toString())
            ),
            new SysIdRoutine.Mechanism(this::runVoltage, null, this)
        );
    }

    public enum ElevatorState {
        TOP,
        MIDDLE,
        BOTTOM,
        STOP
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Elevator", inputs);

        if (!GlobalSettings.EnabledSubsystems.ELEVATOR.get()) {
            stopMotor();

            return;
        }

        switch (state) {
            case TOP -> runPosition(ElevatorConstants.ElevatorSettings.MAX_HEIGHT);

            case MIDDLE -> runPosition(ElevatorConstants.ElevatorSettings.MIDDLE_HEIGHT);

            case BOTTOM -> runPosition(ElevatorConstants.ElevatorSettings.MIN_HEIGHT);

            case STOP -> stopMotor();
        }
    }

    @Override
    public void periodicAfterScheduler() {
        io.applyOutputs(outputs);
    }

    private void setState(ElevatorState state) {
        this.state = state;
    }

    private void stopMotor() {
        outputs.mode = ElevatorIOOutputMode.STOP;
    }

    private void runPosition(Distance height) {
        outputs.mode = ElevatorIOOutputMode.POSITION;
        outputs.targetPosition = height;
    }

    private void runVoltage(Voltage voltage) {
        outputs.mode = ElevatorIOOutputMode.VOLTAGE;
        outputs.targetVoltage = voltage;
    }

    public Command setTop() {
        return runOnce(() -> setState(ElevatorState.TOP));
    }

    public Command setMiddle() {
        return runOnce(() -> setState(ElevatorState.MIDDLE));
    }

    public Command setBottom() {
        return runOnce(() -> setState(ElevatorState.BOTTOM));
    }

    public Command setStop() {
        return runOnce(() -> setState(ElevatorState.STOP));
    }

    public Command zero() {
        return Commands.runOnce(() -> io.seedPosition(Meters.zero()));
    }

    public Command sysidQuasi(SysIdRoutine.Direction direction) {
        return sysid.quasistatic(direction);
    }

    public Command sysidDynamic(SysIdRoutine.Direction direction) {
        return sysid.dynamic(direction);
    }

}
