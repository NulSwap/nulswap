package events;

import io.nuls.contract.sdk.Event;
import io.nuls.contract.sdk.Address;

import java.math.BigInteger;

import static io.nuls.contract.sdk.Utils.require;
import io.nuls.contract.sdk.annotation.Required;

public class Transfer implements Event {

    private Address from;

    private Address to;

    private BigInteger value;

    public Transfer(Address from, @Required Address to, @Required BigInteger value) {
        this.from = from;
        this.to = to;
        this.value = value;
    }

    public Address getFrom() {
        return from;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public Address getTo() {
        return to;
    }

    public void setTo(Address to) {
        this.to = to;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transfer that = (Transfer) o;

        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (to != null ? !to.equals(that.to) : that.to != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransferEvent{" +
                "from=" + from +
                ", to=" + to +
                ", value=" + value +
                '}';
    }

}

