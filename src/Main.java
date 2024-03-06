import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static final List<Thread> myThread = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        int keyMax = 0;
        int valueMax = 0;
        for (int i = 1; i <= 1000; i++) {
            Thread thread = new Thread(() -> {
                final int countR = calcR(generateRoute("RLRFR", 100));
                synchronized (sizeToFreq) {
                    if (sizeToFreq.isEmpty()) {
                        sizeToFreq.put(countR, 1);
                    } else {
                        if (sizeToFreq.containsKey(countR)) {
                            sizeToFreq.put(countR, sizeToFreq.get(countR) + 1);
                        } else sizeToFreq.putIfAbsent(countR, 1);
                    }
                }
            });
            myThread.add(thread);
            thread.start();
        }
        for (Thread thread : myThread) {
            thread.join();
        }
        for (Map.Entry<Integer, Integer> key1 : sizeToFreq.entrySet()) {
            if (key1.getValue() > valueMax) {
                valueMax = key1.getValue();
                keyMax = key1.getKey();
            }
        }
        System.out.println("Самое частое количество повторений " + keyMax + " встретилось (" + valueMax + " раз)");
        for (Map.Entry<Integer, Integer> key1 : sizeToFreq.entrySet()) {
            System.out.println("- " + key1.getKey() + " (" + key1.getValue() + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static Integer calcR(String str) {
        int countR = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'R') {
                countR++;
            }
        }
        return countR;
    }
}