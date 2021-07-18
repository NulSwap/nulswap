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

public class UniswapV2ERC20 implements Contract, IUniswapV2ERC20 {

    public String name = "NUlSwap V2";
    public String symbol = "NUL-V2";
    public int decimals = 18;
    public BigInteger totalSupply;
    public Map<Address, Map<Address, BigInteger>> allowance;
    public Map<Address, BigInteger> balanceOf;



        public byte[] DOMAIN_SEPARATOR;
        // keccak256("Permit(address owner,address spender,uint256 value,uint256 nonce,uint256 deadline)");
        public byte[] PERMIT_TYPEHASH = "0x6e71edae12b1b97f4d1f60370fef10105fa2faae0126114a169c64845d6126c9".getBytes();
        public Map<Address, BigInteger> nonces;

        /*event Approval(address indexed owner, address indexed spender, uint value);*/

        public UniswapV2ERC20(Address token) {
            /*    BigInteger chainId;
               assembly {
                chainId := chainid
                }
              DOMAIN_SEPARATOR = keccak256(
                        abi.encode(
                        keccak256('EIP712Domain(string name,string version,uint256 chainId,address verifyingContract)'),
                        keccak256(name.getBytes()),
                        keccak256("1".getBytes()),
                        chainId,
                        new Address(this.toString())
                        )
                );
        }*/
        }

        public String name(){
                return name;
        }
        public String symbol(){
                return symbol;
        }

        public int decimals(){
                return decimals;
        }

        public BigInteger totalSupply(){
                return totalSupply;
        }

        public byte[] DOMAIN_SEPARATOR(){
                return DOMAIN_SEPARATOR;
        }

        public byte[] PERMIT_TYPEHASH(){
                return PERMIT_TYPEHASH;
        }

        public BigInteger balanceOf(Address owner){
                return balanceOf.get(owner);
        }

        public BigInteger nonces(Address owner){
                return nonces.get(owner);
        }

        public BigInteger allowance(Address owner, Address spender){
                Map<Address, BigInteger> ownerAllowed = allowance.get(owner);
                if (ownerAllowed == null) {
                        return BigInteger.ZERO;
                }
                BigInteger value = ownerAllowed.get(spender);
                if (value == null) {
                        value = BigInteger.ZERO;
                }
                return value;
        }


        protected void _mint(Address to, BigInteger value) {
                totalSupply = totalSupply.add(value);
                balanceOf.put(to, balanceOf.get(to).add(value));
                emit(new Transfer(null, to, value));
        }

        protected void _burn(Address from, BigInteger value){
                balanceOf.put(from, balanceOf.get(from).subtract(value));
                totalSupply = totalSupply.subtract(value);
                emit( new Transfer(from, null, value));
        }

        private void _approve(Address owner, Address spender, BigInteger value) {

                Map<Address, BigInteger> address1Allowed = allowance.get(owner);
                if (address1Allowed == null) {
                        address1Allowed = new HashMap<Address, BigInteger>();
                        allowance.put(owner, address1Allowed);
                }
                address1Allowed.put(spender, value);
                emit(new Approval(owner, spender, value));
        }

        private void _transfer(Address from, Address to, BigInteger value) {
                balanceOf.put(from, balanceOf.get(from).subtract(value));
                balanceOf.put(to, balanceOf.get(to).add(value));
                emit( new Transfer(from, to, value));
        }

        public boolean approve(Address spender, BigInteger value){
                _approve(Msg.sender(), spender, value);
                return true;
        }

        public boolean transfer(Address to, BigInteger value) {
                _transfer(Msg.sender(), to, value);
                return true;
        }

        public boolean transferFrom(Address from, Address to, BigInteger value){
                BigInteger allowance = allowance(from, to);
                check(allowance, value, "Insufficient approved token");
                setAllowed(from, to, allowance.subtract(value));
                _transfer(from, to, value);
                return true;
        }
        private void setAllowed(Address address1, Address address2, BigInteger value) {
                check(value);
                Map<Address, BigInteger> address1Allowed = allowance.get(address1);
                if (address1Allowed == null) {
                        address1Allowed = new HashMap<Address, BigInteger>();
                        allowance.put(address1, address1Allowed);
                }
                address1Allowed.put(address2, value);
        }
        private void check(BigInteger value) {
                require(value != null && value.compareTo(BigInteger.ZERO) >= 0);
        }
        private void check(BigInteger value1, BigInteger value2) {
                check(value1);
                check(value2);
                require(value1.compareTo(value2) >= 0);
        }

        private void check(BigInteger value, String msg) {
                require(value != null && value.compareTo(BigInteger.ZERO) >= 0, msg);
        }

        private void check(BigInteger value1, BigInteger value2, String msg) {
                check(value1);
                check(value2);
                require(value1.compareTo(value2) >= 0, msg);
        }

        public void permit(Address owner, Address spender, BigInteger value, BigInteger deadline, int v, byte[] r, byte[] s) {
                /*        require(deadline.compareTo(BigInteger.valueOf(Block.timestamp())) >= 0 , "UniswapV2: EXPIRED");
               byte[] digest = keccak256(
                abi.encodePacked(
                '\x19\x01',
                DOMAIN_SEPARATOR,
                keccak256(abi.encode(PERMIT_TYPEHASH, owner, spender, value, nonces[owner]++, deadline))
                )
                );
                Address recoveredAddress = ecrecover(digest, v, r, s);
                require(recoveredAddress.equals(null) && recoveredAddress == owner, "UniswapV2: INVALID_SIGNATURE");
                _approve(owner, spender, value);
                }*/
        }

}
