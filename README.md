Jankytray
=============

A system tray application that displays the current status of a jenkins/hudson instance, and its jobs. 

Building
--------
	mvn package
	
Usage
------
	java -jar jankytray-xxx-RELEASE.jar -url http://some/jenkinsServer
	
Command line options
--------------------
    -url baseJenkinsUrl (required)
    -pollingInterval seconds (default: 30 seconds)
