package zixin.socket.quartz;


public class SchedulerRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private SchedulerRuntimeException schedulerException;

	public SchedulerRuntimeException getSchedulerException() {
		return this.schedulerException;
	}

	public SchedulerRuntimeException(SchedulerRuntimeException schedulerException) {
		this.schedulerException = schedulerException;
	}

	public SchedulerRuntimeException() {
	}

	public SchedulerRuntimeException(String message) {
		super(message);
	}

	public SchedulerRuntimeException(Throwable cause) {
		super(cause);
	}

	public SchedulerRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public String toString() {
		if (this.schedulerException != null) {
			return this.schedulerException.toString();
		}
		return super.toString();
	}
}