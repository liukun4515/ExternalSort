package com.thu.bigdata.algorithm.externalsort;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat.Encoding;

public class SortDataIntoFiles {

	private static int count = 0;
	private static int perLong = 8;

	public static void sortData(String inputFile, long sizeOfPerFile, String splitName)
			throws ExternalSortException, IOException {
		if (sizeOfPerFile % perLong != 0) {
			throw new ExternalSortException("The size of per file is not the 8ex");
		}
		File file = new File(inputFile);
		if (!file.exists()) {
			System.out.println("The input file is not exist: " + file.getPath());
			System.exit(0);
		}
		long lengthFile = file.length();
		int numOfFile = 0;
		if (lengthFile % sizeOfPerFile == 0) {
			numOfFile = (int) (lengthFile / sizeOfPerFile);
		} else {
			numOfFile = (int) (lengthFile / sizeOfPerFile + 1);
		}
		System.out.println("The file length is " + lengthFile);
		FileInputStream fileInputStream = new FileInputStream(file);
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(fileInputStream));
		ArrayList<Long> randomlist = new ArrayList<>();
		long temp = 0;
		int numCountOfPerFile = (int) (sizeOfPerFile / perLong);
		// produce split1
		for (; count < numOfFile; count++) {
			randomlist.clear();
			for (int i = 0; i < numCountOfPerFile; i++) {
				try {
					temp = dataInputStream.readLong();
					randomlist.add(temp);
				} catch (IOException e) {
					System.out.println("The numCountOfPerFile is " + numCountOfPerFile + ", the i is " + i);
					System.out.println("The numOfFile is " + numOfFile + ", the count is " + count);
					count = numOfFile;
					i = numCountOfPerFile;
					System.out.println("Finally: " + count);
				} finally {

				}
			}
			long[] randomSequence = new long[randomlist.size()];
			int i = 0;
			for (long l : randomlist) {
				randomSequence[i] = l;
				i++;
			}
			System.out.println("The randomSequence size is " + i);
			// QuickSort.quickSort(randomSequence, 0, randomSequence.length -
			// 1);
			Arrays.sort(randomSequence);
			File outputFile = new File(splitName + count);
			if (outputFile.exists()) {
				System.out.println("The file is exist " + outputFile.getName() + ", and delete it");
				outputFile.delete();
			}
			DataOutputStream outputStream = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(outputFile)));
			for (long l : randomSequence) {
				outputStream.writeLong(l);
			}
			outputStream.flush();
			outputStream.close();
		}
		dataInputStream.close();
	}

	public static void main(String[] args) throws ExternalSortException, IOException {
		long begin, end;
		begin = System.currentTimeMillis();
		sortData(Constent.INPUT_FILE_NAME_16G2, Constent.GB_1, "split");
		end = System.currentTimeMillis();
		System.out.println(end - begin);
		begin = System.currentTimeMillis();
		MergeSort.main(null);
		end = System.currentTimeMillis();
		System.out.println(end - begin);
		begin = System.currentTimeMillis();
		HashProducer.main(null);
		end = System.currentTimeMillis();
		System.out.println(end - begin);
	}
}
