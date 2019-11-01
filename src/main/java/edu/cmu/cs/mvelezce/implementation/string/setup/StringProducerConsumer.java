package edu.cmu.cs.mvelezce.implementation.string.setup;

import edu.cmu.cs.mvelezce.consumer.IConsumer;
import edu.cmu.cs.mvelezce.implementation.string.consumer.StringProducer;
import edu.cmu.cs.mvelezce.implementation.string.producer.StringConsumer;
import edu.cmu.cs.mvelezce.producer.IProducer;
import edu.cmu.cs.mvelezce.setup.IProducerConsumer;
import edu.cmu.cs.mvelezce.setup.ProducerConsumerSetup;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class StringProducerConsumer implements IProducerConsumer {

  private final ProducerConsumerSetup<String> producerConsumerSetup;
  private final Runnable producerThread;
  private final Runnable consumerThread;

  private StringProducerConsumer() {
    BlockingQueue<String> queue = new ArrayBlockingQueue<>(1_000_000);
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    this.producerConsumerSetup = new ProducerConsumerSetup<>(queue, executorService);

    IProducer<String> producer = new StringProducer(queue);
    this.producerThread = new Thread(producer);

    IConsumer<String> consumer = new StringConsumer(queue);
    this.consumerThread = new Thread(consumer);
  }

  public static void main(String[] args) {
    StringProducerConsumer stringProducerConsumer = new StringProducerConsumer();
    stringProducerConsumer.execute();
  }

  @Override
  public void execute() {
    this.producerConsumerSetup.getExecutorService().execute(this.producerThread);
    this.producerConsumerSetup.getExecutorService().execute(this.consumerThread);

    this.producerConsumerSetup.getExecutorService().shutdown();
  }
}
