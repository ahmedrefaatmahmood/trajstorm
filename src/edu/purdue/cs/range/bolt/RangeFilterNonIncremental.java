package edu.purdue.cs.range.bolt;

import java.util.ArrayList;
import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import edu.purdue.cs.range.Constants;
import edu.purdue.cs.range.RangeQuery;

public class RangeFilterNonIncremental extends BaseBasicBolt {

	public void cleanup() {
	}

	ArrayList<RangeQuery> queryList;

	public void execute(Tuple input, BasicOutputCollector collector) {
		if (Constants.objectLocationGenerator
				.equals(input.getSourceComponent())) {
			filterData(input, collector);
		} else if (Constants.queryGenerator.equals(input.getSourceComponent())) {
			addQuery(input);
		}
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		queryList = new ArrayList<RangeQuery>();
	}

	void filterData(Tuple input, BasicOutputCollector collector) {
		boolean qualified = false;
		for (RangeQuery q : queryList) {
			if (q.isInsideRange(
					input.getDoubleByField(Constants.objectXCoordField),
					input.getDoubleByField(Constants.objectYCoordField))) {
				collector.emit(new Values(q.getQueryID(), input
						.getIntegerByField(Constants.objectIdField), input
						.getDoubleByField(Constants.objectXCoordField), input
						.getDoubleByField(Constants.objectYCoordField)));
				qualified = true;
				System.out.println(" Object " + input.getIntegerByField(Constants.objectIdField)
						+ " for Query " + q.getQueryID());
			}
		}
	}

	void addQuery(Tuple input) {
		RangeQuery query = new RangeQuery(
				input.getIntegerByField(Constants.queryIdField),
				input.getDoubleByField(Constants.queryXMinField),
				input.getDoubleByField(Constants.queryYMinField),
				input.getDoubleByField(Constants.queryXMaxField),
				input.getDoubleByField(Constants.queryYMaxField));
		queryList.add(query);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(Constants.queryIdField,
				Constants.objectIdField, Constants.objectXCoordField,
				Constants.objectYCoordField));
	}
}
