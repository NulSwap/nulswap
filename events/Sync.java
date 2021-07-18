package events;

import io.nuls.contract.sdk.Address;
import io.nuls.contract.sdk.Event;
import io.nuls.contract.sdk.annotation.Required;

import java.math.BigInteger;



public class Sync implements Event {


    private BigInteger reserve0;

    private BigInteger reserve1;


    public Sync(@Required BigInteger _reserve0, @Required BigInteger _reserve1) {
        this.reserve0 = _reserve0;
        this.reserve1 = _reserve1;
    }

    public BigInteger getReserve0() {
        return reserve0;
    }




    public BigInteger getReserve1() {
        return reserve1;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sync that = (Sync) o;

        if (reserve0 != null ? !reserve0.equals(that.reserve0) : that.reserve0 != null) return false;
        return reserve1 != null ? !reserve1.equals(that.reserve1) : that.reserve1 != null;
    }

    @Override
    public int hashCode() {
        int result = reserve0 != null ? reserve0.hashCode() : 0;
        result = 31 * result + (reserve1 != null ? reserve1.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Sync{" +
                "reserve0=" + reserve0 +
                ", reserve1=" + reserve1 +
                '}';
    }
}

