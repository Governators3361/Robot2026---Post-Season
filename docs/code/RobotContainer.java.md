---
layout: default
title: RobotContainer.java
parent: Code
nav_order: 6
---

# Robot.java

<div class="field-section">
  <h3>Overview:</h3>
  <div class="field-col">
    <div class="field">
      <label>Where the structure of your robot lives, the majority of your higher level code will live here</label>
    </div>
    <div class="field">
      <label>The most important thing to understand with this class is the order in which your code is run. Many bugs have, can, and will arise from accidentally putting steps in the wrong order and calling a method on a null object. For this reason, this class has been organized into seperate segments that should prevent these errors, if you follow the schema provided.</label>
    </div>
  </div>
</div>
<div class="field-section">
  <h3>Heirarchy / ordered structure of the RobotContainer constructor:</h3>
  <div class="field-col">
    <div class="field">
      <label>First, you must instantiate all of the robot's subsystems. While it is tempting to create a method to encapsulate this process, it should only be called once and must initialize final variables, so it must be in the constructor.<br>When instantiating subsystems, you must also update the reference to these subsystems stored in SubsystemRegistry. These references in SubsystemRegistry are static, and allow for subsystems to find and interact with each other.</label>
    </div>
    <h3>Controller Bindings:</h3>
    <div class="field">
      <label>Next, you must create a default command for the drivesubsystem that takes input from the driver controller joysticks on the periodic scheduling of wpilib. Deadbands are applied to the controller here, these are very important so that minute inputs do not tweak out the robot when being kept intentionally stationary.</label>
    </div>
    <div class="field">
      <label>BUGFIX NOTES FOR THE AFOREMENTIONED DRIVER CONTROLS!.<br> - You may have an issue where driver input appears to be reversed on the left X axis (what we encountered), and you may have the instinct to multiply that axis by -1. I have done this (numerous times), and it is always wrong. Check that your gyro is mounted normally, and experiment with different orientations (haha).</label>
    </div>
    <div class="field">
      <label>Then, configureBindings() is called, which now just maps the function of buttons to certain Commands (see WPILIB section for detailed explanation of commands). Basically, a command is either whileTrue (continuos while holding down the button), or onTrue (just when tapped)</label>
    </div>
    <h3>configureAutoCommands()</h3>
    <div class="field">
      <label>This method creates NamedCommands, an important part of PathPlanner. PathPlanner paths reference these named Commands, and you must create them in your RobotContainer, otherwise these paths will fail.</label>
    </div>
  </div>
</div>
