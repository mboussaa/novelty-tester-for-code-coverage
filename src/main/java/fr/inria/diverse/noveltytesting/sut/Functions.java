package fr.inria.diverse.noveltytesting.sut;

@SuppressWarnings(value={"rawtypes", "unchecked"})
public  interface Functions extends haxe.lang.IHxObject
{
	   int echo(int s, String ss);
	
	   double sum(double a, int b);
	
	   boolean inverse(boolean b);
	
	   int concat(int a, int b);
	   
	   public double cosh(double x);
	   
	   public double sinh(double x);
	   
	   public double tanh(double x) ;
	
}