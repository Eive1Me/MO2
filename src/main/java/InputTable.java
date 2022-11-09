import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class InputTable {
    private final ArrayList<ArrayList<String>> content = new ArrayList<>();

    public InputTable(int width, int height) {
        for (int y = 0; y < height; ++y) {
            ArrayList<String> line = new ArrayList<>();
            for (int x = 0; x < width; ++x) {
                line.add("");
            }
            content.add(line);
        }
    }

    public InputTable(InputTable other) {
        for (int y = 0; y < other.getHeight(); ++y) {
            ArrayList<String> line = new ArrayList<>();
            for (int x = 0; x < other.getWidth(); ++x) {
                line.add(other.get(x, y));
            }
            content.add(line);
        }
    }

    public static InputTable read(String path) throws IOException {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CSVReader csvReader = new CSVReader(reader);
        String[] nextRecord;
        InputTable table = new InputTable(0, 0);
        while ((nextRecord = csvReader.readNext()) != null) {
            table.addRow();
            for (int i = 0; i < nextRecord.length; ++i) {
                if (i >= table.getWidth()) {
                    table.addColumn();
                }
                table.set(i, table.getHeight() - 1, nextRecord[i]);
            }
            ArrayList<String> line = new ArrayList<>();
            for (String recordItem : nextRecord) {
                line.add(recordItem);
            }
        }
        return table;
    }

    public void fill (int x1, int y1, int x2, int y2, String value) {
        if (x1 > x2) {
            int tmp = x2;
            x2 = x1;
            x1 = tmp;
        }
        if (y1 > y2) {
            int tmp = y2;
            y2 = y1;
            y1 = tmp;
        }
        x1 = Math.max(0, x1);
        y1 = Math.max(0, y1);
        x2 = Math.min(getWidth(), x2);
        y2 = Math.min(getHeight(), y2);
        for (int x = x1; x < x2; ++x) {
            for (int y = y1; y < y2; ++y) {
                set(x, y, value);
            }
        }
    }

    public void addRow() {
        ArrayList<String> lineToAdd = new ArrayList<>();
        for (int i = 0; i < getWidth(); ++i) {
            lineToAdd.add("");
        }
        content.add(lineToAdd);
    }

    public void addColumn() {
        for (int i = 0; i <getHeight(); ++i) {
            content.get(i).add("");
        }
    }

    public int getWidth() {
        if (getHeight() > 0) {
            return content.get(0).size();
        }
        return 0;
    }

    public int getHeight() {
        return content.size();
    }

    public String get(int x, int y) {
        return content.get(y).get(x);
    }

    public void set(int x, int y, String value) {
        content.get(y).set(x, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputTable table = (InputTable) o;
        return Objects.equals(content, table.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();

        int maxItemLength = getMaximumElementLength();
        String horizontalLine = String.join("", Collections.nCopies((maxItemLength + 3) * getWidth() + 2, "—"));
        for (int y = 0; y < getHeight(); ++y) {
            answer.append(horizontalLine).append("\n");
            for (int x = 0; x < getWidth(); ++x) {
                String spaces = String.join("", Collections.nCopies(maxItemLength - get(x, y).length(), " "));
                answer.append("| ").append(spaces).append(get(x, y)).append(" ");
            }
            answer.append(" |\n");
        }
        answer.append(horizontalLine);

        return answer.toString();
    }

    public int getMaximumElementLength() {
        int maxLength = 0;
        for (int x = 0; x < getWidth(); ++x) {
            for (int y = 0; y < getHeight(); ++y) {
                if (get(x, y).length() > maxLength) {
                    maxLength = get(x, y).length();
                }
            }
        }
        return maxLength;
    }
}
