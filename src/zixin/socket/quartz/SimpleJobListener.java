package zixin.socket.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJobListener implements JobListener {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public String getName() {
		return getClass().getName() + "@" + System.identityHashCode(this);
	}

	public void jobToBeExecuted(JobExecutionContext context) {
		this.logger.debug("jobToBeExecuted context={}", context);
	}

	public void jobExecutionVetoed(JobExecutionContext context) {
		this.logger.debug("jobExecutionVetoed context={}", context);
	}

	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		this.logger.debug("jobWasExecuted context={}, jobException={}", context, jobException);
	}

	public String toString() {
		return getName();
	}
}