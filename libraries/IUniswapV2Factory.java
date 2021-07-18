package libraries;
import java.math.BigInteger;

import io.nuls.contract.sdk.Address;

public interface IUniswapV2Factory {


    Address feeTo() ;
    Address feeToSetter() ;

    Address getPair(Address tokenA, Address tokenB) ;
    Address allPairs(int v) ;
    BigInteger allPairsLength();

    Address createPair(Address tokenA, Address tokenB);

    void setFeeTo(Address v);
    void setFeeToSetter(Address v);



}
