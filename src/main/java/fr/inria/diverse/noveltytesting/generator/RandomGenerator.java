package fr.inria.diverse.noveltytesting.generator;

import fr.inria.diverse.noveltytesting.model.Parameter;

import java.util.Random;

/**
 * Contains methods that ensure the generation of random values corresponding to
 * primitive types
 * 
 * Created by leiko on 16/10/14.
 */

public class RandomGenerator extends AbstractGenerator {

	protected static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz"
			+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
			+ "&é\"'(-è_çà)=ù*^,;:!?./§~#{[|`\\^@]}]+*²<>%µ£¨";

	@Override
	protected int genInteger(Parameter p) {
		return new Random().nextInt();
	}

	@Override
	protected long genLong(Parameter p) {
		return new Random().nextLong();
	}

    @Override
    protected double genDouble(Parameter p) {
        return (2*(new Random().nextDouble())-1)*Double.MAX_VALUE;
    }
 
	@Override
	protected float genFloat(Parameter p) {
		return (2*(new Random().nextFloat())-1)*Float.MAX_VALUE;
	}

	@Override
	protected char genCharacter(Parameter p) {
		return CHARACTERS.charAt(new Random().nextInt(CHARACTERS.length()));
	}

	@Override
	protected short genShort(Parameter p) {
		return (short) new Random().nextInt(Short.MAX_VALUE + 1);
	}

	@Override
	protected byte genByte(Parameter p) {
		byte[] bytes = new byte[1];
		new Random().nextBytes(bytes);
		return bytes[0];
	}

	@Override
	protected boolean genBoolean(Parameter p) {
		return new Random().nextBoolean();
	}

	@Override
	protected String genString(Parameter p) {
		int strLength = new Random().nextInt(10) + 1; // this is really
														// arbitrary
		StringBuilder str = new StringBuilder();
		while (strLength > 0) {
			str.append(genCharacter(p));
			strLength--;
		}
		return str.toString();
	}
}
