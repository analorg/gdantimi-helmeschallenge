package com.gdantimi;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AnagramApplicationUnitTest {

	private static final String DICTIONARY_FILE = "lemmad.txt";
	private static final String WORD = "palace lap";

	@Test
	public void main_shouldProcessWithoutErrors() throws IOException {
		String path = getClass().getClassLoader().getResource(DICTIONARY_FILE).getPath();
		AnagramApplication.main(new String[] { path, WORD });
	}

	@Test
	public void findWordInDictionary_shouldAllFindAnagrams() {
		List<String> dictionary = Arrays.asList(
				"palace pal", "palace lap", "palace alp", "apace pall", "paella cap",
				"appeal lac", "appall ace", "appal lace", "papal lace"
		);
		List<String> wordInDictionary = AnagramApplication.findWordInDictionary(WORD.toCharArray(), dictionary);
		assertEquals(dictionary.size(), wordInDictionary.size());
	}

	@Test
	public void getNextIndex_shouldFindNextIndexToSwap_whenNextIndexStillInRange() {
		char[] lastPermutation = new char[] { 'a', 'b', 'c', 'c' };
		int nextIndex = AnagramApplication.getNextIndex(0, lastPermutation);

		assertEquals(1, nextIndex);
	}

	@Test
	public void getNextIndex_shouldStartOver_whenNextIndexOutOfRange() {
		char[] lastPermutation = new char[] { 'a', 'b', 'c', 'c' };
		int nextIndex = AnagramApplication.getNextIndex(2, lastPermutation);

		assertEquals(0, nextIndex);
	}
}