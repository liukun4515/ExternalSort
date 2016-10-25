package com.thu.bigdata.algorithm.externalsort;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MergeSortTest {

	private String left = "left";
	private String mid = "mid";
	private String right = "right";
	private File leftFile;
	private File midFile;
	private File rightFile;
	private File outFile;
	private String testout = left + mid + right;
	private long[] leftSe = { 20, 43, 39, 44, 533, 677, 2 };
	private long[] rightSe = { 34, 54, 12, 576, 678, 78, 54 };
	private long[] midSe = { 4, -2, 33, 12, 100, 8, 9 };
	private long[] allSe;

	@Before
	public void setUp() throws Exception {
		leftFile = new File(left);
		midFile = new File(mid);
		rightFile = new File(right);
		outFile = new File(testout);
		if (leftFile.exists()) {
			leftFile.delete();
		}
		if (midFile.exists()) {
			midFile.delete();
		}
		if (rightFile.exists()) {
			rightFile.delete();
		}
		if (outFile.exists()) {
			outFile.delete();
		}
		allSe = new long[leftSe.length + rightSe.length + midSe.length];
		int i;
		for (i = 0; i < leftSe.length; i++) {
			allSe[i] = leftSe[i];
		}
		int j;
		for (j = 0; j < rightSe.length; j++) {
			allSe[i + j] = rightSe[j];
		}
		int m;
		for (m = 0; m < midSe.length; m++) {
			allSe[i + j + m] = midSe[m];
		}
		Arrays.sort(leftSe);
		Arrays.sort(rightSe);
		Arrays.sort(midSe);
		Arrays.sort(allSe);
		// QuickSort.quickSort(leftSe, 0, leftSe.length - 1);
		// QuickSort.quickSort(rightSe, 0, rightSe.length - 1);
		// QuickSort.quickSort(midSe, 0, midSe.length - 1);
		// QuickSort.quickSort(allSe, 0, allSe.length - 1);
	}

	@After
	public void tearDown() throws Exception {
		leftFile.delete();
		rightFile.delete();
		midFile.delete();
		outFile.delete();
	}

	@Test
	public void test() throws IOException, ExternalSortException {
		DataOutputStream leftoutput = new DataOutputStream(new FileOutputStream(leftFile));
		DataOutputStream midoutput = new DataOutputStream(new FileOutputStream(midFile));
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

		for (int i = 0; i < midSe.length; i++) {
			midoutput.writeLong(midSe[i]);
		}
		midoutput.flush();
		midoutput.close();

		String[] inputFiles = new String[3];
		inputFiles[0] = left;
		inputFiles[1] = mid;
		inputFiles[2] = right;
		String result = MergeSort.multiWayInMergeSort(inputFiles, 0, inputFiles.length - 1);
		assertEquals(testout, result);
		long[] resultSe = new long[(int) (outFile.length() / Constent.LONG_SIZE)];
		DataInputStream inputStream = new DataInputStream(new FileInputStream(outFile));
		for (int i = 0; i < resultSe.length; i++) {
			resultSe[i] = inputStream.readLong();
		}
		inputStream.close();
		for (int i = 0; i < allSe.length; i++) {
			System.out.println("The all is " + allSe[i] + "The merge is " + resultSe[i] + "The bool is "
					+ (allSe[i] == resultSe[i]));
		}
		assertArrayEquals(allSe, resultSe);
	}
}
