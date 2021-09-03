package friarLib2.hid;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public class LambdaTrigger extends Trigger {

    TriggerCondition condition;

    public LambdaTrigger (TriggerCondition condition) {
        this.condition = condition;
    }

    @Override
    public boolean get () {
        return condition.get();
    }

    public interface TriggerCondition {
        boolean get();
    }
}
