import events.*;
import libraries.*;
import io.nuls.contract.sdk.Address;
import io.nuls.contract.sdk.Contract;
import io.nuls.contract.sdk.Msg;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static io.nuls.contract.sdk.Utils.*;

public class UniswapV2Factory implements IUniswapV2Factory {

        public Address feeTo;
        public Address feeToSetter;

        public Map<Address, Map<Address, Address>> getPair;
        public Address[] allPairs;
        public int nPairs = 0;

        public UniswapV2Factory(Address _feeToSetter){
            feeToSetter = _feeToSetter;
        }

        public BigInteger allPairsLength() {
            return BigInteger.valueOf(allPairs.length);
        }

        public Address createPair(Address tokenA, Address tokenB){
            require(tokenA != tokenB, "UniswapV2: IDENTICAL_ADDRESSES");
            Address token0, token1;
             if(tokenA.hashCode() < tokenB.hashCode() ){
                 token0 = tokenA;
                 token1 = tokenB;
             }else{
                 token0 = tokenB;
                 token1 = tokenA;
             }
            require(!token0.equals(0), "UniswapV2: ZERO_ADDRESS");
            require(getPair.get(token0).get(token1).equals(null), "UniswapV2: PAIR_EXISTS"); // single check is sufficient
            //byte[] bytecode = sha3(new UniswapV2Pair().toString().getBytes());
            byte[] tw1 = sha3(token0.toString()).getBytes();
            byte[] tw2 = sha3(token1.toString()).getBytes();
            byte[] pair = new byte[tw1.length + tw2.length];
            System.arraycopy(tw1, 0, pair, 0, tw1.length);
            System.arraycopy(tw2, 0, pair, tw1.length, tw2.length);

           // new UniswapV2Pair(pair).initialize(token0, token1);
            Map<Address, Address> address1Allowed = getPair.get(token0);
            Address pairAddress = new Address(pair.toString());
            if (address1Allowed == null) {
                address1Allowed = new HashMap<Address, Address>();
                getPair.put(token0, address1Allowed);
            }
            address1Allowed.put(token1, pairAddress);
            Map<Address, Address> address2Allowed = getPair.get(token1);
            if (address2Allowed == null) {
                address2Allowed = new HashMap<Address, Address>();
                getPair.put(token1, address2Allowed);
            }
            address2Allowed.put(token0, pairAddress);
             // populate mapping in the reverse direction
            allPairs[nPairs++] = pairAddress;
            emit( new PairCreated(token0, token1, pairAddress, allPairs.length));
            return pairAddress;

        }

        public void setFeeTo(Address _feeTo) {
            require(Msg.sender().equals(feeToSetter), "UniswapV2: FORBIDDEN");
            feeTo = _feeTo;
        }

    public Address feeTo() {
        return feeTo;
    }

    public Address allPairs(int v){
            return allPairs[v];
    }

    public Address getPair(Address a1, Address a2) {
        return getPair(a1, a2);
    }

    public Address feeToSetter() {
        return feeToSetter;
    }

    public void setFeeToSetter(Address _feeToSetter){
        require(Msg.sender().equals(feeToSetter), "UniswapV2: FORBIDDEN");
        feeToSetter = _feeToSetter;
    }
}
