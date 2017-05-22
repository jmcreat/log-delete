import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import javax.naming.ConfigurationException;


public class Configuration extends AbstractConfig{

	private String configFileName;
	
	public Configuration() throws ConfigurationException{
		initialize();
	}
	
	private void initialize() throws ConfigurationException{
		
		java.net.URL url= ClassLoader.getSystemResource("log-delete.properties");

		if(url == null){
//		File defaultFile = new File(System.getProperty("user.home"),"log-delete.properties");
		File defaultFile = new File("/usr/share/tomcat7/log-delete.properties");
		configFileName = System.getProperty("log-delete.config.file", defaultFile.getAbsolutePath());
		} else {
			File fileName = new File(url.getFile());
			configFileName = fileName.getAbsolutePath();
		}
		
		try{
			File configFile = new File(configFileName);
			if(! configFile.canRead()){
				throw new ConfigurationException( "Can't open configuration file: " + configFileName );
			}
			props = new java.util.Properties();
			FileInputStream fin = new FileInputStream(configFile);
			props.load(new BufferedInputStream(fin));
			
		}catch (Exception e){
			 throw new ConfigurationException("Can't load configuration file: "+configFileName);
		}
	}
}
