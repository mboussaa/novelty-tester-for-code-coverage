package fr.inria.diverse.noveltytesting.normalization;

/**
 * @author mboussaa
 *
 */

public interface Normalization {

	double normalizeInteger(int val);
	double normalizeFloat(float val);
	double normalizeDouble(double val);
	double normalizeLong(long val);
	double normalizeShort(short val);
	double normalizeByte(byte val);
	double normalizeChar(int val);
	double normalizeString(int val);
}
