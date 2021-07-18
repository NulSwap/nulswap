package libraries;

import java.math.BigInteger;

import io.nuls.contract.sdk.Address;


public interface IUniswapV2Callee {
     void uniswapV2Call(Address sender, BigInteger amount0, BigInteger amount1, byte[] data);
}