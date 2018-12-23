package com.github.marschall.undertow.jfr;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.StackTrace;
import jdk.jfr.TransitionFrom;
import jdk.jfr.TransitionTo;

/**
 * An {@link HttpHandler} that generates <a href="https://openjdk.java.net/jeps/328">Flight Recorder</a> events.
 */
public class JfrHandler implements HttpHandler {

  // http://hirt.se/blog/?p=870
  // https://www.oracle.com/technetwork/oem/soa-mgmt/con10912-javaflightrecorder-2342054.pdf

  private final HttpHandler next;

  public JfrHandler(HttpHandler next) {
    this.next = next;
  }

  @Override
  public void handleRequest(HttpServerExchange exchange) throws Exception {
    HttpEvent event = new HttpEvent();
    event.setMethod(exchange.getRequestMethod().toString());
    event.setUri(exchange.getRequestURI());
    event.setQuery(exchange.getQueryString());
    event.begin();
    exchange.addExchangeCompleteListener((completedExchange, nextListener) -> {
      try {
        event.setCompletedIn(Thread.currentThread());
        event.end();
        event.commit();
      } finally {
        nextListener.proceed();
      }
    });
    this.next.handleRequest(exchange);
    event.setStartedIn(Thread.currentThread());
  }

  @Label("HTTP exchange")
  @Description("An HTTP exchange")
  @Category("HTTP")
  @StackTrace(false)
  static class HttpEvent extends Event {

    @Label("Method")
    @Description("The HTTP method")
    private String method;

    @Label("URI")
    @Description("The request URI")
    private String uri;

    @Label("Query")
    @Description("The query string")
    private String query;

    @TransitionFrom
    @Label ("Started In")
    @Description("The IO thread in which the exchange starts")
    private Thread startedIn;

    @TransitionTo
    @Label ("Completed In")
    @Description("The worker thread in which the exchange finishes")
    private Thread completedIn;

    String getMethod() {
      return this.method;
    }

    void setMethod(String operationName) {
      this.method = operationName;
    }

    String getUri() {
      return this.uri;
    }

    void setUri(String query) {
      this.uri = query;
    }

    String getQuery() {
      return this.query;
    }

    void setQuery(String query) {
      this.query = query;
    }

    Thread getStartedIn() {
      return startedIn;
    }

    void setStartedIn(Thread startedIn) {
      this.startedIn = startedIn;
    }

    Thread getCompletedIn() {
      return completedIn;
    }

    void setCompletedIn(Thread completedIn) {
      this.completedIn = completedIn;
    }



  }

}
