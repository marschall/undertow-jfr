Undertow JFR [![Build Status](https://travis-ci.org/marschall/undertow-jfr.svg?branch=master)](https://travis-ci.org/marschall/undertow-jfr)
============

An Undertow [HttpHandler](http://undertow.io/javadoc/2.0.x/io/undertow/server/HttpHandler.html) that generates [Flight Recorder](https://openjdk.java.net/jeps/328) events.

This project requires Java 11.

![Flight Recording of some HTTP requests](https://raw.githubusercontent.com/marschall/undertow-jfr/master/src/main/javadoc/Screenshot%20from%202018-12-08%2023-46-14.png)


Usage
-----

```xml
        <subsystem xmlns="urn:jboss:domain:undertow:..." ...>
            <!-- ... -->
            <server ...>
                <!-- ... -->
                <host ...>
                    <!-- ... -->
                    <filter-ref name="jfr"/>
                </host>
            </server>
            <!-- ... -->
            <filters>
              <filter name="jfr" module="com.github.marschall.undertow.jfr" class-name="com.github.marschall.undertow.jfr.JfrHandler"/>
            </filters>
        </subsystem>
```


TODO
----

- Can we use a direct jigsaw module dependency in `module.xml`?
- Should exception in `#handleRequest` be caught?
- What about async servlet? Should we use `@TransitionFrom` and `@TransitionTo`?
- What other attributes should we log.

