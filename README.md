# Autonomous Mars Aircraft Decision Engine (AMADE)

## Overview
This project implements an autonomous decision-making system for an unmanned aircraft operating in the extreme environment of Mars. The system is designed to handle critical scenarios, such as detecting approaching dust storms, where communication delays with Earth (14-minute round-trip) make real-time human intervention impossible.

The core of the solution is a Risk-Based Cost Function Engine that evaluates flight maneuvers in real-time based on sensor data, battery constraints, and environmental hazards.

## Part 1: Problem Analysis

### 1. The Autonomy Gap
The aircraft detects a dust storm 12 minutes ahead, but the communication with Earth is 14 minutes. Any command sent from Earth would arrive 2 minutes after the aircraft has already encountered the storm. This constraint necessitates a 100% autonomous decision-making architecture.

### 2. Environmental Challenges on Mars
* **Atmospheric Opacity (Tau):** Martian dust storms significantly increase the atmospheric opacity (Tau). While a clear day has a Tau of ~0.5, severe storms can exceed Tau 10, rendering optical sensors nearly useless due to light scattering.
* **Sensor Degradation:** Dust particles on Mars are highly abrasive. Continuous exposure leads to physical wear on lenses and mechanical joints.
* **Power & Density:** Dust accumulation on solar panels can reduce energy efficiency by up to 80%. Furthermore, storms change local air density, requiring more energy to maintain lift in an already thin atmosphere (1% of Earth's density).
* **Communication Noise:** Iron oxide in the dust and electromagnetic interference from storm-generated lightning can degrade signal quality, reducing bandwidth to minimal data packets.

### 3. Key Assumptions
* The aircraft has a pre-defined library of contingency maneuvers: `PUSH_THROUGH`, `EVADE`, and `HOLD_POSITION` (Landing).
* The aircraft is equipped with a heterogeneous sensor suite (Optical VIO and X-Band Radar) to provide redundancy.

## Part 2: Technical Architecture

### 1. Decision Logic (Cost Function Approach)
Instead of rigid, hard-coded thresholds, the `AutonomousDecisionEngine` uses a Weighted Cost Function. It evaluates each available maneuver and selects the one with the lowest "Risk Score".
* **Visibility Risk:** Penalizes flying through high-Tau environments using optical sensors.
* **Battery Drain:** Calculates the energy cost of longer evasion routes.
* **Mission Delay:** Penalizes maneuvers that significantly stray from the mission timeline.
* **Safety Buffer:** Automatically assigns a near-infinite cost (`Double.MAX_VALUE`) to maneuvers that would deplete the battery below a 5% safety threshold.

### 2. Extensibility & Design Patterns (Strategy Pattern)
To adhere to the **Open/Closed Principle (SOLID)**, the maneuver logic is built using the **Strategy Pattern**. Instead of using a rigid `if/else` structure, each maneuver (`PushThroughStrategy`, `EvadeStrategy`, `HoldPositionStrategy`) implements the `ManeuverStrategy` interface. This allows the engineering team to seamlessly add new flight maneuvers (e.g., `ClimbAltitudeStrategy`) without modifying the core decision engine.

### 3. Sensor Fusion & Fallback Strategy
The `SensorManager` implements a robust fallback mechanism:
* **Primary:** Optical Camera (VIO) for high-precision navigation.
* **Secondary:** X-Band Radar, which is less affected by dust scattering.
* **Logic:** If the `OpticalVisibilitySensor` reports high noise or failure due to Tau levels, the system seamlessly switches to Radar data to maintain situational awareness.

### 4. Hardware Wear Modeling
The simulation introduces a System Wear factor. As the aircraft flies through storms:
* Sensor health decreases.
* The `getValue()` method calculates Gaussian Noise scaled by the current wear, simulating the loss of precision in aging hardware.

## Part 3: Implementation Details

**Core Components (Organized by Domain)**
* **`main.engine`**: Contains the `AutonomousDecisionEngine` and the maneuver strategies. It acts as the "brain".
* **`main.environment`**: Contains `MarsEnvironment` (which randomly generates the dust storm progression, adhering to the Single Responsibility Principle) and `DroneState`.
* **`main.sensors`**: Contains the `SensorManager` and hardware implementations (`OpticalVisibilitySensor`, `RadarSensor`) with fallback logic.
* **`main.telemetry`**: Contains the `BlackBoxLogger` and `TelemetryPacket` for post-flight analysis.
* **`main.MissionSimulator`**: A time-step simulation engine that orchestrates the flow of time and applies the decisions.

## Part 4: Testing & Validation

The project includes a suite of unit tests in `AutonomousDecisionEngineTest` covering critical flight scenarios:
* **Clear Weather + Full Battery:** Validates that the engine chooses the most efficient path (`PUSH_THROUGH`).
* **Severe Storm + Sufficient Battery:** Validates the choice of a safer detour (`EVADE`).
* **Severe Storm + Low Battery:** Validates the emergency fail-safe landing (`HOLD_POSITION`) to prevent total aircraft loss.
* **Extreme Visibility Risk:** Validates the evasion maneuver when opacity is critical but battery allows it.

### How to Run
The easiest way to review and run the project is via an IDE (like IntelliJ IDEA):
1. Open the project folder in your IDE.
2. Run the `main.Main` class to start the simulation.
3. Run the `AutonomousDecisionEngineTest` class (in the `test` directory) to execute the JUnit 5 test suite.