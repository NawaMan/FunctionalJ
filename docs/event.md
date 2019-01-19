# Event



The intention is to provide an abstraction for event response.
This is similar to those of RxJava.
The different is that, here, the response code SHOULD not have to concern how the event flow was prepared or modified.
Similar to the separation between `DeferAction` and `Promise`,
  functional code should not have to care about something like debousing or what thread this will be running.

Another difference is that `Result<...>` is passed along the reactive flow so that both success/failure are handled uniformly.

The work on this is still in progress so not much of use.



I will write this -- promise.

Nawa - 2019-01-08