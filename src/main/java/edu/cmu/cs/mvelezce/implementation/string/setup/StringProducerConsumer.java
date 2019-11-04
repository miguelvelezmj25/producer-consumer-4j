package edu.cmu.cs.mvelezce.implementation.string.setup;

import edu.cmu.cs.mvelezce.implementation.string.consumer.StringConsumer;
import edu.cmu.cs.mvelezce.implementation.string.producer.StringProducer;
import edu.cmu.cs.mvelezce.setup.IProducerConsumer;
import edu.cmu.cs.mvelezce.setup.ProducerConsumerSetup;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class StringProducerConsumer implements IProducerConsumer {

  private final ProducerConsumerSetup<String> producerConsumerSetup;
  private final BlockingQueue<String> inputQueue = new ArrayBlockingQueue<>(1_000_0000);
  private final Runnable producer;
  private final Runnable consumer;

  private StringProducerConsumer() {
    BlockingQueue<String> queue = new ArrayBlockingQueue<>(1_000_000);
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    this.producerConsumerSetup = new ProducerConsumerSetup<>(queue, executorService);

    this.producer = new StringProducer(queue, this.inputQueue);
    this.consumer = new StringConsumer(queue);
  }

  public static void main(String[] args) {
    StringProducerConsumer stringProducerConsumer = new StringProducerConsumer();
    stringProducerConsumer.execute();
  }

  @Override
  public void execute() {
    this.producerConsumerSetup.getExecutorService().execute(this.producer);
    this.producerConsumerSetup.getExecutorService().execute(this.consumer);

    this.producerConsumerSetup.getExecutorService().shutdown();

    this.enter(0);
    this.enter(1);
    this.exit(1);
    this.exit(0);

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    System.out.println("Terminate");
    ((StringProducer) this.producer).terminate();
  }

  private void enter(int i) {
    try {
      this.inputQueue.put("Enter " + i);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void exit(int i) {
    try {
      this.inputQueue.put("Exit " + i);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
