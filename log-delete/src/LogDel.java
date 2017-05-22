import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.naming.ConfigurationException;
import org.apache.log4j.*;

public class LogDel extends TimerTask {
	static Logger logger = Logger.getLogger(LogDel.class);

	private String[] delPathList = new String[3];
	private int delPeriod;
	private String delName;

	public LogDel() {
	};

	public LogDel(String[] delInfo) throws ConfigurationException {
		Config conf = new Configuration();

		String delPath = conf.getString("del.path");
		delPathList = delPath.split(",");
		logger.debug("삭제 대상 경로" + delPathList[0]);
		logger.debug("삭제 대상 경로" + delPathList[1]);
		logger.debug("삭제 대상 경로" + delPathList[2]);

		delPeriod = Integer.parseInt(delInfo[1]);
		logger.debug("로그 삭제 주기.." + delPeriod + "일");
		delName = delInfo[0];
		logger.debug("로그 삭제 키워드.." + delName);
	}

	@Override
	public void run() {
		for (int p = 0; p < delPathList.length; p++) { // config 에 등록된 경로의 파일 삭제

			logger.info(delPathList[p] + "로그폴더 삭제할 파일 탐색 시작============");
			File[] file = new File(delPathList[p].toString()).listFiles();
			
			Calendar fileCal = Calendar.getInstance();
			long todayMil = fileCal.getTimeInMillis();
			long dayMil = 	24*60 *60 * 1000;// 평균 하루 기준
			Date fileDate;
//			logger.info("파일갯수..." + file.length);
//			logger.info("=======================");
			ArrayList<String> delList = new ArrayList<>();
				for (int i = 0; i < file.length; i++) {
						fileDate = new Date(file[i].lastModified());
						fileCal.setTime(fileDate);
						long diffMil = todayMil - fileCal.getTimeInMillis();
						long diffday =  diffMil / dayMil;
						
						try {
							if (diffday >= delPeriod && file[i].exists() && file[i].getName().contains(delName)
									&& file[i].getName().contains("log")) {
								delList.add(file[i].getName());
								file[i].delete();
								logger.info(delPathList[p] + "경로의" + file[i].getName() + "로그를 삭제했습니다.");
							} 
						} catch (Exception e) {
							e.printStackTrace();
							logger.error(delPathList[p] + "경로의" + file[i].getName() + "로그 삭제에 실패했습니다.");
						}
				}
				if(delList.size() >0){
			logger.info(delPathList[p] + "경로의" + "삭제된 로그 갯수 : " + delList.size());
				}else{
					logger.info("삭제할 파일이 존재하지 않습니다.");
				}
				
				
			logger.info(delPathList[p] + " 삭제할 파일 탐색 완료============================"); 
			System.out.println("");
		}
	}

	public String[] restart() {
		LogDel logdel = new LogDel();
		String[] delInfo = new String[2];
		Scanner scanner = new Scanner(System.in);
		try {
			logger.info("컴색 키워드, 삭제 주기를 순서대로 입력하세요");
			delInfo[0] = scanner.next();
			delInfo[1] = Integer.toString(scanner.nextInt());
			if (Integer.parseInt(delInfo[1]) < 1) {
				logger.warn("삭제주기는 1일 이상으로 일단위로만 입력 가능합니다.");
				delInfo = logdel.restart();
			}
			logger.info("키워드.." + delInfo[0] + "주기.." + delInfo[1]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.warn("삭제주기를 숫자로 입력하세요");
			delInfo = logdel.restart();
		} catch (ArrayIndexOutOfBoundsException ae) {
			logger.warn("삭제주기를 입력하세요");
			delInfo = logdel.restart();
		} catch (InputMismatchException ie) {
			logger.warn("키워드와 삭제주기 순서를 바르게 입력하세요");
			delInfo = logdel.restart();
		}
		return delInfo;
	}

	public static void main(String[] args) throws ConfigurationException {
		LogDel logdel = new LogDel();
		Config conf = new Configuration();
		long excutePeriod = conf.getLong("excute.period");
		String[] delInfo = new String[2];
		if (args.length == 0) {
			delInfo = logdel.restart();
		} else {
			long period = (long) Integer.parseInt(args[1]);// 삭제주기
			delInfo[0] = args[0];
			delInfo[1] = Long.toString(period);
			if (period < 1) {
				logger.warn("삭제주기는 1일 이상으로 일단위로만 입력 가능합니다.");
				delInfo = logdel.restart();
			}
		}
		Timer timer = new Timer();
		timer.schedule(new LogDel(delInfo), 2000, excutePeriod);
	}
}

