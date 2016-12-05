package com.cs.test.metric;

import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by admin on 2016/12/5.
 */
@Component
public class CsMetric implements PublicMetrics {
	@Override
	public Collection<Metric<?>> metrics() {
		List<Metric<?>> metrics = new ArrayList<>();

		metrics.add(new Metric<Integer>("csMetric", 10));
		metrics.add(new Metric<Integer>("csMetric.key1", 20));
		metrics.add(new Metric<Integer>("csMetric.key2", 30));

		return metrics;
	}
}
