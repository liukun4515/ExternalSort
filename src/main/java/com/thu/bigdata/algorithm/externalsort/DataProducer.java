package com.thu.bigdata.algorithm.externalsort;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataProducer {

	private static long currentValue;
	private static int a = 3;
	private static int c = 5;
	private static long m = 1125899906842679L;
	private static long beginValue = 2016213605L;

	/**
	 * @param numOfData,
	 *            the amount of the data which want to produce
	 * @param numOfFlush,
	 *            the minimum of flush count
	 * @param filename
	 * @throws IOException
	 */
	public static void produceData(long numOfData, int numOfFlush, String filename) throws IOException {
		File file = new File(filename);
		if (file.exists()) {
			System.out.println(
					"The file is exist." + "The name is " + file.getName() + ", The path is " + file.getPath());
			System.exit(0);
		}
		FileOutputStream out = new FileOutputStream(file);
		DataOutputStream outputStream = new DataOutputStream(out);
		long val;
		val = begin();
		outputStream.writeLong(val);
		for (long i = 1; i < numOfData; i++) {
			val = nextData();
			outputStream.writeLong(val);
			if (i % numOfFlush == 0) {
				outputStream.flush();
				System.out.println("flush:" + i / numOfFlush + "i = " + i);
			}
		}
		outputStream.flush();
		outputStream.close();
		System.out.print("Produce data successfully");
	}

	public static long nextData() {
		currentValue = (currentValue * a + c) % m;
		return currentValue;
	}

	public static long begin() {
		return currentValue = beginValue;
	}

	public static void main(String[] args) throws Exception {
		// 100MBflush一次
		// splitnum is 100*2e17
		// 16位s
		// 2e32
		long size = (long) Math.pow(2, 4);
		int flushsize = (int) Math.pow(2, 2);
		// produceData((long) size, flushsize, "test.data");
	}

}
