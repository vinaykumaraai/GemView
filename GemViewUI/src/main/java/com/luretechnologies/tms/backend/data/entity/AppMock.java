package com.luretechnologies.tms.backend.data.entity;

import java.util.List;
import java.util.Objects;

/**
 * 
 * @author Vinay
 *
 */
public class AppMock extends AbstractEntity {
	private String packageName, description, packageVersion;
	private boolean available;
	private List<AppDefaultParam> appDefaultParamList;
	private Devices device;
	private Profile profile;
	
	private User owner;
	public AppMock() {
		// TODO Auto-generated constructor stub
		super(false);
	}
//Add the device and owner to it App
	public AppMock(String packageName, String description, String packageVersion, boolean available,
			List<AppDefaultParam> appDefaultParamList,Devices device,User owner, Profile profile) {
		super(false);
		Objects.requireNonNull(packageName);
		Objects.requireNonNull(description);
		Objects.requireNonNull(packageVersion);
		Objects.requireNonNull(available);
		Objects.requireNonNull(appDefaultParamList);
		Objects.requireNonNull(device);
		Objects.requireNonNull(owner);
		this.packageName = packageName;
		this.description = description;
		this.packageVersion = packageVersion;
		this.available = available;
		this.appDefaultParamList = appDefaultParamList;
		this.device = device;
		this.owner = owner;
		this.profile = profile;

	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPackageVersion() {
		return packageVersion;
	}

	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public List<AppDefaultParam> getAppDefaultParamList() {
		return appDefaultParamList;
	}

	public void setAppDefaultParamList(List<AppDefaultParam> appDefaultParamList) {
		this.appDefaultParamList = appDefaultParamList;
	}

	public Devices getDevice() {
		return device;
	}
	public void setDevice(Devices device) {
		this.device = device;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (available ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
		result = prime * result + ((packageVersion == null) ? 0 : packageVersion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppMock other = (AppMock) obj;
		if (available != other.available)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (packageName == null) {
			if (other.packageName != null)
				return false;
		} else if (!packageName.equals(other.packageName))
			return false;
		if (packageVersion == null) {
			if (other.packageVersion != null)
				return false;
		} else if (!packageVersion.equals(other.packageVersion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return packageName;
	}

}