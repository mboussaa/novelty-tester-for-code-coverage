package fr.inria.diverse.noveltytesting.sut;

@SuppressWarnings(value={"rawtypes", "unchecked"})
public  interface Functions extends haxe.lang.IHxObject
{
	   int echo(int s, String ss, short sss, char c, long r, byte bb);
	
	   double sum(double a, int b);
	
	   boolean inverse(float b);
	
	   int concat(int a, int b);
	   
	   public double cosh(double x);
	   
	   public double sinh(double x);
	   
	   public double tanh(double x) ;
	
}