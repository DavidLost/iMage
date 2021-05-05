package org.apache.commons.lang3;

import java.util.Iterator;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {
		
	private static Iterator<Integer> randomNumbers;
	
	private int randomInt;
	private String randomString;
	private AbbreviateParameters params;
	
	@BeforeAll
	static void setUpBeforeClass() {
		randomNumbers = new Random().ints().map(Math::abs).iterator();
	}
	
	private static String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(1, 10000000);
	}

	class AbbreviateParameters {
		String str;
		int maxWidth;
		public AbbreviateParameters(String str, int maxWidth) {
			this.str = str;
			this.maxWidth = maxWidth;
		}
		public String generateErrorMessage() {
			return "Assertion failed! abbreviate(\"" + str + "\", " + maxWidth + ") didn't create the expected output.";
		}
	}

	@Test
	public void testAbbreviate_4() {
		params = new AbbreviateParameters("", 4);
		try {
			assertEquals(StringUtils.abbreviate(params.str, params.maxWidth), "");
		} catch (AssertionFailedError afe) {
			fail(params.generateErrorMessage());
		}
	}

	@Test
	public void testAbbreviate_abcdefg_6() {
		params = new AbbreviateParameters("abcdefg", 6);
		try {
			assertEquals(StringUtils.abbreviate(params.str, params.maxWidth), "abc...");
		} catch (AssertionFailedError afe) {
			fail(params.generateErrorMessage());
		}
	}

	@Test
	public void testAbbreviate_abcdefg_7() {
		params = new AbbreviateParameters("abcdefg", 7);
		try {
			assertEquals(StringUtils.abbreviate(params.str, params.maxWidth), "abcdefg");
		} catch (AssertionFailedError afe) {
			fail(params.generateErrorMessage());
		}
	}

	@Test
	public void testAbbreviate_abcdefg_8() {
		params = new AbbreviateParameters("abcdefg", 8);
		try {
			assertEquals(StringUtils.abbreviate(params.str, params.maxWidth), "abcdefg");
		} catch (AssertionFailedError afe) {
			fail(params.generateErrorMessage());
		}
	}

	@Test
	public void testAbbreviate_abcdefg_4() {
		params = new AbbreviateParameters("abcdefg", 4);
		try {
			assertEquals(StringUtils.abbreviate(params.str, params.maxWidth), "a...");
		} catch (AssertionFailedError afe) {
			fail(params.generateErrorMessage());
		}
	}
		
}