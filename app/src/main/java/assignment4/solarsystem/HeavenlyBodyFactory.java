package assignment4.solarsystem;

/**
 * A factory class for creating different types of heavenly bodies.
 */
public class HeavenlyBodyFactory {

  /**
   * Creates a new heavenly body of the specified type.
   *
   * @param type            The type of the heavenly body to create. This should
   *                        be "Star", "Planet", or "Moon".
   * @param name            The name of the heavenly body.
   * @param avgRadiusInKm   The average radius of the heavenly body in kilometers.
   * @param orbitRadiusInKm The radius of the heavenly body's orbit in kilometers.
   * @return A new heavenly body of the specified type.
   * @throws IllegalArgumentException If the specified type is not "Star",
   *                                  "Planet", or "Moon".
   */
  public HeavenlyBody createHeavenlyBody(String type, String name, int avgRadiusInKm, double orbitRadiusInKm) {
    switch (type) {
      case "Star":
        return new Star(name, avgRadiusInKm);
      case "Planet":
        return new Planet(name, avgRadiusInKm, orbitRadiusInKm);
      case "Moon":
        return new Moon(name, avgRadiusInKm, orbitRadiusInKm);
      default:
        throw new IllegalArgumentException("Invalid type: " + type);
    }
  }
}
