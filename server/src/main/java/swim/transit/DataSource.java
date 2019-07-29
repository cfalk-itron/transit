package swim.transit;

import java.util.Random;

import swim.api.ref.SwimRef;
import swim.structure.Record;
import swim.structure.Form;
import swim.structure.Item;
import swim.structure.Value;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import swim.transit.model.*;

public class DataSource {
	// this will be accessed by meter plane and provide data about new meters
	private final SwimRef swim;
	private String[][] allMeters = new String[20][8];
	
	public DataSource(SwimRef swim) {
		this.swim = swim;
		// isn't used for the time being but could be if you wanted to call a command lane from this file
	}
	
	public void repeatSendMeterData(List<DataGeneration> meters) {
		for (DataGeneration m : meters) {
			final ScheduledExecutorService run = Executors.newSingleThreadScheduledExecutor();
			run.scheduleAtFixedRate(() -> sendMeterData(m), 5, 5, TimeUnit.SECONDS); // to change data ever 15 minutes: 15, TimeUnit.MINUTES
			
		}
	}
	
	public void sendMeterData(DataGeneration m) {
		final Meters meters = getMetersData(m);
		if (meters != null && meters.getMeters().size() > 0) {
			final Value value = Form.forClass(Meters.class).mold(meters).toValue();
			swim.command("meter/"+m.getTitle()+"/"+m.getMeterId(), "addMeters", value);
//			System.out.println(m.getTemp());
		}
	}
	
	private Meters getMetersData(DataGeneration m) {	
		final Meters meters = new Meters();
		final DataGeneration meter = new DataGeneration().withMeterId(m.getMeterId()).withTitle(m.getTitle()).withLatitude(m.getLatitude()).withLongitude(m.getLongitude())
                .withkWh(Float.parseFloat(generateMeterData(100.0f, 500.0f))).withVoltage(Float.parseFloat(generateMeterData(9.0f, 20.0f))).withTemp(Float.parseFloat(generateMeterData(60.0f, 80.0f)));
		meters.add(meter);
		return meters;
	}
	
	String[][] generateData() {
		
		allMeters[0] = new String[] { "01", "Villa_1", "21.831909", "21.831909", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[1] = new String[] { "02", "Villa_2", "21.831909", "-72.337318", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[2] = new String[] { "03", "Villa_3", "21.830889", "-72.337663", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[3] = new String[] { "04", "Villa_4", "21.830425", "-72.337954", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[4] = new String[] { "05", "Villa_5", "21.829907", "-72.338108", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[5] = new String[] { "06", "Villa_6", "21.831770", "-72.336320", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[6] = new String[] { "07", "Villa_7", "21.831641", "-72.335533", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[7] = new String[] { "08", "Villa_8", "21.831054", "-72.335781", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[8] = new String[] { "09", "Villa_9", "21.830551", "-72.336957", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[9] = new String[] { "10", "Villa_10", "21.829740", "-72.336898", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[10] = new String[] { "11", "Villa_12", "21.829059", "-72.337489", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[11] = new String[] { "12", "Villa_13", "21.828127", "-72.337206", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[12] = new String[] { "13", "Villa_14", "21.827186", "-72.336741", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[13] = new String[] { "14", "Villa_15", "21.826457", "-72.337437", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[14] = new String[] { "15", "Villa_16", "21.825922", "-72.336759", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[15] = new String[] { "16", "Villa_18", "21.825612", "-72.338191", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[16] = new String[] { "17", "Villa_30", "21.824620", "-72.339458", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[17] = new String[] { "18", "Villa_31", "21.824074", "-72.339912", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[18] = new String[] { "19", "Villa_32", "21.823554", "-72.340319", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
		allMeters[19] = new String[] { "20", "Villa_33", "21.823039", "-72.340506", generateMeterData(100.0f, 500.0f), generateMeterData(9.0f, 20.0f), generateMeterData(60.0f, 80.0f), "-1"};
				
		return allMeters;
		
	}
	
	//	Still don't know what realistic values to expect from kWh, voltage, and temp
	private static String generateMeterData(float leftBound, float rightBound) {
		// generates a float value
	    float randomValue = leftBound + new Random().nextFloat() * (rightBound - leftBound);
	    return Float.toString(randomValue);
	}
	
	@Override
	public String toString() {
		String returnString = "";
		for (String[] meter : allMeters) {
			returnString+=
					"\n{meterId='" + meter[0] + '\''
					+ ", title='" + meter[1] + '\''
					+ ", latitude='" + meter[2] + '\''
					+ ", longitude='" + meter[3] + '\''
					+ ", kWh='" + meter[4] + '\''
					+ ", voltage='" + meter[5] + '\''
					+ ", temp='" + meter[6] + '\''
					+ ", secsSinceReport='" + meter[7] + '\''
					+"}\n";
		}
				
		return returnString;
	}
}
