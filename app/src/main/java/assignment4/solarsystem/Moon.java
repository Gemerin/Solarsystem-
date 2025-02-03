package assignment4.solarsystem;

/**
 * Class that represents a moon object. Extends HeavenlyBody class.
 */
public class Moon extends HeavenlyBody {

  /**
   * The average orbit radius of the moon in kilometers.
   */
  private double avgOrbitRadiusInKm;

  /**
   * Constructs a new Moon object with the specified name, average radius, and
   * average orbit radius.
   *
   * @param name               - The name of the moon.
   * @param avgRadiusInKm      - The average radius of the moon in kilometers.
   * @param avgOrbitRadiusInKm - The average orbit radius of the moon in
   *                           kilometers.
   * @throws IllegalArgumentException if the average radius or the average orbit
   *                                  radius is not valid.
   */
  protected Moon(String name, int avgRadiusInKm, double avgOrbitRadiusInKm) {
    super(name, avgRadiusInKm);
    this.avgOrbitRadiusInKm = avgOrbitRadiusInKm;
    checkAvgRadiusInKm(avgRadiusInKm);
    if (avgOrbitRadiusInKm < 60) {
      throw new IllegalArgumentException("Orbit radius is too small");
    }
  }

  /**
   * Gets the average orbit radius of this moon in kilometers.
   *
   * @return the average orbit radius of this moon in kilometers
   */
  public double getAvgOrbitRadiusInKm() {
    return avgOrbitRadiusInKm;
  }

  /**
   * Checks if the specified radius is valid for a moon.
   *
   * @param radius - The radius to check.
   * @throws IllegalArgumentException if the radius is not between 6 and 10,000
   *                                  kilometers
   */
  protected void checkAvgRadiusInKm(int radius) {
    if (radius < 6) {
      throw new IllegalArgumentException("Radius is too small");
    }
    if (radius > 10000) {
      throw new IllegalArgumentException("Radius is too large");
    }
  }
}
