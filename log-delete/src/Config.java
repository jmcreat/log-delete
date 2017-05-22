public interface Config {

	public java.util.Properties getPropertiese();
	public String getString(String key);
	public int getInt(String key);
	public long getLong (String key);
	public double getDouble(String key);
	public boolean getBoolean(String key);
}
