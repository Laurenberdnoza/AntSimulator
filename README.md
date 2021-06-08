## Installation

### Dependencies
You will need [Java](https://www.java.com/en/download/) to run the simulation. The Gradle build tool
should take care of the rest of the dependencies automatically.

### From source
First, clone the repository:
```shell
git clone https://github.com/gekoke/ants
cd ants
```
To run the project once:
```shell
./gradlew run  # Or "gradlew.bat" on Windows
```

To build and run a jar file:
```shell
./gradlew jar
java -jar build/libs/AntSimulator.jar
```

## Usage

### Controls
- **Left Click** - Drag to place food chunks.
- **A** - Increase simulation speed (4x).
- **S** - Pause the simulation.
- **P** - Toggle pheromone visibility.
