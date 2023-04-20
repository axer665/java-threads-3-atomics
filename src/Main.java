import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger palindrom = new AtomicInteger(0);
    static AtomicInteger same = new AtomicInteger(0);
    static AtomicInteger ascending = new AtomicInteger(0);

    static AtomicInteger three = new AtomicInteger(0);
    static AtomicInteger four = new AtomicInteger(0);
    static AtomicInteger five = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isPolendrome(text)) {
                    palindrom.incrementAndGet();
                    incrementNumbers(text.length());
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isSame(text)) {
                    same.incrementAndGet();
                    incrementNumbers(text.length());
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isAscending(text)) {
                    ascending.incrementAndGet();
                    incrementNumbers(text.length());
                }
            }
        });

        threads.add(thread1);
        threads.add(thread2);
        threads.add(thread3);

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // Лишняя работа =)
        System.out.println("Количество слов - полиндромов: " + palindrom + " шт");
        System.out.println("Количество слов из одинаковых символов: " + same + " шт");
        System.out.println("Количество слов из символов, идущих в алфавитном порядке: " + ascending + " шт");
        System.out.println();

        // Решение задачи
        System.out.println("Красивых слов с длиной 3: " + three + " шт");
        System.out.println("Красивых слов с длиной 4: " + four + " шт");
        System.out.println("Красивых слов с длиной 5: " + five + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    // проверка на полиндром
    public static boolean isPolendrome(String word) {
        int length = word.length();
        for (int i = 0; i < (length / 2); i++) {
            if (word.charAt(i) != word.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
    }

    // проверка на строку из одинаковых символов
    public static boolean isSame(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        char symbol = word.charAt(0);
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) != symbol) {
                return false;
            }
        }
        return true;
    }

    // проверка на символы в алфавитном порядке
    public static boolean isAscending(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        for (int i = 1; i < word.length(); i++) {
            if ( word.charAt(i) < word.charAt(i-1) ) {
                return false;
            }
        }

        return true;
    }

    // увеличиваем нужных счетчиков
    public static void incrementNumbers(int number){
        if (number == 3) {
            three.incrementAndGet();
        } else if (number == 4) {
            four.incrementAndGet();
        } else if (number == 5) {
            five.incrementAndGet();
        }
    }
}