package fr.inria.diverse.noveltytesting.sut;

import haxe.root.Array;

public  class second implements Functions
{
	private int x;
	
	public second(int x) {
		this.x = x;
	}
	
	public second(int x,float y) {
		this.x = x;
	}
	
	public   double sum(double a, int b)
	{
		return ( a + b );
	}
	
	
	public   boolean inverse(float b)
	{
		return  false ;
	}
	
	
	public int echo(int s, String ss, short sss, char c, long r,byte bb)
	{
		return s;
	}
	
	
	public   int concat(int a, int b)
	{
		return ( a + b );
	}


	@Override
	public boolean __hx_deleteField(String field) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Object __hx_lookupField(String field, boolean throwErrors,
			boolean isCheck) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public double __hx_lookupField_f(String field, boolean throwErrors) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Object __hx_lookupSetField(String field, Object value) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public double __hx_lookupSetField_f(String field, double value) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double __hx_setField_f(String field, double value,
			boolean handleProperties) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Object __hx_setField(String field, Object value,
			boolean handleProperties) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object __hx_getField(String field, boolean throwErrors,
			boolean isCheck, boolean handleProperties) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public double __hx_getField_f(String field, boolean throwErrors,
			boolean handleProperties) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Object __hx_invokeField(String field, Array dynargs) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void __hx_getFields(Array<String> baseArr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double cosh(double x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double sinh(double x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double tanh(double x) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
}