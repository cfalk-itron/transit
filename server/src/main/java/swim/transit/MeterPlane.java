package swim.transit;

import java.util.ArrayList;
import java.util.List;

import swim.api.SwimAgent;
import swim.api.SwimRoute;
import swim.api.agent.AgentRoute;
import swim.api.plane.AbstractPlane;
import swim.api.ref.SwimRef;
import swim.fabric.Fabric;
import swim.kernel.Kernel;
import swim.server.ServerLoader;

import main.java.meterDataSim.agent.MeterAgent;
import main.java.meterDataSim.model.DataGeneration;

public class MeterPlane extends AbstractPlane {
	@SwimAgent("meter")
	@SwimRoute("meter/:meterId")
	AgentRoute<MeterAgent> meterAgent;

	// if there were to be more agents, you would follow the pattern below
	/*
	 * @SwimAgent("nameofagent")
	 * 
	 * @SwimRoute("pathtofollow") AgentRoute<AgentType> agentRouteInstanceName;
	 */

	public static void main(String[] args) {
		final Kernel kernel = ServerLoader.loadServer();
		final Fabric fabric = (Fabric) kernel.getSpace("main.java.meterDataSim");

		kernel.start();
		System.out.println("Running MeterPlane");

		startMeterDataGeneration(fabric);

		kernel.run(); // according to Swim repo, this "blocks until termination"
	}

	private static void startMeterDataGeneration(SwimRef swim) {
		// this invokes getMeterData and adds them with the command lane
		final List<DataGeneration> meters = getMeterDataGenerations();
		for (DataGeneration meter: meters) {
			// System.out.println(meter); // uncomment to confirm that meter data String[]s have been correctly parsed into DataSource objects
			// System.out.println(meter.toValue) //uncomment to see how meter DataGeneration instance gets parsed into Recon
			 swim.command("meter/"+meter.getMeterId(), "addMeter", meter.toValue());

		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

		}
	}

	private static List<DataGeneration> getMeterDataGenerations() {
		// this method takes in
		final List<DataGeneration> meters = new ArrayList<>();
		final DataSource dataSource = new DataSource();
		for (String[] data : dataSource.generateData()) {
			meters.add(new DataGeneration(data[0], data[1], Float.parseFloat(data[2]), Float.parseFloat(data[3]),
					Float.parseFloat(data[4]), Float.parseFloat(data[5]), Float.parseFloat(data[6]),
					Integer.parseInt(data[7])));
		}
		// Uncomment to see what meter data gets generated
		// System.out.println(dataSource);
		return meters;
	}
}
