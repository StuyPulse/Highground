package com.stuypulse.robot.subsystems.elevator;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.stuypulse.robot.util.talonfx.logged.LoggedTalonFX;

import edu.wpi.first.units.measure.Distance;

public class ElevatorIOBase implements  ElevatorIO {
    private final LoggedTalonFX motor;

    private final MotionMagicVoltage positionController;

    protected ElevatorIOBase(LoggedTalonFX motor) {
        this.motor = motor;

        ElevatorConstants.ElevatorMotors.MOTOR_CONFIG.configure(motor);

        positionController = new MotionMagicVoltage(0);
    }

    @Override 
    public void updateInputs(ElevatorIOInputs inputs) {
        motor.updateInputs(inputs.motorInputs);
    }

    @Override 
    public void applyOutputs(ElevatorIOOutputs outputs) {
        switch (outputs.mode) {
            // The sensor to mechanism ratio is set so 1 rotation = 1 meter of height
            case POSITION -> motor.setControl(positionController.withPosition(Rotations.of(outputs.targetPosition.in(Meters))));

            case STOP -> motor.stopMotor();
        }
    }

    @Override 
    public void seedPosition(Distance height) {
        motor.setPosition(Rotations.of(height.in(Meters)));
    }
}
