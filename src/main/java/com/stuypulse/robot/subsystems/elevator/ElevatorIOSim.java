/************************* PROJECT HIGHGROUND *************************/
/* Copyright (c) 2026 StuyPulse Robotics. All rights reserved. */
/* Use of this source code is governed by an MIT-style license */
/* that can be found in the repository LICENSE file.           */
/***************************************************************/
package com.stuypulse.robot.subsystems.elevator;

import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.Meters;

import com.stuypulse.robot.constants.GlobalSettings;
import com.stuypulse.robot.util.talonfx.sim.SystemSim;
import com.stuypulse.robot.util.talonfx.sim.TalonFXSimulation;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;

public class ElevatorIOSim extends ElevatorIOBase {
    private final SystemSim<ElevatorSim> system;

    private final TalonFXSimulation motor;

    private ElevatorIOSim(TalonFXSimulation simMotor, SystemSim<ElevatorSim> system) {
        super(simMotor);

        motor = simMotor;

        this.system = system;
    }

    private ElevatorIOSim(SystemSim<ElevatorSim> system) {
        this(
                new TalonFXSimulation(
                        ElevatorConstants.ElevatorMotors.MOTOR_ID,
                        ElevatorConstants.ElevatorSettings.ROTATIONS_TO_HEIGHT_RATIO,
                        system),
                system);
    }

    public ElevatorIOSim() {
        this(
                SystemSim.of(
                        new ElevatorSim(
                                LinearSystemId.createElevatorSystem(
                                        DCMotor.getKrakenX60(1),
                                        ElevatorConstants.ElevatorSettings.MASS.in(Kilograms),
                                        ElevatorConstants.ElevatorSettings.DRUM_RADIUS.in(Meters),
                                        52 / 12),
                                DCMotor.getKrakenX60(1),
                                ElevatorConstants.ElevatorSettings.MAX_HEIGHT.in(Meters),
                                ElevatorConstants.ElevatorSettings.MAX_HEIGHT.in(Meters),
                                true,
                                0),
                        ElevatorConstants.ElevatorSettings.DRUM_RADIUS));
    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {
        system.update(GlobalSettings.DT);
        motor.refresh();

        super.updateInputs(inputs);
    }
}
