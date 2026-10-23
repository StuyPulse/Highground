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
    private static final SystemSim<ElevatorSim> system = 
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
                true, 0),
            ElevatorConstants.ElevatorSettings.DRUM_RADIUS
        );

    private final TalonFXSimulation motor;

    private ElevatorIOSim(TalonFXSimulation simMotor) {
        super(simMotor);

        motor = simMotor;
    }

    public ElevatorIOSim() {
        this(new TalonFXSimulation(ElevatorConstants.ElevatorMotors.MOTOR_ID, ElevatorConstants.ElevatorSettings.ROTATIONS_TO_HEIGHT_RATIO, system)); 
    }

    @Override 
    public void updateInputs(ElevatorIOInputs inputs) {
	    system.update(GlobalSettings.DT);
	    motor.refresh();

	    super.updateInputs(inputs);
    }
}
