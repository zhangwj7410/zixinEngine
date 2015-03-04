package zixin.socket.quartz;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleSchedulerListener implements SchedulerListener {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public void jobScheduled(Trigger trigger) {
		this.logger.debug("jobScheduled {}", trigger);
	}

	public void jobUnscheduled(TriggerKey triggerKey) {
		this.logger.debug("jobUnscheduled {}", triggerKey);
	}

	public void triggerFinalized(Trigger trigger) {
		this.logger.debug("triggerFinalized {}", trigger);
	}

	public void triggerPaused(TriggerKey triggerKey) {
		this.logger.debug("triggersPaused {}", triggerKey);
	}

	public void triggersPaused(String triggerGroup) {
		this.logger.debug("triggersPaused {}", triggerGroup);
	}

	public void triggerResumed(TriggerKey triggerKey) {
		this.logger.debug("triggerResumed {}", triggerKey);
	}

	public void triggersResumed(String triggerGroup) {
		this.logger.debug("triggersResumed {}", triggerGroup);
	}

	public void jobAdded(JobDetail jobDetail) {
		this.logger.debug("jobAdded {}", jobDetail);
	}

	public void jobDeleted(JobKey jobKey) {
		this.logger.debug("jobDeleted {}", jobKey);
	}

	public void jobPaused(JobKey jobKey) {
		this.logger.debug("jobsPaused {}", jobKey);
	}

	public void jobsPaused(String jobGroup) {
		this.logger.debug("jobsPaused {}", jobGroup);
	}

	public void jobResumed(JobKey jobKey) {
		this.logger.debug("jobsResumed {}", jobKey);
	}

	public void jobsResumed(String jobGroup) {
		this.logger.debug("jobsResumed {}", jobGroup);
	}

	public void schedulerError(String msg, SchedulerException cause) {
		this.logger.debug("schedulerError msg={}, cause={}", msg, cause);
	}

	public void schedulerInStandbyMode() {
		this.logger.debug("schedulerInStandbyMode");
	}

	public void schedulerStarted() {
		this.logger.debug("schedulerStarted");
	}

	public void schedulerStarting() {
		this.logger.debug("schedulerStarting");
	}

	public void schedulerShutdown() {
		this.logger.debug("schedulerShutdown");
	}

	public void schedulerShuttingdown() {
		this.logger.debug("schedulerShuttingdown");
	}

	public void schedulingDataCleared() {
		this.logger.debug("schedulingDataCleared.");
	}

	public String toString() {
		return getClass().getName() + "@" + System.identityHashCode(this);
	}
}