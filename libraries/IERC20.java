package libraries;

import java.math.BigInteger;
import io.nuls.contract.sdk.Address;

public interface IERC20 {

    public String name();
    public String symbol();
    public int decimals();
    public BigInteger totalSupply();
    public BigInteger balanceOf(Address owner);
    public BigInteger allowance(Address owner, Address spender);

    boolean approve(Address spender, BigInteger value);
    boolean transfer(Address to, BigInteger value) ;
    boolean transferFrom(Address from, Address to, BigInteger value);


}

