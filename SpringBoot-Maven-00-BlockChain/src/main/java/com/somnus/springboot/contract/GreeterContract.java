package com.somnus.springboot.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.1.1.
 */
public final class GreeterContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161075038038061075083398101806040528101908080518201929190505050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508060019080519060200190610089929190610090565b5050610135565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100d157805160ff19168380011785556100ff565b828001600101855582156100ff579182015b828111156100fe5782518255916020019190600101906100e3565b5b50905061010c9190610110565b5090565b61013291905b8082111561012e576000816000905550600101610116565b5090565b90565b61060c806101446000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806341c0e1b5146100675780634ac0d66e1461007e578063cfae3217146100e7578063ebaac77114610177575b600080fd5b34801561007357600080fd5b5061007c6101e0565b005b34801561008a57600080fd5b506100e5600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610271565b005b3480156100f357600080fd5b506100fc61047f565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561013c578082015181840152602081019050610121565b50505050905090810190601f1680156101695780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561018357600080fd5b506101de600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610521565b005b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141561026f576000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16ff5b565b806040518082805190602001908083835b6020831015156102a75780518252602082019150602081019050602083039250610282565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020600160405180828054600181600116156101000203166002900480156103315780601f1061030f576101008083540402835291820191610331565b820191906000526020600020905b81548152906001019060200180831161031d575b505091505060405180910390207f047dcd1aa8b77b0b943642129c767533eeacd700c7c1eab092b8ce05d2b2faf56001846040518080602001806020018381038352858181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156103ef5780601f106103c4576101008083540402835291602001916103ef565b820191906000526020600020905b8154815290600101906020018083116103d257829003601f168201915b5050838103825284818151815260200191508051906020019080838360005b8381101561042957808201518184015260208101905061040e565b50505050905090810190601f1680156104565780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a3806001908051906020019061047b92919061053b565b5050565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105175780601f106104ec57610100808354040283529160200191610517565b820191906000526020600020905b8154815290600101906020018083116104fa57829003601f168201915b5050505050905090565b806001908051906020019061053792919061053b565b5050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061057c57805160ff19168380011785556105aa565b828001600101855582156105aa579182015b828111156105a957825182559160200191906001019061058e565b5b5090506105b791906105bb565b5090565b6105dd91905b808211156105d95760008160009055506001016105c1565b5090565b905600a165627a7a723058208a52e61fad3f31dc705b8162f78dae3904f4835e30478c1f6bf71b638d5a85150029";

    private GreeterContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private GreeterContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<ModifiedEventResponse> getModifiedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Modified", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ModifiedEventResponse> responses = new ArrayList<ModifiedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ModifiedEventResponse typedResponse = new ModifiedEventResponse();
            typedResponse.oldGreetingIdx = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newGreetingIdx = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.oldGreeting = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newGreeting = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ModifiedEventResponse> modifiedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Modified", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ModifiedEventResponse>() {
            @Override
            public ModifiedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ModifiedEventResponse typedResponse = new ModifiedEventResponse();
                typedResponse.oldGreetingIdx = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newGreetingIdx = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.oldGreeting = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newGreeting = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<TransactionReceipt> kill() {
        Function function = new Function(
                "kill", 
                Arrays.asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> newGreeting(String _greeting) {
        Function function = new Function(
                "newGreeting", 
                Arrays.asList(new org.web3j.abi.datatypes.Utf8String(_greeting)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> greet() {
        Function function = new Function("greet", 
                Arrays.asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }
    
    public RemoteCall<TransactionReceipt> write(String _greeting) {
        Function function = new Function("write", 
                Arrays.asList(new Utf8String(_greeting)), 
                Arrays.<TypeReference<?>>asList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<GreeterContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _greeting) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Utf8String(_greeting)));
        return deployRemoteCall(GreeterContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<GreeterContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _greeting) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Utf8String(_greeting)));
        return deployRemoteCall(GreeterContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static GreeterContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new GreeterContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static GreeterContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new GreeterContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class ModifiedEventResponse {
        public byte[] oldGreetingIdx;

        public byte[] newGreetingIdx;

        public String oldGreeting;

        public String newGreeting;
    }
}