package zixin.socket.quartz;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerJob
  implements Job
{
  private static final Logger logger = LoggerFactory.getLogger(LoggerJob.class);

  public void execute(JobExecutionContext jobExecutionContext)
    throws JobExecutionException
  {
	  /*
    logger.debug("JobInstance: {}", jobExecutionContext.getJobInstance());
    logger.debug("JobRunTime: {}", Long.valueOf(jobExecutionContext.getJobRunTime()));
    logger.debug("FireTime: {}", jobExecutionContext.getFireTime());
    logger.debug("NextFireTime: {}", jobExecutionContext.getNextFireTime());
    logger.debug("PreviousFireTime: {}", jobExecutionContext.getPreviousFireTime());
    logger.debug("ScheduledFireTime: {}", jobExecutionContext.getScheduledFireTime());
    logger.debug("RefireCount: {}", Integer.valueOf(jobExecutionContext.getRefireCount()));
    logger.debug("Recovering: {}", Boolean.valueOf(jobExecutionContext.isRecovering()));
    logger.debug("Calendar: {}", jobExecutionContext.getCalendar());
    logger.debug("FireInstanceId: {}", jobExecutionContext.getFireInstanceId());
    logger.debug("Scheduler: {}", jobExecutionContext.getScheduler());
    logger.debug("MergedJobDataMap: {}", jobExecutionContext.getMergedJobDataMap().getWrappedMap());
    logger.debug("JobDetail: {}", jobExecutionContext.getJobDetail());
    logger.debug("Trigger: {}", jobExecutionContext.getTrigger());

    logger.info("Job {} has been executed.", jobExecutionContext.getJobDetail().getKey());
    */
	  System.out.println("logger");
  }
}