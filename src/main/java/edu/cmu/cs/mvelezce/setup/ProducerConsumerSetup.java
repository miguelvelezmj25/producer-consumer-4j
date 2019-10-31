package edu.cmu.cs.mvelezce.setup;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class ProducerConsumerSetup<T> {
  private final BlockingQueue<T> queue;
  private final ExecutorService executorService;

  public ProducerConsumerSetup(BlockingQueue<T> queue, ExecutorService executorService) {
    this.queue = queue;
    this.executorService = executorService;
  }

  public BlockingQueue<T> getQueue() {
    return queue;
  }

  public ExecutorService getExecutorService() {
    return executorService;
  }
}
