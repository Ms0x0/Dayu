package org.secbug.inter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.secbug.conf.Context;

public class IntruderCrack {

	private ExecutorService pool = Executors.newFixedThreadPool(Context.THREADNUM);
	private List<Future<String>> resp = new ArrayList<>();

	public IntruderCrack() {

	}

	public void start(Callable<String> task) {

		this.resp.add(this.pool.submit(task));
	}

	public void clearCache() {

		for (Future<String> future : this.resp) {
			try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				// e.printStackTrace();
			} finally {
				future.cancel(true);
			}
		}
		this.resp = new ArrayList<>();
		System.gc();
	}

	public void shutdown() {

		this.pool.shutdown();
	}
}
