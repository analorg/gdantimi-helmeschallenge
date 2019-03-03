package com.gdantimi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class AnagramApplication {

	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		String path = args[0];
		char[] word = args[1].toCharArray();

		String result = findWordInDictionary(word, path);

		long endTime = System.nanoTime();
		long executionTime = (endTime - startTime) / 1000;
		System.out.println(executionTime + result);
	}

	protected static String findWordInDictionary(char[] word, String path) throws IOException {
		StringBuilder result = new StringBuilder();
		Arrays.sort(word);
		int wordLength = word.length;
		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
			String dictionaryWord;
			while ((dictionaryWord = br.readLine()) != null) {
				if (dictionaryWord.length() == wordLength) {
					char[] sortedDictionaryWord = dictionaryWord.toLowerCase().toCharArray().clone();
					Arrays.sort(sortedDictionaryWord);
					if (Arrays.equals(sortedDictionaryWord, word)) {
						result.append(",").append(dictionaryWord);
					}
				}
			}
		}
		return result.toString();
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

}
