package backend.wal.reservation.config;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeTypes;

import java.util.HashMap;
import java.util.Map;

public class DelayedMessageExchange extends AbstractExchange implements Exchange {

    private static final String X_DELAYED_MESSAGE;
    private static final boolean DURABLE;
    private static final boolean AUTO_DELETE;
    private static final Map<String, Object> ARGUMENTS = new HashMap<>();

    static {
        X_DELAYED_MESSAGE = "x-delayed-message";
        DURABLE = true;
        AUTO_DELETE = false;
        ARGUMENTS.put("x-delayed-type", ExchangeTypes.DIRECT);
    }

    public DelayedMessageExchange(final String name) {
        super(name, DURABLE, AUTO_DELETE, ARGUMENTS);
    }

    @Override
    public String getType() {
        return X_DELAYED_MESSAGE;
    }
}
