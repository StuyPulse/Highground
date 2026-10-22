package com.stuypulse.robot.commands.subsystems.elevator;

import static edu.wpi.first.units.Units.Meters;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.AutoLogOutput;

import com.stuypulse.robot.util.talonfx.logged.TalonFXInputs;

import edu.wpi.first.units.measure.Distance;

public interface ElevatorIO {
    @AutoLog 
    class ElevatorIOInputs {
        public TalonFXInputs motorInputs = new TalonFXInputs();
    }

    class ElevatorIOOutputs {
        @AutoLogOutput(key = "Elevator/Target Position")
        public Distance targetPosition = Meters.zero();

        @AutoLogOutput(key = "Elevator/Mode")
        public ElevatorIOOutputMode mode = ElevatorIOOutputMode.POSITION;
    }
    
    enum ElevatorIOOutputMode {
        POSITION,
        STOP
    }

    public default void updateInputs(ElevatorIOInputs inputs) {}

    public default void applyOutputs(ElevatorIOOutputs outputs) {}

    public default void seedPosition(Distance height) {}
}
