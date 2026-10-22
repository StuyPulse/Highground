package com.stuypulse.robot.util;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class PeriodicSubsystem extends SubsystemBase {
    private static final List<PeriodicSubsystem> instances = new ArrayList<>();

    public PeriodicSubsystem() {
        instances.add(this);
    }

    public void periodicAfterScheduler() {};

    public static void runAllPeriodicAfterScheduler() {
        for (var instance: instances) {
            instance.periodicAfterScheduler();
        }
     }
}
