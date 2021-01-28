# SPIN_2D_PhysicsGame

#### Note: Read Report.pdf for a full description and analysis of the game

## Compiling and Running the code

### 1. Using IntelliJ

▪ Unzip the folder containing the source files [source_files.zip]

▪ Load the project from the folder named ‘FinalProject’

▪ Run the ‘src/FinalProject_SourceFiles/ThreadedGuiForPhysicsEngine.java’ class [It contains the main() method]

### 2. Using command line

▪ Unzip the folder containing the source files [source_files.zip]

▪ Navigate to the ‘FinalProject’ folder as the working directory

▪ Compile the source files using the following command:

`javac -cp lib/jbox2d-library-2.2.1.1.jar -d classes src/FinalProject_SourceFiles/*.java`

▪ Create an executable jar file using the following command:

`jar cfm Spin.jar manifest.text -C classes FinalProject_SourceFiles`

▪ Run the executable .jar file using the following command:

`java -jar Spin.jar`

▪ Main-Class: /src/FinalProject_SourceFiles/ThreadedGuiForPhysicsEngine

▪ Class-Path: lib/jbox2d-library-2.2.1.1.jar

### 3. Running the executable .jar file directly

▪ ‘Spin.jar’ can be directly run from the command line using the following command:

`java -jar Spin.jar`
