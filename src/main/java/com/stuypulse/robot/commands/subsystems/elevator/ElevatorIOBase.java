package com.stuypulse.robot.commands.subsystems.elevator;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.stuypulse.robot.util.talonfx.logged.LoggedTalonFX;

import edu.wpi.first.units.measure.Angle;

public class ElevatorIOBase implements  ElevatorIO {
    private final LoggedTalonFX motor;

    private final MotionMagicVoltage positionController;

    protected ElevatorIOBase(LoggedTalonFX motor) {
        this.motor = motor;

        positionController = new MotionMagicVoltage(0);
    }

    @Override 
    public void updateInputs(ElevatorIOInputs inputs) {
        motor.updateInputs(inputs.motorInputs);
    }

    @Override 
    public void applyOutputs(ElevatorIOOutputs outputs) {
        switch (outputs.mode) {
            case POSITION -> motor.setControl(positionController.withPosition(outputs.targetPosition));

            case STOP -> motor.stopMotor();
        }
    }

    @Override 
    public void seedPosition(Angle position) {
        motor.setPosition(position);
    }
}
