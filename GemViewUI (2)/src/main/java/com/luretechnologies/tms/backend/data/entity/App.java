package com.luretechnologies.tms.backend.data.entity;

import java.util.Objects;

/**
 * 
 * @author Vinay
 *
 */
public class App extends AbstractEntity {
	private String packageName, file, packageVersion;
	private boolean active;

	public App() {
		// TODO Auto-generated constructor stub
		super(false);
	}

	public App(String packageName, String file, String packageVersion, boolean active) {
		super(false);
		Objects.requireNonNull(packageName);
		Objects.requireNonNull(file);
		Objects.requireNonNull(packageVersion);
		Objects.requireNonNull(active);
		this.packageName = packageName;
		this.file = file;
		this.packageVersion = packageVersion;
		this.active = active;

	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getPackageVersion() {
		return packageVersion;
	}

	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((file == null) ? 0 : file.hashCode());
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
		App other = (App) obj;
		if (active != other.active)
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
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
		return "App [packageName=" + packageName + ", file=" + file + ", version=" + packageVersion + ", active="
				+ active + "]";
	}

}