/************************* PROJECT HIGHGROUND *************************/
/* Copyright (c) 2026 StuyPulse Robotics. All rights reserved. */
/* Use of this source code is governed by an MIT-style license */
/* that can be found in the repository LICENSE file.           */
/***************************************************************/
package com.stuypulse.robot.subsystems.elevator;

import com.stuypulse.robot.util.talonfx.logged.LoggedTalonFX;

public class ElevatorIOReal extends ElevatorIOBase {
    public ElevatorIOReal() {
        super(new LoggedTalonFX(ElevatorConstants.ElevatorMotors.MOTOR_ID));
    }
}
