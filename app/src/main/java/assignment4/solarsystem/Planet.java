package assignment4.solarsystem;

import java.util.ArrayList;

/**
 * Class that represents a planet object. Extends HeavenlyBody class.
 */
public class Planet extends HeavenlyBody {
  /**
   * The average radius of the planet's orbit in kilometers.
   */
  private double avgOrbitRadiusInKm;

  /**
   * A list of moons that orbit this planet.
   */
  private ArrayList<Moon> moons = new ArrayList<>();

  /**
   * Constructs a new Planet object with the specified name, average radius, and
   * average orbit radius.
   *
   * @param name               - The name of the planet.
   * @param avgRadiusInKm      - The average radius of the planet in kilometers.
   * @param avgOrbitRadiusInKm the average orbit radius of the planet in
   *                           kilometers.
   * @throws IllegalArgumentException if the average radius or the average orbit
   *                                  radius is not valid.
   */
  protected Planet(String name, int avgRadiusInKm, double avgOrbitRadiusInKm) {
    super(name, avgRadiusInKm);
    this.avgOrbitRadiusInKm = avgOrbitRadiusInKm;
    checkAvgRadiusInKm(avgRadiusInKm);
    if (avgOrbitRadiusInKm < 18000) {
      throw new IllegalArgumentException("Orbit radius is too small");
    }
  }

  /**
   * Adds a moon to this planet.  
   *
   * @param name               - The name of the moon.
   * @param avgRadiusInKm      - The average radius of the moon in kilometers.
   * @param avgOrbitRadiusInKm - The average orbit radius of the moon in
   *                           kilometers.
   * @return the newly created moon.
   * @throws IllegalArgumentException if the moon's radius is larger than half the 
   *                                   planet's radius
   *                                  or if the moon's orbit radius is less than
   *                                   60 kilometers.
   */
  public Moon addMoon(String name, int avgRadiusInKm, double avgOrbitRadiusInKm) {
    if (avgRadiusInKm > this.getAvgRadiusInKm() / 2) {
      throw new IllegalArgumentException(
        "Error: The moon's radius cannot be larger than half the size of its planet's radius.");
    }
    if (avgOrbitRadiusInKm < 60) {
      throw new IllegalArgumentException(
        "Error: The moon's orbit radius must be at least 60km.");
    }
    if (avgOrbitRadiusInKm < this.getAvgOrbitRadiusInKm()) {
      throw new IllegalArgumentException(
        "Error: The moon's orbit radius cannot be less than the planet's orbit radius.");
    }
    Moon moon = new Moon(name, avgRadiusInKm, avgOrbitRadiusInKm);
    moons.add(moon);
    return moon;
  }

  /**
   * Gets the average orbit radius of this planet in kilometers.
   *
   * @return the average orbit radius of this planet in kilometers.
   */
  public double getAvgOrbitRadiusInKm() {
    return avgOrbitRadiusInKm;
  }

  public ArrayList<Moon> getMoons() {
    return this.moons;
  }

  /**
   * Checks if the specified radius is valid for a planet.
   * The radius is valid if it is between 2,000 and 2,000,000 kilometers.
   *
   * @param radius - The radius to check
   * @throws IllegalArgumentException if the radius is not between 2,000 and
   *                                  2,000,000 kilometers.
   */
  protected void checkAvgRadiusInKm(int radius) {
    if (radius < 2000) {
      throw new IllegalArgumentException("Radius is too small");
    }
    if (radius > 2000000) {
      throw new IllegalArgumentException("Radius is too large");
    }
  }

  /**
   * Gets all the heavenly bodies associated with this planet, including the
   * planet itself and its moons.
   *
   * @return an array of all the heavenly bodies associated with this planet.
   */
  public HeavenlyBody[] getHeavenlyBodies() {
    ArrayList<HeavenlyBody> bodies = new ArrayList<>();
    bodies.add(new Planet(this.getName(), this.getAvgRadiusInKm(), this.avgOrbitRadiusInKm));
    for (Moon moon : this.moons) {
      bodies.add(new Moon(moon.getName(), moon.getAvgRadiusInKm(), moon.getAvgOrbitRadiusInKm()));
    }
    return bodies.toArray(new HeavenlyBody[0]);
  }
}
