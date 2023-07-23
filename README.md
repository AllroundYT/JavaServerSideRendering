# JavaServerSideRendering

This Project was started because I needed a framework for server side rendering with java. I didn't find anything
fitting for my use case, so I decided to create my own. Now the JavaServerSideRendering framework comes with a component
based system and an api to create RESTful apis. Because I made the framework for my self primarily it got the highest
quality support I can offer as long as I need it. The framework is based on vert.x and htmx which makes it superfast and
performant since vert.x is completely asynchronous and event driven. To use the framework just add the maven
dependencies and take a look into the test directory of the project.

Maven:

```maven
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
 	<dependency>
	    <groupId>com.github.AllroundYT</groupId>
	    <artifactId>JavaServerSideRendering</artifactId>
	    <version>v1.0.0-BETA</version>
	</dependency>
```
