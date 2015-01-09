package fr.inria.diverse.noveltytesting.normalization;

import javax.print.DocFlavor.STRING;

/**
 * @author mboussaa
 *
 */
public class NormalizationImpl implements Normalization {
	private int max=100;
	private int min=0;
	
	@Override
	public double normalizeInteger(int val) {

		return ((double)((double)val-((double)Integer.MIN_VALUE))/((double)((double)Integer.MAX_VALUE)-((double)Integer.MIN_VALUE)))*max;
	}

	@Override
	public double normalizeFloat(float val) {

		return (((val-Float.MIN_VALUE)*(max-min))/(Float.MAX_VALUE-Float.MIN_VALUE))+min;
	}

	@Override
	public double normalizeDouble(double val) {

		return ((val-(Double.MAX_VALUE*-1))/(Double.MAX_VALUE-(Double.MAX_VALUE*-1)))*max;
	}

	@Override
	public double normalizeLong(long val) {

		return (((val-Long.MIN_VALUE)*(max-min))/(Long.MAX_VALUE-Long.MIN_VALUE))+min;
	}

	@Override
	public double normalizeShort(short val) {

		return (((val-Short.MIN_VALUE)*(max-min))/(Short.MAX_VALUE-Short.MIN_VALUE))+min;
	}

	@Override
	public double normalizeByte(byte val) {

		return (((val-Byte.MIN_VALUE)*(max-min))/(Byte.MAX_VALUE-Byte.MIN_VALUE))+min;
	}

	@Override
	public double normalizeChar(int val) {
		int maxChar= 249;
		int minChar= 33;
		return (((val-minChar)*(max-min))/(maxChar-minChar))+min;
	}

	@Override
	public double normalizeString(int val) {
		//TODO
		return 0;
	}


}
