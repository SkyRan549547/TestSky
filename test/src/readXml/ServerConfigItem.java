package readXml;

public class ServerConfigItem {
	String type;
	String className;
	String name;
	String description;
	String id;
	String servletName;

	public String getServletName() {
		return servletName;
	}

	public String getServletClass() {
		return servletClass;
	}

	public void setServletName(String servletName) {
		this.servletName = servletName;
	}

	public void setServletClass(String servletClass) {
		this.servletClass = servletClass;
	}

	String servletClass;

	public boolean isHttp() {
		return "http".equals(type);
	}

	public boolean isWebservice() {
		return "webservice".equals(type);
	}

	public String getClassName() {
		return className;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}

	public String toString() {
		return "ServerConfigItem [className=" + className + ", description="
				+ description + ", id=" + id + ", name=" + name + ", type="
				+ type + "]";
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(String id) {
		this.id = id;
	}

}
