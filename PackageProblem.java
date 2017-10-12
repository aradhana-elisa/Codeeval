import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Main {
    
    private final void calculate(final String path) {

        final String comma = ",";
        final Pattern patternColon = Pattern.compile(":");
        final Pattern patternBraces = Pattern.compile("\\)\\(");
        final Pattern patternComma = Pattern.compile(comma);
        final String openBrace = "(";
        final String closedBrace = ")";
        final String empty = "";
        final String space = " ";
        final String dollar = "$";
        final String minus = "-";

        final int[] emptyIntArray = new int[0];

        try {
            int itemSize;
            int maxWeightPackage;
            List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);

            for (String line : lines) {
                if (line.length() == 0) {
                    continue;
                }
                line = line.replace(space, empty);
                String[] packageline = patternColon.split(line, 0);
                maxWeightPackage = Integer.valueOf(packageline[0]);
                if (maxWeightPackage > 100) {
                    continue;
                }
                String[] sItems = patternBraces.split(packageline[1], 0);

                int stringLength = sItems.length;

                List<Item> items = new ArrayList<>(stringLength);

                for (int i = 0; i < stringLength; i++) {
                    String sItem = sItems[i].replace(openBrace, empty);
                    sItem = sItem.replace(closedBrace, empty);
                    String[] itemData = patternComma.split(sItem, 0);

                    final float weightValue = Float.valueOf(itemData[1]);

                    if (weightValue <= maxWeightPackage) {
                        Item item = new Item();
                        item.indexValue = Integer.valueOf(itemData[0]);
                        item.weightValue = weightValue;
                        item.costValue = Integer.valueOf(itemData[2].replace(dollar, empty));
                        items.add(item);
                    }
                }
                Collections.sort(items);

                itemSize = items.size();
                int maximumCost = 0;
                float maxWeight = maxWeightPackage;

                int[] cPack = emptyIntArray;
                int cPacked = 0;
                for (int i = 0; i < itemSize; i++) {
                    for (int j = i; j < itemSize; j++) {

                        int actualCost = 0;
                        float actualWeight = 0;
                        int[] pack = new int[15];
                        int packed = 0;

                        final Item item1 = items.get(j);
                        if ((actualWeight + item1.weightValue) <= maxWeightPackage) {
                            pack[item1.indexValue] = 1;
                            actualCost += item1.costValue;
                            actualWeight += item1.weightValue;
                            packed++;
                        }

                        for (int k = i; k < itemSize; k++) {
                            if (k != j) {
                                final Item item = items.get(k);
                                if ((actualWeight + item.weightValue) <= maxWeightPackage) {
                                    pack[item.indexValue] = 1;
                                    actualCost += item.costValue;
                                    actualWeight += item.weightValue;
                                    packed++;
                                }
                            }
                        }
                        if (packed > 0 && (actualCost > maximumCost || (actualCost == maximumCost && actualWeight < maxWeight))) {
                            maximumCost = actualCost;
                            maxWeight = actualWeight;
                            cPack = pack;
                            cPacked = packed;
                        }
                    }
                }
                if (cPacked > 0) {
                    final StringBuilder sb = new StringBuilder(cPacked * 2);
                    int j = 0;
                    for (int i = 1; i < 15; i++) {
                        if (cPack[i] == 1) {
                            sb.append(i);
                            j++;
                            if (j < cPacked) {
                                sb.append(comma);
                            } else {
                                break;
                            }
                        }
                    }
                    System.out.println(sb.toString());
                } else {
                    System.out.println(minus);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    final class Item implements Comparable<Item> {
        int indexValue;
        float weightValue;
        int costValue;

        @Override
        public int compareTo(final Item o) {
            if (costValue == o.costValue) {
                return weightValue > o.weightValue ? 1 : -1;
            } else {
                return costValue < o.costValue ? 1 : -1;
            }
        }
		@Override
        public String toString() {
            return "Item{" +
                    "index=" + indexValue +
                    ", weight=" + weightValue +
                    ", cost=" + costValue +
                    '}';
        }
    }
    public static void main(String[] args) {

        Main pp = new Main();
        if (args.length != 1) {
            System.err.println("no file specified!!");
        } else {
            pp.calculate(args[0]);
        }
    }


}
