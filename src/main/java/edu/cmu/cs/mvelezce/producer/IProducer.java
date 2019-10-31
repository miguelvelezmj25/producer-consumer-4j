package edu.cmu.cs.mvelezce.producer;

public interface IProducer<T> extends Runnable {

  T produce();

  boolean terminate();
}
