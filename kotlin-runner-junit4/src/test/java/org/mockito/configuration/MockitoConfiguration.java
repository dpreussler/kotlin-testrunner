package org.mockito.configuration;

/**
 * This is to clean runners between tests.
 */
public class MockitoConfiguration extends DefaultMockitoConfiguration {

    @Override
    public boolean enableClassCache() {
        return false;
    }
}