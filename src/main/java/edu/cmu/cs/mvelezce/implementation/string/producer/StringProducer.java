package edu.cmu.cs.mvelezce.implementation.string.producer;

import edu.cmu.cs.mvelezce.producer.IProducer;
import edu.cmu.cs.mvelezce.setup.IProducerConsumer;

import java.util.concurrent.BlockingQueue;

public final class StringProducer implements IProducer<String> {

  private final BlockingQueue<String> queue;

  public StringProducer(BlockingQueue<String> queue) {
    this.queue = queue;
  }

  @Override
  public String produce() {
    throw new UnsupportedOperationException("Method should not be called");
  }

  private String produce(int iter) {
    return iter + "";
  }

  @Override
  public boolean terminate() {
    throw new UnsupportedOperationException("Method should not be called");
  }

  @Override
  public void run() {
    try {
      for (int i = 0; i < 20; i++) {
        this.queue.put(this.produce(i));
      }

      this.queue.put(IProducerConsumer.EOF_PRODUCER);
    } catch (InterruptedException ie) {
      throw new RuntimeException(ie);
    }
  }
}
