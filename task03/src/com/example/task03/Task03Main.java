package com.example.task03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class Task03Main {

    public static void main(String[] args) throws IOException {
        List<Set<String>> anagrams = findAnagrams(
                new FileInputStream("task03/resources/singular.txt"),
                Charset.forName("windows-1251")
        );

        for (Set<String> anagram : anagrams) {
            System.out.println(anagram);
        }
    }

    public static List<Set<String>> findAnagrams(InputStream inputStream, Charset charset) {
        Map<String, Set<String>> groups = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim().toLowerCase(Locale.ROOT);

                // Пропускаем слова, не соответствующие правилам
                if (word.length() < 3) continue;
                if (!word.matches("[а-яё]+")) continue;

                // Ключ для анаграммы — отсортированные буквы слова
                char[] chars = word.toCharArray();
                Arrays.sort(chars);
                String key = new String(chars);

                groups.computeIfAbsent(key, k -> new TreeSet<>()).add(word);
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла", e);
        }

        // Оставляем только наборы, где 2 и более слов
        List<Set<String>> result = groups.values().stream()
                .filter(set -> set.size() >= 2)
                .map(set -> new TreeSet<>(set)) // сортировка слов внутри
                .sorted(Comparator.comparing(set -> set.iterator().next())) // сортировка по первому слову
                .collect(Collectors.toList());

        return result;
    }
}
