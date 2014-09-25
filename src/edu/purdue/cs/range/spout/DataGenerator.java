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

public class DataGenerator extends BaseRichSpout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private Random random;
	private int id;

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
		id++;
		Double x = random.nextDouble() * Constants.xMaxRange;
		Double y = random.nextDouble() * Constants.yMaxRange;
		this.collector.emit(new Values(id, x, y));
//		System.out
//				.println("tuple  generated id:" + id + " x: " + x + " y:" + y);
		try {

			Thread.sleep(Constants.dataGeneratorDelay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		id = 0;
		this.collector = collector;
		random = new Random(Constants.generatorSeed);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(Constants.objectIdField,
				  Constants.objectXCoordField,
				  Constants.objectYCoordField));
	}
}
