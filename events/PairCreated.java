package events;
import io.nuls.contract.sdk.Address;
import io.nuls.contract.sdk.Event;
import io.nuls.contract.sdk.annotation.Required;

public class PairCreated implements  Event{

    private Address token0;

    private Address token1;

    private Address pair;

    private int length;

    public PairCreated(@Required Address token0, @Required Address token1, @Required Address pair, @Required int length) {
        this.token0 = token0;
        this.token1 = token1;
        this.pair = pair;
        this.length = length;
    }

    public Address getToken0() {
        return token0;
    }

    public void setToken0(Address token0) {
        this.token0 = token0;
    }

    public void setToken1(Address token1) {
        this.token1 = token1;
    }

    public Address getToken1() {
        return token1;
    }

    public Address getPair() {
        return pair;
    }

    public void setPAir(Address pair) {
        this.pair = pair;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PairCreated that = (PairCreated) o;

        if (token0 != null ? !token0.equals(that.token0) : that.token0 != null) return false;
        if (token1 != null ? !token1.equals(that.token1) : that.token1 != null) return false;
        if (pair != null ? !pair.equals(that.pair) : that.pair != null) return false;
        return length != 0 ? length != (that.length) : that.length != 0;
    }

    @Override
    public int hashCode() {
        int result = token0 != null ? token0.hashCode() : 0;
        result = 31 * result + (token1 != null ? token1.hashCode() : 0);
        result = 31 * result + (pair != null ? pair.hashCode() : 0);
        result = 31 * result + (length != 0 ? length : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransferEvent{" +
                "token0=" + token0 +
                "token1=" + token1 +
                "pair=" + pair +
                "length=" + length +
                '}';
    }
}

