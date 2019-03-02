package com.gdantimi;

import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnagramApplication {

	public static void main(String[] args) throws IOException {
		System.out.println("Finding anagrams for word " + args[0]);
		long startTime = System.nanoTime();
		String path = args[0];
		char[] word = args[1].toCharArray();

		List<String> dictionary = loadDictionary(path);

		List<String> foundWords = findWordInDictionary(word, dictionary);

		long endTime = System.nanoTime();
		long executionTime = (endTime - startTime) / 1000;
		System.out.println(executionTime + "," + foundWords.stream().collect(joining(",")));
	}

	protected static int getNextIndex(int index, char[] lastPermutation) {
		char current = lastPermutation[index];
		int wordLength = lastPermutation.length;
		if (canIncreaseIndex(index, wordLength) && current == lastPermutation[index + 1]) {
			return getNextIndex(index + 1, lastPermutation);
		}

		if (!canIncreaseIndex(index, wordLength)) {
			return 0;
		}
		return index + 1;
	}

	private static boolean canIncreaseIndex(int index, int wordLength) {
		return index + 1 < wordLength;
	}

	protected static List<String> findWordInDictionary(char[] word, List<String> dictionary) {
		List<String> matches = new ArrayList<>();
		Arrays.sort(word);
		int wordLength = word.length;

		dictionary.forEach(dictionaryWord -> {
			if (dictionaryWord.length() == wordLength) {
				char[] sortedDictionaryWord = dictionaryWord.toLowerCase().toCharArray().clone();
				Arrays.parallelSort(sortedDictionaryWord);
				if (Arrays.equals(sortedDictionaryWord, word)) {
					matches.add(dictionaryWord);
				}
			}
		});
		return matches;
	}

	private static List<String> loadDictionary(String path) throws IOException {
		List<String> dictionary = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
			String line;
			while ((line = br.readLine()) != null) {
				dictionary.add(line);
			}
		}
		return dictionary;
	}
}
