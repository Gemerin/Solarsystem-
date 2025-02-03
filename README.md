# Solar system


1. Copy the clone link and use `git clone` to make a local copy in a suitable folder on your computer.
2. Step into the created folder using `cd`
3. Build the application using `./gradlew build` - it should build without problems
4. Run the application using `./gradlew run -q --console=plain` - it should display an hello world message.


## Project structure

Use `./gradlew build` to build the system - this will also report errors from the plugins above in the console. If you have many issues it is adviced to tackle the first one.

To run the application use `./gradlew run -q --console=plain`

## Thoughts 
I will began by creating four classes, heavenlyBody, star, planet, and moon. I will be using the classes from the solarsystem assingment2 as it allows for an abstract class to maintain checks for the same name and radius of each class, and due to the checkavgradius it allows for checks between moons and planets. 
The Average Radius
For the Sun the average radius must be greater than or equal to 17,000 kilometers. If it's less than 17,000 kilometers, an IllegalArgumentException is thrown with the message "Radius is too small"
For the planets the average radius must be between 2,000 and 200,000 kilometers inclusive. If it's outside this range, an IllegalArgumentException is thrown with the message "Planet radius is not within the acceptable range! Radius should be between 2000 and 200000."
For the moon the average radius must be between 6 and 10,000 kilometers inclusive. If it's outside this range, an IllegalArgumentException is thrown with the message "Radius is too small" for radii less than 6, and "Radius is too large" for radii greater than 10,000.

The Orbit Radius
The orbit radius of the planet (the average distance from the star) must be such that it doesn't collide with any other planet. This is checked by comparing the absolute difference between the new planet's orbit radius and each existing planet's orbit radius with the sum of the star's radius and the existing planet's radius. If the absolute difference is less than the sum, it means the planets would collide, and an IllegalArgumentException is thrown with the message "Planet Orbit too close to other planet! Minimum required orbit radius is " followed by the minimum distance.
For the moon the average orbit radius (the average distance from the planet it orbits) must be greater than or equal to 60 kilometers. If it's less than 60 kilometers, an IllegalArgumentException is thrown with the message "Orbit radius is too small".
These rules ensure that the planets and moons maintain a safe distance from each other and their parent celestial bodies, preventing any collisions.

### Class Diagram
![Solar system class diagram](./Images/solarsystem.png)

I have developed the class diagram more. To maintain encapsulation and allow for the creation of multiple planets I am going to use te factory design pattern to create a heavenlybodyfactory class which allows for a basic constructor for each of the types such as planet, moon and star. This class has a single method createHeavenlyBody which takes in a type, name, avgRadiusInKm, and orbitRadiusInKm. Depending on the type provided, it creates and returns an instance of either Star, Planet, or Moon - all of which are subclasses of HeavenlyBody.

I have implemented a add moon and add planets method that are called when creating a solar system to make the code more concise with less repetition and instead code resuse. The Star, Planet, and Moon classes are subclasses of HeavenlyBody, represented by the arrows pointing up from them to HeavenlyBody. By keeping core methods and information private in Star and Planet classes, you enforce encapsulation. This means other parts of your program can't directly modify their internal state, which helps prevent errors and unexpected behavior. Controlled Access via Factory: The factory acts as a gatekeeper, providing controlled access to object creation and potentially exposing only necessary methods (public methods) of Star and Planet classes through the objects it creates.

The HeavenlyBodyFactory uses the Star, Planet, and Moon classes to create instances of them. This is represented by the arrows pointing down from the subclasses to HeavenlyBodyFactory.
The Console class interacts with the HeavenlyBodyFactory to create HeavenlyBody instances based on user input. This is represented by the arrow from ConsoleUI to HeavenlyBodyFactory.
