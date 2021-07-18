package libraries;
import io.nuls.contract.sdk.Address;
import io.nuls.contract.sdk.Contract;
import io.nuls.contract.sdk.annotation.Required;
import io.nuls.contract.sdk.annotation.View;
import io.nuls.contract.sdk.Msg;
import events.*;
import libraries.IUniswapV2ERC20;

import static io.nuls.contract.sdk.Utils.emit;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import static io.nuls.contract.sdk.Utils.require;
public interface IUniswapV2ERC20{



    String name() ;
    String symbol() ;
    int decimals() ;
    BigInteger totalSupply();
    BigInteger balanceOf(Address owner);
    BigInteger allowance(Address owner, Address spender) ;

    boolean approve(Address spender, BigInteger value);
    boolean transfer(Address to, BigInteger value);
    boolean transferFrom(Address from, Address to, BigInteger value) ;

    byte[] DOMAIN_SEPARATOR();
    byte[] PERMIT_TYPEHASH();
    BigInteger nonces(Address owner);

    void permit(Address owner, Address spender, BigInteger value, BigInteger deadline, int v, byte[] r, byte[] s);


}
