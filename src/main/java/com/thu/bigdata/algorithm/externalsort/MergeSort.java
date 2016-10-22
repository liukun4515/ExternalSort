package com.thu.bigdata.algorithm.externalsort;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MergeSort {

	/**
	 * Use the devide-and-conquer algorithm
	 * 
	 * @param inputFiles
	 * @param begin
	 * @param end
	 * @throws ExternalSortException
	 * @throws IOException
	 */
	public static String multiWayInMergeSort(String[] inputFiles, int begin, int end)
			throws ExternalSortException, IOException {

		String result;
		if (begin == end) {
			return inputFiles[begin];
		} else {
			int mid = (begin + end) / 2;
			String left = multiWayInMergeSort(inputFiles, begin, mid);
			String right = multiWayInMergeSort(inputFiles, mid + 1, end);
			result = mergeSort(left, right);
		}
		return result;
	}

	/**
	 * @param left
	 * @param right
	 * @return
	 * @throws ExternalSortException
	 * @throws IOException
	 */
	public static String mergeSort(String left, String right) throws ExternalSortException, IOException {

		System.out.println("Merge file :" + left + "|" + right);

		File leftFile = new File(left);
		File rightFile = new File(right);
		long leftSize = leftFile.length() / Constent.LONG_SIZE;
		long rightSize = rightFile.length() / Constent.LONG_SIZE;
		long leftValue;
		long rightValue;
		String out = left + right;
		File outFile = new File(out);
		if ((!leftFile.exists()) || (!rightFile.exists())) {
			System.out.println("The left file or the right file is not exist");
			throw new ExternalSortException("The left file or the right file is not exist");
		}
		if (outFile.exists()) {
			System.out.println("The out file is exist");
			throw new ExternalSortException("The out file is exist");
		}

		DataInputStream leftstream = new DataInputStream(new FileInputStream(left));
		DataInputStream rightstream = new DataInputStream(new FileInputStream(right));
		DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(outFile));
		leftValue = leftstream.readLong();
		rightValue = rightstream.readLong();
		while (leftSize > 0 && rightSize > 0) {
			if (leftValue < rightValue) {
				outputStream.writeLong(leftValue);
				leftSize--;
				if (leftSize > 0) {
					leftValue = leftstream.readLong();
				}
			} else {
				outputStream.writeLong(rightValue);
				rightSize--;
				if (rightSize > 0) {
					rightValue = rightstream.readLong();
				}
			}
		}
		while (leftSize > 0) {
			outputStream.writeLong(leftValue);
			leftSize--;
		}
		while (rightSize > 0) {
			outputStream.writeLong(rightValue);
			rightSize--;
		}
		outputStream.flush();
		outputStream.close();
		leftstream.close();
		rightstream.close();
		return out;
	}

	public static void main(String[] args) throws ExternalSortException, IOException {
		String[] inputFiles = new String[16];
		int count = 16;
		String name = "split";
		for (int i = 0; i < count; i++) {
			inputFiles[i] = name + i;
			System.out.println(inputFiles[i]);
		}
		String result = multiWayInMergeSort(inputFiles, 0, inputFiles.length - 1);
		System.out.print("Successfull,!! The result is " + result);

	}
}
