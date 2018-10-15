package functionalj.ref;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

import lombok.val;

@FunctionalInterface
public interface RetainChecker {
	
	public static final RetainChecker forever = new Forever();
	public static final RetainChecker never   = new Never();
	
	public boolean stillValid();
	
	
	//== Sub classes ==
	
	public static class Forever implements RetainChecker {
		@Override
		public boolean stillValid() {
			return true;
		}
		@Override
		public String toString() {
			return "FOREVER";
		}
	}
	
	public static class Never implements RetainChecker {
		@Override
		public boolean stillValid() {
			return false;
		}
		@Override
		public String toString() {
			return "NEVER";
		}
	}
	
	// Time relative
	// Time relative with different first time period
	// Time absolute
	// Equals value from supplier
	// Equals value from ref
	
	public static class RefBoolean implements RetainChecker {
		
		private final Ref<Boolean> ref;
		
		public RefBoolean(Ref<Boolean> ref) {
			this.ref = (ref != null) ? ref : Ref.ofValue(false);
		}
		
		@Override
		public boolean stillValid() {
			return ref.value();
		}
		
	}
	
	public static class SuppliedValueCheck<STATE> implements RetainChecker {
		
		private final AtomicReference<STATE>    state = new AtomicReference<STATE>();
		private final Supplier<STATE>           stateSupplier;
		private final BiPredicate<STATE, STATE> changeCheck;
		private final StateUpdater<STATE>       stateUpdater;
		
		public SuppliedValueCheck(
				Supplier<STATE>           initialStateSupplier, 
				Supplier<STATE>           stateSupplier, 
				BiPredicate<STATE, STATE> changeCheck,
				StateUpdater<STATE>       stateUpdater) {
			val initialState
					= (initialStateSupplier != null)
					? initialStateSupplier.get()
					: stateSupplier.get();
			this.state.set(initialState);
			this.stateSupplier = requireNonNull(stateSupplier);
			this.changeCheck   = (changeCheck  != null) ? changeCheck  : new WhenNotEqual<STATE>();
			this.stateUpdater  = (stateUpdater != null) ? stateUpdater : new UpdateOnChanged<STATE>();
		}
		
		@Override
		public final boolean stillValid() {
			val newValue  = stateSupplier.get();
			val oldState  = state.get();
			val isChanged = isChanged(newValue, oldState);
			val newState  = newState(isChanged, oldState, newValue);
			state.compareAndSet(oldState, newState);
			return !isChanged;
		}
		
		protected boolean isChanged(STATE newState, STATE oldState) {
			return changeCheck.test(oldState, newState);
		}
		protected STATE newState(boolean isChanged, STATE oldState, STATE newValue) {
			return stateUpdater.getNewState(isChanged, oldState, newValue);
		}

		@Override
		public String toString() {
			return "SuppliedValueCheck ["
					+ "state="         + state         + ", "
					+ "stateSupplier=" + stateSupplier + ", "
					+ "changeCheck="   + changeCheck   + ", "
					+ "stateUpdater="  + stateUpdater
					+ "]";
		}
		
		//== Aux classes ==
		
		public static class WhenNotSame<S> implements BiPredicate<S, S> {
			@Override
			public boolean test(S oldState, S newState) {
				return oldState != newState;
			}
			@Override
			public String toString() {
				return "WHEN_NOT_SAME";
			}
		}
		
		public static class WhenNotEqual<S> implements BiPredicate<S, S> {
			@Override
			public boolean test(S oldState, S newState) {
				return !Objects.equals(oldState, newState);
			}
			@Override
			public String toString() {
				return "WHEN_NOT_EQUAL";
			}
		}
		
		@FunctionalInterface
		public static interface StateUpdater<S> {
			public S getNewState(boolean isChanged, S oldState, S newValue);
		}
		
		public static class UpdateOnChanged<S> implements StateUpdater<S> {
			@Override
			public S getNewState(boolean isChanged, S oldState, S newValue) {
				return isChanged ? newValue : oldState;
			}
			@Override
			public String toString() {
				return "WHEN_CHANGE";
			}
		}
		public static class UpdateAlways<S> implements StateUpdater<S> {
			@Override
			public S getNewState(boolean isChanged, S oldState, S newValue) {
				return newValue;
			}
			@Override
			public String toString() {
				return "ALWAYS";
			}
		}
		public static class UpdateNever<S> implements StateUpdater<S> {
			@Override
			public S getNewState(boolean isChanged, S oldState, S newValue) {
				return oldState;
			}
			@Override
			public String toString() {
				return "NEVER";
			}
		}
		
	}
	
}
