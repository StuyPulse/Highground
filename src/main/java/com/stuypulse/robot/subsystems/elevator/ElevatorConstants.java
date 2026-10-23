package com.stuypulse.robot.subsystems.elevator;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;

import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.stuypulse.robot.util.talonfx.config.TalonFXConfig;

import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;

public interface ElevatorConstants {
    public interface ElevatorSettings {
        Distance MIN_HEIGHT = Inches.of(0);

        Distance MAX_HEIGHT = Inches.of(29);

        Distance MIDDLE_HEIGHT = Inches.of(14.5);

        double ROTATIONS_TO_HEIGHT_RATIO = 0;

        AngularVelocity MAX_VELOCITY = RotationsPerSecond.of(0);

        AngularAcceleration MAX_ACCEL = RotationsPerSecondPerSecond.of(0);
    }

    public interface Gains {
        double kP = 0;

        double kI = 0;

        double kD = 0;

        double kS = 0;
        
        double kV = 0;

        double kA = 0;

        double kG = 0;
    }

    public interface ElevatorMotors {
        TalonFXConfig MOTOR_CONFIG = new TalonFXConfig()
			.withStatorCurrentLimit(80)
            .withSupplyCurrentLimit(40)
			.withRampRate(0.25)
			.withNeutralMode(NeutralModeValue.Brake)
			.withInvertedValue(InvertedValue.Clockwise_Positive)
			.withPIDConstants(Gains.kP, Gains.kI, Gains.kD, 0)
			.withFFConstants(Gains.kS, Gains.kV, Gains.kA, Gains.kG,0)
			.withGravityType(GravityTypeValue.Elevator_Static)
			.withSensorToMechanismRatio(ElevatorSettings.ROTATIONS_TO_HEIGHT_RATIO)
			.withMotionProfile(ElevatorSettings.MAX_VELOCITY, ElevatorSettings.MAX_ACCEL);

        int MOTOR_ID = 0;
    }
}
