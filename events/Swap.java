package events;

import io.nuls.contract.sdk.Address;
import io.nuls.contract.sdk.Event;
import io.nuls.contract.sdk.annotation.Required;

import java.math.BigInteger;

public class Swap implements Event{

    private Address sender;

    private BigInteger amount0In;

    private BigInteger amount1In;

    private BigInteger amount0Out;

    private BigInteger amount1Out;

    private Address to;

    public Swap(@Required Address sender, @Required BigInteger amount0In, @Required BigInteger amount1In, @Required BigInteger amount0Out, @Required BigInteger amount1Out, @Required Address to) {
        this.sender = sender;
        this.amount0In = amount0In;
        this.amount1In = amount1In;
        this.amount0Out = amount0Out;
        this.amount1Out = amount1Out;
        this.to = to;
    }

    public Address getSender() {
        return sender;
    }

    public void setSender(Address sender) {
        this.sender = sender;
    }

    public BigInteger getAmount0In() {
        return amount0In;
    }

    public void setAmount0In(BigInteger amount0) {
        this.amount0In = amount0In;
    }

    public BigInteger getAmount1In() {
        return amount1In;
    }

    public void setAmount1In(BigInteger amount1In) {
        this.amount1In = amount1In;
    }

    public BigInteger getAmount0Out() {
        return amount0Out;
    }

    public void setAmount0Out(BigInteger amount0Out) {
        this.amount0Out = amount0Out;
    }

    public BigInteger getAmount1Out() {
        return amount1Out;
    }

    public void setAmount1Out(BigInteger amount1Out) {
        this.amount1Out = amount1Out;
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

        Swap that = (Swap) o;

        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        if (amount0In != null ? !amount0In.equals(that.amount0In) : that.amount0In != null) return false;
        if (amount1In != null ? !amount1In.equals(that.amount1In) : that.amount1In != null) return false;
        if (amount0Out != null ? !amount0Out.equals(that.amount0Out) : that.amount0Out != null) return false;
        if (amount1Out != null ? !amount1Out.equals(that.amount1Out) : that.amount1Out != null) return false;
        return to != null ? !to.equals(that.to) : that.to != null;
    }

    @Override
    public int hashCode() {
        int result = sender != null ? sender.hashCode() : 0;
        result = 31 * result + (amount0In != null ? amount0In.hashCode() : 0);
        result = 31 * result + (amount1In != null ? amount1In.hashCode() : 0);
        result = 31 * result + (amount0Out != null ? amount0Out.hashCode() : 0);
        result = 31 * result + (amount1Out != null ? amount1Out.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransferEvent{" +
                "from=" + sender +
                "amount0In=" + amount0In +
                "amount1In=" + amount1In +
                "amount0Out=" + amount0Out +
                "amount1Out=" + amount1Out +
                ", to=" + to +
                '}';
    }
}

