package edu.cmu.cs.mvelezce.implementation.string.producer;

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
    System.out.println("Consuming message: " + message);
  }

  @Override
  public boolean terminate(String message) {
    return IProducerConsumer.EOF_PRODUCER.equals(message);
  }

  @Override
  public void run() {
    while (true) {
      try {
        String data = this.queue.take();

        if (this.terminate(data)) {
          break;
        }

        this.consume(data);
      } catch (InterruptedException ie) {
        throw new RuntimeException(ie);
      }
    }
  }
}