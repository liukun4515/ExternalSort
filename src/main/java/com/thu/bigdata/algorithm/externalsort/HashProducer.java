package com.thu.bigdata.algorithm.externalsort;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class HashProducer {

	private static long a = 3L;
	private static long m = 1125899906842679L;

	public static void getHashValue(String inFile, String outFile) throws ExternalSortException, IOException {
		File inputFile = new File(inFile);
		File outputFile = new File(outFile);
		if (!inputFile.exists()) {
			System.out.println("The input file is not exist");
			throw new ExternalSortException("The input file is not exist");
		}
		if (outputFile.exists()) {
			System.out.println("Delete the output file");
		}
		long inputFileLength = inputFile.length();
		if (inputFileLength % Constent.LONG_SIZE != 0) {
			throw new ExternalSortException("The input file length is error");
		}
		long inputSize = inputFileLength / Constent.LONG_SIZE;
		long currentValue;
		long nextValue;
		long currentHashValue;
		long nextHashValue;
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
		if (inputSize > 0) {
			currentValue = dataInputStream.readLong();
			currentHashValue = currentValue;
			for (long i = 1; i < inputSize; i++) {
				nextValue = dataInputStream.readLong();
				if (nextValue >= currentValue) {
					nextHashValue = (a * currentHashValue + nextValue) % m;
					currentValue = nextValue;
					currentHashValue = nextHashValue;
					// System.out.println(i);
				} else {
					System.out.println("The next is " + nextValue + ", the current is " + currentValue);
					throw new ExternalSortException("The sorting is error in the " + i);
				}
			}
			System.out.println("Close the data input stream");
			dataInputStream.close();
			FileWriter writer = new FileWriter(outputFile);
			writer.write(String.valueOf(currentHashValue));
			System.out.println("The result hash value is " + currentHashValue);
			writer.close();
		} else {
			dataInputStream.close();
			throw new ExternalSortException("The input file is empty");
		}
	}

	public static void main(String[] args) throws ExternalSortException, IOException {
		System.out.println("To get the hash code");
		getHashValue(
				"split0split1split2split3split4split5split6split7split8split9split10split11split12split13split14split15",
				"HashResult2.data");
	}

}
