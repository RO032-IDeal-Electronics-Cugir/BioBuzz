# Context for AI agets

We are IDeaL Electronics #19085 team inside the First Tech Challange(FTC) Robotics 
in the BioBuzz season.

Video for the season:

This is the stack that we use: Java in Android Studio, Pedro Pathing libraray 
for autonomous period

Before doing any changes, request permission and prompt us with your clear intention.
Make sure to create/modify the details of a code before doing any changes to make sure
the programmer agrees.

Our code lives in: TeamCode/src/main/java/org/firstinspires/ftc/teamcode
Artifacts folder: Artifacts
Our robot configuration is under: hardwareMap.md
We want folders for Autonomous, TeleOP, Subsystems(), Commands(using the subsystems, we create
more complex functions)
We desire a specific folder(TeamCode/src/main/java/org/firstinspires/ftc/teamcode/DetaliiCod) for 
descibing the code already written which should be modified after
every change in code(if something meaningful has changed) - every file should have a description
file attached in that folder. Make sure to read those files before planning the code

We tend on using PID/PIDF constants(not the DcMotor class, ones using formulas based on current 
and target position) and Finite State Machines to enhace the user experience
and make the code easier to debug. If you intend to use something similar/these exact ones, mention
them beforehand