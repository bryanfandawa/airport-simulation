# Airport Simulation (Java)

This project simulates airport operations using Java. It models how planes arrive, wait, take off, or crash due to fuel constraints. The purpose is to demonstrate how queues can be used to manage limited resources like airport runways.

---

## What This Program Does

The simulation uses one or two runways and runs through scenarios where planes either land or take off based on probability. Each plane that arrives has a limited fuel supply. If it waits too long, it crashes. The goal is to measure airport performance by tracking:
- Number of planes that take off
- Number of planes that land
- Average waiting times
- Number of crashes

---

## Plane Class Overview

The `Plane` class is used to represent each aircraft in the simulation. Each plane object tracks:
- The time it enters the queue (`in`)
- The time it starts landing or taking off (`out`)
- How much fuel it has (`fuel`)
- Whether it is departing or arriving (`departing`)

### Fields
| Field | Description |
|-------|-------------|
| `int in` | Time the plane entered the queue |
| `int out` | Time the plane left the queue to land or take off |
| `int fuel` | Maximum time the plane can wait before crashing |
| `boolean departing` | `true` if the plane is departing, `false` if arriving |

### Key Method
| Method | Purpose |
|--------|---------|
| `isDeparting()` | Returns whether the plane is scheduled to depart |

---

## Simulation Scenarios

| Scenario | Runways | Landing Priority |
|--------|---------|------------------|
| 1 | 1 runway | Equal priority for landing and takeoff |
| 2 | 2 runways | Equal priority |
| 3 | 1 runway | 66.67% chance that landing is prioritized |
| 4 | 2 runways | 66.67% chance that landing is prioritized |

---

## Input File Format

The program reads from an input file with the following variables:

| Variable | Description |
|----------|-------------|
| Takeoff_duration | Time required to take off |
| Landing_duration | Time required to land |
| Departure_rate | Probability a plane departs |
| Arrival_rate | Probability a plane arrives |
| Reserve_fuel_time | Time before a plane crashes |
| Simulation_time | Total simulation time |
| Description | Text description of the scenario |

---

## Output of Each Simulation

For each of the four scenarios, the program prints:
- Number of planes that departed
- Average wait time of departing planes
- Number of planes that landed
- Average wait time of landing planes
- Number of planes that crashed

---

## How the Simulation Works

1. The program reads settings from the input file.
2. Each minute, planes may arrive or be scheduled to depart.
3. Arriving planes are added to a landing queue; departing planes are added to a takeoff queue.
4. Runways process planes based on availability and rules.
5. If a plane waits longer than its fuel limit, it crashes.
6. The process repeats for all four scenarios, and statistics are printed.

---

## Method Summary

| Method | Description |
|--------|-------------|
| addPlaneToQueue() | Adds planes to landing or takeoff queues based on probability. |
| checkPlaneDeparting() | Frees the runway when a plane finishes landing or takeoff in single-runway mode. |
| doesPlaneCrash() | Removes planes that run out of fuel while waiting. |
| landPeople() | Lands a plane on the single runway if available. |
| departPeople() | Handles takeoff on the single runway if available. |
| addMinute() | Moves the time forward by one minute. |
| checkDepartingDoneTwo() | Frees runway after a plane completes takeoff (two-runway mode). |
| checkTakeoffDoneTwo() | Frees runway after a plane completes landing (two-runway mode). |
| checkLandingTwo() | Assigns planes to runways for landing in two-runway mode. |
| checkTakeoffTwo() | Assigns planes to runways for takeoff in two-runway mode. |

---

## How to Run

1. Compile the Java files:
```
javac src/airportSim/*.java
```
2. Run the program:
```
java airportSim.airportSimBetter
```

Make sure the input file is in the correct directory.

> **Note:** This simulation is based on a problem from *Data Structures & Other Objects Using Java* by Michael Main.
