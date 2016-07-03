[![Build Status](https://travis-ci.org/dpreussler/kotlin-testrunner.svg?branch=master)](https://travis-ci.org/dpreussler/kotlin-testrunner)


# kotlin-testrunner

Classes and methods are final by default in kotlin.
This makes mocking impossible. This leaves you with the possibilies of 
* either open everything you need in code just for your tests 
* introduce interfaces for every kotlin class you want to mock 
* use Powermock and prepare all your kotlin classes for the test with annoations.

This library removes final modifiers of classes and methods during the test in runtime.

Usage
=====
for Java:
--------
```java
@RunWith(KotlinTestRunner.class)
public class MyJavaTestclass {
   @Test 
   public void test() {
   ...
   }
}
```
for Kotlin:
--------
```kotlin
@RunWith(KotlinTestRunner::class)
class MyKotlinTestclass {
   @Test 
   fun test() {
   ...
   }
}
```


Gradle
======

```groovy
maven { url 'https://oss.sonatype.org/content/repositories/staging/'}
...

compile 'de.jodamob.kotlin:kotlin-testrunner:0.1'
 
```
