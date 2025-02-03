package assignment4.solarsystem;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Class representing a console user interface object.
 */
public class Console {

  private Scanner scanner;
  private Map<String, Star> solarSystems;
  private Star star;
  private Planet planet;

  /**
   * Constructs a new Console object.
   * Initializes a Scanner object for user input and a HashMap to store solar
   * systems.
   */
  public Console() {
    this.scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
    this.solarSystems = new HashMap<>();
  }

  /**
   * Starts the console application.
   * Continuously prompts the user to list existing solar systems, create a new
   * solar system, or quit the application.
   * Handles user input and calls the appropriate methods based on the input.
   */
  public void run() {
    while (true) {
      System.out.println(
            "Enter 'l' to list existing solar systems, 'c' to create a new solar system or 'q' to quit:");
      if (!scanner.hasNextLine()) {
        System.out.println("No input provided.");
        return;
      }
      String input = scanner.nextLine().toLowerCase();
      switch (input) {
        case "l":
          listSolarSystems();
          break;
        case "c":
          createSolarSystem();
          break;
        case "q":
          return;
        default:
          System.out.println("Invalid input. Please try again.");
      }
    }
  }

  /**
   * Lists all available solar systems.
   * If there are no solar systems, it informs the user and returns.
   * Otherwise, it prints the names of all solar systems and prompts the user to
   * select one for development.
   * If the user provides valid input, it calls the `developSolarSystem` method
   * with the selected solar system.
   * If the user provides invalid input or no input, it informs the user and
   * returns.
   */
  private void listSolarSystems() {
    if (solarSystems.isEmpty()) {
      System.out.println("No solar systems available.");
      return;
    }

    System.out.println("Available solar systems:");
    for (String starName : solarSystems.keySet()) {
      System.out.println(starName);
    }
    System.out.println("Enter the name of the solar system you want to develop, or enter 'b' to go back: ");
    if (!scanner.hasNextLine()) {
      System.out.println("No input provided.");
      return;
    }
    String starName = scanner.nextLine();
    if (starName.equalsIgnoreCase("b")) {
      return;
    }

    Star selectedStar = null;
    for (Map.Entry<String, Star> entry : solarSystems.entrySet()) {
      if (entry.getKey().equalsIgnoreCase(starName)) {
        selectedStar = entry.getValue();
        break;
      }
    }
    if (selectedStar == null) {
      System.out.println("Solar system not found.");
      return;
    }

    developSolarSystem(selectedStar);
  }

  /**
   * Provides a menu for the user to interact with a selected solar system.
   * The user can display the solar system, add a planet, add a moon to a planet,
   * remove a member,
   * return to the main menu, sort members by size, or sort members by orbital
   * radius.
   *
   * @param selectedStar The star that is the center of the selected solar system.
   */
  private void developSolarSystem(Star selectedStar) {
    while (true) {
      System.out.println(
          "Enter 'd' to display the selected solar system, 'a' to add a planet, 'm' to add a moon to a planet, 'r' to "
          + "remove a member, or 'b' to back to the main menu, 's' to sort by size, or 'o' to sort by orbital radius.");
      String command = scanner.nextLine().toLowerCase();
      switch (command) {
        case "d":
          displaySolarSystem(selectedStar);
          break;
        case "a":
          addPlanets(selectedStar, true);
          break;
        case "m":
          System.out.println("Enter the name of the planet you want to add a moon to: ");
          System.out.println("Available planets:");
          for (Planet planet : selectedStar.getPlanets()) {
            System.out.println(planet.getName());
          }
          String planetName = scanner.nextLine();
          Planet planet = selectedStar.getPlanetByName(planetName);
          if (planet != null) {
            addMoon(planet);
          } else {
            System.out.println("Planet " + planetName + " not found.");
          }
          break;
        case "r":
          deleteMember(selectedStar);
          break;
        case "b":
          return;
        case "s":
          sortBySize(selectedStar);
          break;
        case "o":
          sortByOrbitalRadius(selectedStar);
          break;
        default:
          System.out.println("Invalid command. Please try again.");
      }
    }
  }

  /**
   * Creates a new solar system.
   * Prompts the user to enter the name and average radius of a star, and creates
   * a new Star object.
   * Then, in a loop, prompts the user to add a planet, add a moon to the last
   * created planet, or go back.
   * If the user chooses to add a planet, it calls the `addPlanets` method.
   * If the user chooses to add a moon, it prompts the user to enter the name of
   * the planet to add the moon to, and calls the `addMoon` method.
   * If the user chooses to go back, it breaks the loop.
   * Finally, it adds the created star to the solar systems map.
   */
  private void createSolarSystem() {
    System.out.println("Enter Star: ");
    String starName = scanner.nextLine();

    while (true) {
      try {
        System.out.println("Enter Star average radius in km:");
        int starRadius = scanner.nextInt();
        scanner.nextLine(); // consume newline left-over
        star = new Star(starName, starRadius);
        break; // break the loop if star is successfully created
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage() + " Please try again.");
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please etner a number.");
        scanner.nextLine();
      }
    }

    while (true) {
      System.out.println(
          "Enter 'p' to add a planet, 'm' to add a moon to the last created planet, or 'b' to go back: ");
      String choice = scanner.nextLine();
      if (choice.equalsIgnoreCase("b")) {
        break;
      } else if (choice.equalsIgnoreCase("p")) {
        addPlanets(star, true);
      } else if (choice.equalsIgnoreCase("m")) {
        if (planet == null) {
          System.out.println("No planet created yet. Please create a planet first.");
        } else {
          System.out.println("Enter the name of the planet you want to add a moon to: ");
          String planetName = scanner.nextLine();
          Planet selectedPlanet = star.getPlanetByName(planetName);
          if (selectedPlanet != null) {
            addMoon(selectedPlanet);
          } else {
            System.out.println("Planet " + planetName + " not found.");
          }
        }
      } else {
        System.out.println(
            "Invalid choice. Please enter 'p' to add planet, 'm' to add moon, or 'b' to go back.");
      }
    }
    solarSystems.put(star.getName(), star);
  }

  /**
   * Adds a new solar system to the map of solar systems.
   *
   * @param star The star that is the center of the solar system to add.
   */
  public void addSolarSystem(Star star) {
    this.solarSystems.put(star.getName(), star);
  }

  /**
   * Displays the details of a solar system.
   * Prints the name and average radius of the star, and for each planet, prints
   * the name, average radius, and average orbital radius.
   * For each moon of each planet, prints the name, average radius, and average
   * orbital radius.
   *
   * @param selectedStar The star that is the center of the solar system to
   *                     display.
   */
  public void displaySolarSystem(Star selectedStar) {
    System.out.println("Star: " + selectedStar.getName() + " - Average Radius: " + selectedStar.getAvgRadiusInKm());
    for (Planet planet : selectedStar.getPlanets()) {
      System.out.println("Planet: " + planet.getName() + " - Average Radius: " + planet.getAvgRadiusInKm()
          + " - Average Orbital Radius: " + planet.getAvgOrbitRadiusInKm());
      for (Moon moon : planet.getMoons()) {
        System.out.println("  Moon: " + moon.getName() + " - Average Radius: " + moon.getAvgRadiusInKm()
            + " - Average Orbital Radius: " + moon.getAvgOrbitRadiusInKm());
      }
    }
  }

  /**
   * Sorts and displays the heavenly bodies in a solar system by size.
   * Creates a list of all heavenly bodies in the solar system, sorts the list by
   * average radius in descending order,
   * and then prints the name and average radius of each heavenly body.
   *
   * @param selectedStar The star that is the center of the solar system to sort.
   */
  public void sortBySize(Star selectedStar) {
    List<HeavenlyBody> bodies = new ArrayList<>();

    // add star
    bodies.add(selectedStar);

    // planets and moons
    for (Planet planet : selectedStar.getPlanets()) {
      bodies.add(planet);
      bodies.addAll(planet.getMoons());
    }
    Collections.sort(bodies, new Comparator<HeavenlyBody>() {
        @Override
        public int compare(HeavenlyBody o1, HeavenlyBody o2) {
          return Double.compare(o2.getAvgRadiusInKm(), o1.getAvgRadiusInKm());
        }
      });
    for (HeavenlyBody body : bodies) {
      System.out.println(body.getName() + " - Average Radius: " + body.getAvgRadiusInKm());
    }
  }

  /**
   * Sorts and displays the heavenly bodies in a solar system by their average
   * orbital radius.
   * Creates a list of all heavenly bodies in the solar system, sorts the list by
   * average orbital radius in ascending order,
   * and then prints the name and average orbital radius of each heavenly body.
   *
   * @param selectedStar The star that is the center of the solar system to sort.
   */
  public void sortByOrbitalRadius(Star selectedStar) {
    List<HeavenlyBody> bodies = new ArrayList<>();

    bodies.add(selectedStar);

    for (Planet planet : selectedStar.getPlanets()) {
      bodies.add(planet);
      bodies.addAll(planet.getMoons());
    }

    Collections.sort(bodies, new Comparator<HeavenlyBody>() {
        @Override
        public int compare(HeavenlyBody o1, HeavenlyBody o2) {
          return o1.compareByOrbit(o2);
        }
      });
    for (HeavenlyBody body : bodies) {
      System.out.println(body.getName() + " - Average Orbital Radius: " + body.getAvgOrbitRadiusInKm());
    }
  }

  /**
   * Adds a new planet to a star.
   * If promptForInput is true, prompts the user to enter the name, average
   * radius, and average orbital radius of the planet.
   * Then, calls the `addPlanet` method of the star with the entered information.
   * If the `addPlanet` method throws an IllegalArgumentException, it prints the
   * exception message and asks for input again.
   * If the `addPlanet` method throws an InputMismatchException, it informs the
   * user and asks for input again.
   *
   * @param star           The star to add the planet to.
   * @param promptForInput Whether to prompt the user for input.
   */
  private void addPlanets(Star star, boolean promptForInput) {
    String planetName = null;
    int planetRadius = 0;
    double planetOrbitRadius = 0.0;

    if (promptForInput) {
      System.out.println("Enter planet name: ");
      planetName = scanner.nextLine();

      while (true) {
        try {
          System.out.println("Enter planet average radius in km: ");
          planetRadius = scanner.nextInt();
          scanner.nextLine();

          System.out.println("Enter planet orbit radius in km: ");
          planetOrbitRadius = scanner.nextDouble();
          scanner.nextLine();

          planet = star.addPlanet(planetName, planetRadius, planetOrbitRadius);
          break; // break the loop if the planet was added successfully
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
        // don't break the loop, ask for input again
        } catch (InputMismatchException e) {
          System.out.println("Invalid input. Please enter a new number.");
          scanner.nextLine();
        }
      }
    }
  }

  /**
   * Adds a new moon to a planet.
   * If the selected planet is null, it informs the user and returns.
   * Otherwise, it prompts the user to enter the name, average radius, and average
   * orbital radius of the moon.
   * If the entered orbital radius is less than the planet's orbital radius, it
   * informs the user and asks for input again.
   * If the `addMoon` method of the planet throws an IllegalArgumentException, it
   * prints the exception message and asks for input again.
   * If the `addMoon` method of the planet throws an InputMismatchException, it
   * informs the user and asks for input again.
   *
   * @param selectedPlanet The planet to add the moon to.
   */
  private void addMoon(Planet selectedPlanet) {
    if (selectedPlanet == null) {
      System.out.println("Planet not found.");
      return;
    }
    System.out.println("Enter moon name: ");
    String moonName = scanner.nextLine();

    while (true) {
      try {
        System.out.println("Enter moon average radius in km: ");
        int moonRadius = scanner.nextInt();
        scanner.nextLine();

        double moonOrbitRadius;
        do {
          System.out.println("Enter moon orbit radius in km: ");
          moonOrbitRadius = scanner.nextDouble();
          scanner.nextLine();

          if (moonOrbitRadius < selectedPlanet.getAvgOrbitRadiusInKm()) {
            System.out.println(
                "The moon's orbit radius cannot be less than the planet's orbit radius. Please enter "
                + "a larger orbit radius.");
          }
        } while (moonOrbitRadius < selectedPlanet.getAvgOrbitRadiusInKm());

        selectedPlanet.addMoon(moonName, moonRadius, moonOrbitRadius);
        break; // break the loop if the moon was added successfully
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a number.");
        scanner.nextLine();
      }
    }
  }

  /**
   * Deletes a member from a solar system.
   * Prompts the user to enter a command to delete the entire solar system, delete
   * a planet, delete a moon, or go back.
   * If the user enters 's', it removes the solar system from the map and calls
   * the `run` method.
   * If the user enters 'p', it prompts the user to enter the name of the planet
   * to delete, and if the planet is found, it removes the planet and its moons
   * from the solar system.
   * If the user enters 'm', it prompts the user to enter the name of the moon to
   * delete, and if the moon is found, it removes the moon from the planet.
   * If the user enters 'b', it returns.
   * If the user enters any other command, it informs the user and asks for input
   * again.
   *
   * @param selectedStar The star that is the center of the solar system to delete
   *                     a member from.
   */
  private void deleteMember(Star selectedStar) {
    System.out.println(
        "Enter 's' to delete a entire solar system, 'p' to delete a planet, 'm' to delete a moon, or 'b' to go back: ");
    String command = scanner.nextLine();
    switch (command) {
      case "s":
        solarSystems.remove(selectedStar.getName());
        System.out.println("Solar system deleted.");
        run();
        break;
      case "p":
        System.out.println("Planets in the solar system:");
        for (Planet p : selectedStar.getPlanets()) {
          System.out.println(p.getName());
        }
        System.out.println("Enter the name of the planet you want to delete: ");
        String planetName = scanner.nextLine();
        Planet planetToRemove = null;
        for (Planet p : selectedStar.getPlanets()) {
          if (p.getName().equals(planetName)) {
            planetToRemove = p;
            break;
          }
        }
        if (planetToRemove == null) {
          System.out.println("Planet not found.");
        } else {
          planetToRemove.getMoons().clear(); // delete all moons
          selectedStar.getPlanets().remove(planetToRemove);
          System.out.println("Planet and its moons deleted.");
        }
        break;
      case "m":
        if (planet == null) {
          System.out.println("No planet selected.");
          break;
        }
        System.out.println("Moons of the planet:");
        for (Moon m : planet.getMoons()) {
          System.out.println(m.getName());
        }
        System.out.println("Enter the name of the moon you want to delete: ");
        String moonName = scanner.nextLine();
        Moon moonToRemove = null;
        for (Moon m : planet.getMoons()) {
          if (m.getName().equals(moonName)) {
            moonToRemove = m;
            break;
          }
        }
        if (moonToRemove == null) {
          System.out.println("Moon not found.");
        } else {
          planet.getMoons().remove(moonToRemove);
          System.out.println("Moon deleted.");
        }
        break;
      case "b":
        return;
      default:
        System.out.println("Invalid command. Please try again.");
    }
  }

  /**
   * Returns a copy of the map of solar systems.
   *
   * @return A new HashMap containing all the entries of the original solar
   *         systems map.
   */
  public Map<String, Star> getSolarSystems() {
    return new HashMap<>(solarSystems);
  }
}
