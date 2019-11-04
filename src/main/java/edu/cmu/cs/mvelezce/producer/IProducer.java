package edu.cmu.cs.mvelezce.producer;

public interface IProducer<T> extends Runnable {

  boolean shouldTerminate();

  void terminate();
}
