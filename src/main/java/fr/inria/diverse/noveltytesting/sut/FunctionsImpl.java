package fr.inria.diverse.noveltytesting.sut;
import org.apache.commons.math3.util.FastMath.ExpFracTable;
import org.apache.commons.math3.util.FastMath.ExpIntTable;

import haxe.lang.EmptyObject;
import haxe.root.*;

@SuppressWarnings(value={"rawtypes", "unchecked"})
public  class FunctionsImpl extends haxe.lang.HxObject implements Functions
{ public static final double PI = 105414357.0 / 33554432.0 + 1.984187159361080883e-9;

/** Napier's constant e, base of the natural logarithm. */
public static final double E = 2850325.0 / 1048576.0 + 8.254840070411028747e-8;

/** Index of exp(0) in the array of integer exponentials. */
static final int EXP_INT_TABLE_MAX_INDEX = 750;
/** Length of the array of integer exponentials. */
static final int EXP_INT_TABLE_LEN = EXP_INT_TABLE_MAX_INDEX * 2;
/** Logarithm table length. */
static final int LN_MANT_LEN = 1024;
/** Exponential fractions table length. */
static final int EXP_FRAC_TABLE_LEN = 1025; // 0, 1/1024, ... 1024/1024

/** StrictMath.log(Double.MAX_VALUE): {@value} */
public static final double LOG_MAX_VALUE = StrictMath.log(Double.MAX_VALUE);

/** Indicator for tables initialization.
 * <p>
 * This compile-time constant should be set to true only if one explicitly
 * wants to compute the tables at class loading time instead of using the
 * already computed ones provided as literal arrays below.
 * </p>
 */
public static final boolean RECOMPUTE_TABLES_AT_RUNTIME = false;

/** log(2) (high bits). */
public static final double LN_2_A = 0.693147063255310059;

/** log(2) (low bits). */
public static final double LN_2_B = 1.17304635250823482e-7;

/** Coefficients for log, when input 0.99 < x < 1.01. */
public static final double LN_QUICK_COEF[][] = {
    {1.0, 5.669184079525E-24},
    {-0.25, -0.25},
    {0.3333333134651184, 1.986821492305628E-8},
    {-0.25, -6.663542893624021E-14},
    {0.19999998807907104, 1.1921056801463227E-8},
    {-0.1666666567325592, -7.800414592973399E-9},
    {0.1428571343421936, 5.650007086920087E-9},
    {-0.12502530217170715, -7.44321345601866E-11},
    {0.11113807559013367, 9.219544613762692E-9},
};

/** Coefficients for log in the range of 1.0 < x < 1.0 + 2^-10. */
public static final double LN_HI_PREC_COEF[][] = {
    {1.0, -6.032174644509064E-23},
    {-0.25, -0.25},
    {0.3333333134651184, 1.9868161777724352E-8},
    {-0.2499999701976776, -2.957007209750105E-8},
    {0.19999954104423523, 1.5830993332061267E-10},
    {-0.16624879837036133, -2.6033824355191673E-8}
};

/** Sine, Cosine, Tangent tables are for 0, 1/8, 2/8, ... 13/8 = PI/2 approx. */
public static final int SINE_TABLE_LEN = 14;

/** Sine table (high bits). */
public static final double SINE_TABLE_A[] =
    {
    +0.0d,
    +0.1246747374534607d,
    +0.24740394949913025d,
    +0.366272509098053d,
    +0.4794255495071411d,
    +0.5850973129272461d,
    +0.6816387176513672d,
    +0.7675435543060303d,
    +0.8414709568023682d,
    +0.902267575263977d,
    +0.9489846229553223d,
    +0.9808930158615112d,
    +0.9974949359893799d,
    +0.9985313415527344d,
};

/** Sine table (low bits). */
public static final double SINE_TABLE_B[] =
    {
    +0.0d,
    -4.068233003401932E-9d,
    +9.755392680573412E-9d,
    +1.9987994582857286E-8d,
    -1.0902938113007961E-8d,
    -3.9986783938944604E-8d,
    +4.23719669792332E-8d,
    -5.207000323380292E-8d,
    +2.800552834259E-8d,
    +1.883511811213715E-8d,
    -3.5997360512765566E-9d,
    +4.116164446561962E-8d,
    +5.0614674548127384E-8d,
    -1.0129027912496858E-9d,
};

/** Cosine table (high bits). */
public static final double COSINE_TABLE_A[] =
    {
    +1.0d,
    +0.9921976327896118d,
    +0.9689123630523682d,
    +0.9305076599121094d,
    +0.8775825500488281d,
    +0.8109631538391113d,
    +0.7316888570785522d,
    +0.6409968137741089d,
    +0.5403022766113281d,
    +0.4311765432357788d,
    +0.3153223395347595d,
    +0.19454771280288696d,
    +0.07073719799518585d,
    -0.05417713522911072d,
};

/** Cosine table (low bits). */
public static final double COSINE_TABLE_B[] =
    {
    +0.0d,
    +3.4439717236742845E-8d,
    +5.865827662008209E-8d,
    -3.7999795083850525E-8d,
    +1.184154459111628E-8d,
    -3.43338934259355E-8d,
    +1.1795268640216787E-8d,
    +4.438921624363781E-8d,
    +2.925681159240093E-8d,
    -2.6437112632041807E-8d,
    +2.2860509143963117E-8d,
    -4.813899778443457E-9d,
    +3.6725170580355583E-9d,
    +2.0217439756338078E-10d,
};


/** Tangent table, used by atan() (high bits). */
public static final double TANGENT_TABLE_A[] =
    {
    +0.0d,
    +0.1256551444530487d,
    +0.25534194707870483d,
    +0.3936265707015991d,
    +0.5463024377822876d,
    +0.7214844226837158d,
    +0.9315965175628662d,
    +1.1974215507507324d,
    +1.5574076175689697d,
    +2.092571258544922d,
    +3.0095696449279785d,
    +5.041914939880371d,
    +14.101419448852539d,
    -18.430862426757812d,
};

/** Tangent table, used by atan() (low bits). */
public static final double TANGENT_TABLE_B[] =
    {
    +0.0d,
    -7.877917738262007E-9d,
    -2.5857668567479893E-8d,
    +5.2240336371356666E-9d,
    +5.206150291559893E-8d,
    +1.8307188599677033E-8d,
    -5.7618793749770706E-8d,
    +7.848361555046424E-8d,
    +1.0708593250394448E-7d,
    +1.7827257129423813E-8d,
    +2.893485277253286E-8d,
    +3.1660099222737955E-7d,
    +4.983191803254889E-7d,
    -3.356118100840571E-7d,
};

/** Bits of 1/(2*pi), need for reducePayneHanek(). */
public static final long RECIP_2PI[] = new long[] {
    (0x28be60dbL << 32) | 0x9391054aL,
    (0x7f09d5f4L << 32) | 0x7d4d3770L,
    (0x36d8a566L << 32) | 0x4f10e410L,
    (0x7f9458eaL << 32) | 0xf7aef158L,
    (0x6dc91b8eL << 32) | 0x909374b8L,
    (0x01924bbaL << 32) | 0x82746487L,
    (0x3f877ac7L << 32) | 0x2c4a69cfL,
    (0xba208d7dL << 32) | 0x4baed121L,
    (0x3a671c09L << 32) | 0xad17df90L,
    (0x4e64758eL << 32) | 0x60d4ce7dL,
    (0x272117e2L << 32) | 0xef7e4a0eL,
    (0xc7fe25ffL << 32) | 0xf7816603L,
    (0xfbcbc462L << 32) | 0xd6829b47L,
    (0xdb4d9fb3L << 32) | 0xc9f2c26dL,
    (0xd3d18fd9L << 32) | 0xa797fa8bL,
    (0x5d49eeb1L << 32) | 0xfaf97c5eL,
    (0xcf41ce7dL << 32) | 0xe294a4baL,
     0x9afed7ecL << 32  };

/** Bits of pi/4, need for reducePayneHanek(). */
public static final long PI_O_4_BITS[] = new long[] {
    (0xc90fdaa2L << 32) | 0x2168c234L,
    (0xc4c6628bL << 32) | 0x80dc1cd1L };

/** Eighths.
 * This is used by sinQ, because its faster to do a table lookup than
 * a multiply in this time-critical routine
 */
public static final double EIGHTHS[] = {0, 0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1.0, 1.125, 1.25, 1.375, 1.5, 1.625};

/** Table of 2^((n+2)/3) */
public static final double CBRTTWO[] = { 0.6299605249474366,
                                        0.7937005259840998,
                                        1.0,
                                        1.2599210498948732,
                                        1.5874010519681994 };

/*
 *  There are 52 bits in the mantissa of a double.
 *  For additional precision, the code splits double numbers into two parts,
 *  by clearing the low order 30 bits if possible, and then performs the arithmetic
 *  on each half separately.
 */

/**
 * 0x40000000 - used to split a double into two parts, both with the low order bits cleared.
 * Equivalent to 2^30.
 */
public static final long HEX_40000000 = 0x40000000L; // 1073741824L

/** Mask used to clear low order 30 bits */
public static final long MASK_30BITS = -1L - (HEX_40000000 -1); // 0xFFFFFFFFC0000000L;

/** Mask used to clear the non-sign part of an int. */
public static final int MASK_NON_SIGN_INT = 0x7fffffff;

/** Mask used to clear the non-sign part of a long. */
public static final long MASK_NON_SIGN_LONG = 0x7fffffffffffffffl;

/** 2^52 - double numbers this large must be integral (no fraction) or NaN or Infinite */
public static final double TWO_POWER_52 = 4503599627370496.0;
/** 2^53 - double numbers this large must be even. */
public static final double TWO_POWER_53 = 2 * TWO_POWER_52;

/** Constant: {@value}. */
public static final double F_1_3 = 1d / 3d;
/** Constant: {@value}. */
public static final double F_1_5 = 1d / 5d;
/** Constant: {@value}. */
public static final double F_1_7 = 1d / 7d;
/** Constant: {@value}. */
public static final double F_1_9 = 1d / 9d;
/** Constant: {@value}. */
public static final double F_1_11 = 1d / 11d;
/** Constant: {@value}. */
public static final double F_1_13 = 1d / 13d;
/** Constant: {@value}. */
public static final double F_1_15 = 1d / 15d;
/** Constant: {@value}. */
public static final double F_1_17 = 1d / 17d;
/** Constant: {@value}. */
public static final double F_3_4 = 3d / 4d;
/** Constant: {@value}. */
public static final double F_15_16 = 15d / 16d;
/** Constant: {@value}. */
public static final double F_13_14 = 13d / 14d;
/** Constant: {@value}. */
public static final double F_11_12 = 11d / 12d;
/** Constant: {@value}. */
public static final double F_9_10 = 9d / 10d;
/** Constant: {@value}. */
public static final double F_7_8 = 7d / 8d;
/** Constant: {@value}. */
public static final double F_5_6 = 5d / 6d;
/** Constant: {@value}. */
public static final double F_1_2 = 1d / 2d;
/** Constant: {@value}. */
public static final double F_1_4 = 1d / 4d;
	private int x;

	public FunctionsImpl(int x){
		this.x=5;
	}
	
	public   double sum(double a, int b)
	{
		return ( a + b );
	}
	
	
	public   boolean inverse(float b)
	{
		return  true ;
	}
	
	
	public int echo(int s, String ss, short sss, char c, long r,byte bb)
	{
		return s;
	}
	
	
	public   int concat(int a, int b)
	{
		return ( a + b );
	}
	  public double cosh(double x) {
	      if (x != x) {
	          return x;
	      }

	      // cosh[z] = (exp(z) + exp(-z))/2

	      // for numbers with magnitude 20 or so,
	      // exp(-z) can be ignored in comparison with exp(z)

	      if (x > 20) {
	          if (x >= LOG_MAX_VALUE) {
	              // Avoid overflow (MATH-905).
	              final double t = exp(0.5 * x);
	              return (0.5 * t) * t;
	          } else {
	              return 0.5 * exp(x);
	          }
	      } else if (x < -20) {
	          if (x <= -LOG_MAX_VALUE) {
	              // Avoid overflow (MATH-905).
	              final double t = exp(-0.5 * x);
	              return (0.5 * t) * t;
	          } else {
	              return 0.5 * exp(-x);
	          }
	      }

	      final double hiPrec[] = new double[2];
	      if (x < 0.0) {
	          x = -x;
	      }
	      exp(x, 0.0, hiPrec);

	      double ya = hiPrec[0] + hiPrec[1];
	      double yb = -(ya - hiPrec[0] - hiPrec[1]);

	      double temp = ya * HEX_40000000;
	      double yaa = ya + temp - temp;
	      double yab = ya - yaa;

	      // recip = 1/y
	      double recip = 1.0/ya;
	      temp = recip * HEX_40000000;
	      double recipa = recip + temp - temp;
	      double recipb = recip - recipa;

	      // Correct for rounding in division
	      recipb += (1.0 - yaa*recipa - yaa*recipb - yab*recipa - yab*recipb) * recip;
	      // Account for yb
	      recipb += -yb * recip * recip;

	      // y = y + 1/y
	      temp = ya + recipa;
	      yb += -(temp - ya - recipa);
	      ya = temp;
	      temp = ya + recipb;
	      yb += -(temp - ya - recipb);
	      ya = temp;

	      double result = ya + yb;
	      result *= 0.5;
	      return result;
	    }

	    /** Compute the hyperbolic sine of a number.
	     * @param x number on which evaluation is done
	     * @return hyperbolic sine of x
	     */
	    public double sinh(double x) {
	      boolean negate = false;
	      if (x != x) {
	          return x;
	      }

	      // sinh[z] = (exp(z) - exp(-z) / 2

	      // for values of z larger than about 20,
	      // exp(-z) can be ignored in comparison with exp(z)

	      if (x > 20) {
	          if (x >= LOG_MAX_VALUE) {
	              // Avoid overflow (MATH-905).
	              final double t = exp(0.5 * x);
	              return (0.5 * t) * t;
	          } else {
	              return 0.5 * exp(x);
	          }
	      } else if (x < -20) {
	          if (x <= -LOG_MAX_VALUE) {
	              // Avoid overflow (MATH-905).
	              final double t = exp(-0.5 * x);
	              return (-0.5 * t) * t;
	          } else {
	              return -0.5 * exp(-x);
	          }
	      }

	      if (x == 0) {
	          return x;
	      }

	      if (x < 0.0) {
	          x = -x;
	          negate = true;
	      }

	      double result;

	      if (x > 0.25) {
	          double hiPrec[] = new double[2];
	          exp(x, 0.0, hiPrec);

	          double ya = hiPrec[0] + hiPrec[1];
	          double yb = -(ya - hiPrec[0] - hiPrec[1]);

	          double temp = ya * HEX_40000000;
	          double yaa = ya + temp - temp;
	          double yab = ya - yaa;

	          // recip = 1/y
	          double recip = 1.0/ya;
	          temp = recip * HEX_40000000;
	          double recipa = recip + temp - temp;
	          double recipb = recip - recipa;

	          // Correct for rounding in division
	          recipb += (1.0 - yaa*recipa - yaa*recipb - yab*recipa - yab*recipb) * recip;
	          // Account for yb
	          recipb += -yb * recip * recip;

	          recipa = -recipa;
	          recipb = -recipb;

	          // y = y + 1/y
	          temp = ya + recipa;
	          yb += -(temp - ya - recipa);
	          ya = temp;
	          temp = ya + recipb;
	          yb += -(temp - ya - recipb);
	          ya = temp;

	          result = ya + yb;
	          result *= 0.5;
	      }
	      else {
	          double hiPrec[] = new double[2];
	          expm1(x, hiPrec);

	          double ya = hiPrec[0] + hiPrec[1];
	          double yb = -(ya - hiPrec[0] - hiPrec[1]);

	          /* Compute expm1(-x) = -expm1(x) / (expm1(x) + 1) */
	          double denom = 1.0 + ya;
	          double denomr = 1.0 / denom;
	          double denomb = -(denom - 1.0 - ya) + yb;
	          double ratio = ya * denomr;
	          double temp = ratio * HEX_40000000;
	          double ra = ratio + temp - temp;
	          double rb = ratio - ra;

	          temp = denom * HEX_40000000;
	          double za = denom + temp - temp;
	          double zb = denom - za;

	          rb += (ya - za*ra - za*rb - zb*ra - zb*rb) * denomr;

	          // Adjust for yb
	          rb += yb*denomr;                        // numerator
	          rb += -ya * denomb * denomr * denomr;   // denominator

	          // y = y - 1/y
	          temp = ya + ra;
	          yb += -(temp - ya - ra);
	          ya = temp;
	          temp = ya + rb;
	          yb += -(temp - ya - rb);
	          ya = temp;

	          result = ya + yb;
	          result *= 0.5;
	      }

	      if (negate) {
	          result = -result;
	      }

	      return result;
	    }

	    /** Compute the hyperbolic tangent of a number.
	     * @param x number on which evaluation is done
	     * @return hyperbolic tangent of x
	     */
	    public  double tanh(double x) {
	      boolean negate = false;

	      if (x != x) {
	          return x;
	      }

	      // tanh[z] = sinh[z] / cosh[z]
	      // = (exp(z) - exp(-z)) / (exp(z) + exp(-z))
	      // = (exp(2x) - 1) / (exp(2x) + 1)

	      // for magnitude > 20, sinh[z] == cosh[z] in double precision

	      if (x > 20.0) {
	          return 1.0;
	      }

	      if (x < -20) {
	          return -1.0;
	      }

	      if (x == 0) {
	          return x;
	      }

	      if (x < 0.0) {
	          x = -x;
	          negate = true;
	      }

	      double result;
	      if (x >= 0.5) {
	          double hiPrec[] = new double[2];
	          // tanh(x) = (exp(2x) - 1) / (exp(2x) + 1)
	          exp(x*2.0, 0.0, hiPrec);

	          double ya = hiPrec[0] + hiPrec[1];
	          double yb = -(ya - hiPrec[0] - hiPrec[1]);

	          /* Numerator */
	          double na = -1.0 + ya;
	          double nb = -(na + 1.0 - ya);
	          double temp = na + yb;
	          nb += -(temp - na - yb);
	          na = temp;

	          /* Denominator */
	          double da = 1.0 + ya;
	          double db = -(da - 1.0 - ya);
	          temp = da + yb;
	          db += -(temp - da - yb);
	          da = temp;

	          temp = da * HEX_40000000;
	          double daa = da + temp - temp;
	          double dab = da - daa;

	          // ratio = na/da
	          double ratio = na/da;
	          temp = ratio * HEX_40000000;
	          double ratioa = ratio + temp - temp;
	          double ratiob = ratio - ratioa;

	          // Correct for rounding in division
	          ratiob += (na - daa*ratioa - daa*ratiob - dab*ratioa - dab*ratiob) / da;

	          // Account for nb
	          ratiob += nb / da;
	          // Account for db
	          ratiob += -db * na / da / da;

	          result = ratioa + ratiob;
	      }
	      else {
	          double hiPrec[] = new double[2];
	          // tanh(x) = expm1(2x) / (expm1(2x) + 2)
	          expm1(x*2.0, hiPrec);

	          double ya = hiPrec[0] + hiPrec[1];
	          double yb = -(ya - hiPrec[0] - hiPrec[1]);

	          /* Numerator */
	          double na = ya;
	          double nb = yb;

	          /* Denominator */
	          double da = 2.0 + ya;
	          double db = -(da - 2.0 - ya);
	          double temp = da + yb;
	          db += -(temp - da - yb);
	          da = temp;

	          temp = da * HEX_40000000;
	          double daa = da + temp - temp;
	          double dab = da - daa;

	          // ratio = na/da
	          double ratio = na/da;
	          temp = ratio * HEX_40000000;
	          double ratioa = ratio + temp - temp;
	          double ratiob = ratio - ratioa;

	          // Correct for rounding in division
	          ratiob += (na - daa*ratioa - daa*ratiob - dab*ratioa - dab*ratiob) / da;

	          // Account for nb
	          ratiob += nb / da;
	          // Account for db
	          ratiob += -db * na / da / da;

	          result = ratioa + ratiob;
	      }

	      if (negate) {
	          result = -result;
	      }

	      return result;
	    }

	
	    public static double exp(double x) {
	        return exp(x, 0.0, null);
	    }

	    /**
	     * Internal helper method for exponential function.
	     * @param x original argument of the exponential function
	     * @param extra extra bits of precision on input (To Be Confirmed)
	     * @param hiPrec extra bits of precision on output (To Be Confirmed)
	     * @return exp(x)
	     */
	    public static double exp(double x, double extra, double[] hiPrec) {
	        double intPartA;
	        double intPartB;
	        int intVal;

	        /* Lookup exp(floor(x)).
	         * intPartA will have the upper 22 bits, intPartB will have the lower
	         * 52 bits.
	         */
	        if (x < 0.0) {
	            intVal = (int) -x;

	            if (intVal > 746) {
	                if (hiPrec != null) {
	                    hiPrec[0] = 0.0;
	                    hiPrec[1] = 0.0;
	                }
	                return 0.0;
	            }

	            if (intVal > 709) {
	                /* This will produce a subnormal output */
	                final double result = exp(x+40.19140625, extra, hiPrec) / 285040095144011776.0;
	                if (hiPrec != null) {
	                    hiPrec[0] /= 285040095144011776.0;
	                    hiPrec[1] /= 285040095144011776.0;
	                }
	                return result;
	            }

	            if (intVal == 709) {
	                /* exp(1.494140625) is nearly a machine number... */
	                final double result = exp(x+1.494140625, extra, hiPrec) / 4.455505956692756620;
	                if (hiPrec != null) {
	                    hiPrec[0] /= 4.455505956692756620;
	                    hiPrec[1] /= 4.455505956692756620;
	                }
	                return result;
	            }

	            intVal++;

	            intPartA = ExpIntTable.EXP_INT_TABLE_A[EXP_INT_TABLE_MAX_INDEX-intVal];
	            intPartB = ExpIntTable.EXP_INT_TABLE_B[EXP_INT_TABLE_MAX_INDEX-intVal];

	            intVal = -intVal;
	        } else {
	            intVal = (int) x;

	            if (intVal > 709) {
	                if (hiPrec != null) {
	                    hiPrec[0] = Double.POSITIVE_INFINITY;
	                    hiPrec[1] = 0.0;
	                }
	                return Double.POSITIVE_INFINITY;
	            }

	            intPartA = ExpIntTable.EXP_INT_TABLE_A[EXP_INT_TABLE_MAX_INDEX+intVal];
	            intPartB = ExpIntTable.EXP_INT_TABLE_B[EXP_INT_TABLE_MAX_INDEX+intVal];
	        }

	        /* Get the fractional part of x, find the greatest multiple of 2^-10 less than
	         * x and look up the exp function of it.
	         * fracPartA will have the upper 22 bits, fracPartB the lower 52 bits.
	         */
	        final int intFrac = (int) ((x - intVal) * 1024.0);
	        final double fracPartA = ExpFracTable.EXP_FRAC_TABLE_A[intFrac];
	        final double fracPartB = ExpFracTable.EXP_FRAC_TABLE_B[intFrac];

	        /* epsilon is the difference in x from the nearest multiple of 2^-10.  It
	         * has a value in the range 0 <= epsilon < 2^-10.
	         * Do the subtraction from x as the last step to avoid possible loss of percison.
	         */
	        final double epsilon = x - (intVal + intFrac / 1024.0);

	        /* Compute z = exp(epsilon) - 1.0 via a minimax polynomial.  z has
	       full double precision (52 bits).  Since z < 2^-10, we will have
	       62 bits of precision when combined with the contant 1.  This will be
	       used in the last addition below to get proper rounding. */

	        /* Remez generated polynomial.  Converges on the interval [0, 2^-10], error
	       is less than 0.5 ULP */
	        double z = 0.04168701738764507;
	        z = z * epsilon + 0.1666666505023083;
	        z = z * epsilon + 0.5000000000042687;
	        z = z * epsilon + 1.0;
	        z = z * epsilon + -3.940510424527919E-20;

	        /* Compute (intPartA+intPartB) * (fracPartA+fracPartB) by binomial
	       expansion.
	       tempA is exact since intPartA and intPartB only have 22 bits each.
	       tempB will have 52 bits of precision.
	         */
	        double tempA = intPartA * fracPartA;
	        double tempB = intPartA * fracPartB + intPartB * fracPartA + intPartB * fracPartB;

	        /* Compute the result.  (1+z)(tempA+tempB).  Order of operations is
	       important.  For accuracy add by increasing size.  tempA is exact and
	       much larger than the others.  If there are extra bits specified from the
	       pow() function, use them. */
	        final double tempC = tempB + tempA;
	        final double result;
	        if (extra != 0.0) {
	            result = tempC*extra*z + tempC*extra + tempC*z + tempB + tempA;
	        } else {
	            result = tempC*z + tempB + tempA;
	        }

	        if (hiPrec != null) {
	            // If requesting high precision
	            hiPrec[0] = tempA;
	            hiPrec[1] = tempC*extra*z + tempC*extra + tempC*z + tempB;
	        }

	        return result;
	    }

	    /** Compute exp(x) - 1
	     * @param x number to compute shifted exponential
	     * @return exp(x) - 1
	     */
	    public static double expm1(double x) {
	      return expm1(x, null);
	    }

	    /** Internal helper method for expm1
	     * @param x number to compute shifted exponential
	     * @param hiPrecOut receive high precision result for -1.0 < x < 1.0
	     * @return exp(x) - 1
	     */
	    public static double expm1(double x, double hiPrecOut[]) {
	        if (x != x || x == 0.0) { // NaN or zero
	            return x;
	        }

	        if (x <= -1.0 || x >= 1.0) {
	            // If not between +/- 1.0
	            //return exp(x) - 1.0;
	            double hiPrec[] = new double[2];
	            exp(x, 0.0, hiPrec);
	            if (x > 0.0) {
	                return -1.0 + hiPrec[0] + hiPrec[1];
	            } else {
	                final double ra = -1.0 + hiPrec[0];
	                double rb = -(ra + 1.0 - hiPrec[0]);
	                rb += hiPrec[1];
	                return ra + rb;
	            }
	        }

	        double baseA;
	        double baseB;
	        double epsilon;
	        boolean negative = false;

	        if (x < 0.0) {
	            x = -x;
	            negative = true;
	        }

	        {
	            int intFrac = (int) (x * 1024.0);
	            double tempA = ExpFracTable.EXP_FRAC_TABLE_A[intFrac] - 1.0;
	            double tempB = ExpFracTable.EXP_FRAC_TABLE_B[intFrac];

	            double temp = tempA + tempB;
	            tempB = -(temp - tempA - tempB);
	            tempA = temp;

	            temp = tempA * HEX_40000000;
	            baseA = tempA + temp - temp;
	            baseB = tempB + (tempA - baseA);

	            epsilon = x - intFrac/1024.0;
	        }


	        /* Compute expm1(epsilon) */
	        double zb = 0.008336750013465571;
	        zb = zb * epsilon + 0.041666663879186654;
	        zb = zb * epsilon + 0.16666666666745392;
	        zb = zb * epsilon + 0.49999999999999994;
	        zb *= epsilon;
	        zb *= epsilon;

	        double za = epsilon;
	        double temp = za + zb;
	        zb = -(temp - za - zb);
	        za = temp;

	        temp = za * HEX_40000000;
	        temp = za + temp - temp;
	        zb += za - temp;
	        za = temp;

	        /* Combine the parts.   expm1(a+b) = expm1(a) + expm1(b) + expm1(a)*expm1(b) */
	        double ya = za * baseA;
	        //double yb = za*baseB + zb*baseA + zb*baseB;
	        temp = ya + za * baseB;
	        double yb = -(temp - ya - za * baseB);
	        ya = temp;

	        temp = ya + zb * baseA;
	        yb += -(temp - ya - zb * baseA);
	        ya = temp;

	        temp = ya + zb * baseB;
	        yb += -(temp - ya - zb*baseB);
	        ya = temp;

	        //ya = ya + za + baseA;
	        //yb = yb + zb + baseB;
	        temp = ya + baseA;
	        yb += -(temp - baseA - ya);
	        ya = temp;

	        temp = ya + za;
	        //yb += (ya > za) ? -(temp - ya - za) : -(temp - za - ya);
	        yb += -(temp - ya - za);
	        ya = temp;

	        temp = ya + baseB;
	        //yb += (ya > baseB) ? -(temp - ya - baseB) : -(temp - baseB - ya);
	        yb += -(temp - ya - baseB);
	        ya = temp;

	        temp = ya + zb;
	        //yb += (ya > zb) ? -(temp - ya - zb) : -(temp - zb - ya);
	        yb += -(temp - ya - zb);
	        ya = temp;

	        if (negative) {
	            /* Compute expm1(-x) = -expm1(x) / (expm1(x) + 1) */
	            double denom = 1.0 + ya;
	            double denomr = 1.0 / denom;
	            double denomb = -(denom - 1.0 - ya) + yb;
	            double ratio = ya * denomr;
	            temp = ratio * HEX_40000000;
	            final double ra = ratio + temp - temp;
	            double rb = ratio - ra;

	            temp = denom * HEX_40000000;
	            za = denom + temp - temp;
	            zb = denom - za;

	            rb += (ya - za * ra - za * rb - zb * ra - zb * rb) * denomr;

	            // f(x) = x/1+x
	            // Compute f'(x)
	            // Product rule:  d(uv) = du*v + u*dv
	            // Chain rule:  d(f(g(x)) = f'(g(x))*f(g'(x))
	            // d(1/x) = -1/(x*x)
	            // d(1/1+x) = -1/( (1+x)^2) *  1 =  -1/((1+x)*(1+x))
	            // d(x/1+x) = -x/((1+x)(1+x)) + 1/1+x = 1 / ((1+x)(1+x))

	            // Adjust for yb
	            rb += yb * denomr;                      // numerator
	            rb += -ya * denomb * denomr * denomr;   // denominator

	            // negate
	            ya = -ra;
	            yb = -rb;
	        }

	        if (hiPrecOut != null) {
	            hiPrecOut[0] = ya;
	            hiPrecOut[1] = yb;
	        }

	        return ya + yb;
	    }
	
}


