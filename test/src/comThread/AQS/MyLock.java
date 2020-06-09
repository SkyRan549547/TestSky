package comThread.AQS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyLock implements Lock {

	private Sync syn = new Sync();

	private class Sync extends AbstractQueuedSynchronizer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected boolean tryAcquire(int arg) {
			int status = getState();
			Thread t = Thread.currentThread();
			if (status == 0) {
				if (compareAndSetState(0, 1)) {
					setExclusiveOwnerThread(t);
					return true;
				}
			} else if (t == getExclusiveOwnerThread()) {
				setState(status + 1);
				return true;
			}
			return false;
		}

		@Override
		protected boolean tryRelease(int arg) {
			Thread t = Thread.currentThread();
			if (getExclusiveOwnerThread() != t) {
				throw new RuntimeException();
			}
			int status = getState();
			int restate = status - arg;
			boolean flag = false;
			if (restate == 0) {
				setExclusiveOwnerThread(null);
				flag = true;
			}
			setState(restate);
			return flag;
		}

		public Condition newCOndition() {
			return new ConditionObject();
		}

	}

	public void lock() {
		syn.acquire(1);

	}

	public void lockInterruptibly() throws InterruptedException {
		syn.acquireInterruptibly(1);

	}

	public Condition newCondition() {

		return syn.newCOndition();
	}

	public boolean tryLock() {

		return syn.tryAcquire(1);
	}

	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {

		return syn.tryAcquireNanos(1, unit.toNanos(time));
	}

	public void unlock() {
		syn.release(1);

	}

}
