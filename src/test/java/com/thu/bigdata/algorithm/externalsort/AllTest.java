package com.thu.bigdata.algorithm.externalsort;

import static org.junit.Assert.assertArrayEquals;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AllTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException, ExternalSortException {
//		fail("Not yet implemented");
		int step = 10;
		String filename = "testfile";
		String origin = "origin";
		long[] randomSe = new long[100];
		Random random = new Random();
		for (int i = 0; i < randomSe.length; i++) {
			randomSe[i] = random.nextLong();
//			System.out.println(randomSe[i]);
		}
		File file = new File(origin);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
		for (int i = 0; i < randomSe.length; i++) {
			dataOutputStream.writeLong(randomSe[i]);
		}
		dataOutputStream.flush();
		dataOutputStream.close();
		
		DataInputStream dataInputStream = new DataInputStream(new FileInputStream(origin));
		
		ArrayList<String> list = new ArrayList<>();
		long[] steps = new long[10];
		for (int i = 0; i < randomSe.length/step; i++) {
			for (int j = 0; j < step; j++) {
				steps[j] = dataInputStream.readLong();
			}
			Arrays.sort(steps);
			filename = filename+i;
			list.add(filename);
			File file2 = new File(filename);
			DataOutputStream dataOutputStream2 = new DataOutputStream(new FileOutputStream(file2));
			for (int j = 0; j < steps.length; j++) {
				dataOutputStream2.writeLong(steps[j]);
			}
			dataOutputStream2.close();
		}
		dataInputStream.close();
		String[] files = new String[list.size()];
		
		String name =  MergeSort.multiWayInMergeSort(list.toArray(files), 0,files.length-1);
		System.out.println(name);
		long[] sortSe = new long[100];
		File file3 = new File(name);
		DataInputStream inputStream = new DataInputStream(new FileInputStream(file3));
		for (int i = 0; i < sortSe.length; i++) {
			sortSe[i] = inputStream.readLong();
		}
		inputStream.close();
		Arrays.sort(randomSe);
		for (int i = 0; i < sortSe.length; i++) {
			System.out.println("The id is "+i+"The random is "+randomSe[i]+" The sort is "+sortSe[i]+" the bool is "+(randomSe[i]==sortSe[i]));
		}
		
	}

}
