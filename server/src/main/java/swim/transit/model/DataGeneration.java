package main.java.meterDataSim.model;

import java.util.Objects;
import swim.structure.Form;
import swim.structure.Kind;
import swim.structure.Tag;
import swim.structure.Value;

// represents the data generated PER meter

@Tag("DataGeneration")
public class DataGeneration {
	// meter specific data
	private String meterId = ""; // not sure of meter id format, so it is a string, not int/other num data type
	private String title = "";
	private float latitude = 0.0f;
	private float longitude = 0.0f;
	private float kWh = 0.0f;
	private float voltage = 0.0f;
	private float temp = 0.0f;
	
	// not sure if this is redundant/used in Transit for other purposes (I am emulating the architecture)
	private int secsSinceReport = -1; // does this really need to be passed through or should it be defined at init per meter instance anyway?
	
	public DataGeneration() {
		
	}
	
	public DataGeneration(String meterId, String title, float latitude, float longitude, float kWh, float voltage, float temp, 
			int secsSinceReport) {
		this.meterId = meterId;
		this.title = title;
		this.latitude = latitude;
		this.longitude = longitude;
		this.kWh = kWh;
		this.voltage = voltage;
		this.temp = temp;
		this.secsSinceReport = secsSinceReport;
	}

	public String getMeterId() {
		return meterId;
	}

	public DataGeneration withMeterId(String meterId) {
		return new DataGeneration(meterId, title, latitude, longitude, kWh, voltage, temp, secsSinceReport);
	}
	
	public String getTitle() {
		return title;
	}

	public DataGeneration withTitle(String title) {
		return new DataGeneration(meterId, title, latitude, longitude, kWh, voltage, temp, secsSinceReport);
	}

	public float getLatitude() {
		return latitude;
	}

	public DataGeneration withLatitude(float latitude) {
		return new DataGeneration(meterId, title, latitude, longitude, kWh, voltage, temp, secsSinceReport);
	}

	public float getLongitude() {
		return longitude;
	}

	public DataGeneration withLongitude(float longitude) {
		return new DataGeneration(meterId, title, latitude, longitude, kWh, voltage, temp, secsSinceReport);
	}

	public float getkWh() {
		return kWh;
	}

	public DataGeneration withkWh(float kWh) {
		return new DataGeneration(meterId, title, latitude, longitude, kWh, voltage, temp, secsSinceReport);
	}

	public float getVoltage() {
		return voltage;
	}

	public DataGeneration withVoltage(float voltage) {
		return new DataGeneration(meterId, title, latitude, longitude, kWh, voltage, temp, secsSinceReport);
	}

	public float getTemp() {
		return temp;
	}

	public DataGeneration withTemp(float temp) {
		return new DataGeneration(meterId, title, latitude, longitude, kWh, voltage, temp, secsSinceReport);
	}

	public int getSecsSinceReport() {
		return secsSinceReport;
	}

	public DataGeneration withSecsSinceReport(int secsSinceReport) {
		return new DataGeneration(meterId, title, latitude, longitude, kWh, voltage, temp, secsSinceReport);
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		} else if (other instanceof DataGeneration) {
			final DataGeneration that = (DataGeneration) other;
			return Float.compare(latitude, that.latitude) == 0
					&& Float.compare(longitude, that.longitude) == 0
					&& Float.compare(kWh, that.kWh) == 0
					&& Float.compare(voltage, that.voltage) == 0
					&& Float.compare(temp, that.temp) == 0
					&& secsSinceReport == that.secsSinceReport
					&& Objects.equals(meterId, that.meterId)
					&& Objects.equals(title, that.title);
			
		}
		return false;
	}
	
	@Override 
	public int hashCode() {
		return Objects.hash(meterId, title, latitude, longitude, kWh, voltage, temp, secsSinceReport);
	}
	
	@Override
	public String toString() {
		return "DataGeneration{"
				+ "meterId='" + meterId + '\''
				+ ", title='" + title + '\''
				+ ", latitude='" + latitude + '\''
				+ ", longitude='" + longitude + '\''
				+ ", kWh='" + kWh + '\''
				+ ", voltage='" + voltage + '\''
				+ ", temp='" + temp + '\''
				+ ", secsSinceReport='" + secsSinceReport + '\''
				+'}';
	}
	
	public Value toValue() {
		return Form.forClass(DataGeneration.class).mold(this).toValue();
		// toValue puts into Recon form
		// Example output =>
		// Record.of(Attr.of("DataGeneration"), Slot.of("meterId", "01"), Slot.of("title", "Villa_1"), Slot.of("latitude", 21.83191f), Slot.of("longitude", 21.83191f), Slot.of("kWh", 456.5497f), Slot.of("voltage", 17.21616f), Slot.of("temp", 61.46204f), Slot.of("secsSinceReport", -1))
	}
	
	@Kind
	private static Form<DataGeneration> form;
}
