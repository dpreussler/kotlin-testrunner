package de.jodamob.kotlin.testrunner.sample

import org.spockframework.mock.CannotCreateMockException
import spock.lang.Specification

class TestWithoutRunner extends Specification {

    def "should fail without mock"() {
        given:
        def finalClassSample = new FinalClassSample()
        def classToBeTested = new ClassToBeTested(finalClassSample)

        when:
        classToBeTested.callMe()

        then:
        thrown IllegalAccessError
    }

    def "should fail trying to mock"() {
        expect:
        try {
            def finalClassSample = Mock FinalClassSample
            def classToBeTested = new ClassToBeTested(finalClassSample)
            classToBeTested.callMe()
        } catch (Exception error) {
            assert error instanceof CannotCreateMockException
        }
    }
}