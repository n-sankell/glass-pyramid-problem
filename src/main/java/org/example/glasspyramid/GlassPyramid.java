package org.example.glasspyramid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlassPyramid {

    private final Map<Integer, List<Glass>> pyramid;
    private Glass selectedGlass;

    public GlassPyramid(int rows) {
        pyramid = buildPyramid(rows);
    }

    public double calculateFillTimeForGlass(int row, int glassNumber) {
        var topGlass = getTopGlass();
        selectedGlass = getSelectedGlass(row, glassNumber);
        var elapsedTime = 0.000;
        var increment = 0.001;

        while (selectedGlassIsFilling()) {
            topGlass.increaseFillAmount(increment, 10);
            elapsedTime += increment;

            pyramid.values().stream()
                .takeWhile(this::selectedGlassIsFilling)
                .forEach(list -> list
                    .forEach(Glass::receiveOverflow)
                );
        }

        return elapsedTime;
    }

    private Map<Integer, List<Glass>> buildPyramid(int rows) {

        Map<Integer, List<Glass>> pyramid = new HashMap<>();

        var previousRow = createRowOfGlasses(1, null);
        pyramid.put(1, previousRow);

        for (int i = 2; i <= rows; i++) {
            var glasses = createRowOfGlasses(i, previousRow);

            pyramid.put(i, glasses);
            previousRow = glasses;
        }
        return pyramid;
    }

    private List<Glass> createRowOfGlasses(int size, List<Glass> previousRow) {
        List<Glass> glasses = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            var glass = new Glass(String.format("G%sR%s", i+1, size));

            if (previousRow != null) {
                if (i == 0) {
                    glass.setAboveRightGlass(previousRow.getFirst());
                } else if (i == size - 1) {
                    glass.setAboveLeftGlass(previousRow.getLast());
                } else {
                    glass.setAboveLeftGlass(previousRow.get(i-1));
                    glass.setAboveRightGlass(previousRow.get(i));
                }
            }
            glasses.add(glass);
        }
        return glasses;
    }

    public boolean selectedGlassIsFilling() {
        return !selectedGlass.isFull();
    }

    private boolean selectedGlassIsFilling(List<Glass> glasses) {
        return !selectedGlass.isFull();
    }

    public Map<Integer, List<Glass>> getPyramid() {
        return pyramid;
    }

    public Glass getTopGlass() {
        return pyramid.get(1).getFirst();
    }

    public Glass getSelectedGlass(int row, int glassNumber) {
        return pyramid.get(row).get(glassNumber-1);
    }

}
