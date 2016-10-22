package com.thu.bigdata.algorithm.externalsort;

import static org.junit.Assert.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MergeSortTest {

	private String left = "left";
	private String right = "right";
	private File leftFile;
	private File rightFile;
	private File outFile;
	private String testout = left + right;
	private long[] leftSe = { 20, 43, 39, 44, 533, 677, 2 };
	private long[] rightSe = { 34, 54, 12, 576, 678, 78, 54 };
	private long[] allSe;

	@Before
	public void setUp() throws Exception {
		leftFile = new File(left);
		rightFile = new File(right);
		outFile = new File(testout);
		if (leftFile.exists()) {
			leftFile.delete();
		}
		if (rightFile.exists()) {
			rightFile.delete();
		}
		if (outFile.exists()) {
			outFile.delete();
		}
		allSe = new long[leftSe.length + rightSe.length];
		int i;
		for (i = 0; i < leftSe.length; i++) {
			allSe[i] = leftSe[i];
		}
		int j;
		for (j = 0; j < rightSe.length; j++) {
			allSe[i + j] = rightSe[j];
		}
		QuickSort.quickSort(leftSe, 0, leftSe.length - 1);
		QuickSort.quickSort(rightSe, 0, rightSe.length - 1);
		QuickSort.quickSort(allSe, 0, allSe.length - 1);
	}

	@After
	public void tearDown() throws Exception {
		leftFile.delete();
		rightFile.delete();
		outFile.delete();
	}

	@Test
	public void test() throws IOException, ExternalSortException {
		DataOutputStream leftoutput = new DataOutputStream(new FileOutputStream(leftFile));
		DataOutputStream rightoutput = new DataOutputStream(new FileOutputStream(rightFile));
		for (int i = 0; i < leftSe.length; i++) {
			leftoutput.writeLong(leftSe[i]);
		}
		leftoutput.flush();
		leftoutput.close();
		for (int i = 0; i < rightSe.length; i++) {
			rightoutput.writeLong(rightSe[i]);
		}
		rightoutput.flush();
		rightoutput.close();

		String[] inputFiles = new String[2];
		inputFiles[0] = left;
		inputFiles[1] = right;
		String result = MergeSort.mergeSort(left, right);
		assertEquals(testout, result);
		long[] resultSe = new long[(int) (outFile.length() / Constent.LONG_SIZE)];
		DataInputStream inputStream = new DataInputStream(new FileInputStream(outFile));
		for (int i = 0; i < resultSe.length; i++) {
			resultSe[i] = inputStream.readLong();
		}
		inputStream.close();
		assertArrayEquals(allSe, resultSe);
	}
}
