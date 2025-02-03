package assignment4;

import assignment4.solarsystem.Console;
import assignment4.solarsystem.HeavenlyBody;
import assignment4.solarsystem.HeavenlyBodyFactory;
import assignment4.solarsystem.Moon;
import assignment4.solarsystem.Planet;
import assignment4.solarsystem.Star;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the SolarSystem App.
 */
public class App {
  private String filePath = "./dataBase/solarsystems.data";
  private Console console;

  /**
   * The main entry point for the application.
   *
   * <p>This method initializes the application and adds a shutdown hook to save the
   * state of the application
   * when the JVM shuts down. It then starts the application by calling the `run`
   * method.
   *
   * @param args The command-line arguments passed to the application. This
   *             parameter is not currently used.
   */
  public static void main(String[] args) {
    App theApp = new App();
    // Add shutdown hook
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        theApp.saveDataToFile(theApp.filePath, theApp.console.getSolarSystems());
      }
    });
    theApp.run();
  }

  /**
   * Loads the data from the file, initializes the console, adds the loaded stars
   * to the console,
   * runs the console, and finally saves the data back to the file.
   *
   * <p>This method is the main execution point for the application after
   * initialization.
   */
  private void run() {
    List<Star> stars = loadDataFromFile(filePath);
    System.out.println("Number of stars loaded: " + stars.size());
    console = new Console();
    for (Star star : stars) {
      console.addSolarSystem(star);
    }
    console.run();

    saveDataToFile(filePath, console.getSolarSystems());
  }

  /**
   * Saves the data of solar systems to a file.
   *
   * <p>This method writes the data of each star, planet, and moon in the solar
   * systems to a file.
   * If the file does not exist, it creates a new one. If the file's parent
   * directories do not exist,
   * it creates them. If the directories could not be created, it prints an error
   * message and returns.
   *
   * @param filePath     The path of the file to write the data to.
   * @param solarSystems The map of solar systems to save. The key is the name of
   *                     the star, and the value is the Star object.
   */
  private void saveDataToFile(String filePath, Map<String, Star> solarSystems) {
    File file = new File(filePath); // Kontrollera om överordnad mapp existerar, skapa den om inte
    if (!file.getParentFile().exists()) {
      boolean dirsCreated = file.getParentFile().mkdirs(); // Försöker skapa mappen
      if (!dirsCreated) {
        System.err.println("Could not create file.");
        return; // Avbryter om mapparna inte kunde skapas
      }
    }

    try (OutputStream fos = new FileOutputStream(file, false);
        Writer writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
      // Iterate over each entry in the solarSystems map
      for (Map.Entry<String, Star> entry : solarSystems.entrySet()) {
        Star star = entry.getValue();
        // Write star data
        bufferedWriter.write(star.getName() + ":" + star.getAvgRadiusInKm());
        bufferedWriter.newLine();
        // data för planet
        for (Planet planet : star.getPlanets()) {
          bufferedWriter
              .write("-" + planet.getName() + ":" + planet.getAvgRadiusInKm() + ":" + planet.getAvgOrbitRadiusInKm());
          bufferedWriter.newLine();
          // månar
          for (Moon moon : planet.getMoons()) {
            bufferedWriter
                .write("--" + moon.getName() + ":" + moon.getAvgRadiusInKm() + ":" + moon.getAvgOrbitRadiusInKm());
            bufferedWriter.newLine();
          }
        }
      }
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads the data of solar systems from a file.
   *
   * <p>This method reads the data of each star, planet, and moon from a file and
   * creates the corresponding objects.
   * If the file does not exist, it creates a new one. If the file couldn't be
   * created, it returns an empty list.
   *
   * @param filePath The path of the file to read the data from.
   * @return A list of Star objects representing the stars loaded from the file.
   */
  private List<Star> loadDataFromFile(String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
        return new ArrayList<>(); // Return an empty list if the file couldn't be created
      }
    }
    List<Star> stars = new ArrayList<>();
    Star star = null;
    Planet currentPlanet = null;
    HeavenlyBodyFactory factory = new HeavenlyBodyFactory();

    try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(":");
        if (line.startsWith("--")) {
          // MOON
          if (currentPlanet != null && parts.length >= 3) {
            HeavenlyBody moon = factory.createHeavenlyBody("Moon", parts[0].substring(2), Integer.parseInt(parts[1]),
                Double.parseDouble(parts[2]));
            currentPlanet.addMoon(moon.getName(), ((Moon) moon).getAvgRadiusInKm(),
                ((Moon) moon).getAvgOrbitRadiusInKm());
          }
        } else if (line.startsWith("-")) {
          // PLANET
          if (parts.length >= 3) {
            HeavenlyBody planet = factory.createHeavenlyBody("Planet",
                parts[0].substring(1), Integer.parseInt(parts[1]),
                Double.parseDouble(parts[2]));
            currentPlanet = (Planet) planet;
            if (star != null) {
              star.addPlanet(currentPlanet.getName(), currentPlanet.getAvgRadiusInKm(),
                  currentPlanet.getAvgOrbitRadiusInKm());
            }
          }
        } else {
          // STAR
          if (star != null) {
            stars.add(star);
          }
          HeavenlyBody starBody = new Star(parts[0], Integer.parseInt(parts[1]));
          star = (Star) starBody;
        }
      }
      if (star != null) {
        stars.add(star);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stars;
  }
}