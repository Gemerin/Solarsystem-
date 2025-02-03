package assignment4.solarsystem;

/**
 * Represents a heavenly body, which can be any celestial object in space.
 * This class implements the Comparable interface, allowing for comparison
 * between different heavenly bodies.
 */
public abstract class HeavenlyBody implements Comparable<HeavenlyBody> {

  /** 
   * The name of the heavenly body.
   */
  private String name;

  /**
   * The average radius of the heavenly body in kilometers.
   */
  private int avgRadiusInKm;

  /** 
   * The orbital radius of the heavenly body in kilometers.
 */
  private double avgOrbitRadiusInKm;

  /**
   * Constructs a new HeavenlyBody object with the specified name and average 
   * radius.
   *
   * @param name          - The name of the heavenly body.
   * @param avgRadiusInKm - The average radius of the heavenly body in kilometers.
   */
  protected HeavenlyBody(String name, int avgRadiusInKm) {
    setName(name);
    setAvgRadiusInKm(avgRadiusInKm);
  }

  /*
   * Gets the name of the heavenly body.
   *
   * @return The name of this heavenly body.
   */
  public String getName() {
    return name;
  }


  /**
   * Set the name of the heavenly body.
   *
   * @param newName - The new name for the this heavenly body.
   * @throws IllegalArgumentException if the new name is null or empty.
   */
  private void setName(String newName) {
    if (newName == null || newName.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    this.name = newName;
  }

  /**
   * Gets the average radius of this heavenly body in kilometers.
   *
   * @return The average radius of this heavenly body.
   */
  public int getAvgRadiusInKm() {
    return avgRadiusInKm;
  }

  /**
   * Sets the average radius of this heavenly body in kilometers.
   *
   * @param radius the new average radius for this heavenly body in kilometers.
   * @throws IllegalArgumentException if the radius is not valid for this type of
   *                                  heavenly body.
   */
  private void setAvgRadiusInKm(int radius) {
    checkAvgRadiusInKm(radius);
    this.avgRadiusInKm = radius;
  }
    
  /**
   * Gets the orbital radius of this heavenly body in kilometers.
   *
   * @return The orbital radius of this heavenly body.
   */
  public double getAvgOrbitRadiusInKm() {
    return avgOrbitRadiusInKm;
  }

  /**
   * Sets the orbital radius of this heavenly body in kilometers.
   *
   * @param radius the new orbital radius for this heavenly body in kilometers.
   * @throws IllegalArgumentException if the radius is not valid for this type of
   *                                  heavenly body.
   */
  protected void setAvgOrbitRadiusInKm(double avgOrbitRadiusInKm) {
    this.avgOrbitRadiusInKm = avgOrbitRadiusInKm;
  }
    
  /**
   * Checks if the specified radius is valid for this type of heavenly body.
   * This method should be overridden by subclasses to provide specific validation
   * logic.
   *
   * @param radius the radius to check.
   * @throws IllegalArgumentException if the radius is not valid for this type of
   *                                  heavenly body.
   */
  protected void checkAvgOrbitRadiusInKm(double radius) {
    if (radius <= 0) {
      throw new IllegalArgumentException("Orbital radius must be positive.");
    }
  }

  /**
   * Checks if the specified radius is valid for this type of heavenly body.
   * This method should be overridden by subclasses to provide specific validation
   * logic. 
   *
   * @param radius the radius to check.
   * @throws IllegalArgumentException if the radius is not valid for this type of
   *                                  heavenly body.
   */
  protected void checkAvgRadiusInKm(int radius) {
  }

  /**
   * Compares this heavenly body to another heavenly body based on their average
   * radius.
   *
   * @param other the other heavenly body to compare to
   * @return a negative integer, zero, or a positive integer as this object's
   *         average radius is less than,
   *         equal to, or greater than the specified object's average radius. 
   */
  @Override
  public int compareTo(HeavenlyBody other) {
    return Integer.compare(this.getAvgRadiusInKm(), other.getAvgRadiusInKm());
  }
 
  /**
   * Compares this heavenly body to another heavenly body based on their average orbital
   * radius.
   *
   * @param other the other heavenly body to compare to
   * @return a negative integer, zero, or a positive integer as this object's
   *         average orbit radius is less than,
   *         equal to, or greater than the specified object's average orbit radius.
   */
  public int compareByOrbit(HeavenlyBody other) {
    return Double.compare(this.getAvgOrbitRadiusInKm(), other.getAvgOrbitRadiusInKm());
  }

  /**
   * Method called by the garbage collector on an object.
   */
  protected final void finalize() {
  }
}
