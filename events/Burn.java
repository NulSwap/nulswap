package events;
import io.nuls.contract.sdk.Address;
import io.nuls.contract.sdk.annotation.Required;

import java.math.BigInteger;
import io.nuls.contract.sdk.Event;

public class Burn implements Event{

    private Address sender;

    private BigInteger amount0;

    private BigInteger amount1;

    private Address to;

    public Burn(@Required Address sender, @Required BigInteger amount0, @Required BigInteger amount1, @Required Address to) {
        this.sender = sender;
        this.amount0 = amount0;
        this.amount1 = amount1;
        this.to = to;
    }

    public Address getSpender() {
        return sender;
    }

    public void setSpender(Address sender) {
        this.sender = sender;
    }

    public BigInteger getAmount0() {
        return amount0;
    }

    public void setAmount0(BigInteger amount0) {
        this.amount0 = amount0;
    }

    public BigInteger getAmount1() {
        return amount1;
    }

    public void setAmount1(BigInteger amount1) {
        this.amount1 = amount1;
    }

    public Address getTo() {
        return to;
    }

    public void setTo(Address to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Burn that = (Burn) o;

        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        if (amount0 != null ? !amount0.equals(that.amount0) : that.amount0 != null) return false;
        if (amount1 != null ? !amount1.equals(that.amount1) : that.amount1 != null) return false;
        return to != null ? !to.equals(that.to) : that.to != null;
    }

    @Override
    public int hashCode() {
        int result = sender != null ? sender.hashCode() : 0;
        result = 31 * result + (amount0 != null ? amount0.hashCode() : 0);
        result = 31 * result + (amount1 != null ? amount1.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransferEvent{" +
                "from=" + sender +
                "amount0=" + amount0 +
                "amount1=" + amount1 +
                ", to=" + to +
                '}';
    }
}
