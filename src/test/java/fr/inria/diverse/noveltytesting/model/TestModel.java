package fr.inria.diverse.noveltytesting.model;

import org.junit.Test;

public class TestModel {

    @Test
    public void testModel() {
        Interface i = new Interface();
        String iName = "org.foo.bar.MyInterface";
        i.setName(iName);

        Method m0 = new Method();
        m0.setName("myMethod");

        Method m1 = new Method();
        m1.setName("toString");
        m1.setReturnValType(String.class.getTypeName());

        Parameter p0 = new Parameter();
        p0.setName("foo");
        p0.setValue(42);
        p0.setType(Integer.class.getTypeName());

        Parameter p1 = new Parameter();
        p1.setName("bar");
        p1.setValue("baz");
        p1.setType(String.class.getTypeName());

        m0.addParameter(p0);
        m0.addParameter(p1);

        i.addMethod(m0);
        i.addMethod(m1);

        // TODO some asserts
    }
}
