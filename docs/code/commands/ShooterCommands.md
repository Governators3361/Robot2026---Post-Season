---
layout: default
title: Shooter Commands
parent: Commands
grand_parent: Code
nav_order: 1
---

# Shooter Commands

<div class="field-section">
  <h3>Commands for controlling custom rpm</h3>
  <div class="field-col">
    <div class="field">
      <label>This set of commands was created for testing and quick adjustments to the target rpm of the shooter mid match. It allowed us to be variable in the distance we shoot from, and enabling advanced actions like spitting with the shooter for moving balls around.</label>
    </div>
    <h3>Coding guidelines used:</h3>
    <div class="field">
      <label>What sticks out in this class is how it was used **in addition** to our other shoot command and bindings. While we certainly didn't want to annoy drivers with switching their target rpms all the time, we implemented this feature right before a tournament, and did not have time to fully test it. By doing it this way, we added a feature WITHOUT messing with the pre-existing, functional shoot command.</label>
    </div>
  </div>
</div>
<div class="field-section">
  <h3>CommandIncrement</h3>
  <div class="field-col">
    <div class="field">
      <label>This command simply increments the customRpm in shootControlSubsystem. We find the instance of the shootControlSubsytem using SubsystemRegistry, and then get the current custom rpm. Our increment button adds 250 rpm, and then we quickly use the Math.min function to ensure our target rpm does not go above 5000. Then, we set the rpm in shootControlSubsytem. <br>This process was quite quick and easy, and that should be a general goal in creating commands. Note how this command is instantaneos, so isFinished always returns true, and in configureBindings the command is configured as an onTrue binding.</label>
    </div>
  </div>
  <h3>CommandDecrement</h3>
  <div class="field-col">
    <div class="field">
      <label>This command is extremely similar to CommandIncrement (seen above) except it decrements down to a minimum rpm of 0.</label>
    </div>
  </div>
</div>
<div class="field-section">
  <h3>Commands for controlling intake and loading motors</h3>
  <div class="field-col">
    <div class="field">
      <label>CommandIntake:<br>This command simply spins the intake motor inwards and the loading motor as to load the hopper with fuel. It is configured on a whileTrue button, therefore it always returns false in it's isFinished method, and it stops the motors when the button is released (using the end button)</label>
    </div>
    <div class="field">
      <label>CommandReverseIntake:<br>This command is the same as CommandIntake, obviously except for the fact that it runs the motors in reverse in order to spit fuel.</label>
    </div>
  </div>
</div>
