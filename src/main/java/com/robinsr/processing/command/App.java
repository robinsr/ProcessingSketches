package com.robinsr.processing.command;

import com.robinsr.processing.sketches.BaseSketch;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import jline.TerminalFactory;
import org.fusesource.jansi.AnsiConsole;
import org.reflections.Reflections;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class App {

    public static void main(String[] args) {
        new App(args);
    }

    public App(String[] args) {
        AnsiConsole.systemInstall();
        System.out.println(ansi().render("Processing Sketches"));

        Map<String, String> sketches = getSketches();

        try {
            ConsolePrompt prompt = new ConsolePrompt();
            PromptBuilder promptBuilder = prompt.getPromptBuilder();

            ListPromptBuilder builder = promptBuilder.createListPrompt()
                    .name("sketchSelection")
                    .message("Select a sketch to run:");

            sketches.forEach((key, value) -> builder.newItem(key).text(value).add());

            builder.addPrompt();

            HashMap<String, ? extends PromtResultItemIF> result = prompt.prompt(promptBuilder.build());

            String selectedSketch = ((ListResult) result.get("sketchSelection")).getSelectedId();

            runSketch(selectedSketch);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                TerminalFactory.get().restore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void runSketch(String sketchClassName) {
        try {
            Class<?> sketchClass = Class.forName(sketchClassName);

            if (BaseSketch.class.isAssignableFrom(sketchClass)) {
                BaseSketch sketch = (BaseSketch) sketchClass.newInstance();
                System.out.printf("Running sketch %s%n", sketchClassName);
                sketch.runSketch(new String[] { sketchClassName });
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
    private Map<String, String> getSketches() {
        Reflections ref = new Reflections("com.robinsr.processing.sketches");

        Set<Class<? extends BaseSketch>> opts = ref.getSubTypesOf(BaseSketch.class);

        Function<Class<? extends BaseSketch>, String> nameGetter = clazz -> {
            try {
                return clazz.newInstance().getSketchName();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        };

        return opts.stream()
                .collect(Collectors.toMap(Class::getName, nameGetter));
    }
}
