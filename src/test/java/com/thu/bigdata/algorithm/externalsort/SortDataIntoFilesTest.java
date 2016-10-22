package com.thu.bigdata.algorithm.externalsort;

import static org.junit.Assert.assertEquals;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author liukun
 *
 */
public class SortDataIntoFilesTest {

	private String filename = "test.data";
	private DataInputStream inputstream = null;
	private int testDatasize = 128;// the testd.data file is 128 byte and 16
									// long
	private int longSize = 8;
	private int count = 0;
	private File file;
	private String testFile = "test";

	@Before
	public void setUp() throws Exception {

		DataProducer.produceData((long) 16, 4, filename);

		file = new File(filename);
		FileInputStream fileInputStream = new FileInputStream(file);
		inputstream = new DataInputStream(fileInputStream);
	}

	@After
	public void tearDown() throws Exception {
		file.delete();
	}

	@Test
	public void test() throws IOException, ExternalSortException {
		assertEquals(testDatasize, file.length());
		long[] testSequence = new long[testDatasize / longSize];
		for (int i = 0; i < testSequence.length; i++) {
			testSequence[i] = inputstream.readLong();
		}
		inputstream.close();
		System.out.println(testSequence);
		SortDataIntoFiles.sortData(filename, testDatasize, testFile);
		File outFile = new File(testFile + count);
		assertEquals(true, outFile.exists());
		inputstream = new DataInputStream(new FileInputStream(outFile));
		long[] expectSequence = new long[testDatasize / longSize];
		for (int i = 0; i < expectSequence.length; i++) {
			expectSequence[i] = inputstream.readLong();
		}
		QuickSort.quickSort(testSequence, 0, testSequence.length - 1);
		for (int i = 0; i < expectSequence.length; i++) {
			assertEquals(expectSequence[i], testSequence[i]);
		}
		inputstream.close();
		outFile.delete();
	}

}
