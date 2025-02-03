package assignment4.solarsystem;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that represents a star object. Extends HeavenlyBody class.
 */
public class Star extends HeavenlyBody {

  /**
   * A list of planets that orbits this star.
   */
  private ArrayList<Planet> planets = new ArrayList<>();

  /**
   * Constructs a new Star with the given name and average radius.
   *
   * @param name          - The name of the star.
   * @param avgRadiusInKm the average radius of the star in kilometers.
   * @throws IllegalArgumentException if the average radius is less than 17,000
   *                                  kilometers.
   */
  public Star(String name, int avgRadiusInKm) {
    super(name, avgRadiusInKm);
    if (avgRadiusInKm < 17000) {
      throw new IllegalArgumentException("Radius is too small");
    }
  }

  /**
   * Array List of the planets.
   *
   * @return - The planents.
   */
  public ArrayList<Planet> getPlanets() {
    return this.planets;
  }

  /**
   * Adds a new planet to this star.
   *
   * @param name               - The name of the planet.
   * @param avgRadiusInKm      - The average radius of the planet in kilometers.
   * @param avgOrbitRadiusInKm the average orbit radius of the planet in
   *                           kilometers.
   * @return the newly created planet.
   */
  public Planet addPlanet(String name, int avgRadiusInKm, double avgOrbitRadiusInKm) {
    if (avgRadiusInKm < 2000 || avgRadiusInKm > 200000) {
      throw new IllegalArgumentException(
        "Planet radius is not within the acceptable range! Radius should be between 2000 and 200000.");
    }
    Planet newPlanet = new Planet(name, avgRadiusInKm, avgOrbitRadiusInKm);
    // collision check with other planets
    for (Planet existingPlanet : planets) {
      double minimumDistance = existingPlanet.getAvgRadiusInKm() + newPlanet.getAvgRadiusInKm();
      if (Math.abs(avgOrbitRadiusInKm - existingPlanet.getAvgOrbitRadiusInKm()) < minimumDistance) {
        throw new IllegalArgumentException(
            "Planet Orbit too close to other planet! Minimum required orbit radius is " + minimumDistance);
      }
    }
    planets.add(newPlanet);
    return newPlanet;
  }

  /**
   * Gets all heavenly bodies associated with this star, including the star itself
   * and all its planets.
   *
   * @return an array of all heavenly bodies associated with this star.
   */
  public HeavenlyBody[] getHeavenlyBodies() {
    ArrayList<HeavenlyBody> bodies = new ArrayList<>();
    bodies.add(new Star(this.getName(), this.getAvgRadiusInKm()));
    for (Planet planet : planets) {
      bodies.addAll(Arrays.asList(planet.getHeavenlyBodies()));
    }
    return bodies.toArray(new HeavenlyBody[0]);
  }

  /**
   * Retrieves a Planet object from the list of planets by its name.
   *
   * @param name The name of the planet to retrieve.
   * @return The Planet object with the specified name, or null if no such planet
   *         exists.
   */
  public Planet getPlanetByName(String name) {
    for (Planet planet : planets) {
      if (planet.getName().equals(name)) {
        return planet;
      }
    }
    return null;
  }
}
