import java.util.Properties;

public abstract class AbstractConfig implements Config{ 
	
	/**
	* 모든 속성값 저장
	**/
	protected Properties props = null;
	public AbstractConfig(){

	}//생성자

	@Override
	public Properties getPropertiese() {
		return props;
	}

	@Override
	public String getString(String key) {
		String value = null;
		value = props.getProperty(key);
		
		if(value==null) throw new IllegalArgumentException("illegal String key" + key);
		
		return value;
		
	}

	@Override
	public int getInt(String key) {

		
		int value = 0;
		try{
			
			value =Integer.parseInt(props.getProperty(key));
		} catch(Exception e){
			throw new IllegalArgumentException("illegal int key" + key);
		}
		
		return value;
	}
	
	

	@Override
	public long getLong(String key) {
		// TODO Auto-generated method stub
		
		long value = 0;
		try{
			value = Long.parseLong(props.getProperty(key));
			
		}catch(Exception e){
			throw new IllegalArgumentException("illegal  key" + key);
		}
		
		return value;
	}

	@Override
	public double getDouble(String key) {

		double value = 0.0;
		
		try {
			value = Double.valueOf(props.getProperty(key)).doubleValue();
		} catch(Exception ed){
			throw new IllegalArgumentException("illegal double key" + key);
		}
		
		return value;
	}

	@Override
	public boolean getBoolean(String key) {
		
		
		boolean value = false;
		try{
			value = Boolean.valueOf(props.getProperty(key)).booleanValue();
			
		}catch(Exception e){
			throw new IllegalArgumentException("illegal boolean key" + key);
		}
		
		return value;
	}
	

	
	
}