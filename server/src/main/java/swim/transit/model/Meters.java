package swim.transit.model;

import java.util.HashMap;
import java.util.Map;

import swim.structure.Form;
import swim.structure.Kind;
import swim.structure.Tag;

// collection of meter objects (represented by DataGeneration objects)

@Tag("meters")
public class Meters {
	private final Map<String, DataGeneration> meters = new HashMap<String, DataGeneration>();
	
	public Meters() {
		
	}
	
	public Map<String, DataGeneration> getMeters() {
		return meters;
	}
	
	public void add(DataGeneration meter) {
		meters.put("meter/"+meter.getMeterId(), meter); // I imagine meterId is unique per meter and therefore functions as an adequate identifier
	}
	
	@Kind 
	private static Form<Meters> form;
}
