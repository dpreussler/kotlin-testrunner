[![Build Status](https://travis-ci.org/dpreussler/kotlin-testrunner.svg?branch=master)](https://travis-ci.org/dpreussler/kotlin-testrunner)


# kotlin-testrunner

Classes and methods are final by default in kotlin.
This makes mocking impossible. This leaves you with the possibilies of 
* either open everything you need in code just for your tests 
* introduce interfaces for every kotlin class you want to mock 
* use Powermock and prepare all your kotlin classes for the test with annoations.

This library removes final modifiers of classes and methods during the test in runtime.


This can not be combined with Robolectric for now but feel free to build a RobolectricTestrunner version based on this


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

compile 'de.jodamob.kotlin:kotlin-testrunner:0.2'
 
```


License
=======

The MIT License (MIT)

Copyright (c) 2016 Danny Preussler

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
