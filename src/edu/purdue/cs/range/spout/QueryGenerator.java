package edu.purdue.cs.range.spout;

import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import edu.purdue.cs.range.Constants;

public class QueryGenerator extends BaseRichSpout {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private Random random;
	private int id;
	TopologyContext context;

	public void ack(Object msgId) {
		System.out.println("OK:" + msgId);
	}

	public void close() {
	}

	public void fail(Object msgId) {
		System.out.println("FAIL:" + msgId);
	}

	public void nextTuple() {

		String str;

		if (id < Constants.numQueries) {
			id++;

			Double xmin = random.nextDouble() * (Constants.xMaxRange);
			Double ymin = random.nextDouble() * (Constants.yMaxRange);
			Double xrange = random.nextDouble() * (Constants.queryMaxWidth);
			Double yrange = random.nextDouble() * (Constants.queryMaxHeight);
			Double xmax = xmin + xrange;
			Double ymax = ymin + yrange;
			if (xmax > Constants.xMaxRange)
				xmax = Constants.xMaxRange;
			if (ymax > Constants.yMaxRange)
				ymax = Constants.yMaxRange;
			
			this.collector.emit(new Values(id, xmin, ymin, xmax, ymax));
			try {
				Thread.sleep(Constants.queryGeneratorDelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		id = 0;
		this.collector = collector;
		this.context = context;
		random = new Random(Constants.generatorSeed);
	}

	/**
	 * Declare the output field "word"
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(Constants.queryIdField,
				Constants.queryXMinField, Constants.queryYMinField,
				Constants.queryXMaxField, Constants.queryYMaxField));
	}
}
