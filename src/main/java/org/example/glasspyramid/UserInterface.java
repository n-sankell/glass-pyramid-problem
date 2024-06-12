package org.example.glasspyramid;

import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;
    private boolean initialPyramidPrompt = true;
    private boolean initialRowPrompt = true;
    private boolean initialGlassPrompt = true;
    private GlassPyramid pyramid;
    private Integer selectedRow;
    private Integer selectedGlass;

    public void start() {
        reset();
        System.out.println(
            "Calculate the time in seconds to fill a given glass in a pyramid of glasses (type 'exit' anytime to quit)"
        );
        while (running) {
            try {
                if (pyramid == null) {
                    promptPyramidSize();
                }
                if (selectedRow == null && running) {
                    promptWhichRow();
                }
                if (selectedGlass == null && running) {
                    promptWhichGlass();
                }
                if (running) {
                    double fillTime = pyramid.calculateFillTimeForGlass(selectedRow, selectedGlass);
                    System.out.printf(
                        "Total fill time for glass %s on row %s = %s seconds%n%n", selectedGlass, selectedRow, fillTime
                    );
                    reset();
                    promptUseAgain();
                }
            }
            catch (IllegalArgumentException e) {
                if (e instanceof NumberFormatException) {
                    System.out.print("Please enter a valid number: ");
                } else {
                    System.out.print(e.getMessage());
                }
            }
        }
    }

    private void promptPyramidSize() {
        if (initialPyramidPrompt) {
            System.out.print("Specify the desired number of rows for the pyramid (2-50): ");
            initialPyramidPrompt = false;
        }
        while (pyramid == null && running) {
            var input = checkInput(scanner.nextLine());

            if (running) {
                int rows = Integer.parseInt(input);
                validatePyramidSize(rows);
                pyramid = new GlassPyramid(rows);
            }
        }
    }

    private void promptWhichRow() {
        if (initialRowPrompt) {
            System.out.print("Select a row in the pyramid: ");
            initialRowPrompt = false;
        }
        while (selectedRow == null && running) {
            var input = checkInput(scanner.nextLine());

            if (running) {
                int parsedLine = Integer.parseInt(input);
                validateRowInput(parsedLine);
                selectedRow = parsedLine;
            }
        }
    }

    private void promptWhichGlass() {
        if (initialGlassPrompt) {
            System.out.printf("Select a glass on row %s to calculate the fill time: ", selectedRow);
            initialGlassPrompt = false;
        }
        while (selectedGlass == null && running) {
            var input = checkInput(scanner.nextLine());

            if (running) {
                int parsedLine = Integer.parseInt(input);
                validateGlassInput(parsedLine, selectedRow);
                selectedGlass = parsedLine;
            }
        }
    }

    private void promptUseAgain() {
        System.out.println("Press enter to try again or type 'exit' to quit");
        checkInput(scanner.nextLine());
    }

    private String checkInput(String input) {
        if (input.equalsIgnoreCase("exit")) {
            running = false;
        }
        return input;
    }

    private void validatePyramidSize(int size) {
        if (size < 2 || size > 50) {
            throw new IllegalArgumentException("Please specify a row number between 2-50: ");
        }
    }

    private void validateRowInput(int row) {
        var glassPyramid = pyramid.getPyramid();
        if (!glassPyramid.containsKey(row)) {
            throw new IllegalArgumentException(
                String.format("No such row (%s), current size of the pyramid is %s: ", row, glassPyramid.size())
            );
        }
    }

    private void validateGlassInput(int glass, int row) {
        if (glass < 1 || pyramid.getPyramid().get(row).size() < glass) {
            throw new IllegalArgumentException(String.format("No such glass (%s) exists on row %s: ", glass, row));
        }
    }

    private void reset() {
        running = true;
        pyramid = null;
        selectedRow = null;
        selectedGlass = null;
        initialPyramidPrompt = true;
        initialRowPrompt = true;
        initialGlassPrompt = true;
    }

}
