package edu.cmu.cs.mvelezce.implementation.string.producer;

import edu.cmu.cs.mvelezce.producer.IProducer;
import edu.cmu.cs.mvelezce.setup.IProducerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public final class StringProducer implements IProducer<String> {

  private final BlockingQueue<String> queue;
  private final BlockingQueue<String> inputQueue;

  private final AtomicBoolean shouldTerminate = new AtomicBoolean(false);

  public StringProducer(BlockingQueue<String> queue, BlockingQueue<String> inputQueue) {
    this.queue = queue;
    this.inputQueue = inputQueue;
  }

  @Override
  public boolean shouldTerminate() {
    return shouldTerminate.get();
  }

  @Override
  public void terminate() {
    try {
      this.inputQueue.put(IProducerConsumer.EOF_PRODUCER);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    this.shouldTerminate.set(true);
  }

  @Override
  public void run() {

    try {
      while (!this.shouldTerminate()) {
        this.queue.put(this.inputQueue.take());
      }

      System.out.println("Terminating from " + Thread.currentThread().getName());
    } catch (InterruptedException ie) {
      throw new RuntimeException(ie);
    }
  }
}
