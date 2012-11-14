Jankytray
=============

A system tray application that displays the current status of a jenkins/hudson instance, and its jobs. 

Building
--------
	1) Set desired platform in gradle.properties
	2) gradle clean createJar
	3) Runnable jar and all runtime dependencies will be located under build/output

Running
	1) Follow the build steps and run the jar
	- or -
	2) gradle runApp 
	
	Note #2 is more for development than everyday usage.
	