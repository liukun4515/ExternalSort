package com.thu.bigdata.algorithm.externalsort;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author liukun
 *
 */
public class QuickSortTest {

	private long[] randomSequence = { 2, 0, 43, -10, 88, 432432, 84, 55, 33 };
	private long[] sortSequence = { -10, 0, 2, 33, 43, 55, 84, 88, 432432 };

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		QuickSort.quickSort(randomSequence, 0, randomSequence.length - 1);
		assertArrayEquals(sortSequence, randomSequence);
	}

}
