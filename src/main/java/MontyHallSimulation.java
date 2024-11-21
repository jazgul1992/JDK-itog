import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Random;

public class MontyHallSimulation {
    private static final Random random = new Random();

    @Getter
    @AllArgsConstructor
    static class GameResult {
        private final boolean win; // Выигрыш при текущей стратегии
        private final boolean switchStrategy; // Использовалась ли стратегия смены
    }

    public static GameResult playGame(boolean switchChoice) {
        // Генерация дверей
        int carDoor = random.nextInt(3); // Дверь с машиной
        int chosenDoor = random.nextInt(3); // Дверь, выбранная участником

        // Ведущий открывает дверь с козой
        int openedDoor;
        do {
            openedDoor = random.nextInt(3);
        } while (openedDoor == carDoor || openedDoor == chosenDoor);

        // Участник делает выбор
        int finalChoice = switchChoice ? (3 - chosenDoor - openedDoor) : chosenDoor;

        return new GameResult(finalChoice == carDoor, switchChoice);
    }

    public static void main(String[] args) {
        int iterations = 1000;
        HashMap<Integer, GameResult> results = new HashMap<>();

        int winsWithSwitch = 0;
        int winsWithoutSwitch = 0;

        for (int i = 1; i <= iterations; i++) {
            boolean switchStrategy = i % 2 == 0; // Чередуем стратегию
            GameResult result = playGame(switchStrategy);
            results.put(i, result);

            if (result.isWin()) {
                if (result.isSwitchStrategy()) {
                    winsWithSwitch++;
                } else {
                    winsWithoutSwitch++;
                }
            }
        }

        // Вывод статистики
        System.out.println("Результаты:");
        System.out.println("Победы при смене выбора: " + winsWithSwitch);
        System.out.println("Победы без смены выбора: " + winsWithoutSwitch);

        double switchWinRate = (double) winsWithSwitch / (iterations / 2) * 100;
        double stayWinRate = (double) winsWithoutSwitch / (iterations / 2) * 100;

        System.out.printf("Процент побед при смене выбора: %.2f%%%n", switchWinRate);
        System.out.printf("Процент побед без смены выбора: %.2f%%%n", stayWinRate);
    }
}

