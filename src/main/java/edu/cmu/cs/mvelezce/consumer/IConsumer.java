package edu.cmu.cs.mvelezce.consumer;

public interface IConsumer<T> extends Runnable {

  void consume(T data);

  boolean shouldTerminate(T data);
}
