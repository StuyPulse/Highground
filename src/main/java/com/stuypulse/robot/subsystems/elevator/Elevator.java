package com.stuypulse.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import com.stuypulse.robot.commands.subsystems.elevator.ElevatorIOInputsAutoLogged;
import com.stuypulse.robot.constants.GlobalSettings;
import com.stuypulse.robot.subsystems.elevator.ElevatorIO.ElevatorIOOutputMode;
import com.stuypulse.robot.subsystems.elevator.ElevatorIO.ElevatorIOOutputs;
import com.stuypulse.robot.util.PeriodicSubsystem;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;

public class Elevator extends PeriodicSubsystem {
    private final ElevatorIO io;
    private final ElevatorIOInputsAutoLogged inputs;
    private final ElevatorIOOutputs outputs;

    private ElevatorState state;

    public Elevator(ElevatorIO io) {
        this.io = io;
        inputs = new ElevatorIOInputsAutoLogged();
        outputs = new ElevatorIOOutputs();

        state = ElevatorState.BOTTOM;

        io.seedPosition(ElevatorConstants.ElevatorSettings.MIN_HEIGHT);
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

    public Command setTop() {
        return runOnce(() -> setState(ElevatorState.TOP));
    }

    public Command setMiddle() {
        return runOnce(() -> setState(ElevatorState.MIDDLE));
    }

    public Command setBottom() {
        return runOnce(() -> setState(ElevatorState.BOTTOM));
    }
}
