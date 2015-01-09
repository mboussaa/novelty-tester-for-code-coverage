package fr.inria.diverse.noveltytesting.runner;

import fr.inria.diverse.noveltytesting.model.Interface;
import fr.inria.diverse.noveltytesting.model.Method;
import fr.inria.diverse.noveltytesting.model.MethodOutput;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * This class runs the methods of the given Interface model using the generated
 * data for the different parameters
 *
 * Created by leiko on 17/10/14.
 */
public class RunnerImpl implements Runner {

    @Override
    public void exec(Class<?> clazz, Interface anInterface)
            throws InstantiationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object instance = clazz.newInstance();

        for (java.lang.reflect.Method binMethod : clazz.getDeclaredMethods()) {
            List<String> paramTypes = new LinkedList<>();
            for (Class<?> c : binMethod.getParameterTypes()) {
                paramTypes.add(c.getName());
            }
            Method method = anInterface.getMethod(binMethod.getName(), paramTypes);
            if (method != null) {
                Object returnVal = binMethod.invoke(instance, method.getParametersValue());
                if (returnVal != null) {
                    //method.setReturnVal(returnVal);
                    // TODO improve that
                    MethodOutput mo = new MethodOutput();
                    mo.setReturnVal(returnVal);
                    method.addMethodOutput("java", mo);

                }
            }
        }
    }
}