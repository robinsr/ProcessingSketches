package com.robinsr.processing.command;

import com.robinsr.processing.sketches.BaseSketch;
import org.reflections.Reflections;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        new App(args);
    }

    public App(String[] args) {
        String msg = String.join("\n",
                "Welcome", "Select a Sketch", ""
        );
        System.out.println(msg);

        List<String> sketches = getSketches();

        List<String> options = sketches.stream()
                .map(sketch -> sketches.indexOf(sketch) + ": " + sketch)
                .collect(Collectors.toList());

        System.out.println(String.join("\n", options));


        Scanner scanner = new Scanner(System.in);
        int selection = scanner.nextInt();

        try {
            String className = sketches.get(selection);

            Class<?> sketchClass = Class.forName(className);

            if (BaseSketch.class.isAssignableFrom(sketchClass)) {
                BaseSketch sketch = (BaseSketch) sketchClass.newInstance();
                System.out.printf("Running sketch %s%n", className);
                sketch.runSketch(new String[] { className });
            }

            System.out.println("All done! Thanks");
        } catch (IndexOutOfBoundsException | ClassNotFoundException e) {
            System.out.println("Try again");
        } catch (InstantiationException e) {
            System.err.println("Can not instantiate sketch class");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("Can not run sketch class method 'runSketch'");
            e.printStackTrace();
        }
    }

    /**
     * Gets the class names for all project sketches
     * @return list of sketch class names
     */
    private static List<String> getSketches() {
        Reflections ref = new Reflections("com.robinsr.processing.sketches");

        Set<Class<? extends BaseSketch>> opts = ref.getSubTypesOf(BaseSketch.class);

        return opts.stream()
                .map(Class::getName)
                .collect(Collectors.toList());
    }

}
