package swim.transit.agent;

import swim.api.SwimLane;
import swim.api.SwimTransient;
import swim.api.agent.AbstractAgent;
import swim.api.lane.CommandLane;
import swim.api.lane.MapLane;
import swim.api.lane.ValueLane;

import java.util.Collection;
import java.util.Map;

import swim.transit.model.*;

public class MeterAgent extends AbstractAgent {
	
	@SwimLane("dataGeneration")
	public ValueLane<DataGeneration> meter;
	
	/* added */
	@SwimLane("count") 
	public ValueLane<Integer> metersCount; // in the Amanyana simulation, this should equal exactly 20
	
	@SwimLane("addMeters")
	public CommandLane<Meters> addMeters = this.<Meters>commandLane().onCommand(this::onMeters);
	// kind of used to track snapshot of all meters' current states?
	
	@SwimTransient
	@SwimLane("meters")
	public MapLane<String, DataGeneration> meters;
	/* added ends */ 
	
	@SwimTransient
	@SwimLane("temps")
	public MapLane<Long, Float> temps;
	
	@SwimTransient
	@SwimLane("kWhs")
	public MapLane<Long, Float> kWhs;
	
	@SwimTransient
	@SwimLane("voltages")
	public MapLane<Long, Float> voltages;
	
	
	@SwimLane("addMeter")
	public CommandLane<DataGeneration> addMeter = this.<DataGeneration>commandLane().onCommand(this::onDataGeneration);
	
//	@SwimLane("addMeter")
//	public CommandLane<DataGeneration> addMeter = this.<DataGeneration>commandLane()
//		.onCommand(m -> {
//			onDataGeneration(m);
//		});
	
	private void onDataGeneration(DataGeneration meter) {
		final long time = System.currentTimeMillis() - (meter.getSecsSinceReport() * 1000L);
		
		this.meter.set(meter); // TODO: a bit confusing, but this.meter refers to the meter value lane; meter is the argument passed through onDataGeneration()
		temps.put(time, meter.getTemp());
		kWhs.put(time, meter.getkWh());
		voltages.put(time, meter.getVoltage());
		
		if (temps.size() > 10) {
			temps.drop(temps.size() - 10);
		    }
		if (kWhs.size() > 10) {
			kWhs.drop(kWhs.size() - 10);
		    }
		if (voltages.size() > 10) {
		      voltages.drop(voltages.size() - 10);
		    }
	}
	
	private void onMeters(Meters newMeters) {
		if (newMeters == null || newMeters.getMeters().size() == 0) {
			return; // abort function if there are no new meters
		}
		updateMeters(newMeters.getMeters());
		for (DataGeneration meter : newMeters.getMeters().values()) {
			final String thisMeterId = meter.getMeterId();
			if (thisMeterId != null && !thisMeterId.equals("")) {
				context.command("meter/"+meter.withMeterId(thisMeterId).getTitle()+"/"+thisMeterId, "addMeter", meter.toValue());
				addMeter("meter/"+meter.withMeterId(thisMeterId).getTitle()+"/"+thisMeterId, meter);
			}
		}
		metersCount.set(this.meters.size());
	}
	
	private void updateMeters(Map<String, DataGeneration> newMeters) {
		final Collection<DataGeneration> currentMeters = this.meters.values();
		for (DataGeneration meter : currentMeters) {
			if (!newMeters.containsKey(meter.getMeterId())) {
				meters.remove(meter.getMeterId());
			}
		}
	}
	
	private void addMeter(String thisMeterId, DataGeneration meter) {
		this.meters.put("meter/"+meter.withMeterId(thisMeterId).getTitle()+"/"+thisMeterId, meter.withMeterId(thisMeterId)); 
		System.out.println("Meter "+thisMeterId+" updated");
	}
	
	@Override
	public void didStart() {
		meters.clear();
		metersCount.set(0);
		System.out.println("Started Agent: " + nodeUri().toString());
	}
}
