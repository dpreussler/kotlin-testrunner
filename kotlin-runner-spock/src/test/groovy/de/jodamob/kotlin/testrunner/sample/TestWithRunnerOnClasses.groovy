package de.jodamob.kotlin.testrunner.sample

import de.jodamob.kotlin.testrunner.OpenedClasses
import de.jodamob.kotlin.testrunner.SpotlinTestRunner
import de.jodamob.kotlin.testrunner.nonopen.FinalNonOpenClassSample
import org.junit.runner.RunWith
import org.spockframework.mock.CannotCreateMockException
import spock.lang.Specification

@RunWith(SpotlinTestRunner.class)
@OpenedClasses(FinalClassSample)
class TestWithRunnerOnClasses extends Specification {

    def "should fail without mock"() {
        expect:
        try {
            def finalClassSample = Mock FinalClassSample
            def classToBeTested = new ClassToBeTested(finalClassSample)
            classToBeTested.callMe()
        } catch (Exception error) {
            assert error instanceof CannotCreateMockException
        }
    }

    def "should mock correctly"() {
        given:
        def finalClassSample = Mock FinalClassSample
        def classToBeTested = new ClassToBeTested(finalClassSample)

        when:
        classToBeTested.callMe()

        then:
        notThrown IllegalAccessError
    }

    def "should open classes within filter only"() {
        expect:
        try {
            def finalClassSample = Mock FinalNonOpenClassSample
            def classToBeTested = new ClassToBeTested(finalClassSample)
            classToBeTested.callMe()
        } catch (Exception error) {
            assert error instanceof CannotCreateMockException
        }
    }
}