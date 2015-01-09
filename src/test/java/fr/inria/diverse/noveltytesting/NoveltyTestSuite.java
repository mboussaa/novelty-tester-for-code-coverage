package fr.inria.diverse.noveltytesting;

import fr.inria.diverse.noveltytesting.model.TestModel;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestModel.class,
        NoveltyGenerationTest.class
})
public class NoveltyTestSuite {
    /* DO NOT PUT CODE HERE, IT IS MEANINGLESS
     * THIS CLASS IS HERE JUST TO HOLD @RunWith
     * & @SuiteClasses annotations */
}
