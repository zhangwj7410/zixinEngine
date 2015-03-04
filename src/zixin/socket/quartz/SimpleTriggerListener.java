package zixin.socket.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTriggerListener implements TriggerListener {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public String getName() {
		return getClass().getName() + "@" + System.identityHashCode(this);
	}

	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		this.logger.debug("triggerFired trigger={}, context={}", trigger, context);
	}

	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		this.logger.debug("vetoJobExecution trigger={}, context={}", trigger, context);
		return false;
	}

	public void triggerMisfired(Trigger trigger) {
		this.logger.debug("triggerMisfired trigger={}", trigger);
	}

	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			Trigger.CompletedExecutionInstruction triggerInstructionCode) {
		this.logger.debug("triggerComplete trigger={}, context={}, triggerInstructionCode={}",
				new Object[] { trigger, context, triggerInstructionCode });
	}

	public String toString() {
		return getName();
	}
}