Undertow JFR
============

An Undertow [HttpHandler](http://undertow.io/javadoc/2.0.x/io/undertow/server/HttpHandler.html) that generates [Flight Recorder](https://openjdk.java.net/jeps/328) events.

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
              <filter name="jfr" module="com.github.marschall.undertow.jfr" class-name="com.github.marschall.untertow.jfr.JfrHandler"/>
            </filters>
        </subsystem>
```


TODO
----

- Can we use a direct jigsaw module dependency in `module.xml`?
- Should exception in `#handleRequest` be caught?
- What about async servlet? Should we use `@TransitionFrom` and `@TransitionTo`?
- What other attributes should we log.

