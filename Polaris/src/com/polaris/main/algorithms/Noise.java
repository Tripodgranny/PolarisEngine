package com.polaris.main.algorithms;

import java.util.Random;

public class Noise {
	
	private final static Random R = new Random();
	private final static int DEFAULT_SEED = 1000000000;
	
	private static long seed = R.nextInt(DEFAULT_SEED);
	
	// These values increase the randomized range of close by numbers/coordinates
	private static int xRandom = 496326;
	private static int yRandom = 325176;
	
	// REGULAR NOISE FOR A COORDINATE, returns float value (-1f to 1f)
	public static float getNoise(int x, int y) {
		R.setSeed(x * xRandom + y * yRandom + seed);
		float n = R.nextFloat() * 2f - 1f;
		return n;
	}
	
	// SMOOTHED NOISE - Calls getNoise on square grid of coordinates shown below
	// c.s.c          c = corner; s = side; C = center;
	// s.C.s
	// c.s.c
	public static float getSmoothNoise(int x, int y) {
		float corners = (getNoise(x-1, y-1) + getNoise(x+1, y-1) + getNoise(x-1, y+1) + getNoise(x-1, y+1)) /16f;
		float sides = (getNoise(x-1, y)+getNoise(x+1, y)+getNoise(x, y-1)+getNoise(x, y+1)) /8f;
		float center = getNoise(x, y) /4f;
		return corners + sides + center;
	}
	
	// INTERPOLATED NOISE - Calls getSmoothNoise on each vertex of plane
	public static float getInterpolatedNoise(float x, float y) {
		int intX = (int) x;
		int intY = (int) y;
		float fracX = x - intX;
		float fracY = y - intY;
		float v1 = getSmoothNoise(intX, intY);
		float v2 = getSmoothNoise(intX + 1, intY);
		float v3 = getSmoothNoise(intX, intY + 1);
		float v4 = getSmoothNoise(intX + 1, intY + 1);
		float i1 = interpolate(v1, v2, fracX);
		float i2 = interpolate(v3, v4, fracX);
		return interpolate(i1, i2, fracY);
	}
	
	// SET THE SEED FOR GENERATION
	public static void setSeed(int newSeed) {
		seed = R.nextInt(newSeed);
	}
	
	private static float interpolate(float a, float b, float blend) {
		double theta = blend * Math.PI;
		float f = (float) (1f - Math.cos(theta)) * 0.5f;
		return a * (1f - f) + b * f;
	}

}
