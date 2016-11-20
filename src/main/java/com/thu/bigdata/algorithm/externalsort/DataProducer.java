package com.thu.bigdata.algorithm.externalsort;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataProducer {

	private static long currentValue;
	private static long a = 3L;
	private static long c = 5L;
	private static long m = 1125899906842679L;
//	private static long beginValue = 2016213605L;
	private static long beginValue = 2016310569L;

	/**
	 * @param numOfData,
	 *            the amount of the data which want to produce
	 * @param numOfFlush,
	 *            the minimum of flush count
	 * @param filename
	 * @throws IOException
	 */
	public static void produceData(long numOfData, long numOfFlush, String filename) throws IOException {
		File file = new File(filename);
		if (file.exists()) {
			System.out.println(
					"The file is exist." + "The name is " + file.getName() + ", The path is " + file.getPath());
			System.exit(0);
		}
		FileOutputStream out = new FileOutputStream(file);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
		DataOutputStream outputStream = new DataOutputStream(bufferedOutputStream);
		long val;
		val = begin();
		outputStream.writeLong(val);
		for (long i = 2; i <= numOfData; i++) {
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
		currentValue = beginValue;
		return currentValue;
	}

	public static void main(String[] args) throws Exception {
		// 100MBflush一次
		// splitnum is 100*2e17
		// 16位s
		// 2e32
		long size = (long) Math.pow(2, 31);
		long flushsize = (int) Math.pow(2, 28);
		produceData((long) size, flushsize, Constent.INPUT_FILE_NAME_16G);
	}

}
