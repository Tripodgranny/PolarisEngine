package com.polaris.main.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.lwjgl.system.MemoryUtil;

public class Utils {
	
	private static Random rnd = new Random();
	private static DecimalFormat formater = new DecimalFormat("#,##0.00");
	
	public static String toDecimalFormat(double d) {
		return formater.format(d);
	}
	
	public static FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
		buffer.put(data).flip();
		return buffer;
	}
	
	public static IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
		buffer.put(data).flip();
		return buffer;
	}
	
	public static String loadResource(String filename) throws Exception {
		String result;
		try (InputStream in = Utils.class.getResourceAsStream(filename);
				Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
				result = scanner.useDelimiter("\\A").next();
		}
		return result;
	}
	
	public static List<String> readAllLines(String filename) {
		List<String> list = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(Class.forName(Utils.class.getName()).getResourceAsStream(filename)))) {
			String line;
			
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static float randomFloat(final float min, final float max) {
		float randomFloat = 0;
		
		int negativeChance = rnd.nextInt() + 99;
		negativeChance = negativeChance < 50 ? -1 : 1;
		randomFloat = rnd.nextFloat(max) + min * negativeChance;
		
		return randomFloat;
		
	}
	
	public static float randomFloat(final float value) {
		float randomFloat = 0;
		
		int negativeChance = rnd.nextInt() + 99;
		negativeChance = negativeChance < 50 ? -1 : 1;
		randomFloat = rnd.nextFloat(value) * negativeChance;
		
		return randomFloat;
		
	}

}
