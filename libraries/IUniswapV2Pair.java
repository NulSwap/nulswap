package libraries;
import io.nuls.contract.sdk.Address;
import io.nuls.contract.sdk.Contract;
import io.nuls.contract.sdk.Msg;

import java.math.BigInteger;

import io.nuls.contract.sdk.Block;

import static io.nuls.contract.sdk.Utils.*;
import static io.nuls.contract.sdk.Utils.require;



public interface IUniswapV2Pair {

    BigInteger totalSupply();
    BigInteger balanceOf(Address owner) ;
    BigInteger allowance(Address owner, Address spender);

    boolean approve(Address spender, BigInteger value) ;
    boolean transfer(Address to, BigInteger value);
    boolean transferFrom(Address from, Address to, BigInteger value);

    byte[] DOMAIN_SEPARATOR() ;
    byte[] PERMIT_TYPEHASH() ;
    BigInteger nonces(Address owner);

    void  permit(Address owner, Address spender, BigInteger value, BigInteger deadline, int v, int r, byte[] s);

    BigInteger MINIMUM_LIQUIDITY();
    Address factory() ;
    Address token0() ;
    Address token1() ;
    BigInteger[] getReserves();
    BigInteger price0CumulativeLast() ;
    BigInteger price1CumulativeLast() ;
    BigInteger kLast();

    BigInteger mint(Address to);
    BigInteger[] burn(Address to);
    void swap(BigInteger amount0Out, BigInteger amount1Out, Address to, byte[] data);
    void skim(Address to);
    void sync();

    void initialize(Address a, Address ab);
}
