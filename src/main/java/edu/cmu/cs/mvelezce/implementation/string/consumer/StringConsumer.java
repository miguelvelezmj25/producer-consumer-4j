package edu.cmu.cs.mvelezce.implementation.string.consumer;

import edu.cmu.cs.mvelezce.consumer.IConsumer;
import edu.cmu.cs.mvelezce.setup.IProducerConsumer;

import java.util.concurrent.BlockingQueue;

public final class StringConsumer implements IConsumer<String> {

  private final BlockingQueue<String> queue;

  public StringConsumer(BlockingQueue<String> queue) {
    this.queue = queue;
  }

  @Override
  public void consume(String message) {
    System.out.println(
        "Consuming message: " + message + " from " + Thread.currentThread().getName());
  }

  @Override
  public boolean shouldTerminate(String message) {
    return IProducerConsumer.EOF_PRODUCER.equals(message);
  }

  @Override
  public void run() {
    while (true) {
      try {
        String data = this.queue.take();

        if (this.shouldTerminate(data)) {
          System.out.println("Terminating from " + Thread.currentThread().getName());
          break;
        }

        this.consume(data);
      } catch (InterruptedException ie) {
        throw new RuntimeException(ie);
      }
    }
  }
}
