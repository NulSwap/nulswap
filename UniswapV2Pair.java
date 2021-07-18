import io.nuls.contract.sdk.Address;
import io.nuls.contract.sdk.Contract;
import io.nuls.contract.sdk.Msg;

import java.math.BigInteger;
import events.*;
import io.nuls.contract.sdk.Block;
import libraries.*;

import static io.nuls.contract.sdk.Utils.*;
import static io.nuls.contract.sdk.Utils.require;


public class UniswapV2Pair extends UniswapV2ERC20 implements IUniswapV2Pair{

        public BigInteger MINIMUM_LIQUIDITY = BigInteger.valueOf(10000);
       // private static final byte[]  SELECTOR = sha3("transfer(address,uint256)".getBytes()).getBytes();

        public  Address factory;
        public Address token0;
        public Address token1;
        public BigInteger totalSupply = BigInteger.ZERO;

        private BigInteger reserve0;           // uses single storage slot, accessible via getReserves
        private BigInteger reserve1;           // uses single storage slot, accessible via getReserves
        private BigInteger blockTimestampLast; // uses single storage slot, accessible via getReserves

         public BigInteger price0CumulativeLast;
         public BigInteger  price1CumulativeLast;
         public BigInteger kLast; // reserve0 * reserve1, as of immediately after the most recent liquidity event

        private BigInteger unlocked = BigInteger.valueOf(1);
        private void lock() {
            require(unlocked.compareTo(BigInteger.valueOf(1)) == 0, "UniswapV2: LOCKED");
            unlocked = BigInteger.valueOf(0);

           // unlocked = 1;
        }


        public BigInteger totalSupply(){
            return totalSupply;
        }

        public BigInteger balanceOf(Address a){
            return new UniswapV2ERC20(token0).balanceOf(a);
        }

    public BigInteger balanceOf(Address a, Address b){
        return new UniswapV2ERC20(token0).allowance(a, b);
    }


    public void permit(Address owner, Address spender, BigInteger value, BigInteger deadline, int v, int r, byte[] s) {

    }


    public BigInteger MINIMUM_LIQUIDITY() {
        return null;
    }


    public Address factory() {
        return null;
    }


    public Address token0() {
        return null;
    }


    public Address token1() {
        return null;
    }

    public BigInteger[] getReserves(){
            BigInteger[] g = new BigInteger[3];

            BigInteger _reserve0 = reserve0;
            BigInteger _reserve1 = reserve1;
            BigInteger _blockTimestampLast = blockTimestampLast;
            g[0] = _reserve0;
            g[1] = _reserve1;
            g[2] = _blockTimestampLast;
            return g;
        }


    public BigInteger price0CumulativeLast() {
        return null;
    }


    public BigInteger price1CumulativeLast() {
        return null;
    }


    public BigInteger kLast() {
        return null;
    }

    private void _safeTransfer(Address token, Address to, BigInteger value) {
        /*    boolean success;
        (boolean success, byte[] data) = token.call(abi.encodeWithSelector(SELECTOR, to, value));
        require(success && (data.length == 0 || abi.decode(data, (bool))), "UniswapV2: TRANSFER_FAILED");
*/
        }

        /*
        event Mint(address indexed sender, uint amount0, uint amount1);
        event Burn(address indexed sender, uint amount0, uint amount1, address indexed to);
        event Swap(
        address indexed sender,
        uint amount0In,
        uint amount1In,
        uint amount0Out,
        uint amount1Out,
        address indexed to
        );
        event Sync(uint112 reserve0, uint112 reserve1);
        */

        public UniswapV2Pair(){
            super(new Address(""));
            factory = Msg.sender();
        }

        // called once by the factory at time of deployment
        public void initialize(Address _token0, Address _token1)  {
            require(Msg.sender().equals(factory), "UniswapV2: FORBIDDEN"); // sufficient check
            token0 = _token0;
            token1 = _token1;
        }

        // update reserves and, on the first call per block, price accumulators
        private void _update(BigInteger balance0, BigInteger balance1, BigInteger _reserve0, BigInteger _reserve1)  {
            //ATENTION: VOU meter long no require abaixo mas significa uint112(-1)
            require(balance0.compareTo(BigInteger.valueOf(Long.MAX_VALUE))  <= 0  && balance1.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) <= 0, "UniswapV2: OVERFLOW");

            BigInteger blockTimestamp = BigInteger.valueOf( Block.timestamp()).mod(BigInteger.valueOf((long) Math.pow(2,32)));
            BigInteger timeElapsed = blockTimestamp.subtract(blockTimestampLast); // overflow is desired
            if (timeElapsed.compareTo(BigInteger.ZERO) > 0 && !_reserve0.equals(BigInteger.ZERO) && !_reserve1.equals(0)) {
            // * never overflows, and + overflow is desired
                price0CumulativeLast.add( _reserve1.divide(_reserve0).multiply(timeElapsed));
                price1CumulativeLast.add(_reserve0.divide(_reserve1).multiply(timeElapsed));
            }
            reserve0 = balance0;
            reserve1 = balance1;
            blockTimestampLast = blockTimestamp;
            emit( new Sync(reserve0, reserve1));
        }

        // if fee is on, mint liquidity equivalent to 1/6th of the growth in sqrt(k)
        private boolean _mintFee(BigInteger _reserve0, BigInteger _reserve1)  {
            Address feeTo = new UniswapV2Factory(factory).feeTo();
            boolean feeOn = feeTo != null;
            BigInteger _kLast = kLast; // gas savings
            if (feeOn) {
                if (!_kLast.equals(0)) {
                    BigInteger rootK = this.sqrt(_reserve0.multiply(_reserve1));
                    BigInteger rootKLast = this.sqrt(_kLast);
                    if (rootK.compareTo(rootKLast) > 0) {
                        BigInteger numerator = totalSupply.multiply(rootK.subtract(rootKLast));
                        BigInteger denominator = rootK.multiply(BigInteger.valueOf(5)).add(rootKLast);
                        BigInteger liquidity = numerator.divide(denominator);
                        if (liquidity.compareTo(BigInteger.valueOf(0)) > 0) _mint(feeTo, liquidity);
                    }
                }
            } else if (!_kLast.equals(0)) {
                kLast = BigInteger.valueOf(0);
            }
            return feeOn;
        }



        // this low-level function should be called from a contract which performs important safety checks
        public BigInteger mint(Address to) {
            lock();
            BigInteger[] g = getReserves();
            BigInteger _reserve0 = g[0], _reserve1 = g[1];

            BigInteger balance0 = new UniswapV2ERC20(token0).balanceOf( new Address(this.toString()));
            BigInteger balance1 = new UniswapV2ERC20(token1).balanceOf(new Address(this.toString()));
            BigInteger amount0 = balance0.subtract(_reserve0);
            BigInteger amount1 = balance1.subtract(_reserve1);

            boolean feeOn = _mintFee(_reserve0, _reserve1);
            BigInteger _totalSupply = totalSupply; // gas savings, must be defined here since totalSupply can update in _mintFee
            BigInteger liquidity;
            if (_totalSupply.equals(BigInteger.valueOf(0))) {
                liquidity = this.sqrt(amount0.multiply(amount1)).subtract(MINIMUM_LIQUIDITY);
            this._mint(new Address("0"), MINIMUM_LIQUIDITY); // permanently lock the first MINIMUM_LIQUIDITY tokens
            } else {
                liquidity = amount0.multiply(_totalSupply).divide( _reserve0).min( amount1.multiply(_totalSupply).divide(_reserve1));
            }
            require(liquidity.compareTo(BigInteger.valueOf(0)) > 0, "UniswapV2: INSUFFICIENT_LIQUIDITY_MINTED");
            _mint(to, liquidity);

            _update(balance0, balance1, _reserve0, _reserve1);
            if (feeOn) kLast = reserve0.multiply(reserve1); // reserve0 and reserve1 are up-to-date
            //  emit( new  Mint(Msg.sender(), amount0, amount1));
            return liquidity;
        }

        // this low-level function should be called from a contract which performs important safety checks
        public BigInteger[] burn(Address to) {
            lock();
            BigInteger[] g = getReserves();
            BigInteger _reserve0 = g[0], _reserve1 = g[1];
            Address _token0 = token0;                                // gas savings
            Address _token1 = token1;                                // gas savings
            BigInteger balance0 = new UniswapV2ERC20(_token0).balanceOf(new Address(this.toString()));
            BigInteger balance1 = new UniswapV2ERC20(_token1).balanceOf(new Address(this.toString()));
            BigInteger liquidity = balanceOf.get(new Address(this.toString()));

            boolean feeOn = _mintFee(_reserve0, _reserve1);
            BigInteger _totalSupply = totalSupply; // gas savings, must be defined here since totalSupply can update in _mintFee
            BigInteger amount0 = liquidity.multiply(balance0).divide(_totalSupply); // using balances ensures pro-rata distribution
            BigInteger amount1 = liquidity.multiply(balance1).divide(_totalSupply); // using balances ensures pro-rata distribution
            require(amount0.compareTo(BigInteger.ZERO) > 0 && amount1.compareTo(BigInteger.ZERO) > 0, "UniswapV2: INSUFFICIENT_LIQUIDITY_BURNED");
            _burn(new Address(this.toString()), liquidity);
            _safeTransfer(_token0, to, amount0);
            _safeTransfer(_token1, to, amount1);
            balance0 = new UniswapV2ERC20(_token0).balanceOf(new Address(this.toString()));
            balance1 = new UniswapV2ERC20(_token1).balanceOf(new Address(this.toString()));

            _update(balance0, balance1, _reserve0, _reserve1);
            if (feeOn) kLast = reserve0.multiply(reserve1); // reserve0 and reserve1 are up-to-date
            emit( new Burn(Msg.sender(), amount0, amount1, to));
            BigInteger[] n = new BigInteger[2];
             n[0] = amount0;
             n[1] = amount1;
             return n;

        }

        // this low-level function should be called from a contract which performs important safety checks
        public void swap(BigInteger amount0Out, BigInteger amount1Out, Address to, byte[] data) {
            lock();
            require(amount0Out.compareTo(BigInteger.ZERO) > 0 || amount1Out.compareTo(BigInteger.ZERO) > 0, "UniswapV2: INSUFFICIENT_OUTPUT_AMOUNT");
                BigInteger[] g = getReserves();
                BigInteger _reserve0 = g[0], _reserve1 = g[1];// gas savings
            require(amount0Out.compareTo(_reserve0) < 0 && amount1Out.compareTo(_reserve1) < 0, "UniswapV2: INSUFFICIENT_LIQUIDITY");

            BigInteger balance0;
            BigInteger balance1;
            { // scope for _token{0,1}, avoids stack too deep errors
                Address _token0 = token0;
                Address _token1 = token1;
                require(to != _token0 && to != _token1, "UniswapV2: INVALID_TO");
                if (amount0Out.compareTo(BigInteger.ZERO) > 0) _safeTransfer(_token0, to, amount0Out); // optimistically transfer tokens
                if (amount1Out.compareTo(BigInteger.ZERO) > 0) _safeTransfer(_token1, to, amount1Out); // optimistically transfer tokens
                //if (data.length > 0) new IUniswapV2Callee(to).uniswapV2Call(Msg.sender(), amount0Out, amount1Out, data);
                balance0 = new UniswapV2ERC20(_token0).balanceOf(new Address(this.toString()));
                balance1 =  new UniswapV2ERC20(_token1).balanceOf(new Address(this.toString()));
            }
            BigInteger amount0In = balance0.compareTo(_reserve0.subtract(amount0Out)) > 0 ? balance0.subtract(_reserve0.subtract(amount0Out)) : BigInteger.ZERO;
            BigInteger amount1In = balance1.compareTo(_reserve1.subtract(amount1Out)) > 0 ? balance1.subtract(_reserve1.subtract(amount1Out)) : BigInteger.ZERO;
            require(amount0In.compareTo(BigInteger.ZERO) > 0 || amount1In.compareTo(BigInteger.ZERO) > 0, "UniswapV2: INSUFFICIENT_INPUT_AMOUNT");
            { // scope for reserve{0,1}Adjusted, avoids stack too deep errors
                BigInteger balance0Adjusted = balance0.multiply(BigInteger.valueOf(1000)).subtract(amount0In.multiply(BigInteger.valueOf(3)));
                BigInteger balance1Adjusted = balance1.multiply(BigInteger.valueOf(1000)).subtract(amount1In.multiply(BigInteger.valueOf(3)));
                require(balance0Adjusted.multiply(balance1Adjusted).compareTo(_reserve0.multiply(_reserve1).multiply(BigInteger.valueOf(100000))) > 0, "UniswapV2: K");
            }

            _update(balance0, balance1, _reserve0, _reserve1);
            emit( new Swap(Msg.sender(), amount0In, amount1In, amount0Out, amount1Out, to));
        }

        // force balances to match reserves
        public void skim(Address to){
            lock();
            Address _token0 = token0; // gas savings
            Address _token1 = token1; // gas savings
            _safeTransfer(_token0, to, new UniswapV2ERC20(_token0).balanceOf(new Address(this.toString())).subtract(reserve0));
            _safeTransfer(_token1, to, new UniswapV2ERC20(_token1).balanceOf(new Address(this.toString())).subtract(reserve1));
        }

        // force reserves to match balances
        public void sync(){
            lock();
            _update( new UniswapV2ERC20(token0).balanceOf(new Address(this.toString())),  new UniswapV2ERC20(token1).balanceOf(new Address(this.toString())), reserve0, reserve1);
        }
    public void permit(Address owner, Address spender, BigInteger value, BigInteger deadline, int v, byte[] r, byte[] s) {

    }

    public static BigInteger sqrt(BigInteger x) {
        BigInteger div = BigInteger.ZERO.setBit(x.bitLength() / 2);
        BigInteger div2 = div;
        // Loop until we hit the same value twice in a row, or wind
        // up alternating.
        for (; ; ) {
            BigInteger y = div.add(x.divide(div)).shiftRight(1);
            if (y.equals(div) || y.equals(div2))
                return y;
            div2 = div;
            div = y;
        }
    }
}