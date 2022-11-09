import java.io.IOException;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Неверное количество аргументов");
            return;
        }
        InputTable table = null;
        try {
            table = InputTable.read(args[0]);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода. Видимо, файла нет");
            return;
        }

        Methods mathMethods = new Methods();

        System.out.println("\nСчитанные данные:\n" + table);

        System.out.println("\n\n" + String.join("",Collections.nCopies((table.getMaximumElementLength()/3*2) * table.getWidth() + 5, "▬")) + "\n\n");

        System.out.println("Методом северо-западного угла: "
                + mathMethods.calcSumThroughPotentialAndNorthwestCornerMethods(table) + " руб");

        System.out.println("\n\n" + String.join("",Collections.nCopies((table.getMaximumElementLength()/3*2) * table.getWidth() + 5, "▬")) + "\n\n");

        System.out.println("Методом минимальных стоимостей: "
                + mathMethods.calcSumThroughPotentialAndMinimalCostMethods(table) + " руб");
    }
}
