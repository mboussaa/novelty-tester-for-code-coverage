package fr.inria.diverse.noveltytesting.coverage;

 

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.instr.Instrumenter;
import org.jacoco.core.runtime.IRuntime;
import org.jacoco.core.runtime.LoggerRuntime;
import org.jacoco.core.runtime.RuntimeData;

import fr.inria.diverse.noveltytesting.classfinder.ClassFinderImpl;
import fr.inria.diverse.noveltytesting.classfinder.ClassFinder;
import fr.inria.diverse.noveltytesting.model.ImplementedClass;
import fr.inria.diverse.noveltytesting.model.Interface;
import fr.inria.diverse.noveltytesting.model.Method;
import fr.inria.diverse.noveltytesting.model.MethodOutput;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * Coverage class using JaCoCo core API. Target classes will be instrumented,
 * executed and analyzed. Finally the coverage information will be dumped.
 * 
 * @author mboussaa
 *
 */

public class CoverageImpl implements Coverage {

	private MemoryClassLoader memoryClassLoader;
	private IRuntime runtime;
	private ClassFinder cf;
	private List<Class<?>> allJavaClasses;
	private List<Class<?>> implementedClasses;
	private List<Class<?>> targetedClasses;
	private File file;
	

	@Override
	public void exec(String sourcePackage, Interface anInterface, String myCompiledClasses)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, IOException, Exception {

		file = new File(myCompiledClasses);
		Class<?> clazz = Class.forName(anInterface.getName());

		memoryClassLoader = new MemoryClassLoader();
		runtime = new LoggerRuntime();
		cf = new ClassFinderImpl();

		allJavaClasses = cf.findClasses(sourcePackage);
		implementedClasses = cf.findClassesPerInterface(clazz, sourcePackage);
		targetedClasses = new LinkedList<Class<?>>();

		/**
		 * startup the runtime
		 *
		 */
		RuntimeData data = new RuntimeData();
		runtime.startup(data);

		/**
		 * Instrumentation of all java classes in the source package except
		 * interfaces
		 *
		 */
		for (int i = 0; i < allJavaClasses.size(); i++) {
			final Instrumenter instr = new Instrumenter(runtime);
			final byte[] instrumented = instr.instrument(
					getTargetClass(allJavaClasses.get(i).getName()),
					allJavaClasses.get(i).getName());
			if(!allJavaClasses.get(i).getName().equals("org.apache.commons.math3.exception.util.ExceptionContext"))
			memoryClassLoader.addDefinition(allJavaClasses.get(i).getName(),
					instrumented);
		}

		for (int i = 0; i < implementedClasses.size(); i++) {
			Class<?> cl = memoryClassLoader.loadClass(implementedClasses.get(i)
					.getName());
			targetedClasses.add(cl);

		}

		/**
		 * Invoke all services in the implemented classes
		 *
		 */
		for (java.lang.reflect.Method binMethod : clazz.getDeclaredMethods()) {
			List<String> paramTypes = new LinkedList<>();
			for (Class<?> c : binMethod.getParameterTypes()) {
				paramTypes.add(c.getName());
			}
			Method method = anInterface.getMethod(binMethod.getName(),
					paramTypes);
			
		
			if (method != null) {
				for (int i = 0; i < targetedClasses.size(); i++) {
					Method mConst = null;
					
					// get a random constructor from the list of generated constructors
					for (ImplementedClass ic : anInterface.getClasses()) {
						if (ic.getClasses().getName().equals(targetedClasses.get(i).getName())){
							 mConst = ic.getRandomConstructor();
						}
					}

					Constructor constructors = targetedClasses.get(i).getDeclaredConstructor(mConst.getParameterClassTypes());
					
					Object instance = constructors.newInstance(mConst.getParametersValue());

					binMethod.invoke(instance, method.getParametersValue());

				}
			}

		}

		/**
		 * shutdown the runtime
		 *
		 */
		ExecutionDataStore executionData = new ExecutionDataStore();
		SessionInfoStore sessionInfos = new SessionInfoStore();
		data.collect(executionData, sessionInfos, false);
		runtime.shutdown();

		/**
		 * analyze .classes in the source package
		 *
		 */
		CoverageBuilder coverageBuilder = new CoverageBuilder();
		Analyzer analyzer = new Analyzer(executionData, coverageBuilder);

		int c = analyzer.analyzeAll(file);

		double coverageRatio = 0;
		for (IClassCoverage cc : coverageBuilder.getClasses()) {
			coverageRatio += cc.getInstructionCounter().getCoveredRatio();
		}
		coverageRatio /= coverageBuilder.getClasses().size();
		anInterface.setCoverageRatio(coverageRatio);
	}

	/**
	 * set colors of visited statements
	 *
	 */
	private String getColor(final int status) {
		switch (status) {
		case ICounter.NOT_COVERED:
			return "red";
		case ICounter.PARTLY_COVERED:
			return "yellow";
		case ICounter.FULLY_COVERED:
			return "green";
		}
		return "";
	}

	/**
	 * get class as an input stream
	 *
	 */
	private InputStream getTargetClass(String packageName) {
		final String resource = '/' + packageName.replace('.', '/') + ".class";
		return getClass().getResourceAsStream(resource);
	}

}
