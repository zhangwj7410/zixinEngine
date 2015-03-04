package zixin.socket.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.quartz.Calendar;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.JobFactory;
import org.quartz.spi.MutableTrigger;
import org.quartz.spi.OperableTrigger;

public class SchedulerTemplate {
	private Scheduler scheduler;

	public SchedulerTemplate() {
		try {
			this.scheduler = StdSchedulerFactory.getDefaultScheduler();
		} catch (Exception e) {
			throw new SchedulerRuntimeException("Failed to create scheduler.", e);
		}
	}

	public SchedulerTemplate(String quartzConfigFilename) {
		try {
			StdSchedulerFactory factory = new StdSchedulerFactory(quartzConfigFilename);
			this.scheduler = factory.getScheduler();
		} catch (Exception e) {
			throw new SchedulerRuntimeException("Failed to create scheduler using config file: "
					+ quartzConfigFilename, e);
		}
	}

	public SchedulerTemplate(Properties props) {
		try {
			StdSchedulerFactory factory = new StdSchedulerFactory(props);
			this.scheduler = factory.getScheduler();
		} catch (Exception e) {
			throw new SchedulerRuntimeException(
					"Failed to create scheduler using custom properties.", e);
		}
	}

	public SchedulerTemplate(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public Scheduler getScheduler() {
		return this.scheduler;
	}

	public void addCalendar(String calName, Calendar calendar, boolean replace,
			boolean updateTriggers) {
		try {
			this.scheduler.addCalendar(calName, calendar, replace, updateTriggers);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public boolean checkExists(JobKey jobKey) {
		try {
			return this.scheduler.checkExists(jobKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public boolean checkExists(TriggerKey triggerKey) {
		try {
			return this.scheduler.checkExists(triggerKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void clear() {
		try {
			this.scheduler.clear();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public Calendar getCalendar(String calendarName) {
		try {
			return this.scheduler.getCalendar(calendarName);
		} catch (Exception e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public boolean deleteCalendar(String calendarName) {
		try {
			return this.scheduler.deleteCalendar(calendarName);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public boolean deleteJob(JobKey jobKey) {
		try {
			return this.scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public boolean deleteJobs(List<JobKey> jobKeys) {
		try {
			return this.scheduler.deleteJobs(jobKeys);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public SchedulerContext getContext() {
		try {
			return this.scheduler.getContext();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public JobDetail getJobDetail(JobKey jobKey) {
		try {
			return this.scheduler.getJobDetail(jobKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public List<String> getJobGroupNames() {
		try {
			return this.scheduler.getJobGroupNames();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public Set<JobKey> getJobKeys(GroupMatcher<JobKey> groupMatcher) {
		try {
			return this.scheduler.getJobKeys(groupMatcher);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public SchedulerMetaData getMetaData() {
		try {
			return this.scheduler.getMetaData();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public Set<String> getPausedTriggerGroups() {
		try {
			return this.scheduler.getPausedTriggerGroups();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public Trigger getTrigger(TriggerKey triggerKey) {
		try {
			return this.scheduler.getTrigger(triggerKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public List<String> getTriggerGroupNames() {
		try {
			return this.scheduler.getTriggerGroupNames();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> groupMatcher) {
		try {
			return this.scheduler.getTriggerKeys(groupMatcher);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public Trigger.TriggerState getTriggerState(TriggerKey triggerKey) {
		try {
			return this.scheduler.getTriggerState(triggerKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public List<? extends Trigger> getTriggersOfJob(JobKey jobKey) {
		try {
			return this.scheduler.getTriggersOfJob(jobKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public boolean interrupt(JobKey jobKey) {
		try {
			return this.scheduler.interrupt(jobKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public boolean interrupt(String fireInstanceId) {
		try {
			return this.scheduler.interrupt(fireInstanceId);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void pauseAll() {
		try {
			this.scheduler.pauseAll();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void pauseJob(JobKey jobKey) {
		try {
			this.scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void pauseJobs(GroupMatcher<JobKey> groupMatcher) {
		try {
			this.scheduler.pauseJobs(groupMatcher);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void pauseTrigger(TriggerKey triggerKey) {
		try {
			this.scheduler.pauseTrigger(triggerKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void pauseTriggers(GroupMatcher<TriggerKey> groupMatcher) {
		try {
			this.scheduler.pauseTriggers(groupMatcher);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public Date rescheduleJob(TriggerKey triggerKey, Trigger trigger) {
		try {
			return this.scheduler.rescheduleJob(triggerKey, trigger);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void resumeAll() {
		try {
			this.scheduler.resumeAll();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void resumeJob(JobKey jobKey) {
		try {
			this.scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void resumeJobs(GroupMatcher<JobKey> groupMatcher) {
		try {
			this.scheduler.resumeJobs(groupMatcher);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void resumeTrigger(TriggerKey triggerKey) {
		try {
			this.scheduler.resumeTrigger(triggerKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void resumeTriggers(GroupMatcher<TriggerKey> groupMatcher) {
		try {
			this.scheduler.resumeTriggers(groupMatcher);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) {
		try {
			this.scheduler.scheduleJobs(triggersAndJobs, replace);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void scheduleJob(JobDetail jobDetail, Set<? extends Trigger> triggers, boolean replace) {
		try {
			this.scheduler.scheduleJob(jobDetail, triggers, replace);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void setJobFactory(JobFactory jobFactory) {
		try {
			this.scheduler.setJobFactory(jobFactory);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void shutdown() {
		try {
			this.scheduler.shutdown();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void shutdown(boolean waitForJobToComplete) {
		try {
			this.scheduler.shutdown(waitForJobToComplete);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void standby() {
		try {
			this.scheduler.standby();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void start() {
		try {
			this.scheduler.start();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void startDelayed(int seconds) {
		try {
			this.scheduler.startDelayed(seconds);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void triggerJob(JobKey jobKey, JobDataMap jobDataMap) {
		try {
			this.scheduler.triggerJob(jobKey, jobDataMap);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void triggerJob(JobKey jobKey) {
		try {
			this.scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public boolean unscheduleJob(TriggerKey triggerKey) {
		try {
			return this.scheduler.unscheduleJob(triggerKey);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public boolean unscheduleJobs(List<TriggerKey> triggerKeys) {
		try {
			return this.scheduler.unscheduleJobs(triggerKeys);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public List<String> getCalendarNames() {
		try {
			return this.scheduler.getCalendarNames();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public boolean isShutdown() {
		try {
			return this.scheduler.isShutdown();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public boolean isInStandbyMode() {
		try {
			return this.scheduler.isInStandbyMode();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public boolean isStarted() {
		try {
			return this.scheduler.isStarted();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public SchedulerMetaData getSchedulerMetaData() {
		try {
			return this.scheduler.getMetaData();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public Date scheduleJob(JobDetail jobDetail, Trigger trigger) {
		try {
			return this.scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void scheduleJob(Trigger trigger) {
		try {
			this.scheduler.scheduleJob(trigger);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public String getSchedulerName() {
		try {
			return this.scheduler.getSchedulerName();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public String getSchedulerInstanceId() {
		try {
			return this.scheduler.getSchedulerInstanceId();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void resumeAllTriggers() {
		try {
			this.scheduler.resumeAll();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public ListenerManager getListenerManager() {
		try {
			return this.scheduler.getListenerManager();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void addJob(JobDetail job, boolean replace) {
		try {
			this.scheduler.addJob(job, replace);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void addJob(JobDetail job, boolean replace,
			boolean storeNonDurableWhileAwaitingScheduling) {
		try {
			this.scheduler.addJob(job, replace, storeNonDurableWhileAwaitingScheduling);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public List<JobExecutionContext> getCurrentlyExecutingJobs() {
		try {
			return this.scheduler.getCurrentlyExecutingJobs();
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void addJobListener(JobListener listener) {
		try {
			this.scheduler.getListenerManager().addJobListener(listener);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void addTriggerListener(TriggerListener listener) {
		try {
			this.scheduler.getListenerManager().addTriggerListener(listener);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void addSchedulerListener(SchedulerListener listener) {
		try {
			this.scheduler.getListenerManager().addSchedulerListener(listener);
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public void startAndShutdown(long waitTimeInMillis) {
		start();
		if (waitTimeInMillis > 0L) {
			synchronized (this) {
				try {
					wait(waitTimeInMillis);
				} catch (InterruptedException e) {
					throw new SchedulerRuntimeException("Failed to wait after scheduler started.",
							e);
				}
			}
		}

		shutdown(true);
	}

	public void startAndWait() {
		startAndWait(0);
	}

	public void startAndWait(int startDelayInSeconds) {
		if (startDelayInSeconds <= 0)
			start();
		else {
			startDelayed(startDelayInSeconds);
		}
		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new SchedulerRuntimeException("Failed to wait after scheduler started.", e);
			}
		}
	}

	public List<JobDetail> getAllJobDetails() {
		try {
			List jobs = new ArrayList();
			List<String> groups = this.scheduler.getJobGroupNames();
			for (String group : groups) {
				Set<JobKey> keys = this.scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group));
				for (JobKey key : keys) {
					JobDetail jobDetail = this.scheduler.getJobDetail(key);
					jobs.add(jobDetail);
				}
			}
			return jobs;
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public List<Trigger> getAllTriggers() {
		try {
			List triggers = new ArrayList();
			List<String> groups = this.scheduler.getTriggerGroupNames();
			for (String group : groups) {
				Set<TriggerKey> keys = this.scheduler.getTriggerKeys(GroupMatcher
						.triggerGroupEquals(group));
				for (TriggerKey key : keys) {
					Trigger trigger = this.scheduler.getTrigger(key);
					triggers.add(trigger);
				}
			}
			return triggers;
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public List<Date> getNextFireTimes(Trigger trigger, Date startTime, int maxCount) {
		OperableTrigger clonedTrigger = (OperableTrigger) ((OperableTrigger) trigger).clone();
		Calendar cal = null;

		if (clonedTrigger.getNextFireTime() == null) {
			clonedTrigger.computeFirstFireTime(cal);
		}

		List list = new ArrayList();
		Date nextDate = startTime;
		int count = 0;
		while (count++ < maxCount) {
			nextDate = clonedTrigger.getFireTimeAfter(nextDate);
			if (nextDate == null)
				break;
			list.add(nextDate);
			clonedTrigger.triggered(cal);
		}
		return list;
	}

	public List<Date> getNextFireTimesWithCalendar(Trigger trigger, Date startTime, int maxCount) {
		List<Date> dates = getNextFireTimes(trigger, startTime, maxCount);
		String calName = trigger.getCalendarName();
		if (calName == null) {
			return dates;
		}

		Calendar cal = getCalendar(calName);
		List result = new ArrayList();
		for (Date dt : dates) {
			if (cal.isTimeIncluded(dt.getTime())) {
				result.add(dt);
			}
		}
		return result;
	}

	public JobDetail updateJobDetail(JobDetail newJobDetail) {
		try {
			JobDetail oldJob = this.scheduler.getJobDetail(newJobDetail.getKey());
			this.scheduler.addJob(newJobDetail, true);
			return oldJob;
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public Trigger updateTrigger(Trigger newTrigger) {
		try {
			Trigger oldTrigger = this.scheduler.getTrigger(newTrigger.getKey());
			this.scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
			return oldTrigger;
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public Trigger uncheduleJob(TriggerKey triggerKey) {
		try {
			Trigger trigger = this.scheduler.getTrigger(triggerKey);
			boolean success = this.scheduler.unscheduleJob(trigger.getKey());
			if (!success)
				throw new SchedulerRuntimeException("Failed to unschedule job with trigger key "
						+ triggerKey);
			return trigger;
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public List<Trigger> getPausedTriggers() {
		try {
			List result = new ArrayList();
			List<String> groups = this.scheduler.getTriggerGroupNames();
			for (String group : groups) {
				Set<TriggerKey> triggerKeys = this.scheduler.getTriggerKeys(GroupMatcher
						.triggerGroupEquals(group));
				for (TriggerKey key : triggerKeys) {
					if (this.scheduler.getTriggerState(key) == Trigger.TriggerState.PAUSED) {
						result.add(this.scheduler.getTrigger(key));
					}
				}
			}
			return result;
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public List<? extends Trigger> deleteJobAndGetTriggers(JobKey key) {
		try {
			List triggers = this.scheduler.getTriggersOfJob(key);
			boolean success = this.scheduler.deleteJob(key);
			if (!success)
				throw new SchedulerRuntimeException("Unable to delete job " + key);
			return triggers;
		} catch (SchedulerException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public Date scheduleCronJob(String name, String cron, Class<? extends Job> jobClass) {
		return scheduleCronJob(JobKey.jobKey(name), cron, jobClass, null, new Date(), null);
	}

	public Date scheduleCronJob(String name, String cron, Class<? extends Job> jobClass,
			Map<String, Object> dataMap) {
		return scheduleCronJob(JobKey.jobKey(name), cron, jobClass, dataMap, new Date(), null);
	}

	public Date scheduleCronJob(String name, String cron, Class<? extends Job> jobClass,
			Map<String, Object> dataMap, Date startTime) {
		return scheduleCronJob(JobKey.jobKey(name), cron, jobClass, dataMap, startTime, null);
	}

	public Date scheduleCronJob(JobKey jobKey, String cron, Class<? extends Job> jobClass,
			Map<String, Object> dataMap, Date startTime, Date endTime) {
		JobDetail job = createJobDetail(jobKey, jobClass, false, dataMap);
		TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
		Trigger trigger = createCronTrigger(triggerKey, cron, startTime, endTime);
		return scheduleJob(job, trigger);
	}

	public Date scheduleSimpleJob(String name, int repeatTotalCount, long repeatInterval,
			Class<? extends Job> jobClass) {
		return scheduleSimpleJob(JobKey.jobKey(name), repeatTotalCount, repeatInterval, jobClass,
				null, null, null);
	}

	public Date scheduleSimpleJob(String name, int repeatTotalCount, long repeatInterval,
			Class<? extends Job> jobClass, Map<String, Object> dataMap) {
		return scheduleSimpleJob(JobKey.jobKey(name), repeatTotalCount, repeatInterval, jobClass,
				dataMap, null, null);
	}

	public Date scheduleSimpleJob(String name, int repeatTotalCount, long repeatInterval,
			Class<? extends Job> jobClass, Map<String, Object> dataMap, Date startTime) {
		return scheduleSimpleJob(JobKey.jobKey(name), repeatTotalCount, repeatInterval, jobClass,
				dataMap, startTime, null);
	}

	public Date scheduleSimpleJob(JobKey jobKey, int repeatTotalCount, long repeatInterval,
			Class<? extends Job> jobClass, Map<String, Object> dataMap, Date startTime, Date endTime) {
		JobDetail job = createJobDetail(jobKey, jobClass, false, dataMap);
		TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
		Trigger trigger = createSimpleTrigger(triggerKey, repeatTotalCount, repeatInterval,
				startTime, endTime);
		return scheduleJob(job, trigger);
	}

	public String getSchedulerNameAndId() {
		String result = null;
		try {
			result = getSchedulerName() + "_$_" + getSchedulerInstanceId();
		} catch (RuntimeException e) {
			result = getScheduler().getClass().getSimpleName() + "@"
					+ System.identityHashCode(getScheduler());
		}
		return result;
	}

	public static JobDetail createJobDetail(String name, Class<? extends Job> jobClass) {
		return createJobDetail(JobKey.jobKey(name), jobClass, false, null);
	}

	public static JobDetail createJobDetail(JobKey jobKey, Class<? extends Job> jobClass,
			boolean durable, Map<String, Object> dataMap) {
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey)
				.storeDurably(durable).build();
		if (dataMap != null)
			jobDetail.getJobDataMap().putAll(dataMap);
		return jobDetail;
	}

	public static MutableTrigger createCronTrigger(String name, String cron) {
		return createCronTrigger(TriggerKey.triggerKey(name), cron, new Date(), null);
	}

	public static MutableTrigger createCronTrigger(String name, String cron, Date startTime) {
		return createCronTrigger(TriggerKey.triggerKey(name), cron, startTime, null);
	}

	public static MutableTrigger createCronTrigger(TriggerKey triggerKey, String cron,
			Date startTime, Date endTime) {
		if (startTime == null)
			startTime = new Date();
		try {
			CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger()
					.withIdentity(triggerKey).startAt(startTime).endAt(endTime)
					.withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();

			return (MutableTrigger) trigger;
		} catch (RuntimeException e) {
			throw new SchedulerRuntimeException(e);
		}
	}

	public static MutableTrigger createSimpleTrigger(String name) {
		return createSimpleTrigger(name, 1, 0);
	}

	public static MutableTrigger createSimpleTrigger(String name, int repeatTotalCount, int interval) {
		return createSimpleTrigger(name, repeatTotalCount, interval, new Date());
	}

	public static MutableTrigger createSimpleTrigger(String name, int repeatTotalCount,
			int interval, Date startTime) {
		return createSimpleTrigger(TriggerKey.triggerKey(name), repeatTotalCount, interval,
				startTime, null);
	}

	public static MutableTrigger createSimpleTrigger(TriggerKey triggerKey, int repeatTotalCount,
			long repeatInterval, Date startTime, Date endTime) {
		if (startTime == null) {
			startTime = new Date();
		}

		int repeatCount = repeatTotalCount - 1;
		if (repeatTotalCount < 0) {
			repeatCount = -1;
		}
		SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder
				.newTrigger()
				.withIdentity(triggerKey)
				.startAt(startTime)
				.endAt(endTime)
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule().withRepeatCount(repeatCount)
								.withIntervalInMilliseconds(repeatInterval)).build();

		return (MutableTrigger) trigger;
	}

	public static Map<String, Object> mkMap(Object[] dataArray) {
		Map map = new HashMap();
		if (dataArray.length % 2 != 0) {
			throw new IllegalArgumentException("Data must come in pair: key and value.");
		}

		for (int i = 0; i < dataArray.length; i++) {
			Object keyObj = dataArray[i];
			if (!(keyObj instanceof String)) {
				throw new IllegalArgumentException("Key must be a String type, but got: "
						+ keyObj.getClass());
			}
			String key = (String) keyObj;
			i++;
			Object value = dataArray[i];
			map.put(key, value);
		}
		return map;
	}

	public String toString() {
		return "QuartzScheduler[" + getSchedulerNameAndId() + "]";
	}
}
