package fr.inria.diverse.noveltytesting.coverage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import fr.inria.diverse.noveltytesting.model.Interface;

public interface Coverage {
	 void exec(String sourcePackage, Interface anInterface, String myCompiledClasses) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, Exception;
	          
}
