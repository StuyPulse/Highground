package com.stuypulse.robot.subsystems.elevator;

import com.stuypulse.robot.util.talonfx.logged.LoggedTalonFX;

public class ElevatorIOReal extends ElevatorIOBase {
    public ElevatorIOReal() {
        super(new LoggedTalonFX(ElevatorConstants.ElevatorMotors.MOTOR_ID));
    }
}